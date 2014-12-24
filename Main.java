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
		for (int i = 0; i < 5; ++i) {
			assert (input.hasNext ());
			String cardString = input.next ();
			Card card = CardReader.readCard (cardString);
			result.push (card);
		}
		return result;
	}

	public static String solve (List<Card> hand, Stack<Card> deck) {
		String result = "null";

		for (Card card : hand) {
			
		}

		return result;
	}

	public static void printSolution (List<Card> hand, Stack<Card> deck, String result) {
		System.out.print ("Hand: ");
		for (Card card : hand) {
			System.out.print (card + " ");
		}

		System.out.print ("Deck: ");
		for (Card card : deck) {
			System.out.print (card + " ");
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

			String result = solve (hand, deck);

			printSolution (hand, deck, result);

		}

		return;
	}
}