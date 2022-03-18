package sort_evaluations;

import java.util.ArrayList;


/**
 * @author H. James de St. Germain
 * @date Spring 2017
 * 
 *       regular merge sort
 */
public class Merge_Sort  <Type extends Comparable<? super Type>> implements Sorter<Type>
{

	/**
	 * have a value for switching over to insertion sort
	 */
	protected double insertion_cutoff = 8; 

	/**
	 *  The name of the sort
	 */
	public String name_of_sort()
	{
		return "Merge Sort - Cutoff: " + this.insertion_cutoff;
	}

	/**
	 * Merge Sort
	 * 
	 * split array in half
	 * sort left
	 * sort right
	 * combine
	 * 
	 * 
	 * @param array          the values to sort from small to high
	 * @param low            the index of the starting value in the "virtual array"
	 * @param high           the index of the ending value in the "virtual array"
	 * 
	 */
	
	public void merge_sort(ArrayList<Type> array, ArrayList<Type> auxillary, int low, int high)
	{

		if (high - low + 1 <= this.insertion_cutoff)
		{
			Sort_Utils.insertion_sort(array, low, high);
			return;
		}

		int mid = (low + high) / 2;
		merge_sort(array, auxillary, low, mid);
		merge_sort(array, auxillary, mid + 1, high);
		combine(array, auxillary, low, mid + 1, high);

	}

	/**
	 * combine the values in array into the auxiliary
	 * 
	 * @param array
	 *            - original values (the entire array)
	 * @param auxillary
	 *            - spare space
	 * @param low
	 *            - low,mid are the lower array
	 * @param mid
	 *            - mid +1 ,high are the upper array
	 * @param high
	 * 
	 *            combine the sub arrays in the _array_ parameter. use the
	 *            _auxillary_ parameter for scratch space
	 */

	public void combine(ArrayList<Type> array, ArrayList<Type> auxillary, int low, int mid, int high)
	{

		int leftIndex = low;
		int rightIndex = mid;

		//auxillary will store all of the newly sorted numbers
		auxillary.clear();


		//check values in both "virtual" arrays, and add the lower of the two
		while (leftIndex < mid && rightIndex <= high)
		{
			if (array.get(leftIndex).compareTo(array.get(rightIndex)) < 0)
			{
				auxillary.add(array.get(leftIndex));
				leftIndex++;

			} else
			{
				auxillary.add(array.get(rightIndex));
				rightIndex++;
			}

		}

		/*
		 * all elements in right virtual array have been added to auxillary,
		 * add all the elements in left
		 * 
		 */
		while (leftIndex < mid)
		{
			auxillary.add(array.get(leftIndex));
			leftIndex++;
		}

		/*
		 * add all elements to auxillary that are in the right virtual array
		 */
		while (rightIndex <= high)
		{
			auxillary.add(array.get(rightIndex));
			rightIndex++;
		}

		/*
		 * re-arrange the values in the original array with the newly sorted
		 * auxillary array
		 */
		int auxillaryIndex = 0;
		for (int originalArrayIndex = low; originalArrayIndex <= high; originalArrayIndex++)
		{
			array.set(originalArrayIndex, auxillary.get(auxillaryIndex));
			auxillaryIndex++;
		}

		//end of method
	}




	/**
	 * Allow the insertion sort cut off to be changed
	 */
	public void set_constant( double cutoff )
	{
		this.insertion_cutoff = cutoff;
	}

	/**
	 * sort the array
	 */
	@Override
	public void sort( ArrayList<Type> array )
	{
		ArrayList<Type> auxillary = new ArrayList<Type>();
		this.merge_sort(array, auxillary, 0, array.size()-1); // hard coded in 0 because that is the first element
	}

	@Override
	public Complexity_Class get_expected_complexity_class()
	{
		return Complexity_Class.NLogN;
	}


}