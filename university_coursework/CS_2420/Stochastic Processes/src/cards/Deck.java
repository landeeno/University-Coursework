package cards;


/**
 * Class that holds a static array of cards: a playing card deck
 * This deck is used in all of the methods in this package and is static
 * @authorParker Stewart * Landon Crowther
 *
 */
public class Deck 
{
	

	/**
	 * Initializes a static final array to be used by every instance of hand and card
	 * Adds instances of card to the array creating a full deck of playing cards
	 */
	static final Card[] deck_array = new Card[52];
	static 
	{
		for (int i=0; i < 13; i++)
		{
			deck_array[i] = new Card(i,Suits.clubs,i % 13);
		}
		for (int i = 13; i < 26; i++)
		{
			deck_array[i] = new Card(i,Suits.diamonds,i % 13);
		}
		for (int i = 26; i < 39; i++)
		{
			deck_array[i] = new Card(i,Suits.hearts,i % 13);
		}
		for (int i = 39; i < 52; i++)
		{
			deck_array[i] = new Card(i,Suits.spades,i % 13);
		}
	}	
	
	/**
	 * Resets the deck by setting all the cards to not be dealt
	 */
	public static void reset_deck()
	{
		for(int i = 0; i < 52; i++)
		{
			deck_array[i].is_dealt = false;
		}
	}



}
