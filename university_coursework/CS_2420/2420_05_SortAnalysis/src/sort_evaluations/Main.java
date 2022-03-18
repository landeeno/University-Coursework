/**
 * 
 */
package sort_evaluations;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Parker Stewart
 * Landon Crowther
 * February 16, 2017
 * 
 * put your code to time all of your sorts here.
 * 
 * Pseudocode:
 *     built a list of sorters
 *     add all of the sorts (e.g., Quick_Sort_Naive, Quick_Sort_Inplace...) to the list
 *     for each element of the list
 *        time it for a wide range of values (e.g., 10,000 -> 100,000,000)
 *     test insertion sort separately because you can't wait that long...
 *
 */
public class Main
{

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		
		/*
		Sort_Utils timer = new Sort_Utils();
		ArrayList<Sorter<Integer>> sort_methods = new ArrayList<>();
		sort_methods.add(new Java_Sort<Integer>()); // index 0
		sort_methods.add(new Merge_Sort<Integer>()); // index 1
		sort_methods.add(new Quick_Sort_Naive<Integer>()); // index 2
		sort_methods.add(new Quick_Sort_Inplace_First_Pivot<Integer>()); // index 3
		sort_methods.add(new Quick_Sort_Inplace_Random_Pivot<Integer>()); // index 4
		sort_methods.add(new Quick_Sort_Inplace_M3<Integer>()); // index 5
		sort_methods.add(new Shell_Sort<Integer>()); // index 6
		sort_methods.add(new Insertion_Sort<Integer>());
//		for(int i = 0; i < sort_methods.size(); i++)
//		{
//			timer.test_and_time(sort_methods.get(i), 0, 10000, 100_000,100_000_000);
//		}
		
		int insertion = 7;
//		sort_methods.get(insertion).set_constant(2);
//		timer.test_and_time(sort_methods.get(insertion), 0, 10000, 100_000,100_000_000);
//		sort_methods.get(insertion).set_constant(4);
//		timer.test_and_time(sort_methods.get(insertion), 0, 10000, 100_000,100_000_000);
//		sort_methods.get(insertion).set_constant(6);
//		timer.test_and_time(sort_methods.get(insertion), 0, 10000, 100_000,100_000_000);
//		sort_methods.get(insertion).set_constant(8);
//		timer.test_and_time(sort_methods.get(insertion), 0, 10000, 100_000,100_000_000);
//		sort_methods.get(insertion).set_constant(10);
//		timer.test_and_time(sort_methods.get(insertion), 0, 10000, 100_000,100_000_000);
//		sort_methods.get(insertion).set_constant(12);
//		timer.test_and_time(sort_methods.get(insertion), 0, 10000, 100_000,100_000_000);
//		sort_methods.get(insertion).set_constant(14);
//		timer.test_and_time(sort_methods.get(insertion), 0, 10000, 100_000,100_000_000);
		
		timer.test_and_time(sort_methods.get(7), 0, 2500, 40000, 1000000000);
		*/
		
		ArrayList<Sorter<Integer>> sort_methods = new ArrayList<>();
		sort_methods.add(new Quick_Sort_Inplace_M3<Integer>()); // index 5
		ArrayList<Integer> test = new ArrayList<>();
		test.add(15); test.add(12); test.add(8); test.add(0); test.add(12); test.add(5); 
		test.add(60); test.add(11); // {15, 132, 8, 0, 12, 5, 60, 15};
		sort_methods.get(0).sort(test);
		
		
		
		
	}



}