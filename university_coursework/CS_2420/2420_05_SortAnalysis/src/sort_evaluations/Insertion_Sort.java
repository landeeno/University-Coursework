package sort_evaluations;

import java.util.ArrayList;

/**
 *       
 *        Parker Stewart
 *        Landon Crowther
 *        February 16, 2017
 *        This class is the insertion sort class that sorts an array using an insertion sort
 */

public class Insertion_Sort <Type extends Comparable<? super Type>> implements Sorter<Type> 
{

	/**
	 * Returns the Name of the sort
	 */
	public String name_of_sort()
	{
		return "Insertion Sort";
	}

	/**
	 * No affect on insertion sort
	 * Simply print a debug message saying this is ignored
	 */
	public void set_constant( double constant )
	{
		System.out.println("This is ignored for an Insertion Sort");
	}

	/**
	 * Note: the actual insertion sort code should be written in the Sort_Utils package
	 * call that code in this method
	 * sort the entire array using insertion sort
	 */
	@Override
	public void sort(ArrayList<Type> array)
	{
		
		Sort_Utils.insertion_sort(array, 0, array.size()-1); 
		// hard coded zero because that is the start of the array
		
	}

	/**
	 * 
	 * @return the expected complexity of this algorithm
	 */
	@Override
	public Complexity_Class get_expected_complexity_class()
	{
		return Complexity_Class.NSquared;
	}


}
