package stars;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Geometry_Vector_Test
{
	
	Geometry_Vector vector1;
	Geometry_Vector vector2;
	
	/**
	 * This tests that the constructor works. The constructor is also
	 * being tested during each method in the setUp() method, so only
	 * 1 test is necessary to test the main constructor.
	 */
	@Test
	public void test_first_constructor()
	{
		Geometry_Vector expected_result = new Geometry_Vector(3,4);
		assertEquals(vector1, expected_result);
	}
	
	/**
	 * test constructor that makes a copy
	 */
	@Test
	public void test_second_constructor()
	{
		Geometry_Vector expected_result = new Geometry_Vector(vector1);
		assertEquals(expected_result, vector1);
		
		Geometry_Vector expected_result_2 = new Geometry_Vector(vector2);
		assertEquals(expected_result_2, vector2);
		
		Geometry_Vector new_vector = new Geometry_Vector(1,5);
		Geometry_Vector copy_of_new_vector = new Geometry_Vector(new_vector);
		assertEquals(new_vector, copy_of_new_vector);
	}
	
	@Test
	public void test_add_to_me_1() 
	{
		Geometry_Vector expected_result = new Geometry_Vector(-6, 16);
		vector1.add_to_me(vector2);
		assertEquals(expected_result, vector1);
	}
	
	@Test
	public void test_add_to_me_2()
	{
		Geometry_Vector opposite_of_vector1 = new Geometry_Vector(-3,-4);	
		vector1.add_to_me(opposite_of_vector1);
		Geometry_Vector expected_result = new Geometry_Vector(0,0);
		assertEquals(expected_result, vector1);
	}
	
	@Test
	public void test_subtract_from_me_1()
	{
		Geometry_Vector test_vector= new Geometry_Vector(25, 10);
		Geometry_Vector subtract_vector = new Geometry_Vector(5, 10);
		
		test_vector.subtract_from_me(subtract_vector);
		
		Geometry_Vector expected_result = new Geometry_Vector(20, 0);
		
		assertEquals(expected_result, test_vector);
	}
	
	@Test
	public void test_subtract_from_me_2()
	{
		Geometry_Vector test_vector= new Geometry_Vector(17, 12);
		Geometry_Vector subtract_vector = new Geometry_Vector(7, 8);
		
		test_vector.subtract_from_me(subtract_vector);
		
		Geometry_Vector expected_result = new Geometry_Vector(10, 4);
		
		assertEquals(expected_result, test_vector);
	}
	
	@Test
	public void test_divide_by_1()
	{
		vector1.divide_by(10);
		Geometry_Vector expected_result = new Geometry_Vector(0.3, 0.4);
		assertEquals(vector1, expected_result);
	}
	
	@Test
	public void test_divide_by_2()
	{
		vector2.divide_by(10);
		Geometry_Vector expected_result = new Geometry_Vector(-0.9	, 1.2);
		assertEquals(vector2, expected_result);
	}
	
	@Test
	public void test_multiply_me_by_1()
	{
		vector1.multiply_me_by(10);
		Geometry_Vector expected_result = new Geometry_Vector(30, 40);
		
		assertEquals(expected_result, vector1);
	}
	
	@Test
	public void test_multiply_me_by_2() 
	{
		vector2.multiply_me_by(10);
		Geometry_Vector expected_result = new Geometry_Vector(-90, 120);
		assertEquals(expected_result, vector2);
	}
	
	@Test
	public void test_magnitude_1()
	{
		assertTrue(vector1.magnitude() == 5);
	}
	
	@Test
	public void test_magnitude_2()
	{
		assertTrue(vector2.magnitude() == 15);
	}
	
	@Test 
	public void test_toString_1()
	{
		String result = new String(vector1.toString());
		String expected = new String("(3.0,4.0)" + "\n magnitude is :" + "5.0");
		assertEquals(result, expected);
	}
	
	@Test 
	public void test_toString_2()
	{
		String result = new String(vector2.toString());
		String expected = new String("(-9.0,12.0)" + "\n magnitude is :" + "15.0");
		//System.out.println(expected);
		assertEquals(result, expected);
	}
	
	@Test
	public void test_normalize_1()
	{
		vector1.normalize();
		Geometry_Vector expected_result = new Geometry_Vector((double)3/5, (double)4/5);
		assertEquals(expected_result.x, vector1.x, 0.001);
		assertEquals(expected_result.y, vector1.y, 0.01);
	}
	
	@Test
	public void test_normalize_2()
	{
		vector2.normalize();
		Geometry_Vector expected_result = new Geometry_Vector((double)-9/15, (double)12/15);
		assertEquals(expected_result, vector2);
	}
	

	@Before
	public void setUp() throws Exception
	{
		vector1 = new Geometry_Vector(3, 4);
		vector2 = new Geometry_Vector(-9,12);
		
	}



	
	

}
