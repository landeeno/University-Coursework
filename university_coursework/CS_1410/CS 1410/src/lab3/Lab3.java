package lab3;

public class Lab3 {

	/**
	 * Adds up the series of numbers from 0 to topNumber.
	 * @param topNumber
	 * @return the summed series.
	 */
	public static int sumFrom0ToNumber(int topNumber) {
		int currentNumber = 0;
		int sum = 0;
		while (currentNumber <= topNumber) {
			sum = currentNumber + sum;
			currentNumber++;
			
			// This adds one to currentNumber
		}
		// Change the return
		return sum;
	}
	/**
	 * This code displays the characters in a string.
	 * Convert it to count the number of 'a' or 'A' character in the string.
	 * @param word
	 * @return the number of 'a' or 'A' characters in word.
	 */
	public static int countA(String word) {
		int letterPosition = 0;
		int letterA = 0;
		while (letterPosition < word.length()){
			char currentLetter = word.charAt(letterPosition);
				if((currentLetter == 'a') || (currentLetter == 'A')) {
				letterA++; 
			}
			System.out.println(word.charAt(letterPosition));
			letterPosition++; // This increments letterPosition by 1	
		}
		// change the return to the count of a letters.
		return letterA;
	}
	
	/**
	 * Given a word, take the first half of the word and put it on the 
	 * end.
	 * @param word
	 * @return Returns the new word with the front moved to the back.
	 */
	public static String splitFlipWord(String word) {
		int wordLength = word.length();
		String firstHalf = word.substring(0, wordLength/2);
		String secondHalf = word.substring(wordLength/2, wordLength);
		//Add code and change the return
		return secondHalf + firstHalf;
	}
	
	public static void main(String[] args) {
		// Test the sum method
		System.out.println("The sum from 0 to 5 is " + sumFrom0ToNumber(5));
		// Test the countA method
		System.out.println("The a/A count is: " + countA("A happy anaconda"));
		// Test the splitFlip method
		String startWord = "David";
		System.out.println("Splitflip of " + startWord + " is " + splitFlipWord(startWord));
	}

}
