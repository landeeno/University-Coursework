package a8;
/***
 * Tests the time to add N elements to a DynamicArray compared to DynamicArray2
 * @author David Johnson,  Erin Parker
 *
 */
public class SpeedTest {

	public static void main(String[] args) {
		
		// Create an object of each array type
		DynamicArray slowDA = new DynamicArray();
		DynamicArray2 fastDA = new DynamicArray2();
		
		// Set the desired number of iterations
		int N = 200;

		
		// Collect the time required to add N elements to the slow DA.
		long startTime = System.nanoTime();
		for(int i = 0; i < N; i++) {
			slowDA.add("" + i);  // adds to the end of the array
		}
		long endTime = System.nanoTime();
		System.out.println("DynamicArray took " + (endTime - startTime) / 1000000000.0 + 
				" sec to add " + N + " elements.");

		// Collect the time required to add N elements to the fast DA.
		startTime = System.nanoTime();
		for(int i = 0; i < N; i++) {
			fastDA.add("" + i);   // adds to the end of the array
		}
		endTime = System.nanoTime();
		System.out.println("DynamicArray2 took " + (endTime - startTime) / 1000000000.0 + 
				" sec to add " + N + " elements.");
	}

}
