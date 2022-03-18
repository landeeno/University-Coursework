package cracking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The Crack class contains the methods required to simulate a simple
 * password hacking program. This class loads txt files that are either 
 * a set of words or hash values, and then uses a variety of 
 * algorithms to hack the passwords using a hash algorithm
 * 
 * 
 * @author Nathan Robbins, Landon Crowther
 * @date April 05, 2017
 *
 */
public class Crack
{
	// boolean which determines what implementation of password hacking to use
	static final boolean I_DID_NOT_STUDY_ALGORITHMS = false;
	
	/**
	 * This method loads a txt file indicated by the file_name, 
	 * and puts each line of text into a String in an ArrayList
	 * 
	 * Will exit if file could not be found.
	 * 
	 * @param file_name - path name of the txt file
	 * @return ArrayList where each line of text is an entry
	 */
	static public ArrayList<String> read_file_into_array(String file_name)
	{

		ArrayList<String> result = new ArrayList<String>();

		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(new File(file_name)));
			String line;
			//load next line into reader
			while ((line = reader.readLine()) != null)
			{
				result.add(line);
			}
			reader.close();
			return result;
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("File not found");
			System.exit(0);
		}

		return result;

	}

	/**
	 * This method is very similar to read_file_into_array, except for rather
	 * than loading every line of text into an array, each line of text
	 * is stored in a HashSet. 
	 * @param file_name - String containing filepath
	 * @return - HashSet with all lines of text
	 */
	static public HashSet<String> read_file_into_hash_set(String file_name)
	{
		HashSet<String> result = new HashSet<>();
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(new File(file_name)));
			String line;
			//load each line
			while ((line = reader.readLine()) != null)
			{
				result.add(line);
			}
			reader.close();
			return result;
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("File not found");
			System.exit(0);
		}

		return result;

	}

	/**
	 * 
	 * This method compares (hashes of) all permutations of up to "Max_Length"
	 * characters vs the given list of hashes (the password file). 
	 * 
	 * This method utilizes a recursive helper method to to check every possible 
	 * combination of characters ranging from a-z. Given a list of hash values and 
	 * the MD5 hash algorithm, this method tries to find all possible combination
	 * of characters (up to max_length) that when hashed are contained in the 
	 * input hashes
	 * 
	 * @param hashes
	 *            - hashes that you are seeing if you can find matches to
	 * @param max_length
	 *            - how many characters the passwords can be (in length)
	 * @return the list of found passwords and their corresponding md5 hashes
	 *         (e.g., [ "cat :
	 *         d077f244def8a70e5ea758bd8352fcd8AB3293292CEF2342ACD32342" ])
	 */
	static public ArrayList<String> brute_force_attack(Collection<String> hashes, int max_length)
	{
		ArrayList<String> successes = new ArrayList<String>();
		StringBuilder so_far = new StringBuilder();
		brute_force_attack(hashes, successes, so_far, 1, max_length);
		return successes;
	}

	/**
	 * Recursive helper method. This method does the bulk of the work for the 
	 * brute_force_attack method. It works by building a string, computing the
	 * hash, and checking if it contained in the hash parameter. 
	 * 
	 * If the hashed word is contained in the hashes parameter, the hashed string is added
	 * to an ArrayList in the specified format.
	 * 
	 * The method requires a depth and a max_length parameters. The depth parameter indicates
	 * how many letters have been guessed yet (ie 'a' would be depth of 1, and 'aa' would be depth of 2)
	 * 
	 * The base case is when the depth is greater than the max_length. The max_length parameter is the
	 * maximum amount of characters to compute. 
	 * 
	 * @param hashes - collection of hash-values that are potential passwords
	 * @param successes - ArrayList that contains all the successful keys in specified format
	 * @param so_far - string of characters that is being modified until a hash is found
	 * @param depth - current length of characters
	 * @param max_length - max length of characters that is being tested for
	 */
	static public void brute_force_attack(Collection<String> hashes,
			ArrayList<String> successes, StringBuilder so_far,
			int depth, int max_length)
	{
		//base case
		if (depth > max_length)
		{
			return;
		}

		String word = so_far.toString();
		String hash = hashWord(word);
		
		if (hashes.contains(hash))
		{
			successes.add(word + " : " + hash);
		}
		
		/*
		 * check for success complete, start recursion:
		 * 
		 * add every character starting from 'a' to the 
		 * end of the current word so_far. Once the 
		 * character has been added,  make the recursive call
		 */
		for (int charInd = 0; charInd < 26; charInd++)
		{
			StringBuilder nextPerm = new StringBuilder(word + (char) ('a' + charInd));
			brute_force_attack(hashes, successes, nextPerm, depth + 1, max_length);
		}
	}

	/**
	 * compare all words in the given list (dictionary) to the password
	 * collection we wish to crack. Works by looping through each
	 * word in the dictionary parameter, and seeing if the hashes parameter
	 * contains the MD5 hash of the word
	 *
	 * @param dictionary
	 *            - The list of likely passwords
	 * @param hashes
	 *            - Collection of the hashwords we are trying to break
	 * @return the list of found passwords and their corresponding md5 hashes
	 *         (e.g., "cat :
	 *         d077f244def8a70e5ea758bd8352fcd8AB3293292CEF2342ACD32342")
	 */
	static public ArrayList<String> dictionary_attack(ArrayList<String> dictionary, Collection<String> hashes)
	{
		
		ArrayList<String> foundPasswords = new ArrayList<String>();
		
		for(String word : dictionary)
		{
			String hash = hashWord(word);
			if (hashes.contains(hash))
			{
				foundPasswords.add(word + " : " + hash);
			}
		}
		
		return foundPasswords;
	}

	/**
	 * Similar to the brute_force_attack method, but utilizing multiple threads
	 * to increase performance.
	 * 
	 * Use up to 8 threads
	 * 
	 * compute all permutations of strings and compare them to a list of already
	 * hashed passwords to see if there is a match
	 * 
	 * @param max_permutation_length
	 *            - how long of passwords to attempt (suggest 6 or less)
	 * @return a list of successfully cracked passwords
	 */
	public static ArrayList<ArrayList<String>> multi_thread_brute_force_attack(int max_permutation_length,
			Collection<String> hashes)
	{
		long start_time = System.nanoTime();
		System.out.println("starting computation");

		ArrayList<Thread> threads = new ArrayList<>();

		int count = 0;
		int AVAILABLE_THREADS = 4;

		ExecutorService thread_pool = Executors.newFixedThreadPool(AVAILABLE_THREADS);
		ArrayList<ArrayList<String>> successes = new ArrayList<ArrayList<String>>();

		for (int i = 0; i < 26; i++)
		{
			successes.add(new ArrayList<>());
		}

		for (int i = 0; i < 26; i++)
		{
			int temp = i;

			thread_pool.execute(new Runnable()
			{

				@Override
				public void run()
				{
					System.out.println("working on permutation " + temp);
					brute_force_attack(hashes, successes.get(temp), new StringBuilder("" + (char) ('a' + temp)), 1,
							max_permutation_length);
				}
			});
			;
		}

		try
		{
			thread_pool.shutdown();
			thread_pool.awaitTermination(1, TimeUnit.DAYS);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		long total_time = System.nanoTime() - start_time;
		System.out.println("done: ( " + (total_time / 1000000000.0) + " seconds )");

		return successes;

	}

	/**
	 * Given a string of characters, compute the MD5 hash
	 * @param word - word that is being hashed
	 * @return - MD5 hash
	 */
	public static String hashWord(String word)
	{
		MessageDigest hash_generator = null;
		try
		{
			hash_generator = java.security.MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			System.out.println("Error occured while hashing " + word + ". Exiting...");
			System.exit(0);
		}

		// build MD5 hash of a permutation
		hash_generator.update(word.getBytes());
		byte[] digest = hash_generator.digest();

		StringBuffer hashword_hex_code = new StringBuffer();
		for (byte b : digest)
		{
			hashword_hex_code.append(String.format("%02x", b & 0xff));
		}

		// use hashword_hex_code to compare to already encrypted/hashed words
		return hashword_hex_code.toString();
	}

	/**
	 * main method for Crack. Loads a file for possible words and 
	 * possible hashes, and then checks to see if any of the 
	 * possible words are passwords (based ont he hash algorithm)
	 * @param args
	 */
	public static void main(String[] args)
	{
		Collection<String> hashes;
		
		if (I_DID_NOT_STUDY_ALGORITHMS)
        {
            hashes = read_file_into_array("Resources/a_few_hashes");
        }
        else
        {
            hashes = read_file_into_hash_set("Resources/hashwords_short");
        }
        
        //multi_thread_brute_force_attack(5, hashes);

        // also do this for your dictionary_attack
        ArrayList<String> dictionary = read_file_into_array("Resources/common_passwords_cain");
        ArrayList<String> success = dictionary_attack(dictionary, hashes);
        System.out.println(success);
	}
}
