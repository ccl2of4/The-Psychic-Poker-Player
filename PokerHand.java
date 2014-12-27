import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.LinkedHashMap;
import java.util.Arrays;
import java.util.Stack;

public final class PokerHand implements Comparable<PokerHand>, Iterable<Card> {

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
		return false;
	}

	private final boolean findFourOfAKind () {
		List<Card> cardsList = new ArrayList<Card> (cards);
		return findMatches (cardsList, 4, false);
	}

	private final boolean findFullHouse () {
		List<Card> cardsList = new ArrayList<Card> (cards);
		return findMatches (cardsList, 2, true) && findMatches (cardsList, 3, false);
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

		lastCard = array[0];
		for (int i = 1; i < array.length; ++i) {
			Card currentCard = array[i];

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