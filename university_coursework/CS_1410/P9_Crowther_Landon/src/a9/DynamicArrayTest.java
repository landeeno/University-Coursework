package a9;

import static org.junit.Assert.*;

import org.junit.Test;

public class DynamicArrayTest {

	/**
	 * test that DynamicArray is in fact a 
	 * generic method that works for different 
	 * types of objects:
	 */
	@Test
	public void differentObjectTypes() {
	
		DynamicArray<String> s = new DynamicArray<>();
		assertTrue(s.size() == 0);
		s.add("String!");
		assertTrue(s.size() == 1);
		
		DynamicArray<Integer> i = new DynamicArray<>();
		assertTrue(i.size() == 0);
		i.add(13);
		assertTrue(i.size() == 1);
		
		DynamicArray<Double> d = new DynamicArray<>();
		assertTrue(d.size() == 0);
		d.add(321.12344);
		assertTrue(i.size() == 1);
	}
	
	/**
	 * Shows that size of DynamicArray changes appropriately
	 * when adding elements to end of array
	 */
	@Test
	public void addTest() {
		DynamicArray<String> s = new DynamicArray<>();
		
		String expected = "[] real length: 0";
		assertTrue(s.size() == 0);
		assertTrue(expected.equals(s.toString()));
	
		s.add("a");
		assertTrue(s.size() == 1);
		expected = "[a] real length: 1";
		assertTrue(expected.equals(s.toString()));		
		
	}
	
	/**
	 * shows that the add method works when adding at different 
	 * index values:
	 */
	@Test
	public void addTest1() {
		DynamicArray<Integer> d = new DynamicArray<>();
		
		d.add(1);
		d.add(2);
		d.add(3);
		
		assertTrue(d.size() == 3);
		
		//adding at index 0
		d.add(0, 0);
		assertTrue(d.size() == 4 && d.toString().equals("[0, 1, 2, 3] real length: 4"));
	}
	
	
	/**
	 * shows proper remove functionality:
	 * removes at any index, and DynamicArray shifts accordingly
	 */
	@Test
	public void remove1() {
		DynamicArray<String> d = new DynamicArray<>();
		
		d.add("a");
		d.add("b");
		d.add("c");
		d.add("d");
		
		//check toString and size
		assertTrue(d.size() == 4 && d.toString().equals("[a, b, c, d] real length: 4"));
		
		//removing at index 0
		d.remove(0);
		assertTrue(d.size() == 3 && d.toString().equals("[b, c, d] real length: 3"));
		
		//removing at index other than 0
		d.remove(1);
		assertTrue(d.size() == 2 && d.toString().equals("[b, d] real length: 2"));
		
	}
}
