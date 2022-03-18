package cs2420;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

/**
 * The JUnit testing class for Heap.
 * 
 * @author Landon Crowther & Michelle Simmons
 * @date 2017.04.12
 */
public class HeapTest {

	Heap<Integer> heap;

	@Before
	public void setUp() {
		heap = new Heap<>();
	}

	@Test
	public void test_basic_insertion() {
		Heap<Integer> heap = new Heap<>();

		heap.add(5);
		heap.add(6);
		heap.add(3);
		heap.add(7);
		heap.add(8);
		heap.add(1);

		assertEquals(6, heap.size());

		Object[] temp = heap.toArray();

		assertArrayEquals(new Integer[] { null, 1, 6, 3, 7, 8, 5 }, temp);

		// if you want to look at your heap, uncomment this line to generate a
		// graph file,

		//heap.generateDotFile("goHere");

		// or uncomment this line, run the tests:

		//System.out.println(heap);

		// and then paste the output of the console into:
		// http://www.webgraphviz.com/
	}

	@Test
	public void testBuildHeapFromArray() {
		Integer[] array = { 2, 3, 4, 5, 6, 7, 1 };
		heap.build_heap_from_array(array);
		assertEquals(7, heap.size());
		assertEquals(new Integer(1), heap.dequeue());
		assertEquals(6, heap.size());
	}

	@Test
	public void testBuildHeapFromArrayWithNull() {
		Integer[] array = { null, 1, 2, null, null, 3, 4, -5, null, null };
		heap.build_heap_from_array(array);

		assertEquals(5, heap.size());
		assertEquals(new Integer(-5), heap.dequeue());
		assertEquals(4, heap.size());
		assertEquals(new Integer(1), heap.peek());

	}

	@Test
	public void testRemoveFromPeakSingle() {
		heap.add(1);

		assertEquals(1, heap.size());

		heap.dequeue();

		assertEquals(0, heap.size());
	}

	@Test(expected = NoSuchElementException.class)
	public void testDequeueFromPeakEmptyThrowException() {
		heap.dequeue();
	}

	@Test
	public void testDequeuFromPeakLeftChildOnly() {
		heap.add(1);
		heap.add(2);

		assertEquals(2, heap.size());

		heap.dequeue();

		assertEquals(new Integer(2), heap.peek());
		assertEquals(1, heap.size());
	}

	@Test
	public void heapSort() {
		heap.add(1);
		heap.add(2);
		heap.add(3);

		heap.heap_sort();

		assertArrayEquals(heap.toArray(), new Integer[] { null, 3, 2, 1 });

	}
	
	@Test
	public void testHeapToArray() {
		heap.add(1);
		heap.add(3);
		heap.add(4);
		heap.add(2);
		heap.add(10);
		
		assertArrayEquals(new Integer[] {null, 1, 2, 4, 3, 10}, heap.toArray());
	}

	@Test
	public void heapSortComplex() {

		final int count = 1000;
		Integer[] randomArray = new Integer[count];

		for (int i = 0; i < count; i++) {
			double random = Math.random() * (double) count;
			randomArray[i] = (int) random;
		}

		heap.build_heap_from_array(randomArray);

		Arrays.sort(randomArray, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2 - o1;
			}
		});
		
		heap.heap_sort();

		Integer[] resultingArray = new Integer[count];

		Object[] heapArray = heap.toArray();

		for (int i = 1; i <= count; i++) {
			resultingArray[i - 1] = (int) heapArray[i];
		}

		assertArrayEquals(resultingArray, randomArray);
	}

	@Test
	public void testDequeueFromPeakTwoChildren() {
		heap.add(1);
		heap.add(2);
		heap.add(3);

		assertEquals(3, heap.size());

		heap.dequeue();

		assertEquals(new Integer(2), heap.peek());
		assertEquals(2, heap.size());
	}

	@Test
	public void test_lots_of_insertions_deletions_peeks() {
		final int COUNT = 100;
		Random generator = new Random();

		for (int i = 1; i <= COUNT; i++) {
			int random = generator.nextInt();
			heap.add(random);
		}

		assertEquals(COUNT, heap.size());

		while (heap.size() > 1) {
			Integer smallest = heap.dequeue();
			assertTrue(smallest <= heap.peek());
		}

	}

	@Test
	public void testAddAndDequeueWithMultipleSameElements() {
		heap.add(2);
		heap.add(4);
		heap.add(3);
		heap.add(10);
		heap.add(4);
		heap.add(5);
		heap.add(2);

		heap.dequeue();
		assertEquals(new Integer(2), heap.dequeue());
		assertEquals(new Integer(3), heap.dequeue());
		assertEquals(new Integer(4), heap.dequeue());
		assertEquals(new Integer(4), heap.peek());
		assertEquals(3, heap.size());
	}
	
	@Test
	public void testHeapConstructorCopyHeap() {
		heap.add(2);
		heap.add(4);
		heap.add(3);
		heap.add(10);
		heap.add(4);
		heap.add(5);
		heap.add(2);
		heap.add(6);
		
		Heap<Integer> sameHeap = new Heap<>(heap);
		
		assertEquals(8, sameHeap.size());
		
		sameHeap.dequeue();
		
		//System.out.println(sameHeap.toString());
	}

}
