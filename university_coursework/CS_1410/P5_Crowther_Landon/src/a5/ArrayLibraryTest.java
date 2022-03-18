package a5;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Landon Crowther
 * u0926601
 * HW A5 (actually A6)
 * @author Landon Crowther
 *
 */

public class ArrayLibraryTest {


	@Test
	public void testClear1() {
		int[] array = {1, 2, 3, 4, 5};
		int[] expectedResult = new int[5];
		ArrayLibrary.clear(array);
		assertArrayEquals(array, expectedResult);	
	}

	@Test
	public void testClear2() {
		int[] array = {-1232151, 0, 3112, -512, -21, 9, -5}; //tests big numbers, 0, and negative
		int[] expectedResult = new int[array.length];
		ArrayLibrary.clear(array);
		assertArrayEquals(array, expectedResult);
	}

	@Test
	public void testClear3() {
		int[] array = {}; //tests empty arrays
		int[] expectedResult = {};
		ArrayLibrary.clear(array);
		assertArrayEquals(array, expectedResult);
	}

	@Test
	public void testReverseFill1() {	//reverseFill test
		int[] arr = new int[5];
		ArrayLibrary.reverseFill(arr, 14);
		int[] finalArray = {18, 17, 16, 15, 14};
		assertArrayEquals(finalArray, arr);	

	}

	@Test
	public void testReverseFill2() {	//testing array length 1 case

		int[] zeroArray = {0};
		ArrayLibrary.reverseFill(zeroArray, 0);
		int[] expectedResult = {0};
		assertArrayEquals(expectedResult, zeroArray);
	}
	
	@Test
	public void testReverseFill3() {	//testing empty array
		int[] emptyArr = {};
		ArrayLibrary.reverseFill(emptyArr, 3);
		int[] expectedResult = {};
		assertArrayEquals(expectedResult, emptyArr);
	}


	@Test
	public void testArrayToString1() {	//testing arrayToString method
		int[] arr = {1, 2, -3, 4, 5, 0};		//note, array contains negative number and "0"
		String expectedResult = "{1, 2, -3, 4, 5, 0}";
		String result = ArrayLibrary.arrayToString(arr);
		assertEquals(expectedResult, result);

	}
	@Test
	public void testArrayToString2() {	//testing for empty array
		int[] emptyArray = {};
		String expectedResult = "{}";
		String result = ArrayLibrary.arrayToString(emptyArray);
		assertEquals(expectedResult, result);
	}
	@Test
	public void testArrayToString3() {	//testing for array of length 1
		int[] arrayLength = {0};
		String expectedResult = "{0}";
		String result = ArrayLibrary.arrayToString(arrayLength);
		assertEquals(expectedResult, result);	
	}


	@Test
	public void testContainsDuplicate1() {	//testing contains duplicate method
		String[] stringArray1 = {"no", "duplicates"};	//no duplicates
		assertFalse(ArrayLibrary.containsDuplicate(stringArray1));
	}
	@Test
	public void testContainsDuplicate2() {	//testing string array with duplicates
		String[] stringArray2 = {"duplicates", "duplicates"};
		assertTrue(ArrayLibrary.containsDuplicate(stringArray2));
	}
	@Test
	public void testContainsDuplicate3() {	//testing empty array
		String[] emptyStringArray = {""};
		assertFalse(ArrayLibrary.containsDuplicate(emptyStringArray));
	}

	//note - average method states that array must have at least one value
	@Test
	public void testAverage1() {	//testing average -- note, there are negative and large numbers
		int[] array1 = {-12, -6, 0, 3, 3, 15, 2000};
		assertEquals(286.14285, ArrayLibrary.average(array1), 0.01);
	}
	@Test
	public void testAverag2() {		//testing for array of only 0
		int[] zeroArray = new int[3];
		assertEquals(0.00, ArrayLibrary.average(zeroArray), 0.01);
	}
	@Test
	public void testAverage3() {	//testing for single digit array
		int[] singleValue = {5};
		assertEquals(5.0, ArrayLibrary.average(singleValue), 0.01);
	}

	@Test
	public void testAverage4() {	//assignment example 
		int[] primes = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47 };
		assertEquals(21.8667, ArrayLibrary.average(primes), 0.01);
	}

	@Test
	public void testFrequencyCoun1t() {	//assignment example

		int[] exampleArray = {4, 2, 3, 5, 4, 4, 3, 2, 9, 1, 3, 4, 5, 2, 1, 5 };
		int[] expectedResult = {0,2,3,3,4,3,0,0,0,1};
		int[] result = ArrayLibrary.frequencyCount(exampleArray);
		assertArrayEquals(expectedResult, result);
	}

	@Test
	public void testFrequencyCount2() {	//testing for empty array
		int[] emptyArray = {};
		int[] expectedResult = new int[10];
		int[] result = ArrayLibrary.frequencyCount(emptyArray);
		assertArrayEquals(expectedResult, result);

	}

	@Test
	public void testFrequencyCount3() {	//testing for array containing only 0
		int[] arrayOfZeros = new int[10];
		int[] expectedResult = new int[10];
		// because input array is array of only 0s, frequency @ 0 should be 10
		expectedResult[0] = 10;
		int[] result = ArrayLibrary.frequencyCount(arrayOfZeros);
		assertArrayEquals(expectedResult, result);

	}

	@Test		
	public void testMakeFilledArray1() {	//testing makeFilledArray
		int[] expectedResult = {0,1,2};
		assertArrayEquals(expectedResult, ArrayLibrary.makeFilledArray(3));
	}

	@Test
	public void testMakeFilledArray2() {	//testing for empty array
		int[] emptyArray = {};
		assertArrayEquals(emptyArray, ArrayLibrary.makeFilledArray(0));
	}

	@Test
	public void testMakeFilledArray3() {	//testing for array length 1
		int[] expectedResult = {0};
		assertArrayEquals(expectedResult, ArrayLibrary.makeFilledArray(1));

	}

	@Test
	public void testMakeFilledArray4() {	//testing for lengths of array		
		//these tests check to make sure the length of the arrays match up with the method parameters
		assertTrue(ArrayLibrary.makeFilledArray(3).length == 3); 
		assertTrue(ArrayLibrary.makeFilledArray(0).length == 0);
		assertFalse(ArrayLibrary.makeFilledArray(0).length == 1);
	}
	
	@Test
	public void testBinarySearch() {	//testing binary search using assignment example:
		int[] arrayLength4 = ArrayLibrary.makeFilledArray(5);
		assertTrue(ArrayLibrary.binarySearchCount(arrayLength4, 3) == 3);
		
		
	}
	@Test
	public void testSequentialSearch() {	//testing sequential serach using assignment example:
		int[] arrayLength4 = ArrayLibrary.makeFilledArray(5);
		assertTrue(ArrayLibrary.sequentialSearchCount(arrayLength4, 3) == 4);

	}
}