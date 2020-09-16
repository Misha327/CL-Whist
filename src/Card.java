import java.io.Serializable;
import java.util.*;
import java.util.Comparator;

public class Card implements Serializable, Comparable<Card> {
    private static final long serialVersionUID = 100;
    private Rank rank;
    private Suit suit;


    //Access enum's in another class while being private?
    public enum Rank {
        TWO(2), THREE(3), FOUR(4),
        FIVE(5), SIX(6), SEVEN(7), EIGHT(8),
        NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);

        private final int value;

        Rank(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public Rank getNext() {
            return Rank.values()[(ordinal() + 1) % values().length];
        }
    }

    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES;

        //Fix usage of this
        public static Suit randomSuit() {
            Random rand = new Random();
            int index = rand.nextInt(4);
            return Suit.values()[index];
        }
    }

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public int compareTo(Card o) {
        char a = this.getSuit().name().charAt(0);
        char b = o.getSuit().name().charAt(0);
        if (this.getRank().getValue() > o.getRank().getValue()) {
            return 1;
        } else if (this.getRank().getValue() < o.getRank().getValue()) {
            return -1;
        } else if (this.getRank().getValue() == o.getRank().getValue()) {
            if (a > b) {
                return 1;
            } else if (a < b) {
                return -1;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return rank + " " + suit;
    }


    public static Card max(List<Card> cards) {
        Card max = null;
        //List<Card> list = Arrays.asList(cards);
        Iterator itr = cards.iterator();
        if (itr.hasNext()) {
            max = (Card) itr.next();
        }
        while (itr.hasNext()) {
            Card current = (Card) itr.next();
            if (current.compareTo(max) == 1) {
                max = current;
            }
        }
        return max;
    }

    static class CompareDescending implements Comparator<Card> {
        public int compare(Card a, Card b) {
            char A = a.getSuit().name().charAt(0);
            char B = a.getSuit().name().charAt(0);
            if (a.getRank().getValue() < b.getRank().getValue() || A < B) {
                return 1;
            } else if (a.getRank().getValue() > b.getRank().getValue() || A > B) {
                return -1;
            } else return 0;
        }
    }

    //Rewrite to use Rank exclusively
    static class CompareRank implements Comparator<Card> {
        public int compare(Card a, Card b) {
            return a.getRank().getValue() - b.getRank().getValue();
        }
    }

    public static ArrayList<Card> chooseGreater(List<Card> list, Comparator<Card> comp, Card a) {
        ArrayList<Card> arrayList = new ArrayList<>();
        for (Card card : list) {
            if (comp.compare(a, card) < 0) {
                arrayList.add(card);
            }
        }
        return arrayList;
    }

    public static void selectTest() {
        Card testCard = new Card(Rank.TEN, Suit.CLUBS);

        List<Card> cardsList = new ArrayList<>();
        cardsList.add(new Card(Rank.NINE, Suit.DIAMONDS));
        cardsList.add(new Card(Rank.NINE, Suit.SPADES));
        cardsList.add(new Card(Rank.TWO, Suit.CLUBS));
        cardsList.add(new Card(Rank.TWO, Suit.DIAMONDS));
        cardsList.add(new Card(Rank.SEVEN, Suit.CLUBS));
        cardsList.add(new Card(Rank.SEVEN, Suit.SPADES));
        cardsList.add(new Card(Rank.JACK, Suit.CLUBS));
        cardsList.add(new Card(Rank.JACK, Suit.HEARTS));
        cardsList.add(new Card(Rank.ACE, Suit.HEARTS));
        System.out.println("chooseGreater\nTest card: " + testCard);

        List<Card> list = chooseGreater(cardsList,
                (o1, o2) -> {
                    int result = o1.getRank().getValue() - o2.getRank().getValue();
                    if (result == 0) {
                        char a = o1.getSuit().name().charAt(0);
                        char b = o2.getSuit().name().charAt(0);
                        if (a > b) {
                            return 1;
                        } else if (a < b) {
                            return -1;
                        }
                    }
                    return result;
                }, testCard);
        System.out.println("Lambda: "+list);

        list = chooseGreater(cardsList, new CompareDescending(), testCard);
        System.out.println("CompareDescending: "+list);

        list = chooseGreater(cardsList, new CompareRank(), testCard);
        System.out.println("CompareRank: "+list);

    }

    static class AscendingOrdinal implements Comparator<Card> {
        public int compare(Card a, Card b) {
            return a.getRank().ordinal() - b.getRank().ordinal();
        }
    }

    static class DescendingOrdinal implements Comparator<Card> {

        @Override
        public int compare(Card a, Card b) {
            return b.getRank().ordinal() - a.getRank().ordinal();
        }
    }

    public static void main(String[] args) {

        Card two = new Card(Rank.TWO, Suit.HEARTS);
        Card five = new Card(Rank.FIVE, Suit.DIAMONDS);

        ArrayList<Card> array = new ArrayList<>();
        array.add(new Card(Rank.NINE, Suit.DIAMONDS));
        array.add(new Card(Rank.NINE, Suit.SPADES));
        array.add(new Card(Rank.TWO, Suit.DIAMONDS));
        array.add(new Card(Rank.TWO, Suit.CLUBS));
        array.add(new Card(Rank.JACK, Suit.CLUBS));
        array.add(new Card(Rank.KING, Suit.CLUBS));
        array.add(new Card(Rank.JACK, Suit.HEARTS));
        array.add(new Card(Rank.ACE, Suit.HEARTS));

        System.out.println("Unsorted list: " + array+"\n");
        System.out.println("Highest value card: " + max(array)+"\n");

        Collections.sort(array);
        System.out.println("Collection.sort : " + array+"\n");

        array.sort(new CompareDescending());
        System.out.println("CompareDescending: "+array+"\n");

        array.sort(new CompareRank());
        System.out.println("CompareRank: "+array+"\n");

        selectTest();
    }
}
