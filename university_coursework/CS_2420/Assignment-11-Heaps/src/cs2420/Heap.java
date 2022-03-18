package cs2420;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Represents a priority queue of generically-typed items. The queue is
 * implemented as a minimum heap.
 * 
 * The minimum heap is implemented implicitly as an array.
 * 
 * @author Landon Crowther & Michelle Simmons
 * @date 2017.04.12
 */
public class Heap<Type> implements Priority_Queue<Type> {
	/**
	 * The number of elements in the heap (NOT: the capacity of the array)
	 */
	private int size;

	/**
	 * The implementation array used to store heap values.
	 * 
	 * Uses a 1 indexed array. This means the 0 bucket is ignored and the
	 * capacity of the array must be 1 larger than size.
	 */
	private Type[] heap_array;

	private int swapCount;

	/**
	 * If the user provides a comparator, use it instead of default comparable
	 */
	private Comparator<? super Type> comparator;

	/**
	 * Constructs an empty priority queue. Orders elements according to their
	 * natural ordering (i.e., AnyType is expected to be Comparable)
	 * 
	 * AnyType is not forced to be Comparable.
	 */
	@SuppressWarnings("unchecked")
	public Heap() {
		size = 0;
		comparator = null;
		heap_array = (Type[]) new Object[10];
		swapCount = 0;
	}

	/**
	 * Construct an empty priority queue with a specified comparator.
	 * 
	 * Orders elements according to the input Comparator (i.e., AnyType need not
	 * be Comparable).
	 */
	public Heap(Comparator<? super Type> c) {
		super();
		comparator = c;
	}

	/**
	 * Constructs a priority queue that is identical to the passed in priority
	 * queue... In effect, creates a copy of the parameter priority queue.
	 * 
	 * @param copyHeap
	 *            - the heap to copy for the new heap
	 */
	public Heap(Heap<Type> copyHeap) {
		this.size = copyHeap.size;
		this.comparator = copyHeap.comparator;
		this.heap_array = copyHeap.heap_array.clone();
		swapCount = 0;
	}

	/**
	 * 
	 * Removes and returns the minimum item in this priority queue.
	 * 
	 * @throws NoSuchElementException
	 *             if this priority queue is empty. (Runs in logarithmic time.)
	 */
	public Type dequeue() {

		if (size == 0) {
			throw new NoSuchElementException();
		}

		Type topElement = heap_array[1];
		swap(1, size);
		heap_array[size] = null;
		size--;
		sink(1);
		return topElement;
	}

	/**
	 * Reassigns the location of and returns the minimum item in this priority
	 * queue. When a Type is dequeued in this method, instead of being removed
	 * from the array, it is placed at the end and ignored by sink().
	 * 
	 * @throws NoSuchElementException
	 *             if this priority queue is empty. (Runs in logarithmic time.)
	 */
	private Type dequeueHeapSort() {

		if (size == 0) {
			throw new NoSuchElementException();
		}

		Type topElement = heap_array[1];
		swap(1, size);
		size--;
		sink(1);
		return topElement;
	}

	/**
	 * Percolates data down the heap until it reaches its proper place
	 * (according to rules of the heap)
	 * 
	 * @param index
	 *            - the index of the data to be percolated down
	 */
	private void sink(int index) {

		int currentIndex = index;
		int leftIndex = leftChild(currentIndex);
		int rightIndex = rightChild(currentIndex);
		Type current = heap_array[currentIndex];
		Type left;
		Type right;

		while (currentIndex < size) {

			current = heap_array[currentIndex];

			// case with two children
			if (rightIndex <= size) {

				right = heap_array[rightIndex];
				left = heap_array[leftIndex];

				if (compare(current, right) > 0 || compare(current, left) > 0) {

					if (compare(left, right) < 0) {// rightChild > leftChild
						swap(currentIndex, leftIndex);
						currentIndex = leftIndex;
					} else {
						swap(currentIndex, rightIndex);
						currentIndex = rightIndex;
					}

					leftIndex = leftChild(currentIndex);
					rightIndex = rightChild(currentIndex);
				} else {
					break;
				}
			}

			// case with only left child
			else if (leftIndex <= size) {

				left = heap_array[leftIndex];
				if (compare(current, left) > 0) {
					swap(currentIndex, leftIndex);
					currentIndex = leftIndex;
					leftIndex = leftChild(currentIndex);
					rightIndex = rightChild(currentIndex);
				} else {
					break;
				}

			}

			// case with no children
			else {
				break;
			}
		}

	}

	/**
	 * Adds an item to this priority queue. (Runs in logarithmic time.) Can
	 * sometimes terminate early.
	 * 
	 * @param x
	 *            -- the item to be inserted
	 */
	public void add(Type x) {
		resize();
		heap_array[++size] = x;
		swim(size);
	}

	/**
	 * Percolates data up the heap until it reaches its proper place (according
	 * to rules of the heap)
	 * 
	 * @param index
	 *            - the index of the data to be percolated up
	 */
	private void swim(int index) {
		int current = index;
		int parent = getParentIndex(current);
		while (current > 1 && compare(heap_array[parent], heap_array[current]) > 0) {

			swap(current, parent);

			current = parent;
			parent = getParentIndex(current);
		}
	}

	/**
	 * Swaps the data in the two given indexes in an array.
	 * 
	 * @param indexOne
	 *            - the first index to be swapped
	 * @param indexTwo
	 *            - the second index to be swapped
	 */
	private void swap(int indexOne, int indexTwo) {
		swapCount++;
		Type temp = heap_array[indexOne];
		heap_array[indexOne] = heap_array[indexTwo];
		heap_array[indexTwo] = temp;
	}

	/**
	 * For analysis purposes.
	 * 
	 * @return the number of swaps performed
	 */
	public int getSwapCount() {
		return swapCount;
	}

	/**
	 * Automatically resizes the heap array if there are no more available
	 * buckets.
	 */
	@SuppressWarnings("unchecked")
	private void resize() {
		if ((heap_array.length - 1) > size) {
			return;
		}

		Type[] temp = heap_array;

		int newSize = heap_array.length * 2;
		heap_array = (Type[]) new Object[newSize];

		for (int index = 0; index < temp.length; index++) {
			heap_array[index] = temp[index];
		}
	}

	/**
	 * Gets the index of the parent.
	 * 
	 * @param index
	 *            - the index of the child
	 * @return the index of the child's parent
	 */
	private int getParentIndex(int index) {
		return index / 2;
	}

	/**
	 * Gets the index of the left child.
	 * 
	 * @param index
	 *            - the index of the parent
	 * @return the index of the left child of the parent
	 */
	private int leftChild(int index) {
		return index * 2;
	}

	/**
	 * Gets the index of the right child.
	 * 
	 * @param index
	 *            - the index of the parent
	 * @return the index of the right child of the parent
	 */
	private int rightChild(int index) {
		return index * 2 + 1;
	}

	/**
	 * Generates a DOT file for visualizing the binary heap.
	 */
	public void generateDotFile(String filename) {
		try (PrintWriter out = new PrintWriter(filename)) {
			out.println(this);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * Internal method for comparing left and right using Comparator if provided
	 * by the user at construction time, or Comparable, if no Comparator was
	 * provided.
	 */
	@SuppressWarnings("unchecked")
	private int compare(Type lhs, Type rhs) {
		if (comparator == null) {
			return ((Comparable<? super Type>) lhs).compareTo(rhs); // safe to
																	// ignore
																	// warning
		}

		// We won't test your code on non-Comparable types if we didn't supply a
		// Comparator
		return comparator.compare(lhs, rhs);
	}

	/**
	 * @return a copy of the array used in the heap
	 */
	public Object[] toArray() {
		Object[] copy_of_array = new Object[size + 1];

		for (int i = 1; i <= size; i++) {
			copy_of_array[i] = heap_array[i];
		}

		return copy_of_array;
	}

	/**
	 * @return a string representing the DOT data of the heap
	 */
	@Override
	public String toString() {
		String result = "digraph Heap {\n\tnode [shape=record]\n";
		for (int i = 1; i <= size; i++) {
			result += "\tnode" + i + " [label = \"<f0> |<f1> " + heap_array[i] + "|<f2> \"]\n";
			if (((i * 2)) <= size)
				result += "\tnode" + i + ":f0 -> node" + ((i * 2)) + ":f1\n";
			if (((i * 2) + 1) <= size)
				result += "\tnode" + i + ":f2 -> node" + ((i * 2) + 1) + ":f1\n";
		}
		result += "}";

		result += "\n//--------------------------------------------\n";

		return result;
	}

	/**
	 * 1) copy data from array into heap storage 
	 * 2) do an "in place" creation of the heap
	 * 
	 * @param array
	 *            - random data (unordered)
	 */
	@SuppressWarnings("unchecked")
	public void build_heap_from_array(Type[] array) {
		clear();
		heap_array = (Type[]) new Object[array.length + 1];
		heap_array[0] = null;

		int j = 0;

		for (int i = 0; i < array.length; i++) {
			j++;
			if (array[i] != null) {
				size++;
				heap_array[j] = array[i];
			} else {
				j--;
			}
		}

		for (int index = size / 2; index > 0; index--) {
			sink(index);
		}

	}

	/**
	 * Convert the heap array into a sorted array from largest to smallest
	 * 
	 * Note: this destroys the heap property of the array and should be a
	 * terminal operation, which is not what we would likely do in a real
	 * program, but is appropriate to for our purposes (i.e., understanding how
	 * heap sort works in place).
	 * 
	 */
	public void heap_sort() {

		int size = this.size;

		while (this.size > 0) {
			dequeueHeapSort();
		}

		this.size = size;

	}

	/**
	 * @return the smallest element the queue.
	 * @throws NoSuchElementException
	 *             if this priority queue is empty. (Must run in constant time.)
	 */
	@Override
	public Type peek() {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		return heap_array[1];
	}

	/**
	 * @return the total number of elements in the queue
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Makes this priority queue empty.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		size = 0;
		heap_array = (Type[]) new Object[10];
	}

}
