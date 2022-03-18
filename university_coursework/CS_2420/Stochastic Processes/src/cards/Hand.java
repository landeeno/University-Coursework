package cards;


/**
 * Class that contains methods regarding building hands of either size 5 or 7 and
 * evaluating the rank of those hands. Used for testing exhaustive and stochastic
 * sampling of playing cards.
 * @author Parker Stewart * Landon Crowther
 *
 */

public class Hand 
{


	Rank rank;
	Card[] cards_in_hand;
	int[] histogram;

	/**
	 * Constructor for the hand. if is_five is true then it creates a hand object with 
	 * a card array that can hold 5. If it is false then the constructor does the same
	 * but with array length 7
	 * @param is_five
	 */
	public Hand(   boolean is_five  )
	{
		if (is_five == true)
		{
			this.cards_in_hand = new Card[5];
		}

		else 
		{
			this.cards_in_hand = new Card[7];
		}

		this.histogram = new int[14];


	}

	/**
	 * This method takes in an array of size 5 or 7 (because it calls the clear_hand method
	 * which resets the hand array to either 5 or 7) and then sets the cards to the array in 
	 * the hand, and marks them as dealt 
	 * WARNING: will just return out of the method if the array length is not 5 or 7  
	 * @param hand_values
	 */
	public void set_specific_hand(int[] hand_values)
	{
		Deck.reset_deck();
		clear_hand(hand_values.length);

		if(check_validity(hand_values) == false) 
		{
			return;
		}

		for (int i = 0; i < hand_values.length; i ++)
		{
			this.cards_in_hand[i] = Deck.deck_array[hand_values[i]];
			Deck.deck_array[hand_values[i]].is_dealt = true;
		}

	}


	/**
	 * This method is used in texas holdem
	 * Normally, the cards in a hand are marked as dealt, and they can only be held by one 
	 * hand object at a time. In Texas Holdem however, there are only two unique cards per 
	 * hand object, and the other five are shared amongst the players, so the shared_cards
	 * are not marked as dealt. 
	 * @param hand1 
	 * @param hand2
	 * @param first_filler
	 * @param second_filler
	 * @param shared_cards
	 */
	public static void get_two_random_hands(Hand hand1, Hand hand2 , 
			int[] first_filler, int[] second_filler, int[] shared_cards)
	{
		Deck.reset_deck();

		hand1.clear_hand(7);
		hand2.clear_hand(7);

		hand1.cards_in_hand[0] = Deck.deck_array[first_filler[0]];
		Deck.deck_array[first_filler[0]].is_dealt = true;
		hand1.cards_in_hand[1] = Deck.deck_array[first_filler[1]];
		Deck.deck_array[first_filler[1]].is_dealt = true;

		hand2.cards_in_hand[0] = Deck.deck_array[second_filler[0]];
		Deck.deck_array[second_filler[0]].is_dealt = true;
		hand2.cards_in_hand[1] = Deck.deck_array[second_filler[1]];
		Deck.deck_array[second_filler[1]].is_dealt = true;


		for (int i = 2; i < 7; i++)
		{
			hand1.cards_in_hand[i] = Deck.deck_array[shared_cards[i-2]];
			hand2.cards_in_hand[i] = Deck.deck_array[shared_cards[i-2]];
		}


	}

	/**
	 * Helper method that checks if the hand values passed in are correct
	 * Checks if there are either 5 or 7 cards, checks if they have already been dealt
	 * or not, and checks to make sure there are no duplicate cards in the array
	 * If any of those checks fail, the method returns false and prints a message
	 * of the failure to the console
	 * @param hand_values
	 * @return
	 */

	private boolean check_validity(int[] hand_values)
	{
		if(hand_values.length != 5 && hand_values.length != 7) 
		{
			System.out.println("Illegal Hand size. Try again");
			return false;
		}

		for(int i = 0; i < hand_values.length; i++)
		{
			if (Deck.deck_array[i].is_dealt == true)
			{
				System.out.println("Card already dealt");
				return false;

			}
		}

		for(int j = 1; j < hand_values.length; j++)
		{

			if (hand_values[j] == hand_values[j-1])
			{
				System.out.println("Duplicate cards called, cannot create hand");
				return false;
			}
		}
		return true;
	}


	/**
	 * To string method that returns all the cards in the hand 
	 * by calling the toString() method on each card. 
	 */
	public String toString()
	{
		String output = "";

		for(int i = 0; i < this.cards_in_hand.length; i++)
		{
			output +=  cards_in_hand[i].toString() + ", ";
		}

		return output;
	}

	/**
	 * Helper method that resets the card_in_hand array
	 * Also clears the historgram
	 * @param hand_length
	 */
	private void clear_hand(int hand_length)
	{
		cards_in_hand = new Card[hand_length];
		this.histogram = new int[14]; 
	}


	/**
	 * Method that generates a random hand of either 5 or 7 cards
	 * Uses the random generator to randomly select cards from the deck array 
	 * @param is_five
	 */
	public void get_random_hand( boolean is_five )
	{
		Deck.reset_deck();
		int hand_size = 0;
		//My_Best_Random_Generator random = new My_Best_Random_Generator(); // TODO 

		Javas_Random_Generator random = new Javas_Random_Generator();

		if(is_five) // hand of five cards
		{
			hand_size = 5;
		}
		else
		{
			hand_size = 7;
		}
		clear_hand(hand_size);
		for(int loop_index = 0; loop_index < hand_size; loop_index++)
		{
			// max of 51 since the array indexes are 0-51
			Card chosen_card = Deck.deck_array[random.next_int(51)];
			// if the card was dealt, then loops until a non dealt card is chosen
			while (chosen_card.is_dealt)
			{
				chosen_card = Deck.deck_array[random.next_int(51)];
			}

			chosen_card.is_dealt = true;
			cards_in_hand[loop_index] = chosen_card;	
		}		

	}


	/**
	 * Method that uses and insertion sort to sort the cards in the hand
	 * based off their value
	 */
	public void sort_hand()
	{
		int length = cards_in_hand.length;
		for (int index = 1; index < length; index++)
		{

			Card current_card = cards_in_hand[index];

			if (cards_in_hand[index - 1].value.ordinal() <= current_card.value.ordinal())
			{
				continue; // they are already in the correct order
			} else
			{
				int array_index_copy = index;
				while (array_index_copy != 1)
				{
					if (cards_in_hand[index - 1].value.ordinal() > current_card.value.ordinal() )
					{
						Card temp_card = cards_in_hand[index - 1];
						cards_in_hand[index-1] = current_card;
						cards_in_hand[array_index_copy] = temp_card;

						array_index_copy--;
					} else
						break; // keeps the method from searching the whole loop
				}

			}
		}

	}




	/**
	 * Method that checks the rank of the hand in order of the rank's value
	 * i.e.: Royal flush first, then straight flush, etc...
	 * Returns the rank of the hand
	 * @return the rank (ex: royal flush) as an enum 
	 */
	public Rank evaluate()
	{
		make_histogram();

		int[] straight_evaluation = check_straight();
		boolean flush_evaluation = check_flush();

		// checks for Royal flush or straight flush
		if (straight_evaluation[0] == 1)
		{
			//check if flush also exists
			if (flush_evaluation == true)
			{
				if (straight_evaluation[1] == 13) return Rank.Royal_Flush;

				else return Rank.Straight_Flush;

			}

		}

		if (check_four_of_a_kind()) return Rank.Four_Of_A_Kind;

		if (check_full_house()) return Rank.Full_House;

		if (flush_evaluation) return Rank.Flush;

		if (straight_evaluation[0] == 1) return Rank.Straight;

		if (check_three_of_a_kind()) return Rank.Three_Of_A_Kind;

		if (check_two_pair()) return Rank.Two_Pair;

		if(check_single_pair()) return Rank.Pair;

		// every check failed, so the hand is just a high card
		return Rank.High_Card;
	}

	/**
	 * Checks the hand to see if there is a single pair
	 * @return  true if there is
	 */
	private boolean check_single_pair()
	{

		int count = 0;
		for(int i = 0; i < 13; i++)
		{
			if (histogram[i] == 2)
			{
				count++;
			}
		}

		// there was only one pair
		if (count == 1)
		{
			return true;
		}

		// no pairs
		return false;
	}

	/**
	 * Checks the hand to see if there is two pairs in the hand
	 * @return  true if there is
	 */
	private boolean check_two_pair()
	{

		int count = 0;
		for(int i = 0; i < 13; i++)
		{
			if (histogram[i] == 2)
			{
				count++;
			}
		}

		// there was at least two pairs
		if (count >= 2)
		{
			return true;
		}
		// not two pair
		return false;
	}

	/**
	 * Checks for a three of a kind in the hand
	 * @return true if there is
	 */
	private boolean check_three_of_a_kind()
	{

		for (int i = 0; i < 13; i ++)
		{
			if (histogram[i] == 3)
			{
				return true;
			}
		}
		return false;
	}


	/**
	 * Method that checks for a flush in the hand, not dependent on hand size
	 * Creates an array of length four (four suits) to be used as a histogram
	 * Iterates through the hand and increments the index in the histogram
	 * depending on the suit of the card: 0-clubs, 1-diamonds, 2-hearts, 3-spades
	 * If any of the spots in the array = 5 then there is a flush and the method returns true
	 * @return false if there is no flush
	 */
	private boolean check_flush()
	{
		// 0-clubs, 1-diamonds, 2-hearts, 3-spades
		int[] suit_hist = new int[4];

		for(int i = 0; i < this.cards_in_hand.length; i++)
		{
			if(cards_in_hand[i].suit == Suits.clubs)
			{
				suit_hist[0]++;
				if(suit_hist[0] == 5) return true; // there is a flush
			}
			else if(cards_in_hand[i].suit == Suits.diamonds)
			{
				suit_hist[1]++;
				if(suit_hist[1] == 5) return true; // there is a flush
			}
			else if(cards_in_hand[i].suit == Suits.hearts)
			{
				suit_hist[2]++;
				if(suit_hist[2] == 5) return true; // there is a flush
			}
			else
			{
				suit_hist[3]++;
				if(suit_hist[3] == 5) return true; // there is a flush
			}
		}
		// no flush
		return false;
	}



	/**
	 * This method checks to see if a straight exists in the hand.
	 * It works by starting at the end of the array, and working backwards
	 * through the hand. 
	 * @return an array of ints. The first element in the array is a 1 or 0.
	 * 1 == true (straight exists)
	 * 2 == false (no straight)
	 * 
	 * The second element in the array returns the highest element in the straight
	 * 
	 * For example, the cards "2, 3, 4, 5, 6" would return the following array: 
	 * 		{ 1 , 6 }
	 * 
	 * Note -- high_card refers to the value of the highest card. 13 = ace, 12 = king, ---
	 */
	private int[] check_straight()
	{
		int count = 0;
		int high_card = -1;
		for (int loop_index = 13; loop_index >= 0; loop_index--)
		{

			if (histogram[loop_index] > 0)
			{
				count++;

				if (loop_index > high_card)
				{
					high_card = loop_index;
				}

			}
			else
			{
				count = 0;
			}

			if (count == 5)
			{

				int[] result = {1, loop_index + 4};
				//loop_index + 4 gives the highest value in the straight
				return result;
			}

		}

		int[] result = {0, high_card};
		return result;
	}

	/**
	 * check for a pair of three and a pair of two (full house)
	 * uses two for loops
	 * @return
	 */
	private boolean check_full_house()
	{

		for (int i = 13; i > 0; i--)
		{
			//one part of histogram has at least 3 things, check for a 2 pair
			if ( histogram[i] == 3 )
			{
				for (int j = 13; j > 0; j--)
				{
					//make sure same number isn't getting counted twice
					if (j == i)
					{
						continue;
					}

					//found another pair! it's a full house
					if ( histogram[j] == 2 )
					{
						return true;
					}
				}
			}


			// check if high card is a pair instead of 3 first
			if( histogram[i] == 2)
			{
				for ( int j = 13; j > 0; j--)
				{
					//make sure same number isn't getting counted twice
					if (j == i)
					{
						continue;
					}

					//found 3 of a kind! it's a full house
					if ( histogram[j] == 3 )
					{
						return true;
					}
				}
			}


		}

		//no full house
		return false;
	}

	/**
	 * check all elements in histogram array to see if the value is
	 * four. If the value is four (on any card), there is a 
	 * four of a kind
	 * @return
	 */
	private boolean check_four_of_a_kind()
	{

		for (int i = 0; i < 13; i ++)
		{
			if (histogram[i] == 4)
			{
				return true;
			}
		}
		return false;
	}

	/** Method that iterates through the cards array and increments
	 * the index in the histogram array according to the card's 
	 * rank
	 * Updates the histogram instance variable
	 */

	private void make_histogram()
	{
		for (Card c : cards_in_hand)
		{
			int value = c.value.ordinal();

			if (value == 12)
			{
				histogram[0]++;
				histogram[13]++;
			}
			else
			{
				//value+1 because ace is repeated twice (beginning and end)
				histogram[value+1]++;
			}

		}
	}


}
