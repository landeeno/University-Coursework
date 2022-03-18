
package sort_evaluations;

import java.util.ArrayList;

/**
 * Parker Stewart
 * Landon Crowther
 * February 16, 2017
 * 
 * This code is an abstract base class for all versions of quicksort.
 * 
 * 
 *  Write Quick sort using a single array(list) and doing it in place 
 * 
 *  Instrument it to allow the changing of the Insertion Sort Switch over
 * 
 */
public abstract class Quick_Sort  <Type extends Comparable<? super Type>> implements Sorter<Type>
   
   
{
	/**
	 * create a field for the insertion sort switch over level
	 */
	
	protected double insertion_cutoff = 1; 

	/**
	 *  Choose a Pivot (return it's value in the array)
	 *  Modify array as appropriate (e.g., median of three will move smallest value to front of array)
	 *
	 * @param array - the array of all values (we only sort a sub piece of the array)
	 * @param start - the start position in the (sub) array
	 * @param end   - the end position in the (sub) array
	 * @return the pivot value
	 */
	protected abstract Type  choose_pivot( ArrayList<Type> array, int start, int end );
	

	/**
	 * Given an array, partition the array such that
	 * all the elements lower than or equal to the pivot are on the left,
	 * all the elements greater than the pivot are on the right.
	 * 
	 * Works when the pivot is at the end
	 * 
	 * @param array   - data data to sort
	 * @param left    - the start index of the sub array (inclusive)
	 * @param right   - the end index of the sub array (inclusive)
	 * 
	 * @return the location of the pivot
	 */

	protected int partition( ArrayList<Type> array, int left, int right ) 
	{
		
		Type pivot = choose_pivot(array,left,right);
		
		
		int i = left; 
		int j = right - 1; // we know the pivot is at the end of the array
		while(i<=j)
		{
			while(array.get(i).compareTo(pivot) < 0 && i <= j)
			{
				i++;
			}
			while(array.get(j).compareTo(pivot) >= 0 && j > i)
			{
				j--;
			}
			
			if(i>=j)
			{
				break;
			}
			Sorter.swap(array, i, j);
			
		}
		
		Sorter.swap(array, i, right);
		
		return i;
	}

	/**
	 * The actual Quick Sort on an Array routine.
	 * 
	 * Algorithm:
	 *   1) choose a pivot (store in last bucket for convenience)
	 *   2) o) move all elements higher than the pivot to the right side of the array
	 *      o) move all elements lower than the pivot to the left side of the array
	 *   3) put the pivot back between upper and lower group
	 *   4) sort left side
	 *   5) sort right side   (WARNING: don't sort pivot again)
	 * 
	 * @param array - data to be sorted
	 * @param start is the index of the beginning element
	 * @param end is the index of the last element
	 * 
	 * 
	 */
	private void quick_sort( ArrayList<Type> array, int start, int end )
	{
		
		if(end-start <= this.insertion_cutoff)
		{
			Sort_Utils.insertion_sort(array, start, end);
			return;
		}
		
		
		else
		{

			int pivot_index = this.partition(array, start, end);
			
			quick_sort(array, start, pivot_index-1);
			quick_sort(array, pivot_index+1, end);
			
		}
		
		
	}

	/**
	 * the sort interface method.
	 * 
	 * call quicksort on the array
	 * 
	 */
	
	public void sort( ArrayList<Type> array )
	{
		quick_sort(array, 0, array.size() -1 );
	}

	/**
	 * Name the sort
	 */
	public abstract String name_of_sort();

	/**
	 * The constant in this case is the insertion sort cutoff level... always greater than 3
	 */
	public void set_constant( double constant )
	{
		this.insertion_cutoff = constant;
	}
	
	/**
	 * @return the expected complexity for quick sort 
	 */
	@Override
	public Complexity_Class get_expected_complexity_class()
	{
		return Complexity_Class.NLogN;
	}




}
