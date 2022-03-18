package hash_tables;

import java.util.ArrayList;
import java.util.LinkedList;

public class Hash_Table_Chaining<KeyType, ValueType> implements Hash_Map<KeyType, ValueType>
{
	/*
	 * used for statistic tracking:
	 * longestChain - longest chain
	 * maximumChainSize - Change this if needed
	 */
	private final int							maximumChainSize = 5;
	
	private  boolean							allowResize;
	
	//storage for the Hash_Table_Chaining
	private ArrayList<LinkedList<Pair<KeyType, ValueType>>>	table;
	protected int								capacity;
	protected int								num_of_entries;
		
	//used for statistic tracking:
	private int 								collisionCount;
	private int 								findCount;
	private int 								insertCount;
	private int									hashCount;
	private long 								insertTime;
	private long 								findTime;
	private long 								hashTime;
	
	
	/**
	 * Hash Table Constructor
	 * @param initial_capacity - try to make this equal to twice the expected number of values
	 * 
	 * Note - because this is the chain implementation, prime numbers are not 
	 * necessary
	 */
	public Hash_Table_Chaining( int initial_capacity )
	{
		this.capacity = initial_capacity;
		init_table();
		this.num_of_entries = 0;
		this.allowResize = true;
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
	 * Creates and inserts a new Pair(key, value). 
	 * 
	 * If the bucket associated with the corresponding key.hashCode 
	 * is already full, the new Pair will be added to the LinkedList
	 * in the bucket. 
	 * 
	 * The method keeps track of which LinkedList is the longest.
	 * If the longest chaing is every greater than the maximumChainSize 
	 * (instance variable), the Hash_Table will rebuild itself, assuming
	 * the instance variable allows so. 
	 * 
	 * It rebuilds itself by doubling the capacity and re-inserting
	 * all the pairs again. 
	 */
	public void insert( KeyType key, ValueType value )
	{
		
		long startTime = System.nanoTime();
		
		insertCount++;
		
		if ( ( num_of_entries / (double)capacity ) > maximumChainSize)
		{	
			this.resize( capacity*2 );
		}
		
		Pair<KeyType, ValueType> newPair = new Pair<>(key, value);
				
		int hash = hash(key);
		
		int initialIndex = hash % capacity;

		Pair<KeyType, ValueType> alreadyExistingPair = findPair(newPair.key, table.get(initialIndex));
		
		if(alreadyExistingPair == null)
		{
			table.get(initialIndex).add(newPair);
			num_of_entries++;
		}
		else
		{
			alreadyExistingPair.value = newPair.value;
		}
		
		insertTime += (System.nanoTime() - startTime);
		
		
		
	}
	
	/**
	 * Private helper method. Call on a LinkedList<Pair<KeyType, ValueType>>. Will return 
	 * the pair that contains the given key, or null if the key doesn't exist in the list.
	 * @param key - hash key that is being serached for
	 * @param list - LinkedList that could possibley contain the hashkey
	 * @return - Pair that contains the key, or null otherwise
	 */
	private Pair<KeyType, ValueType> findPair(KeyType key, LinkedList<Pair<KeyType, ValueType>> list)
	{
		
		for(Pair<KeyType, ValueType> pair : list)
		{
			if(pair.key.equals(key))
			{
				return pair;
			}
			//update collisionCount
			collisionCount++;
		}
		
		//no key found
		return null;
		
	}


	/**
	 * Search through the the Hash_Table_Chaining for a given key. Note, 
	 * this method keeps track of necessary statistics, and utilizes the 
	 * helper method "findPair()"
	 * 
	 */
	public ValueType find( KeyType key )
	{
		long startTime = System.nanoTime();
		
		findCount++;
	
		int hash = hash(key);
		int initialIndex = hash % capacity;
		
		Pair<KeyType, ValueType> alreadyExistingPair = findPair(key, table.get(initialIndex));
		
		if(alreadyExistingPair != null)
		{
			findTime += (System.nanoTime() - startTime);
			return alreadyExistingPair.value;
		}
		else
		{
			findTime += (System.nanoTime() - startTime);
			return null;
		}
		
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
	 * Returns the number of entries in the hash table (i.e., the number of stored key-value pairs).
	 */
	public int size()
	{
		return num_of_entries;
	}


	/**
	 * Calculates and formats all of the necessary statistics
	 * 
	 * {@inheritDoc}
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
	 * Clear the hash table by resetting it to a new LinkedList(capacity), and created
	 * a new LinkedList in each bucket
	 */
	private void init_table()
	{
		
		table = new ArrayList<LinkedList<Pair<KeyType, ValueType>>>(capacity);
			
		for (int i = 0; i < capacity; i++)
		{
			table.add(new LinkedList<>());
		}
		
	}

	/**
	 * 
	 */
	public void set_resize_allowable( boolean status )
	{
		this.allowResize = status;
	}
	
	/**
	 * 
	 * This method resizes the HashTable by doubling the length of the 
	 * backing data array. If the resize method is called, but the new
	 * size is smaller than the original size, nothing will happen.
	 * 
	 * Also, if specific instance variable is set to not allow 
	 * resizing, nothing will happen. 
	 */
	public void resize( int new_size )
	{
				
		if ( !allowResize || new_size <= capacity)
		{
			return;
		}
		
		//doesn't need to be prime for chain implementation
		capacity = new_size;
		
		ArrayList<LinkedList<Pair<KeyType, ValueType>>> oldTable = table;
		clear();
		
		for (LinkedList<Pair<KeyType, ValueType>> list : oldTable)
		{
			if (list.size() > 0)
			{
				for(Pair<KeyType, ValueType> p : list)
				{
					insert(p.key, p.value);
				}
			}
		}
	}

	// end of class
}
