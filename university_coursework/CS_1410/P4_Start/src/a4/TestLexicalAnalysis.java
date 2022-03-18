/**
 * Landon Crowther
 * u0926601
 * CS 1410 - HW 4
 */
package a4;
import java.io.File;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests required code in the LexicalAnalysis class. You should add
 * additional tests and asserts to more carefully test your code.
 * @author David Johnson
 *
 */
public class TestLexicalAnalysis {

	/**
	 * Test the word count done on a simple file. The last parameter
	 * of the assert is a margin of error allowed.
	 */
	@Test
	public void testAnalyzeFilePercentage() {
		File file = new File("TestWords.txt");
		assertEquals(4.16 ,LexicalAnalysis.analyzeFile(file,"hi"), 0.01);		//TestWords has "Hi", not "hi" -- analyzeFile still works
		assertEquals(0.00 ,LexicalAnalysis.analyzeFile(file, "asdf"), 0.01);	//"asdf" is not in TestWords.txt
		
	}

	/**
	 * Test the percentage computation
	 */
	@Test
	public void testComputePercentage() {
		assertEquals(10.0 ,LexicalAnalysis.computePercentage(10,100), 0.0001);
		assertEquals(33.33333333 ,LexicalAnalysis.computePercentage(1, 3), 0.001); 
		assertEquals(0.0,LexicalAnalysis.computePercentage(0,1), 0.01);
		assertEquals(100.0,LexicalAnalysis.computePercentage(1, 1), 0.01);
		
		//assertEquals("Cannot divide by zero" ,LexicalAnalysis.computePercentage(1, 0), 0.01);
		
		//This test is commented out for a reason:
		//in my computePercentage code, I have the method set to 
		//	1) print out "Cannot divide by zero" &
		//	2) terminate the system. If the system is terminated, the remainder of 
		//		the TestLexicalAnalysis program stops, and therefore the rest of the 
		//		methods aren't tested. Un-comment this method to check it.
	}

	/**
	 * Test the makeBar method
	 */
	@Test
	public void testMakeBar() {
		assertEquals("++1", LexicalAnalysis.makeBar(3.01,'1'));	// successfully rounds down
		assertEquals("++@", LexicalAnalysis.makeBar(2.78,'@'));	// successfully rounds up
	}

	/**
	 * Test the cleanToken method. It shows that a token with stuff
	 * before and after is cleaned up, but also that a token with too
	 * much punctuation is not fully cleaned up.
	 */
	@Test
	public void testCleanToken() {
		assertEquals("IF", LexicalAnalysis.cleanToken(".if\'?")); 
		assertEquals("IF\'\"", LexicalAnalysis.cleanToken(".if\'\".?")); 
		assertEquals("HI\"", LexicalAnalysis.cleanToken(".hi\".."));
		assertEquals("HI", LexicalAnalysis.cleanToken("hi"));		//works for regular words
		assertEquals("?HI", LexicalAnalysis.cleanToken("??hi."));	//doesn't clean up the second ?
		
		//assertEquals("Index out of bounds", LexicalAnalysis.cleanToken("?"));
		
		//This test is commented out for a reason:
		//in my cleanToken code, I have the method set to 
		//	1) print out "Index out of bound" &
		//	2) terminate the system. If the system is terminated, the remainder of 
		//		the TestLexicalAnalysis program stops, and therefore the rest of the 
		//		methods aren't tested. Un-comment this method to check it.

	}
	@Test
	public void testCheckPunct() {
		assertEquals(true, LexicalAnalysis.checkPunct('!'));	//true b/c ! is a punctuation mark
		assertEquals(false, LexicalAnalysis.checkPunct('d'));	//false b/c 'a' is not a punctuation mark
	}
}
