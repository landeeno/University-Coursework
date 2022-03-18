package assignment09;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class NodeTest
{
	Node n1, n2;

	@Before
	public void setUp() throws Exception
	{
		n1 = new Node(1, 2, 3);
		n2 = new Node(2 , 3, 4);
	}

	/**
	 * Tests that all fields of a Node are instantiated correctly by the constructor.
	 */
	@Test
	public void testNodeConstructor()
	{
		Node testNode = new Node(0,1,2);
		assertEquals(0,testNode.getID());
		assertEquals(1, testNode.getX());
		assertEquals(2, testNode.getY());
		
		assertFalse(testNode.alreadySeen());
		assertTrue(testNode.adjacent != null);
		assertTrue(testNode.getBack() == null);
	}

	/**
	 * Tests that connecting two Nodes adds the connection to each Node's adjacency list.
	 */
	@Test
	public void testConnectTo()
	{

	
		assertFalse(n1.adjacent.contains(n2));
		assertFalse(n2.adjacent.contains(n1));
		
		n1.connectTo(n2);
		
		assertTrue(n1.adjacent.contains(n2));
		assertTrue(n2.adjacent.contains(n1));
	}

	/**
	 * Tests functionality of getter and setter for Node 'previous' field.
	 */
	@Test
	public void testSetBackAndGetBack()
	{
		assertTrue(n1.getBack() == null);
		n1.setBack(n2);
		assertTrue(n1.getBack() == n2);
	}

	/**
	 * Tests functionality of getter and setter for Node 'seen' field.
	 */
	@Test
	public void testMarkAsSeenAndAlreadySeen()
	{
		assertFalse(n1.alreadySeen());
		n1.markAsSeen();
		assertTrue(n1.alreadySeen());
	}

	/**
	 * Tests that the adjacency list is properly updated when connecting Nodes.
	 * Tests the cases of 0 adjacent, 1 adjacent, 2 adjacent.
	 */
	@Test
	public void testAdjacent()
	{
		for(Node node: n1.adjacent())
		{
			fail("Adjacency iterated with no nodes adjacent.");
		}
		
		n1.connectTo(n2);
		for(Node node: n1.adjacent())
		{
			assertTrue(n1.adjacent.contains(node));
		}
		
		n1.connectTo(new Node(3,3,3));
		
		for(Node node: n1.adjacent())
		{
			assertTrue(n1.adjacent.contains(node));
		}
	}

	/**
	 * Tests functionality of getter for Node 'id' field.
	 */
	@Test
	public void testGetID()
	{
		assertEquals(1, n1.getID());
		assertEquals(2, n2.getID());
	}

	/**
	 * Tests functionality of getter for Node 'yPos' field.
	 */
	@Test
	public void testGetY()
	{
		assertEquals(3, n1.getY());
		assertEquals(4, n2.getY());
	}

	/**
	 * Tests functionality of getter for Node 'xPos' field.
	 */
	@Test
	public void testGetX()
	{
		assertEquals(2, n1.getX());
		assertEquals(3, n2.getX());
	}

}
