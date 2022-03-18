package a9;
/**
 * tests for DynamicArrayAddOne
 */
import static org.junit.Assert.*;

import org.junit.Test;

public class DynamicArrayAddOneTest {

	/**
	 * tests that DynamicArrayAddOne works for multiple
	 * object types
	 */
	@Test
	public void differentObjectTypes() {

		DynamicArrayAddOne<String> s = new DynamicArrayAddOne<>();
		assertTrue(s.size() == 0);
		s.add("String!");
		assertTrue(s.size() == 1);

		DynamicArrayAddOne<Integer> i = new DynamicArrayAddOne<>();
		assertTrue(i.size() == 0);
		i.add(13);
		assertTrue(i.size() == 1);

		DynamicArrayAddOne<Double> d = new DynamicArrayAddOne<>();
		assertTrue(d.size() == 0);
		d.add(321.12344);
		assertTrue(i.size() == 1);
	}

	/**
	 * Testing the add method for DynamicArrayAddOne
	 * 
	 * works for both add(Object o) && add(int i, Object o);
	 * 
	 * Also, size() and toString work properly, and array length adjusts accordingly
	 */
	@Test
	public void add1() {
		DynamicArrayAddOne<Integer> d = new DynamicArrayAddOne<>() ;
		assertTrue(d.size() == 0);

		//adding to d
		d.add(1);
		//check size and toString
		assertTrue(d.size() == 1 && d.toString().equals("[1] real length: 1"));
		//check location of added element 1
		assertTrue(d.get(0) == 1);
		
		
		//adding another element
		d.add(0, 999);
		//check for size and toString
		assertTrue(d.size() == 2 && d.toString().equals("[999, 1] real length: 2"));
		//check that element added in first test moved properly
		assertTrue(d.get(1) == 1);

	}

	/**
	 * testing remove functionality of DynamicArrayAddOne
	 */
	@Test
	public void remove1() {
		DynamicArrayAddOne<Integer> d = new DynamicArrayAddOne<>() ;
		//adding elements
		d.add(1);
		d.add(2);
		d.add(3);
		//check that array is structured properly
		assertTrue(d.size() == 3 && d.toString().equals("[1, 2, 3] real length: 3"));
		
		//removing element in middle of array
		d.remove(1);
		
		//check to see that elements shifted properly, and that the real length was
		//modified correctly, and that the size is correct
		assertTrue(d.toString().equals("[1, 3] real length: 2") && d.size() == 2);
	}


}
