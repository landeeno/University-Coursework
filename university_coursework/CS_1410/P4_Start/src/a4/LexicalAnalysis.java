/**
 * Landon Crowther
 * u0926601
 * CS 1410 - HW 4
 * 
 * Note: I'm not sure what other try-catch statements to include... 
 */

package a4;
import java.io.File;

import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

/**
 * This class opens a file of query words, then counts the occurrences of each query
 * word in two comparison documents. The relative frequency of each query word is shown
 * as a bar chart followed by the exact percentage of time the query occurs. 
 * @author David Johnson
 *
 */
public class LexicalAnalysis {

	/**
	 * Drives the analysis program. Main should have a user select two texts and then 
	 * loop over a query file, printing the relative frequency of each query.
	 * @param args
	 */
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() {



				// Open a file chooser.
				JFileChooser chooser = new JFileChooser();

				// Prompt the user for a file
				System.out.println("Select the first text");
				File file1 = fileChooser(chooser);

				// Prompt the user for a second file
				System.out.println("Select the second text");
				File file2 = fileChooser(chooser);

				// Do not proceed if either file choice was cancelled.
				if (file1 != null && file2 != null) {
					// Look in the project folder for a queries file.
					String filename = "queries.txt";
					// Get the file
					File queryFile = new File(filename);
					// Declare a Scanner for use
					Scanner fs = null;
					try {
						fs = new Scanner(queryFile);
					} catch (FileNotFoundException e) {
						// Say something bad has happened and quit if the query file doesn't work.
						System.out.println("ERROR: File " + queryFile.getName() + " does not exist.");
						System.exit(0);
					}
					// If we made it here, read the queries and analyze the frequency
					while (fs.hasNext()) {
						// Get the query word
						String query = fs.next();
						System.out.println("Comparing usage of " + query);

						// Get the percentage of words that are query in file 1.
						double percent1 = analyzeFile(file1,query);
						String chart = makeBar(percent1, '1');
						System.out.println(chart + " " + String.format( "%.2f",percent1) + "%");

						// Get the percentage of words that are query in file 2.
						double percent2 = analyzeFile(file2,query);
						String chart2 = makeBar(percent2, '2');
						System.out.println(chart2 + " " + String.format( "%.2f",percent2) + "%");
					} // no more tokens
				} // both files were chosen
				else {
					System.out.println("A file was not chosen");
				}
			} 
		}
				);}

	/**
	 * Given a JFileChooser, use it to ask the user for a file.
	 * @param chooser
	 * @return Either a File object or null, if nothing chosen.
	 */
	public static File fileChooser(JFileChooser chooser) {
		int outcome = chooser.showOpenDialog(null);
		if (outcome == JFileChooser.APPROVE_OPTION) {
			File filename = chooser.getSelectedFile();
			return filename;
		}
		else
			return null;
	}

	/**
	 * Given a file, count how many occurrences of key are in file. A token matches
	 * the key if the cleaned token (see description of cleanToken) matches an
	 * all upper-case version of key.
	 * @param file 
	 * @param key a string of 'a'-'z' or 'A'-'Z' letters
	 * @return the percentage of tokens in file that match key, or quits if file
	 * is not a valid file.
	 */
	public static double analyzeFile( File file, String key) {

		//initializing the keyword input -- start by capitalizing test word
		key = key.toUpperCase();

		//initializing the scanner
		Scanner s = null;

		//try-catch statement
		try {
			s = new Scanner(file);

			//prints out error message and quits program if file can't be found
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			System.exit(0);
		}

		//initializing amount of key words
		int keyCount = 0;

		//initializing token amount
		int wordCount = 0;

		//scanner loop
	//	try {
		while (s.hasNext()) {
			String word = s.next();

			//re-format scanner token to remove punctuation
			word = cleanToken(word);

			//check to see if token is equivalent to key word
			if (word.compareTo(key) == 0) {

				//increase key word quantity
				keyCount++;
			}

			//increase total word count
			wordCount++;

		}
		s.close();
		//} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("asdf");
			System.exit(0);
		//}

		//calculate percentage of words that are keywords
		double percentage  = computePercentage(keyCount, wordCount);
		return percentage;		
	}

	/**
	 * Given a count of found for key and the total number of words in a file,
	 * computes the percentage of words that is key, according to the matching rules.
	 * @param key is the count of words that match the query word
	 * @param words is the count of words in the file.
	 * @return returns the percentage in a range from 0.0-100.0, so retaining fractional values.
	 */
	public static double computePercentage(int key, int words) {

		//initialize percentage variable and perform percentage math
		if (words == 0) {
			System.out.println("Cannot divide by zero");
			System.exit(0);
		}
		double percentage = (double)key/(double)words*100;
		return percentage;
	}

	/**
	 * Given a non-negative number pos, and character c, produces a string with 
	 * round(pos-1) number of '+' followed by c. 
	 * If pos is zero, then produce a string with just c in it (this looks the
	 * same as a pos of 1).
	 * @param pos
	 * @param c
	 * @return a string representing a bar chart for the value in pos.
	 */
	public static String makeBar(double pos, char c) {
		//initialize with empty string
		String finalString = "";

		//determine how many characters to place in front of symbol
		int barAmount = (int)(Math.round(pos-1));

		//determine if symbols are required at all
		if (pos == 0) {
			finalString = finalString + (c);
		}

		//if the wordCount percentage is greater than 1, add a characater to it for each percent
		else {
			for (int i=0; i < barAmount; i++) {
				finalString = finalString + "+";
			}
			finalString = finalString + (c);
		}

		return finalString;
	}

	/**
	 * 
	 * 
	 * cleanToken tries to clean up a token from a text file. The text file may
	 * have punctuation we want to ignore, and we will remove it according to the
	 * following rules. These rules are not perfect, but they should be followed exactly.
	 * 
	 * Punctuation counts as a . ? , ! " or ' character. The character ' is written
	 * as '\'' since ' helps define the boundary of the character. The \ tells the
	 * compiler that the letter following is a special character.
	 * 
	 * The rule for cleaning up a token is that we will remove 
	 *   - up to one punctuation character from the front of the token (to handle "hello situations)
	 *   - up to two punctuation characters from the end of the token (to handle it?" kind of situations)
	 *   - turn the remaining characters upper-case.
	 * 
	 *  I recommend writing helper methods to handle different cases.
	 * @param word token from the source file.
	 * @return the word with the punctuation removed according to the rules and uppercase.
	 */
	public static String cleanToken(String word) {

		//initialize the word length
		int wordLength = word.length();

		//try catch statement -- checks to see if the string is
		//a single character with just punctuation mark
		try {
			//check to see if the first character is a punctuation mark
			if (checkPunct(word.charAt(0)) == true) {

				//if true, get rid of punctuation & redefine the word length
				word = word.substring(1, wordLength);	
				wordLength = word.length();
			}

			//check to see if the very last character is a punctuation mark
			if (checkPunct(word.charAt(wordLength - 1)) == true) {

				//if true, get rid of punctuation & redefine the word length 
				word = word.substring(0, wordLength - 1);
				wordLength = word.length();
			}

			//check to see if the second to last character is a punctuation mark
			//	note:	this second "if" statement is only applicable if there are
			//			two or more punctuation marks in the token
			if (checkPunct(word.charAt(wordLength - 1)) == true) {

				//if true, get rid of punctuation
				word = word.substring(0, wordLength - 1);
			}

		} 

		//error statement for cases when the string is only a punctuation mark
		catch (StringIndexOutOfBoundsException e) {
			System.out.println("Index out of bounds");
			System.exit(0);
		}



		//capitalize the remainder of the word
		word = word.toUpperCase();
		return word;
	}
	/**
	 * This helper method takes a character input and determines whether or not it is a punctuation mark.
	 * There is an array of possible punctuation marks, and the method uses a for loop to check to see if
	 * the input character is any of the characters in the array. 
	 * 
	 * @param c - character of interest
	 * @return - boolean:	true if the character of interest is a punctuation mark,
	 * 						false if the character of interest is not a punctuation mark 
	 */
	public static boolean checkPunct (char c) {
		//TA helped me come up with this method:	

		//creating array of the punctuation characters that 
		//may be found:
		char[] badChar = new char[9];
		badChar[0] = '.';
		badChar[1] = '?';
		badChar[2] = ',';
		badChar[3] = '!';
		badChar[4] = '"';
		badChar[5] = '\'';
		badChar[6] = ':';
		badChar[7] = '_';
		badChar[8] = '-';
		System.out.println(badChar[9]);
		//initializing the boolean to return as false (no punctuation)
		boolean returnVal = false;

		//loop through the array of punctuation:		
		for (int i = 0; i < badChar.length; i++) {


			//check to see if the input character is a punctuation mark
			if (c == badChar[i]) 

				//change the value of the boolean if the input character IS any 
				//punctuation mark
				returnVal =  true;
		}

		return returnVal;
	}
}


