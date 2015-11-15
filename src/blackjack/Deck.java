package blackjack;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by brianwallace on 11/13/15.
 */

// I decided to not have a shuffle class.  Rather, the player just instantiates a new deck
public class Deck {

    private Card[] mSixDeck;
    private int mCardIndex;

    public Deck() {
        mCardIndex = 0;
        mSixDeck = createSixDeck();
    }

    // getter functions. this returns the entire deck
    public Card[] getSixDeck() {
        return mSixDeck;
    }
    // getter returns the card at index i
    public Card getSixDeckCard(int i){
        return mSixDeck[i];
    }

    // gets current index
    public int getCardIndex() {
        return mCardIndex;
    }

    // draws card. increases index
    public Card drawCard(){
        Card card = mSixDeck[mCardIndex];
        mCardIndex++;
        return card;
    }

    //create a single card deck unshuffled
    public Card[] createCardDeck(){

        Card[] deck = new Card[52];

        //create array for suits
        for(int i = 0; i < 52; i++)
        {
            int value = i % 13 + 1;
            int suit = i / 13;
            deck[i] = new Card(value, suit);
        }

        return deck;
    }
    //creates the six card deck shuffled
    public Card[] createSixDeck(){
        Card[] sixDeck = new Card[52 * 6];

        for(int i = 0; i < 6; i++)
        {
            Card[] deck = createCardDeck();
            for(int j = 0; j < 52; j++){
                sixDeck[(52 * i) + j] = deck[j];
            }
        }

        sixDeck = shuffleArray(sixDeck);
        return sixDeck;
    }

    // this randomly shuffles an array of ints. hacked from http://stackoverflow.com/questions/1519736/random-shuffling-of-an-array
    static Card[] shuffleArray(Card[] ar)
    {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Card a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }

        return ar;
    }
}
