package cards;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Class that computes the accuracy of stochastic sampleling, the time it takes
 * to stochastic sample, the ten best cards to get in texas holdem, and the chances
 * of certain cards winning vs. a random spread of cards.
 * Each of the methods called in main print out table formats to the console
 * @authorParker Stewart * Landon Crowther
 *
 */

public class Timing_Accuracy 
{

	static int[] sample_size_array = {100, 1000, 10000, 100_000, 1_000_000, 10_000_000};
	static int experiment_count = 1;


	public static void main(String[] args)
	{
		compute_accuracy();
		time_stochastic();
		texas_hold_em_analysis();
		chances_to_win(50); // input is sample size
		
	}


	/**
	 * Method that calls the get exhaustive results method and get stochastic results method
	 * and then computes the percent error for each rank, adds the total error up, and 
	 * prints out in table format the sample size and the percent error for that size
	 */
	public static void compute_accuracy()
	{
		// Compute exhaustive percentages for each 	
		double[] exhaustive_results = get_exhaustive_results(7);	//actual mathematical percentages

		double[] error_totals_FINAL = new double[10];
		

		System.out.println("Average error for each sample size with experiment count: " + experiment_count);
		System.out.println("Sample Size" + "\t" + "Percent Error");
		for(int i = 0; i < sample_size_array.length; i++)
		{
			double temp_error_total_for_sample_size = 0;
			
			double[] stochastic_results = get_stochastic_results(7, sample_size_array[i]);

			double[] percent_difference_at_each_rank = new double[10];

			for(int j = 0; j < error_totals_FINAL.length; j++)
			{
				percent_difference_at_each_rank[j] = Math.abs((stochastic_results[j] - exhaustive_results[j])); 
			}

			for(int k = 0; k < percent_difference_at_each_rank.length; k++)
			{
				temp_error_total_for_sample_size += percent_difference_at_each_rank[k];
			}

			System.out.println(sample_size_array[i] + "\t" + temp_error_total_for_sample_size);
		}
		System.out.println();

	}


	/**
	 * Method that returns the average percent each rank appeared in the histogram
	 * when computed stochastically. 
	 * The average is computed adding up the percentage each loop iteration, which is "experiment_count"
	 * times, and the divided by that count
	 * @param hand_size- 5 or 7
	 * @param sample_size- how many hands are created and checked
	 * @return
	 */
	public static double[] get_stochastic_results(int hand_size, int sample_size)
	{
		double[] intermediate_array = new double[10]; // added percentages for each rank 
		double[] percentage_histogram_averaged = new double[10];
		int count = 0;

		while (count < experiment_count)
		{
			double[] temp_percentage_hist = Odds.percentage_per_hand_category_stochastic(hand_size, sample_size);

			/*
			 * Adds the percentage of each rank to the array, so the 
			 * average can be computed later
			 */
			for(int i = 0; i < intermediate_array.length; i++)
			{
				intermediate_array[i] += temp_percentage_hist[i];
			}

			count++;
		}

		/*
		 * Once the sample has been computed for amount of loops = to experiment_count
		 * the sample of the percentage of each rank is taken
		 */

		for(int i = 0; i < intermediate_array.length; i++)
		{
			percentage_histogram_averaged[i] = intermediate_array[i] / experiment_count; // computes the average for each rank
		}

		return percentage_histogram_averaged;

	}


	/**
	 * Method that returns the percentage of hands that appeared when calculated exhaustively 
	 * The average is not taken since the percentages should be the same everytime
	 * @param hand_size 5-7
	 * @return
	 */
	public static double[] get_exhaustive_results(int hand_size)
	{
		double[] average_array = new double[10];
		average_array = Odds.percentage_per_hand_category_exhaustive(hand_size);
		return average_array;

	}
	
	
	/**
	 * Method the computes the chances of cards: first card can be any Spade from 2 to Ace,
	 * and the second card can be any other Spade or any Heart 
	 * Computes the odds and prints them out in a table format 
	 * The odds are in the form of favorable : unfavorable outcomes 
	 * @param sample_size
	 */
	public static void chances_to_win(int sample_size)
	{
		
		System.out.println("Calculating odds of spades/hearts or spades/spades winning");
		System.out.println("Card " + "\t" + "Odds");
		for(int first_spades = 39; first_spades < 52; first_spades++)
		{
			for(int second_hearts = 26; second_hearts < 39; second_hearts++)
			{
				
				/*
				 * Testing the odds for a sample of 50 with different cards
				 */
				int count = 0;
				double[] odds = new double[sample_size];
				while(count < sample_size)
				{
				int[] random_cards = get_two_random_cards(first_spades,second_hearts);
				odds[count++] = Odds.odds_to_win(first_spades, second_hearts, random_cards[0], random_cards[1], 1);
				}
				
				int times_won = 0;
				for(int i = 0; i < odds.length; i++)
				{
					times_won += (int)odds[i];
				}
				int unfavorable = (int)(sample_size - times_won);
				
				System.out.print(Deck.deck_array[first_spades].toString() +" " + Deck.deck_array[second_hearts].toString() );
				System.out.println("\t" + times_won + " : " + unfavorable +"\t" + times_won);
			}
			
			for(int second_spades = 39; second_spades < 52; second_spades++)
			{
				if(second_spades == first_spades) continue; // don't want the same card twice
				
				/*
				 * Testing the odds for a sample of sample_size with different cards
				 */
				int count = 0;
				double[] odds = new double[sample_size];
				while(count < sample_size)
				{
				int[] random_cards = get_two_random_cards(first_spades,second_spades);
				odds[count++] = Odds.odds_to_win(first_spades, second_spades, random_cards[0], random_cards[1], 1);
				}
				
				int times_won = 0;
				for(int i = 0; i < odds.length; i++)
				{
					times_won += (int)odds[i];
				}
				int unfavorable = (int)(sample_size - times_won);
				
				System.out.print(Deck.deck_array[first_spades].toString() +" " + Deck.deck_array[second_spades].toString() );
				System.out.println("\t" + times_won + " : " + unfavorable +"\t" + times_won);
				
			}
		}
	}
	
	/**
	 * Helper method for chances_to_win, that returns two random cards that 
	 * have not already been chosen. It is similar to the get_random_hand method
	 * in the Hand class, however it only gets two card hands and returns 
	 * their location in the deck
	 * @return
	 */
	private static int[] get_two_random_cards(int illegal_card1, int illegal_card2)
	{

		Random_Generator random = new Javas_Random_Generator();
		int[] random_cards = new int[2];
		
		int card_index = random.next_int(51);
		
		if(card_index == illegal_card1 || card_index == illegal_card2)
		{
			while(card_index == illegal_card1 || card_index == illegal_card2)
			{
				card_index = random.next_int(51);
			}
		}
		random_cards[0] = card_index;
		
		// the second card cannot be the already chosen cards
		if(card_index == illegal_card1 || card_index == illegal_card2 || card_index == random_cards[0])
		{
			while(card_index == illegal_card1 || card_index == illegal_card2 || card_index == random_cards[0])
			{
				card_index = random.next_int(51);
			}
		}
		random_cards[1] = card_index;
		
		return random_cards;
		
	}
	
	
	
	
	/**
	 * method that computes how much time is required to stochastically rank 
	 * each hand. Works by starting a counter, making _n_ random hands (associated with 
	 * times array), and calling the evaluate method on those hands 500_000 times.
	 * 
	 * This is repeated on 5-card hards and 7-card hands
	 */
	public static void time_stochastic()
	{
		int[] times = { 1000, 250_000, 500_000, 750_000, 1_000_000, 2_500_000, 5_000_000, 7_500_000, 10_000_000};
		final int average_count = 100;

		System.out.println("Stochastic Timing: 5 Cards");
		System.out.println("Stochastic Sample Count:" + "\t" + "Average Time (s)");

		for (int i = 0; i < times.length; i++)
		{
			int count = 0;
			double elapsed_time = 0;
			int times_count = 0;

			while(count < average_count)
			{
				while(times_count < times[i])
				{
					double start_time = System.nanoTime();
					Hand h = new Hand(true);
					h.get_random_hand(true);
					elapsed_time += System.nanoTime() - start_time;
					times_count++;
				}
				count++;
			}
			
			elapsed_time /= average_count;
			elapsed_time /= 1_000_000_000;
			
			System.out.println(times[i] + "\t" + elapsed_time);
		

		}

		System.out.println();
		System.out.println("Stochastic Timing: 7 Cards");
		System.out.println("Stochastic Sample Count:" + "\t" + "Average Time (s)");

		for (int i = 0; i < times.length; i++)
		{
			
			int count = 0;
			double elapsed_time = 0;
			int times_count = 0;

			while(count < average_count)
			{
				while(times_count < times[i])
				{
					double start_time = System.nanoTime();
					Hand h = new Hand(false);
					h.get_random_hand(false);
					elapsed_time += System.nanoTime() - start_time;
					times_count++;
				}
				count++;
			}
			
			elapsed_time /= average_count;
			elapsed_time /= 1_000_000_000;
			
			System.out.println(times[i] + "\t" + elapsed_time);
			

		}

		double time_exh_five = System.nanoTime();
		Odds.percentage_per_hand_category_exhaustive(5);
		time_exh_five = System.nanoTime() - time_exh_five;

		double time_exh_seven = System.nanoTime();
		Odds.percentage_per_hand_category_exhaustive(7);
		time_exh_seven = System.nanoTime() - time_exh_seven;

		System.out.println();
		System.out.println("Exhastive 5 (s): " + time_exh_five / 1_000_000_000);
		System.out.println("Exhastive 7 (s): " + time_exh_seven / 1_000_000_000);

	}

	/**
	 * Stochastically determines the best two-card starting hand to have in
	 * texas hold em. This method works by performing the following actions:
	 * 
	 * 1) Creating an ArrayList (possible_start_hands) that contains every
	 * possible 2-card pair, starting at deck indices [0,1] [0,2] --- [50,51]
	 * 
	 * 2) Making a PriorityQueue that will contain the top 10 highest scoring
	 * start hands
	 * 
	 * 3) Comparing each start hand to EVERY OTHER start hand, making sure not
	 * to "play" against itself
	 * 
	 * 4) Translating the PriorityQueue of integer values (corresponding to the
	 * indices in the possible_start_hands) to the exact set of hands that
	 * scored the highest.
	 * 
	 * Scoring works as follows:
	 * 
	 * The Odds class has a method titled "odds_to_win" , in which given two
	 * hands, it computes the odds that hand 1 will beat hand 2. the odds_to_win
	 * method was utilized in the scoring analysis.
	 * 
	 * Each starting hand had the odds_to_win method called on it and EVERY
	 * other possible starting hand. Each time the starting hand won, the result
	 * from odds_to_win was added to a double value. After the original start
	 * hand had been compared to every other possible hand, its total score was
	 * divided by the number of hands it was compared to. This gave us an
	 * "average" score for the hand.
	 * 
	 * This score was added to the PriorityQueue, and only the top 10 scoring
	 * hands were kept in the queue.
	 */
	public static void texas_hold_em_analysis()
	{
		System.out.println();
		ArrayList<Integer[]> possible_start_hands = new ArrayList<>();

		// adding all possible start hand combinations to possible_start_hands
		for (int first = 0; first < 51; first++)
		{
			for (int second = first + 1; second < 52; second++)
			{
				Integer[] possibility = { first, second };
				possible_start_hands.add(possibility);
			}
		}

		// initialziing the PriorityQueue that will store the top 10 scoring
		// hands

		//variable indicated how many of the "top" elements we want to keep track of 
		final int number_of_top_elements = 10;

		PriorityQueue<HashMapCustom> top_10 = new PriorityQueue<>(number_of_top_elements, new HashMapCustomComparator());

		// final variable that determines how many "games" of poker each set of
		// hands must play
		final int sample_size = 50;

		/*
		 * This loop compares each hand (noted by "hand_of_interest") to EVERY
		 * other possible hand. The odds_to_win method is called on
		 * "hand_of_interest" and some other hand, and they play "sample_size"
		 * games. The odds that hand_of_interest wins is recorded, and
		 * hand_of_interest repeats this process with another possible start
		 * hand. The "score" continues to accumulate, until the hand_of_interest
		 * has played every other possible hand. The score is then normalized,
		 * by dividing the score by the number of other hands it played against.
		 */
		for (int hand_of_interest = 0; hand_of_interest < possible_start_hands.size(); hand_of_interest++)
		{
			double odds_for_hand_of_interest = 0;
			for (int every_other_possible_hand = 0; every_other_possible_hand < possible_start_hands
					.size(); every_other_possible_hand++)
			{
				// hand_of_interest can't play itself, move on to the next hand
				if (hand_of_interest == every_other_possible_hand)
				{
					continue;
				}
				// add to current score
				odds_for_hand_of_interest += Odds.odds_to_win(possible_start_hands.get(hand_of_interest)[0],
						possible_start_hands.get(hand_of_interest)[1],
						possible_start_hands.get(every_other_possible_hand)[0],
						possible_start_hands.get(every_other_possible_hand)[1], sample_size);
			}

			/*
			 * normalize score note, the possible_start_hand-1 is to account for
			 * the fact that hand_of_interest did not play against itself
			 */
			odds_for_hand_of_interest = odds_for_hand_of_interest / (possible_start_hands.size() - 1);

			// store the index of hand_of_interest and it's calculated store
			HashMapCustom temp = new HashMapCustom(hand_of_interest, odds_for_hand_of_interest);
			// add to the PriorityQueue
			top_10.add(temp);

			// pop once the size of PriorityQueue has reached capacity (10)
			if (top_10.size() > number_of_top_elements)
			{
				top_10.poll();
			}
		}

		// scoring complete, translating PriorityQueue to usable data

		int[] top_10_keys = new int[10];
		// head = lowest
		for (int i = 9; i >= 0; i--)
		{
			top_10_keys[i] = top_10.remove().get_key();
		}

		ArrayList<Integer[]> best_hands = new ArrayList<>();

		for (int i = 0; i < 10; i++)
		{
			best_hands.add(possible_start_hands.get(top_10_keys[i]));
		}

		System.out.println("Best possible hands:");
		for (Integer[] i : best_hands)
		{
			System.out.println(Deck.deck_array[i[0]].toString() + " & " + Deck.deck_array[i[1]].toString());
		}
	}

	/**
	 * Comparator for HashMapCustom object. Works by comparing the value
	 * variable in two HashMapCustom objects.
	 *
	 */
	private static class HashMapCustomComparator implements Comparator<HashMapCustom>
	{
		@Override
		public int compare(HashMapCustom left, HashMapCustom right)
		{
			/*
			 * Because the value in a HashMapCustom will most likely be a number
			 * between 0-1, the value is multiplied by 10,000. This ensures that
			 * (typcially) a number other than 0 will be returned when casting
			 * the difference to an int. By multiplying by 10,000, we ensure
			 * that we're comparing everything up until the ten-thousandth place
			 * in the value parameter.
			 */
			int multiplier = 10_000;
			double result = (left.get_value() * multiplier) - (right.get_value() * multiplier);
			return (int) result;
		}
	}

	/**
	 * Helper class that is used in the texas_hold_em_analysis
	 * 
	 * Essentially just a non-generic simplified version of Java's HashMap
	 * object. In all honesty, it was easier just write a simplified,
	 * customizeable class rather than trying to implement Java's.
	 * 
	 * The only functionality we needed was the key-value link
	 *
	 */
	private static class HashMapCustom
	{

		Integer key;
		Double value;

		public HashMapCustom(Integer key, Double value)
		{
			this.key = key;
			this.value = value;
		}

		public Integer get_key()
		{
			return this.key;
		}

		public Double get_value()
		{
			return this.value;
		}
	}



}
