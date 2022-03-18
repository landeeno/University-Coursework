/**
 * 
 */
package cs2420;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * This class tests all the main functionality of the Node class
 * @author Brent Collins and Landon Crowther
 *
 */
public class NodeTest
{
	//main node that is used frequently for testing purposeds
	Node test;
	

	/**
	 * setup main test node
	 */
	@Before
	public void setUp() throws Exception
	{
		test = new Node("test", 1000);
	}
	
	/**
	 * Tests proper functionality of the leaf node constructor
	 */
	@Test
	public final void testLeafConstructor()
	{
		Node testNode = new Node("testNode" , 0);
		assertEquals( "testNode" , testNode.get_symbol());
		assertEquals(0 , testNode.get_frequency());
		assertTrue( testNode.leaf());
	}

	/**
	 * test proper functionality of the non-leaf node
	 * constructor
	 */
	@Test
	public final void testNonLeafConstructor()
	{
		Node left = new Node("left" , 0);
		Node right = new Node("right", 1);
		Node parent = new Node("parent" , left, right);
		
		assertFalse( parent.leaf() );
		assertEquals( left.get_parent() , parent );
		assertEquals( right.get_parent() , parent) ;
		
	}
		

	/**
	 * Ensures that get_symbol() works 
	 */
	@Test
	public final void testGet_symbol()
	{
		assertEquals("test" , test.get_symbol());
		
		
	}

	/**
	 * proper toString() functionality
	 */
	@Test
	public final void testToString()
	{
		String expected = "test: 1000";
		assertEquals( expected, test.toString() );
	}

	/**
	 * Test method for {@link cs2420.Node#leaf()}.
	 */
	@Test
	public final void testLeaf()
	{
		Node leaf1 = new Node ("left", 1000);
		Node leaf2 = new Node("right", 2000);
		Node parent = new Node ("parent" ,leaf1, leaf2);
		
		assertTrue(leaf1.leaf());
		assertTrue(leaf2.leaf());
		assertFalse(parent.leaf());
		
	}


	/**
	 * shows that the set_parent method works proerly
	 */
	@Test
	public final void testSet_parent()
	{
		Node leaf1 = new Node ("random", 1000);
		Node leaf2 = new Node("right", 2000);
		Node leaf3 = new Node("random" , 0);
		
		Node parent = new Node ("parent" ,leaf1, leaf2);
		
		assertTrue(leaf3.get_parent() == null);
		leaf3.set_parent(parent);
		assertTrue(leaf3.get_parent() == parent);
	}

	/**
	 * proper functionality of getter method for node class
	 */
	@Test
	public final void testGet_parent()
	{
		
		Node parent = new Node("parent", test, new Node("empty", 0));
		assertEquals(parent, test.get_parent() );
		
	}


	/**
	 * returns the parents left
	 */
	@Test
	public final void testParents_left()
	{
		Node left = new Node("left", 1);
		Node right = new Node("right", 2);
		@SuppressWarnings("unused")
		Node parent = new Node("parent", left, right);
		
		assertEquals( left, right.parents_left() );
		
	}

	/**
	 * proper functionality of get frequency method
	 */
	@Test
	public final void testGet_frequency()
	{
		assertEquals( 1000, test.get_frequency() );
	}

	/**
	 * Test method for {@link cs2420.Node#increment_frequency()}.
	 */
	@Test
	public final void testIncrement_frequency()
	{
		Node newNode = new Node("" , 0);
		assertTrue(newNode.get_frequency() == 0);
		newNode.increment_frequency();
		assertTrue(newNode.get_frequency() == 1);
		
	}

	/**
	 * constructs a tree and ensures that get_symbol
	 * works properly
	 */
	@Test
	public final void testGet_symbolString()
	{
		/*
		 *	Construct following tree ('-') indicates non-symbol node:
		 *
		 * 					-
	     * 				-      50
	     * 			 3	  -
	     * 				 4	5  
		 */
		
		Node leaf3 = new Node("leaf3" , 3);
		Node leaf4 = new Node("leaf4" , 4);
		Node leaf5 = new Node("leaf5" , 5);
		Node leaf50 = new Node("leaf50" , 50);
		Node p2 = new Node("p2" , leaf4, leaf5);
		Node p1 = new Node("p1" , leaf3, p2);		
		Node root = new Node("root" , p1, leaf50);
		
		//test all the Nodes in the tree
		assertEquals( "leaf3" , root.get_symbol("00"));
		assertEquals( "leaf4" , root.get_symbol("010" ) );
		assertEquals( "leaf5" , root.get_symbol("011"));
		assertEquals("leaf50", root.get_symbol("1") );
		
		//test for non-leaf node:
		assertEquals(null, root.get_symbol("0"));
		
		//test for illegal string:
		assertEquals(null, root.get_symbol("wrong_code"));
		
		
		
	}

	/**
	 * shows that the proper comparison is implemented
	 */
	@Test
	public final void testCompareTo()
	{
		
		Node node1 = new Node("node1" , 1);
		Node node2 = new Node("node2" , 2);
		
		assertTrue( node1.compareTo(node2) < 0 );
		
	}
	
	/**
	 * proper compareTo when the frequencies are the same
	 */
	@Test
	public final void testCopmareToForSameFrequency()
	{
		/*
		 *	Construct following tree ('-') indicates non-symbol node:
		 *
		 * 				  --
	     * 				-     - 
	     * 			   1 2   2 1
		 */
		
		Node n1 = new Node("1" , 1);
		Node n2 = new Node("2", 2);
		Node n3 = new Node("3" , 2);
		Node n4 = new Node("4" , 1);
		
		Node parent1 = new Node("p1" , n1, n2);
		Node parent2 = new Node("p2" , n3, n4);
		Node root = new Node("root", parent1, parent2 );
		
		assertTrue(root.get_frequency() == 6);
		assertTrue (parent1.get_frequency() == parent2.get_frequency());
		// p1 leftMostNode is less than p2 leftMostNode
		assertTrue( parent1.compareTo(parent2) < 0 );
		
		
	}




//end of class
}
