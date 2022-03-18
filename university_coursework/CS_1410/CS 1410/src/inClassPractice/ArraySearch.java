package inClassPractice;

import java.util.Arrays;

public class ArraySearch {

	public static void main(String[] args) {
		int[] vals = {5, 12, -3, 0, 15, 17, 39, 1005, 12, -20, -4};
		Arrays.sort(vals);
		System.out.println("Found 5 at: " + binarySearch(vals, 5));

		int massIndex = vals.length + 1;
		System.out.println(massIndex);
	}

	public static int binarySearch(int[] vals, int key) {
		
		//initializing high and low variables
		int low = 0;
		int high = vals.length - 1;	

		//loop that searches through array
		while (low <= high) {

			
			int arrayLength = high + 1 - low;
			int mid = arrayLength/2 + low; 

			if (vals[mid] == key) {
				count++;
				return mid;
			}
			if (vals[mid] < key) {
				low = mid + 1;
			}
			if (vals[mid] > key) {
				high = mid - 1;
			}
		}
		
		return -1;

	}
}