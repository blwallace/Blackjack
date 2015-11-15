package blackjack;

/**
 * Created by brianwallace on 11/13/15.
 */

// this is the player class.
public class Player{

    // keeps track of the player variables
    private int mPlayerMoney;
    private Card[] mHand;
    private int mCardNum;
    private int mScore;
    private int mBetValue;

    // constructor.  The player cannot have more than 12 cards
    public Player(int playerMoney) {
        mPlayerMoney = playerMoney;
        mHand = new Card[12];
        mCardNum = 0;
    }

    //getters and setters below
    public int getPlayerMoney() {
        return mPlayerMoney;
    }

    public void setHand(Card card){
        System.out.println("Player Dealt: " + card.toString());
        mHand[mCardNum] = card;
        mCardNum++;
        setScore();
    }

    // function to set the score
    private void setScore(){
        int score = 0;
        int aceCount = 0;
        for(int i = 0; i < mHand.length; i++){
            if(mHand[i] != null){
                //ace logic. always add 11 first
                if(mHand[i].getValue()== 1)
                {
                    score += 11;
                    aceCount++;
                }
                else{
                    score += mHand[i].getValue();
                }
                // if player has > 21, then subtract out the ace avlue
                if(score > 21){
                    while(aceCount > 0 && score > 21){
                        score = score - 10;
                        aceCount--;
                    }
                }
            }
        }
        //set score
        mScore = score;
    }

    //returns players score
    public int getScore() {
        return mScore;
    }

    //sets players score
    public void resetScore(){
        mScore=0;
        mHand = new Card[12];
        mCardNum = 0;
    }


    // determines if a players busted
    public boolean busted(){
        if(mScore > 21){
            return true;
        }
        else{
            return false;
        }
    }
    // betting mechanics.  if the player bets more than his current total, he's putting it all in
    public void setBet(int bet){
        if(bet < mPlayerMoney){
            mBetValue = bet;
        }
        else{
            mBetValue = mPlayerMoney;
        }
        mPlayerMoney -= mBetValue;
        System.out.println("Player bet " + mBetValue);
    }

    public void winBet(){
        mPlayerMoney += 2 * mBetValue;
    }
    public void winBlack(){
        mPlayerMoney += 2 * mBetValue + (mBetValue / 2);
    }

//    public void loseBet(){
//        mPlayerMoney -= mBetValue;
//    }

    public Card[] getHand(){return mHand;}

    public Integer getBet(){return mBetValue;}

}
