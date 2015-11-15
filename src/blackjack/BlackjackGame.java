package blackjack;

import javax.swing.*;

/**
 * Created by brianwallace on 11/13/15.
 */
public class BlackjackGame {

    private Player mPlayer;
    private Dealer mDealer;
    private GameTable mGame;
    private Deck deck;

    public BlackjackGame() {

        // creaets a player starting with 1000 dollars
        mPlayer = new Player(1000);
        mDealer = new Dealer();
        //creates deck
        deck = new Deck();
        // loads everyone into the game
        GameTable mGame = new GameTable(mPlayer,mDealer,deck);

        // Assigns game to hte JFRAME
        JFrame frame = new JFrame("BlackJack");
        frame.setContentPane(mGame.getPanel1());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
