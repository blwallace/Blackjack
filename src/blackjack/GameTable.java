package blackjack;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by brianwallace on 11/13/15.
 */

// this is the heart of the game.  it contains the ruls of blackjack and sits the dealer and player
// its also responsible for displaying images.  next time i'd break this class into more classes
// for ease of reading and debugging
public class GameTable extends JPanel{

    // these are the jframe stuff
    private JPanel mPanel1;
    private JPanel mScorePanel;
    private JPanel mDealerPanel;
    private JPanel mPlayerPanel;
    private JPanel mActionPanel;
    private JLabel mScorePlayer;
    private JLabel mScoreDealer;
    private JLabel mDealerPoints;
    private JPanel mDealerHand;
    private JLabel mPlayerPoints;
    private JPanel mPlayerHand;
    private JButton mActionBet;
    private JButton mActionHit;
    private JButton mActionHold;
    private JLabel mBetLabel;
    private JTextField mBetField;
    private JLabel mCardDealer1;
    private JLabel mCardDealer2;
    private JLabel mCardDealer3;
    private JLabel mCardDealer4;
    private JLabel mCardDealer5;
    private JLabel mCardDealer6;
    private JLabel mCardDealer7;
    private JLabel mCardDealer8;
    private JLabel mCardDealer9;
    private JLabel mCardDealer10;
    private JLabel mCardPlayer1;
    private JLabel mCardPlayer2;
    private JLabel mCardPlayer3;
    private JLabel mCardPlayer4;
    private JLabel mCardPlayer5;
    private JLabel mCardPlayer6;
    private JLabel mCardPlayer7;
    private JLabel mCardPlayer8;
    private JLabel mCardPlayer9;
    private JLabel mCardPlayer10;
    private JLabel[] mCardPlayer;
    private JLabel[] mCardDealer;
    private Player mPlayer;
    private Dealer mDealer;
    private Deck mDeck;
    private boolean mHitTurn;


    public GameTable(Player player, Dealer dealer, Deck deck) {

        //assigns the player, dealer and deck to the board
        mPlayer = player;
        mDealer = dealer;
        mDeck = deck;
        // prevents the user from hitting/holdiing right away
        mHitTurn = false;

        //updates UI componetns and starts game
        updatePlayer();
        mDealerPoints.setText("Dealer Hand");
        mScoreDealer.setText("                  Enter bet to begin");
        updateButtons();;
        packageCardHand();
        blankCardSetup();

    }


    public void dealHand(){
        //deals hands to players
        mPlayer.setHand(mDeck.drawCard());
        mPlayer.setHand(mDeck.drawCard());
        mDealer.setHand(mDeck.drawCard());
        mDealer.setHand(mDeck.drawCard());
        System.out.println("Player Score: " + mPlayer.getScore());
        System.out.println("Dealer Shows: " + mDealer.getHand()[1]);
        mScoreDealer.setText("");
        refreshTableImages();
    }

    // this goal was that startgame would have more methods.  however, it didn't end up that way =/
    public void startGame(){
        dealHand();
    }
    // function to update dealer hand
    public void updateDealer() {
        mDealerPoints.setText("Dealer Shows: " + mDealer.getHand()[1]);
    }

    public void updateDealerAll(){
        mDealerPoints.setText("Dealer Hand: " + mDealer.getScore());
    }

    // function to update Player hand
    public void updatePlayer() {
        mScorePlayer.setText("Player: $" + mPlayer.getPlayerMoney());
        mPlayerPoints.setText("Player Hand: " + mPlayer.getScore());
    }

    public JPanel getPanel1() {
        return mPanel1;
    }

    //determines who wins the game and allocates resources.  Just a lot of if/then statements
    public void gameWinner(){

        // shows all cards
        refreshTableImages();
        showDealerHand();
        updateDealerAll();

        if(mDealer.busted() && mPlayer.busted()){
            System.out.println("Tie Game");
            mScoreDealer.setText("Tie Game");
        } else if (mDealer.getScore() == mPlayer.getScore()) {
            System.out.println("Tie Game");
            mScoreDealer.setText("Tie Game");
        } else if (blackHand(mPlayer.getHand())) {
            mScoreDealer.setText("Player Wins. Blackjack");
            mPlayer.winBet();
        } else if (mDealer.busted()) {
            System.out.println("Player Wins");
            mScoreDealer.setText("Player Wins. Dealer Busted");
            mPlayer.winBet();
        } else if (mPlayer.busted()) {
            System.out.println("Dealer Wins");
            mScoreDealer.setText("Dealer Wins. Player Busted");
//            mPlayer.loseBet();
        } else if (mDealer.getScore() > mPlayer.getScore()) {
            System.out.println("Dealer Wins");
            mScoreDealer.setText("Dealer Wins");
//            mPlayer.loseBet();
        } else {
            System.out.println("Player Wins");
            mScoreDealer.setText("Player Wins");
            mPlayer.winBet();
        }
        System.out.println(mPlayer.getPlayerMoney());
        updatePlayer();
        System.out.println("");
        clearTable();
        mHitTurn = false;
    }

    //reset the table
    public void clearTable(){
        mPlayer.resetScore();
        mDealer.resetScore();
        mDeck = new Deck();
    }


    // hit button
    class HitButton implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            // we can only hit if we're allowed to hit
            if(mHitTurn){
                // draw cards and sets hand
                Card car = mDeck.drawCard();
                mPlayer.setHand(car);
                // updates images
                refreshTableImages();
                updatePlayer();
                System.out.println("Player Score: " + mPlayer.getScore());
                if (mPlayer.busted()) {
                    System.out.println("Player busted");
                    gameWinner();
                }
            }
            }
    }

    // hold button
    class HoldButton implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if(mHitTurn){
                mDealer.playHand(mDeck);
                gameWinner();
                refreshTableImages();
            }
        }
    }

    class BetButton implements ActionListener{
        public void actionPerformed(ActionEvent event){
            if(!mHitTurn){
                //sets bet
                mPlayer.setBet(Integer.parseInt(mBetField.getText()));
                blankCardSetup();
                startGame();
                refreshTableImages();
                updatePlayer();
                updateDealer();
                mScoreDealer.setText("");
                mBetField.setText(mPlayer.getBet().toString());
                mHitTurn = true;

                //if there is a blackjack we end the game
                if(blackJackEndGame()){
                    gameWinner();
                }
            }
        }
    }

    // ui stuff.  takes no inputs, just updates the gameboard to display cards
    public void refreshTableImages(){
        Card[] playerHand = mPlayer.getHand();
        Card[] dealerHand = mDealer.getHand();

        for(int i = 0; i < 10; i++){
            if(playerHand[i] != null){
                mCardPlayer[i].setIcon(getCardImage(playerHand[i]));
            }
        }

        for(int i = 1; i < 10; i++){
            if(dealerHand[i] != null){
                mCardDealer[i].setIcon(getCardImage(dealerHand[i]));
            }
        }
    }

    public void showDealerHand(){
        Card[] dealerHand = mDealer.getHand();
        for(int i = 0; i < 10; i++){
            if(dealerHand[i] != null){
                mCardDealer[i].setIcon(getCardImage(dealerHand[i]));
            }
        }
    }

    //ui stuff. enter a card and this returns an image that you can use
    public Icon getCardImage(Card card){
        //strings together file location from card
        String fileLocation = ("src/img/" + card.getValue() + card.getSuit() + ".png");
        BufferedImage myPicture = null;
        Image cardResized = null;
        try {
            myPicture = ImageIO.read(new File(fileLocation));
            cardResized =  myPicture.getScaledInstance(75,100,25);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageIcon icon = new ImageIcon(cardResized);
        return icon;
    }

    // ui stuff. this creates blank card images
    public void blankCardSetup(){
        BufferedImage myPicture = null;
        Image cardResized = null;
        try {
            myPicture = ImageIO.read(new File("src/img/0.png"));
            cardResized =  myPicture.getScaledInstance(75,100,25);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(cardResized);

        mCardDealer1.setIcon(icon);
        mCardDealer2.setIcon(icon);
        mCardDealer3.setIcon(icon);
        mCardDealer4.setIcon(icon);
        mCardDealer5.setIcon(icon);
        mCardDealer6.setIcon(icon);
        mCardDealer7.setIcon(icon);
        mCardDealer8.setIcon(icon);
        mCardDealer9.setIcon(icon);
        mCardDealer10.setIcon(icon);
        mCardPlayer1.setIcon(icon);
        mCardPlayer2.setIcon(icon);
        mCardPlayer3.setIcon(icon);
        mCardPlayer4.setIcon(icon);
        mCardPlayer5.setIcon(icon);
        mCardPlayer6.setIcon(icon);
        mCardPlayer7.setIcon(icon);
        mCardPlayer8.setIcon(icon);
        mCardPlayer9.setIcon(icon);
        mCardPlayer10.setIcon(icon);
    }

    //this just puts all the images in an array for hte player and dealer
    public void packageCardHand(){
        mCardDealer = new JLabel[10];
        mCardPlayer = new JLabel[10];
        mCardDealer[0] = mCardDealer1;
        mCardDealer[1] = mCardDealer2;
        mCardDealer[2] = mCardDealer3;
        mCardDealer[3] = mCardDealer4;
        mCardDealer[4] = mCardDealer5;
        mCardDealer[5] = mCardDealer6;
        mCardDealer[6] = mCardDealer7;
        mCardDealer[7] = mCardDealer8;
        mCardDealer[8] = mCardDealer9;
        mCardDealer[9] = mCardDealer10;
        mCardPlayer[0] = mCardPlayer1;
        mCardPlayer[1] = mCardPlayer2;
        mCardPlayer[2] = mCardPlayer3;
        mCardPlayer[3] = mCardPlayer4;
        mCardPlayer[4] = mCardPlayer5;
        mCardPlayer[5] = mCardPlayer6;
        mCardPlayer[6] = mCardPlayer7;
        mCardPlayer[7] = mCardPlayer8;
        mCardPlayer[8] = mCardPlayer9;
        mCardPlayer[9] = mCardPlayer10;
    }

    public void updateButtons(){
        //updates button names
        mActionBet.setText("Bet");
        mActionHit.setText("Hit");
        mActionHold.setText("Hold");
        mBetLabel.setText("Set Bet");
        //
        ActionListener listener1 = new HitButton();
        mActionHit.addActionListener(listener1);
        ActionListener listener2 = new HoldButton();
        mActionHold.addActionListener(listener2);
        ActionListener listener3 = new BetButton();
        mActionBet.addActionListener(listener3);
    }


    //determins if a hand has a blackjack
    public boolean blackHand(Card[] cards){
        Card first = cards[0];
        Card second = cards[1];

        if(first.getValue() == 1 && second.getValue() >= 10){
            return true;
        }
        else if(second.getValue() == 1 && first.getValue() >= 10){
            return true;
        }
        else{
            return false;
        }

    }
    // if the dealer or player has a blackjack to start off, you end the game
    public boolean blackJackEndGame(){
        if(blackHand(mDealer.getHand()) || blackHand(mPlayer.getHand())){
            return true;
        }
        else{
            return false;
        }
    }


}
