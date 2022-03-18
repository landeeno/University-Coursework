package cards;

import java.util.ArrayList;

/**
 * This class does all the statistical work involved with determining
 * the chances that certain poker have at winning
 * 
 * @author Parker Stewart * Landon Crowther
 *
 */

public class Odds
{

	// histogram associated with exhaustive count
	static double[] total_rank_hist = new double[10];
	// histogram assoicated with stochastic count
	static double[] random_rank_hist = new double[10];
	static double total_hands_calculated = 0;
	
	/**
	 * Method that takes in four cards as parameters and returns the odds of hand1 beating hand2 
	 * given the amount of samples
	 * @param h1c1
	 * @param h1c2
	 * @param h2c1
	 * @param h2c2
	 * @param samples
	 * @return
	 */
	static double odds_to_win(int h1c1, int h1c2, int h2c1, int h2c2, int samples)
	{

		int count = 0; 


		Hand hand1 = new Hand(false);
		Hand hand2 = new Hand(false);
		ArrayList<Integer> illegal_cards = new ArrayList<>();
		illegal_cards.add(h1c1); illegal_cards.add(h1c2); illegal_cards.add(h2c1); illegal_cards.add(h2c2);
		int[] first_filler = {h1c1, h1c2};
		int[] second_filler = {h2c1, h2c2};

		double hand_1_odds = 0;


		while (count < samples)
		{
			int[] shared_cards = return_five(illegal_cards);
			Hand.get_two_random_hands(hand1, hand2,first_filler,second_filler, shared_cards);

			int rank1 = hand1.evaluate().ordinal();
			int rank2 = hand2.evaluate().ordinal();

			if (rank1 == rank2)
			{
				boolean did_hand1_win = tie_breaker(hand1,hand2);
				if(did_hand1_win) hand_1_odds++;
			}
			else 
			{
				if (rank1 > rank2)
				{
					hand_1_odds++;
				}
			}

			count++;
		}

		return (hand_1_odds / (double)samples); 

	}

	/**
	 * Method that checks to see if hand1 has the high card vs. hand2
	 * If they are equal or hand2 has the higher card, the method returns false
	 * @param hand1
	 * @param hand2
	 * @return true if hand1 wins the tiebreaker
	 */
	private static boolean tie_breaker(Hand hand1, Hand hand2)
	{
		hand1.sort_hand();
		hand2.sort_hand();

		for(int i = hand1.cards_in_hand.length - 1; i >= 0; i-- )
		{
			if(hand1.cards_in_hand[i].value.ordinal() > hand2.cards_in_hand[i].value.ordinal())
			{
				return true;
			}
			else if ( hand1.cards_in_hand[i].value.ordinal() < hand2.cards_in_hand[i].value.ordinal())
			{
				return false;
			}
		}
		return false;
	}

	/**
	 * Given an array list of "illegal cards" (containing integers values
	 * referring to the location of these cards in the deck object) , returns an
	 * array list of random deck values that are not in the illegal cards parameter,
	 * and has no duplicates.  
	 * @param illegal_cards - array list containing four card integers
	 * @return - array list containing 5 random card integers
	 */
	private static int[] return_five(ArrayList<Integer> illegal_cards)
	{
		ArrayList<Integer> return_array = new ArrayList<>();
		int count = 0;

		//		My_Best_Random_Generator random = new My_Best_Random_Generator(); //TODO
		//		random.set_constants(25214903917L, 11);
		Javas_Random_Generator random = new Javas_Random_Generator();

		while (count != 5)
		{
			int temp = random.next_int(51);

			if (!illegal_cards.contains(temp) && !return_array.contains(temp) )
			{
				return_array.add(temp);
				count++;
			}	
		}
		int[] output_array = new int[5];
		for (int i = 0; i < output_array.length; i ++)
		{
			output_array[i] = return_array.get(i);
		}

		return output_array;
	}


	/**
	 * Method the exhaustively computes the percentage each hand has of appearing given a deck of cards
	 * Iterates through every hand combination there is, and uses a histogram to increment each value 
	 * corresponding to a certain rank (High card at 0, Royal Flush at 10) 
	 * Then computes the percentage of each hand appearance over the total number of hands possible
	 * Returns an empty array if the parameter is not 5 or 7
	 * 
	 * @param hand_size
	 * @return array of percentages for each possible hand out of the whole deck. Should add up to 100%
	 */
	static double[] percentage_per_hand_category_exhaustive(int hand_size)
	{
		double[] rank_hist = new double[10];
		double total = 0;

		if (hand_size == 5)
		{
			for(int first = 0; first < 48; first++ )
			{
				for(int second = first + 1; second < 49; second++ )
				{
					for(int third = second + 1; third < 50; third++)
					{
						for(int fourth = third + 1; fourth < 51; fourth++)
						{
							for(int fifth = fourth + 1; fifth < 52; fifth++)
							{
								Hand hand = new Hand(true);
								int[] deck_vals = {first, second, third, fourth, fifth};
								hand.set_specific_hand(deck_vals);
								int rank = hand.evaluate().ordinal();
								rank_hist[rank]++;
								total++;
							}
						}
					}
				}
			}
			total_hands_calculated = total;
			double[] percent_array = new double[rank_hist.length];
			int reverse_index = rank_hist.length - 1;
			int second_reverse_index = rank_hist.length - 1;

			for(int index = 0; index < rank_hist.length; index++)
			{
				percent_array[reverse_index--] = (rank_hist[index] / total) * 100;
				total_rank_hist[second_reverse_index--] = rank_hist[index];
			}

			return percent_array;


		}

		if (hand_size == 7)
		{
			for(int first = 0; first < 46; first++ )
			{
				for(int second = first + 1; second < 47; second++ )
				{
					for(int third = second + 1; third < 48; third++)
					{
						for(int fourth = third + 1; fourth < 49; fourth++)
						{
							for(int fifth = fourth + 1; fifth < 50; fifth++)
							{
								for(int sixth = fifth + 1; sixth < 51; sixth++)
								{
									for(int seventh = sixth + 1; seventh < 52; seventh++)
									{
										Hand hand = new Hand(false);
										int[] deck_vals = {first, second, third, fourth, fifth, sixth, seventh};
										hand.set_specific_hand(deck_vals);
										int rank = hand.evaluate().ordinal();
										rank_hist[rank]++;
										total++;
									}
								}

							}
						}
					}
				}
			}
			total_hands_calculated = total;
			double[] percent_array = new double[rank_hist.length];
			int reverse_index = rank_hist.length - 1;
			int second_reverse_index = rank_hist.length - 1;

			for(int index = 0; index < rank_hist.length; index++)
			{
				percent_array[reverse_index--] = (rank_hist[index] / total) * 100;
				total_rank_hist[second_reverse_index--] = rank_hist[index];
			}

			return percent_array;

		}	

		// just returns an empty array if the hand size was not 5 or 7 
		return rank_hist;		
	}
	
	/**
	 * Method that returns the total number of hands calculated exhaustively
	 * @return
	 */
	static double get_total_hands()
	{
		return total_hands_calculated;
	}

	/**
	 * Helper method to be used in the timing class to compute the weighted average of each rank
	 * @return
	 */
	static double[] get_total_per_rank()
	{
		return total_rank_hist;
	}
	
	/**
	 * Helper method to be used in the timing class that returns the total number of ranks
	 * computed stochastically 
	 * @return
	 */
	static double[] get_random_per_rank()
	{
		return random_rank_hist;
	}

	/**
	 * Method that stochastically computes the percentages each hand has of randomly appearing in a deck
	 * Similar to the percentage_per_hand_category_exhaustive method, except it is random
	 * instead of computing every possible hand
	 * @param hand_size
	 * @param random_samples
	 * @return
	 */
	static double[] percentage_per_hand_category_stochastic(int hand_size , int random_samples)
	{
		
		double[] rank_hist = new double[10];
		
		int total = 0;
		boolean is_five = true;
		if (hand_size == 7)
		{
			is_five = false;
		}
	
		while (total < random_samples)
		{
			Hand hand = new Hand(is_five);
			hand.get_random_hand(is_five);
			int rank = hand.evaluate().ordinal();
			rank_hist[rank]++;
			total++;
		}
		
		
		double[] percent_array = new double[rank_hist.length];
		int reverse_index = rank_hist.length - 1;
		int second_reverse_index = rank_hist.length - 1;
		for(int index = 0; index < rank_hist.length; index++)
		{
			//add the stochastic results back to return array in percent form (ie 10% rather than 0.1)
			percent_array[reverse_index--] = (rank_hist[index] / total) * 100;
			random_rank_hist[second_reverse_index--] = rank_hist[index];
		}
		return percent_array;

	}
	
	

	
}
