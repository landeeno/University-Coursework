package hash_tables;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


public class Hash_MapTest
{
	Hash_Map<My_String,My_String> testHash;

	@Before
	public void setUp() throws Exception
	{
		//Change only this line to test each implementation
		testHash = new Hash_Table_Linear_Probing<>(50);
		//testHash = new Hash_Table_Quadratic_Probing<>(50);
		//testHash = new Hash_Table_Chaining<>(50);
	}

	@Test
	public void testInsert()
	{
		//Insert should increase the size and store the element
		assertEquals(0, testHash.size());
		testHash.insert(new My_String("TestKey"), new My_String("TestVal"));
		assertEquals(1, testHash.size());
		assertEquals(new My_String("TestVal"), testHash.find(new My_String("TestKey")));
		//Insert should overwrite values with the same key
		testHash.insert(new My_String("TestKey"), new My_String("TestValReplace"));
		assertEquals(1, testHash.size());
		assertEquals(new My_String("TestValReplace"), testHash.find(new My_String("TestKey")));
		
	}

	@Test
	public void testFind()
	{
		//Should return null if the key isn't in the hash map
		assertEquals(null,testHash.find(new My_String("TestKey")));
		//Should find a value with a given key
		testHash.insert(new My_String("TestKey"), new My_String("TestVal"));
		assertEquals(new My_String("TestVal"), testHash.find(new My_String("TestKey")));
	}

	@Test
	public void testClear()
	{
		//Should reset size and remove elements
		testHash.insert(new My_String("TestKey1"), new My_String("TestVal1"));
		testHash.insert(new My_String("TestKey2"), new My_String("TestVal2"));
		testHash.insert(new My_String("TestKey3"), new My_String("TestVal3"));
		assertEquals(3, testHash.size());
		assertEquals(new My_String("TestVal1"), testHash.find(new My_String("TestKey1")));
		testHash.clear();
		assertEquals(0, testHash.size());
		assertEquals(null, testHash.find(new My_String("TestKey1")));
	}

	@Test
	public void testCapacity()
	{
		//Capacity was initialized in the before setup of this class to be 50
		if (testHash instanceof Hash_Table_Chaining)
		{
			//Should be as initialized at 50
			assertEquals(50, testHash.capacity());
			//Adding should not affect capacity
			testHash.insert(new My_String("TestKey"), new My_String("TestVal"));
			assertEquals(50, testHash.capacity());
		}
		else //Linear or quadratic probing
		{
			//Should be the next prime after 50, 53
			assertEquals(53, testHash.capacity());
			//Adding should not affect capacity
			testHash.insert(new My_String("TestKey"), new My_String("TestVal"));
			assertEquals(53, testHash.capacity());
		}
		
		
	}

	@Test
	public void testSize()
	{
		//Size should be number of elements, should increase with insert
		assertEquals(0, testHash.size());
		testHash.insert(new My_String("TestKey"), new My_String("TestVal"));
		assertEquals(1, testHash.size());
		//Overwriting elements should not effect size
		testHash.insert(new My_String("TestKey"), new My_String("Override"));
		assertEquals(1, testHash.size());
	}

	@Test
	public void testPrint_stats()
	{
		
		testHash.insert(new My_String("TestKey"), new My_String("TestVal1"));
		testHash.insert(new My_String("TestKey"), new My_String("TestOverlap1"));
		testHash.insert(new My_String("TestKey2"), new My_String("TestVal2"));
		testHash.insert(new My_String("TestKey2"), new My_String("TestOverlap2"));
		
		//[1] collisions (per find/insert): 4 Inserts, 0 Collision
		//[2] number of elements: 2 elements
		//[3] the capacity.

		ArrayList<Double> testResult = testHash.print_stats();
		
		
		
		if (testHash instanceof Hash_Table_Chaining)
		{
			//Collisions are defined differently in chaining
			//Should be a collision for each element searched in the lists
			//Lists were searched twice, with 1 element each, 0 expected collisions
			
			assertEquals(0, testResult.get(0),0.01);
			assertEquals(2, testResult.get(1), 0);
			assertEquals(50, testResult.get(2),0);
		}
		else //Linear or quadratic probing
		{
			assertEquals(0, testResult.get(0),0.01);
			assertEquals(2, testResult.get(1), 0);
			assertEquals(53, testResult.get(2),0);
		}
	}

	@Test
	public void testResize()
	{
		if (testHash instanceof Hash_Table_Chaining)
		{
			//Current capacity is 50
			assertEquals(50, testHash.capacity());
			
			//Elements should remain after expansion
			testHash.insert(new My_String("TestKey"), new My_String("TestVal"));
			assertEquals(new My_String("TestVal"), testHash.find(new My_String("TestKey")));
			
			testHash.resize(60);
			
			//Should change capacity to given value
			assertEquals(60, testHash.capacity());
		}
		else //Linear or quadratic probing
		{
			//Current capacity is 53
			assertEquals(53, testHash.capacity());
			
			//Elements should remain after expansion
			testHash.insert(new My_String("TestKey"), new My_String("TestVal"));
			assertEquals(new My_String("TestVal"), testHash.find(new My_String("TestKey")));
			
			testHash.resize(60);
			
			//Should change capacity to next prime number after given value
			//61 is first prime after 60
			assertEquals(61, testHash.capacity());
		}
		
		assertEquals(new My_String("TestVal"), testHash.find(new My_String("TestKey")));
		
	}

}
