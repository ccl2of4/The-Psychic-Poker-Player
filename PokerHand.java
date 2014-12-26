import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Arrays;

public class PokerHand implements Comparable<PokerHand>, Iterable<Card> {

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

	public Iterator<Card> iterator () {
		return cards.iterator ();
	}

	public void swap (Card oldCard, Card newCard) {
		this.cards.remove (oldCard);
		this.cards.add (newCard);
		compute ();
	}

	@Override
	public String toString () {
		return key;
	}

	@Override
	public int compareTo (PokerHand other) {
		PokerHandFinder thisFinder = hands.get (this.key);
		PokerHandFinder otherFinder = hands.get (other.key);

		return otherFinder.getPriority () - thisFinder.getPriority ();
	}

	private void compute () {
		for (String key : hands.keySet ()) {
			PokerHandFinder p = hands.get (key);
			if (p.find (this)) {
				this.key = key;
				break;
			}
		}
		assert (this.key != null);
	}

	private boolean findStraightFlush () {
		return false;
	}

	private boolean findFourOfAKind () {
		return false;
	}

	private boolean findFullHouse () {
		return false;
	}

	private boolean findFlush () {
		Card.Suit suit = Card.Suit.NONE;
		for (Card card : cards) {
			if (suit == Card.Suit.NONE)
				suit = card.getSuit ();
			else if (suit != card.getSuit ())
				return false;
		}
		return true;
	}

	private boolean findStraight () {
		Card[] array = cards.toArray (new Card[0]);
		Arrays.sort (array);

		assert (array.length > 0);

		Card lastCard = array[0];
		for (int i = 1; i < array.length; ++i) {
			Card currentCard = array[i];

			int lastFaceVal = lastCard.getFaceValue().getVal ();
			int currentFaceVal = currentCard.getFaceValue().getVal ();
			if (i != 1 && lastCard.getFaceValue () == Card.FaceValue.ACE)
				lastFaceVal = Card.FaceValue.KING.getVal () + 1;

			if (currentFaceVal != (lastFaceVal + 1) )
				return false;

			lastCard = currentCard;
		}
		return true;
	}

	private boolean findThreeOfAKind () {
		List<Card> cardsList = Arrays.asList(cards.toArray (new Card[0]));
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

	private boolean findTwoPairs () {
		List<Card> cardsList = Arrays.asList(cards.toArray (new Card[0]));
		List<Card> matchedCards = new ArrayList<Card> ();

		boolean foundFirst = false;
		boolean foundSecond = false;

		firstLoop: for (int i = 0; i < cardsList.size (); ++i) {
			Card first = cardsList.get (i);
			for (int j = i + 1; j < cardsList.size (); ++j) {
				Card second = cardsList.get (j);

				if (first.getFaceValue () == second.getFaceValue ())  {
					matchedCards.add (first);
					matchedCards.add (second);
					foundFirst = true;
					break firstLoop;
				}
			}
		}
		if (!foundFirst) return false;

		secondLoop: for (int i = 0; i < cardsList.size (); ++i) {
			Card first = cardsList.get (i);
			if (matchedCards.contains (first)) continue;

			for (int j = i + 1; j < cardsList.size (); ++j) {
				Card second = cardsList.get (j);
				if (matchedCards.contains (second)) continue;

				if (first.getFaceValue () == second.getFaceValue ())  {
					foundSecond = true;
					break secondLoop;
				}
			}
		}
		
		return foundSecond;
		
	}

	private boolean findOnePair () {

		Card[] array = cards.toArray (new Card[0]);

		for (int i = 0; i < array.length; ++i) {
			Card first = array[i];
			for (int j = i + 1; j < array.length; ++j) {
				Card second = array[j];

				if (first.getFaceValue () == second.getFaceValue ())
					return true;
			}
		}
		return false;
	}

	private boolean findHighestCard () {
		return true;
	}

	private abstract static class PokerHandFinder {
		public abstract int getPriority ();
		public abstract boolean find (PokerHand p);
	}
}