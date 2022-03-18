package cs2420;

/**
 * The Timing class for Heap. This class uses a few methods to produce useful
 * data for our analysis, including time to insert elements into a heap, time to
 * dequeue elements from a heap, and the number of swaps that occur when a heap
 * is built.
 * 
 * @author Landon Crowther & Michelle Simmons
 * @date 2017.04.13
 */
public class Timing {
	final static Integer[] arraySizes = { 100, 1000, 10_000, 100_000, 200_000, 300_000, 400_000, 500_000, 600_000,
			700_000, 800_000, 900_000, 1_000_000 }; // Different sizes at which to build arrays
	final static int averageCount = 25; // Number of trials to run for each experiment

	static int[] increasing; // An array of integers sorted from least to greatest
	static int[] decreasing; // An array to integers sorted from greatest to least
	static int[] random; // An array of randomly sorted integers

	/**
	 * Creates an array of randomly sorted integers.
	 * 
	 * @param size
	 *            - the size to build the array
	 */
	private static void buildRandom(int size) {
		random = new int[size];
		for (int index = 0; index < size; index++) {
			random[index] = (int) (Math.random() * size);
		}
	}

	/**
	 * Creates an array of integers sorted from least to greatest.
	 * 
	 * @param size
	 *            - the size to build the array
	 */
	private static void buildIncreasing(int size) {
		increasing = new int[size];
		for (int index = 0; index < size; index++) {
			increasing[index] = index;
		}
	}

	/**
	 * Creates an array of integers sorted from greatest to least.
	 * 
	 * @param size
	 *            - the size to build the array
	 */
	private static void buildDecreasing(int size) {
		decreasing = new int[size];
		int count = 0;
		for (int index = size; index > 0; index--) {
			decreasing[count] = index;
			count++;
		}
	}

	/**
	 * Prints out the time in nanoseconds required to build a heap. Repeats this
	 * experiment for heaps of several sizes, and runs 25 trials for each heap.
	 * Also prints out the number of swaps performed in the process of building
	 * the heap.
	 */
	public static void timeInsert() {

		System.out.println("Insert: ");
		System.out.println("Size: \t" + "Increasing: \t" + "Decreasing: \t" + "Random: \t" + "Swap Count Increasing: \t"
				+ "Swap Count Decreasing: \t" + "Swap Count Random: \t");

		for (Integer currentSize : arraySizes) {

			buildIncreasing(currentSize);
			buildDecreasing(currentSize);

			// time increasing

			int swapCountIncreasing = 0;
			int count = 0;
			double insertTimeIncreasing = 0;
			while (count < averageCount) {
				Heap<Integer> heap = new Heap<>();
				double startTime = System.nanoTime();
				for (int i = 0; i < currentSize; i++) {
					heap.add(increasing[i]);
				}
				double totalTime = System.nanoTime() - startTime;

				insertTimeIncreasing += totalTime;

				swapCountIncreasing = heap.getSwapCount();

				count++;
			}

			insertTimeIncreasing /= averageCount;

			// time decreasing
			int swapCountDecreasing = 0;
			count = 0;
			double insertTimeDecreasing = 0;
			while (count < averageCount) {
				Heap<Integer> heap = new Heap<>();
				double startTime = System.nanoTime();
				for (int i = 0; i < currentSize; i++) {
					heap.add(decreasing[i]);
				}
				double totalTime = System.nanoTime() - startTime;
				insertTimeDecreasing += totalTime;

				swapCountDecreasing = heap.getSwapCount();

				count++;
			}

			insertTimeDecreasing /= averageCount;

			// time random
			double swapCountRandom = 0;
			count = 0;
			double insertTimeRandom = 0;
			while (count < averageCount) {
				Heap<Integer> heap = new Heap<>();
				buildRandom(currentSize);
				double startTime = System.nanoTime();
				for (int i = 0; i < currentSize; i++) {
					heap.add(random[i]);
				}
				double totalTime = System.nanoTime() - startTime;
				insertTimeRandom += totalTime;
				count++;
				swapCountRandom += heap.getSwapCount();
			}

			insertTimeRandom /= averageCount;
			swapCountRandom /= averageCount;

			System.out.println(
					currentSize + "\t" + insertTimeIncreasing + "\t" + insertTimeDecreasing + "\t" + insertTimeRandom
							+ "\t" + swapCountIncreasing + "\t" + swapCountDecreasing + "\t" + swapCountRandom);

		}

		System.out.println();

	}

	/**
	 * Prints out the time in nanoseconds required to dequeue every element from
	 * a heap. Repeats this experiment for heaps of several sizes, and runs 25
	 * trials for each heap.
	 */
	public static void timeDequeue() {

		System.out.println("Timing Dequeue: ");
		System.out.println("Size: \t" + "Time Increasing: \t" + "Time Decreasing: \t" + "Time Random: \t");

		for (Integer currentSize : arraySizes) {

			buildIncreasing(currentSize);
			buildDecreasing(currentSize);

			// time dequeue for increasing & decreasing
			Heap<Integer> heapIncreasing = new Heap<>();
			Heap<Integer> heapDecreasing = new Heap<>();

			for (int i = 0; i < currentSize; i++) {
				heapIncreasing.add(increasing[i]);
				heapDecreasing.add(decreasing[i]);
			}

			double dequeueTimeIncreasing = 0;
			double dequeueTimeDecreasing = 0;

			int count = 0;
			while (count < averageCount) {

				Heap<Integer> heapIncreasingCopy = new Heap<Integer>(heapIncreasing);
				Heap<Integer> heapDecreasingCopy = new Heap<Integer>(heapDecreasing);

				double startTimeIncreasing = System.nanoTime();
				for (int i = 0; i < currentSize; i++) {
					heapIncreasingCopy.dequeue();
				}
				double totalTimeIncreasing = System.nanoTime() - startTimeIncreasing;
				dequeueTimeIncreasing += totalTimeIncreasing;

				double startTimeDecreasing = System.nanoTime();
				for (int i = 0; i < currentSize; i++) {
					heapDecreasingCopy.dequeue();
				}
				double totalTimeDecreasing = System.nanoTime() - startTimeDecreasing;
				dequeueTimeDecreasing += totalTimeDecreasing;

				count++;
			}

			dequeueTimeIncreasing /= averageCount;
			dequeueTimeDecreasing /= averageCount;

			// time random
			count = 0;
			double dequeueTimeRandom = 0;
			while (count < averageCount) {

				Heap<Integer> heap = new Heap<>();
				buildRandom(currentSize);
				for (int i = 0; i < currentSize; i++) {
					heap.add(random[i]);
				}

				double startTime = System.nanoTime();
				for (int i = 0; i < currentSize; i++) {
					heap.dequeue();
				}
				double totalTime = System.nanoTime() - startTime;

				dequeueTimeRandom += totalTime;
				count++;

			}

			dequeueTimeRandom /= averageCount;

			System.out.println(currentSize + "\t" + dequeueTimeIncreasing + "\t" + dequeueTimeDecreasing + "\t"
					+ dequeueTimeRandom);
		}

		System.out.println();

	}

	/**
	 * Main method for Timing. Prints out timing results for adding to and
	 * dequeuing from a heap.
	 */
	public static void main(String[] args) {

		timeInsert();
		timeDequeue();

	}

}
