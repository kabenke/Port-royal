package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * test  class for move.
 */
public class MoveTest {

    private Card card;
    private Card cardToSet;
    private  boolean playerIsActive;
    private Player player;
    private Player playerToSet;
    private Move move;


    /**
     * initialize the test environment
     */
    @Before
    public void setUp(){
        player = new Player("Hans", PlayerType.HUMAN);
        playerToSet = new Player("Sitter", PlayerType.HUMAN);
        cardToSet = new Person(2, 0, Ability.TRADER, 0, Color.UNDEFINED);
        move = new Move(player, card, true, new Match());
    }

    /**
     * test the getter ans setter of Card-Object
     */
    @Test
    public void testGetSetCard(){
        move.setCard(cardToSet);
        assertEquals(cardToSet,move.getCard());
    }

    /**
     * test the getter ans setter of isActive
     */
    @Test
    public void testGetSetIsActive(){
        move.setPlayerIsActive(true);
        Assert.assertTrue(move.getPlayerIsActive());
        move.setPlayerIsActive(false);
        Assert.assertFalse(move.getPlayerIsActive());
    }

    /**
     * test the getter and setter of Player
     */
    @Test
    public void testGetSetPlayer(){
        move.setPlayer(playerToSet);
        assertEquals(playerToSet,move.getPlayer());
    }

    /**
     * test the getter and setter of matchBeforeMove
     */
    @Test
    public void testGetSetMatchBeforeMove() {
        Match localMatch = new Match();
        move.setMatchBeforeMove(localMatch);
        assertEquals(localMatch, move.getMatchBeforeMove());
    }

}
