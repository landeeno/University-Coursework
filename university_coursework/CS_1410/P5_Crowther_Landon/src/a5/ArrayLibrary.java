package a5;

/**
 * Landon Crowther
 * u0926601
 * HW A5 (Actually A6)
 * @author Landon Crowther
 *
 */
public class ArrayLibrary {


	public static void main(String[] args) {		
		for ( int i = 10; i <= 1000000; i = i*10) {
			System.out.println("Average count for an array sized " + i + " using binary search is:     " + performanceTestBinary(i,500));
			System.out.println("Average count for an array sized " + i + " using sequential search is: " + performanceTestSequential(i,500));
			System.out.println();
		}
		
	}

	/**
	 * This method sets every element of an array to 0.
	 * For example:
	 * 		int[] testArray = new int[n];
	 * 		clear(testArray) would return [0,0,0...n] (array of n zeros).
	 * @param arr - array that should be cleared to zeros
	 */
	public static void clear (int[] arr) {

		for(int i = 0; i < arr.length; i++)
			arr[i] = 0;
	}

	/**
	 * This method fills an existing array in descending order, ending with integer N. 
	 * For example:
	 * 		reverseFill(arr, 6)
	 * 		should return an array that looks like: [9, 8, 7, 6]
	 * 		assuming that the length of arr is 4. 
	 * @param arr - input array 
	 * @param N - integer number that arr will end on 
	 */
	public static void reverseFill(int[] arr, int N) {
		for(int j = arr.length - 1; j >= 0; j--) {
			arr[j] = N;
			N++;
		}
	}

	/**
	 * This method takes an integer array and converts it to a string. 
	 * For example:
	 * 		arrayToString({-1, 0, 15});
	 * 		would result in the string "{-1, 0, 15}"
	 * 
	 * @param arr - integer array
	 * @return string listing each of the input integers
	 */
	public static String arrayToString(int[] arr) {

		//initializing the return string to start with an open bracket
		String convertedArray = "{";

		//for loop that cycles through each element of arr
		for (int i = 0; i < arr.length; i++) {
			//adding the numerical value of the array as a string element
			convertedArray = convertedArray + arr[i];

			//if statement that adds the phrase ", " to each line in the string
			//as long as the for loop isn't on the last element
			if (i < arr.length - 1) 
				convertedArray = convertedArray + ", ";
		}
		//adding ending bracket
		convertedArray = convertedArray + "}";
		return convertedArray;

	}

	/**
	 * This method takes an array of strings and checks to see if there are any duplicate strings.
	 * For example:
	 * 		containsDuplicate({"the", "the"}); would return true, but
	 * 		containsDuplicate({"The", "the"}); would return false. 
	 *
	 * @param stringArray - array of different strings
	 * @return	true or false, depending on whether or not there are duplicates
	 */
	public static boolean containsDuplicate(String[] stringArray) {


		for (int j = 0; j < stringArray.length; j++) {

			//initializing to check first word in string
			for (int i = j+1; i < stringArray.length; i++) {

				//if statement that checks for duplicates
				if ( stringArray[i].equals(stringArray[j]) ) {

					//return case for duplicates
					return true;
				}
			}
		}
		//return case if no duplicates are found
		return false;


	}

	/**
	 * This method averages all of the values in a string. 
	 * 
	 * Note, the input array must have at least one value.
	 * @param arr - array of integer values
	 * @return average - average value of array
	 */
	public static double average(int[] arr) {
		//initializing number of variables and the total sum
		int n = 0;
		int sum = 0; 

		for (int i = 0; i < arr.length; i++) {
			sum = sum + arr[i];
			n++;
		}

		double average = sum/(double)(n);
		return average;

	}

	/**
	 * This method counts the frequency of integers 0-9 in an array, and then creates a new array of length 10
	 * showing how often each of the input array integers occured.
	 * 
	 * For example:
	 *		frequencyCount({0, 0, 1, 1, 1, 2, 9}); would produce an array that lookes like:
	 *		{2, 3, 1, 0, 0, 0, 0, 0, 1} 
	 * @param arr - input array of integers ranging from 0-9 (inclusive)
	 * @return	array of length 10 that displays the frequencies of the integers 0-9
	 */
	public static int[] frequencyCount(int[] arr) {
		int[] newArray = new int[10];

		//outer for loop cycles through each element of INPUT array
		for (int j = 0; j < arr.length; j++) {

			for (int i = 0; i < arr.length; i++)
				if (arr[i] == j) {
					newArray[j]++;
				}
		}

		return newArray;
	}

	/**
	 * This method makes an array starting at 0, and increasing in increments of 1 until the array is the
	 * length of the input parameter, size.
	 * 
	 * For example:
	 * 		makeFilledArray(4) would produce {0, 1, 2, 3}
	 * 
	 * @param size - length of the array
	 * @return array of length size starting at 0 in increasing order
	 */
	public static int[] makeFilledArray(int size) {

		//initializing array to all zeros
		int[] arr = new int[size];
		//filling each element of the array with the successive number
		for (int i = 1; i < arr.length; i++) {
			arr[i] = i;
		}

		return arr;
	}

	/**
	 * This method searches an array for a specific value, and reports how many iterations it took to find that value
	 * @param vals - integer arryay of values to search through
	 * @param key - integer that is being searched for 
	 * @return - number of iterations required to find the integer key
	 * 	will return -1 if integer key is not in the parameter array
	 */
	public static int binarySearchCount(int[] vals, int key) {

		int count = 0; 

		int low = 0;
		int high = vals.length - 1;

		

		while (low <= high) {
			
			//int mid = (low + high)/2;
			int arrayLength = high + 1 - low;
			int mid = arrayLength/2 + low;

			//case for when mid reaches key: 
			if (mid == key) {
				count++;
				return count;
			}

			//case for when key is in upper half:
			if (mid < key) {
				low = mid + 1;

			}

			//case for when key is in lower half:
			else if (mid > key) {
				high = mid - 1;

			}	
			count++;
		}

		//returns -1 if key is not part of  array
		return-1;	

	}

	/**
	 * This method is another way of searching for a specific value in an array. However, this method works by searching
	 * each element of the array, starting at the first element and ending with the last.
	 * 
	 * @param vals - array to search through
	 * @param key - number that is being searched for
	 * @return number of iterations it took to find the key or "-1" if the number is not part of the array. 
	 */
	public static int sequentialSearchCount(int[] vals, int key) {

		int count = 0;

		for (int i = 0; i < vals.length; i++) {
			if (vals[i] == key) {
				count++;
				return count;
			}
			else count++;
		}
		//returns -1 if key is not part of array
		return -1;
	}

	/**
	 * This method tests the performance of the binarySearchCount method. It works by creating an array of the 
	 * parameter (size) and performing the binarySearchCount method (parameter repititions) times. It then
	 * utilizes the average method, and computes the average of the search counts. 
	 * 
	 * During each search, a random "key" variable is generated, and the amount of times the search method had 
	 * to search for that key is stored in a new array. This new array is the one that the average method uses
	 * to generate the return value. 
	 * 
	 * @param size - non-negative array size
	 * @param repititions - non negative repition count
	 * @return average search count for specified array size
	 */
	public static double performanceTestBinary(int size, int repititions) {
		int[] vals = makeFilledArray(size);
		int[] countValues = new int[repititions];
		for (int i = 0; i < repititions; i++) {
			int key = (int)(Math.random() * vals.length);
			countValues[i] = binarySearchCount(vals, key);			
		}

		double average = average(countValues);
		return average;	

	}

	
	/**
	 * This method tests the performance of the sequentialSearchCount method. It works by creating an array of the 
	 * parameter (size) and performing the sequentialSearchCount method (parameter repititions) times. It then
	 * utilizes the average method, and computes the average of the search counts. 
	 * 
	 * During each search, a random "key" variable is generated, and the amount of times the search method had 
	 * to search for that key is stored in a new array. This new array is the one that the average method uses
	 * to generate the return value.
	 * 
	 * 
	 * @param size - non-negative array size
	 * @param repititions - non negative repition count
	 * @return average search count for specified array size
	 */
	public static double performanceTestSequential(int size, int repititions) {
		int[] vals = makeFilledArray(size);
		int[] countValues = new int[repititions];
		for (int i = 0; i < repititions; i++) {
			int key = (int)(Math.random() * vals.length);
			countValues[i] = sequentialSearchCount(vals, key);			
		}

		double average = average(countValues);
		return average;	

	}

}




