import java.util.List;
import java.util.Iterator;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;

public class Main {

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