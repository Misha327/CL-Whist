import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanStrategy implements Strategy {


    @Override
    public Card chooseCard(Hand h, Trick t) {
        Card resultCard = null;
        int player = 0;
        int index = 0;
        boolean validCard = false;

        if (player == t.curPlayer()) {
            while (!validCard) {
                if (player == t.getLeadID()) {
                    System.out.println("You're leading");
                }
                System.out.println(t);
                //Sort for convenience
                Collections.sort(h.cards, new Card.AscendingOrdinal());
                System.out.println("\n" + h);
                System.out.println("Choose a card 1-" + h.cards.size());

                Scanner sc = new Scanner(System.in);
                try {
                    index = (sc.nextInt() - 1);
                    if (0 <= index && index < h.cards.size()) {
                        validCard = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Only numbers between 1-" + h.cards.size());
                    continue;
                }
                resultCard = h.cards.get(index);
                if (h.hasSuit(t.getLeadSuit()) && resultCard.getSuit() != t.getLeadSuit()) {
                    validCard = false;
                    System.out.println("YOU MUST PLAY THE LEAD SUIT WHEN YOU HAVE IT!");
                }
            }
            h.cards.remove(index);
        }
        return resultCard;
    }

    @Override
    public void updateData(Trick c) {

    }
}
