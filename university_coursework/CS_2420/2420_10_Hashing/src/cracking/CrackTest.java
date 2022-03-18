package cracking;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * A few tests for the Crack file
 * 
 * @author Nathan Robbins, Landon Crowther
 * @date April 05, 2017
 *
 */
public class CrackTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testRead_file_into_array()
	{
		// Test that words from a file are successfully parsed
		ArrayList<String> testArr = Crack.read_file_into_array("Resources/a_few_words");
		assertEquals("a", testArr.get(0));
		assertEquals("dog", testArr.get(3));
		assertEquals("van", testArr.get(testArr.size() - 1));
	}

	@Test
	public void testRead_file_into_hash_set()
	{
		// Test that words from a file are successfully parsed
		ArrayList<String> testArr = Crack.read_file_into_array("Resources/a_few_words");
		HashSet<String> testHash = Crack.read_file_into_hash_set("Resources/a_few_words");
		assertTrue(testHash.containsAll(testArr));
		assertEquals(testArr.size(), testHash.size());
	}

	@Test
	public void testBrute_force_attack()
	{
		// Test that correct permutations are found
		HashSet<String> testHashes = new HashSet<String>();
		testHashes.add(Crack.hashWord("a"));
		testHashes.add(Crack.hashWord("b"));
		testHashes.add(Crack.hashWord("cd"));

		ArrayList<String> success = new ArrayList<String>();
		success = Crack.brute_force_attack(testHashes, 2);
		assertEquals(3, success.size());
	}

	@Test
	public void testDictionary_attack()
	{
		// Test that hashes passwords are correctly found when their original
		// words are loaded from a dictionary file
		HashSet<String> testHashes = new HashSet<String>();
		testHashes.add(Crack.hashWord("123"));
		testHashes.add(Crack.hashWord("123123"));
		testHashes.add(Crack.hashWord("1234"));

		ArrayList<String> success = new ArrayList<String>();
		success = Crack.dictionary_attack(Crack.read_file_into_array("Resources/common_passwords_cain"), testHashes);
		assertEquals(3, success.size());
	}

	@Test
	public void testMD5Hash()
	{
		// Test that our MD5 hashes are the same as already calculated hashes
		ArrayList<String> testHashes = Crack.read_file_into_array("Resources/a_few_hashes");
		ArrayList<String> testWords = Crack.read_file_into_array("Resources/a_few_words");

		assertEquals(testHashes.get(0), Crack.hashWord(testWords.get(0)));
		assertEquals(testHashes.get(2), Crack.hashWord(testWords.get(2)));
		assertEquals(testHashes.get(4), Crack.hashWord(testWords.get(4)));
	}

	@Test
	public void testMultiThreadAttack()
	{
		// Test that correct permutations are found
		HashSet<String> testHashes = new HashSet<String>();
		testHashes.add(Crack.hashWord("a"));
		testHashes.add(Crack.hashWord("b"));
		testHashes.add(Crack.hashWord("cd"));

		ArrayList<ArrayList<String>> success = Crack.multi_thread_brute_force_attack(2, testHashes);
		
		assertEquals(1, success.get(0).size());
		assertEquals(1, success.get(1).size());
		assertEquals(1, success.get(2).size());
		assertEquals(0, success.get(3).size());
	}
}
