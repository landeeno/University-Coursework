package hash_tables;

import static hash_tables.Primes.next_prime;

import java.util.ArrayList;


/**
 * This class has all the necessary methods and proper functionality of 
 * the Linear Probing implementation of a HashTable. 
 * 
 * @author Nathan Robbins, Landon Crowther
 * @date April 05, 2017
 *
 */
public class Hash_Table_Linear_Probing<KeyType, ValueType> implements Hash_Map<KeyType, ValueType>
{

	//main storage for Hash_Table
	private ArrayList<Pair<KeyType, ValueType>> table;
	protected int capacity;
	protected int num_of_entries;

	//boolean allowing array resize
	protected boolean allowResize;

	//used for statistic tracking
	private int collisionCount;
	private int findCount;
	private int insertCount;
	private int hashCount;
	private long insertTime;
	private long findTime;
	private long hashTime;


	/**
	 * Constructor for Linear_Hash_Table. Note, if the initial_capacity
	 * parameter is not a prime number, the next prime number proceeding
	 * initial_capacity will be used
	 * @param initial_capacity - number of buckets initially in array
	 */
	public Hash_Table_Linear_Probing(int initial_capacity)
	{

		this.capacity = next_prime(initial_capacity);
		this.allowResize = true;
		init_table();
		this.num_of_entries = 0;
	}

	/**
	 * creates inserts a new Pair(key, value). 
	 * 
	 * If the array of backing data ("table") already
	 * contains data at the index that they key's has points to, 
	 * the new Pair will be inserted at the next available bucket.
	 * 
	 * Note - in the instance of the QuadraticProbing, the new Pair will be 
	 * added at the next available bucket, but proceeding in a quadratic fashion
	 * (ie check 1 after, 4 after, 9 after, etc.)
	 * 
	 * In the instance that the hash of the key already exists in the
	 * backing data, the new value will override the existing value. 
	 * 
	 * This method will automatically resize if the number of entries is 
	 * greater than half the capacity (assuming the instance variable allows so)
	 */
	public void insert(KeyType key, ValueType value)
	{

		long startTime = System.nanoTime();

		insertCount++;

		if (num_of_entries > capacity / 2)
		{
			this.resize(capacity * 2);
		}

		Pair<KeyType, ValueType> newPair = new Pair<>(key, value);


		int hash = hash(key);


		int initialIndex = hash % capacity;
		int newIndex = initialIndex;

		int offset = 0;

		while (table.get(newIndex) != null && !table.get(newIndex).key.equals(key))
		{
			collisionCount++;
			offset++;
			newIndex = Math.abs(advance(initialIndex, offset)) % capacity;
		}

		if (table.get(newIndex) == null)
		{
			num_of_entries++;
		}

		table.set(newIndex, newPair);

		long endTime = System.nanoTime();

		insertTime += (endTime - startTime);

	}

	/**
	 * Equivalent to calling the .hashCode() method, but ensures that 
	 * all of the required statistics are kept track of, rather than
	 * having to copy and paste multiple times
	 * @param key - key that hashCode() method is called on
	 * @return - hash- int hash value for key
	 */
	private int hash(KeyType key)
	{
		long startTime = System.nanoTime();
		int hash = Math.abs(key.hashCode());
		long endTime = System.nanoTime();

		hashTime += (endTime - startTime);
		hashCount++;

		return hash;
	}

	/**
	 * This method indicates the proper implementation of 
	 * advancing through the data. In an instance of the 
	 * Linear_Probing, the advancements will happen 
	 * 1 step at a time until a spot is available. 
	 * 
	 * @param initial - initial index
	 * @param count - amount of spaces to proceed (will change each time
	 * method is called)
	 * @return - new index for the insert method to use
	 */
	protected int advance(int initial, int count)
	{
		return initial + count;
	}

	/**
	 * if doubling is off, then do not change table size in insert method
	 * 
	 * @param on
	 *            - turns doubling on (the default value for a hash table should
	 *            be on)
	 */
	public void doubling_behavior(boolean on)
	{
		// FIXME:
	}


	/**
	 * Serach through the HashTable for a given key.
	 * If the key is found, return the value associated with it
	 * 
	 * Works by re-hashing the key, checking the corresponding
	 * index, and proceeding in a linear fashion through the indicies
	 * until the key is found.
	 * 
	 * If the key is not found (ie an empty spot is found before the key),
	 * return null
	 * 
	 * Will keep track of appropriate statistics. 
	 * 	
	 */
	public ValueType find(KeyType key)
	{

		long startTime = System.nanoTime();

		findCount++;

		int hash = hash(key);

		int initialIndex = hash % capacity;
		int newIndex = initialIndex;

		int offset = 0;

		while ( table.get(newIndex) != null && !table.get(newIndex).key.equals(key) )
		{
			collisionCount++;
			offset++;
			newIndex = Math.abs(advance(initialIndex, offset)) % capacity;
		}

		if (table.get(newIndex) != null)
		{
			findTime+= (System.nanoTime() - startTime);
			return table.get(newIndex).value;
		}

		findTime += (System.nanoTime() - startTime);
		return null;
	}

	/**
	 * Remove all items from the hash table (and resets stats).
	 */
	public void clear()
	{
		init_table();
		this.num_of_entries = 0;
		reset_stats();
	}

	/**
	 * Returns the capacity of the hash table.
	 */
	public int capacity()
	{
		return capacity;
	}

	/**
	 * Returns the number of entries in the hash table (i.e., the number of
	 * stored key-value pairs).
	 */
	public int size()
	{
		return num_of_entries;
	}

	/**
	 * Calculates, formats, and prints all the relevant statistics
	 * 
	 *  {@inheritDoc}
	 *  
	 *  
	 */
	public ArrayList<Double> print_stats()
	{
		ArrayList<Double> stats = new ArrayList<>();
		double averageCollisions = (double)collisionCount / (findCount + insertCount);

		stats.add(averageCollisions);
		stats.add((double)num_of_entries);
		stats.add((double)capacity);

		System.out.println(this.toString());

		return stats;
	}

	/**
	 * Calculate and formats the necessary statistcal information and
	 * returns a nicely formatted String
	 */
	public String toString()
	{
		String result = new String();
		double averageCollisions = (double)collisionCount / (findCount + insertCount);
		double avgHashTime = hashTime / (double) hashCount;
		double avgInsertTime = insertTime / (double) insertCount;
		double avgFindTime = findTime / (double) findCount;
		double PercentFilled = (num_of_entries / (double) capacity) * 100;


		result = "------------ Hash Table Info ------------\n" 
				+ "  Average collisions: " + averageCollisions
				+"\n  Average Hash Function Time: " + avgHashTime 
				+  "\n  Average Insertion Time: " + avgInsertTime 
				+ "\n  Average Find Time: " + avgFindTime
				+ "\n Percent filled : " + PercentFilled +  "\n  Size of Table  : " + capacity
				+ "\n  Elements       : " + num_of_entries 
				+ "\n-----------------------------------------\n";

		return result;

	}

	/**
	 * Resets the hash table stats.
	 *
	 */
	public void reset_stats()
	{
		collisionCount = 0;
		findCount = 0;
		insertCount = 0;
		hashCount = 0;

		findTime = 0;
		insertTime = 0;
		hashTime = 0;

	}

	/**
	 * Clear the hash table array by resetting to a new array with current capacity
	 * and initializes all of the entries to null
	 */
	private void init_table()
	{

		table = new ArrayList<Pair<KeyType, ValueType>>(capacity);

		for (int i = 0; i < capacity; i++)
		{
			table.add(null);
		}

	}

	/**
	 * 
	 */
	public void set_resize_allowable(boolean status)
	{
		allowResize = status;
	}

	/**
	 * Assuming that the class variable is currently allowing resize,
	 * this method will restructure the backing data and expand,
	 * increasing capacity.
	 * 
	 * Note, the capacity will initially double, but will continue to increase
	 * until the next prime number is found.
	 * 
	 * Once the backing array size has doubled, statistics are
	 * reset and all the pairs are added again. 
	 */
	public void resize(int new_size)
	{

		if ( !allowResize || new_size <= capacity )
		{
			return;
		}

		int firstPrime = Primes.next_prime(new_size);
		capacity = firstPrime;

		ArrayList<Pair<KeyType, ValueType>> oldTable = table;
		clear();

		for (Pair<KeyType, ValueType> p : oldTable)
		{
			if (p != null)
			{
				insert(p.key, p.value);
			}
		}


	}
//end of class
}