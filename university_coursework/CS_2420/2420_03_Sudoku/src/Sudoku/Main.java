package Sudoku;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class Main
{

	public static void main(String[] args) 
	{

		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File("p1.txt")));
			
			
			System.out.println("Recursive Solver: ");
			Sudoku puzzle = new Sudoku(br);
			puzzle.solve_sudoku();
			System.out.println(puzzle.toString());
			
			System.out.println();
						
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		try
		{
			
			System.out.println("Elimination Solver: (does not completely solve puzzle)");
			BufferedReader br = new BufferedReader(new FileReader(new File("p1.txt")));
			
			Sudoku puzzle2 = new Sudoku(br);
			puzzle2.solve_by_elimination();
			
			System.out.println(puzzle2.toString());
			
			System.out.println();
			
			System.out.println("Print Possibilities Method: ");
			Sudoku.print_possibilities(puzzle2.possible);
						
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		
				
	}
}
