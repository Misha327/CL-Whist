public class BasicPlayer implements Player {

    public static Card.Suit trumps;
    private int id;
    private Hand hand = new Hand();
    private Strategy st;

    BasicPlayer(int id) {
        this.id = id;
    }

    @Override
    public void dealCard(Card c) {
        hand.add(c);
    }

    @Override
    public void setStrategy(Strategy s) throws NullPointerException{
        if (s == null) {
            throw new NullPointerException("Strategy should not be null");
        }
        st = s;
    }

    @Override
    public Card playCard(Trick t) throws NullPointerException, IllegalStateException {
        if(t == null) {
            throw new NullPointerException("Trick should not be null");
        }
        if(st == null) {
            throw new IllegalStateException("Strategy must be assigned");
        }

        Card temp = st.chooseCard(hand, t);
        hand.remove(temp);
        return temp;

    }

    @Override
    public void viewTrick(Trick t) throws NullPointerException {
        if(t == null) {
            throw new NullPointerException("Trick should not be null");
        }
        st.updateData(t);
    }

    @Override
    public void setTrumps(Card.Suit s) {
        trumps = s;
    }

    @Override
    public int getID() {
        return id;
    }

    public Hand getHand() {
        return hand;
    }

    public static void main(String[] args) {

    }

}
