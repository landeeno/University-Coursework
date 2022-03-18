package a9;
/**
 * Landon Crowther
 * u0926601
 * CS 1410 - Assignment A9
 * @author lando
 *
 *
 *
 * @param <T>
 * 
 * 
 * DynamicArrayCustom:
 * 
 * Defaults at length 2: 
 * 
 * When adding element past current lenght (ie adding element 3), array length doubles
 * 
 * When removing elements, array sometimes will srhink to conserve memory.
 * Array only shrinks if the array length is 4 times the count. For example,
 * if there are 2 elements in the array of length 8, then when another element is
 * removed, the array will shrink to 3/4 the current size (size 6)
 * 
 */
public class DynamicArrayCustom<T> extends DynamicArrayAbstract<T> {

	/**
	 * constructor for DynamicArrayCustom
	 */
	public DynamicArrayCustom() {
		data = (T[]) new Object[2];
		count = 0;
	}

	/**
	 * growth strategy is same as DynamicArrayDouble
	 */
	@Override
	public T[] growthStrategy(int i) {

		T[] newData;

		if (count == data.length) {		
			newData = (T[]) new Object[(data.length*2)];
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
	 * shrink strategy is different than both DynamicArrayAddOne
	 * and DynamicArrayDouble. If the real length of the array is 
	 * more than 2* the count (ie count = 2, length = 8), this remove 
	 * strategy will shave off a quarter of the array length (go from 8 to 6).
	 */
	@Override
	public T[] shrinkStrategy(int i) {

		if (data.length >= count*4) {
			T[] newData = (T[]) new Object[data.length*3/4];


			for(int j = 0; j < i; j++) {
				newData[j] = data[j];
			}

			for(int j = i; j < size(); j++) {
				newData[j] = data[j + 1];
			}

			count--;

			return newData;
		}

		else {
			T[] newData = (T[]) new Object[data.length];

			for(int j = 0; j < i; j++) {
				newData[j] = data[j];
			}

			for(int j = i; j < size() -1 ; j++) {
				newData[j] = data[j + 1];
			}

			count--;

			return newData;
		}
	}


}

