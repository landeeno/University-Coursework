package a9;

public class SpeedTest {

public static void main(String[] args) {
		
		// Create an object of each array type
		DynamicArrayAddOne<String> addOneDA = new DynamicArrayAddOne<>();
		DynamicArrayDouble<String> doubleDA = new DynamicArrayDouble<>();
		DynamicArrayCustom<String> customDA = new DynamicArrayCustom<>();
		
		// Set the desired number of iterations
		int N = 25000;

		
		
		// Collect the time required to add N elements to the addOneDA.
		long startTime = System.nanoTime();
		for(int i = 0; i < N; i++) {
			addOneDA.add( (int)( Math.random() * addOneDA.size() ) , "");  // adds to the end of the array
		}
		long endTime = System.nanoTime();
		System.out.println("DynamicArrayAddOne took " + (endTime - startTime) / 1000000000.0 + 
				" sec to add " + N + " elements.");		
		
		
		// Collect the time required to add N elements to the doubleDA.
		startTime = System.nanoTime();
		for(int i = 0; i < N; i++) {
			doubleDA.add( (int)( Math.random() * doubleDA.size() ) , "");   // adds to the end of the array
		}
		endTime = System.nanoTime();
		System.out.println("DynamicArrayDouble took " + (endTime - startTime) / 1000000000.0 + 
				" sec to add " + N + " elements.");
		
		// Collect the time required to add N elements to the customDA.
				startTime = System.nanoTime();
				for(int i = 0; i < N; i++) {
					customDA.add( (int)( Math.random() * customDA.size() ) , "");   // adds to the end of the array
				}
				endTime = System.nanoTime();
				System.out.println("DynamicArrayCustom took " + (endTime - startTime) / 1000000000.0 + 
						" sec to add " + N + " elements.");		
	}
	
}


