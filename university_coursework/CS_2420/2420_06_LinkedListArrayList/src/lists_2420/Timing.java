package lists_2420;

/**
 * 
 * @author Landon Crowther & Brent Collins
 * 
 * This class analyzes the efficiency of the add methods in both the linked and array implementations
 *
 */
public class Timing
{
	//array of sizes ranging from 10_000 to 1_000_000
	static Integer[] sizes = { 10_000, 50_000, 100_000, 200_000, 300_000, 400_000, 500_000, 600_000, 700_000, 800_000,
			900_000, 1_000_000 };
	//array of sizes ranging from 100 to 100_000 (used for addMiddle only - because it takes signficantly longer)
	static Integer[] sizes_addMiddle = { 100, 1000, 5000, 10_000, 20_000, 30_000, 40_000, 50_000, 60_000, 70_000,
			80_000, 90_000, 100_000 };

	//number of samples taken for each size in each test
	static int average = 100;

	//booleans to switch between array_list and linked_list
	static boolean doArrayList = false;
	static boolean doLinkedList = false;
	static boolean doRemoveArrayListFirst = true;
	static boolean doRemoveArrayListLast = true;
	static boolean doRemoveLinkedListFirst = true;
	static boolean doRemoveLinkedListLast = true;
	
	//number that is being added in the add call
	static Integer number = 10;

	public static void main(String[] args)
	{

		if (doArrayList)
		{
			average = 100;
			addFirstTiming_Array(sizes);
			addLastTiming_Array(sizes);
			average = 5;
			addMiddleTiming_Array(sizes_addMiddle);
		}

		if (doLinkedList)
		{
			average = 100;
			addFirstTiming_Linked(sizes);
			addLastTiming_Linked(sizes);
			average = 5;
			addMiddleTiming_Linked(sizes_addMiddle);
		}
		
		if (doRemoveArrayListFirst)
		{
			average = 5000;
			removeFirstArray(1000, 100);
		}
		
		if (doRemoveArrayListLast)
		{
			average = 5000;
			removeLastArray(1000, 100);
		}
		
		if (doRemoveLinkedListFirst)
		{
			average = 5000;
			removeFirstLinked(1000, 100);
		}
		
		if (doRemoveLinkedListLast)
		{
			average = 5000;
			removeLastLinked(1000, 100);
		}
		
		

	}

	/**
	 * Times the add_first() method for Array_List_2420 for different sizes of
	 * arrays
	 * 
	 * @param sizes
	 */
	public static void addFirstTiming_Array(Integer[] sizes)
	{
		System.out.println("Testing add_first() with Array_List_2420: ");
		System.out.println("Length: \tAverage Time (s): \t");

		for (Integer currentSize : sizes)
		{

			int counter = 0;
			double averageTime = 0;

			while (counter <= average)
			{
				Array_List_2420 test = new Array_List_2420();
				double startTime = System.nanoTime();
				for (int j = 0; j < currentSize; j++)
				{
					test.add_first(number);
				}
				double elapsedTime = System.nanoTime() - startTime;
				averageTime += elapsedTime;
				counter++;
			}

			averageTime /= average;
			averageTime = toRegTime(averageTime);

			System.out.println(currentSize + "\t" + averageTime);
		}
		System.out.println();
	}

	/**
	 * Times add_last() for Array_List_2420 for different sizes of arrays
	 * 
	 * @param sizes
	 */
	public static void addLastTiming_Array(Integer[] sizes)
	{
		System.out.println("Testing add_last() with Array_List_2420: ");
		System.out.println("Length: \tAverage Time (s): \t");
		for (Integer currentSize : sizes)
		{

			int counter = 0;
			double averageTime = 0;

			while (counter <= average)
			{
				Array_List_2420 test = new Array_List_2420();
				double startTime = System.nanoTime();
				for (int j = 0; j < currentSize; j++)
				{
					test.add_last(number);
				}
				double elapsedTime = System.nanoTime() - startTime;
				averageTime += elapsedTime;
				counter++;
			}

			averageTime /= average;
			averageTime = toRegTime(averageTime);

			System.out.println(currentSize + "\t" + averageTime);
		}
		System.out.println();
	}

	/**
	 * Times add_middle() for Array_List_2420 for different sizes of arrays
	 * 
	 * @param sizes
	 */
	public static void addMiddleTiming_Array(Integer[] sizes)
	{
		System.out.println("Testing add_middle() with Array_List_2420: ");
		System.out.println("Length: \tAverage Time (s): \t");
		for (Integer currentSize : sizes)
		{
			int counter = 0;
			double averageTime = 0;

			while (counter <= average)
			{
				Array_List_2420 test = new Array_List_2420();
				double startTime = System.nanoTime();
				for (int j = 0; j < currentSize; j++)
				{
					int randomNum = random(currentSize);
					test.add_middle(randomNum, number);
				}
				double elapsedTime = System.nanoTime() - startTime;
				averageTime += elapsedTime;
				counter++;
			}
			averageTime /= average;
			averageTime = toRegTime(averageTime);

			System.out.println(currentSize + "\t" + averageTime);
		}
		System.out.println();
	}

	/**
	 * Times the add_first() method for Linked_List_2420 for different sizes of
	 * arrays
	 * 
	 * @param sizes
	 */
	public static void addFirstTiming_Linked(Integer[] sizes)
	{
		System.out.println("Testing add_first() with Linked_List_2420: ");
		System.out.println("Length: \tAverage Time (s): \t");

		for (Integer currentSize : sizes)
		{

			int counter = 0;
			double averageTime = 0;

			while (counter <= average)
			{
				Linked_List_2420 test = new Linked_List_2420();
				double startTime = System.nanoTime();
				for (int j = 0; j < currentSize; j++)
				{
					test.add_first(number);
				}
				double elapsedTime = System.nanoTime() - startTime;
				averageTime += elapsedTime;
				counter++;
			}

			averageTime /= average;
			averageTime = toRegTime(averageTime);

			System.out.println(currentSize + "\t" + averageTime);
		}
		System.out.println();
	}

	/**
	 * Times the add_last() method for Linked_List_2420 for different sizes of
	 * arrays
	 * 
	 * @param sizes
	 */
	public static void addLastTiming_Linked(Integer[] sizes)
	{
		System.out.println("Testing add_last() with Linked_List_2420: ");
		System.out.println("Length: \tAverage Time (s): \t");

		for (Integer currentSize : sizes)
		{

			int counter = 0;
			double averageTime = 0;

			while (counter <= average)
			{
				Linked_List_2420 test = new Linked_List_2420();
				double startTime = System.nanoTime();
				for (int j = 0; j < currentSize; j++)
				{
					test.add_first(number);
				}
				double elapsedTime = System.nanoTime() - startTime;
				averageTime += elapsedTime;
				counter++;
			}

			averageTime /= average;
			averageTime = toRegTime(averageTime);

			System.out.println(currentSize + "\t" + averageTime);
		}
		System.out.println();
	}

	/**
	 * Times add_middle() for Linked_List_2420 for different sizes of arrays
	 * 
	 * @param sizes
	 */
	public static void addMiddleTiming_Linked(Integer[] sizes)
	{
		System.out.println("Testing add_middle() with Linked_List_2420: ");
		System.out.println("Length: \tAverage Time (s): \t");
		for (Integer currentSize : sizes)
		{
			int counter = 0;
			double averageTime = 0;

			while (counter <= average)
			{
				Linked_List_2420 test = new Linked_List_2420();
				double startTime = System.nanoTime();
				for (int j = 0; j < currentSize; j++)
				{
					int randomNum = random(currentSize);
					test.add_middle(randomNum, number);
				}
				double elapsedTime = System.nanoTime() - startTime;
				averageTime += elapsedTime;
				counter++;
			}
			averageTime /= average;
			averageTime = toRegTime(averageTime);

			System.out.println(currentSize + "\t" + averageTime);
		}
		System.out.println();
	}
	
	/**
	 * time to remove removalElements from an array of arraySize
	 * @param arraySize
	 * @param removalElements
	 */
	public static void removeFirstArray(int arraySize, int removalElements)
	{
		int count = 0;
		double elapsedTime = 0;
		
		while (count < average)
		{
			Array_List_2420 test = new Array_List_2420();
			
			//build array
			for (int i = 0; i < arraySize; i ++)
			{
				test.add_first(i);
			}
			
			double startTime = System.nanoTime();
			for (int i = 0; i < removalElements; i++)
			{
				test.remove_first();
			}
			elapsedTime += (System.nanoTime() - startTime);
			count++;
		}
		
		double averageTime = elapsedTime / average;
		averageTime = toRegTime(averageTime);
		
		System.out.println("Time spent removing " + removalElements + "from the beginning of a Array_List: \t" + averageTime);
		
	}
	
	/**
	 * time to remove removalElements from an array of arraySize
	 * @param arraySize
	 * @param removalElements
	 */
	public static void removeLastArray(int arraySize, int removalElements)
	{
		int count = 0;
		double elapsedTime = 0;
		
		while (count < average)
		{
			Array_List_2420 test = new Array_List_2420();
			
			//build array
			for (int i = 0; i < arraySize; i ++)
			{
				test.add_first(i);
			}
			
			double startTime = System.nanoTime();
			for (int i = 0; i < removalElements; i++)
			{
				test.remove_last();
			}
			elapsedTime += (System.nanoTime() - startTime);
			count++;
		}
		
		double averageTime = elapsedTime / average;
		averageTime = toRegTime(averageTime);
		
		System.out.println("Time spent removing " + removalElements + "from the end of a Array_List: \t" + averageTime);
		
	}
	
	/**
	 * time to remove removalElements from an array of arraySize
	 * @param arraySize
	 * @param removalElements
	 */
	public static void removeFirstLinked(int arraySize, int removalElements)
	{
		int count = 0;
		double elapsedTime = 0;
		
		while (count < average)
		{
			Linked_List_2420 test = new Linked_List_2420();
			
			//build array
			for (int i = 0; i < arraySize; i ++)
			{
				test.add_first(i);
			}
			
			double startTime = System.nanoTime();
			for (int i = 0; i < removalElements; i++)
			{
				test.remove_first();
			}
			elapsedTime += (System.nanoTime() - startTime);
			count++;
		}
		
		double averageTime = elapsedTime / average;
		averageTime = toRegTime(averageTime);
		
		System.out.println("Time spent removing " + removalElements + "from the beginning of a Linked_List: \t" + averageTime);
		
	}
	
	/**
	 * time to remove removalElements from an array of arraySize
	 * @param arraySize
	 * @param removalElements
	 */
	public static void removeLastLinked(int arraySize, int removalElements)
	{
		int count = 0;
		double elapsedTime = 0;
		
		while (count < average)
		{
			Linked_List_2420 test = new Linked_List_2420();
			
			//build array
			for (int i = 0; i < arraySize; i ++)
			{
				test.add_first(i);
			}
			
			double startTime = System.nanoTime();
			for (int i = 0; i < removalElements; i++)
			{
				test.remove_last();
			}
			elapsedTime += (System.nanoTime() - startTime);
			count++;
		}
		
		double averageTime = elapsedTime / average;
		averageTime = toRegTime(averageTime);
		
		System.out.println("Time spent removing " + removalElements + "from the end of a Linked_List: \t" + averageTime);
	}
	
	

	/**
	 * generate random number from 0 to max
	 * @param max - maximum possible random number
	 * @return - random number
	 */
	public static int random(int max)
	{
		return (int) (Math.random() * max);
	}

	/**
	 * convert from nano seconds to regular seconds
	 * @param nanoseconds
	 * @return - seconds
	 */
	public static double toRegTime(double nanoseconds)
	{
		return nanoseconds / 1_000_000_000;
	}
}
