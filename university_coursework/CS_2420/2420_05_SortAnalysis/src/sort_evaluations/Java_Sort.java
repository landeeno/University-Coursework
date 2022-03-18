
package sort_evaluations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Parker Stewart
 * Landon Crowther
 * February 16, 2017
 *         this code wraps JAVAs arraylist built in sort to see
 *         how it compares with our sorts
 * 
 */
public class Java_Sort<Type extends Comparable<? super Type>> implements Sorter<Type>
{

	/**
	 * implement this to sort the array using the arraylist built in sort and natural order comparator
	 */
	public void sort( ArrayList<Type> array )
	{
		array.sort(new Comparator<Type>()
		{
			@Override
			public int compare(Type leftHandSide, Type rightHandSide)
			{
				return leftHandSide.compareTo(rightHandSide);
			}
		});
	}

	/**
	 * return the Name the sort (in this case Java's sort)
	 */
	public String name_of_sort()
	{
		return "Java's sort";
	}

	/**
	 *  Nothing to do here. Print a message that you can't modify Java's sort algorithm
	 */
	public void set_constant( double constant )
	{
		System.out.println("You cannot modify Java's sort algorithm");
	}


	/**
	 * hypothesize what you think javas sort BIG O should be.
	 * @return the expected complexity for javas sorts
	 */
	@Override
	public Complexity_Class get_expected_complexity_class()

	{
		return Complexity_Class.NLogN; 
	}
	

}
