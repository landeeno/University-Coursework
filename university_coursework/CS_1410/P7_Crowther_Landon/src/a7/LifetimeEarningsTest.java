package a7;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * Landon Crowther
 * u0926601
 * HW7 - Lifetime Earnings Comparison
 * @author Landon Crowther
 *
 */
public class LifetimeEarningsTest {

	/**
	 * Testing that the constructor works by comparing the currentSalary variable to the 
	 * value that the salary getter method produces
	 */
	@Test
	public void testConstructor1() {
		LifetimeEarnings test = new LifetimeEarnings(10, 100);
		assertTrue(10.00 == test.currentSalary());
	}

	/**
	 * Testing to see if every element in test1 and test2 are the same.
	 * Note -- created an equals method for to simplify testing
	 */
	@Test
	public void testConstructor2() {
		LifetimeEarnings test1 = new LifetimeEarnings(10, 100);	
		LifetimeEarnings test2 = new LifetimeEarnings(10, 100);	
		assertTrue(test1.equals(test2));
	}

	/**
	 * Testing that constructor can create more than 1 type of LifetimeEarnings object
	 * by creating two different objects and using my equals method
	 */
	@Test
	public void testConstructor3() {
		LifetimeEarnings a = new LifetimeEarnings(1, 10);	
		LifetimeEarnings b = new LifetimeEarnings(2, 10);	
		assertFalse(a.equals(b));
	}

	/**
	 * Shows that when year = 0, currentSalary instance variable remains unchanged.
	 */
	@Test
	public void testCurrentSalary1() {
		LifetimeEarnings test = new LifetimeEarnings(10, 100);
		assertTrue(10 == test.currentSalary());		
	}

	/**
	 * Test that currentSalary getter method gets the most updated currentSalary, even 
	 * if the year is different from the starting year
	 */
	@Test
	public void testCurrentSalary2() {
		LifetimeEarnings test = new LifetimeEarnings(10, 100);
		test.incrementYear();
		assertTrue(20 == test.currentSalary());
	}

	/**
	 * Tests that the currentSalary value remains the same if there is 
	 * a yearly raise percentage of 0
	 */
	@Test
	public void testCurrentSalary3() {
		LifetimeEarnings test = new LifetimeEarnings(10, 0);
		assertTrue(10 == test.currentSalary());	
		test.incrementYear();
		assertTrue(10 == test.currentSalary());
	}

	@Test
	public void testCurrentSalary4() {
		LifetimeEarnings a = new LifetimeEarnings(10, 100);
		LifetimeEarnings b = new LifetimeEarnings(10, 100);
		assertTrue( a.currentSalary() == b.currentSalary() );
	}

	/**
	 * tests that the private variable currentYear is in fact
	 * equal to 1 after executing setYear(1)
	 */
	@Test
	public void testSetYear1() {

		LifetimeEarnings test1 = new LifetimeEarnings(10, 100);
		test1.setYear(1);

		LifetimeEarnings test2 = new LifetimeEarnings(10, 100);		
		test2.incrementYear();

		assertTrue(test1.equals(test2));
	}

	/**
	 * Shows that setYear works after incrementing the year
	 */
	@Test
	public void testSetYear2() {
		LifetimeEarnings test = new LifetimeEarnings(10, 100);

		test.incrementYear();

		test.setYear(0);

		assertTrue(test.currentSalary() == 10);		
	}

	@Test
	public void testSetYear3() {
		LifetimeEarnings test = new LifetimeEarnings(10, 100);
		test.setYear(2);
		assertTrue(40 == test.currentSalary());
	}

	/**
	 * shows that the user can't set the year to a negative number
	 */
	@Test 
	public void testSetYear4() {
		LifetimeEarnings test = new LifetimeEarnings(10, 100);
		test.incrementYear();
		test.setYear(-5);
		assertTrue(20 == test.currentSalary());
	}

	// the incrementYear and setYear tests will look very similar:

	/**
	 * Using the currentSalary variable to confirm that incrementYear works
	 */
	@Test
	public void incrementYear1() {
		LifetimeEarnings test = new LifetimeEarnings(10, 100);
		assertTrue(10 == test.currentSalary());

		test.incrementYear();
		assertTrue(20 == test.currentSalary());
	}

	/**
	 * shows that increment year is equivalent to setting year to 1
	 */
	@Test
	public void incrementYear2() {
		LifetimeEarnings a = new LifetimeEarnings(10, 100);
		a.incrementYear();

		LifetimeEarnings b = new LifetimeEarnings(10, 100);
		b.setYear(1);

		assertTrue(a.equals(b));
	}

	/**
	 * shows that the computeLifetimeEarnings method produces the correct number
	 * of values for the array. ie using 3 as an input parameter, computeLifeteimeEarnings
	 * should produce an array of length three: year 0, year 1, year 2.
	 */
	@Test
	public void computeLifetimeEarnings1() {
		LifetimeEarnings test = new LifetimeEarnings(10, 100);
		assertTrue( 3 == test.computeLifetimeEarnings(3).length );
	}

	/**
	 * shows that the computeLifetimeEarnings method produces the correct values
	 */
	@Test
	public void computeLifetimeEarnings2() {
		LifetimeEarnings test = new LifetimeEarnings(10, 100);
		double[] expected = {10.00, 20.00, 40.00};
		assertArrayEquals(expected, test.computeLifetimeEarnings(3), 0.01);		
	}

	/**
	 * shows that if 0 is used as the parameter for computeLifeTime earnings,
	 * the method will return an empty array.
	 */
	@Test
	public void computeLifetimeEarnings3() {
		LifetimeEarnings test = new LifetimeEarnings(10, 100);
		double[] result = test.computeLifetimeEarnings(0);
		assertTrue( 0 == result.length );
	}

	/**
	 * shows that the parameter numYears for computeLifetimeEarnings 
	 * must be non-negative
	 */
	@Test
	public void computeLifetimeEarnings4() {
		LifetimeEarnings test = new LifetimeEarnings(10, 100);
		assertTrue( null == test.computeLifetimeEarnings(-1));
	}



	/**
	 * shows that the toString method produces the correct string
	 */
	@Test
	public void toString1() {
		LifetimeEarnings test = new LifetimeEarnings(10, 100);
		String expectedResult = "Current year: 0\nRaise percentage: 100.0\nStarting salary: 10.0\n";
		assertEquals( expectedResult, test.toString());
	}

	/**
	 * shows that the toString method produces the correct string after modifying
	 * the object in two different ways
	 */
	@Test
	public void toString2() {
		LifetimeEarnings test = new LifetimeEarnings(10, 100);
		test.incrementYear();
		String expectedResult = "Current year: 1\nRaise percentage: 100.0\nStarting salary: 10.0\n";
		assertEquals( expectedResult, test.toString() );

		test.setYear(5);
		String expectectedResult2 = "Current year: 5\nRaise percentage: 100.0\nStarting salary: 10.0\n";
		assertEquals( expectectedResult2, test.toString() );
	}

	/**
	 * shows that toStiring works even after performing two of the same modifications original
	 * object
	 */
	@Test
	public void toString3() {
		LifetimeEarnings test1 = new LifetimeEarnings(10, 100);
		test1.computeLifetimeEarnings(5);

		LifetimeEarnings test2 = new LifetimeEarnings(10, 100);
		test2.setYear(5-1);

		assertEquals( test1.toString(), test2.toString() );


	}

	/**
	 * shows proper functionality of sumWages method
	 */
	@Test
	public void sumWages1() {
		LifetimeEarnings test = new LifetimeEarnings(10, 100);
		double[] sal = test.computeLifetimeEarnings(3);
		double expected = 10 + 20 + 40;
		assertTrue( expected == LifetimeEarningsComparison.sumWages(sal));
	}

	/**
	 * shows case for a zero-length array parameter in sumWages
	 */
	@Test
	public void sumWages2() {
		double[] empty = {};
		double expected = 0.0;
		double result = LifetimeEarningsComparison.sumWages(empty);
		assertEquals(expected, result, 0.01);
	}

	/**
	 * shows that sumWages works for an array with negative and 0 double entries
	 */
	@Test 
	public void sumWages3() {
		double[] negativeArr = {-1.0, 0.00, 1.00, 1.50};
		double expected = -1 + 0 + 1 + 1.50;
		double result = LifetimeEarningsComparison.sumWages(negativeArr);
		assertEquals( expected, result, 0.01);
	}
	
	/**
	 * shows that sumWages works for length 1 arrays
	 */
	@Test
	public void sumWages4() {
		double[] single = {3.00};
		double expected = 3.00;
		double result = LifetimeEarningsComparison.sumWages(single);
		assertEquals(result, expected, 0.01);
	}

}
