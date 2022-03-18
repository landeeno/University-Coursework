package hash_tables;


/**
 * This class contains all of the structure and functional methods
 * for the Prime numbers that are used in the rest of the package.
 * 
 * @author Nathan Robbins, Landon Crowther
 * @date April 05, 2017
 *
 */
public class Primes
{


	/**
	 * Determines if a number is prime or not.
	 * Works by checking every number up until sqrt(value) 
	 * for the possibility of being prime. 
	 * @param value - number that is checked for prime
	 * @return - true if prime, false otherwise
	 */
	public static boolean is_prime( int value )
	{
		for (int i = 2; i*i<value; i++)
		{
			if (value % i == 0)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * next_prime
	 * 
	 * Note: static function
	 * 
	 * @param value
	 *            - the starting point to search for a prime
	 * @return - the value if prime, otherwise the next prime after value
	 * proceeding the value parameter
	 */
	public static int next_prime( int value )
	{
		int potentialPrimeNumber = value;
		
		while (true)
		{
			if (is_prime(potentialPrimeNumber))
			{
				return potentialPrimeNumber;
			}
			potentialPrimeNumber++;
		}
		

	}
	
//end of class
}
