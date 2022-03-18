package sort_evaluations;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Parker Stewart
 * Landon Crowther
 * February 16, 2017
 *  use the median of three technique to compare vs random pivot selection, etc.
 */
public class Quick_Sort_Inplace_M3 <Type extends Comparable<? super Type>> extends Quick_Sort<Type>
{

	/**
	 * Median of Three (choose the middle element position)
	 * 
	 * WARNING: this not only chooses the pivot, but modifies the position of the three elements.
	 * 
	 * 1) Choose 3 elements from the array (start, middle, end)
	 * 2) Place the median element at array[end-1]
	 * 3) Place low element at array[start]
	 * 4) Place high element at array[end]
	 * 
	 * You shouldn't call this method when the array is smaller than 3 elements
	 * 
	 * @param array the array with all the data (we sort a sub piece of the array)
	 * @param start  = index of start of array
	 * @param end    = index of end of array
	 */
	@Override
	protected Type choose_pivot( ArrayList<Type> array, int start, int end )
	{
		
		int median_position = end-1;
		int mid = (start + end)/2;
		
		Type a = array.get(start);
		Type b = array.get(mid);
		Type c = array.get(end);


		// logic to determine the correct order 
		if ( a.compareTo(b) < 0 && a.compareTo(c) < 0 )
		{
			if (b.compareTo(c) < 0)
			{
				// a < b < c - already in proper order
				Sorter.swap(array, mid, median_position);
			}
			else
			{
				// a < c < b
				Sorter.swap(array, mid, end);
				Sorter.swap(array, mid, median_position);
			}
		}
		else if ( b.compareTo(a) < 0 && b.compareTo(c) < 0 )
		{
			if ( a.compareTo(c) < 0)
			{
				// b < a < c
				Sorter.swap(array , start, mid);
				Sorter.swap(array, mid, median_position);
				
			}
			else
			{
				// b < c < a
				Sorter.swap(array, start, end);
				Sorter.swap(array, mid, start);
				Sorter.swap(array, mid, median_position);
			}
		}
		else //C must be lowest
		{
			if (a.compareTo(b) < 0)
			{
				// c < a < b
				Sorter.swap(array, end, start);
				Sorter.swap(array, end, mid);
				Sorter.swap(array, mid, median_position);
			}
			else
			{
				// c < b < a
				Sorter.swap(array, start, end);
				Sorter.swap(array, mid, median_position);
			}
		}
		
		return array.get(median_position);

	}
	
	
	

	/**
	 * Given an array, partition the array such that
	 * all the elements lower than or equal to the pivot are on the left,
	 * all the elements greater than the pivot are on the right.
	 * 
	 * For a median of 3, the pivot is placed at end-1 when passed into this method
	 * This method overrides the partition method in quick sort, and has an extra step
	 * which moves the pivot to the end
	 * 
	 * @param array   - data data to sort
	 * @param left    - the start index of the sub array (inclusive)
	 * @param right   - the end index of the sub array (inclusive)
	 * 
	 * @return the location of the pivot
	 */
	
	@Override
	protected int partition( ArrayList<Type> array, int left, int right ) 
	{
		
		Type pivot = choose_pivot(array,left,right);
		Sorter.swap(array, right,right-1);
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
	 * Name the sort
	 */
	public String name_of_sort()
	{
		return "Quick Sort Median of 3 Pivot (Cutoff: " + super.insertion_cutoff +")"; 
	}


}
