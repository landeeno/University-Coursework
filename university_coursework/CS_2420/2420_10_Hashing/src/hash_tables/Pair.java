package hash_tables;

/**
 * Represents a key-value pair of generic items.
 */
public class Pair<KeyType, ValueType>
{
	public KeyType		key;
	public ValueType	value;

	/**
	 * Constructor, associates key and vaue
	 * @param k
	 * @param v
	 */
	public Pair( KeyType k, ValueType v )
	{
		this.key = k;
		this.value = v;
	}

	public String toString()
	{
		return "<" + this.key + " " + this.value + ">";
	}
}
