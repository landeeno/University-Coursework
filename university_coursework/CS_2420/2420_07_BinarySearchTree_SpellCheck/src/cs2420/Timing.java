package cs2420;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

public class Timing {

	public static void main(String[] args) {

		ArrayList<Integer> sorted = new ArrayList<Integer>();
		ArrayList<Integer> random = new ArrayList<>();
		long timeStart = 0;
		long timeEnd = 0;
		long timeTotal = 0;

		long timeSortedBSTAddAll = 0;
		long timeRandomBSTAddAll = 0;
		long timeSortedBSTContainsAll = 0;
		long timeRandomBSTContainsAll = 0;

		long timeSortedJavaAddAll = 0;
		long timeRandomJavaAddAll = 0;
		long timeSortedJavaContainsAll = 0;
		long timeRandomJavaContainsAll = 0;

		final int averageCount = 100;

		Integer[] BSTSizes = {1000, 2000, 3000, 4000, 5000,
				6000, 7000, 8000, 9000, 10_000 };
		Integer[] javaSizes = {1000, 5000, 10_000, 50_000, 100_000, 
				500_000, 1_000_000};
		


		// TIME OUR BST //

		// fill lists with data 0 - numElementsBST

		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
		TreeSet<Integer> javaTree = new TreeSet<>();

		// TIME ADD ALL //
		System.out.println("---TIMING ADD ALL FOR BST---  (탎)");
		System.out.printf("%s\t%s\t\t%s\n", "Number of Integers", "Sorted", "Random Order");

		for (Integer currentSize : BSTSizes)
		{
			sorted.clear();
			random.clear();
			for (int i = 0; i < currentSize; i++) {
				sorted.add(i);
				random.add(i);
			}


			int counter = 0;
			while (counter < averageCount) {

				timeStart = System.nanoTime();
				tree.addAll(sorted);
				timeEnd = System.nanoTime();

				timeTotal += (timeEnd - timeStart);
				counter++;
				tree.clear();
			}

			timeSortedBSTAddAll = timeTotal / counter;
			timeSortedBSTAddAll /= 1000; // put time into seconds
			counter = 0;
			timeTotal = 0;

			counter = 0;
			while (counter < averageCount) {
				// randomize list each time
				Collections.shuffle(random);

				timeStart = System.nanoTime();
				tree.addAll(random);
				timeEnd = System.nanoTime();

				timeTotal += (timeEnd - timeStart);
				counter++;
				tree.clear();
			}


			timeRandomBSTAddAll = timeTotal / counter;
			timeRandomBSTAddAll /= 1000; // put time into seconds
			counter = 0;
			timeTotal = 0;

			// print information //


			System.out.printf("%8d\t\t%s\t\t%s\t\n", currentSize,
					timeSortedBSTAddAll < 0 ? "     " : String.format("%5.2f", (float) timeSortedBSTAddAll),
							timeRandomBSTAddAll < 0 ? "     " : String.format("%5.2f", (float) timeRandomBSTAddAll));

		}

		// TIME CONTAINS ALL //
		System.out.println("---TIMING CONTAINS ALL FOR BST---  (탎)");
		System.out.printf("%s\t%s\t\t%s\n", "Number of Integers", "Sorted", "Random Order");

		for (Integer currentSize : BSTSizes)
		{
			sorted.clear();
			random.clear();
			for (int i = 0; i < currentSize; i++) {
				sorted.add(i);
				random.add(i);
			}


			int counter = 0;
			while (counter < averageCount) {
				tree.addAll(sorted);
				timeStart = System.nanoTime();
				tree.containsAll(sorted);
				timeEnd = System.nanoTime();

				timeTotal += (timeEnd - timeStart);
				counter++;
				tree.clear();
			}

			timeSortedBSTContainsAll = timeTotal / counter;
			timeSortedBSTContainsAll /= 1000; // put time into seconds
			counter = 0;
			timeTotal = 0;

			counter = 0;
			while (counter < averageCount) {
				// randomize list each time
				Collections.shuffle(random);
				tree.addAll(random);
				timeStart = System.nanoTime();
				tree.containsAll(random);
				timeEnd = System.nanoTime();

				timeTotal += (timeEnd - timeStart);
				counter++;
				tree.clear();
			}


			timeRandomBSTContainsAll = timeTotal / counter;
			timeRandomBSTContainsAll /= 1000; // put time into seconds
			counter = 0;
			timeTotal = 0;

			// print information //


			System.out.printf("%8d\t\t%s\t\t%s\t\n", currentSize,
					timeSortedBSTContainsAll < 0 ? "     " : String.format("%5.2f", (float) timeSortedBSTContainsAll),
							timeRandomBSTContainsAll < 0 ? "     " : String.format("%5.2f", (float) timeRandomBSTContainsAll));

		}
		
		System.out.println("---TIMING ADD ALL FOR JAVA TREE SET--- (탎)");
		System.out.printf("%s\t%s\t\t%s\n", "Number of Integers", "Sorted", "Random Order");
		
		for (Integer currentSize : javaSizes)
		{
			sorted.clear();
			random.clear();
			for (int i = 0; i < currentSize; i++) {
				sorted.add(i);
				random.add(i);
			}


			int counter = 0;
			while (counter < averageCount) {

				timeStart = System.nanoTime();
				javaTree.addAll(sorted);
				timeEnd = System.nanoTime();

				timeTotal += (timeEnd - timeStart);
				counter++;
				javaTree.clear();
			}

			timeSortedJavaAddAll = timeTotal / counter;
			timeSortedJavaAddAll /= 1000; // put time into seconds
			counter = 0;
			timeTotal = 0;

			counter = 0;
			while (counter < averageCount) {
				// randomize list each time
				Collections.shuffle(random);

				timeStart = System.nanoTime();
				javaTree.addAll(random);
				timeEnd = System.nanoTime();

				timeTotal += (timeEnd - timeStart);
				counter++;
				javaTree.clear();
			}


			timeRandomJavaAddAll = timeTotal / counter;
			timeRandomJavaAddAll /= 1000; // put time into seconds
			counter = 0;
			timeTotal = 0;

			// print information //


			System.out.printf("%8d\t\t%s\t\t%s\t\n", currentSize,
					timeSortedJavaAddAll < 0 ? "     " : String.format("%5.2f", (float) timeSortedJavaAddAll),
							timeRandomJavaAddAll < 0 ? "     " : String.format("%5.2f", (float) timeRandomJavaAddAll));

		}
		
		System.out.println("---TIMING CONTAINS ALL FOR JAVA TREE SET--- (탎)");
		System.out.printf("%s\t%s\t\t%s\n", "Number of Integers", "Sorted", "Random Order");
		
		for (Integer currentSize : javaSizes)
		{
			sorted.clear();
			random.clear();
			for (int i = 0; i < currentSize; i++) {
				sorted.add(i);
				random.add(i);
			}


			int counter = 0;
			while (counter < averageCount) {
				
				javaTree.addAll(sorted);

				timeStart = System.nanoTime();
				javaTree.containsAll(sorted);
				timeEnd = System.nanoTime();

				timeTotal += (timeEnd - timeStart);
				counter++;
				javaTree.clear();
			}

			timeSortedJavaContainsAll = timeTotal / counter;
			timeSortedJavaContainsAll /= 1000; // put time into seconds
			counter = 0;
			timeTotal = 0;

			counter = 0;
			while (counter < averageCount) {
				// randomize list each time
				Collections.shuffle(random);
				javaTree.addAll(random);

				timeStart = System.nanoTime();
				javaTree.addAll(random);
				timeEnd = System.nanoTime();

				timeTotal += (timeEnd - timeStart);
				counter++;
				javaTree.clear();
			}


			timeRandomJavaContainsAll = timeTotal / counter;
			timeRandomJavaContainsAll /= 1000; // put time into seconds
			counter = 0;
			timeTotal = 0;

			// print information //


			System.out.printf("%8d\t\t%s\t\t%s\t\n", currentSize,
					timeSortedJavaContainsAll < 0 ? "     " : String.format("%5.2f", (float) timeSortedJavaContainsAll),
							timeRandomJavaContainsAll < 0 ? "     " : String.format("%5.2f", (float) timeRandomJavaContainsAll));

		}
		
		
	}
}
