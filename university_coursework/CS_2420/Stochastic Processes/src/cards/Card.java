package cards;

public class Card 
{

	boolean is_dealt = false; 

	Suits suit;
	Value value;

	int location;
	public Card(int _location, Suits _suit, int _rank)
	{
		location = _location;
		suit = _suit;
		switch(_rank)
		{
		case 0: value = Value._2; break;
		case 1: value = Value._3; break;
		case 2: value = Value._4; break;
		case 3: value = Value._5; break;
		case 4: value = Value._6; break;
		case 5: value = Value._7; break;
		case 6: value = Value._8; break;
		case 7: value = Value._9; break;
		case 8: value = Value._10; break;
		case 9: value = Value.jack; break;
		case 10: value = Value.queen; break;
		case 11: value = Value.king; break;
		case 12: value = Value.ace; break;
		}
		
	}


	public boolean equals(Card other_card)
	{
		if(other_card.suit == this.suit && other_card.value == this.value)
			return true;

		return false;
	}


	public String toString()
	{
		// TODO
		String output = this.value + " " + this.suit;

		return output;
	}

}
