import java.util.*;

class Hand implements Iterable<Card> {

    public static long serialVersionUID = 300;

    //cards made public to allow use in BasicStrategy
    public List<Card> cards = new ArrayList<>();
    public List<Card> orginalOrder = new ArrayList<>();

    private int[] cardsPerSuit = new int[4];
    private int[] totalValue;

    public Hand() {

    }

    public Hand(Card cards[]) {
        for (Card c : cards) add(c);
    }

    public Hand(Hand hand) {
        add(hand.cards);
    }

    public void calculateValue() {
        int aces = this.countRank(Card.Rank.ACE);
        totalValue = new int[aces + 1];
        int values = 0;
        for (Card c : cards) {
            if (c.getRank() != Card.Rank.ACE) {
                values += c.getRank().getValue();
            }
        }
        for (int i = 0; i < totalValue.length; i++) {
            totalValue[i] = (i * 10) + aces + values;
        }

    }

    //Add low value to ACE
    public void add(Card c) {
        cards.add(c);
        orginalOrder.add(c);
        cardsPerSuit[c.getSuit().ordinal()]++;
        calculateValue();

    }

    public void add(Collection<Card> cards) {
        for (Card c : cards) add(c);
    }

    public void add(Hand hand) {
        add(hand.cards);
    }

    //Doesn't take in ACE low
    public boolean remove(Card c) {
        if (cards.remove(c)) {
            orginalOrder.remove(c);
            cardsPerSuit[c.getSuit().ordinal()]--;
            calculateValue();
            return true;
        }
        return false;
    }

    public boolean remove(Collection<Card> cards) {
        boolean outcome = true;
        for (Card c : cards) {
            boolean result = remove(c);
            if (outcome) outcome = result;
        }
        return outcome;
    }

    public Card remove(int position) {
        Card c = cards.get(position);
        remove(c);
        return c;
    }

    @Override
    public Iterator<Card> iterator() {
        return new HandIterator<>();
    }


    //todo:Has to be able to traverse in order they were added even after sort:: add to another array
    private class HandIterator<Card> implements Iterator<Card> {
        int pos = 0;

        @Override
        public boolean hasNext() {
            if (pos < cards.size()) {
                return true;
            }
            return false;
        }

        @Override
        public Card next() {
            return (Card) cards.get(pos++);
        }
    }

    public void sort() {
        Collections.sort(cards);
    }

    public void sortByRank() {
        Collections.sort(cards, new Card.CompareRank());
    }

    public int countSuit(Card.Suit suit) {
        int count = 0;
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getSuit() == suit) {
                count++;
            }
        }
        return count;
    }

    public int countRank(Card.Rank rank) {
        int count = 0;
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getRank() == rank) {
                count++;
            }
        }
        return count;
    }

    public boolean hasSuit(Card.Suit suit) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getSuit() == suit) {
                return true;
            }
        }
        return false;
    }

    //todo: It's the same as the card to string
    @Override
    public String toString() {
        String hand = "";
        for (int i = 0; i < cards.size(); i++) {
            hand += "("+(i+1)+")"+cards.get(i).toString() + ", ";
        }
        return hand;
    }

    public Card highestCard(Card.Suit suit) {
        Collections.sort(cards, new Card.DescendingOrdinal());
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getSuit() == suit) {
                return cards.get(i);
            }
        }
        return null;
    }

    public Card lowestCard(Card.Suit suit) {
        Collections.sort(cards, new Card.AscendingOrdinal());
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getSuit() == suit) {
                return cards.get(i);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Deck deck = new Deck();
        Hand hand = new Hand();
        for (int i = 0; i < 13; i++) {
            hand.add(deck.deal());
        }
        System.out.println("Added 13 cards from a deck:");
        System.out.println(hand);

        System.out.print("Total values: ");
        for (int i = 0; i < hand.totalValue.length; i++) {
            System.out.print(hand.totalValue[i] + ",");

        }

        System.out.println("\n\nSorted by using CompareRank: ");
        hand.sortByRank();
        System.out.println(hand);

        System.out.println("\nSorted by using Card compareTo: ");
        hand.sort();
        System.out.println(hand);

        System.out.println("\nHas DIAMONDS: " + hand.hasSuit(Card.Suit.DIAMONDS));
        System.out.println("\nCount DIAMONDS: " + hand.countSuit(Card.Suit.DIAMONDS));
        System.out.println("\nCount FIVE: " + hand.countRank(Card.Rank.FIVE));

        hand.remove(2);
        System.out.println("\nRemoved the card with index 2 in hand: ");
        System.out.println(hand);


        Iterator<Card> it = hand.orginalOrder.iterator();

        while(it.hasNext()) {
            System.out.print(it.next() + ", ");
        }





//        for (Card card: hand) {
//            System.out.println(card);
//        }


//
//        System.out.println(hand.cards.size());
//
//        //System.out.println(hand.hasSuit(Card.Suit.DIAMONDS));
//        hand.sort();
//
//        System.out.println(hand);
//        //System.out.println(deck.size());
    }
}
