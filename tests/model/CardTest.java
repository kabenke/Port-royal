package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *  test the abstract class Card.
 */
public class CardTest {
    private int coins;
    private int coinsToSet;

    private boolean isFaceUp;
    private boolean isFaceUpToSet;

    private Ship card;
    private Ship clonedCard;

    /**
     * initialize for the card test
     */
    @Before
    public void setUp()
    {
        card = new Ship(2, 2, Color.BLUE);
    }


    /**
     * test if flip flips the boolean
     */
     @Test
    public void testFlipAndSetGetIsFaceUp(){
         card.setIsFaceUp(false);
         card.flip();
         Assert.assertTrue(card.getIsFaceUp());
         card.setIsFaceUp(true);
         card.flip();
         Assert.assertFalse(card.getIsFaceUp());
     }

    /**
     *  test if the coin set and get of coins
     */
    @Test
    public void testGetSetCoin(){
        card.setCoins(5);
        Assert.assertEquals(5, card.getCoins());
    }

}
