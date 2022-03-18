package a9;

public abstract class DynamicArrayAbstract<T> {

	// Make the instance variable protected as the subclasses will be tightly
	// integrated with this class.
	protected T[] data;	// the backing array
	protected int count; // The number of elements used in the data array
	/**
	 * Creates an empty dynamic array.
	 */

	
	@SuppressWarnings("unchecked")
	public DynamicArrayAbstract() {
		data = (T[]) new Object[0];
		count = 0;
	}
	

	/***
	 * Given an index i, returns a reference to an array that
	 * has space at i for a new value, and old values in the data 
	 * array copied into their appropriate locations. The count is 
	 * changed by 1 to account for t he "opening" in the array.
	 * @param i
	 * @return
	 */
	
	public abstract T[] growthStrategy(int i);

	/**
	 * Adds a string s to the dynamic array at index i.
	 * 
	 * @param i
	 * @param s
	 */
	public void add(int i, T object) {
		if(i < 0 || i > this.size())
			throw new IndexOutOfBoundsException();

		// We have abstracted out the code that grows the array
		T[] newData = growthStrategy(i);
		newData[i] = object;
		data = newData;
	}

	/**
	 * Adds string s to end of the dynamic array.
	 * 
	 * @param s
	 */
	public void add(T object) {
		add(count, object);
	}

	/**
	 * Returns the string stored at index i in the dynamic array.
	 * 
	 * @param i
	 * @return the string stored at index i
	 */
	public T get(int i) {
		if(i < 0 || i >= size())
			throw new IndexOutOfBoundsException();

		return data[i];
	}

	/**
	 * Returns the number of elements in the dynamic array.
	 * 
	 * @return the number of elements
	 */
	public int size() {
		return count;
	}

	/**
	 * Changes the value of the string stored at index i in the dynamic array to
	 * s.
	 * 
	 * @param i
	 * @param s
	 */
	public void set(int i, T object) {
		if(i < 0 || i >= size())
			throw new IndexOutOfBoundsException();

		data[i] = object;
	}

	/***
	 * Given an index i, returns a reference to an array that
	 * has the value at i removed and the array values copied to their appropriate locations. 
	 * The count is changed by -1 to account for the "removal" in the array.
	 * @param i
	 * @return
	 */
	
	public abstract T[] shrinkStrategy(int i);

	/**
	 * Removes the string at index i in the dynamic array.
	 * 
	 * @param i
	 */
	public void remove(int i) {
		if(i < 0 || i >= size())
			throw new IndexOutOfBoundsException();

		data = shrinkStrategy(i);
	}

	/**
	 * Returns the dynamic array as a formatted string.
	 * 
	 * @return the formatted string
	 */
	public String toString() {
		String result = "[";
		if(size() > 0) {
			result += get(0);
		}
		for(int i = 1; i < size(); i++) {
			result += ", " + get(i);
		}
		return result + "]"+" real length: " + data.length;
	}	
	
	
}
