package cs2420;

import java.io.File;
import java.nio.file.Paths;

/**
 * Class to test and time the Huffman algorithm to determine the optimal number
 * of words to use for compressing the text. Warning, all verbose flags should
 * be off to run this. I also advise modifying the compress_file method so it
 * does not write the file every time the data is compressed. This is what I did
 * during testing.
 * 
 * @author Brent Collins, Landon Crowther
 *
 */

public class HuffmanTiming
{

	final static int averageCount = 20;

	public static void main(String[] args)
	{
		HuffmanTreeUsingWords huffman;

		String file_name = "";

		// choose a file to use
		file_name = "Resources\\two_cities";
		//file_name = "Resources\\constitution";
		// file_name = "Resources\\green_eggs_and_ham";
		// file_name = "Resources\\alphabetplus";
		// file_name = "Resources\\alphabet";
		// file_name = "Resources\\simple";
		// file_name = "Resources\\a_few_letters";

		// get baseline scores
		huffman = new HuffmanTreeUsingWords(0);


		System.out.println("Word Count: " + "\t" + "Compression Ratio:");
		for (int i = 0; i <= 1500; i += 10)
		{
			huffman = new HuffmanTreeUsingWords(i);

			huffman.compress_file(new File(file_name), new File(file_name + "." + huffman.WORD_COUNT + ".huf"));

			double compressionRatio = huffman.originalSize / (double) huffman.compressedSize;

			System.out.println(i + "\t" + compressionRatio);

		}

	}

}
