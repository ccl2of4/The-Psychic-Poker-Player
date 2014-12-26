public class Card implements Comparable<Card> {
	
	public enum Suit {
		SPADES, CLUBS, HEARTS, DIAMONDS, NONE
	}

	public enum FaceValue  {
		ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5),
		SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
		JACK(11), QUEEN(12), KING(13);

		private int val;
		private FaceValue (int val) {
			this.val = val;
		}
		public int getVal () {
			return val;
		}
	}



	private FaceValue faceval;
	private Suit suit;

	public Card (FaceValue faceval, Suit suit) {
		this.faceval = faceval;
		this.suit = suit;
	}
	public Suit getSuit () {
		return suit;
	}
	public FaceValue getFaceValue () {
		return faceval;
	}

	@Override
	public String toString () {
		String result = "";

		switch (faceval) {
			case ACE :
				result += 'A';
				break;
			case TWO :
				result += '2';
				break;
			case THREE :
				result += '3';
				break;
			case FOUR :
				result += '4';
				break;
			case FIVE :
				result += '5';
				break;
			case SIX :
				result += '6';
				break;
			case SEVEN :
				result += '7';
				break;
			case EIGHT :
				result += '8';
				break;
			case NINE :
				result += '9';
				break;
			case TEN :
				result += 'T';
				break;
			case JACK :
				result += 'J';
				break;
			case QUEEN :
				result += 'Q';
				break;
			case KING :
				result += 'K';
				break;
			default :
				assert (false);
		}

		switch (suit) {
			case HEARTS :
				result += "H";
				break;
			case CLUBS :
				result += "C";
				break;
			case DIAMONDS :
				result += "D";
				break;
			case SPADES :
				result += "S";
				break;
			default :
				assert (false);
		}

		return result;
	}

	/* comparable */
	@Override
	public int compareTo (Card other) {
		return this.faceval.getVal () - other.faceval.getVal ();
	}
}