
package a8;
/**
 * 
 * @author Landon Crowther
 * u0926601
 * CS 1410 - HW8
 * 
 * DynamicArray2 Class:
 *
 */
//A more efficient version of the DynamicArray class created in lecture.
//
//Represents the dynamic array [data[0], data[1], ..., data[count-1]]
//In other words, the first count elements of data are elements of the 
//dynamic array. The remainder of data is room to grow.  When data fills 
//up, we allocate a new array that is twice as long. 
//0 <= count <= data.length is always true.
public class DynamicArray2 {
	/**
	 * declaring instance variables:
	 */
	private String[] data;   // the backing array
	private int count;       // the number of elements in the dynamic array

	/**
	 * Creates an empty dynamic array with room to grow.
	 * (DO NOT modify this method.)
	 */
	public DynamicArray2() {
		// Start with a few available spaces
		data = new String[8];
		count = 0;
	}

	/**
	 * GETTER METHOD
	 * Returns the number of elements in the dynamic array.
	 * 
	 * @return the number of elements
	 */
	public int size() {
		return count;
	}

	/**
	 * This method utilizes the add(int i, String s) by adding the parameter
	 * string to the end of the DynamicArray2 object
	 * @param s - String
	 */
	// Appends s  the end of the dynamic array
	public void add(String s) {
		add(count, s);
	}

	/**
	 * add(int i, String s) adds the String s to the DynamicArray2 objct at integer i
	 * if integer i is (-) or out of bounds of the legal DynamicArray2 boundaries,
	 * IndexOutOfBounds error will be thrown.
	 * @param i - index
	 * @param s - String
	 */
	public void add(int i, String s) {	

		//throws error if index is greater than count -- method will run as long as 
		//the index is either a current element in data, or the next element (next empty space)
		if(i < 0 || i > count)
			throw new IndexOutOfBoundsException();

		// If there is no room for s in data, create a new array 
		// that is twice as long as data. Copy the contents of data 
		// over to this new array. Set data (the reference to the 
		// backing array) to this new array.
		//modifies array size if current array is full
		if (count == data.length) 
			recreate(data);	

		//statement that applies to most scenarios -- data already has values 
		if(data[0] != null) {
			//creating new variable j so that count isn't modified
			int j = count;
			//loop that shifts all the data to the right of i
			while (j != i) {	
				data[j] = data[j-1];
				j--;
			}
			//replace data after the rest of the array has been modified
			data[i] = s;
		}
		//specific case for if the array is initially empty
		else {
			data[i] = s;
		}

		// Update count.
		count++;

	}



	/**
	 * Method that removes element of data at specified index i. Throws error if
	 * i is not a valid index
	 * 
	 * @param i - index that 
	 */
	public void remove(int i) {	

		//check to see if index that is being removed is valid
		if(i < 0 || i >= count)
			throw new IndexOutOfBoundsException();

		//loop that shifts each element 
		while ( i <= count) {
			data[i] = data[i+1];
			i++;
		}

		//update the count
		count--;

	}

	/**
	 * getter method that returns the element of data at a specific integer
	 * @param i - integer to evaluate at
	 * @return value of data at index i
	 */
	public String get(int i) {

		//test to see if index is valid
		if(i < 0 || i >= count)
			throw new IndexOutOfBoundsException();
		//return value of index
		return data[i];
	}

	/**
	 * method that replaces a specific index in a pre-existing DynamicArray2 object
	 * @param i - index to replace
	 * @param s - string replace parameter index
	 */
	public void set(int i, String s) {
		//test to see if index is valid
		if(i < 0 || i >= count)
			throw new IndexOutOfBoundsException();
		//replace data at index i
		data[i] = s;
	}

	/**
	 * Returns a formatted string version of this dynamic array.
	 * 
	 * @return the formatted string
	 */
	public String toString() {
		String result = "[";
		if(size() > 0) 
			result += get(0);

		for(int i = 1; i < size(); i++) 
			result += ", " + get(i);

		return result + "]";
	}

	/**
	 * Private Method -- method only called within this class
	 * 
	 * This method is only called when the the current data String[] array is full, and
	 * other elements of are being added to data. This method works by creating a 
	 * temporary array thats is equivalent to data, then re-defining data so that 
	 * it's a new array of twice the length. Then, each element from the temporary array is 
	 * copied over to the new data array. The result is a "data" array that contains the same 
	 * values, but the length is twice as long. 
	 * 
	 * @param data array that will be copied
	 */
	private void recreate(String[] arr) {

		//save current data
		String[] current = arr;

		//make new array
		data = new String[2*count];

		//replace all the old data in the new array
		for (int i = 0; i < current.length; i++) {
			data[i] = current[i];
		}

	}

}
