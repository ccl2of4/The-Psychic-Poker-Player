import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;

public class Main {

	public static void main (String[] args) {

		Scanner in = new Scanner (System.in);

		while (true) {

			ArrayList<Card> hand = new ArrayList<Card> ();
			Stack<Card> deck = new Stack<Card> ();

			for (int i = 0; i < 5; ++i) {
				assert (in.hasNext ());
				String cardString = in.next ();
				Card card = CardReader.readCard (cardString);
				hand.add (card);
			}

			for (int i = 0; i < 5; ++i) {
				assert (in.hasNext ());
				String cardString = in.next ();
				Card card = CardReader.readCard (cardString);
				deck.push (card);
			}

			System.out.print ("Hand: ");
			for (Card card : hand) {
				System.out.print (card + " ");
			}

			System.out.print ("Deck: ");
			for (Card card : deck) {
				System.out.print (card + " ");
			}

			System.out.println ("Best hand: ");

			System.out.println ();

			if (!in.hasNext ()) break;
		}

		return;
	}
}