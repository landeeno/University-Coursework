package Sudoku;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

public class SudokuTest extends Sudoku
{

	Sudoku puzzle;



	/**
	 * shows that the backing structure of the Sudoku
	 * class variables works properly, and proper 1D
	 * array is constructed.
	 * 
	 */
	@Test
	public void testPuzzle1D()
	{
		puzzle = new Sudoku();

		int[] result = puzzle.get_puzzle_1D();		
		int[] expected = new int[81];

		assertArrayEquals ( result , expected );

		puzzle.puzzleValues[1] = 1;		
		expected[1] = 1;

		assertArrayEquals ( result , expected );
	}


	/**
	 * shows that that get_puzzle_2D method
	 * will format the puzzle properly
	 */
	@Test
	public void testPuzzle2D()
	{
		puzzle = new Sudoku();
		puzzle.puzzleValues[15] = 1;

		int[][] result = puzzle.get_puzzle_2D();
		int[][] expected = new int[9][9];

		int[] rowAndCol = Sudoku.determine2DLocation(15);

		expected[ rowAndCol[0] ][ rowAndCol[1] ] = 1;

		assertArrayEquals(result , expected);

	}


	@Test
	public void testGetGuessCount()
	{
		puzzle = new Sudoku();

		//initially no guess counts
		assertTrue(puzzle.get_guess_count() == 0);

		/*
		 * change guess count
		 * 
		 * NOTE - had to make recursiveGuessCount protected
		 * in order to change it
		 */
		puzzle.recursiveGuessCount = 15;
		assertTrue(puzzle.get_guess_count() == 15);

	}

	/**
	 * test to make sure that valid_for_row function
	 * does not accept illegal parameters
	 */
	@Test
	public void testValidForRowBounds()
	{

		puzzle = new Sudoku();

		//row parameter cannot be above 8 or below 0
		assertTrue(puzzle.valid_for_row(9, 5) == false);
		assertTrue(puzzle.valid_for_row(-1, 5) == false);
	}


	/**
	 * test the valid_for_row method to make sure that for the following puzzle I
	 * get the expected results
	 * 
	 * Row, Col
	 * 
	 *    0 1 2  3 4 5  6 7 8 
	 *    
	 * 0  0 0 0  1 0 0  0 0 0
	 * 1  0 0 0  0 2 0  0 0 0
	 * 2  0 0 0  0 0 3  0 0 0
	 * 
	 * 3  0 0 0  0 0 0  0 0 0
	 * 4  0 0 0  0 0 0  0 0 0
	 * 5  0 0 0  0 0 0  0 0 0
	 * 
	 * 6  0 0 0  0 0 0  0 0 0
	 * 7  0 0 0  0 0 0  0 0 0
	 * 8  0 0 0  0 0 0  0 0 0
	 * 
	 */
	@Test
	public void testValidForRow()
	{
		puzzle = new Sudoku();

		puzzle.puzzleValues[3]  = 1;
		puzzle.puzzleValues[13] = 2;
		puzzle.puzzleValues[23] = 3;

		//row 0 CANNOT have another 1 as it already exists
		assertFalse(puzzle.valid_for_row(0,1));

		//row 0 CAN have a 9 -- it does not already exist
		assertTrue(puzzle.valid_for_row(0, 9));

		//row 8 can have ANY value
		for (int possibleNum = 1; possibleNum < 10; possibleNum++)
		{
			assertTrue(puzzle.valid_for_row(8, possibleNum));
		}
	}

	/**
	 * test to make sure that valid_for_column function
	 * does not accept illegal parameters
	 */
	@Test
	public void testValidForColumnBounds()
	{
		puzzle = new Sudoku();

		//column parameter cannot be above 8 or below 0
		assertTrue(puzzle.valid_for_column(9, 5) == false);
		assertTrue(puzzle.valid_for_column(-1, 5) == false);
	}

	/**
	 * test the valid_for_column method to make sure that for the following puzzle I
	 * get the expected results
	 * 
	 * Row, Col
	 * 
	 *    0 1 2  3 4 5  6 7 8 
	 *    
	 * 0  0 0 0  1 0 0  0 0 0
	 * 1  0 0 0  0 2 0  0 0 0
	 * 2  0 0 0  0 0 3  0 0 0
	 * 
	 * 3  0 0 0  0 0 0  0 0 0
	 * 4  0 0 0  0 0 0  0 0 0
	 * 5  0 0 0  0 0 0  0 0 0
	 * 
	 * 6  0 0 0  0 0 0  0 0 0
	 * 7  0 0 0  0 0 0  0 0 0
	 * 8  0 0 0  0 0 0  0 0 0
	 * 
	 */
	@Test
	public void testValidForColumn()
	{

		puzzle = new Sudoku();

		puzzle.puzzleValues[3]  = 1;
		puzzle.puzzleValues[13] = 2;
		puzzle.puzzleValues[23] = 3;

		//column 3 CANNOT have another "1" -- 1 already exists
		assertFalse(puzzle.valid_for_column(3, 1));

		//column 3 CAN have a 9, because a 9 does not exist yet. 
		assertTrue(puzzle.valid_for_column(3, 9));

		//column 8 can have ANY number
		for (int possibleNum = 1; possibleNum < 10; possibleNum++)
		{
			assertTrue(puzzle.valid_for_column(8, possibleNum));
		}

	}


	/**
	 * test to make sure that valid_for_row function
	 * does not accept illegal parameters
	 */
	@Test
	public void testValidForBoxBounds()
	{
		puzzle = new Sudoku();

		//box parameter cannot be above 8 or below 0
		assertTrue(puzzle.valid_for_box(9, 5) == false);
		assertTrue(puzzle.valid_for_box(-1, 5) == false);
	}


	/**
	 * 
	 * test valid_for_box method to make sure that duplicated
	 * values cannot be placed in the same box
	 * 
	 * 
	 * boxes are mapped out as the following: 
	 * 
	 * 0 | 1 | 2 
	 * --+---+--
	 * 3 | 4 | 5 
	 * --+---+---
	 * 6 | 7 | 8
	 * 
	 * 
	 * Will construct the following Sudoku puzzle and perform tests
	 * 
	 *  Row, Col
	 * 
	 *    0 1 2  3 4 5  6 7 8 
	 *    
	 * 0  0 0 0  1 0 0  0 0 0
	 * 1  0 0 0  0 2 0  0 0 0
	 * 2  0 0 0  0 0 3  0 0 0
	 * 
	 * 3  0 0 0  0 0 0  0 0 0
	 * 4  0 0 0  0 0 0  0 0 0
	 * 5  0 0 0  0 0 0  0 0 0
	 * 
	 * 6  0 0 0  0 0 0  0 0 0
	 * 7  0 0 0  0 0 0  0 0 0
	 * 8  0 0 0  0 0 0  0 0 0
	 * 
	 *  
	 */
	@Test
	public void testValidForBox()
	{
		puzzle = new Sudoku();

		puzzle.puzzleValues[3]  = 1;
		puzzle.puzzleValues[13] = 2;
		puzzle.puzzleValues[23] = 3;

		final int box1 = 1;

		//box 1 CANNOT have another 1
		assertFalse(puzzle.valid_for_box(box1, 1)); 
		//box 1 CAN contain a 9
		assertTrue(puzzle.valid_for_box(1,  9));

		//box 8 can have ANY number
		for (int possibleNum = 1; possibleNum < 10; possibleNum++)
		{
			assertTrue(puzzle.valid_for_box(8, possibleNum)); 
		}
	}


	/**
	 * test to make sure that the is_valid method 
	 * doesn't work if invalid parameters are passed
	 */
	@Test
	public void testIsValidBounds()
	{
		puzzle = new Sudoku();

		//make sure that "position" parameter is within bounds
		assertTrue(puzzle.is_valid(-1, 5) == false );
		assertTrue(puzzle.is_valid(81, 5) == false );
	}

	/**
	 * test is_valid(), which utilizes the three other
	 * valid tests
	 * 
	 * Constructing the following puzzle to conduct tests:
	 * 
	 * 
	 * Row, Col
	 * 
	 *    0 1 2  3 4 5  6 7 8 
	 *    
	 * 0  0 0 0  1 0 0  0 0 0
	 * 1  0 0 0  0 2 0  0 0 0
	 * 2  0 0 0  0 0 3  0 0 0
	 * 
	 * 3  0 0 0  0 0 0  0 0 0
	 * 4  0 0 0  0 0 0  0 0 0
	 * 5  0 0 0  0 0 0  0 0 0
	 * 
	 * 6  0 0 0  0 0 0  0 0 0
	 * 7  0 0 0  0 0 0  0 0 0
	 * 8  0 0 0  0 0 0  0 0 0
	 * 
	 */
	@Test
	public void testIsValid()
	{

		puzzle = new Sudoku();

		puzzle.puzzleValues[3]  = 1;
		puzzle.puzzleValues[13] = 2;
		puzzle.puzzleValues[23] = 3;

		//any number other than 1 can go in the upper left corner
		for (int guess = 2; guess < 10; guess ++)
		{
			assertEquals( (guess != 1) , is_valid( 0, guess ) );
		}
		
		assertTrue(puzzle.is_valid(0, 1) == false);

		//any number can go in the bottom right cell with this setup
		for (int guess = 1; guess < 10; guess ++)
		{
			assertTrue( is_valid(80, guess) == true );
		}
	}

	/**
	 * tests that recursive solver works only within 
	 * valid parameters
	 */
	@Test
	public void solveSudokeBounds()
	{
		puzzle = new Sudoku();

		assertTrue(puzzle.solve_sudoku(-1) == false);
		assertTrue(puzzle.solve_sudoku(82) == false);

	}

	//FIXME find a way to test recursive
	@Test
	public void solveSudoku()
	{
		
		BufferedReader incompleteTXT = null;
		
		try
		{
			incompleteTXT = new BufferedReader(new FileReader(new File("p1.txt")));			
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		Sudoku testPuzzle = new Sudoku(incompleteTXT);
		
		//incomplete Sudoku puzzle is NOT solved
		assertTrue( (testPuzzle.isPozzleSolved == false) );
		
		//solve incomplete Sudoku puzzle
		assertTrue(testPuzzle.solve_sudoku()) ;
		//ensure puzzle is solved
		assertTrue( testPuzzle.isPozzleSolved == true) ;
			
	}


	@Test
	public void toStringTest()
	{
		puzzle = new Sudoku();

		String expected = 
				"0 0 0 | 0 0 0 | 0 0 0 \n"
						+ "0 0 0 | 0 0 0 | 0 0 0 \n"
						+ "0 0 0 | 0 0 0 | 0 0 0 \n"
						+ "------+-------+------ \n"
						+ "0 0 0 | 0 0 0 | 0 0 0 \n"
						+ "0 0 0 | 0 0 0 | 0 0 0 \n"
						+ "0 0 0 | 0 0 0 | 0 0 0 \n"
						+ "------+-------+------ \n"
						+ "0 0 0 | 0 0 0 | 0 0 0 \n"
						+ "0 0 0 | 0 0 0 | 0 0 0 \n"
						+ "0 0 0 | 0 0 0 | 0 0 0 ";

		String result = puzzle.toString();

		assertTrue(result.equals(expected));

	}


	@Test
	public void testVerify1()
	{
		puzzle = new Sudoku();
		
		assertTrue( puzzle.verify() == true );
		
		puzzle.puzzleValues[0] = 1;
		
		assertTrue( puzzle.verify() == true );
		
		puzzle.puzzleValues[1] = 1;
		
		assertTrue ( puzzle.verify() == false );
	}
	

	/**
	 * test that verify works on a previously solved puzzle
	 */
	@Test
	public void testVerify2()
	{
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader(new File("p1_sol.txt")));
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		Sudoku solved = new Sudoku(br);
		
		assertTrue( solved.verify() == true);
		
	}
	
	/**
	 * test that verify works on a non-solved puzzle, and 
	 * also works after puzzle has been solved 
	 */
	@Test
	public void testVerify3()
	{
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader(new File("p1.txt")));
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		Sudoku solved = new Sudoku(br);
		assertTrue( solved.verify() == true);
		
		solved.solve_sudoku();
		
		assertTrue( solved.verify() == true) ;
		
	}
	

	@Test
	public void solveByEliminationTest()
	{
		
		//FIXME
	}


	/**
	 * test the helper methdod that determines 
	 * the number in the hash set
	 */
	@Test
	public void retrieveNumberFromHashSetTest()
	{
		HashSet<Integer> set = new HashSet<>();
		// set is empty
		assertTrue(set.size() == 0);

		// retrieveNumberFromHash set will return -1 bc set is empty
		int expected = -1;
		int result = Sudoku.retrieveNumberFromHashSet(set);

		// set contains something
		set.add(5);
		assertTrue(set.size() == 1);

		// retrieveNumberFromHashSet will return 5 bc that's what was added
		expected = 5;
		result = Sudoku.retrieveNumberFromHashSet(set);

		assertTrue (expected == result);
	}

	/**
	 * This is just testing the general functionality of our print possibilities
	 * 
	 */
	@Test
	public void testPrintPossibilities()
	{
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader(new File("p1.txt")));
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		Sudoku puzzle = new Sudoku(br);
		
		//calling print possibilities method
		//it works -- no errors thrown
		
		puzzle.solve_by_elimination();
		Sudoku.print_possibilities(puzzle.possible);
		

		
	}
	
	/**
	 * This test creates an ArrayList of HashSets, puts a value in 
	 * in an index that corresponds to a HashSet.
	 * We then call the prune_box method on a box that should have
	 * the value we put in. We then make sure that it has removed that value
	 * from the box.
	 */



	@Test
	public void testPruneBox()
	{
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader(new File("p1.txt")));
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		Sudoku puzzle = new Sudoku(br);
		puzzle.solve_by_elimination();
		HashSet<Integer> replacement = new HashSet<Integer>();
		replacement.add(5);
		
		puzzle.possible.set(19, replacement);
		assertTrue(puzzle.possible.get(19).contains(5));
		
		prune_box(puzzle.possible, 0, 5);
		assertTrue(!puzzle.possible.get(19).contains(5));
		
		
		
	}
	
	@Test
	public void testPruneRow()
	{
		//ArrayList that will contain the Sets
		ArrayList<HashSet<Integer>> possibleValues = new ArrayList<HashSet<Integer>>();
		
		/*
		 * Loop that will create, initialize, and add 
		 * a HashSet to each set of the possibleValues ArrayList
		 * 
		 * Only adding 9 sets -- 1 row
		 */
		for (int setIndex = 0; setIndex < 9; setIndex ++)
		{
			//initialize HashSet
			HashSet<Integer> set = new HashSet<>();
			/*
			 * add number [1,2] to hash set 
			 * 
			 * Note - only addint 2 numbers for testing purposes
			 */
			for (int possibleNum = 1; possibleNum < 3; possibleNum ++)
			{
				set.add(possibleNum);
			}
			
			//add set to possibleValues ArrayList
			possibleValues.add(set);
		}
		
		//confirm that there are two elements in the sets for the row
		for (int rowIndex = 0; rowIndex < 9; rowIndex++)
		{
			assertTrue(possibleValues.get(rowIndex).size() == 2);
		}
		
		/*
		 * pruning a value from the sets that doesn't exist. Shows that
		 * the method doesn't cause problems if the value isn't in the set
		 */
		Sudoku.prune_row(possibleValues, 0, 9);
		
		//confirm that there are two elements in the sets for the row
		for (int rowIndex = 0; rowIndex < 9; rowIndex++)
		{
			assertTrue(possibleValues.get(rowIndex).size() == 2);
		}
		
		/*
		 * prune value from row that DOES exist. All HashSets
		 * will change in size except for the index that was pruned. 
		 */
		Sudoku.prune_row(possibleValues, 0, 1);
		
		/*
		 * confirm that the HashSets for each index in possibleValues row
		 * (except for element 1) has a size of 1. 
		 */
		for (int rowIndex = 0; rowIndex < 9; rowIndex++)
		{	
			
				assertTrue(possibleValues.get(rowIndex).size() == 1);
			
			
		}
		
	}
	/**
	 * This test creates an ArrayList of HashSets, puts a value in 
	 * in an index that corresponds to a HashSet.
	 * We then call the prune_column method on a column that should have
	 * the value we put in. We then make sure that the method has removed that value
	 * from the box.
	 */
	@Test
	public void testPruneCol()
	{
		
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader(new File("p1.txt")));
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		Sudoku puzzle = new Sudoku(br);
		puzzle.solve_by_elimination();
		HashSet<Integer> replacement = new HashSet<Integer>();
		replacement.add(5);
		
		puzzle.possible.set(27, replacement);
		assertTrue(puzzle.possible.get(27).contains(5));
		
		prune_column(puzzle.possible, 0, 5);
		assertTrue(!puzzle.possible.get(27).contains(5));
	}

	/**
	 * This test creates an ArrayList of HashSets, puts multiple values in 
	 * at different indices that correspond to a HashSets, which are all in the same
	 * row, column, and box.
	 * We then call the prune method on a position and value that should have
	 * the value in that row, box, and column. We then make sure that the method has removed that value
	 * from the row, box and column.
	 */
	@Test
	public void testPrune()
	{
		
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader(new File("p1.txt")));
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		Sudoku puzzle = new Sudoku(br);
		puzzle.solve_by_elimination();
		HashSet<Integer> replacement = new HashSet<Integer>();
		replacement.add(5);
		
		puzzle.possible.set(27, replacement);
		puzzle.possible.set(19, replacement);
		puzzle.possible.set(2, replacement);
		
		assertTrue(puzzle.possible.get(27).contains(5));
		assertTrue(puzzle.possible.get(19).contains(5));
		assertTrue(puzzle.possible.get(2).contains(5));
		prune(puzzle.possible, 0, 5);
		assertTrue(!puzzle.possible.get(27).contains(5));
		assertTrue(!puzzle.possible.get(19).contains(5));
		assertTrue(!puzzle.possible.get(2).contains(5));
		//FIXME
	}


	@Test
	public void testConvertRowAndColToIndex()
	{
		int expected = 0;
		int result = Sudoku.convertRowAndColumnToIndex(0, 0);

		assertTrue(expected == result);

		expected =80;
		result = Sudoku.convertRowAndColumnToIndex(8, 8);

		assertTrue(expected == result);
	}

	/**
	 * Test showing that the convertRowAndColToIndex has 
	 * to have valid parameters
	 */
	@Test
	public void testConvertRowAndColToIndexBounds()
	{
		int result = Sudoku.convertRowAndColumnToIndex(9, 5);
		assertTrue(result == -1);

		result = Sudoku.convertRowAndColumnToIndex(-1, 5);
		assertTrue(result == -1);

		result = Sudoku.convertRowAndColumnToIndex(5, 9);
		assertTrue(result == -1);

		result = Sudoku.convertRowAndColumnToIndex(5, -1);
		assertTrue(result == -1);
	}


	/**
	 * test that the determine2DLocation method gives proper row and
	 * column values
	 */
	@Test
	public void testDetermine2DLocation()
	{
		int[] expected = {0,0};
		int[] result = Sudoku.determine2DLocation(0);
		
		assertArrayEquals(expected, result);
		
		int[] expected2 = {8, 8};
		result = Sudoku.determine2DLocation(80);
		
		assertArrayEquals(expected2, result);
	}

	
	/**
	 * Test that helper method returns negative array
	 * if proved illegal bounds
	 */
	@Test
	public void testDetermine2DLocationBounds()
	{
		int[] expected = {-1, -1};
		int[] result = Sudoku.determine2DLocation(-1);
		
		assertArrayEquals(expected, result);
		
		result = Sudoku.determine2DLocation(81);
		
		assertArrayEquals(expected, result);
	}
	
	@Test
	public void testDetermineBoxNumber()
	{
		int expected = 0;
		int result = Sudoku.determineBoxNumber(0, 0);
		
		assertTrue(expected == result);
		
		expected = 8;
		result = Sudoku.determineBoxNumber(8, 7);
	}
	


}




