
package lists_2420;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import lists_2420.Linked_List_2420.Node;

/**
 * @author Landon Crowther & Brent Collins
 * 
 *         This class implements the methods in the List_2420 class. It uses an
 *         Integer[] array to keep track of everything, and it wraps around when
 *         needed. First and Last always have null values in between.
 * 
 */
public class Array_List_2420 implements List_2420
{

	// index associated with first value in backingArray
	private int firstIndex;
	// index associated with last value in backingArray
	private int lastIndex;
	// backing array that contains all the data
	private Integer[] backingArray;
	// counter for number of elements in backingArray
	private int size;

	public Array_List_2420()
	{
		backingArray = new Integer[1024];
		// initialize first and last indices to the first element
		firstIndex = 0;
		lastIndex = 0;
		size = 0;
	}

	/**
	 * Alternate Constructor. The only time this is ever used is for testing. Is
	 * used to test the expand() method. Simplifies things from a 1024 backing
	 * to a newStartSize backing.
	 * 
	 * @param newStartSize
	 *            - size of backing array
	 */
	public Array_List_2420(int newStartSize)
	{
		backingArray = new Integer[newStartSize];
		// initialize first and last indices to the first element
		firstIndex = 0;
		lastIndex = 0;
		size = 0;
	}

	/**
	 * Alternate Constructor. The only time this constructor will ever be used
	 * is when calling the to_ArrayList_post_recurse() method. See Javadoc.
	 *
	 * @param copy
	 *            - Array_List_2420 object we are trying to duplicate
	 */
	public Array_List_2420(Array_List_2420 copy)
	{
		this.backingArray = copy.backingArray;
		this.firstIndex = copy.firstIndex;
		this.lastIndex = copy.lastIndex;
		this.size = copy.size;
	}

	/**
	 * add data to the beginning of array and shif appropriately
	 */
	@Override
	public void add_first(Object data)
	{

		// backingArray is empty, add to very beginning
		if (size == 0)
		{
			backingArray[0] = (Integer) data;
			size++;
			return;
		}
		// make sure that the firstIndex and lastIndex are the same for
		// 1-element array
		else if (this.size == 1)
		{
			lastIndex = firstIndex;
		}

		expand();

		// first part of array is full, add to the end of array
		if (firstIndex == 0)
		{
			this.backingArray[this.backingArray.length - 1] = (Integer) data;
			firstIndex = this.backingArray.length - 1;
		}

		/*
		 * firstIndex is somewhere with null values before it, place data
		 * parameter in the empty spot
		 */
		else
		{
			this.backingArray[firstIndex - 1] = (Integer) data;
			firstIndex--;
		}

		size++;
	}

	/**
	 * add data to the end of the backingArray and shif appropriately
	 */
	@Override
	public void add_last(Object data)
	{
		// backing array is empty, call the add_first method
		if (this.size == 0)
		{
			add_first(data);
		}

		// backing array is NOT empty
		else
		{
			expand();
			this.backingArray[lastIndex + 1] = (Integer) data;
			lastIndex++;
			size++;
		}

	}

	/**
	 * add_middle()
	 * 
	 * inserts data after a specified number of elements
	 * 
	 * @param after
	 *            - number of elements after. For example if after is 2 on the
	 *            array [0, 1, 2], we would add after the second element (so in
	 *            between the 1 and 2)
	 * 
	 * @param -
	 *            data data that we want to insert
	 * 
	 *
	 */
	@Override
	public void add_middle(int after, Object data)
	{

		if (after >= size)
		{
			add_last(data);
			return;
		}

		// adding after the 0th elmement is same as adding first
		if (after < 0)
		{
			add_first(data);
			return;
		}

		expand();

		int length = this.getBackingArrayLength();
		int numberOfElementsToShift = this.size - after - 1;
		int counter = 0;

		/*
		 * shifts properly regardless of structure
		 * 
		 * ie [1, 2, 3, null, null, null] or [3, null, null, null, 1, 2]
		 * 
		 * where "firstIndex" is wherever the (1) is located and "lastIndex" is
		 * wherever the (3) is located
		 */
		int i = lastIndex + length;
		while (counter < numberOfElementsToShift)
		{
			backingArray[(i + 1) % length] = backingArray[i % length];
			i--;
			counter++;
		}
		// insert data into proper spot
		backingArray[(i + 1) % length] = (Integer) data;
		// update lastIndex properly
		lastIndex = (lastIndex + 1) % length;

		size++;

	}

	/**
	 * resets the backingArray by overriding the current backingArray and
	 * resetting first and last indexs
	 */
	@Override
	public void clear()
	{
		this.backingArray = new Integer[1024];
		firstIndex = 0;
		lastIndex = 0;
	}

	/**
	 * iteratively determine the item is in the backingArray return true if it
	 * is, false otherwise
	 */
	@Override
	public boolean contains(Object item)
	{
		for (int i = firstIndex; i < firstIndex + this.size; i++)
		{
			if (backingArray[i % backingArray.length].equals(item))
				return true;
		}

		return false;
	}

	/**
	 * recursively search for an object. Uses helper method
	 */
	@Override
	public boolean contains_recursive(Object item)
	{
		return containsRecursiveHelper(firstIndex, firstIndex + this.size, item);
	}

	/**
	 * Given start, end, and item, see if the item is within the start and end
	 * boundaries.
	 * 
	 * @param start
	 *            - start index
	 * @param end
	 *            - end index
	 * @param item
	 *            - item that we are looking for
	 * @return
	 */
	private boolean containsRecursiveHelper(int start, int end, Object item)
	{
		if (start > end)
			return false;

		if (backingArray[start % this.getBackingArrayLength()] == item)
			return true;

		return containsRecursiveHelper(start + 1, end, item);
	}

	/**
	 * return the object associated with firstIndex throw exception if
	 * backingArray is empty
	 */
	@Override
	public Object get_first() throws NoSuchElementException
	{

		if (this.backingArray[firstIndex] != null)
			return this.backingArray[firstIndex];
		else
			throw new NoSuchElementException();

	}

	/**
	 * return the object associated with lastIndex throw exception if
	 * backingArray is empty
	 */
	@Override
	public Object get_last() throws NoSuchElementException
	{
		if (backingArray[lastIndex] != null)
		{
			return backingArray[lastIndex];
		} else
			throw new NoSuchElementException();
	}

	/**
	 * remove value associated with firstIndex, throw exception if backingArray
	 * is all null
	 * 
	 * return the removed object
	 */
	@Override
	public Object remove_first() throws NoSuchElementException
	{
		if (backingArray[firstIndex] == null)
		{
			throw new NoSuchElementException();
		}

		else
		{
			Integer temporary = backingArray[firstIndex];
			backingArray[firstIndex] = null;
			if (firstIndex != backingArray.length - 1)
				firstIndex++;
			else
				firstIndex = 0;
			size--;
			return temporary;
		}
	}

	/**
	 * remove the value associated with lastIndex throw exception if value
	 * doesn't exist or is empty
	 * 
	 * return the removed object
	 * 
	 */
	@Override
	public Object remove_last() throws NoSuchElementException
	{
		if (backingArray[lastIndex] == null)
		{
			throw new NoSuchElementException();
		}

		else
		{
			Object temporary = backingArray[lastIndex];
			backingArray[lastIndex] = null;
			lastIndex--;
			
			if (lastIndex < 0)
			{
				lastIndex = backingArray.length - 1;
			}
			
			size--;
			return temporary;
		}
	}

	/**
	 * compute the size iteratively
	 * 
	 * loop through each element in backingArray and add up the non-null
	 * elements
	 */
	@Override
	public int size()
	{
		int count = 0;
		for (int i = 0; i < backingArray.length; i++)
		{
			if (backingArray[i] != null)
			{
				count++;
			}
		}
		return count;
	}

	/**
	 * Reverses the order of the elements in the backingArray by swapping and
	 * appropriately adjusting indicieds.
	 * 
	 * Utilizes a while loop and a counter to keep track of when to stop
	 * swapping.
	 * 
	 * A few if statements are included to insure proper bounds of first and
	 * last index
	 * 
	 */
	@Override
	public void reverse()
	{
		int count = 0;
		int lastOriginal = lastIndex;
		int firstOriginal = firstIndex;
		while (count < size)
		{
			// swap first and last
			Integer temp = backingArray[lastIndex];
			backingArray[lastIndex] = backingArray[firstIndex];
			backingArray[firstIndex] = temp;

			firstIndex++;
			lastIndex--;

			// check for wrap around on firstIndex
			if (firstIndex >= backingArray.length)
			{
				firstIndex = 0;
			}

			// check for wrap around on lastIndex
			if (lastIndex < 0)
			{
				lastIndex = backingArray.length - 1;
			}

			if (lastIndex == firstIndex)
			{
				// we're done swapping
				firstIndex = firstOriginal;
				lastIndex = lastOriginal;
				return;
			}

			count = count + 2;
		}

		this.firstIndex = firstOriginal;
		this.lastIndex = lastOriginal;

	}

	/**
	 * compute the size of Array_List recursively.
	 * 
	 * NOTE - as stated in project description, this method doesn't have to be
	 * recursive for the array implementation.
	 */
	@Override
	public int compute_size_recursive()
	{
		return this.size;
	}

	/**
	 * Because we are working in the Array_List_2420 class, we DON'T need to
	 * implement a recursive solution here. We simply need to use an iterative
	 * solution.
	 * 
	 * That being said, the post_recurse() method asks for the values in REVERSE
	 * ORDER. To simplify things, we decided to make a new Array_List_2420 by
	 * COPYING another Array_List_2420 object.
	 * 
	 * After making the copy, we can call the reverse() method on the copy, and
	 * constructing an ArrayList from the reversed copy will be much simpler
	 * 
	 * NOTE - a superior program would compute this method without having to
	 * make a duplicate Array_List_2420. This will have implications for very
	 * large array sizes //FIXME fix if time allows
	 * 
	 * @return - ArrayList of values in reverse sorted order
	 * 
	 */
	@Override
	public ArrayList<Object> to_ArrayList_post_recurse()
	{
		// do iteratively for this method - can use iterative solution
		ArrayList<Object> result = new ArrayList<>();

		Array_List_2420 copy = new Array_List_2420(this);
		copy.reverse();

		result = copy.to_ArrayList();

		return result;
	}

	/**
	 * Given an Array_List_2420 , add each element to an ArrayList and return
	 * that ArrayList
	 */
	@Override
	public ArrayList<Object> to_ArrayList()
	{
		ArrayList<Object> result = new ArrayList<>();

		int size = this.size;
		int length = this.getBackingArrayLength();

		for (int i = firstIndex; i < firstIndex + size; i++)
		{
			if (i < length)
			{
				result.add(backingArray[i]);
			} else
			{
				result.add(backingArray[i % length]);
			}
		}

		return result;
	}

	/**
	 * Helper method
	 * 
	 * If the backingArray is "full" (ie no more "null" elements), double the
	 * size.
	 * 
	 * This will change the order of elements. For example, if we have the
	 * array:
	 * 
	 * [3, 4, 1, 2], where value of (1) is "firstIndex" and value of (4) is the
	 * "lastIndex"
	 * 
	 * when we call expand, the result will be as follows:
	 * 
	 * [1, 2, 3, 4, null, null, null, null];
	 * 
	 * This will be the same if the original aray was [1, 2, 3, 4];
	 */
	protected void expand()
	{

		int backingArrayLength = this.backingArray.length;

		if (this.size < backingArrayLength)
			return;

		Integer[] copy = new Integer[2 * backingArrayLength];

		/*
		 * use mod operator to properly add values from original backingArray to
		 * the copy.
		 */
		for (int i = 0; i < backingArrayLength; i++)
		{
			copy[i] = backingArray[firstIndex % backingArrayLength];
			firstIndex++;
		}

		this.backingArray = copy;
		this.firstIndex = 0;
		this.lastIndex = this.size - 1;

	}

	/**
	 * Helper method to make code cleaner and used in testing purposes
	 * 
	 * @return - length of backing array (including null )
	 */
	public int getBackingArrayLength()
	{
		return this.backingArray.length;
	}
	
	/**
	 * Creates a string that describes the contents of the list, starting with
	 * the size in parentheses for example, a list of the numbers 0, 1, 2, 3
	 * would print as:
	 * 
	 * "(4) [0]--> [1]--> [2]--> [3]--> null"
	 *
	 * an empty list should simply return the string "empty"
	 *
	 * 
	 * @return a string representation of this chain of nodes
	 */
	public String toString()
	{
		String returnString = "(" + size + ") ";
		for (int i = firstIndex; i < firstIndex  + size; i++)
		{
			returnString += "[" + backingArray[i % size] + "]--> ";
		}
		
		returnString += "null";
		
		return returnString;
	}
	

}
