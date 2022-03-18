
package sort_evaluations;

import java.util.ArrayList;

import javax.sound.sampled.LineEvent.Type;

/**
 * Parker Stewart
 * Landon Crowther
 * February 16, 2017
 * test quick sort just choosing the first value as the pivot. 
 */
public class Quick_Sort_Inplace_First_Pivot <Type extends Comparable<? super Type>> extends Quick_Sort<Type>
{

	/**
	 * First_Pivot
	 * 
	 * 1) choose the first element in the array as the pivot
	 * 2) Place this element at array[end]
	 * 
	 * @param array          = the array with all the data (we sort a sub piece of the array)
	 * @param start          = index of start of array
	 * @param end            = index of end of array
	 */
	@Override
	protected Type choose_pivot( ArrayList<Type> array, int start, int end )
	{
		Type pivot = array.get(start);
		
		Sorter.swap(array, start, end);
		
		return pivot;
	}

	/**
	 * Name the sort
	 */
	public String name_of_sort()
	{
		return "Quick Sort First Pivot (Cutoff: " + super.insertion_cutoff +")"; 
	}


}
