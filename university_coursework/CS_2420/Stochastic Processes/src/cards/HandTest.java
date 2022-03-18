package cards;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * This class tests that the evaluate function works for
 * each possible set of ranks (ie royal flush to high card). 
 * 
 * Works by setting up a hand that has a specific set of cards, and
 * making sure that the proper rank is returned when evaluated
 * @author Parker Stewart * Landon Crowther
 *
 */
public class HandTest
{

	Hand test;
	
	@Before
	public void setUp() throws Exception
	{
		test = new Hand(false);
	}

	@Test
	public void test_royal_flush()
	{
		int[] rf = {47, 48, 49, 50, 51, 5, 8};
		test.set_specific_hand(rf);
		assertEquals(Rank.Royal_Flush , test.evaluate());		
	}
	
	@Test
	public void straight_flush()
	{
		int[] sf = {0, 1, 2, 3, 4, 15, 18};
		test.set_specific_hand(sf);
		assertEquals(Rank.Straight_Flush , test.evaluate());	
	}
	
	@Test
	public void four_of_kind()
	{
		int[] fk = { 4, 17, 30, 43, 0, 13, 26 };
		test.set_specific_hand(fk);
		assertEquals(Rank.Four_Of_A_Kind, test.evaluate());
	}
	
	@Test
	public void full_house()
	{
		int[] fh = {0, 13, 26, 43, 30, 8, 9};
		test.set_specific_hand(fh);
		assertEquals(Rank.Full_House, test.evaluate());
		
	}
	
	@Test
	public void flush()
	{
		int[] f = {0, 1, 11, 3, 4, 13, 19};
		test.set_specific_hand(f);
		assertEquals(Rank.Flush , test.evaluate());
	}
	
	@Test
	public void straight()
	{
		int[] s = {0, 14, 28, 29, 30, 8, 21};
		test.set_specific_hand(s);
		assertEquals(Rank.Straight , test.evaluate());
	}
	
	@Test
	public void three_of_kind()
	{
		int[] tk = {12, 25, 38, 8, 19, 3, 0};
		test.set_specific_hand(tk);
		assertEquals(Rank.Three_Of_A_Kind , test.evaluate());
	}
	
	@Test
	public void two_pair()
	{
		int[] tp = {0, 13, 1, 14, 25, 28, 32};
		test.set_specific_hand(tp);
		assertEquals(Rank.Two_Pair, test.evaluate());
	}
	
	@Test
	public void one_pair()
	{
		int[] op = {0, 13, 51, 11, 23, 35, 19};
		test.set_specific_hand(op);
		assertEquals(Rank.Pair , test.evaluate());
	}
	
	@Test
	public void high_card()
	{
		int[] hc = {0, 2, 43, 31, 34, 23, 25};
		test.set_specific_hand(hc);
		assertEquals(Rank.High_Card, test.evaluate());
	}

}
