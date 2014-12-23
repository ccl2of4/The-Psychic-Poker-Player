public final class CardReader {

	private CardReader () {}

	public static Card readCard (String cardString) {
		assert (cardString.length () == 2);

		char faceValChar = cardString.charAt (0);
		char suitChar = cardString.charAt (1);

		Card.FaceValue faceVal;
		Card.Suit suit;

		switch (faceValChar) {
			case 'A' :
				faceVal = Card.FaceValue.ACE;
				break;
			case '2' :
				faceVal = Card.FaceValue.TWO;
				break;
			case '3' :
				faceVal = Card.FaceValue.THREE;
				break;
			case '4' :
				faceVal = Card.FaceValue.FOUR;
				break;
			case '5' :
				faceVal = Card.FaceValue.FIVE;
				break;
			case '6' :
				faceVal = Card.FaceValue.SIX;
				break;
			case '7' :
				faceVal = Card.FaceValue.SEVEN;
				break;
			case '8' :
				faceVal = Card.FaceValue.EIGHT;
				break;
			case '9' :
				faceVal = Card.FaceValue.NINE;
				break;
			case 'T' :
				faceVal = Card.FaceValue.TEN;
				break;
			case 'J' :
				faceVal = Card.FaceValue.JACK;
				break;
			case 'Q' :
				faceVal = Card.FaceValue.QUEEN;
				break;
			case 'K' :
				faceVal = Card.FaceValue.KING;
				break;
			default :
				assert (false);
				faceVal = Card.FaceValue.ACE;
		}

		switch (suitChar) {
			case 'H' :
				suit = Card.Suit.HEARTS;
				break;
			case 'D' :
				suit = Card.Suit.DIAMONDS;
				break;
			case 'C' :
				suit = Card.Suit.CLUBS;
				break;
			case 'S' :
				suit = Card.Suit.SPADES;
				break;
			default :
				assert (false);
				suit = Card.Suit.HEARTS;
		}

		Card result = new Card (faceVal, suit);
		return result;
	}
}