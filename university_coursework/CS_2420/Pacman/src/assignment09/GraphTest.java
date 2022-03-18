package assignment09;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.BeforeClass;
import org.junit.Test;

public class GraphTest
{
	private static Graph smallGraph;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		smallGraph = new Graph("Resources/tinyMaze.txt");
	}

	/**
	 * Tests the Graph constructor correctly interprets a file.
	 */
	@Test
	public void testGraphConstructor()
	{
		Graph tinyGraph = new Graph("Resources/tinyOpen.txt");
		assertEquals(tinyGraph.startID, tinyGraph.gameGrid[1][1]);
		assertEquals(tinyGraph.goalID, tinyGraph.gameGrid[3][3]);
		
		assertEquals(-1, tinyGraph.gameGrid[0][0]);
		assertTrue(tinyGraph.gameGrid[2][2] >= 0);
		
	}

	/**
	 * Tests that the correct shortest path is found for a maze with a known shortest path.
	 */
	@Test
	public void testFindShortestPath()
	{
		smallGraph.findShortestPath();
		assertEquals(-5, smallGraph.gameGrid[5][5]);
		assertEquals(-5, smallGraph.gameGrid[4][5]);
		assertEquals(-5, smallGraph.gameGrid[4][4]);
		assertEquals(-5, smallGraph.gameGrid[4][3]);
		assertEquals(-5, smallGraph.gameGrid[3][3]);
		assertEquals(-5, smallGraph.gameGrid[2][3]);
		assertEquals(-5, smallGraph.gameGrid[1][3]);
		assertEquals(-5, smallGraph.gameGrid[1][4]);
	}

	// Exception Testing
	
	/**
	 * Test that a file with invalid boundaries throws a RuntimeException error.
	 */
	@Test(expected = RuntimeException.class)
	public void testInvalidBoundaryFormatThrowsException()
	{
		Graph invalidGraph = new Graph("Resources/illegalBoundaries.txt");
	}
	
	/**
	 * Tests that a file without dimension listings throws a RuntimeException error.
	 */
	@Test(expected = RuntimeException.class)
	public void testMissingDimensionsThrowsException()
	{
		Graph invalidGraph = new Graph("Resources/noDimensions.txt");
	}

}
