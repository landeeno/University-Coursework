package inClassPractice;

public class DynamicArray {


	private String[] data;
	DynamicArray() {

	}


	public String getElement(int index) {
		if (index < 0 || index >= data.length) {
			throw new IndexOutOfBoundsException();
		}
		return data[index];
	}

	public void setElement(int index, String newValue) {
		if (index < 0 || index >= data.length) {
			throw new IndexOutOfBoundsException();
		}
		data[index] = newValue;
	}

	/**
	 * interesting cases to test:
	 * 
	 * insert at 0,
	 * insert in middle,
	 * insert at end
	 * 
	 * instructions:
	 * 		1) make new array of length + 1
	 * 		2) copy everything to the left of insert point
	 * 			for loop
	 * 		3) copy in new value
	 * 		4) copy everything to the right
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param i
	 * @param s
	 */
	public void add(int i, String s) {
		//making new array
		String[] newData = new String[data.length + 1];

		// copy stuff to the left
		for (int index = 0; index < i; index++) {
			newData[index] = data[index];
		}

		//copy middle
		newData[i] = s;

		//copy to right
		for (int index = i+1; index < newData.length; index++) 
			newData[index] = data[index - 1]; 
	}

	//special case for adding to the end
	public void add(String s) {
		add(data.length, s);
	}

	public void remove(int i) {
		if (index < 0 || index >= data.length)
			throw new IndexOutOfBoundsException();
		

	}



}



