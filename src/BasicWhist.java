import java.util.Scanner;
import java.lang.*;

/**
 * @author ajb
 */
public class BasicWhist {
    static final int NOS_PLAYERS = 4;
    static final int NOS_TRICKS = 13;
    static final int WINNING_POINTS = 7;
    static int team1Points = 0;
    static int team2Points = 0;
    static BasicPlayer[] players;

    private enum Gametype {
        AI, HUMAN
    }

    public BasicWhist(BasicPlayer[] pl) {
        players = pl;
    }

    public static void dealHands(Deck newDeck) {
        for (int i = 0; i < NOS_TRICKS * 4; i++) {
            players[i % NOS_PLAYERS].dealCard(newDeck.deal());
        }
    }

    public static Trick playTrick(Player firstPlayer) {
        Trick t = new Trick(firstPlayer.getID());
        int playerID = firstPlayer.getID();

        for (int i = 0; i < NOS_PLAYERS; i++) {
            int next = (playerID + i) % NOS_PLAYERS;
            t.setCard(players[next].playCard(t), players[next]);
        }
        return t;
    }

    public void playGame() {
        //Create a new deck
        int team1TricksWon = 0;
        int team2TricksWon = 0;
        Deck d = new Deck();
        dealHands(d);

        int firstPlayer = (int) (NOS_PLAYERS * Math.random());
        Card.Suit trumps = Card.Suit.randomSuit();
        Trick.setTrumps(trumps);

        for (int i = 0; i < NOS_PLAYERS; i++) {
            players[i].setTrumps(trumps);
            players[i].setStrategy(new BasicStrategy());
        }

        for (int i = 0; i < NOS_TRICKS; i++) {
            Trick t = playTrick(players[firstPlayer]);
            System.out.println("Trick:\n" + t);
            firstPlayer = t.findWinner();

            if (firstPlayer == 0 || firstPlayer == 2) {
                team1TricksWon++;

            } else {
                team2TricksWon++;
            }
            System.out.println("Team 1 total: " + team1Points);
            System.out.println("Team 2 total: " + team2Points + "\n");
            System.out.println("Team 1 tricks: " + team1TricksWon);
            System.out.println("Team 2 tricks: " + team2TricksWon);

        }

        if (team1TricksWon > team2TricksWon) {
            team1Points += Math.max(team1TricksWon - 6, 0);
        } else {
            team2Points += Math.max(team2TricksWon - 6, 0);
        }

    }

    public static void humanGame() {
        //Create a new deck
        int team1TricksWon = 0;
        int team2TricksWon = 0;

        Deck d = new Deck();
        dealHands(d);

        int firstPlayer = (int) (NOS_PLAYERS * Math.random());
        Card.Suit trumps = Card.Suit.randomSuit();
        Trick.setTrumps(trumps);

        for (int i = 0; i < NOS_PLAYERS; i++) {
            players[i].setTrumps(trumps);
            players[i].setStrategy(new BasicStrategy());
        }
        players[0].setStrategy(new HumanStrategy());

        for (int i = 0; i < NOS_TRICKS; i++) {
            Trick t = playTrick(players[firstPlayer]);
            System.out.println("Trick =" + t);
            int winner = t.findWinner();
            if (winner == 0 || winner == 2) {
                team1TricksWon++;
            } else team2TricksWon++;

            System.out.println("Team 1 total: " + team1Points);
            System.out.println("Team 2 total: " + team2Points + "\n");
            System.out.println("Team 1 tricks: " + team1TricksWon);
            System.out.println("Team 2 tricks: " + team2TricksWon);
        }
        if (team1TricksWon > team2TricksWon) {
            team1Points += Math.max(team1TricksWon - 6, 0);
        } else {
            team2Points += Math.max(team2TricksWon - 6, 0);
        }
    }

    public void playMatch(Gametype mode) {
        team1Points = 0;
        team2Points = 0;
        while (team1Points < WINNING_POINTS && team2Points < WINNING_POINTS) {
            switch (mode) {
                default:
                case AI:
                    playGame();
                    break;
                case HUMAN:
                    humanGame();
                    break;
            }
        }
        if (team1Points >= WINNING_POINTS) {
            System.out.println("Trick winner is team 1 with: " + (team1Points));
            System.out.println("Another game? y/n: ");
            Scanner scan = new Scanner(System.in);
            String qn = scan.next();
            if (qn.equals("y")) {
                playTestGame(mode);
            }

        } else if (team2Points >= WINNING_POINTS) {
            System.out.println("Trick winner is team 2 with: " + (team2Points));
            System.out.println("Another game? y/n: ");
            Scanner scan = new Scanner(System.in);
            String qn = scan.next();
            if (qn.equals("y")) {
                playTestGame(mode);
            }
        }
    }

    public static void playTestGame(Gametype mode) {
        BasicPlayer[] p = new BasicPlayer[NOS_PLAYERS];
        for (int i = 0; i < p.length; i++) {
            p[i] = new BasicPlayer(i);//CREATE YOUR PLAYERS HERE
        }
        BasicWhist bw = new BasicWhist(p);
        bw.playMatch(mode); //Just plays a single match
    }

    public static void main(String[] args) {
        //Gametype AI or Human calls a different game method
//        playTestGame(Gametype.AI);
        playTestGame(Gametype.HUMAN);
    }
}
