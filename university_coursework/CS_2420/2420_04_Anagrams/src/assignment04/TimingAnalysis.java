package assignment04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class TimingAnalysis
{
	/*
	 * class variable that's changed throughout main method. Determines how many
	 * loops are needed to compute average
	 */
	private static int loopCount;

	/*
	 * String array that is initialized in the main method. Equivalent to the
	 * words_english file in a String[] array
	 */
	private static String[] wordList;

	/*
	 * Initialized in main method. Equivalent to size of words_english file
	 */
	private static int wordListSize;

	/*
	 * array that contains different sizes values. Used for testing methods that
	 * are dependant on size of array (ie 10 elements, 100 elements, 1000, etc.)
	 */
	private static int[] arraySizes = { 0, 1, 10, 100, 1000, 5000, 10_000, 20_000, 30_000, 40_000, 50_000, 60_000,
			70_000, 80_000, 90_000, 100_000 };

	public static void main(String[] args)
	{
		// load wordList from file
		wordList = getWordList();
		wordListSize = wordList.length;
//
//		/*
//		 * timing areAngagrams():
//		 */
//		int[] stringSizes = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
//		loopCount = 500_000;
//		System.out.println("Testing areAnagrams() with " + loopCount + " iterations");
//		System.out.println("String Size: " + "\t" + "Average Time (s): ");
//		for (int i : stringSizes)
//		{
//			int size = i;
//			double average = timeAreAnagrams(size);
//			System.out.println(size + "\t" + average);
//		}
//
//		System.out.println();
//
//		/*
//		 * timing getLargestAnagramGroup()
//		 */
//		loopCount = 25;
//		System.out.println("Testing getLargestAnagramGroup() with " + loopCount + " iterations");
//		System.out.println("Array Size: " + "\t" + "Average Time (s): ");
//		for (int arraySize : arraySizes)
//		{
//			int size = arraySize;
//			double averageTime = timeGetLargestAnagramGroup(size);
//			System.out.println(size + "\t" + averageTime);
//
//		}
//
//		System.out.println();

//		/*
//		 * timing insertionSort() on Integer:
//		 */
//		loopCount = 25;
//		System.out.println("Testing insertionSort() on Integer with " + loopCount + " iterations");
//		System.out.println("Array Size: " + "\t" + "Average Time (s): ");
//		for (int arraySize : arraySizes)
//		{
//			int size = arraySize;
//			double averageTime = timeInsertionSortInteger(size);
//			System.out.println(size + "\t" + averageTime);
//		}
//
//		System.out.println();

		/*
		 * timing getLargestAnagramGroupJavaSort():
		 */
		loopCount = 500;
		System.out.println("Testing getLargestAnagramGroupJavaSort() " + loopCount + " iterations");
		System.out.println("Array Size: " + "\t" + "Average Time (s): ");
		for (int arraySize : arraySizes)
		{
			int size = arraySize;
			double averageTime = timeGetLargestAnagramGroupJavaSort(size);
			System.out.println(size + "\t" + averageTime);
		}

		System.out.println();
		
		/*
		 * timing insertionSort() on String:
		 */
		loopCount = 25;
		System.out.println("Testing insertionSort() on Strings with " + loopCount + " iterations");
		System.out.println("Array Size: " + "\t" + "Average Time (s): ");
		for (int arraySize : arraySizes)
		{
			int size = arraySize;
			double averageTime = timeInsertionSortString(size);
			System.out.println(size + "\t" + averageTime);
		}

		System.out.println();

		// end of main
	}

	/**
	 * Method that times the AnagramUtil.insertionSort method with strings.
	 * Works by creating a random String array of length arrayLength (parameter)
	 * (This random array contains values from the words_english file)
	 * 
	 * the AnagramUtil.insertionSort method then uses this random array and the
	 * performance is timed.
	 * 
	 * @param arrayLength
	 *            - size of array that will be tested
	 * @return
	 */
	private static double timeInsertionSortString(int arrayLength)
	{
		int loopIndex = 0;
		double elapsedTime = 0;

		while (loopIndex != loopCount)
		{
			String[] randomArray = generateRandomStringArray(arrayLength);
			double startTime = System.nanoTime();
			AnagramUtil.insertionSort(randomArray, new Comparator<String>()
			{
				@Override
				public int compare(String leftHandSide, String rightHandSide)
				{
					return leftHandSide.compareTo(rightHandSide);
				}
			});

			elapsedTime += (System.nanoTime() - startTime);
			loopIndex++;
		}

		// convert to seconds
		elapsedTime /= 1_000_000_000;
		double averageTime = elapsedTime / loopCount;
		return averageTime;

	}

	/**
	 * Method that times the AnagramUtil.insertionSort method with Integers.
	 * Works by creating a random Integer array of length arrayLength
	 * (parameter) Note - this random Integer array contains values from 0 to
	 * the length of the array. This would ensure that the values in the array
	 * are relatively similar and sorting would make sense
	 * 
	 * the AnagramUtil.insertionSort method then uses this random array and the
	 * performance is timed.
	 * 
	 * @param arrayLength
	 *            - size of array that will be tested
	 * @return
	 */
	private static double timeInsertionSortInteger(int arrayLength)
	{
		int loopIndex = 0;
		double elapsedTime = 0;

		while (loopIndex != loopCount)
		{
			Integer[] randomArray = generateRandomIntegerArray(arrayLength);
			double startTime = System.nanoTime();
			AnagramUtil.insertionSort(randomArray, new Comparator<Integer>()
			{
				@Override
				public int compare(Integer leftHandSide, Integer rightHandSide)
				{
					return leftHandSide.compareTo(rightHandSide);
				}
			});

			elapsedTime += (System.nanoTime() - startTime);
			loopIndex++;
		}

		elapsedTime /= 1_000_000_000;
		double averageTime = elapsedTime / loopCount;
		return averageTime;

	}

	/**
	 * Method that times the AnagramUtil.getLargestAnagramGroup method. Works by
	 * creating a random String array of length arrayLength (parameter) (This
	 * random array contains values from the words_english file)
	 * 
	 * the AnagramUtil.getLargestAnagramGroup method then uses this random array
	 * and the performance is timed.
	 * 
	 * @param arrayLength
	 *            - size of array that will be tested
	 * @return
	 */
	private static double timeGetLargestAnagramGroup(int arrayLength)
	{

		int loopIndex = 0;
		double elapsedTime = 0;

		while (loopIndex != loopCount)
		{
			String[] randomArray = generateRandomStringArray(arrayLength);
			double startTime = System.nanoTime();
			AnagramUtil.getLargestAnagramGroup(randomArray);
			elapsedTime += (System.nanoTime() - startTime);
			loopIndex++;
		}
		elapsedTime /= 1_000_000_000;
		double average = elapsedTime / loopCount;
		return average;
	}

	/**
	 * This method is essentially the same as the previous method, with one
	 * minor difference. Instead of using the AnagramUtil.getLargestAnagramGroup
	 * method, we used a helper method that is also in this class. This helper
	 * method is essentially the same as the
	 * AnagramUtil.getLargestAnagramGroup() method, except for instead of using
	 * insertion sort, it uses Java's Arrays.sort()
	 * 
	 * Works by creating a random String array of length arrayLength (parameter)
	 * (This random array contains values from the words_english file)
	 * 
	 * @param arrayLength
	 *            - size of array that will be tested
	 * @return
	 */
	private static double timeGetLargestAnagramGroupJavaSort(int arrayLength)
	{

		int loopIndex = 0;
		double elapsedTime = 0;

		while (loopIndex != loopCount)
		{
			String[] randomArray = generateRandomStringArray(arrayLength);
			double startTime = System.nanoTime();
			getLargestAnagramGroupJavaSort(randomArray);
			elapsedTime += (System.nanoTime() - startTime);
			loopIndex++;
		}
		elapsedTime /= 1_000_000_000;
		double average = elapsedTime / loopCount;
		return average;
	}

	/**
	 * Method that times the AnagramUtil.areAnagrams method. Works by generating
	 * two Strings of random characters of length arrayLength(parameter). (Uses
	 * helper method for this)
	 * 
	 * Then, the AnagramUtil.areAnagrams() method is called for these
	 * randomStrings and timed.
	 *
	 * @param arrayLength
	 *            - size of array that will be tested
	 * @return
	 */
	public static double timeAreAnagrams(int stringLength)
	{
		int loopIndex = 0;
		double elapsedTime = 0;
		while (loopIndex != loopCount)
		{
			String randomStringOne = generateRandomString(stringLength);
			String stringTwo = generateRandomString(stringLength);

			double startTime = System.nanoTime();

			AnagramUtil.areAnagrams(randomStringOne, stringTwo);
			elapsedTime += (System.nanoTime() - startTime);

			loopIndex++;
		}

		elapsedTime /= 1_000_000_000;
		double average = elapsedTime / loopCount;
		return average;
	}

	/**
	 * helper method that returns a string containing each word in words_english
	 * 
	 * @return -- words_english in string format
	 */
	private static String[] getWordList()
	{
		try
		{
			BufferedReader wordFile = new BufferedReader(new FileReader("Resources/words_english"));
			Scanner myScanner = new Scanner(wordFile);
			ArrayList<String> wordList = new ArrayList<>();
			while (myScanner.hasNextLine())
			{
				wordList.add(myScanner.next());
			}

			String[] wordFileArray = new String[wordList.size()];
			for (int index = 0; index < wordList.size(); index++)
			{
				wordFileArray[index] = wordList.get(index);
			}

			myScanner.close();
			return wordFileArray;

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * generates an Integer[] array of random Integers. These integers range
	 * from 0 to the maxValue paramter.
	 * 
	 * Because these values are relatively close to one another, the result of
	 * this array is an array that would need sorting (purpose of the timing
	 * class)
	 * 
	 * @param maxValue
	 * @return
	 */
	private static Integer[] generateRandomIntegerArray(int maxValue)
	{

		Integer[] returnArray = new Integer[maxValue];
		for (int i = 0; i < maxValue; i++)
		{
			Integer randomNum = (int) (Math.random() * maxValue);
			returnArray[i] = randomNum;
		}
		return returnArray;
	}

	/**
	 * generates a String[] array of length arrayLength (parameter) each String
	 * in this array comes from the words_english file
	 * 
	 * @param arrayLength
	 *            - length of array we're trying to create
	 * @return - random String array of length arrayLength
	 */
	private static String[] generateRandomStringArray(int arrayLength)
	{

		String[] returnString = new String[arrayLength];
		for (int i = 0; i < arrayLength; i++)
		{
			int randomIndex = (int) (Math.random() * wordListSize);
			String s = wordList[randomIndex];
			returnString[i] = s;
		}

		return returnString;
	}

	/**
	 * generates a string of random characters of length
	 * stringLength(parameters)
	 * 
	 * Uses ascii values to randomly select letters a-z in English alphabet
	 * 
	 * @param stringLength
	 *            - length of string we're creating
	 * @return - random string
	 */
	private static String generateRandomString(int stringLength)
	{
		String result = "";

		for (int i = 0; i < stringLength; i++)
		{
			int randomAsciiValue = (int) (Math.random() * 26) + 97;
			char character = (char) randomAsciiValue;
			result = result + character;
		}

		return result;
	}

	/**
	 * essentially same method as AngramUtl.getLargestAnagramGroup() , except
	 * for instead of using the insertionSort, we use Java's Arrays.sort()
	 * method
	 * 
	 * This method returns the largest group of anagrams in the input array of
	 * words, in no particular order. It returns an empty array if there are no
	 * anagrams in the input array. Calls the Arrays.sort() method
	 * 
	 * @param stringArray
	 * @return
	 */
	public static String[] getLargestAnagramGroupJavaSort(String[] stringArray)
	{

		String[] originalArrayCopy = new String[stringArray.length];
		for (int i = 0; i < stringArray.length; i++)
		{
			originalArrayCopy[i] = stringArray[i].toLowerCase();
		}

		for (int arrayIndex = 0; arrayIndex < stringArray.length; arrayIndex++)
		{
			stringArray[arrayIndex] = AnagramUtil.sort(stringArray[arrayIndex]);
		}

		ArrayList<Integer> indexStorage = new ArrayList<>();

		Arrays.sort(stringArray);

		// determine what anagram is the most common
		int mostCommonAnagramCount = 0;
		int tempCount = 0;
		String mostCommonAnagram = "";
		for (int i = 1; i < stringArray.length; i++)
		{

			if (stringArray[i].equals(stringArray[i - 1]))
			{
				tempCount++;
			} else
			{
				if (tempCount > mostCommonAnagramCount)
				{
					mostCommonAnagramCount = tempCount;
					mostCommonAnagram = stringArray[i - 1];
					tempCount = 0;
				} else
				{
					tempCount = 0;
				}
			}
		}
		// determine which strings in the orignal are anagrams of most common
		for (int index = 0; index < originalArrayCopy.length; index++)
		{

			String sorted = AnagramUtil.sort(originalArrayCopy[index]);

			if (AnagramUtil.areAnagrams(sorted, mostCommonAnagram))
			{
				indexStorage.add(index);
			}
		}

		String[] result = new String[indexStorage.size()];
		for (int index = 0; index < indexStorage.size(); index++)
		{
			result[index] = originalArrayCopy[indexStorage.get(index)];
		}

		return result;
	}

}
