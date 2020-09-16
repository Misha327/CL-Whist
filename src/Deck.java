import java.io.*;
import java.util.Iterator;
import java.util.Random;

public class Deck implements Iterable<Card>, Serializable {
    private static final long serialVersionUID = 49;
    private Card[] cards = new Card[52];
    private int numCards = 0;
    private int num = 0;

    //Creates a Deck with 52 cards and shuffles them
    Deck() {
        for (int i = 0; Card.Suit.values().length > i; i++) {
            for (int j = 0; Card.Rank.values().length > j; j++) {
                cards[numCards] = new Card(Card.Rank.values()[j], Card.Suit.values()[i]);
                numCards++;
            }
        }
        for (int i = 0; cards.length > i; i++) {
            Random rand = new Random();
            int randPos = rand.nextInt(cards.length);
            Card temp = cards[i];
            cards[i] = cards[randPos];
            cards[randPos] = temp;
        }
    }

    public int size() {
        return this.numCards;
    }

    public final void newDeck() {
        this.numCards = 0;

        for (int i = 0; Card.Suit.values().length > i; i++) {
            for (int j = 0; Card.Rank.values().length > j; j++) {
                cards[numCards] = new Card(Card.Rank.values()[j], Card.Suit.values()[i]);
                numCards++;
            }
        }
        for (int i = 0; cards.length > i; i++) {
            Random rand = new Random();
            int randPos = rand.nextInt(cards.length);
            Card temp = cards[i];
            cards[i] = cards[randPos];
            cards[randPos] = temp;
        }
    }

    public Iterator<Card> iterator() {
        return new DeckIterator<>();
    }

    //todo:Use this in deal()
    private class DeckIterator<Card> implements Iterator<Card> {
        int pos = numCards - 1;

        @Override
        public boolean hasNext() {
            if (pos >= 0) {
                return true;
            }
            return false;
        }

        @Override
        public Card next() {
            return (Card) cards[pos--];
        }
    }

    public Card deal() {
        Card temp = cards[--numCards];
        cards[numCards] = null;
        return temp;
    }

    public Iterator<Card> spadeIterator() {
        return new SpadeIterator<>();
    }

    private class SpadeIterator<Card> implements Iterator<Card> {
        int pos = 0;

        @Override
        public boolean hasNext() {
            if (pos < numCards) {
                for (int i = pos; i < numCards; i++) {
                    if (cards[i].getSuit().name().equals("SPADES")) {
                        pos = i;
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public Card next() {
            return (Card) cards[pos++];
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        Iterator<Card> spadeIterator = spadeIterator();
        while (spadeIterator.hasNext()) {
            Card card = spadeIterator.next();
            out.writeObject(card);
            num++;
        }

    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        cards = new Card[52];
        try {
            for (int i = 0; i < cards.length; i++) {
                Card c = (Card) in.readObject();
                cards[i] = c;
                numCards++;
            }
        } catch (OptionalDataException e) {

        }
    }

    @Override
    public String toString() {
        String str = "";
        for (Card c : cards) {
            str += c + " ";
        }
        return str;
    }

    public static void main(String[] args) {

        Card two = new Card(Card.Rank.ACE, Card.Suit.CLUBS);
        Deck deck = new Deck();

//        System.out.println("Deck:");
//        System.out.println(deck);
//
//        System.out.println("New Deck:");
//        deck.newDeck();
//        System.out.println(deck);
//
//        System.out.println("DeckIterator: ");
//        Iterator<Card> di = deck.iterator();
//        while (di.hasNext()) {
//            System.out.println(di.next());
//        }
//
//        System.out.println("\nDeal: " + deck.deal() + "\n");
//
//        System.out.println("SpadeIterator: ");
//        Iterator<Card> spade = deck.spadeIterator();
//        while (spade.hasNext()) {
//            System.out.print(spade.next()+", ");
//        }
//        System.out.println();

        String filename = "spades.ser";
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(filename);
            out = new ObjectOutputStream(fos);
            out.writeObject(deck);

            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Card temp;
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream(filename);
            in = new ObjectInputStream(fis);
            deck = (Deck) in.readObject();

            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println(deck);



//        deck.newDeck();
//        for (Card x : deck) {
//            System.out.println(x);
//        }


//        System.out.println(deck.deal());

//        System.out.println(deck.size());
        //System.out.println(Arrays.toString(deck.newDeck()));


    }
}
