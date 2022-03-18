/**
 * Landon Crowther
 * u0926601
 * Assignment A3 - LoopPatterns
 * CS 1410 - 001
 * 
 * @author Landon Crowther
 * 
 * This java document contains a variety of loop techniques. Each method has a javadoc attached
 * that describes how the method works, the required inputs, and what the return value is.
 * 
 */

package a3;
import java.util.Scanner;

public class LoopPatterns {

	public static void main(String[] args) {
		System.out.println(findSmallestPositiveNumber("12 1 9 -4 13 0"));	//returns 1
		System.out.println(findSmallestPositiveNumber("-1 0 -3"));			//returns -1 (first number)

		System.out.println("");

		System.out.println(isPalindrome("civic"));							//returns true
		System.out.println(isPalindrome("abc"));							//returns false
		System.out.println(isPalindrome(""));								//returns true

		System.out.println("");

		System.out.println(moreEvenThanOdd("2 2 2 3 3"));					//returns true
		System.out.println(moreEvenThanOdd("2 2 3 3"));						//returns false
		System.out.println(moreEvenThanOdd("3 3 3 2"));						//returns false
		System.out.println(moreEvenThanOdd(""));							//returns false

		System.out.println("");

		System.out.println(camelCase("What is this sentence"));				//returns whatIsThisSentence
		System.out.println(camelCase(""));									//returns empty string
		System.out.println(camelCase("Capital letter"));					//returns capitalLetter

		System.out.println("");

		System.out.println(lowestAlphabetically("cat dog animal zebra"));	//returns animal
		System.out.println(lowestAlphabetically("a aa aaa"));				//returns a

		System.out.println("");

		System.out.println(timesTable(3,3));								//returns 3x3 table with 3 character spacing
		System.out.println(timesTable(4,6));								//returns 4x4 table with 6 character spacing

		System.out.println("");

		System.out.println(paddedNumberString(12,6));						//returns ----12 ("-" emitted)
	}

	/**
	 * This method takes a string of integer numbers, and evaluates all of them to determine which is the smallest
	 * positive number. 
	 * 
	 * For example:
	 * 
	 * 		findSmallestPositiveNumber("0 12 3 -4 15); would return 3,
	 * 
	 * There must be at least one positive number in the string. (O doesn't count as a positive number).
	 * 
	 * @param	string of numbers - must have at least one positive number
	 * @return	returns the smallest number that is positive. If there are no positive numbers, first number
	 * 			of string will be returned. 
	 */
	public static int findSmallestPositiveNumber(String numbers) {
		Scanner s = new Scanner(numbers);
		int currentValue = s.nextInt();
		while (s.hasNextInt()) {
			int number = s.nextInt();
			if (number > 0 && number < currentValue) {
				currentValue = number;
			}	
		}
		s.close();
		return currentValue;
	}

	/**
	 * This method tests to see if the input string is a palindrome (same spelling forwards and backwards). For example, the word
	 * "racecar" is a palindrome, because if the spelling is the same forwards and backwards. However, 
	 * the word "Racecar" is not a palindrome -- if spelled backwards, it would be racecaR. 
	 * 
	 * 		isPalindrome("racecar"); would return true, 
	 * 		isPalindrome("Racecar"); would return false, &
	 * 		isPalindrome(""); would return true (technically, and empty string is a palindrome). 		
	 * 
	 * 
	 * @param	string that is being tested for a palindrome (same spelling backwards and forwards)
	 * @return	true if parameter is a palindrome, false if parameter string is not a palindrome.
	 */
	public static boolean isPalindrome(String word) {
		String reverseWord = "";
		for (int i = 0; i < word.length(); i++) {
			reverseWord = word.charAt(i) + reverseWord;
		}
		if (reverseWord.equals(word)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * This method analyzes a string of integer numbers, and determines if there are more even numbers
	 * than odd. For example:
	 * 
	 *		moreEvenThanOdd("2 2 2 3"); would return true,
	 *		moreEvenThanOdd("2 3 3 3"); would return false,  
	 *		moreEvenThanOdd("2 2 3 3"); would return false, (# even numbers = # odd numbers) &
	 *		moreEvenThanOdd(""); would return false (no numbers to compare to).		
	 * 
	 * @param	string of integer numbers
	 * @return	true or false value -- true if there are more even numbers than odd, false if
	 * 			there are more odd values than even
	 */
	public static boolean moreEvenThanOdd(String numbers) {

		Scanner s = new Scanner(numbers);

		//initializing even and odd quantities
		int even = 0;
		int odd = 0;

		//while loop that checks to see if each number is even or odd
		while (s.hasNextInt()) {
			if (s.nextInt()%2 == 0) {
				even++;
			}
			else {
				odd++;
			}

		}
		s.close();

		//checks to see if the number of evens is greater than odd
		if (even > odd) {
			return true;
		}

		//returns false in any case that even is not greater than odd
		else {
			return false;
		}
	}

	/**
	 * This method takes a input of lower-case letters as a string, and converts them to camel case style.
	 * 
	 * For example:
	 * 		
	 * 		camelCase("convert this to camel case"); would return the string
	 * 			convertThisToCamelCase
	 * 		camelCase(""); would return the an empty string
	 * 		camelCase("This string starts with a capital word"); would return the string
	 * 			thisStringStartsWithACapitalWord
	 * 			
	 * @param	String that should be converted to camel case
	 * @return	String converted to camel case style.
	 */
	public static String camelCase(String sentence) {

		Scanner s = new Scanner(sentence);

		//Initializing method. Creating an empty string that method will modify.
		String newPhrase = "";

		//If statement that determines if the input string is an empty string or not.
		if (s.hasNext()) {

			//Camel case conversion for input strings that have words
			while (s.hasNext()) {
				String word = s.next();
				String everythingButFirstLetter = word.substring(1,word.length());
				newPhrase = newPhrase + Character.toUpperCase(word.charAt(0)) + everythingButFirstLetter;
			}
			newPhrase = Character.toLowerCase(newPhrase.charAt((0))) + newPhrase.substring(1, newPhrase.length());
			s.close();
			return newPhrase;
		} 

		//Returns empty string if input string has no tokens in it. 
		else {
			s.close();
			return newPhrase;
		}
	}

	/**
	 * This method takes a string of words, and returns the word that is lowest alphabetically. 
	 * 
	 * For example:
	 * 	
	 * 		lowestAlphabetically("dog cat bunny animal"); would return "animal"
	 * 		lowestAlphabetically("bob billy becky"); would return "becky" (becky is lowest alphabetically)
	 * 		lowestAlphabetically("a aaaa"); would return "a" ("a" is technically lower than "aaaa")
	 * 
	 * @param	sentence with only lower-case words
	 * @return	token in the input string that is the lowest alphabetically
	 * 
	 * Note:	no upper-case letters, numbers, or symbols should be used in this method or it will not work. \
	 * 			input string must have at least one word. 
	 */
	public static String lowestAlphabetically(String sentence) {

		Scanner s = new Scanner(sentence);

		//initializing loop so that the first token is the string is the token that
		//all of the future tokens will be compared to. 
		String lowestSoFar = s.next();
		while (s.hasNext()) {
			String wordOfInterest = s.next();
			int comparison = wordOfInterest.compareTo(lowestSoFar);
			if (comparison < 0 ) {
				lowestSoFar = wordOfInterest;
			}
		}
		s.close();
		return lowestSoFar;
	}

	/**
	 * This method creates a multiplication table for integers between 1-9 (number > 10 won't format correctly).
	 * 
	 * Note:	This method makes use of the paddedNumberString method. That details of that method
	 * 			are explained with it's corresponding javadoc.
	 * 
	 * This method works in three stages: 
	 * 
	 * The first stage creates the top row (called "header"). It simply lists the numbers in a row.
	 * The second stage creates the "banner" (decorative line underneath)
	 * The last stage does the actual math and creation of the banner.
	 * 
	 * For example:
	 * 		timesTable(4,4); would return:
	 * 
	 *       1   2   3   4
	 *    ----------------
	 *  1|   1   2   3   4
	 *  2|   2   4   6   8
	 *  3|   3   6   9  12
	 *  4|   4   8  12  16
	 * 		
	 * 		timesTable(3,3); would return:
	 * 
	 *    1  2  3
	 *    ---------
	 *  1|  1  2  3
	 *  2|  2  4  6
	 *  3|  3  6  9
	 * 
	 * 
	 * @param	number - the number input is the times-table integer to go up to. 
	 * 		For example:
	 * 			number = 3 would create a times table up to 3x3
	 * 			number = 9 would create a times table up to 9x9
	 * 
	 * @param 	spacing - the spacing parameter determines how far apart the spacing between values in
	 * 			the times table should be. 
	 * 				For example:
	 * 					spacing = 4 would ensure that there are 4 characters of spacing before each number in return string
	 * 					spacing = 12 would ensure that there are 12 characters of spacing before each number in return string
	 * 
	 * @return	returns a string that displays the formatted multiplication table 
	 */
	public static String timesTable(int number, int spacing) {

		//header:
		String header = "  ";
		for (int i = 1; i <= number; i++) {

			//this creates the header that displays all of the numbers in a row
			header = header + paddedNumberString(i, spacing);
		}
		header = header + "\n";

		//banner underneath header:
		String banner = "  ";

		//determining the nubmer of characters required for the banner.
		int numOfCharacters = number * spacing;
		for (int i = 0; i < numOfCharacters; i++) {

			//by default, the "-" character is used. This can be changed to any character. 
			banner = banner + "-";
		}
		banner = banner + "\n";

		//body of table: 

		//initializing with empty string that will be added to.
		String body = "";

		//creating a nested for loop - the nested for loop is required as we are changing both the rows and columns.
		for (int i = 1; i <= number; i++) {

			//this sets up the formatting for the leftmost side of the table
			body = body + Integer.toString(i) + "|";

			//this inner for-loop does the actual multiplication, and then formats it with the correct spacing
			for (int j = 1; j <=number; j++) {

				//uses paddedNumberString method to multiply the numbers for the table and format them correctly
				body = body + paddedNumberString(i*j, spacing);
			}
			//adds a new line to the string, so that there is a line break for the next row of values.
			body = body + "\n";
		}

		//combinig all three strings:
		String result = header + banner + body;

		//finalized table
		return result;
	}

	/**
	 * This method is a sub-method that is used by the timesTable method. The primary purpose of this method is to 
	 * display the value in the times table string with the correct spacing. Some numbers in the times table string
	 * are 2 digits long, while others are 1 digit long. This method accounts for that, so that the formatting is consistent.
	 * 
	 * It works by taking the "spacing" input from the timesTable method, and subtracting the lenght of the value.
	 * 
	 * For example:
	 * 		if the times table value is "12" with a spacing of "4", there would be 2 white spaces before the 12. This
	 * 		is because there should be 4 total character spacings, but the numerical value of 12 takes up two of them.
	 * 
	 * 		paddedNumberString(12,6); would return:
	 * 			    12
	 * 			notice that this is actually "----12" (without the 4 hyphens). This is because their should be 6
	 * 			total spaces, but the characters "1" and "12" (from the value 12) take up two of them. This leaves 4
	 * 			empty spaces before the 12.  			
	 * 	
	 * This method places the spaces before the actual numerical value. This is so each number is equilly spaced, and 
	 * there are no empty spaces after after the timesTable method has completed. 
	 * 
	 * @param 	number - the value (as a string) thats should be displayed in the timesTable. This value 
	 * 				will be inputted from the timesTable method.
	 * @param 	spacing - this is the numerical value for how far apart each value should be. This input comes from the
	 * 				timesTable method.
	 * @return	the string that displays the inputted value with the appropriate spacing. 
	 */
	public static String paddedNumberString(int value, int spacing) {

		//converting the input value (as an integer) to a string
		String paddingToString = Integer.toString(value);

		//determining how many spaces there should be before the actual character is displayed
		int lengthOfSpacing = spacing - paddingToString.length();

		//creating a loop that adds the required number of spaces
		for (int i = 0; i < lengthOfSpacing; i++) {
			paddingToString = " " + paddingToString;
		}
		return paddingToString;
	}

}

