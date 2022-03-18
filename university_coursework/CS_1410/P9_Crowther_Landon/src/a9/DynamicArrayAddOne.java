package a9;

/**
 * /**
 * Landon Crowther
 * u0926601
 * CS 1410 - Assignment A9
 * @author lando
 *
 *	DymamicArrayAddOne
 *
 * array length changes with each operation 
 *
 */

public class DynamicArrayAddOne<T> extends DynamicArrayAbstract<T> {

	/**
	 * constructor method
	 */
	public DynamicArrayAddOne() {
		super();
	}

	/**
	 * growth strategy that adds one to the current array length whenever elements are added
	 */
	@Override
	public T[] growthStrategy(int i) {
		
		
		T[] newData = (T[]) new Object[data.length + 1];
	
		for(int j = 0; j < i; j++) {
			newData[j] = data[j];
		}
		
		 for(int j = size(); j > i; j--) {
		     newData[j] = data[j - 1];
		 }
		count++;
		return newData;
	}

	/**
	 * shrink strategy that decreases the array length by 1 whenever 
	 * an element is removed. 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T[] shrinkStrategy(int i) {
		T[] newData = (T[]) new Object[data.length - 1];
		count--;
		for(int j = 0; j < i; j++) {
			newData[j] = data[j];
		}

		for(int j = i; j < size(); j++) {
			newData[j] = data[j + 1];
		}

		return newData;
	
	}

}
