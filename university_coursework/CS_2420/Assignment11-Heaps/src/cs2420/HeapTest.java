package cs2420;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class HeapTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void test()
	{
		fail("Not yet implemented");
	}

	
	 // sample simple test you can use
    @Test
    public void test_basic_insertion()
    {
            Heap<Integer> heap = new Heap<>();
            
            heap.add( 5 );
            heap.add( 6 );
            heap.add( 3 );
            heap.add( 7 );
            heap.add( 8 );
            heap.add( 1 );
            
            assertEquals(6, heap.size());
            
            Object[] temp = heap.toArray();
            
            assertArrayEquals(new Integer[]{null, 1,6,3,7,8,5}, temp);

            // if you want to look at your heap, uncomment this line to generate a graph file,
            //
            //       heap.generateDotFile("Documents/test_heap.dot");
            //
            // or uncomment this line, run the tests:
            //
            //       System.out.println(heap);
            //
            // and then paste the output of the console into: http://www.webgraphviz.com/
    }


    // sample advanced test you might want to implement
    @Test
    public void test_lots_of_insertions_deletions_peeks()
    {
            Heap<Integer> heap = new Heap<>();
            
            final int COUNT = 1000;
            Random generator = new Random();
            
            // FIXME: add COUNT elements to HEAP

            assertEquals(COUNT, heap.size());
            
            int smallest = heap.dequeue();

            // FIXME: while the heap has elements
            //           remove one, make sure it is larger than smallest, update smallest 
    }
}
