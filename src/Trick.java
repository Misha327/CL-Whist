
public class Trick {
    private static Card.Suit trumps;
    private Card.Suit lead;
    private Card[] cards = new Card[4];
    private int winner;
    private int leadID;

    //p is the lead player
    public Trick(int leadPlayerID) {
        leadID = leadPlayerID;
    }

    public static void setTrumps(Card.Suit s) {
        if(s == null) {
            throw new NullPointerException("Trump must not be null");
        }
        trumps = s;
    }

    public Card.Suit getLeadSuit() {
        return lead;
    }

    public void setCard(Card c, Player p) {
        if (lead == null) {
            lead = c.getSuit();
        }
        cards[p.getID()] = c;

    }

    public Card getCard(Player p) {
        return cards[p.getID()];
    }

    public int getLeadID() {
        return leadID;
    }

    public int curPlayer() {
        for (int i = 0; i < 4; i++) {
            int index = (leadID + i) % 4;
            if (cards[index] == null) {
                return index;
            }
        }
        return -1;
    }

    public Card.Suit getTrumps() {
        return trumps;
    }

    public Card[] getCards() {
        return cards;
    }

    public int findWinner() {
        Card card = null;
        int winner = -1;
        for (int i = 0; i < 4; i++) {
            int index = (leadID + i) % 4;
            Card temp = cards[index];

            if (temp == null){
                continue;
            }
            if (card == null) {
                card = temp;
                winner = index;

            } else if (card.getSuit() != trumps && temp.getSuit() == trumps) {
                card = temp;
                winner = index;

            } else if (card.getSuit() == temp.getSuit() && temp.getRank().ordinal() > card.getRank().ordinal()) {
                card = temp;
                winner = index;
            }
        }
        return this.winner = winner;

    }

    @Override
    public String toString() {
        boolean temp = false;
        String str = "Lead player: " + (getLeadID() + 1) + "\nTrump: " + getTrumps() + "\n";
        for (int j = 0; j < getCards().length; j++) {
            if (getCards()[j] != null) {
                str += "Player " + (j + 1) + " : " + getCards()[j] + "\n";
            }
            else {
                temp = true;
            }
        }
        if (!temp) {
            str += "Winner = " + (findWinner() + 1) + "\n";
        }
        return str;

    }


}
