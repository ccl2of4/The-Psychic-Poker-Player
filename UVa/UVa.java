import java.util.List;
import java.util.Iterator;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;


import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.LinkedHashMap;
import java.util.Arrays;
import java.util.Stack;






class Card implements Comparable<Card> {
	
	public enum Suit {
		SPADES, CLUBS, HEARTS, DIAMONDS, NONE
	}

	public enum FaceValue  {
		LOW_ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5),
		SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
		JACK(11), QUEEN(12), KING(13), HIGH_ACE (14);

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
	public void flipAceValue () {
		if (this.faceval == FaceValue.LOW_ACE)
			this.faceval = FaceValue.HIGH_ACE;
		else if (this.faceval == FaceValue.HIGH_ACE)
			this.faceval = FaceValue.LOW_ACE;
	}

	@Override
	public String toString () {
		String result = "";

		switch (faceval) {
			case LOW_ACE :
				result += 'A';
				break;
			case HIGH_ACE :
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














final class PokerHand implements Comparable<PokerHand>, Iterable<Card> {

	private static LinkedHashMap<String, PokerHandFinder> hands;

	private HashSet<Card> cards;
	private String key;

	static {

		hands = new LinkedHashMap<String, PokerHandFinder> ();

		hands.put ("straight-flush", new PokerHandFinder () {
			public int getPriority () { return 0; }
			public boolean find (PokerHand p) {
				return p.findStraightFlush ();
		}});

		hands.put ("four-of-a-kind", new PokerHandFinder () {
			public int getPriority () { return 1; }
			public boolean find (PokerHand p) {
				return p.findFourOfAKind ();
		}});

		hands.put ("full-house", new PokerHandFinder () {
			public int getPriority () { return 2; }
			public boolean find (PokerHand p) {
				return p.findFullHouse ();
		}});

		hands.put ("flush", new PokerHandFinder () {
			public int getPriority () { return 3; }
			public boolean find (PokerHand p) {
				return p.findFlush ();
		}});

		hands.put ("straight", new PokerHandFinder () {
			public int getPriority () { return 4; }
			public boolean find (PokerHand p) {
				return p.findStraight ();
		}});

		hands.put ("three-of-a-kind", new PokerHandFinder () {
			public int getPriority () { return 5; }
			public boolean find (PokerHand p) {
				return p.findThreeOfAKind ();
		}});

		hands.put ("two-pairs", new PokerHandFinder () {
			public int getPriority () { return 6; }
			public boolean find (PokerHand p) {
				return p.findTwoPairs ();
		}});

		hands.put ("one-pair", new PokerHandFinder () {
			public int getPriority () { return 7; }
			public boolean find (PokerHand p) {
				return p.findOnePair ();
		}});

		hands.put ("highest-card", new PokerHandFinder () {
			public int getPriority () { return 8; }
			public boolean find (PokerHand p) {
				return p.findHighestCard ();
		}});

	}

	public PokerHand (Set<Card> cards) {
		this.cards = new HashSet<Card> (cards);
		this.key = null;
		compute ();
	}

	public PokerHand (List<Card> cards) {
		this.cards = new HashSet<Card> (cards);
		this.key = null;
		compute ();
	}

	public PokerHand (PokerHand other) {
		this.cards = new HashSet<Card> (other.cards);
		this.key = other.key;
	}

	public final Iterator<Card> iterator () {
		return cards.iterator ();
	}

	public final void swap (Card oldCard, Card newCard) {
		this.cards.remove (oldCard);
		this.cards.add (newCard);
		compute ();
	}

	@Override
	public final String toString () {
		return key;
	}

	@Override
	public final int compareTo (PokerHand other) {
		PokerHandFinder thisFinder = hands.get (this.key);
		PokerHandFinder otherFinder = hands.get (other.key);

		return otherFinder.getPriority () - thisFinder.getPriority ();
	}

	private final void compute () {
		for (String key : hands.keySet ()) {
			PokerHandFinder p = hands.get (key);
			if (p.find (this)) {
				this.key = key;
				break;
			}
		}
		assert (this.key != null);
	}

	private final boolean findStraightFlush () {
		return findStraight () && findFlush ();
	}

	private final boolean findFourOfAKind () {
		List<Card> cardsList = new ArrayList<Card> (cards);
		return findMatches (cardsList, 4, false);
	}

	private final boolean findFullHouse () {
		List<Card> cardsList = new ArrayList<Card> (cards);
		return findMatches (cardsList, 3, true) && findMatches (cardsList, 2, false);
	}

	private final boolean findFlush () {
		Card.Suit suit = Card.Suit.NONE;
		for (Card card : cards) {
			if (suit == Card.Suit.NONE)
				suit = card.getSuit ();
			else if (suit != card.getSuit ())
				return false;
		}
		return true;
	}

	private final boolean findStraight () {
		Card[] array = cards.toArray (new Card[0]);
		Arrays.sort (array);

		assert (array.length > 0);

		Card lastCard = array[0];

		boolean found = true;
		for (int i = 1; i < array.length; ++i) {
			Card currentCard = array[i];

			if (currentCard.getFaceValue().getVal () != (lastCard.getFaceValue().getVal () + 1) ) {
				found = false;
				break;
			}

			lastCard = currentCard;
		}
		if (found) return true;


		for (Card card : array)
			card.flipAceValue ();
		Arrays.sort (array);

		lastCard = array[0];
		for (int i = 1; i < array.length; ++i) {
			Card currentCard = array[i];

			if (currentCard.getFaceValue().getVal () != (lastCard.getFaceValue().getVal () + 1) ) {
				return false;
			}

			lastCard = currentCard;
		}

		return true;
	}

	private final boolean findThreeOfAKind () {
		List<Card> cardsList = new ArrayList<Card> (cards);
		for (int i = 0; i < cardsList.size (); ++i) {
			Card first = cardsList.get (i);

			for (int j = i + 1; j < cardsList.size (); ++j) {
				Card second = cardsList.get (j);

				if (first.getFaceValue () == second.getFaceValue ()) {
					for (int k = j + 1; k < cardsList.size (); ++k) {
						Card third = cardsList.get (k);

						if (third.getFaceValue () == second.getFaceValue ())
							return true;
					}
				}
			}
		}
		return false;
	}

	private final boolean findTwoPairs () {
		List<Card> cardsList = new ArrayList<Card> (cards);
		return findMatches (cardsList, 2, true) && findMatches (cardsList, 2, false);
	}

	private final boolean findOnePair () {
		List<Card> cardsList = new ArrayList<Card> (cards);
		return findMatches (cardsList, 2, false);
	}

	private final boolean findHighestCard () {
		return true;
	}

	private static final boolean findMatches (List<Card> cards, int numToMatch, boolean remove) {
		assert (numToMatch > 0);
		Stack<Integer> matchedIndices;

		for (int i = 0; i < cards.size (); ++i) {
			Card first = cards.get (i);
			Card.FaceValue faceVal = first.getFaceValue ();
			int numLeft = numToMatch;

			matchedIndices = new Stack<Integer> ();
			matchedIndices.push (i);

			loop: while (matchedIndices.size () < numToMatch) {
				
				int lastIdx = matchedIndices.peek ();
				for (int j = lastIdx + 1;; ++j) {
					if (j >= cards.size ())
						break loop;

					Card candidate = cards.get (j);

					if (candidate.getFaceValue () == faceVal) {
						matchedIndices.push (j);
						break;
					}
				}

			}
			if (matchedIndices.size () == numToMatch) {
				
				if (remove) {
					while (!matchedIndices.empty ()) {
						int removeIdx = matchedIndices.pop ();
						cards.remove (removeIdx);
					}
				}

				return true;
			}
		}
		return false;
	}

	private abstract static class PokerHandFinder {
		public abstract int getPriority ();
		public abstract boolean find (PokerHand p);
	}
}



























final class CardReader {

	private CardReader () {}

	public static Card readCard (String cardString) {
		assert (cardString.length () == 2);

		char faceValChar = cardString.charAt (0);
		char suitChar = cardString.charAt (1);

		Card.FaceValue faceVal;
		Card.Suit suit;

		switch (faceValChar) {
			case 'A' :
				faceVal = Card.FaceValue.LOW_ACE;
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
				faceVal = Card.FaceValue.HIGH_ACE;
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






















class Main {

	public static List<Card> readHand (Iterator<String> input) {
		ArrayList<Card> result = new ArrayList<Card> ();
		for (int i = 0; i < 5; ++i) {
			assert (input.hasNext ());
			String cardString = input.next ();
			Card card = CardReader.readCard (cardString);
			result.add (card);
		}
		return result;
	}

	public static Stack<Card> readDeck (Iterator<String> input) {
		Stack<Card> result = new Stack<Card> ();
		Stack<Card> in = new Stack<Card> ();
		for (int i = 0; i < 5; ++i) {
			assert (input.hasNext ());
			String cardString = input.next ();
			Card card = CardReader.readCard (cardString);
			in.push (card);
		}

		while (!in.empty ())
			result.push (in.pop ());

		return result;
	}

	public static String solve (List<Card> hand, Stack<Card> deck) {
		List<Card> usedDeckCards = new ArrayList<Card> ();
		PokerHand best = null;

		while (true) {
			
			PokerHand result = solveHelper (usedDeckCards, hand);
			if (best == null || result.compareTo (best) > 0)
				best = result;

			if (deck.empty ())
				break;

			usedDeckCards.add (deck.pop ());
		}

		assert (best != null);
		return best.toString ();
	}

	public static PokerHand solveHelper (List<Card> usedCards, List<Card> hand) {
		if (usedCards.size () == 5)
			return new PokerHand (usedCards);

		PokerHand best = null;

		for (Card card : hand) {
			List<Card> tempUsedCards = new ArrayList<Card> (usedCards);
			List<Card> tempHand = new ArrayList<Card> (hand);

			tempHand.remove (card);
			tempUsedCards.add (card);

			PokerHand result = solveHelper (tempUsedCards, tempHand);
			if (best == null || result.compareTo (best) > 0)
				best = result;
		}

		assert (best != null);
		return best;
	}

	public static void printSolution (List<Card> hand, Stack<Card> deck, String result) {
		System.out.print ("Hand: ");
		for (Card card : hand) {
			System.out.print (card + " ");
		}

		System.out.print ("Deck: ");
		while (!deck.empty ()) {
			System.out.print (deck.pop () + " ");
		}

		System.out.print ("Best hand: ");
		System.out.print (result);

		System.out.println ();
	}

	public static void main (String[] args) {

		Scanner input = new Scanner (System.in);

		while (input.hasNext ()) {

			List<Card> hand = readHand (input);
			Stack<Card> deck = readDeck (input);

			@SuppressWarnings (value="unchecked")
			Stack<Card> deckClone = (Stack<Card>)deck.clone ();

			String result = solve (hand, deckClone);

			printSolution (hand, deck, result);

		}

		return;
	}
}