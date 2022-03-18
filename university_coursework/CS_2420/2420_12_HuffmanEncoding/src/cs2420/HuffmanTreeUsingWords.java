package cs2420;

import static cs2420.Bit_Operations.*;
import static cs2420.Utility.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @author Original Huffman code by Erin Parker, April 2007 Adapted by H. James
 *         de St. Germain to words as symbols 2008 Heavily Updated by H. James
 *         de St. Germain 2017
 *         Additional methods implemented by Brent Collins and Landon Crowther
 * 
 *         Implements file compression and decompression using Huffman's
 *         algorithm.
 * 
 *         Instead of compressing on just characters, we treat words as symbols
 *         as well. To see what the best levels of compression are, we choose
 *         the "N" most frequent words (modified by their length) and use these
 *         along with characters as symbols for compression.
 * 
 */
public class HuffmanTreeUsingWords {
	/**
	 * allows us to tag the end of a file with a special character
	 * 
	 * otherwise it's not clear how to end the bit stream output of compression
	 * (i.e., bit stream needs to print a full byte at end, but we may only have
	 * a partial byte)
	 */
	final static String EOF = "EOF";

	/**
	 * For encoding, how many words to treat as symbols
	 */
	int WORD_COUNT;

	/**
	 * For a verbose account of what is going on, set these to true.
	 */
	static final boolean VERBOSE_ENCODING_TREE = true;
	static final boolean VERBOSE_FILE_SIZE = true;
	static final boolean VERBOSE_PRINT_SYMBOL_BITS = true;
	static final boolean VERBOSE_PRINT_TREE = true;

	/**
	 * When we encode using words the original file size is lost, store it to
	 * get compression ratio. Also keep track of unique words in each document
	 * so we can see what the optimal percentage of words used is in our tests.
	 */

	static int originalSize;
	static int compressedSize;
	static int uniqueWords;

	/**
	 * The root of the Huffman tree
	 */
	private Node root;

	/**
	 * Constructor for an empty tree
	 * 
	 * @param words_as_symbols_count
	 *            - take the top N words and use as symbols
	 *
	 */
	public HuffmanTreeUsingWords(int words_as_symbols_count) {
		if(words_as_symbols_count < 0) {
			throw new IllegalArgumentException("Must be positive number");
		}
		this.WORD_COUNT = words_as_symbols_count;
		this.root = null;
	}

	/**
	 * Generates a compressed version of the input file.
	 * 
	 * 1) read the file, counting all the symbols 2) create the huffman tree
	 * based on frequency counts 3) compress the data into a binary file
	 * 
	 * @param infile
	 *            - input file of (uncompressed) data
	 * @param outfile
	 *            - output file of compressed data
	 */
	public void compress_file(File infile, File outfile) {
		List<String> ordered_list_of_symbols = new ArrayList<>();

		Hashtable<String, Node> top_words;
		Hashtable<String, Node> all_symbols;

		ArrayList<Character> buffer = read_file(infile);
		originalSize = buffer.size();
		top_words = compute_most_common_word_symbols(buffer, this.WORD_COUNT);
		all_symbols = compute_remaining_single_character_symbols(buffer, top_words, ordered_list_of_symbols);

		create_tree(all_symbols.values());

		compress_data(outfile, all_symbols, ordered_list_of_symbols);

	}

	/**
	 * Generates a decompressed version of the input file.
	 * 
	 * 1) Read the encoding information (how to reconstruct the tree) from the
	 * front of the file. 2) Build the Huffman tree (exactly as it was for
	 * compression) 3) read the bits from the compressed file one by one until a
	 * bit sequence finds a leaf in the huffman tree. report the symbol in the
	 * leaf and start again.
	 * 
	 * @param infile
	 *            - Path to input file (of compressed data)
	 * @param outfile
	 *            - output file of decompressed data
	 */
	public void decompress_file(Path path, File outfile) {
		try {
			//
			// have to read the file as bytes and then process bits inside of
			// those bytes
			//
			byte[] bytes = Files.readAllBytes(path);
			ByteBuffer byte_buffer = ByteBuffer.wrap(bytes);

			Hashtable<String, Node> symbols = new Hashtable<>();

			symbols = read_file_header_with_symbol_frequencies(byte_buffer); // builds
																				// symbol
																				// frequency
																				// table

			if (VERBOSE_FILE_SIZE) {
				System.out.println("");
				System.out.printf("\tHeader Size in Bytes:   %10d\n", byte_buffer.position());
				System.out.printf("\tBody Size in Bytes:     %10d\n", byte_buffer.remaining());
				System.out.printf("\tTotal Size in Bytes:    %10d\n", bytes.length);
			}

			this.root = create_tree(symbols.values());

			decompress_data(this.root, byte_buffer, outfile);
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	/**
	 * Write the compressed file, including the encoding information and the
	 * compressed data.
	 * 
	 * @param file
	 *            - the file to write the data to
	 * @param ordered_list_of_symbols
	 *            - the symbols created by parsing the input file
	 * 
	 * @throws IOException
	 *             - if something goes wrong...
	 */
	private static void compress_data(File file, Hashtable<String, Node> symbols,
			List<String> ordered_list_of_symbols) {
		try (DataOutputStream out = new DataOutputStream(new FileOutputStream(file))) {
			byte[] file_header = build_file_header(symbols.values());
			byte[] symbol_bit_stream = build_compressed_bit_stream(ordered_list_of_symbols, symbols);

			out.write(file_header);
			out.write(symbol_bit_stream);
			compressedSize = file_header.length + symbol_bit_stream.length;
			if (VERBOSE_FILE_SIZE) {
				System.out.printf("Header Size:   %10d bytes\n", file_header.length);
				System.out.printf("Encoding Size: %10d bytes\n", symbol_bit_stream.length);
				System.out.printf("Total Size:    %10d bytes\n", (file_header.length + symbol_bit_stream.length));
				System.out.printf("Reduction in Size: %10f \n",
						(originalSize - (file_header.length + symbol_bit_stream.length)) / (double) originalSize);
				System.out.printf("Compression ratio: %10f \n",
						originalSize / ((double) file_header.length + symbol_bit_stream.length));

			}

		} catch (IOException e) {
			throw new RuntimeException("Eror: could not read file");
		}

	}

	/**
	 * Read the compressed data bit stream, translating it into characters, and
	 * writing the uncompressed values into "out"
	 * 
	 * @param bits
	 *            - the compressed bit stream that needs to be turned back into
	 *            real characters and words
	 * @param outfile
	 *            - the file to put this data into
	 * @throws IOException
	 *             - if something goes wrong
	 */
	private static void decompress_data(Node huffman_root, ByteBuffer bits, File outfile) throws IOException {
		List<String> symbol_list = convert_bitstream_to_original_symbols(bits, huffman_root);

		write_data(outfile, symbol_list);
	}

	/**
	 * The compressed file has two parts: a) the HEADER - symbol frequency count
	 * b) the DATA - compressed symbol bit stream
	 * 
	 * Here we read the HEADER and build the list of symbols (and counts)
	 * 
	 * 1) read four bytes (representing the number of characters in the symbol
	 * 2) read that many groups of bytes (each is a character in the symbol) 3)
	 * read four bytes (representing the symbol's frequency)
	 * 
	 * repeat from 1, unless we read a zero, then the encoding table is complete
	 * 
	 * Reads the file_bytes parameter in the proper header format. 
	 * 
	 * 
	 * @param file_bytes
	 *            - the bytes from the compressed file (which start with the
	 *            header information)
	 * @return the hashtable containing all the symbol nodes
	 */
	private static Hashtable<String, Node> read_file_header_with_symbol_frequencies(ByteBuffer file_bytes) {

		if (VERBOSE_ENCODING_TREE) {
			System.out.println("\n---------------- Reading encoding tree information  -----------------");
		}

		Hashtable<String, Node> symbolList = new Hashtable<String, Node>();

		while (true) {
			int length = file_bytes.getInt();
			if (length == 0) {
				break;
			}

			String symbol = "";
			for (int i = 0; i < length; i++) {
				symbol += (char) file_bytes.get();
			}

			int freq = file_bytes.getInt();
			Node n = new Node(symbol + "", freq);
			symbolList.put(symbol, n);
		}

		if (VERBOSE_ENCODING_TREE) {
			System.out.println("\n\tRead encoding table. Size:  " + file_bytes.position() + " bytes");
		}

		return symbolList;
	}

	/**
	 * transfer all the data from the file into an array list
	 * 
	 * @param infile
	 *            - file we are compressing
	 * 
	 * @return the ArrayList representation of the file (ordered of course)
	 */
	static ArrayList<Character> read_file(File infile) {
		final int End_Of_Stream = -1;
		ArrayList<Character> buffer = new ArrayList<Character>(1000);

		try (FileReader reader = new FileReader(infile)) {

			while (true) {
				int character_code = reader.read();

				if (character_code == End_Of_Stream) {
					break;
				}

				buffer.add((char) character_code);
			}
		} catch (Exception e) {
			throw new RuntimeException("Error: reading the file.");
		}

		return buffer;
	}

	/**
	 * To build the Huffman tree (for compression), we must compute the list of
	 * symbols from the file.
	 * 
	 * Algorithm:
	 * 
	 * 1) counts how often all the words appear 2) keep the N most common words
	 * o) put every symbol into a Node and store this in a hash table
	 * 
	 * Method is very similar to compute_remaining_words (which was written),
	 * except for every symbol was added to a hashtable. If the symbol previously
	 * existed, the frequency for that symbol was incremented. Once all symbols
	 * were in there, the nodes were sorted from a list, and the proper
	 * amount of nodes were added to the return Hashtable. 
	 *
	 * @param buffer
	 *            - list of all characters in file in order
	 * @param count
	 *            - find the top N (count) words
	 * 
	 * @return a hash table containing nodes, where each node contains the word
	 *         and the frequency of that word
	 * 
	 */
	static Hashtable<String, Node> compute_most_common_word_symbols(ArrayList<Character> buffer, int count) {

		// create hash table to store the nodes and the associated text
		Hashtable<String, Node> histogram = new Hashtable<String, Node>();
		String word = "";
		// loop through all the characters, if there is more than one character
		// in a row, add the word, or increment the value
		for (Character c : buffer) {
			if (Character.isLetter(c)) {
				word += c;
			} else if (word.length() > 1) {
				Utility.increment(word, histogram);
				word = "";
			} else {
				word = "";
			}
		}

		// keep track of the number of unique words in the text
		uniqueWords = histogram.size();

		// dump the values into a list to be sorted
		List<Node> sortedList = new ArrayList<Node>(histogram.values());

		// Sort the list in descending order
		Collections.sort(sortedList, Collections.reverseOrder());

		// only keep the required amount of words
		if ( count > uniqueWords ) {
			sortedList = sortedList.subList(0, uniqueWords);
		}
		else {
			sortedList = sortedList.subList(0, count);
		}

		// clear the histogram and refill it with the sorted desired words
		histogram.clear();
		for (Node n : sortedList) {
			histogram.put(n.get_symbol(), n);
		}

		return histogram;

	}

	/**
	 * given the list of all characters in the file, count all symbols in the
	 * file that are not the "most frequent" word symbols (which have already
	 * been counted). Return the hash table containing ALL nodes(symbols and
	 * counts) for the given characters
	 * 
	 * ADDITIONALLY - return the ordered list of all symbols via the "out"
	 * parameter
	 * 
	 * @param buffer
	 *            - the file's characters
	 * @param word_symbols
	 *            - the words that have already been identified as most common
	 *            and are to be used as symbols
	 * @param ordered_list_of_symbols
	 *            - RETURNS an array list of all symbols in the file.
	 * 
	 * @return the final symbol table representing the huffman nodes (not
	 *         connected yet) for the symbols in the file.
	 */
	public static Hashtable<String, Node> compute_remaining_single_character_symbols(ArrayList<Character> buffer,
			Hashtable<String, Node> word_symbols, List<String> ordered_list_of_symbols) {
		//
		// Now count all the other symbols (e.g., single characters not in
		// symbol words)
		//

		Hashtable<String, Node> all_symbols = new Hashtable<>();
		String current_symbol = "";

		all_symbols.putAll(word_symbols);

		for (Character ch : buffer) {
			if (Character.isLetter(ch)) // build up words
			{
				current_symbol += ch;
			} else // a non letter (thus marking the division between possible
					// word symbols
			{
				// 1) if we have started to build a word
				if (current_symbol.length() > 0) {
					// if it is not a TOP word
					if (!word_symbols.containsKey(current_symbol)) {
						// add all it's letters to the symbols table and the
						// ordered list
						for (int i = 0; i < current_symbol.length(); i++) {
							increment("" + current_symbol.charAt(i), all_symbols);
							ordered_list_of_symbols.add("" + current_symbol.charAt(i));
						}
					} else // just add the word to the ordered list
					{
						ordered_list_of_symbols.add(current_symbol);
					}
				}

				// 2) account for the current character (non-letter)
				increment("" + ch, all_symbols);
				ordered_list_of_symbols.add("" + ch);

				// start over building another word
				current_symbol = "";
			}
		}

		// add end of file at end of symbols
		all_symbols.put(EOF, new Node(EOF, 1));
		ordered_list_of_symbols.add(EOF);

		return all_symbols;
	}

	/**
	 * given a list of bits (and the root of the huffman tree) create a list of
	 * symbols
	 * 
	 * 
	 * Assuming that the bit stream was constructed properly, this method does
	 * the proper byte operations that can "decode" the compressed file. 
	 * This method is called after the header has been read. 
	 * 
	 * DECOMPRESSION Pseudocode
	 * 
	 * For each bit in the bit stream (the compressed file, after the header),
	 * code += get the next bit if code forms path from root of huffman tree to
	 * leaf we have a symbol, save it reset code to empty end end
	 * 
	 * @param bit_stream
	 *            - all the bits representing the symbols in the file
	 * @param root
	 *            - the root of the huffman tree
	 * 
	 * @return the reconstructed list of symbols
	 */
	static List<String> convert_bitstream_to_original_symbols(ByteBuffer bit_stream, Node root) {

		if (VERBOSE_PRINT_SYMBOL_BITS) {
			System.out.println("------------- Converting bit sequences back into symbols -------------------");
		}

		List<String> stringCodes = new ArrayList<String>();

		String code = "";
		while (bit_stream.hasRemaining()) {

			byte bits = bit_stream.get();
			for (int i = 0; i < 8; i++) {
				// get bit, if on, add 1 to code, else add 0
				boolean myBit = Bit_Operations.get_bit(bits, i);
				if (myBit) {
					code += "1";
				} else {
					code += "0";
				}
				// code += Bit_Operations.get_bit(bits, i) ? "1" : "0";
				String symbol = root.get_symbol(code);
				// if the symbol is at leaf node, add the symbol
				if (symbol != null) {

					// if we hit the end of file, return
					if (symbol.equals(EOF)) {
						return stringCodes;

					}
					stringCodes.add(symbol);
					code = "";

				}
			}
		}

		return stringCodes;
		// throw new RuntimeException("Error: did not find EOF termination
		// character");

	}

	/**
	 * COMPRESSION - write the symbol frequencies
	 * 
	 * This method builds the proper header file. It works by creating three sections
	 * per symbol: the first is the lenght of the symbol, the next is the characters that
	 * make up the symbol, and the last is the frequency of the symbol. 
	 * 
	 * These sections allow for tree decomopression. 
	 * 
	 * 1) Writes the symbols and frequency information to the output file, 
	 * allowing the Huffman tree to be reconstructed at the time of
	 * decompression.
	 * 
	 * 2) NOTE: for debug purposes, the symbols are written from most frequent
	 * to least frequent... but this is not necessary
	 * 
	 * 3) FORMAT of HEADER is:
	 * 
	 * LENGTH, SYMBOL, FREQUENCY (repeated for all symbols) ZERO (so we know we
	 * are out of symbols - okay because a length of 0 doesn't make sense)
	 * 
	 * 4) EXAMPLE (for the following symbols and frequencies: (a,5) (hello,10),
	 * (EOF,1)
	 * 
	 * 1a5, 5hello10, 3EOF1 (note: there of course are no spaces or commas and
	 * this information is written as bits....)
	 * 
	 * @param huffman_nodes
	 *            - the collection of symbols and frequencies (i.e., Nodes) in
	 *            the document
	 * 
	 * @throws IOException
	 *             - if something goes wrong with the file writing
	 */
	private static byte[] build_file_header(Collection<Node> huffman_nodes) throws IOException {
		int count = 0;

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		;

		if (VERBOSE_ENCODING_TREE) {
			System.out.printf("------------ encoding table (ordered by frequency) ------------\n");
		}

		// sort nodes in ascending order to adhere to format
		List<Node> sortedNodes = new ArrayList<Node>(huffman_nodes);
		Collections.sort(sortedNodes, Collections.reverseOrder());
		for (Node n : sortedNodes) {
			// write the length, symbol and frequency
			out.write(Bit_Operations.convert_integer_to_bytes(n.get_symbol().length()));
			count += 4;
			out.write(n.get_symbol().getBytes());
			count += n.get_symbol().length();
			out.write(Bit_Operations.convert_integer_to_bytes(n.get_frequency()));
			count += 4;
			
		}

		if (VERBOSE_ENCODING_TREE) {
			System.out.println("\n\tEncoding Table Size:  " + count + " bytes");
		}
		// write a 0 to signify end
		out.write(Bit_Operations.convert_integer_to_bytes(0));
		return out.toByteArray();

	}

	/**
	 * DECOMPRESSION
	 * 
	 * Writes the decompressed data (Symbols) to the output file.
	 * 
	 * As each symbol is decompressed, write its component characters
	 * 
	 * @param outfile
	 *            - stream for the output file
	 * @param symbol_list
	 *            - the symbolss to write
	 * 
	 * @throws IOException
	 */
	static void write_data(File outfile, List<String> symbol_list) throws IOException {
		try (FileOutputStream fs = new FileOutputStream(outfile)) {
			for (String symbol : symbol_list) {
				for (int i = 0; i < symbol.length(); i++) {
					fs.write(symbol.charAt(i));
				}
			}
		}
	}

	/**
	 * COMPRESSION
	 * 
	 * For each symbol in the input file, encode it using the Huffman tree by
	 * writing the bit code to the output file.
	 * 
	 * This method uses the "determine_bit_pattern_for_symbol" method
	 * 
	 * PSEUDOCODE:
	 * 
	 * for every symbol (in order ) find bit pattern put bits from pattern into
	 * bitset return the byte[] from the bitset
	 * 
	 * @param ordered_list_of_symbols
	 *            - all the letters (words/symbols) from the file in order
	 * @param table
	 *            - the hashtable from symbol string to node
	 * 
	 * @return the bytes representing the bit stream of the compressed symbols
	 * 
	 * @throws IOException
	 */
	static byte[] build_compressed_bit_stream(List<String> ordered_list_of_symbols, Hashtable<String, Node> table)
			throws IOException {
		BitSet bitset = new BitSet();

		if (VERBOSE_PRINT_SYMBOL_BITS) {
			System.out.println("\n----------- Compressing  --------------");
			System.out.println(
					"Building bit representation of each symbol for " + ordered_list_of_symbols.size() + " symbols");
		}

		int bitIndex = 0;
		for (String str : ordered_list_of_symbols) {
			Node n = table.get(str);
			LinkedList<Integer> bitPattern = determine_bit_pattern_for_symbol(n);
			int length = bitPattern.size();
			for (int i = 0; i < length; i++) {
				if (bitPattern.pollLast() == 1) {
					bitset.set(bitIndex, true);
				}

				bitIndex++;
			}

		}

		if (VERBOSE_PRINT_SYMBOL_BITS) {
			System.out.println("\n----------- done --------------");
		}
		byte[] reversed = Bit_Operations.get_bytes(bitset);

		return reversed;
	}

	/**
	 * Constructs a Huffman tree to represent bit codes for each character.
	 * 
	 * This method slowly condenses the tree until there is only 
	 * one node, and all of the sub-nodes are contained within it. 
	 * 
	 * This is the method is the HEART of the Huffman algorithm.
	 * 
	 * Algorithm:
	 * 
	 * o) put all the nodes into a priority queue
	 * 
	 * 1) choose the two least frequent symbols (removing from PQ) 2) combine
	 * these in a new huffman node 3) put this new node back in the PQ 4) repeat
	 * until no nodes in PQ
	 * 
	 * @return the root of the tree
	 * @param the
	 *            nodes to be built into a tree
	 */
	static Node create_tree(Collection<Node> nodes) {

		PriorityQueue<Node> queue = new PriorityQueue<Node>();
		queue.addAll(nodes);
		int pCount = 0;
		while (queue.size() > 1) {
			Node x = queue.poll();
			Node y = queue.poll();
			// assume y is has the smallest
			Node smaller = y;
			Node larger = x;
			if (x.compareTo(y) < 0) {
				smaller = x;
				larger = y;
			}
			Node parent = new Node("_N_" + pCount, smaller, larger);

			x.set_parent(parent);
			y.set_parent(parent);

			queue.add(parent);
			pCount++;

		}

		Node root = queue.poll();

		if (VERBOSE_PRINT_TREE) {
			System.out.println(root.createDot());
		}
		return root;

	}

	/**
	 * Returns the bit code for a symbol.
	 * 
	 * This is computed by traversing the path from the given leaf node up to
	 * the root of the tree.
	 * 
	 * 1) when encountering a left child, a 0 to be pre-appended to the bit
	 * code, and 2) when encountering a right child causes a 1 to be
	 * pre-appended.
	 * 
	 * For example: the symbol "A" might return the code "1011101"
	 * 
	 * QUESTION: why do we use a linkedlist as the return type? because it's a
	 * queue and we will get the the first symbol input first, doesn't make
	 * sense
	 * 
	 * @param symbol
	 *            - symbol to be encoded
	 * @param node
	 *            - the node in the huffman tree containing the symbol
	 * 
	 */
	private static LinkedList<Integer> determine_bit_pattern_for_symbol(Node leaf) {
		LinkedList<Integer> bitCode = new LinkedList<Integer>();
		if (!leaf.leaf()) {
			throw new RuntimeException("needs to be a leaf node");
		}
		while (leaf.get_parent() != null) {
			if (leaf.parents_left().get_symbol().equals(leaf.get_symbol())) {

				bitCode.add(0);
			} else {
				bitCode.add(1);
			}
			leaf = leaf.get_parent();
		}
		return bitCode;
	}
	/**
	 * getter for the root node
	 * @return the root node of the tree
	 */
	public Node getRoot() {
		return this.root;
	}

}
