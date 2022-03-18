package lists_2420;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Landon Crowther & Brent Collins
 * 
 *         This test file tests all of the methods in both the linked list and
 *         array list implementations.
 * 
 *         There are a few tests which only apply to the array list
 *         implementation, and they are stated in their respective titles and
 *         Javadocs
 * 
 *         "_Empty" refers to a list (linked or array) with 0 elements "_Single"
 *         refers to a list with 1 element "_Multiple" refers to a list with 1+
 *         elements
 *
 */

public class List_2420_Test
{

	List_2420<Integer> test;

	/**
	 * allow changing between linked and array lists
	 */
	public List_2420<Integer> new_list()
	{
		return new Array_List_2420();
	}

	/**
	 * assign value of "test" that is used in each test
	 */
	@Before
	public void setupTest()
	{
		test = new_list();
	}

	/**
	 * tests add_first() method for empty, sngle, and multiple
	 */
	@Test
	public void testAddFirst()
	{

		// nothing in test
		assertEquals(0, test.size());

		// 5 should be first AND last
		test.add_first(5);
		assertTrue(test.get_first() == 5);
		assertTrue(test.get_last() == 5);

		test.add_first(10);

		// order is now 10, 5
		assertEquals(2, test.size());
		assertTrue(test.get_first() == 10);
		assertTrue(test.get_last() == 5);

		// order is now 0, 10, 5 (3 elements)
		test.add_first(0);
		assertEquals(3, test.size());
		assertTrue(test.get_first() == 0);
		assertTrue(test.get_last() == 5);
	}

	/**
	 * tests add_first() method for empty, single, and multiple
	 */
	@Test
	public void testAddLast()
	{

		test.add_last(5);

		// first and last should be same value (5)
		assertTrue((test.get_first() == 5) && (test.get_last() == 5));

		test.add_last(10);

		// order is 5, 10
		assertTrue(test.get_first() == 5);
		assertTrue(test.get_last() == 10);
		assertEquals(2, test.size());

		test.add_last(15);

		// order is 5, 10, 15
		assertTrue(test.get_first() == 5);
		assertTrue(test.get_last() == 15);
		assertEquals(3, test.size());

	}

	/**
	 * test the expand helper method for the Array_List_2420 implementation
	 */
	@Test
	public void testExpand_ArrayListOnly()
	{

		if (test instanceof Array_List_2420)
		{
			Array_List_2420 test_instanceof_Array_List_2420 = new Array_List_2420(4);

			assertEquals(4, test_instanceof_Array_List_2420.getBackingArrayLength());

			// add 4 elements to the array:
			int i = 0;
			while (i < 4)
			{
				test_instanceof_Array_List_2420.add_last(i);
				i++;
			}

			// array should be full, but the length hasn't changed yet
			assertEquals(4, test_instanceof_Array_List_2420.getBackingArrayLength());
			assertEquals(4, test_instanceof_Array_List_2420.size());

			// add one more element. Length should double, size should increment
			test_instanceof_Array_List_2420.add_first(0);
			assertEquals(8, test_instanceof_Array_List_2420.getBackingArrayLength());
			assertEquals(5, test_instanceof_Array_List_2420.size());

		} else // for LinkedList purposes only
			assertTrue(1 == 1);
	}

	/**
	 * tests add_middle() on an empty list
	 */
	@Test
	public void testAddMiddle_Empty()
	{

		int insertionIndex = 12;
		assertTrue(insertionIndex >= test.size());

		test.add_middle(insertionIndex, 1000);

		// size is one
		assertEquals(1, test.size());
		// first and last elements are 1000
		assertTrue((test.get_first() == 1000) && (test.get_last() == 1000));
	}

	/**
	 * test add_middle() on a list with only one element by adding at index that
	 * is equivalent to the size
	 */
	@Test
	public void testAddMiddle_SingleElement_1()
	{

		test.add_first(0);

		assertTrue(test.size() == 1);

		/*
		 * adding element 100 to index 1. Because 1 is the size, 100 will be
		 * added to the end
		 */
		test.add_middle(1, 100);

		// because 0 was the middle element, 100 should be added to the end
		assertEquals(2, test.size());
		assertTrue(100 == test.get_last());
		assertTrue(0 == test.get_first());

	}

	/**
	 * test add_middle() on a list with only one element by adding at index that
	 * is greater than the size
	 */
	@Test
	public void testAddMiddle_SingleElement_2()
	{

		test.add_first(0);

		// 12 > size, but 100 will still be added to the end
		test.add_middle(12, 100);

		// because 0 was the middle element, 100 should be added to the end
		assertEquals(2, test.size());
		assertTrue(100 == test.get_last());
		assertTrue(0 == test.get_first());
	}

	/**
	 * test add_middle() method. comments explain details //FIXME make sure the
	 * logic is correct here
	 */
	@Test
	public void testAddMiddle_Multiple()
	{
		test.add_last(1);
		test.add_last(2);
		test.add_first(0);

		assertEquals(3, test.size());

		/*
		 * List should be in following order: [0, 1, 2]
		 * 
		 * Note: in the Array_List implementation, the backingArray would be
		 * setup as follows:
		 * 
		 * [1, 2, null, null, ----- null, 0];
		 * 
		 * This is intentional to show that add middle works regardless of how
		 * the Array_List is structured
		 */

		/*
		 * after = 0; array should be: [0, 10, 1, 2, ----];
		 */
		test.add_middle(0, 10);

		// size has update
		assertEquals(4, test.size());

		// first and last values are the same (because we added in the middle)
		assertTrue((0 == test.get_first()) && (2 == test.get_last()));

		/*
		 * to show that the 10 actually exists, we are going to remove the first
		 * element. This will result in the following:
		 * 
		 * [10, 1, 2, null---];
		 */

		test.remove_first();

		// size = 2, 10 = first, 2 = last
		assertEquals(3, test.size());
		assertTrue(10 == test.get_first());
		assertTrue(2 == test.get_last());

	}

	/**
	 * tests that NoSuchElementException is thrown for the get_last() method on
	 * an empty list
	 */
	@Test(expected = java.util.NoSuchElementException.class)
	public void testGetLast_Empty()
	{
		// test is empty - should throw exception
		test.get_last();
	}

	/**
	 * tests get_last() with single entries
	 */
	@Test
	public void testGetLast_Single()
	{
		assertTrue(test.size() == 0);
		test.add_first(5);

		// 5 is the last element, so it is returned
		assertTrue(5 == test.get_last());
	}

	/**
	 * test get_last() when there are 2+ elements
	 */
	@Test
	public void testGetLast_Multiple()
	{
		test.add_last(1);
		test.add_last(2);
		test.add_last(3);

		assertTrue(3 == test.get_last());

		// add something to end:
		test.add_last(100);

		assertTrue(100 == test.get_last());
	}

	/**
	 * test size() method
	 * 
	 * note - size variable is used to test many of the other methods
	 */
	@Test
	public void testSize()
	{
		// test is empty
		assertEquals(0, test.size());

		// add one and check
		test.add_first(1);
		assertEquals(1, test.size());

		// add one more
		test.add_first(2);
		assertEquals(2, test.size());
	}

	/**
	 * test size() method
	 * 
	 * note - size() is inherently used to test many of the other methods
	 */
	@Test
	public void testSizeRecursive()
	{
		// test is empty
		assertEquals(0, test.compute_size_recursive());

		// add one and check
		test.add_first(1);
		assertEquals(1, test.compute_size_recursive());

		// add one more
		test.add_first(2);
		assertEquals(2, test.compute_size_recursive());
	}

	/**
	 * tests that NoSuchElementException is thrown for the get_first() method on
	 * an empty list
	 */
	@Test(expected = java.util.NoSuchElementException.class)
	public void testGetFirst_Exception()
	{
		// test is empty - should throw exception
		test.get_first();
	}

	/**
	 * shows that get_first() returns the first (and only) value for a
	 * single-element entry
	 */
	@Test
	public void testGetFirst_Single()
	{
		assertTrue(test.size() == 0);
		test.add_first(5);

		// 5 is the first element, so it is returned
		assertTrue(5 == test.get_first());
	}

	/**
	 * shows that get_first() works when there are multiple elements in the
	 * entry
	 */
	@Test
	public void testGetFirst_Multiple()
	{
		test.add_last(1);
		test.add_last(2);
		test.add_last(3);

		assertTrue(1 == test.get_first());

		// add something to beginning:
		test.add_first(100);

		assertTrue(100 == test.get_first());
	}

	/**
	 * tests that NoSuchElementException is thrown for the remove_last() method
	 * on an empty list
	 */
	@Test(expected = java.util.NoSuchElementException.class)
	public void testRemoveLast_Empty()
	{
		// test is empty - should throw exception
		test.remove_last();
	}

	/**
	 * tests that NoSuchElementException is thrown for the remove_first() method
	 * on an empty list
	 */
	@Test(expected = java.util.NoSuchElementException.class)
	public void testRemoveFirst_Empty()
	{
		// test is empty - should throw exception
		test.remove_first();
	}

	/**
	 * test remove_last() on List with one element
	 */
	@Test
	public void testRemoveLast_Single()
	{
		test.add_first(1);

		assertEquals(1, test.size());

		test.remove_last();

		assertEquals(0, test.size());
	}

	/**
	 * test remove_last() on an array with multiple elements
	 */
	@Test
	public void testRemoveLast_Multiple()
	{

		test.add_last(0);
		test.add_last(1);
		test.add_last(2);

		assertEquals(3, test.size());

		// order should be 0,1,2

		Integer result = test.remove_last();
		Integer expected = 2;

		assertEquals(expected, result);
		assertEquals(2, test.size());

		// check that the last element is now 1, not 0
		assertTrue(test.get_last() == 1);
		// first element unchanged
		assertTrue(test.get_first() == 0);

	}

	/**
	 * test remove_first on single element list
	 */
	@Test
	public void testRemoveFirst_Single()
	{
		test.add_first(1);

		assertEquals(1, test.size());

		test.remove_first();

		assertEquals(0, test.size());
	}

	/**
	 * test remove_first on multiple element list
	 */
	@Test
	public void testRemoveFirst_Multiple()
	{
		test.add_last(0);
		test.add_last(1);
		test.add_last(2);

		assertEquals(3, test.size());

		// order should be 0,1,2

		Integer result = test.remove_first();
		Integer expected = 0;

		assertEquals(expected, result);

		// order should now be 1,2
		assertEquals(2, test.size());
		assertTrue(test.get_last() == 2);
		// first element should be changed
		assertTrue(test.get_first() == 1);
	}

	/**
	 * tests that clear works on an empty list
	 */
	@Test
	public void testClear_Empty()
	{
		assertEquals(0, test.size());
		test.clear();

		// size should remain 0
		assertEquals(0, test.size());
	}

	/**
	 * clear works when there is only one element
	 */
	@Test
	public void testClear_SingleElement()
	{
		test.add_first(0);
		assertEquals(1, test.size());

		test.clear();

		// test should be empty
		assertEquals(0, test.size());
	}

	/**
	 * clear works on entries with multiple elements
	 */
	@Test
	public void testClear_MultipleElements()
	{
		test.add_first(0);
		test.add_first(0);
		test.add_first(0);

		// size should be 3
		assertEquals(3, test.size());

		test.clear();

		// test should be empty
		assertEquals(0, test.size());
	}

	/**
	 * test non recursive contains methdo on empty list
	 */
	@Test
	public void testContains_Empty()
	{
		// test should be empty and contiains nothing
		assertTrue(test.contains(2) == false);
	}

	/**
	 * test non recursive contains method on single element list
	 */
	@Test
	public void testContains_Single()
	{

		test.add_first(1);
		assertTrue(test.contains(1) == true);
		assertFalse(test.contains(1000) == true);

	}

	/**
	 * test non recursive contains on multiple element list
	 */
	@Test
	public void testContains_Multiple()
	{
		test.add_first(1);
		test.add_last(2);
		test.add_first(3);
		// test should contain 1,2,3 but not 10
		assertTrue(test.contains(10) == false);
		assertTrue(test.contains(1) == true);
		assertTrue(test.contains(2) == true);
		assertTrue(test.contains(3));
	}

	/**
	 * test recursive contains method on empty list
	 */
	@Test
	public void testContainsRecursive_Empty()
	{
		// test should be empty and contiains nothing
		assertTrue(test.contains(2) == false);
	}

	/**
	 * test recursive contains method on single element list
	 */
	@Test
	public void testContainsRecursive_Single()
	{

		test.add_first(1);
		assertTrue(test.contains(1) == true);
		assertFalse(test.contains(1000) == true);

	}

	/**
	 * test recursive contains on multiple element list
	 */
	@Test
	public void testContainsRecursive_Multiple()
	{
		test.add_first(1);
		test.add_last(2);
		test.add_first(3);
		// test should contain 1,2,3 but not 10
		assertTrue(test.contains(10) == false);
		assertTrue(test.contains(1) == true);
		assertTrue(test.contains(2) == true);
		assertTrue(test.contains(3) == true);
	}

	/**
	 * shows that reverse works on an empty array
	 */
	@Test
	public void testReverse_Empty()
	{

		assertEquals(0, test.size());

		test.reverse();

		assertEquals(0, test.size());

		// nothing happened.
		// very difficult to test reverse on empty data set

	}

	/**
	 * shows that reverse works with a single element
	 */
	@Test
	public void testReverse_SingleElement()
	{
		test.add_first(0);

		assertTrue(test.get_first() == 0);
		assertTrue(test.get_last() == 0);
		;

		test.reverse();

		assertTrue(test.get_first() == 0);
		assertTrue(test.get_last() == 0);

	}

	/**
	 * tests that reverse works for lists with 2 elements
	 */
	@Test
	public void testReverse_TwoElements()
	{
		test.add_last(1);
		test.add_last(2);

		// order is 1, 2
		assertTrue(1 == test.get_first());
		assertTrue(2 == test.get_last());

		test.reverse();

		// order should now be 2, 1
		assertTrue(2 == test.get_first());
		assertTrue(1 == test.get_last());
	}

	/**
	 * Test the reverse() method for multiple elements. Test by adding 4
	 * elements, checking the ends, reversing them, checking ends again. Then,
	 * remove the outer ends, and check to ensure that order stayed constant.
	 */
	@Test
	public void testReverse_ManyElements2()
	{
		test.add_last(1);
		test.add_last(2);
		test.add_last(3);
		test.add_last(4);

		// order should be 1,2,3,4
		assertEquals(4, test.size());
		assertTrue((1 == test.get_first()) && (4 == test.get_last()));

		test.reverse();

		// order should be 4,3,2,1
		assertTrue((4 == test.get_first()) && (1 == test.get_last()));
		// size hasn't changed
		assertEquals(4, test.size());

		// removing first and last elements to ensure that order is proper
		test.remove_first();
		test.remove_last();

		assertEquals(2, test.size());

		assertTrue(3 == test.get_first());
		assertTrue(2 == test.get_last());

		test.reverse();

		// order should be 2,3
		assertTrue((2 == test.get_first()) && (3 == test.get_last()));

	}

	/**
	 * test the toArrayList() non-recursive method for empty list
	 */
	@Test
	public void testToArrayList_Empty()
	{

		ArrayList<Integer> expected = new ArrayList<>();

		assertEquals(expected, test.to_ArrayList());
	}

	/**
	 * test the toArrayList() non-recursive method for single value
	 */
	@Test
	public void testToArrayList_Single()
	{
		ArrayList<Integer> expected = new ArrayList<>();
		expected.add(1);

		test.add_last(1);

		assertEquals(expected, test.to_ArrayList());

	}

	/**
	 * test the toArrayList() non-recursive method for multiple values
	 */
	@Test
	public void testToArrayList_Multiple()
	{
		ArrayList<Integer> expected = new ArrayList<>();
		for (int i = 0; i < 100; i++)
		{
			expected.add(i);
			test.add_last(i);
		}

		assertEquals(expected, test.to_ArrayList());

	}

	/**
	 * test the toArrayList() non-recursive method for empty list
	 */
	@Test
	public void testToArrayListPostRecurse_Empty()
	{

		ArrayList<Integer> expected = new ArrayList<>();

		assertEquals(expected, test.to_ArrayList_post_recurse());
	}

	/**
	 * test the toArrayList() non-recursive method for single value
	 */
	@Test
	public void testToArrayListPostRecurse_Single()
	{
		ArrayList<Integer> expected = new ArrayList<>();

		expected.add(1);
		test.add_last(1);

		ArrayList<Integer> result = test.to_ArrayList_post_recurse();

		assertEquals(expected, result);

	}

	/**
	 * test the toArrayList() non-recursive method for multiple values
	 */
	@Test
	public void testToArrayListPostRecurse_Multiple()
	{
		ArrayList<Integer> expected = new ArrayList<>();

		// in reverse order because that's how post recuse works
		expected.add(4);
		expected.add(3);
		expected.add(2);
		expected.add(1);

		test.add_last(1);
		test.add_last(2);
		test.add_last(3);
		test.add_last(4);

		ArrayList<Integer> result = test.to_ArrayList_post_recurse();

		assertEquals(expected, result);

	}

	/**
	 * test the helper method getBackingArrayLength() in Array_List_2420 class
	 */
	@Test
	public void testGetBackingArrayLength_ArrayListOnly()
	{

		if (test instanceof Array_List_2420)
		{
			// initializes to a 1024 array:
			assertEquals(1024, ((Array_List_2420) test).getBackingArrayLength());

			// make alternate sized array
			Array_List_2420 test2 = new Array_List_2420(50);
			assertEquals(50, test2.getBackingArrayLength());
		} else
		{
			assertFalse(1 == 2);
		}

	}

}
