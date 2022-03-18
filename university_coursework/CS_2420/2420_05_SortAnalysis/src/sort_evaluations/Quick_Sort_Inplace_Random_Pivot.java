
package sort_evaluations;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Parker Stewart
 * Landon Crowther
 * February 16, 2017
 * 
 * This class contains the pivot chooser method that picks a random pivot in the array list for
 * a quick sort
 */
public class Quick_Sort_Inplace_Random_Pivot <Type extends Comparable<? super Type>> extends Quick_Sort<Type>
{

	/**
	 * Random_Pivot
	 * 
	 * 1) Choose an element at random in the array and use it as pivot
	 * 2) Place this element at array[end]
	 * 
	 * @param array     = the array with all the data (we sort a sub piece of the array)
	 * @param start      = index of start of array
	 * @param end        = index of end of array
	 */
	@Override
	protected Type choose_pivot( ArrayList<Type> array, int start, int end )
	{
		int random_index = (int)(Math.random()*(end-start) + start);
		Type pivot = array.get(random_index);
		
		Sorter.swap(array, random_index, end);
		
		
		return pivot;
	}

	/**
	 * Name the sort
	 */
	public String name_of_sort()
	{
		return "Quick Sort Random Pivot (Cutoff: " + super.insertion_cutoff +")"; 
	}


}
