import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Iterator;

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
		return false;
	}

	private boolean findStraight () {
		return false;
	}

	private boolean findThreeOfAKind () {
		return false;
	}

	private boolean findTwoPairs () {
		return false;
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