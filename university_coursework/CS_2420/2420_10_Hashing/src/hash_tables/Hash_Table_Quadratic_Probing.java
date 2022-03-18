package hash_tables;

/**
 * This class has all the necessary methods and proper functionality of 
 * the Quadratic Probing implementation of a HashTable. 
 * 
 * Note, all functionality is the same as the Linear_Probing implementation,
 * except for the method that advances properly
 * 
 * @author Nathan Robbins, Landon Crowther
 * @date April 05, 2017
 *
 */
public class Hash_Table_Quadratic_Probing<KeyType, ValueType> extends Hash_Table_Linear_Probing<KeyType, ValueType>
{

	/**
	 * {@inheritDoc}
	 */
	public Hash_Table_Quadratic_Probing(int initial_capacity)
	{
		super(initial_capacity);
	}
	
	/**
	 * This method advances the search in a quadratic fashion.
	 * Ie instead of advancing linearly until an availbale bucket
	 * is found, the HashTable will advance 1 space, 4, 9, etc.
	 * 
	 * @param - initial index and the next spot
	 * @return - new Index
	 */
	@Override
	protected int advance(int initial, int count)
	{
		return initial + count*count;
	}

}
