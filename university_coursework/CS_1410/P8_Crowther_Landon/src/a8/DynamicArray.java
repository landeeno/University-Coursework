package a8;

/**
 * A DynamicArray behaves like an array of strings, except that it can grow and
 * shrink. It is indexed beginning with zero. When a DynamicArray is created, it
 * is empty. Operations are provided to add, get, set, and remove elements.
 * 
 */
public class DynamicArray {

	private String[] data; // the backing array

	/**
	 * Creates an empty dynamic array.
	 */
	public DynamicArray() {
		data = new String[0];
	}

	/**
	 * Adds a string s to the dynamic array at index i.
	 * 
	 * @param i
	 * @param s
	 */
	public void add(int i, String s) {
		if(i < 0 || i > data.length)
			throw new IndexOutOfBoundsException();

		String[] newData = new String[data.length + 1];

		for(int j = 0; j < i; j++) {
			newData[j] = data[j];
		}

		newData[i] = s;

		for(int j = i + 1; j < newData.length; j++) {
			newData[j] = data[j - 1];
		}

		data = newData;
	}

	/**
	 * Adds string s to end of the dynamic array.
	 * 
	 * @param s
	 */
	public void add(String s) {
		add(data.length, s);
	}

	/**
	 * Returns the string stored at index i in the dynamic array.
	 * 
	 * @param i
	 * @return the string stored at index i
	 */
	public String get(int i) {
		if(i < 0 || i >= data.length)
			throw new IndexOutOfBoundsException();

		return data[i];
	}

	/**
	 * Returns the number of elements in the dynamic array.
	 * 
	 * @return the number of elements
	 */
	public int size() {
		return data.length;
	}

	/**
	 * Changes the value of the string stored at index i in the dynamic array to
	 * s.
	 * 
	 * @param i
	 * @param s
	 */
	public void set(int i, String s) {
		if(i < 0 || i >= data.length)
			throw new IndexOutOfBoundsException();

		data[i] = s;
	}

	/**
	 * Removes the string at index i in the dynamic array.
	 * 
	 * @param i
	 */
	public void remove(int i) {
		if(i < 0 || i >= data.length)
			throw new IndexOutOfBoundsException();

		String[] newData = new String[data.length - 1];

		for(int j = 0; j < i; j++) {
			newData[j] = data[j];
		}

		for(int j = i; j < newData.length; j++) {
			newData[j] = data[j + 1];
		}

		data = newData;
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
		return result + "]";
	}
}
