
package sort_evaluations;

import java.util.ArrayList;

import javax.lang.model.element.QualifiedNameable;

/**
 * Parker Stewart
 * Landon Crowther
 * February 16, 2017
 * @author H. James de St. Germain
 *
 *         This is a naive implementation of quicksort which uses copies of
 *         Arraylists instead of using a single array "in-place".
 * 
 *         This code should be much easier to "understand" than in-place manipulations
 * 
 *         WARNING: even though it extends quick sort, it DOES NOT use the super class
 *         implementation!
 *         
 *         Note: you must implement the quick_sort abstract class before this will work.
 * 
 * 
 */
public class Quick_Sort_Naive <Type extends Comparable<? super Type>> extends Quick_Sort<Type>
{

	/**
	 * start off with just using the first value as a pivot, but you should
	 * (after everything is working) implement median of three
	 */
	@Override
	protected Type choose_pivot( ArrayList<Type> array, int start, int end )
	{
		return array.get(0);
		// return median_of_three( array );
	}

	/**
	 * Median of Three (choose the middle element position)
	 * 
	 * returns the value of the middle element of the array list
	 */
	private Type median_of_three( ArrayList<Type> array )
	{
		return array.get((array.size()/2));
	}

	/**
	 * A "Naive" implementation of Quick Sort which uses extra memory
	 * 
	 * Algorithm:
	 * if array small enough, insertion sort it
	 * choose a pivot element (using Median of 3)
	 * put all the small elements in one array
	 * put all the large elements in one array
	 * put all the pivot elements in one array
	 * 
	 * quicksort large
	 * quicksort small
	 * 
	 * put small + pivots + larg back into array
	 */
	public void quick_sort_naive( ArrayList<Type> array )
	{
		/*
		 * Base Case
		 */
		 if (array.size() <= super.insertion_cutoff) // super class insertion cutoff value)
		{
			Sort_Utils.insertion_sort(array, 0, array.size()-1);
			return;
		}

		/*
		 * Some extra storage to make things easier
		 */
		ArrayList<Type> small_elements = new ArrayList<Type>();
		ArrayList<Type> large_elements = new ArrayList<Type>();
		ArrayList<Type> pivots = new ArrayList<Type>();

		/*
		 * Partition the list into three (small, pivot, large) lists
		 */
		Type pivot_value = this.median_of_three(array);

		for (int i = 0; i < array.size(); i++)
		{
			if (array.get(i).compareTo(pivot_value) < 0)
			{
				small_elements.add(array.get(i));
			}
			else if (array.get(i).equals((pivot_value)))
			{
				pivots.add(array.get(i));
			}
			else
			{
				large_elements.add(array.get(i));
			}
		}

		/*
		 * Quick Sort each half
		 */
		quick_sort_naive(small_elements);
		quick_sort_naive(large_elements);

		/*
		 * Put back together
		 */
		array.clear();
		array.addAll(small_elements);
		array.addAll(pivots);
		array.addAll(large_elements);

	}

	/**
	 * Name of Sort_Tester
	 */
	public String name_of_sort()
	{
		return "Quick Sort Naive (Cutoff: " + super.insertion_cutoff +")"; 
	}

	/**
	 * Convert the Type[] array into an array list and
	 * then call the appropriate sort routine!
	 * 
	 * @param array
	 *            the array of data to sort from small to large
	 */
	@Override
	public void sort( ArrayList<Type> array )
	{
		quick_sort_naive(array);
	}

}
