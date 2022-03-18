package cs2420;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

/**
 * testing instance for the class HuffmanTreeUsingWords. The class tests the functionality at a high level,
 * but also tests some of the methods in a more granular manner.
 * @author Brent Collins and Landon Crowther
 *
 */

public class HuffmanTreeUsingWordsTest {

	//common files that are used for testing purposes.
	File alphabetFile;
	File aFewWordsFile;
	File jimFile;
	HuffmanTreeUsingWords huffman;

	@Before
	public void setUp() throws Exception {
		alphabetFile = new File("Resources\\alphabet");
		aFewWordsFile = new File("Resources\\a_few_words");
		jimFile = new File("Resources\\a_few_letters_jim_0_.huf");
		HuffmanTreeUsingWords huffman = new HuffmanTreeUsingWords(0);
	}

	/**
	 * high level test to make sure the huffman tree is working. Compress a
	 * file, decompress it, then read it back in and make sure it is the same as
	 * the original file.
	 * 
	 */

	@Test
	public final void testHuffmanTreeUsingWords() {
		
		//create the tree
		huffman = new HuffmanTreeUsingWords(0);
		String file_name = "Resources\\a_few_letters";
		String[] new_file_name = new String[0]; // file_name.split("\\.");

		if (new_file_name.length > 1) {
			file_name = new_file_name[0];
		}
		
		//compress the file
		huffman.compress_file(new File(file_name), new File(file_name + "." + huffman.WORD_COUNT + ".huf"));
		file_name += "." + huffman.WORD_COUNT;
		
		//decompress the file
		huffman.decompress_file(Paths.get(file_name + ".huf"), new File(file_name + ".uncompress"));
		
		//read the file back in and test to make sure it is the same
		ArrayList<String> uncompressed = read_file_into_array(file_name + ".uncompress");
		System.out.println(uncompressed.get(0));
		assertEquals(uncompressed.get(0), "aaaabbbccd");

		
	}
	
	/**
	 * high level test to make sure the huffman tree is working. Compress a
	 * file, decompress it, then read it back in and make sure it is the same as
	 * the original file.
	 * 
	 */

	@Test
	public final void testHuffmanTreeUsingWords2() {
		
		//create the tree
		huffman = new HuffmanTreeUsingWords(0);
		String file_name = "Resources\\a_few_letters";
		String[] new_file_name = new String[0]; // file_name.split("\\.");

		if (new_file_name.length > 1) {
			file_name = new_file_name[0];
		}
		
		//compress the file
		huffman.compress_file(new File(file_name), new File(file_name + "." + huffman.WORD_COUNT + ".huf"));
		file_name += "." + huffman.WORD_COUNT;
		
		//decompress the file
		huffman.decompress_file(Paths.get(file_name + ".huf"), new File(file_name + ".uncompress"));
		
		//read the file back in and test to make sure it is the same
		ArrayList<String> uncompressed = read_file_into_array(file_name + ".uncompress");
		System.out.println(uncompressed.get(0));
		assertEquals(uncompressed.get(0), "aaaabbbccd");

		
	}
	/**
	 *compress a file, then compare it against a compressed file
	 *provided to us by Professor  de St. Germain. Make sure they are equal
	 */
	@Test
	public final void testCompress_file() {

		//create the tree
		huffman = new HuffmanTreeUsingWords(0);
		String file_name = "Resources\\a_few_letters";
		String[] new_file_name = new String[0]; // file_name.split("\\.");

		if (new_file_name.length > 1) {
			file_name = new_file_name[0];
		}
		
		//compress the file
		huffman.compress_file(new File(file_name), new File(file_name + "." + huffman.WORD_COUNT + ".huf"));
		file_name += "." + huffman.WORD_COUNT;
		
		//read file back in
		ArrayList<String> compressed = read_file_into_array(file_name + ".huf");
		
		ArrayList<String> knownCompressed = read_file_into_array("Resources\\a_few_letters_jim.0" + ".huf");
		
		assertEquals(compressed, knownCompressed);
		
	}
	/**
	 * take an already compressed file, provided to us by Professor  de St. Germain
	 * then decompress it and see if it is the correct file
	 */
	@Test
	public final void testDecompress_file() {
		huffman = new HuffmanTreeUsingWords(0);
		String file_name = "Resources\\a_few_letters_jim.0";
		String[] new_file_name = new String[0]; // file_name.split("\\.");

		if (new_file_name.length > 1) {
			file_name = new_file_name[0];
		}
		huffman.decompress_file(Paths.get(file_name + ".huf"), new File(file_name + ".uncompress"));
		
		//read the file back in and test to make sure it is the same
		ArrayList<String> uncompressed = read_file_into_array(file_name + ".uncompress");
		System.out.println(uncompressed.get(0));
		assertEquals(uncompressed.get(0), "aaaabbbccd");
	}

	
	/**
	 * test if the compute_most_common_word_symbols computes the most common
	 * words against a known set
	 */
	@Test
	public final void testCompute_most_common_word_symbols() {
		//create the huffman tree
		huffman = new HuffmanTreeUsingWords(0);
		String file_name = "Resources\\a_few_letters";
		String[] new_file_name = new String[0]; // file_name.split("\\.");

		if (new_file_name.length > 1) {
			file_name = new_file_name[0];
		}
		
		//compress the file
		huffman.compress_file(new File(file_name), new File(file_name + "." + huffman.WORD_COUNT + ".huf"));
		ArrayList<Character> buffer = huffman.read_file(new File(file_name));
		
		//get the most common words, in this there is only one
		Hashtable<String, Node> fewWords = huffman.compute_most_common_word_symbols(buffer, 1);
		
		//test to make sure the size is one
		assertTrue(fewWords.size() == 1);
		
		//create the known node
		Node n1 = new Node("aaaabbbccd", 1);
		

		
		//test to make sure the nodes are equal
		assertEquals(n1.get_symbol(), fewWords.get("aaaabbbccd").get_symbol());
//		assertEquals(n1.get_frequency(), n1Actual.get_frequency());
//
//		assertEquals(n2.get_symbol(), n2Actual.get_symbol());
//		assertEquals(n2.get_frequency(), n2Actual.get_frequency());

	}
	
	/**
	 * test if the compute_most_common_word_symbols computes the most common
	 * words against a known set with a larger text and using more than 0 words
	 */
	@Test
	public final void testCompute_most_common_word_symbols2() {
		
		huffman = new HuffmanTreeUsingWords(1);
		String file_name = "Resources\\green_eggs_and_ham";
		String[] new_file_name = new String[0]; 

		if (new_file_name.length > 1) {
			file_name = new_file_name[0];
		}

		huffman.compress_file(new File(file_name), new File(file_name + "." + huffman.WORD_COUNT + ".huf"));
		ArrayList<Character> buffer = huffman.read_file(new File(file_name));
		Hashtable<String, Node> fewWords = huffman.compute_most_common_word_symbols(buffer, 1);

		assertTrue(fewWords.size() == 1);
		//it is know from previous analysis that "not" is the most common word and  occurs 65 times
		Node n1 = new Node("not", 65);
		

		

		assertEquals(n1.get_symbol(), fewWords.get("not").get_symbol());


	}

	

	static public ArrayList<String> read_file_into_array(String file_name) {
		ArrayList<String> strings = new ArrayList<>();
		Scanner scanner;
		try {
			scanner = new Scanner(new File(file_name));

			while (scanner.hasNext()) {
				strings.add(scanner.next());
			}
			scanner.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		return strings;
	}

}
