import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BasicStrategy implements Strategy {


    @Override
    public Card chooseCard(Hand h, Trick t) {
        Card resultCard = null;

        int player = t.curPlayer();
        int partner = (player + 2) % 4;

        //If first player of the trick
        if (player == t.getLeadID()) {
            //Play the max card which sets the lead suit
            Collections.sort(h.cards, new Card.DescendingOrdinal());
            resultCard = h.cards.get(0);
        }
        //If partner is winning
        else if (t.findWinner() == partner) {
            //Play the lowest card of the lead suit
            if (h.hasSuit(t.getLeadSuit())) {
               resultCard = h.lowestCard(t.getLeadSuit());
            }
            //
            else {
                List<Card> lowestCards = new ArrayList<>();
                for (Card.Suit value : Card.Suit.values()) {
                    if (value != t.getLeadSuit() && h.hasSuit(value)) {
                        lowestCards.add(h.lowestCard(value));
                    }
                }
                Collections.sort(lowestCards, new Card.AscendingOrdinal());
                for (Card x : lowestCards) {
                    //Discard the lowest non-trump
                    if (x.getSuit() != t.getTrumps()) {
                        resultCard = x;
                        break;
                    }
                }
                //Discard the lowest card of any suit
                if (resultCard == null) resultCard = lowestCards.get(0);
            }
        }
        //Partner not the winner and has yet to play
        else if (partner != t.findWinner()){
            //If can follow suit and can beat the current highest card
            if (t.getCards()[t.findWinner()].getSuit() != t.getTrumps() || t.getLeadSuit() == t.getTrumps()) {

                //Play highest lead suit if possible
                if (h.hasSuit(t.getLeadSuit()) && h.highestCard(t.getLeadSuit()).getRank().ordinal() > t.getCards()[t.findWinner()].getRank().ordinal() ) {
                    resultCard = h.highestCard(t.getLeadSuit());
                }

                //Play the highest trump card
                else if (h.hasSuit(t.getTrumps())) {
                    resultCard = h.highestCard(t.getTrumps());
                }
                //Play lowest lead suit if cannot win
                else if (h.hasSuit(t.getLeadSuit())) {
                    resultCard = h.lowestCard(t.getLeadSuit());
                }
                //Play the lowest possible card
                else {
                    List<Card> lowestCards = new ArrayList<>();
                    for (Card.Suit value : Card.Suit.values()) {
                        if (value != t.getLeadSuit() && h.hasSuit(value)){
                            lowestCards.add(h.lowestCard(value));
                        }
                    }
                    Collections.sort(lowestCards, new Card.AscendingOrdinal());
                    for (Card x : lowestCards) {
                        if (x.getSuit() != t.getTrumps()) {
                            resultCard = x;
                            break;
                        }
                    }
                    if (resultCard == null) resultCard = lowestCards.get(0);
                }
            }
            //Winning card must be a trump
            else {

                //Otherwise play the lowest lead suit
                if (h.hasSuit(t.getLeadSuit())) {
                    resultCard = h.lowestCard(t.getLeadSuit());
                }
                //If have got a trump higher than winning trump
                else if (h.hasSuit(t.getTrumps()) && h.highestCard(t.getTrumps()).getRank().ordinal() > t.getCards()[t.findWinner()].getRank().ordinal()) {
                    resultCard = h.highestCard(t.getTrumps());
                }
                //Play the lowest possible card
                else {
                    List<Card> lowestCards = new ArrayList<>();
                    for (Card.Suit value : Card.Suit.values()) {
                        if (value != t.getLeadSuit() && h.hasSuit(value)){
                            lowestCards.add(h.lowestCard(value));
                        }
                    }
                    Collections.sort(lowestCards, new Card.AscendingOrdinal());
                    for (Card x : lowestCards) {
                        if (x.getSuit() != t.getTrumps()) {
                            resultCard = x;
                            break;
                        }
                    }
                    if (resultCard == null) resultCard = lowestCards.get(0);
                }
            }

        }

        return resultCard;
    }

    @Override
    public void updateData(Trick c) {

    }
}
