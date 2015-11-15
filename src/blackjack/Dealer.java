package blackjack;

/**
 * Created by brianwallace on 11/13/15.
 */

// this is the dealer and the
public class Dealer{

    //global methods for the dealer.  we don't need to keep track of his money
    private Card[] mHand;
    private int mCardNum;
    private int mScore;

    public Dealer() {
        mHand = new Card[12];
        mCardNum = 0;
    }
    // adds a card to the hand
    public void setHand(Card card){
        mHand[mCardNum] = card;
        mCardNum++;
        setScore();

    }

    // function to set the score
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

    //lists all dealer cards
    private void getCards(){
        for(int i = 0; i < mHand.length; i++){
            //add if the slot isnt null
            if(mHand[i] != null)
            {
                System.out.println(mHand[i]);
            }
        }
    }


    // returns score
    public int getScore() {
        return mScore;
    }
    //sets players score
    public void resetScore(){
        mScore=0;
        mHand = new Card[12];
        mCardNum = 0;
    }

    //shows card
    public Card showCard(int i)
    {
        return mHand[i];
    }

    public Card[] getHand(){
        return mHand;
    }

    // dealer plays hand
    public void playHand(Deck deck){
        while(playNext())
        {
            setHand(deck.drawCard());
        }
    }


    // determiens if hte dealer should play the next hand
    public boolean playNext()
    {
        if(this.getScore() <= 17)
        {
            return true;
        }
        else
        {
            return false;
        }
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

}
