package cs2420;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the functionality of each method in
 * the BinarySearchTree class. Tests include boundary,
 * edge, and special cases.
 * @author lando
 *
 */
public class BinarySearchTreeTest
{
	//BSTs that will be used for testing.
	BinarySearchTree<Integer> empty;
	BinarySearchTree<Integer> single;
	BinarySearchTree<Integer> multiple;
	

	/**
	 * will create following BST:
	 * 
	 * 				5
	 * 
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUpSingle() throws Exception
	{
		single = new BinarySearchTree<Integer>();
		single.add(1);
	}
	
	/**
	 * empty is a BST with no values
	 */
	@Before 
	public void setUpEmpty()
	{
		empty = new BinarySearchTree<Integer>();
	}
	
	/**
	 * Will create the following tree for test purposes: (size = 8)
	 *                         7
	 *                   3           10
	 *                 1   5           15
	 *                    4  6
	 */
	@Before
	public void setUpMultiple() throws Exception
	{
		multiple = new BinarySearchTree<Integer>();
		multiple.add(7);
		multiple.add(3);
		multiple.add(1);
		multiple.add(5);
		multiple.add(4);
		multiple.add(6);
		multiple.add(10);
		multiple.add(15);
	}

	/**
	 * shows that size() works on empty BST
	 */
	@Test
	public void sizeEmpty()
	{
		assertTrue(empty.size() == 0);
	}
	
	/**
	 * shows that size() works BST with one element
	 */
	@Test
	public void sizeSingle()
	{
		assertTrue(single.size() == 1);
	}
	
	/**
	 * shows that size() works BST with many elements
	 */
	@Test
	public void sizeMultiple()
	{
		assertTrue(multiple.size() == 8);
	}
	
	/**
	 * tests remove method works for a BST when calling 
	 * remove on the root node
	 */
	@Test
	public void removeRootMultiple()
	{
		assertEquals(true, multiple.remove(7));
		assertEquals(7 , multiple.size());
		
	}
	
	/**
	 * tests remove method on single BST
	 */
	@Test
	public void removeSingle()
	{
		assertTrue(single.size() == 1);
		
		assertEquals(true, single.remove(1));
		
		assertEquals(0 , single.size());
	}
	
	/**
	 * successfully removes element from BST with many values
	 * 
	 * remove at end value
	 */
	@Test
	public void removeMultipleEnd()
	{
		assertEquals(8, multiple.size());
		assertEquals(true, multiple.remove(15));
		//remove value at the end of BST tree
		assertEquals(7, multiple.size());
		assertEquals(false, multiple.contains(15));
	}
	
	/**
	 * successfully removes element from BST with many values
	 * 
	 * remove at middle value
	 */
	@Test
	public void removeMultipleMiddle()
	{
		assertEquals(8, multiple.size());
		assertEquals(true, multiple.remove(10));
		//remove value at the mid section of BST tree
		assertEquals(7, multiple.size());
		assertEquals(false, multiple.contains(10));
	}
	
	/**
	 * successfully removes element from BST with many values
	 * 
	 * remove at root
	 */
	@Test
	public void removeMultipleRoot()
	{
		assertEquals(8, multiple.size());
		assertEquals(true, multiple.remove(7));
		//remove value at the mid section of BST tree
		assertEquals(7, multiple.size());
		assertEquals(false, multiple.contains(7));
	}
	
	/**
	 * tests remove methond on empty BST
	 */
	@Test
	public void removeEmpty()
	{
		assertEquals( false, empty.remove(1));
	}
	
	/**
	 * contains should return false on an empty array
	 */
	@Test
	public void containsEmpty()
	{
		assertEquals( false, empty.contains(1));
	}
	
	/**
	 * shows that contains works on a BST with only 1 elememnt
	 */
	@Test
	public void containsSingle()
	{
		assertEquals(false, single.contains(1000));
		assertEquals(true, single.contains(1));
	}
	
	/**
	 * shows that the contains method works on nonsingle BST.
	 * 
	 * Note, the test passes while testing contains on a 
	 * element that doesn't exist.
	 */
	@Test
	public void containsMultiple()
	{
		assertEquals(false, multiple.contains(1000));
		assertEquals(true, multiple.contains(7));
		assertEquals(true, multiple.contains(10));
		assertEquals(true, multiple.contains(15));
		assertEquals(true, multiple.contains(3));
		assertEquals(true, multiple.contains(1));
		assertEquals(true, multiple.contains(5));
		assertEquals(true, multiple.contains(4));
		assertEquals(true, multiple.contains(6));		
	}
	
	/**
	 * tests toArrayList() on empty BST. Note, because
	 * an empty BST defaults to null, the element null
	 * had to be added
	 */
	@Test
	public void toArrayListEmpty()
	{
		ArrayList<Integer> empty = new ArrayList<>();
		empty.add(null);
		ArrayList<Integer> result = this.empty.toArrayList();
		assertEquals(empty, result);
	}
	
	
	/**
	 * tests toArrayList on a single array by building an
	 * ArrayList with the same element as the "single" BST
	 * and comparing
	 */
	@Test
	public void toArrayListSingle()
	{
		ArrayList<Integer> single = new ArrayList<Integer>();
		single.add(1);
		
		ArrayList<Integer> result = this.single.toArrayList();
		assertEquals(result, single);
	}
	
	
	/**
	 * tests the toArrayList method. Works by calling the toArrayList 
	 * method on the "multiple" BST, and also adding all of the elements
	 * in the proper order to an "expected" ArrayList and comparing the two
	 * 
	 */
	@Test
	public void toArrayListMultiple()
	{
		ArrayList<Integer> expected = new ArrayList<Integer>();
		//adding values to "expected" in proper order:
		expected.add(1);
		expected.add(3);
		expected.add(4);
		expected.add(5);
		expected.add(6);
		expected.add(7);
		expected.add(10);
		expected.add(15);
		
		ArrayList<Integer> result = multiple.toArrayList();
		
		assertEquals(expected, result);
	}
	
	/**
	 * The following method tests the add method. It works by adding 
	 * elements to the "empty" BST. In addition, it tests that elements
	 * can't be added twice. 
	 */
	@Test
	public void addEmpty()
	{
		assertTrue (0 == empty.size());
		
		assertEquals( true , empty.add(1) );
		
		//size should be 1:
		assertEquals(1, empty.size());
		
		//try adding 1 again, (should be false)
		assertEquals(false, empty.add(1));
		
		//size hasn't changed:
		assertEquals(1, empty.size());
		
		//add something other than 1
		assertEquals(true, empty.add(5) );
		
		//size should be 2:
		assertEquals(2, empty.size());		
	}
	
	/**
	 * test addAll. Works by adding a list of values successfully,
	 * and unsuccessfully adding the same values
	 */
	@Test
	public void addAll()
	{
		ArrayList<Integer> values = new ArrayList<>();
		values.add(5);
		values.add(4);
		values.add(3);
		values.add(2);
		values.add(1);
		
		assertEquals(true, empty.addAll(values));
		
		assertEquals(5, empty.size());
		
		//try and add the same values again
		assertEquals(false, empty.addAll(values));
	}
	
	/**
	 * test clear on an empty set. Testing by
	 * size, but size won't change 
	 */
	@Test
	public void clearEmpty()
	{
		assertTrue( 0 == empty.size());
		empty.clear();
		assertTrue(0 == empty.size());
		
	}
	
	/**
	 * test clear on empty BST
	 */
	@Test
	public void clearSingle()
	{
		assertEquals( 1 , single.size());
		single.clear();
		assertEquals(0 , single.size());
	}
	
	/**
	 * test clear on multiple BST
	 */
	@Test
	public void clearMultiple()
	{
		assertEquals( 8 , multiple.size());
		multiple.clear();
		assertEquals(0 , multiple.size());
	}
	
	/**
	 * show that exception is thrown when testing first()
	 * on empty BST
	 */
	@Test(expected=java.util.NoSuchElementException.class)
	public void firstEmptyException() 
	{
		//test empty - should throw exception
		empty.first();
	}
	
	/**
	 * show that exception is thrown when testing first()
	 * on empty BST
	 */
	@Test(expected=java.util.NoSuchElementException.class)
	public void lastEmptyException() 
	{
		//test empty - should throw exception
		empty.last();
	}
	
	/**
	 * Show that first() and last() methods
	 * work on a BST with one element
	 */
	@Test
	public void firstAndLastSingle()
	{
		assertTrue(1 == single.first());
		assertTrue(1 == single.last());
	}
	
	
	/**
	 * test first method by getting first (lowest) element,
	 * removing it, and getting the next first element
	 * 
	 */
	@Test
	public void firstMultiple()
	{
		assertTrue(1 == multiple.first());
		assertTrue(multiple.remove(1));
		
		assertTrue(3 == multiple.first());
	}
	
	
	/**
	 * test last method by getting last (highest) element,
	 * removing it, and getting the next last element
	 * 
	 */
	@Test
	public void lastMultiple()
	{
		assertTrue(15 == multiple.last());
		assertTrue(multiple.remove(15));
		assertTrue(10 == multiple.last());
	}
	
	/**
	 * tests that removeAll works properly. Works by calling addAll on a 
	 * set of values, and then removeAll on the same set.
	 */
	@Test
	public void removeAll()
	{
		ArrayList<Integer> values = new ArrayList<>();
		values.add(5);
		values.add(4);
		values.add(3);
		values.add(2);
		values.add(1);
		
		assertEquals(true, empty.addAll(values));
		
		assertEquals(5, empty.size());
		
		//empty should have 5,4,3,2,1
		assertEquals(true, empty.removeAll(values));
		
		
		assertEquals(0, empty.size());
		

	}
	
	/**
	 * test isEmpty method on empty, single, and multiple
	 */
	@Test
	public void isEmpty()
	{
		assertTrue(empty.isEmpty());
		assertFalse(single.isEmpty());
		assertFalse(multiple.isEmpty());
	}
	
	/**
	 * shows proper functionality of containsAll
	 */
	@Test
	public void containsAll()
	{
		ArrayList<Integer> values = new ArrayList<>();
		values.add(5);
		values.add(4);
		values.add(3);
		values.add(2);
		values.add(1);
		
		assertEquals(true, empty.addAll(values));
		
		assertEquals(5, empty.size());
		
		//empty should have 5,4,3,2,1
		assertTrue(empty.containsAll(values));
	}
	
	
	
	

}
