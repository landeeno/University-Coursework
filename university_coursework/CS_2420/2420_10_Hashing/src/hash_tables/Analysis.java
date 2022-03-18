package hash_tables;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * This class will run methods that answer the questions required by the analysis
 * 
 * @author Nathan Robbins, Landon Crowther
 *
 */
public class Analysis
{

	/**
	 * load all names and ages into an ArrayList<Pair<String name, Integer age>>
	 * used by other methods
	 * @param fileName
	 * @return
	 */
	private static ArrayList<Pair<String, Integer>> loadFile(String fileName)
	{
		ArrayList<Pair<String, Integer>> names = new ArrayList<>();

		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));

			String line = reader.readLine(); //skip first line

			while ((line = reader.readLine()) != null)
			{
				String[] firstLastAge = line.split("\\s+");

				String fullName = firstLastAge[0] + " " + firstLastAge[1];
				Integer age = Integer.parseInt(firstLastAge[2]);  

				Pair<String, Integer> p = new Pair<String, Integer>( fullName, age );
				names.add(p);
			}
			reader.close();
			return names;
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("File not found");
			System.exit(0);
		}
		return null;
	}


	public static void main(String[] args)
	{
		ArrayList<Pair<String, Integer>> names = loadFile("Resources/names");

		//test 1
		Hash_Table_Linear_Probing<String, Integer> linear = new Hash_Table_Linear_Probing<>(79);
		Hash_Table_Quadratic_Probing<String, Integer> quadratic = new Hash_Table_Quadratic_Probing<>(79);
		Hash_Table_Chaining<String, Integer> chain = new Hash_Table_Chaining<>(79);

		linear.set_resize_allowable(false);
		quadratic.set_resize_allowable(false);
		chain.set_resize_allowable(false);

		for (int i = 0; i < 79; i++)
		{
			String name = names.get(i).key;
			Integer age = names.get(i).value;

			linear.insert(name, age);
			quadratic.insert(name, age);
			chain.insert(name, age);
		}

		linear.print_stats();
		quadratic.print_stats();
		chain.print_stats();


		//test 2 part 1:

		System.out.println("Capacity\t" + "Linear Ins\t" + "Linear Find\t" + "Quad Ins\t" + "Quad Find");

		for (int capacity = 1000; capacity <= 5000; capacity += 200)
		{
			linear = new Hash_Table_Linear_Probing<>(capacity);
			quadratic = new Hash_Table_Quadratic_Probing<>(capacity);
			linear.set_resize_allowable(false);
			quadratic.set_resize_allowable(false);

			double totalTimeLinearInsert = 0;
			for (Pair<String, Integer> p : names)
			{
				String name = p.key;
				Integer age = p.value;

				double startTime = System.nanoTime();
				linear.insert(name, age);		
				totalTimeLinearInsert += (System.nanoTime() - startTime);

				if (linear.size() == capacity)
				{
					break;
				}

			}

			double totalTimeLinearFind = 0;
			for (Pair<String, Integer> p : names)
			{
				String name = p.key;

				double startTime = System.nanoTime();
				linear.find(name);	
				totalTimeLinearFind += (System.nanoTime() - startTime);

			}

			double totalTimeQuadraticInsert = 0;
			for (Pair<String, Integer> p : names)
			{
				String name = p.key;
				Integer age = p.value;

				double startTime = System.nanoTime();
				quadratic.insert(name, age);		
				totalTimeQuadraticInsert += (System.nanoTime() - startTime);

				if (quadratic.size() == capacity)
				{
					break;
				}
			}

			double totalTimeQuadraticFind = 0;
			for (Pair<String, Integer> p : names)
			{
				String name = p.key;

				double startTime = System.nanoTime();
				quadratic.find(name);	
				totalTimeQuadraticFind += (System.nanoTime() - startTime);
			}

			System.out.println(capacity + "\t" + totalTimeLinearInsert + "\t" + totalTimeLinearFind + "\t" + totalTimeQuadraticInsert + "\t" + totalTimeQuadraticFind);		
		}

		System.out.println();
		
		System.out.println("Capacity\t" + "Chain Insert\t" + "Chain Find\t");
		for (int capacity = 10; capacity <= 1000; capacity += 10)
		{
			chain = new Hash_Table_Chaining(capacity);
			chain.set_resize_allowable(true);
			
			double totalTimeInsert = 0;
			for (Pair<String, Integer> p : names)
			{
				String name = p.key;
				Integer age = p.value;

				double startTime = System.nanoTime();
				chain.insert(name, age);		
				totalTimeInsert += (System.nanoTime() - startTime);
			}

			double totalTimeFind = 0;
			for (Pair<String, Integer> p : names)
			{
				String name = p.key;

				double startTime = System.nanoTime();
				chain.find(name);	
				totalTimeFind += (System.nanoTime() - startTime);
			}
			
			System.out.println(capacity + "\t" + totalTimeInsert + "\t" + totalTimeFind);			
		}


	}


}

