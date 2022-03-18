package a9;
/**
 * tests for DynamicArrayCustom
 */
import static org.junit.Assert.*;

import org.junit.Test;

public class DynamicArrayCustomTest {

	/**
	 * tests that DynamicArrayCustom works for multiple
	 * object types
	 */
	@Test
	public void differentObjectTypes() {

		DynamicArrayCustom<String> s = new DynamicArrayCustom<>();
		assertTrue(s.size() == 0);
		s.add("String!");
		assertTrue(s.size() == 1);

		DynamicArrayCustom<Integer> i = new DynamicArrayCustom<>();
		assertTrue(i.size() == 0);
		i.add(13);
		assertTrue(i.size() == 1);

		DynamicArrayCustom<Double> d = new DynamicArrayCustom<>();
		assertTrue(d.size() == 0);
		d.add(321.12344);
		assertTrue(i.size() == 1);
	}

	/**
	 * testing add functionality of DynamicArrayCustom
	 * checking for real length and size
	 */
	@Test
	public void add() {
		DynamicArrayCustom<Integer> d = new DynamicArrayCustom<>();
		
		//check that real length is 2:
		assertTrue(d.size() == 0 && d.toString().equals("[] real length: 2"));
		
		//adding 2 elements to DynamicArrayCustom
		d.add(1);
		d.add(2);
		//check to see that size increased by 1 and real length doubled		
		assertTrue(d.size() == 2 && d.toString().equals("[1, 2] real length: 2"));
		
		//adding element at index 0:
		d.add(0, 0);
		
		/*
		 * checking 3 things:
		 * 1 - array shifted properly while adding at index 0
		 * 2 - size changed properly
		 * 3 - array length doubled b/c before adding, count was = to data.length
		 */
		assertTrue(d.size() ==3 && d.toString().equals("[0, 1, 2] real length: 4"));
		
	}
	
	/**
	 * testing remove strategies of DA custom
	 * My remove strategy: 
	 * 	if the real length of the array is more than 4 times the count,
	 * 	shrink the array by 1/4
	 * 
	 * for example
	 * 
	 * if the array length is 8, but there are only two elements in the array, and the 
	 * user tries to remove an element, the array will remove 1/4 of the empty space
	 * (real length will become 6)
	 * 
	 */
	@Test
	public void remove() {
		DynamicArrayCustom<Integer> d = new DynamicArrayCustom<>();
		
		
		
		int numElements = 1; 
		while (numElements <= 5) {
			d.add(numElements);
			numElements++			;
		}
		
		//added enough elements to make the array length 8
		assertTrue(d.size() == 5 && d.toString().equals("[1, 2, 3, 4, 5] real length: 8"));
		
		//removing 3 elements from the dynamic array (only 2 elements left)
		d.remove(0);
		d.remove(0);
		d.remove(0);
		
		/*
		 * test shows 2 things:
		 * 1 - array shifts properly when removing indices at 0
		 * 2 - real array length hasn't yet been modified
		 */
		assertTrue(d.size() == 2 && d.toString().equals("[4, 5] real length: 8"));
		
		//removing one more element that will adjust the array length:
		d.remove(0);
		//check to see that the array length adjusted properly
		assertTrue(d.size() == 1 && d.toString().equals("[5] real length: 6"));
		
		//removing the last elemeent to show that the array size shrinks again
		d.remove(0);
		assertTrue(d.size() == 0 && d.toString().equals("[] real length: 4"));
			
	}
	
}
