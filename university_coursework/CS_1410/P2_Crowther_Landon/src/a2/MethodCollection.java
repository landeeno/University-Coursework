package a2;
import java.util.Scanner;

/* 
 * Instructions for students:
 * 
 * Use the main method only to make calls to the other methods to test them.  
 * The correct operation of your methods should not depend in any way on the
 * code in main.
 * 
 * Do not do any printing within the method bodies, except the main method.
 * 
 * Leave your testing code in main -- you will be graded on this.
 *  
 * You must include Javadoc comments for all methods below. See the 
 * assignment specifications for more details. 
 */

/**
 * The methods in this script do a variety of things. Each method has a description that shows
 * how the method works and what it does. 
 * 
 * @author Landon Crowther
 */
public class MethodCollection {

	public static void main(String[] args) {
		System.out.println(countTokens(" ")); // should be 0
		System.out.println(countTokens("How many tokens?")); // should be 3
		System.out.println(countTokensThatAreNotInt("I have 2 1 4dogs")); //3
		System.out.println(countTokensThatAreNotInt(" 00 010 0 0220")); // 0 
		System.out.println(describeSign(-20)); //negative
		System.out.println(describeSign(13)); //non-negative
		System.out.println(describeSign(0)); // non-negative
		System.out.println(isEvenlyDivisibleBySeven(21)); // true
		System.out.println(isEvenlyDivisibleBySeven(13)); // false
		System.out.println(makeSquare(6)); // 6X6 square
		System.out.println(makeSquare(1)); // empty line
		System.out.println(capitalizeLastCharacter("hello")); // should print "hellO"
		System.out.println(capitalizeLastCharacter("ASDF")); // ASDF
		System.out.println(capitalizeLastCharactersInSentence("this is my sentence"));
	}

	/**
	 * Returns the number of tokens (as identified by the s.next() method on
	 * Scanners s) that appears in the parameter sentence. For example,
	 * countTokens("this is a test") should return 4, and countTokens("") should
	 * return 0.
	 * 
	 * IMPLEMENTATION NOTE: This will require a while loop. Use a Scanner to
	 * split the string into individual words. You may not use more advanced
	 * Java functions that do the token counting for you.
	 */
	public static int countTokens(String sentence) {
		Scanner numTokens = new Scanner(sentence);
		int tokenAmount = 0;		
		while (numTokens.hasNext()) {
			tokenAmount++;
			numTokens.next();
		}		
		numTokens.close();					
		// Change or modify the return. This is here for now to prevent compiler
		// errors.
		return tokenAmount;

	}

	/**
	 * Returns the number of tokens (as identified by the s.next() method on
	 * Scanners s) in the parameter sentence that are not an int, as identified
	 * by s.hasNextInt(). For example, countTokensThatAreNotInt("this is 12 a
	 * test") should return 4, and countTokensThatAreNotInt("12") should return
	 * 0.
	 * 
	 * IMPLEMENTATION NOTE: This will require a while loop. Use a Scanner to
	 * split the string into individual words. Use an if statement to figure out
	 * whether to count the token or not.
	 */
	public static int countTokensThatAreNotInt(String sentence) {
		int nonInt = 0;
		Scanner countAllTokens = new Scanner(sentence);
		while (countAllTokens.hasNext()) {
			if (!countAllTokens.hasNextInt()) {
				nonInt++;				 	
			}
			countAllTokens.next();
		}
		// Change or modify the return. This is here for now to prevent compiler
		// errors.
		countAllTokens.close();
		return nonInt;
	}

	/**
	 * Given some integer value, this method will determine if the integer value is
	 * positive or negative. If the input is a negative number, the method will report "negative"
	 * If the input is 0, or a positive number, the method will report "non-negative"
	 * 
	 * @param integer value that is being tested.
	 * @return String that declares if integer is negative or not. 
	 */
	public static String describeSign(int value) {
		if (value >= 0) {
			return "non-negative";
		}
		else {
			return "negative";	
		}
	}

	/**
	 * This method determines if the number that is inputted can be easily divisible by the
	 * integer 7, and will return a "true" or "false" statement depending on the result.
	 * For example, if the input value is 20, the method will return "false." If the input value
	 * is 14, the method will return "true".
	 * 
	 * @param integer value that is being divided by seven
	 * @return true or false, depending on result. 
	 */
	public static boolean isEvenlyDivisibleBySeven(int value) {
		if (value%7 == 0) {
			return true;
		}
		else {
			return false;
		}

	}

	/**
	 * Returns a String of length width that begins and ends with the character
	 * edge and contains width-2 copies of the character inner in between. For
	 * example, if edge is '+', inner is '-', and width is 8, the method should
	 * return "+------+".
	 * 
	 * The method does not print anything. The parameter width must be greater
	 * than or equal to 2.
	 * 
	 * IMPLEMENTATION NOTE: This method is already completely implemented. There
	 * is no need for you to change anything.
	 */
	public static String makeLine(char edge, char inner, int width) {
		String line = "";
		int i = 0;
		while (i < width - 2) {
			line = line + inner;
			i = i + 1;
		}
		return edge + line + edge;
	}

	/**
	 * This method will print out a string that creates a box given by the width input.
	 * The box will look like this: 
	 * 
	 * +-----+
	 * |     |
	 * |     |
	 * |     |
	 * |     |
	 * |     |
	 * +-----+
	 * 
	 * 
	 * The size of the box will vary, depending on the input. 
	 * 
	 * The input width must be greater than 2, however. Any width input less than two will 
	 * cause the main method to print a blank line. 
	 * 
	 *  
	 *  @param width (and length) of the box.
	 *  @return String that is square. 
	 *
	 */
	public static String makeSquare(int width) {

		if (width < 2) {
			return "";
		}

		String square = makeLine('+', '-', width) + "\n";

		for (int i=0; i < width-2; i++) {
			square = square + makeLine('|', ' ', width) + "\n";
		}
		square = square + (makeLine('+', '-', width));

		return square;
	}

	/**
	 * This method will take a word (as a String) and capitalize the last letter of the word.
	 * Note, it will not work if the input string has more than one word (that is the next method).
	 * 
	 * For example,
	 * capitalizeLastCharacter("Fabulous") is "FabulouS" and
	 * capitalizeLastCharacter("AWESOME") is "AWESOME".
	 * 
	 * @param string that the last character will be capitalized
	 * @return the modified word. 
	 */
	public static String capitalizeLastCharacter(String word) {
		int finalPosition = word.length() -1;
		// Why doesn't word.length() start at 0....?
		String everythingButLastLetter = word.substring(0,finalPosition);
		char lastLetter = word.charAt(finalPosition);
		String result = everythingButLastLetter + Character.toUpperCase(lastLetter);
		// Change or remove this statement as needed
		return result;

	}

	/**
	 * This method will take a sentence input (as a string) and capitalize the last
	 * letter of each word. For example, capitalizeLastCharactersInSentence("How are you")
	 * would return "HoW arE yoU" 
	 * 
	 * This method makes use of the method capitalizeLastCharacter(), which is right above this.
	 * 
	 * @param is the sentence string that will be capitalized
	 * @return is the final, modified sentence. 
	 */
	public static String capitalizeLastCharactersInSentence(String sentence) {
		Scanner numTokens = new Scanner(sentence);
		String returnString = "";
		while (numTokens.hasNext()) {
			String word = numTokens.next();
			word = (capitalizeLastCharacter(word));
			returnString = returnString + word + " ";

		}
		numTokens.close();		
		return returnString;
	}

}
