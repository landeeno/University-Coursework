package assignment04;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Comparator;


import org.junit.Test;

public class AnagramUtilTest {


	@Test
	public void testSort1() 
	{
		assertEquals("aekprr",AnagramUtil.sort("parker")); // a normal word 
	}

	@Test
	public void testSort2()
	{
		assertEquals("adlnno",AnagramUtil.sort("Landon")); // can handle uppercase 
	}
	
	@Test
	public void testSort3()
	{
		assertEquals("",AnagramUtil.sort("")); // can handle an empty string
	}
	
	@Test
	public void testInsertionSort1() // test a normal amount of words 
	{
		String[] arrayTest = {"Parker","Landon","cat"};
		String[] expectedOutput = {"cat","Landon","Parker"};
		AnagramUtil.insertionSort(arrayTest, new Comparator<String>()
	
		{
			@Override
			public int compare(String leftHandSide, String rightHandSide)
			{
				return leftHandSide.compareToIgnoreCase(rightHandSide);
			}
		});
		assertArrayEquals(expectedOutput, arrayTest);
	}
	
	@Test
	public void testInsertionSort2() // test an empty array 
	{
		String[] arrayTest2 = {};
		String[] expectedOutput2 = {};
		AnagramUtil.insertionSort(arrayTest2, new Comparator<String>()
		{
			@Override
			public int compare(String leftHandSide, String rightHandSide)
			{
				return leftHandSide.compareToIgnoreCase(rightHandSide);
			}
		});
		assertArrayEquals(expectedOutput2,arrayTest2);	
	}
	
	@Test
	public void testInsertionSort3() // test an Integer array  
	{
		Integer[] arrayTest3 = {1,6,0,4,5,6};
		Integer[] expectedOutput3 = {0,1,4,5,6,6};
		AnagramUtil.insertionSort(arrayTest3, new Comparator<Integer>()
		{
			@Override
			public int compare(Integer leftHandSide, Integer rightHandSide)
			{
				return leftHandSide.compareTo(rightHandSide);
			}
		});
		assertArrayEquals(expectedOutput3,arrayTest3);
	}
	
	@Test
	public void testInsertionSort4() // test Characters 
	{
		Character[] arrayTest4 = {'e','z','h','p','i'};
		Character[] expectedOutput4 = {'e','h','i','p','z'};
		AnagramUtil.insertionSort(arrayTest4, new Comparator<Character>()
		{
			@Override
			public int compare(Character leftHandSide, Character rightHandSide)
			{
				return leftHandSide.compareTo(rightHandSide);
			}
		});
		assertArrayEquals(expectedOutput4,arrayTest4);
	}
	
	
	@Test
	public void testAreAnagrams1() // actual anagrams
	{
		assertTrue(AnagramUtil.areAnagrams("adlnno","adlnno"));
	}
	
	@Test
	public void testAreAnagrams2() // Empty strings
	{
		assertTrue(AnagramUtil.areAnagrams("",""));
	}
	
	@Test
	public void testAreAnagrams3() // not anagrams
	{
		assertFalse(AnagramUtil.areAnagrams("aekprr", "adlnno"));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testGetLargestAnagramGroup1() // testing anagrams at the beginning 
	{
		String[] testArray1 = {"Landon","Parker","dog","god","ogd","dgo","Jim","aldnno"};
		String[] outputArray = AnagramUtil.getLargestAnagramGroup(testArray1);
		String[] expectedOutput = {"dog","god","ogd","dgo"};
		assertEquals(expectedOutput,outputArray);
		
	}
	
	@Test
	public void testGetLargestAnagramGroup2() // testing anagrams at the end of the list
	{
		String[] testArray2 = {"Landon","Parker","Jim","aldnno","dog","god","ogd","dgo"};
		String[] outputArray2 = AnagramUtil.getLargestAnagramGroup(testArray2);
		String[] expectedOutput2 = {"dog","god","ogd","dgo"};
		
		assertArrayEquals(expectedOutput2,outputArray2);
	}
	
	@Test
	public void testGetLargestAnagramGroup3() // testing an inputted empty array 
	{
		String[] testArray3 = {};
		String[] outputArray3 = AnagramUtil.getLargestAnagramGroup(testArray3);
		String[] expectedOutput3 = {};
		
		assertArrayEquals(expectedOutput3,outputArray3);
 	}
	
	
	@Test
	public void testGetLargestAnagramGroupFile1() // Reads in a file. 
	{
		String filename1 = "Resources/testFile1";
		String[] outputArray1 = AnagramUtil.getLargestAnagramGroup(filename1);
		String[] expectedOutput1 = {"ate", "eat", "tea","tae"};
		assertArrayEquals(expectedOutput1,outputArray1);
	}
}

