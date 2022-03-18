
package sort_evaluations;

import java.util.ArrayList;

/**
 * @author H. James de St. Germain
 * @date Spring 2007
 * 
 *       Code inspired by Mark Allen Weiss' code
 * 
 *       this is an implementation of the Shell Sort Routine
 *
 */
public class Shell_Sort<Type extends Comparable<? super Type>> implements Sorter<Type>
{

	/**
	 * the choice of "gap" for shell sort
	 */
	double GAP = 2.2;

	/**
	 * The name of the sort
	 */
	public String name_of_sort()
	{
		return "Shell Sort using a gap of " + this.GAP;
	}

	/**
	 * For details on Shell Sort, see the Text or google
	 * 
	 * 
	 * @param array
	 *            the values to sort from small to high
	 */
	private void shell_sort( ArrayList<Type> array )
	{
		int gap = array.size() / 2;

		while (gap > 0)
		{
			for (int i = gap; i < array.size(); i++)
			{
				Type tmp = array.get(i);
				int j = i;
				while (j >= gap && tmp.compareTo(array.get(j - gap)) < 0)
				{
					//Swap. 
					Sorter.swap(array, j, j-gap);

					j -= gap;
				}
			}

			// change the gap value to a smaller value
			if (gap == 2)
			{
				gap = 1;
			}
			else
			{
				gap = (int) (gap / this.GAP);
			}
		}

	}

	/**
	 * Allow the gap to be changed more time testing
	 */
	public void set_constant( double cutoff )
	{
		this.GAP = cutoff;
	}

	/**
	 *  sort the array
	 */
	@Override
	public void sort( ArrayList<Type> array )
	{
		shell_sort(array);
	}

	@Override
	public Complexity_Class get_expected_complexity_class()
	{
		return Complexity_Class.NSquared;
	}

}