package assignment09;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * This class tests that for several mazes under 100x100, solving the maze takes
 * less than 10 seconds.
 * 
 * @author Nathan Robbins, Landon Crowther
 * @date March 29, 2017
 */
public class PathFinderTest
{
	long startTime;

	@Before
	public void setUp() throws Exception
	{
		startTime = System.nanoTime();
	}

	@Test
	public void testTinyMazeUnderTenSeconds()
	{
		PathFinder.solveMaze("Resources/tinyMaze.txt", "testOutputMaze.txt");
		long endTime = System.nanoTime();
		long totalTime = endTime - startTime;
		double timeInSec = totalTime * Math.pow(10, -9);
		assertTrue(timeInSec < 10);
	}

	@Test
	public void testMediumMazeUnderTenSeconds()
	{
		PathFinder.solveMaze("Resources/mediumMaze.txt", "testOutputMaze.txt");
		long endTime = System.nanoTime();
		long totalTime = endTime - startTime;
		double timeInSec = totalTime * Math.pow(10, -9);
		assertTrue(timeInSec < 10);
	}

	@Test
	public void testBigMazeUnderTenSeconds()
	{
		PathFinder.solveMaze("Resources/bigMaze.txt", "testOutputMaze.txt");
		long endTime = System.nanoTime();
		long totalTime = endTime - startTime;
		double timeInSec = totalTime * Math.pow(10, -9);
		assertTrue(timeInSec < 10);
	}

	@Test
	public void testRandomMazeUnderTenSeconds()
	{
		PathFinder.solveMaze("Resources/randomMaze.txt", "testOutputMaze.txt");
		long endTime = System.nanoTime();
		long totalTime = endTime - startTime;
		double timeInSec = totalTime * Math.pow(10, -9);
		assertTrue(timeInSec < 10);
	}

	@Test
	public void testUnsolvableMazeUnderTenSeconds()
	{
		PathFinder.solveMaze("Resources/unsolvable.txt", "testOutputMaze.txt");
		long endTime = System.nanoTime();
		long totalTime = endTime - startTime;
		double timeInSec = totalTime * Math.pow(10, -9);
		assertTrue(timeInSec < 10);
	}

}
