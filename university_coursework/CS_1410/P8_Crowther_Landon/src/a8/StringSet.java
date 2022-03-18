package a8;

/**
 * Landon Crowther
 * u0926601
 * CS1410 - HW8
 * 
 * StringSet class: 
 * @author lando
 *
 */
public class StringSet {


	//creating instance variables
	private DynamicArray2 data;

	//StringSet constructor method
	public StringSet() {
		data = new DynamicArray2();
	}


	/**
	 * method that inserts string e into the StringSet object, assuming
	 * that e is not already part of the StringSet object. If e is already
	 * part of object, nothing happens.
	 * 
	 * if e = null, error will be thrown. 
	 * @param string
	 */
	public void insert(String e) {
		if (e == null) {
			throw new IllegalArgumentException();
		}

		if(contains(e) == false) {
			data.add(e);
		}
	}

	/**
	 * method that is called upon a StringSet object to determine if the 
	 * StringSet contains the String e
	 * 
	 * if e = null, error will be thrown. 
	 * 
	 * @param string that is being searched for
	 * @return	true if StringSet contains string, false otherwise;
	 */
	public boolean contains(String e) {

		//check to see if the string is null
		if (e == null) {
			throw new IllegalArgumentException();
		}

		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).equals(e)) {
				return true; 
			}
		}

		return false;
	}

	/**
	 * method that removes String e from a StringSet object, 
	 * assuming that e is already part of StringSet object. If
	 * e is not part of StringSet object, nothing is removed. 
	 * 
	 * If e = null, error will be thrown. 
	 * 
	 * @param string
	 */
	public void remove(String e) {

		//check to see if the string is null
		if (e == null) {
			throw new IllegalArgumentException();
		}

		//check to see if String e is in the StringSet s:
		if (this.contains(e) == true) {

			//search the DynamicArray2 for the String e
			for (int i = 0; i < data.size(); i++) {

				//check to see if the elements in the DynamicArray2 are 
				//equivalent to the parameter e:
				if (data.get(i).equals(e)) {

					//removes element e if it is found
					data.remove(i);
				}
			}
		}

	}
	
	/**
	 * Returns the number of strings in the StringSet object 
	 * @return integer size value
	 */
	public int size() {
		return data.size();
	}  

	/**
	 * method that combines to StringSet objects. Method works by first
	 * creating a new StringSet object, and then copying each non-repeated element
	 * of the parameter other. Then, the 	
	 * @param other
	 * @return
	 */
	public StringSet union(StringSet other) {

		//check to see if other is a null object
		if ( other == null) {
			throw new IllegalArgumentException();
		}

		//making StringSet
		StringSet result = new StringSet();	

		//adding all elements of THIS StringSet
		for (int i = 0; i < this.size(); i++) {
			result.insert(this.data.get(i));
		}

		//adding all elements of other StringSet that aren't there already
		for (int i = 0; i < other.size(); i++) {		
			result.insert(other.data.get(i));
		}

		return result;
	}

	/**
	 * 	Computes and returns the intersection of the StringSet that calls this method and the 
	 * StringSet argument to the method.
	 * The original StringSets should not be changed. The intersection set contains every 
	 * element that is in both of the StringSets and no other elements.
	 * Throws an IllegalArgumentException if other is null.
	 * @param other - StringSet object
	 * @return	new StringSet object that incorporates commanalities of both StringSets
	 */
	public StringSet intersection(StringSet other) {

		//check to see if other is a null object
		if ( other == null  ){
			throw new IllegalArgumentException();
		}


		//creating new StringSet object
		StringSet result = new StringSet();
		for (int i = 0; i < this.size(); i ++) {

			//loop that adds all elements that both StringSet objects have in common
			for (int j = 0; j < other.size(); j++) {
				if(this.data.get(i).equals(other.data.get(j))) {
					result.insert(this.data.get(i));
				}
			}			
		}
		return result;
	}

	// Returns a formatted string version of this set
	// Examples: If set contains "a" and "b", this method should 
	// return the string "{a, b}".  If the set is empty, this 
	// method should return the string "{}".

	/**
	 * Returns a formatted string version of this set
	 * Examples: If set contains "a" and "b", this method should 
	 * return the string "{a, b}".  If the set is empty, this 
	 * method should return the string "{}".
	 */
	public String toString() {

		String result = "{";
		if(this.size() > 0) { 
			result += this.data.get(0);
		}

		for(int i = 1; i < this.size(); i++) 
			result += ", " + this.data.get(i);

		return result + "}";

	}

}