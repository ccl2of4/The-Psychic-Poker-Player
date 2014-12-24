import java.util.List;
import java.util.TreeMap;

public class PokerHand implements Comparable {

	private List<Card> cards;

	private static TreeMap<String, PokerHandFinder> hands;


	static {
		hands = new TreeMap<String, PokerHandFinder> ();

		hands.put ("straight-flush", new PokerHandFinder () {
			public boolean find () {
				return true;
		}});

		hands.put ("four-of-a-kind", new PokerHandFinder () {
			public boolean find () {
				return true;
		}});

		hands.put ("full-house", new PokerHandFinder () {
			public boolean find () {
				return true;
		}});

		hands.put ("flush", new PokerHandFinder () {
			public boolean find () {
				return true;
		}});

		hands.put ("straight", new PokerHandFinder () {
			public boolean find () {
				return true;
		}});

		hands.put ("three-of-a-kind", new PokerHandFinder () {
			public boolean find () {
				return true;
		}});

		hands.put ("two-pairs", new PokerHandFinder () {
			public boolean find () {
				return true;
		}});

		hands.put ("one-pair", new PokerHandFinder () {
			public boolean find () {
				return true;
		}});

		hands.put ("highest-card", new PokerHandFinder () {
			public boolean find () {
				return true;
		}});

	}

	public PokerHand (List<Card> cards) {
		this.cards = cards;
	}

	private void compute () {
		return;
	}

	@Override
	public String toString () {
		return "";
	}

	@Override
	public int compareTo (Object other) {
		if (other instanceof PokerHand) {
			PokerHand otherHand = (PokerHand)other;
			return 0;
		}
		return 0;
	}

	private static class PokerHandFinder {
		public boolean find () {
			return false;
		}
	}
}