package a9;

/**
 * Landon Crowther
 * u0926601
 * CS 1410 - Assignment A9
 * @author lando
 *
 * @param <T>
 */

public class DynamicArrayDouble<T> extends DynamicArrayAbstract<T> {

	/**
	 * constructor for the DynamicArrayDouble 
	 */
	public DynamicArrayDouble() {
		super();
	}

	/**
	 * growth strategy for adding elements. When array is full,  
	 * new array will be twice length of current array plus one. 
	 * For example, an array of length 3 would go to length 7:
	 * 			3*2 + 1 = 7
	 */
	@Override
	public T[] growthStrategy(int i) {

		//if statement that determines if array length needs to be adjusted
		if (count == data.length) {		
			//create array of length*2 + 1 (for 0-length arrays)
			T[] newData = (T[]) new Object[(data.length*2) + 1];
			//shifting data appropriately
			for(int j = 0; j < i; j++) {
				newData[j] = data[j];
			}
			for(int j = size(); j > i; j--) {
				newData[j] = data[j - 1];
			}
			count++;
			return newData;
		}
		
		else {
			count++;
			return this.data;
		}
	}
	
	/**
	 * shrink strategy that shifts array elements when
	 * something is removed (array length never decreases
	 */
	@Override
	public T[] shrinkStrategy(int i) {
		T[] newData = (T[]) new Object[data.length];

		for(int j = 0; j < i; j++) {
			newData[j] = data[j];
		}

		for(int j = i; j < size(); j++) {
			newData[j] = data[j + 1];
		}

		count--;

		return newData;
	}
}
