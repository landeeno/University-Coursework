package a8;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Landon Crowther
 * u0926601
 * CS 1410 - HW8
 * 
 * Test cases for DynamicArray2 class
 * @author Landon Crowther
 *
 */
public class DynamicArray2Test {

	/**
	 * tests that DynamicArray2 is an empty array
	 */
	@Test
	public void Constructor1() {
		DynamicArray2 test = new DynamicArray2();
		assertTrue(test.size() == 0);
	}

	/**
	 * shows that constructor method produces empty array
	 */
	@Test
	public void Constructor2() {
		DynamicArray2 test = new DynamicArray2();
		String expected = "[]";
		assertTrue(test.toString().equals(expected));
	}

	/**
	 * shows that the add(int i, String s) method properly works
	 */
	@Test
	public void add1() {
		DynamicArray2 test = new DynamicArray2();
		assertTrue(test.size() == 0);
		test.add(0, "sdf");
		assertTrue(test.size() == 1);
	}

	/**
	 * shows that the add(String s) method properly works
	 */
	@Test
	public void add2() {
		DynamicArray2 test = new DynamicArray2();
		assertTrue(test.size() == 0);
		test.add("asdf");
		assertTrue(test.size() == 1);
	}

	/**
	 * shows that the add(String s) method adds s to the END of the array
	 */
	@Test
	public void add3() {
		DynamicArray2 test = new DynamicArray2();
		test.add(0, "asdf");
		test.add(1, "asdf");
		test.add("c");
		String expected = "c";		
		assertTrue(expected.equals(test.get(2)));		
	}

	/**
	 * shwos that the add(int i, String s) method properly shifts 
	 * the other elements in the array
	 */
	@Test
	public void add4() {
		DynamicArray2 test = new DynamicArray2();
		test.add("the");
		test.add("dog");
		test.add("jumped");
		test.add(1, "black");
		String expected = "[the, black, dog, jumped]";
		assertTrue(expected.equals(test.toString()));				
	}

	/**
	 * shows that if the user tries to add at an invalid
	 * index, the IndexOutOfBoundsException is thrown
	 */
	@Test
	public void add5() {
		DynamicArray2 test = new DynamicArray2();
		try {
			test.add(1, "asdf");
			fail("Expected an IndexOutOfBoundsException to be thrown");
		} catch (IndexOutOfBoundsException anIndexOutOfBoundsException) {
			return;
		}
	}

	/**
	 * shows that the default length of the DynamicArray22 (8) can be modified
	 * length of array was changed
	 * 
	 * would have used data.length, but there is no getter method for length,
	 * and they aren't static
	 */
	@Test
	public void add6() {
		DynamicArray2 test = new DynamicArray2();
		for (Integer i = 0; i < 8; i ++) {
			test.add("" + i);
		}
		assertTrue(test.size() == 8);
		test.add("creating new array");
		assertTrue(test.size() == 9);
	}

	/**
	 * shows that the error is thrown if trying to get an 
	 * element in data that doesn't exist
	 */
	@Test
	public void get1() {
		DynamicArray2 test = new DynamicArray2();

		//testing edge case
		try {
			test.get(0);
			fail("Expected an IndexOutOfBoundsException to be thrown");
		} catch (IndexOutOfBoundsException anIndexOutOfBoundsException) {
			return;
		}

		//testing middle case
		try {
			
			test.get(4);
			fail("Expected an IndexOutOfBoundsException to be thrown");
		} catch (IndexOutOfBoundsException anIndexOutOfBoundsException) {
			return;
		}

		//testing edge case again
		try {
			test.get(7);
			fail("Expected an IndexOutOfBoundsException to be thrown");
		} catch (IndexOutOfBoundsException anIndexOutOfBoundsException) {
			return;
		}
		
		//testing case for index that currently doesn't exist
		try {
			test.get(10);
			fail("Expected an IndexOutOfBoundsException to be thrown");
		} catch (IndexOutOfBoundsException anIndexOutOfBoundsException) {
			return;
		}
		
		try {
			test.get(-1);
			fail("Expected an IndexOutOfBoundsException to be thrown");
		} catch (IndexOutOfBoundsException anIndexOutOfBoundsException) {
			return;
		}
		
	}
	

	/**
	 * shows that get method properly works:
	 */
	@Test
	public void get2() {
		DynamicArray2 test = new DynamicArray2();
		test.add("asdf");
		String expected = "asdf";
		assertTrue(test.get(0).equals(expected));
	}
	
	/**
	 * tests that remove throws errors for indices that don't exist,
	 * or that are filled with a null entry
	 */
	@Test
	public void remove1() {
		DynamicArray2 test = new DynamicArray2();
		
		try {
			test.remove(0);
			fail("Expected an IndexOutOfBoundsException to be thrown");
		} catch (IndexOutOfBoundsException anIndexOutOfBoundsException) {
			return;
		}
		
		try {
			test.remove(4);
			fail("Expected an IndexOutOfBoundsException to be thrown");
		} catch (IndexOutOfBoundsException anIndexOutOfBoundsException) {
			return;
		}
		
		try {
			test.remove(10);
			fail("Expected an IndexOutOfBoundsException to be thrown");
		} catch (IndexOutOfBoundsException anIndexOutOfBoundsException) {
			return;
		}
		
		try {
			test.remove(-1);
			fail("Expected an IndexOutOfBoundsException to be thrown");
		} catch (IndexOutOfBoundsException anIndexOutOfBoundsException) {
			return;
		}
	}
	
	/**
	 * shows that the value of count changes with remove method, 
	 * and the string represended by the DynamicArray is modified
	 * correctly 
	 */
	@Test
	public void remove2() {
		DynamicArray2 test = new DynamicArray2();
		
		test.add("asdf");
		assertTrue(test.size() == 1);
		assertTrue(test.toString().equals("[asdf]"));
		
		test.remove(0);
		assertTrue(test.size() == 0);
		assertTrue(test.toString().equals("[]"));
	}
	
	/**
	 * shows that the remove method properly shifts the 
	 * order of the other string elements
	 */
	@Test 
	public void remove3() {
		DynamicArray2 test = new DynamicArray2();
		
		test.add("the");
		test.add("black");
		test.add("dog");
		test.add("jumped");
		
		test.remove(1);
		String expected = "[the, dog, jumped]";
		assertTrue(test.toString().equals(expected));
	}
	
	/**
	 * shows proper functionality of set method
	 */
	@Test
	public void set1() {
		DynamicArray2 test = new DynamicArray2();
		
		test.add("asdf");
		test.set(0, "new string");
		assertTrue("new string".equals(test.get(0)));
	}
	
	/**
	 * shows that errors are thrown for edge and non-existent cases
	 */
	@Test
	public void set2() {
		DynamicArray2 test = new DynamicArray2();
		
		try {
			test.set(0, "asdf");
			fail("Expected an IndexOutOfBoundsException to be thrown");
		} catch (IndexOutOfBoundsException anIndexOutOfBoundsException) {
			return;
		}
		
		try {
			test.set(4, "asdf");
			fail("Expected an IndexOutOfBoundsException to be thrown");
		} catch (IndexOutOfBoundsException anIndexOutOfBoundsException) {
			return;
		}
		
		try {
			test.set(10, "asdf");
			fail("Expected an IndexOutOfBoundsException to be thrown");
		} catch (IndexOutOfBoundsException anIndexOutOfBoundsException) {
			return;
		}
		
		try {
			test.set(-1, "asdf");
			fail("Expected an IndexOutOfBoundsException to be thrown");
		} catch (IndexOutOfBoundsException anIndexOutOfBoundsException) {
			return;
		}
	}
	
	/**
	 * shows that the set method doesn't alter order of strings
	 */
	@Test
	public void set3() {
		DynamicArray2 test = new DynamicArray2();
		
		test.add("1");
		test.add("2");
		test.add("3");
		
		test.set(1, "asdf");
		String expected = "[1, asdf, 3]";
		assertTrue(test.toString().equals(expected));
	}
	
	/**
	 * NOTE: size() method was used to test many of the
	 * other methods, so it doesn't have a ton of test, as it
	 * has already been proven to work. 
	 */
	@Test
	public void size1() {
		DynamicArray2 test = new DynamicArray2();
		
		assertTrue(test.size() == 0);
		test.add("asdf");
		assertTrue(test.size() == 1);
		
	}
	
	/**
	 * NOTE: toString was used to test many other methods, so only
	 * one test here. 
	 */
	@Test
	public void toString1() {
		DynamicArray2 test = new DynamicArray2();
		
		String expected = "[]";
		assertTrue(test.toString().equals(expected));
		
		test.add("asdf");
		expected = "[asdf]";
		assertTrue(test.toString().equals(expected));
		
		test.add("1234");
		
		
	}
	
}
