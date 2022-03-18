package assignment04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class AnagramUtil 
{
	
	
	/**
	 *  This method returns the sorted version of the input string. The
	 * sorting must be accomplished using an insertion sort.
	 * @param word - string, can be upper case or lower case, or an empty string
	 * @return the sorted version of the word in alphabetical order
	 */
	public static String sort(String word)
	{
		
		word = word.toLowerCase();
		
		Character[] characterArray = stringToCharacter(word);
		
		
		insertionSort(characterArray, new Comparator<Character>()
		{
			@Override
			public int compare(Character leftHandSide, Character rightHandSide)
			{
				return leftHandSide.compareTo(rightHandSide);
			}
		});
		
		String returnString = "";
		for(int index = 0; index < characterArray.length; index++)
		{
			returnString+=characterArray[index];
		}
		return returnString;


	}


	/**
	 * Method that sorts the arrays using Insertion Sort  
	 * @param arrayOfElements
	 * @param elementComparator
	 */
	public static <T> void insertionSort(T[] arrayOfElements, Comparator<? super T> elementComparator)
	{
		for ( int arrayIndex = 1; arrayIndex < arrayOfElements.length; arrayIndex ++)
		{

			T currentElement = arrayOfElements[arrayIndex];
			T previousElement = arrayOfElements[arrayIndex - 1];

			if ( elementComparator.compare(previousElement, currentElement) <= 0 )
			{
				continue;
			}
			else 
			{
				int arrayIndexCopy = arrayIndex;

				while (arrayIndexCopy != 0)
				{
					if ( elementComparator.compare(arrayOfElements[arrayIndexCopy-1], currentElement) > 0)
					{
						arrayOfElements[arrayIndexCopy] = arrayOfElements[arrayIndexCopy - 1];
						arrayOfElements[arrayIndexCopy - 1] = currentElement;
						arrayIndexCopy--;
					} 

					else 
					{
						break;
					}

				}

			}

		}
	}

	/**
	 *  This method returns true if the two input strings are anagrams of each other,
	 *   otherwise returns false
	 *  This method uses the .equals method
	 * @param sortedStringOne - string 
	 * @param sortedStringTwo - string to be compared
	 * @return
	 */
	public static boolean areAnagrams(String stringOne, String stringTwo)
	{
		if(stringOne.length() == stringTwo.length())
		{
			if (sort(stringOne).equals(sort(stringTwo)))
			{
				return true;
			}
		}
			return false;
			
	}
	/**
	 *This method returns the largest group of anagrams in the input
	 *array of words, in no particular order. It returns an empty array if
	 *there are no anagrams in the input array.
	 *Calls the insertion sort method and uses an anonymous comparator method
	 *
	 *@param stringArray - an array of Strings, can be any size
	 *@return an array of the largest group of anagrams, in their original spelling
	 *
	 **/
	public static String[] getLargestAnagramGroup(String[] stringArray)
	{
		
		String[] originalArrayCopy = new String[stringArray.length];
		
		for (int index = 0; index < stringArray.length; index ++)
		{
			originalArrayCopy[index] = stringArray[index].toLowerCase();
		}
		
		for ( int arrayIndex = 0; arrayIndex < stringArray.length; arrayIndex++ )
		{	
			originalArrayCopy[arrayIndex]  =  sort(originalArrayCopy[arrayIndex]);
		}
		
		
		ArrayList<Integer> indexStorage = new ArrayList<>();

		insertionSort(originalArrayCopy, new Comparator<String>()
		{
			@Override
			public int compare(String leftHandSide, String rightHandSide)
			{
				return leftHandSide.compareToIgnoreCase(rightHandSide);
			}
		}); // Anonymous Comparator class
	
		//determine what anagram is the most common
		int mostCommonAnagramCount = 0;
		int tempCount = 0;
		String mostCommonAnagram = "";
		for (int index = 1; index < stringArray.length; index ++)
		{
			if ( originalArrayCopy[index].equals(originalArrayCopy[index-1])  )
			{
				tempCount++;
			}
			else
			{
				if (tempCount > mostCommonAnagramCount )
				{
					mostCommonAnagramCount = tempCount;
					mostCommonAnagram = originalArrayCopy[index-1];
					tempCount = 0;
				}
				else
				{
					tempCount = 0;
				}
			}
		}
		//determine which strings in the orignal are anagrams of most common
		for (int index = 0; index < stringArray.length; index ++)
		{
			if ( areAnagrams(stringArray[index], mostCommonAnagram) )
			{
				indexStorage.add(index);
			}
		}

		String[] result = new String[indexStorage.size()];
		for (int index = 0; index < indexStorage.size(); index++)
		{
			result[index] = stringArray[indexStorage.get(index)];
		}

		return result;
	}
	
	
	 /**
	  * 
	  * Behaves the same as the previous method, but reads the list of
	  * words from the input filename. It is assumed that the file contains
	  * one word per line. If the file does not exist or is empty, the method
	  * returns an empty array because there are no anagrams.
	  * Uses a file reader to read in the referenced file, and then uses a scanner to store
	  * the words from the file into an array
	  * There can be no empty spaces in the file 
	  * @param fileName a name of the input file 
	  * @return the largest group of anagrams, similar to the above method
	  */
	public static String[] getLargestAnagramGroup(String fileName)
	{
		try
		{
		BufferedReader wordFile = new BufferedReader(new FileReader (fileName));
		Scanner myScanner = new Scanner(wordFile);
		ArrayList<String> wordList = new ArrayList<>();
			while(myScanner.hasNextLine())
			{
				wordList.add(myScanner.next());
			}
		String[] wordFileArray = new String[wordList.size()];
		for(int index = 0; index <wordList.size(); index++)
		{
			wordFileArray[index] = wordList.get(index);
		}
		myScanner.close();
		
		return getLargestAnagramGroup(wordFileArray); // If the file is read correctly will return here
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		// If a file cannot be read in it will return an empty string array
		String[] emptyString = {};
		return emptyString;
	}
	/**
	 * Helper method to convert from a string to a Character array
	 * @param word
	 * @return Character array 
	 */
	private static Character[] stringToCharacter(String word)
	{
		if ( word == null ) {
		     return null;
		   }

		   int len = word.length();
		   Character[] array = new Character[len];
		   for (int index = 0; index < len ; index++) {
		      array[index] = new Character(word.charAt(index));
		   }

		   return array;
	}
}
