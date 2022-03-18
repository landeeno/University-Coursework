package a8;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Landon Crowther
 * u0926601
 * HW8 - CS1410
 * 
 * Test cases for StringSet class
 * @author Landon Crowther
 *
 */
public class StringSetTest {

	/**
	 * test for null input string: 
	 */
	@Test
	public void contains1() {
		StringSet test = new StringSet();
		try {
			test.contains(null);
			fail("Expected an IllegalArgumentException to be thrown");
		} catch (IllegalArgumentException anIllegalArgumentException) {
			return;
		}
	}

	/**
	 * proper functionality of contains method
	 */
	@Test
	public void contains2() {
		StringSet test = new StringSet();

		test.insert("asdf");
		assertTrue(test.contains("asdf") == true);
	}

	/**
	 * shows that contains works for strings that
	 * are first or second elements of StringSet
	 */
	@Test
	public void contains3() {
		StringSet test = new StringSet();

		test.insert("1");
		test.insert("2");

		assertTrue(test.contains("1") == true);
		assertTrue(test.contains("2") == true);

	}

	/**
	 * contains recognizes empty strings differently than
	 * null
	 */
	@Test
	public void contains4() {
		StringSet test = new StringSet();

		test.insert("");
		assertTrue(test.contains("") == true);
	}

	/**
	 * contains method shows false if:
	 * 		StringSet is empty:
	 * 		StringSet does not contain e:
	 */
	@Test
	public void contains5() {
		StringSet test = new StringSet();
		assertFalse(test.contains("") == true);

		test.insert("asdf");
		assertFalse(test.contains("") == true);
	}

	/**
	 * insert throws IllegalArgumentException when 
	 * null string is used as parameter
	 */
	@Test
	public void insert1() {
		StringSet test = new StringSet();

		try {
			test.insert(null);
			fail("Expected an IllegalArgumentException");
		} catch (IllegalArgumentException anIllegalArgumentException) {
			return;
		}		
	}

	/**
	 * shows that insert method works properly and adds the
	 * string parameter
	 */
	@Test
	public void insert2() {

		StringSet test = new StringSet();

		test.insert("asdf");
		assertTrue(test.contains("asdf") == true);

	}

	/**
	 * insert won't add an element that already exists
	 */
	@Test
	public void insert3() {
		StringSet test = new StringSet();

		test.insert("asdf");
		assertTrue(test.size() == 1);

		test.insert("asdf");
		assertTrue(test.size() == 1);

	}

	/**
	 * empty strings are allowed
	 */
	@Test
	public void insert4() {
		StringSet test = new StringSet();

		test.insert("");
		assertTrue(test.size() == 1);
	}


	@Test
	public void remove1() {
		StringSet test = new StringSet();
		
		
		try {
			test.remove(null);
			fail("Expected an IllegalArgumentException to be thrown");
		} catch (IllegalArgumentException e) {
			return;
		}	

	}


	/**
	 * shows that remove can successfully remove an element
	 * from StringSet object
	 */
	@Test
	public void remove2() {
		StringSet test = new StringSet();

		test.insert("asdf");
		test.remove("asdf");

		assertTrue(test.size() == 0);
	}

	/**
	 * shows that elements can be removed from non-singular arrays, 
	 * and order remains the same as the DynamicArray2 class.
	 */
	@Test
	public void remove3() {
		StringSet test = new StringSet();

		test.insert("1");
		test.insert("2");
		test.insert("3");
		test.insert("4");

		test.remove("3");

		String expected = "{1, 2, 4}";

		assertTrue(expected.equals(test.toString()));		
		assertTrue(test.size() == 3);
	}

	/**
	 * shows that removing an element that doesn't exist has no
	 * effect on the SubString object
	 */
	@Test
	public void remove4() {
		StringSet test = new StringSet();

		test.insert("1");
		test.insert("2");
		test.insert("3");

		test.remove("0");

		String expected = "{1, 2, 3}";

		assertTrue(expected.equals(test.toString()));
		assertTrue(test.size() == 3);
	}
	
	/**
	 * NOTE: size() is used a lot in testing other methods, so 
	 * it won't be here as thoroughly.
	 * 
	 * size measures correct size of default StringSet
	 */
	@Test
	public void size1() {
		StringSet test = new StringSet();
		
		assertTrue(test.size() == 0);
	}

	/**
	 * size works for empty and filled strings
	 */
	@Test
	public void size2() {
		StringSet test = new StringSet();

		test.insert("");
		test.insert("123");
		test.insert("11wefsa djsovij");
		
		assertTrue(test.size() == 3);
	}
		
	/**
	 * proper union functionality, showing the following:
	 * 
	 * 		count of union is the correct
	 * 		order of joining StringSet objects is correct
	 */
	@Test
	public void union1() {
		StringSet a = new StringSet();
		StringSet b = new StringSet();
		
		a.insert("1");
		a.insert("2");
		
		b.insert("a");
		b.insert("b");
		
		assertTrue(a.size() == 2 && b.size() == 2);
		
		StringSet result = a.union(b);
		
		assertTrue(result.size() == 4);
		assertTrue(result.toString().equals("{1, 2, a, b}"));
	}
	
	/**
	 * shows that union method doesn't allow duplicates
	 */
	@Test
	public void union2() {
		StringSet a = new StringSet();
		StringSet b = new StringSet();
		
		a.insert("1");
		a.insert("2");
		a.insert("a");
		a.insert("b");
		
		b.insert("a");
		b.insert("b");
		
		StringSet result = a.union(b);
		
		assertTrue(result.size() == 4);
		assertTrue(result.toString().equals("{1, 2, a, b}"));
	}
	
	
	/**
	 * shows case for empty StringSets in union method:
	 */
	@Test
	public void union3() {
		
		StringSet a = new StringSet(); 
		StringSet b = new StringSet();
		
		StringSet result = a.union(b);
		
		assertTrue(result.toString().equals("{}"));
		assertTrue(result.size() == 0);
		
		a.insert("asdf");
		result=a.union(b);
		
		assertTrue(result.toString().equals("{asdf}"));
		assertTrue(result.size() == 1);
		
	}
	
	/**
	 * shows the case when both StringSets are the same
	 */
	@Test
	public void union4() {
		
		StringSet a = new StringSet();
		StringSet b = new StringSet();
		
		a.insert("a");
		a.insert("b");
		
		b.insert("a");
		b.insert("b");
		
		StringSet result = a.union(b);
		
		assertTrue(result.size() == 2);
		assertTrue(result.toString().equals("{a, b}"));
	}
	
	
	/**
	 * shows that union method will fail if StringSet parameter is null;
	 */
	@Test
	public void union5() {
		
		StringSet a = new StringSet();
		StringSet b = null;
		
		try {
			a.union(b);
			fail();
		} catch (IllegalArgumentException anIllegalArgumentException) {
			return;
		}
		
//		//not sure if this is necessary as compiler catches NullPointerException
//		try {
//			b.union(a);
//			fail();
//		} catch (NullPointerException n) {
//			return;
//		}
	}
	
	/**
	 * shows proper functionality of intersection (common elements are merged)
	 */
	@Test
	public void intersection1() {
		
		StringSet a = new StringSet();
		StringSet b = new StringSet();
		
		a.insert("1");
		a.insert("2");
		a.insert("a");
		a.insert("b");
		
		b.insert("a");
		b.insert("b");
		
		StringSet result = a.intersection(b);
		
		assertTrue(result.size() == 2);
		assertTrue(result.toString().equals("{a, b}"));
	}
	
	/**
	 * when performing intersection on 2 StringSets w/ no commonalities, the
	 * resulting StringSet has 0 elements. Also, order of this scenario doesn't 
	 * make a difference. 
	 */
	@Test
	public void intersection2() {
		
		StringSet a = new StringSet();
		StringSet b = new StringSet();
		
		a.insert("1");
		a.insert("2");
		
		b.insert("a");
		b.insert("b");
		
		StringSet result = a.intersection(b);
		
		assertTrue(result.size() == 0);
		assertTrue(result.toString().equals("{}"));
		
		result = b.intersection(a);
		
		assertTrue(result.size() == 0);
		assertTrue(result.toString().equals("{}"));
	}
	
	/**
	 * shows that case StringSet is null;
	 */
	@Test
	public void intersection3() {
		
		StringSet a = new StringSet();
		StringSet b = null;
		
		try {
			a.intersection(b);
			fail();
		} catch (IllegalArgumentException e) {
			return;
		}
		
//		//not sure if this is necessary as compiler catches NullPointerException
//		try {
//			b.intersection(a);
//			fail();
//		} catch (NullPointerException e) {
//			return;
//		}
	}
	
	/**
	 * case for two empty StringSet objects
	 */
	@Test
	public void intersection4() {
		StringSet a = new StringSet();
		StringSet b = new StringSet();
		
		StringSet result = a.intersection(b);
		
		//shows that intersection of two empty StringSets is empty StringSet
		assertTrue(result.size() == 0);
		assertTrue(result.toString().equals("{}"));
		
		result = b.intersection(a);
		
		//shows that order of intersection on two empty StringSets doesn't matter
		assertTrue(result.size() == 0);
		assertTrue(result.toString().equals("{}"));
	}
	
	/**
	 * NOTE - toString method is used to test many of the other methods.
	 * Only simple cases will be tested, as other scenarios used toString
	 * as a verification.
	 */
	@Test
	public void toString1() {
		
		StringSet test = new StringSet();
		
		assertTrue(test.toString().equals("{}"));
		
		test.insert("this is a test");
		
		assertTrue(test.toString().equals("{this is a test}"));
		
		test.insert("1234");
		
		assertTrue(test.toString().equals("{this is a test, 1234}"));
		
	}
	
}
