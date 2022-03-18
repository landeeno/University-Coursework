package Sudoku;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Sudoku
{

	/**
	 * Declare fields here as needed.
	 *
	 * You may choose to store your internal data as either a 1D array or a 2D
	 * array. The 1D array will make the recursive solver slightly easier to
	 * write (why?). The 2D array might make traversing the puzzle slightly
	 * easier (why?).
	 */

	public int[] puzzleValues = new int[81];
	protected int recursiveGuessCount = 0;

	public int eliminationGuessCount = 0;

	protected boolean isPozzleSolved = false;

	protected double marginalPuzzleProgress;
	public ArrayList<HashSet<Integer>> possible;

	/**
	 * Create a new puzzle by reading a file
	 *
	 * the file should be 9 rows of 9 numbers separated by whitespace the
	 * numbers should be 1-9 or 0 representing an empty square
	 *
	 * Improper format (too many numbers per line, no numbers, too many total
	 * numbers, etc) should result in a runtime exception being thrown.
	 *
	 */
	public Sudoku(BufferedReader reader) 
	{

	
		try {
		Scanner scanner = new Scanner(reader);

		// loop which adds each value of sudoku file to a puzzleValues array

		for (int puzzleIndex = 0; puzzleIndex < 81; puzzleIndex++)
		{
			puzzleValues[puzzleIndex] = scanner.nextInt();

		}
		
		if (scanner.hasNextInt() == true)
		{
			scanner.close();
			throw new RuntimeException();
		}

		scanner.close();
		} catch (RuntimeException e) {
			e.fillInStackTrace();
		}


	}


	public Sudoku()
	{

	}

	/**
	 * @return a copy of the puzzle as a 1D matrix
	 */
	public int[] get_puzzle_1D()
	{
		int[] puzzleValuesCopy = puzzleValues;
		return puzzleValuesCopy;
	}

	/**
	 * @return a copy of the puzzle as a 2D matrix
	 */
	public int[][] get_puzzle_2D()
	{
		int[] puzzleValuesCopy = puzzleValues;
		int[][] array2D = new int[9][9];
		// index that will keep track of the puzzle in 1D
		int index = 0;
		/*
		 * double for loop which essentially moves the puzzle value entries from
		 * a singular array to a 2D array by moving values to the next row after
		 * 9 entries
		 */
		for (int row = 0; row < 9; row++)
		{
			for (int col = 0; col < 9; col++)
			{
				array2D[row][col] = puzzleValuesCopy[index];
				index++;
			}
		}

		return array2D;
	}

	/**
	 * @return how many guesses it took to recursively solve the problem.
	 */
	public int get_guess_count()
	{
		return this.recursiveGuessCount;
	}

	/**
	 * Function: valid_for_row
	 *
	 * Description: Determine if the given number can be placed in the given row
	 * without violating the rules of sudoku
	 *
	 * Inputs:
	 * 
	 * @input row : which row to see if the number can go into
	 * @input number: the number of interest
	 *
	 *        Outputs:
	 *
	 *        true if it is possible to place that number in the row without
	 *        violating the rule of 1 unique number per row.
	 *
	 *        In other words, if the given number is already present in the row,
	 *        it is not possible to place it again (return false), otherwise it
	 *        is possible to place it (return true);
	 * 
	 *        WARNING: call this function before placing the number in the
	 *        puzzle...
	 *
	 */
	protected boolean valid_for_row(int row, int number)
	{

		if (row > 8 || row < 0)
		{
			return false;
		}

		int startingIndex = row * 9;

		for (int loopIndex = startingIndex; loopIndex < startingIndex + 9; loopIndex++)
		{
			if (puzzleValues[loopIndex] == 0)
			{
				continue;
			}
			if (puzzleValues[loopIndex] == number)
				return false;
		}

		// return true if number parameter doesn't break sudoku rules
		return true;
	}

	/**
	 * Function: valid_for_col (see above)
	 */
	protected boolean valid_for_column(int col, int number)
	{

		if (col > 8 || col < 0)
		{
			return false;
		}

		int startingIndex = col;
		for (int loopIndex = startingIndex; loopIndex < 81; loopIndex = loopIndex + 9)
		{
			if (puzzleValues[loopIndex] == 0)
			{
				continue;
			}

			if (puzzleValues[loopIndex] == number)
				return false;
		}

		// return true if number parameter doesn't break sudoku rules
		return true;
	}

	/**
	 * Function: valid_for_box (see above)
	 *
	 * The sudoku boxes are:
	 *
	 * 0 | 1 | 2 
	 * --+---+--
	 * 3 | 4 | 5 
	 * --+---+---
	 * 6 | 7 | 8
	 *
	 * where each box represents a 3x3 square in the game.
	 *
	 * 
	 * This method utilizes a switch statement
	 *
	 *
	 */
	protected boolean valid_for_box(int box, int number)
	{

		if (box > 8 || box < 0)
		{
			return false;
		}

		/*
		 * initialize row and column variables. These values are reassigned when
		 * later, as the "box" parameter goes into a switch statement, and the
		 * values for "row" and "col" are reassigned to the proper box.
		 * 
		 * Boxes are mapped as follows:
		 * 
		 * 0 | 1 | 2 
		 * --+---+--- 
		 * 3 | 4 | 5 
		 * --+---+---
		 * 6 | 7 | 8
		 * 
		 * 
		 */
		int row = -1;
		int col = -1;

		switch (box)
		{
		case 0:
			row = 0;
			col = 0;
			break;
		case 1:
			row = 0;
			col = 3;
			break;
		case 2:
			row = 0;
			col = 6;
			break;
		case 3:
			row = 3;
			col = 0;
			break;
		case 4:
			row = 3;
			col = 3;
			break;
		case 5:
			row = 3;
			col = 6;
			break;
		case 6:
			row = 6;
			col = 0;
			break;
		case 7:
			row = 6;
			col = 3;
			break;
		case 8:
			row = 6;
			col = 6;
			break;
		}

		/*
		 * row and col have been assigned. This loop iterates through the
		 * appropriate values in the puzzleValues array to ensure that the
		 * original "number" parameter is not already in the specified box.
		 */
		for (int rowIndex = row; rowIndex < row + 3; rowIndex++)
		{
			for (int colIndex = col; colIndex < col + 3; colIndex++)
			{



				// determine index in 1D array from row and column values
				int index = convertRowAndColumnToIndex(rowIndex, colIndex);

				if (puzzleValues[index] == 0)
				{
					continue;
				}

				if (puzzleValues[index] == number)
					return false;
			}
		}
		// return true if number parameter doesn't break sudoku rules
		return true;
	}

	/**
	 *
	 * Function is_valid( position, value )
	 *
	 * Determine if the given value is valid in the puzzle at that position.
	 *
	 * Inputs:
	 * 
	 * @param position
	 *            - which bucket in the puzzle to check for validity - should be
	 *            empty
	 * @param possible_value
	 *            - the value to check (1-9)
	 * 
	 * @return true if valid
	 */
	protected boolean is_valid(int position, int possible_value)
	{

		if (position < 0 || position > 80)
		{
			return false;
		}

		int[] coordinates = determine2DLocation(position);
		int row = coordinates[0];
		int col = coordinates[1];

		int boxNumber = determineBoxNumber(row, col);

		// make sure that the "possible_value" parameter checks all three tets
		// (column, row, box)
		if ((valid_for_row(row, possible_value) == true) && (valid_for_column(col, possible_value) == true)
				&& (valid_for_box(boxNumber, possible_value) == true))
		{
			// test passed: return true
			return true;
		} 
		else
		{
			//tests failed: return false
			return false;
		}

	}

	/**
	 * solve the sudoku problem
	 * 
	 * @return true if successful
	 */
	public boolean solve_sudoku()
	{
		//initiate recursive method
		return solve_sudoku(0);
	}

	/**
	 *
	 * Function solve_sudoku( puzzle, position )
	 *
	 * Recursively check to see if the puzzle can be solved following class
	 * algorithm
	 *
	 * Inputs: position - the index of the "current" box in the array (should be
	 * set to 0 by initial call)
	 *
	 */
	public boolean solve_sudoku(int position)
	{

		if (position > 81 || position < 0)
		{
			return false;
		}

		//base case: method has checked every cell and puzzle is valid
		if ( (position == 81)  )
		{
			this.isPozzleSolved = true;
			return true;
		}

		/*
		 * skip over this "position" if it is already occupied by a 
		 * number other than 0 (aka not empty)
		 */
		if (puzzleValues[position] != 0)
		{
			return solve_sudoku(position + 1);
		}

		/*
		 * for loop that cycles through values 1-9 and checks to see
		 * if they can be placed in the indicated cell without
		 * breaking Sudoku rules
		 */
		for (int possibleNumber = 1; possibleNumber < 10; possibleNumber++)
		{

			/*
			 * if possibleNumber can be placed in cell, try it 
			 * and initiate recursive method. If not, skip to the
			 * next number in the possibleNumber loop. 
			 */
			if ( is_valid(position, possibleNumber) == false )
			{
				/*
				 * possibleNumber CANNOT be placed in this cell
				 * skip to next number guess
				 */
				continue;
			} 
			else
			{
				/*
				 * possible number CAN be placed in this cell. Do the following:
				 * 
				 * 1) place number in cell
				 * 2) update the guessCount for recursive solver
				 * 3) continue on with recursive solver and update guess
				 * 
				 */
				puzzleValues[position] = possibleNumber;
				recursiveGuessCount++;

				/*
				 * check to see if the rest of the puzzle can be 
				 * solved with this guess
				 */
				if (solve_sudoku(position + 1) == true )
				{
					//recursion made it to cell 81: return true
					return true;
				} 

				//recursion didn't make it to cell 81
				else
				{
					/*
					 * reset the cell to "empty" so that theo
					 * outer loop can try the next possibleNumber
					 * in this cell 					
					 */
					puzzleValues[position] = 0;
				}
			}

		}
		//something went wrong, puzzle couldn't be solved. 
		return false;
	}

	/**
	 * Function: toString( )
	 *
	 * @return a string showing the state of the board
	 * 
	 *         toString returns a string of the puzzle in the following format:
	 * 
	 *         0 0 0 | 0 0 0 | 0 0 0 
	 *         0 0 0 | 0 0 0 | 0 0 0 
	 *         0 0 0 | 0 0 0 | 0 0 0
	 *         ------+-------+------ 
	 *         0 0 0 | 0 0 0 | 0 0 0 
	 *         0 0 0 | 0 0 0 | 0 0 0
	 *         0 0 0 | 0 0 0 | 0 0 0 
	 *         ------+-------+------ 
	 *         0 0 0 | 0 0 0 | 0 0 0
	 *         0 0 0 | 0 0 0 | 0 0 0 
	 *         0 0 0 | 0 0 0 | 0 0 0
	 * 
	 *
	 */
	@Override
	public String toString()
	{
		// initialize result String
		String result = "";
		// intermediate string that will be used to display the major "grid"
		String intermediate = "------+-------+------ \n";

		// internal "counter" to keep track of when to put the "|" character
		int threeVals = 0;

		for (int puzzleIndex = 0; puzzleIndex < 81; puzzleIndex++)
		{
			int currentNumber = puzzleValues[puzzleIndex];

			// add a new line if we're at the end of the row
			if ((puzzleIndex % 9 == 0) && (puzzleIndex != 0))
			{
				result = result + "\n";
			}

			// add the "intermediate" string if we've made three rows
			if ((puzzleIndex % 27 == 0) && (puzzleIndex != 0))
			{
				result = result + intermediate;
			}

			// add next number and a space to the result string
			result = result + currentNumber + " ";
			// update the counter for the "|" character
			threeVals++;

			// if statement that determines if the "|" character should be
			// placed or not
			if ((threeVals == 3))
			{

				// check to make sure that the puzzleIndex isn't at the end of a
				// row
				if (determine2DLocation(puzzleIndex)[1] != 8)
				{
					// add "|" if puzzle index is NOT at end of row
					result = result + "| ";
				}
				// reset counter
				threeVals = 0;
			}
		}
		// string is complete
		return result;

	}

	/**
	 * Given a puzzle (filled or partial), verify that every element does not
	 * repeat in row, col, or box.
	 * 
	 * @return true if a validly solved puzzle
	 */
	public boolean verify()
	{

		//loop through every index in puzzle
		for (int sudokuPuzzleIndex = 0; sudokuPuzzleIndex < 81; sudokuPuzzleIndex++)
		{

			// determine value of puzzle, row, column, and box number
			int testNumber = puzzleValues[sudokuPuzzleIndex];

			puzzleValues[sudokuPuzzleIndex] = 0;

			// if any of the "valid_for" tests fail, return false
			if ( (is_valid(sudokuPuzzleIndex, testNumber)) == false )
			{
				return false;
			}

			puzzleValues[sudokuPuzzleIndex] = testNumber;

		}

		// all the tests passed; return true.
		return true;

	}

	/**
	 * Attempt to solve a sudoku by eliminating obviously wrong values Algorithm
	 *
	 * 1) build a 81 (representing 9x9) array of sets 2) put a set of 1-9 in
	 * each of the 81 spots 3) do initial elimination for each known value,
	 * eliminate nubmers from sets in same row, col, box eliminate all values in
	 * the given square 4) while progress is being made (initially true) find a
	 * non-processed square with one possible answer and eliminate this number
	 * from row, col, box
	 */
	public void solve_by_elimination()
	{
		// initialize ArrayList of sets instance variable
		possible = new ArrayList<HashSet<Integer>>();

		double initialNumberOfValuesInPuzzle = 0;

		/*
		 * numbersInSudoke is the counter that keeps track of the progerss
		 * of the elimination solver. If the "numbersInSudoku" variable
		 * reaches 81, every cell in the puzzle is filled. 
		 */
		double numbersInSudoku = 0;

		//loop through entire puzzle
		for (int puzzleIndex = 0; puzzleIndex < 81; puzzleIndex++)
		{
			//initialize HashSet which will contain cell possibilities
			HashSet<Integer> setOfPossibleValues = new HashSet<Integer>();
			//add HashSet to each element in possibleValues ArrayList
			possible.add(setOfPossibleValues);

			/*
			 * if the puzzle index already contains a number, add 
			 * that number to the hash set and skip to the next number.
			 * 
			 * There is no point in adding possible values 1-9 to a set
			 * that is already filled.  Instead, just add the single number
			 * that the puzzle already contains so that it can be pruned
			 * in a future step.
			 */
			if (puzzleValues[puzzleIndex] != 0)
			{
				initialNumberOfValuesInPuzzle++;
				//add the puzzle value to the HashSet
				setOfPossibleValues.add(puzzleValues[puzzleIndex]);
				//break out of loop and move onto next cell index
				continue;
			}

			/*
			 * the puzzle contains a 0 at this cell. Fill it's corresponding
			 * HashSet with values 1-9, as these are the possible values
			 * for this cell at this point. 
			 */
			for (int j = 1; j < 10; j++)
			{
				//adding values 1-9
				setOfPossibleValues.add(j);

			}
		}

		/*
		 * variable for while loop that determines if the 
		 * loop should continue.  
		 */
		boolean progressMade = true;

		//enter loop
		while (progressMade)
		{
			/*
			 * set progressMade to false. This way the loop will 
			 * exit if no progress is being made. 
			 */
			progressMade = false;

			//This loop does the "heavy lifting" of the elimination method:
			for (int cellIndex = 0; cellIndex < 81; cellIndex++)
			{
				//retrieve the Set of values at the specified inedex
				HashSet<Integer> arrayListSet = possible.get(cellIndex);

				/*
				 * check the size of the Set. If the size is greater than 1, then
				 * there are multiple values and nothing can be done with this 
				 * basic solver.
				 * If the size of the set is 1, then we know that either
				 * the cell had a number from the original puzzle, or it has been
				 * pruned and other possibilities have been eliminated
				 */
				if (arrayListSet.size() == 1)
				{
					//determine which number is in the hash set
					int singleNumber = retrieveNumberFromHashSet(arrayListSet);
					//update the puzzle by setting this cell to 
					//whichever number was in the Set
					puzzleValues[cellIndex] = singleNumber;
					//update the counter
					numbersInSudoku++;

					/*
					 * A value of the puzzle has just been placed, therefore
					 * the surrounding row, column, and box sets need to be pruned
					 * so that other possibilites can be eliimanted. This is the
					 * logic side of Sudoku
					 */
					prune(possible, cellIndex, singleNumber);
					//set progressMade boolean to true so that the loop whill continue. 
					progressMade = true;

				}

				//validity check to ensure the puzzle is complete
				if ( (numbersInSudoku == 81) ) 
				{
					//break out of method: puzzle is complete
					this.isPozzleSolved = true;
					break;
				}

			}

		}

		double percentIncrease = (numbersInSudoku - initialNumberOfValuesInPuzzle)
				/(81-initialNumberOfValuesInPuzzle);
		this.marginalPuzzleProgress = 100*percentIncrease;


	}

	/**
	 * helper method to see retrieve single value from HashSet.
	 * 
	 * Because the HashSet API doesn't work with indices or have a 
	 * "getValue()" type of method, we had to be a bit creative.
	 * 
	 * Because we know that the HashSet will ONLY contain numbers
	 * 1-9, we can loop through all elements in the Set and use
	 * the contains() method.
	 * 
	 * Note -- this method will only be called if the cell sized 
	 * has confirmed to be 1, so we are protected from the possibility
	 * of having multiple values in the Set
	 * 
	 * @param set - the HashSet to search
	 * @return the single value in that HashSet
	 */
	protected static int retrieveNumberFromHashSet(HashSet<Integer> set)
	{
		//list if possible numbers the hash set could contain
		for (int possibleNum = 1; possibleNum < 10; possibleNum++)
		{
			//check to see if the Set contains the possible number
			if (set.contains((Integer) possibleNum))
			{
				return possibleNum;
			}
		}

		/*
		 * return -1 if the Set does not contain 1-9. Should never happen.
		 */
		return -1;

	}

	/**
	 * print a grid showing all possible valid answers This should be a 27x27
	 * matrix.
	 *
	 * I would suggest you do this first to get good debugging help
	 * 
	 * @param possibilities
	 *            - array list of all the sets of 1-9s
	 */
	public static void print_possibilities(ArrayList<HashSet<Integer>> possibilities)
	{
		int[][] bigArray2D = new int[27][27];
		String possibleString = "";

		for (int row = 0; row < 27; row++)
		{
			for (int col = 0; col < 27; col++)
			{
				int currentCell = determineBoxNumber(row, col);
				HashSet<Integer> cell = possibilities.get(currentCell);

				// get row and column in cell
				int cellRow = row % 3;
				int cellCol = col % 3;
				int cellValue = cellRow * 3 + cellCol + 1;
				if (cell.contains(cellValue))
				{
					bigArray2D[row][col] = cellValue;
				}
			}
		}
		for (int row = 0; row < 27; row++)
		{
			if (row % 9 == 0 && row != 0)
			{
				possibleString += " ----------------------------------------------------"
						+ "------------------------------";
			}
			if (row % 3 == 0)
			{
				possibleString += "\n";
			}
			for (int col = 0; col < 27; col++)
			{
				if (col == 0)
				{
					possibleString += "[";
				} else if (col % 3 == 0 && col % 9 != 0)
				{
					possibleString += "] [";
				} else if (col % 9 == 0)
				{
					possibleString += "] \t|\t [";
				}

				if (bigArray2D[row][col] == 0)
				{
					possibleString += " ";
				} else
				{
					possibleString += bigArray2D[row][col];
				}

			}

			possibleString += "]\n";
		}
		System.out.println(possibleString);
	}

	/**
	 * Given a possibility constraint matrix (81 sets of [1-9]) remove the given
	 * number from every matrix in the given box
	 * 
	 * @param possibilities
	 *            - 81 sets of [1-9]
	 * @param position
	 *            - where the value exists (transform to row,col)
	 * @param value
	 *            - the value to prune
	 */
	protected static void prune_box(ArrayList<HashSet<Integer>> possibilities, int position, Integer value)
	{

		// determine box number from position value
		int[] coordinates = determine2DLocation(position);
		int row = coordinates[0];
		int col = coordinates[1];
		int boxNumber = determineBoxNumber(row, col);

		/*
		 * Cycle through every element in the ArrayList, and determine the box number
		 * if the box number is the same as the box number from the parameter, then 
		 * remove the value parameter if the Set in that box contains it. 
		 * 
		 * Note -- this isn't the most effective way of performing this action,
		 * but because it is fine in this case because the amount of indices the loop
		 * is working with is relatively small. 
		 */
		for (int puzzleLoopIndex = 0; puzzleLoopIndex < 81; puzzleLoopIndex++)
		{
			//determine box number from loopIndex
			int[] coords = determine2DLocation(puzzleLoopIndex);
			int rowI = coords[0];
			int colI = coords[1];
			int boxNumberI = determineBoxNumber(rowI, colI);

			//check to see if the value the cell from the puzzleLoopIndex
			//has the same box number as the original parameter. Remove if so.
			if (boxNumber == boxNumberI)
			{
				possibilities.get(puzzleLoopIndex).remove(value);
			}
		}
	}

	/**
	 * Function convertRowAndColumnToIndex( row, col )
	 * 
	 * This helper method takes a set of row and column values that point to a
	 * particular cell in the Sudoku puzzle, and returns the proper
	 * 1-dimensional index value for those indices.
	 *
	 */
	protected static int convertRowAndColumnToIndex(int row, int col)
	{

		//check to make sure row and col are valid
		if (row > 8 || row < 0 || col > 8 || col < 0)
		{
			return -1;
		}

		//determine index number based on row and column indices
		return (9 * row) + col;
	}

	/**
	 * 	 * Given a possibility constraint matrix (81 sets of [1-9]) remove the given
	 * number from every matrix in the given column
	 * 
	 * @param possibilities
	 *            - 81 sets of [1-9]
	 * @param position
	 *            - where the value exists (transform to row,col)
	 * @param value
	 *            - the value to prune
	 */
	protected static void prune_column(ArrayList<HashSet<Integer>> possibilities, int position, Integer value)
	{
		//determine the column based on the position parameter
		int[] coordinates = determine2DLocation(position);
		int col = coordinates[1];

		//loop in incraments of 9, so the loop cycles through a whole column
		for (int loopIndex = col; loopIndex < 81; loopIndex += 9)
		{
			/*
			 * check to see if the associated Sets contain the 
			 * value we are trying to remove, and act accordingly
			 */
			if (possibilities.get(loopIndex).contains(value))
			{
				possibilities.get(loopIndex).remove(value);
			}
		}
	}

	/**
	 * 	 * Given a possibility constraint matrix (81 sets of [1-9]) remove the given
	 * number from every matrix in the given row
	 * 
	 * @param possibilities
	 *            - 81 sets of [1-9]
	 * @param position
	 *            - where the value exists (transform to row,col)
	 * @param value
	 *            - the value to prune
	 */
	protected static void prune_row(ArrayList<HashSet<Integer>> possibilities, int position, Integer value)
	{

		//determine row from "position" parameter
		int[] coordinates = determine2DLocation(position);
		int row = coordinates[0];

		//the row is known, but the indices won't match up without multiplying row by 9
		int index = 9 * row;

		for (int loopIndex = index; loopIndex < index + 9; loopIndex++)
		{
			//remove given number from every cell in given row
			if (possibilities.get(loopIndex).contains(value))
			{
				possibilities.get(loopIndex).remove(value);
			}
		}

	}

	/**
	 * Helper method that combines the prune_row, prune_column, and prune_box into one method. 
	 * @param possibilities
	 *            - 81 sets of [1-9]
	 * @param position
	 *            - where the value exists (transform to row,col)
	 * @param value
	 *            - the value to prune
	 */
	protected static void prune(ArrayList<HashSet<Integer>> possibilities, int position, Integer value)
	{
		prune_box(possibilities, position, value);
		prune_row(possibilities, position, value);
		prune_column(possibilities, position, value);
	}


	/**
	 * Helper Method: given an index in the puzzle, return the row
	 * and column of that index 
	 * 
	 * @param index - value from [0-80] resembling location in array
	 * @return coordinates - an array of 2 numbers where coordinates[0]
	 * represents the row and coordinates[1] represents the column
	 */
	public static int[] determine2DLocation(int index)
	{

		if (index > 80 || index < 0)
		{
			int[] negativeReturn = {-1, -1};
			return negativeReturn;
		}
		//determine row and col
		int column = index % 9;
		int row = (index - column) / 9;

		//form return array
		int[] coordinates = { row, column };

		return coordinates;

	}


	public static int[] determine3by3Location(int index)
	{
		int column = index % 3;
		int row = (index - column) / 3;

		int[] coordinates = { row, column };
		return coordinates;

	}

	/**
	 * Helper method that returns the box number based on the row and 
	 * column of a specific cell
	 * @param row
	 * @param col
	 * @return value [0-8] which corresponds to the box number of that cell
	 * 
	 * (see above valid_for_box method for box mapping)
	 */
	public static int determineBoxNumber(int row, int col)
	{



		//convert from 9x9 system to 3x3 system
		int rowBox = row / 3;
		int colBox = col / 3;

		//calculate and return box number
		return (3 * rowBox) + colBox;
	}

	//end of class
}
