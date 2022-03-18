package a9;
/**
 * tests for DynamicArrayDouble
 */
import static org.junit.Assert.*;

import org.junit.Test;

public class DynamicArrayDoubleTest {

	/**
	 * tests that DynamicArrayDouble works for multiple
	 * object types
	 */
	@Test
	public void differentObjectTypes() {

		DynamicArrayDouble<String> s = new DynamicArrayDouble<>();
		assertTrue(s.size() == 0);
		s.add("String!");
		assertTrue(s.size() == 1);

		DynamicArrayDouble<Integer> i = new DynamicArrayDouble<>();
		assertTrue(i.size() == 0);
		i.add(13);
		assertTrue(i.size() == 1);

		DynamicArrayDouble<Double> d = new DynamicArrayDouble<>();
		assertTrue(d.size() == 0);
		d.add(321.12344);
		assertTrue(i.size() == 1);
	}
	
	/**
	 * test to show that add functionality works properly
	 * and that the real array length adjusts properly
	 */
	@Test
	public void add() {
		DynamicArrayDouble<Integer> d = new DynamicArrayDouble<>();
		
		//check to make sure array and size is correct
		d.add(1);
		assertTrue(d.size() == 1 && d.toString().equals("[1] real length: 1"));
		
		//check to see that size is correct, and that the real length is correct
		d.add(2);
		assertTrue(d.size() == 2 && d.toString().equals("[1, 2] real length: 3"));
		
		
		}
	

	/**
	 * check to see that remove method works properly
	 * size and array length
	 */
	@Test
	public void remove() {
		DynamicArrayDouble<Integer> d = new DynamicArrayDouble<>();
		
		d.add(1);
		d.add(2);
		d.add(3);
		d.add(4);
		
		//check for proper size and real length
		assertTrue(d.size() == 4 && d.toString().equals("[1, 2, 3, 4] real length: 7"));
		
		
		//remove element from middle of array
		d.remove(2);
		
		//check to see that size is correct and array length is correct
		assertTrue(d.size() == 3 && d.toString().equals("[1, 2, 4] real length: 7"));
		
	}

}
