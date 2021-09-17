package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test class to test the methods of MatchHelp.
 */
public class MatchHelpTest {

    /**
     * matchHelp to help match
     */
    private MatchHelp matchHelp;

    /**
     * match to use the methods on
     */
    private Match matchEmptyPlayerList;

    /**
     * setup to initialize objects to test
     * @throws Exception if something goes wrong
     */
    @Before
    public void setUp() throws Exception {
        matchEmptyPlayerList = new Match();
        matchHelp = matchEmptyPlayerList.getMatchHelp();

        ArrayList<Card> drawPile = new ArrayList<>();
        drawPile.add(new Ship(1, 1, Color.YELLOW));
        drawPile.add(new Ship(2, 3, Color.BLACK));
        drawPile.add(new Ship(3, 4, Color.GREEN));

        matchEmptyPlayerList.setDrawPile(drawPile);
    }

    /**
     * testing whether the constructor initializes everything properly
     */
    @Test
    public void constructorTest() {
        Match localMatch = new Match();
        MatchHelp localHelp = new MatchHelp(localMatch);
        assertNotNull(localHelp.getMatch());
        assertEquals(localMatch, localHelp.getMatch());
    }

    /**
     * testing whether you can get an Active player from empty list
     */
    @Test (expected = IllegalArgumentException.class)
    public void nextActivePlayerEmptyList() {
        matchHelp.nextActivePlayer();
    }

    /**
     * testing to get the next player in a list of 2
     */
    @Test
    public void nextActivePlayerTest() {
        Player fillPlayer = new Player("Fillip", PlayerType.HUMAN);
        Player Tobi = new Player("Tobi", PlayerType.HUMAN);
        matchEmptyPlayerList.getMatchHelp().addPlayer(fillPlayer);
        matchEmptyPlayerList.getMatchHelp().addPlayer(Tobi);
        assertEquals(matchEmptyPlayerList.getActivePlayerInLastTurn(), fillPlayer);
        assertEquals(matchHelp.nextActivePlayer(), Tobi);
        assertEquals(matchEmptyPlayerList.getActivePlayerInLastTurn(), Tobi);
    }

    /**
     * testing to get the next player in a list of 1
     */
    @Test
    public void nextActivePlayerTestOnePlayerInList() {
        Player Tobi = new Player("Tobi", PlayerType.HUMAN);
        matchEmptyPlayerList.getMatchHelp().addPlayer(Tobi);
        assertEquals(matchEmptyPlayerList.getActivePlayerInLastTurn(), Tobi);
        assertEquals(matchHelp.nextActivePlayer(), Tobi);
        assertEquals(matchEmptyPlayerList.getActivePlayerInLastTurn(), Tobi);
    }

    /**
     * testing that you cant swap cards with the same index
     */
    @Test (expected = IllegalArgumentException.class)
    public void swapCardsSameIndexTest() {
        matchHelp.swapCards(1, 1);
    }

    /**
     * invalid index 1
     */
    @Test (expected = IllegalArgumentException.class)
    public void swapCardsInvalidIndex1Test() {
        matchHelp.swapCards(-1, 2);
    }

    /**
     * invalid index 1
     */
    @Test (expected = IllegalArgumentException.class)
    public void swapCardsInvalidIndex2Test() {
        matchHelp.swapCards(4, 2);
    }

    /**
     * invalid index 2
     */
    @Test (expected = IllegalArgumentException.class)
    public void swapCardsInvalidIndex3Test() {
        matchHelp.swapCards(1, -1);
    }

    /**
     * invalid index 2
     */
    @Test (expected = IllegalArgumentException.class)
    public void swapCardsInvalidIndex4Test() {
        matchHelp.swapCards(1, 4);
    }

    /**
     * testing valid card swaps
     */
    @Test
    public void swapCardsTest() {
        Card card1 = matchEmptyPlayerList.getDrawPile().get(0);
        Card card2 = matchEmptyPlayerList.getDrawPile().get(2);

        matchHelp.swapCards(0, 2);
        assertEquals(matchEmptyPlayerList.getDrawPile().get(0), card2);
        assertEquals(matchEmptyPlayerList.getDrawPile().get(2), card1);

        matchHelp.swapCards(2, 0);
        assertEquals(matchEmptyPlayerList.getDrawPile().get(0), card1);
        assertEquals(matchEmptyPlayerList.getDrawPile().get(2), card2);
    }

    /**
     * testing if the piles get swapped correctly
     */
    @Test
    public void swapPilesTest() {
        ArrayList<Card> oldDrawPile = this.matchEmptyPlayerList.getDrawPile();
        ArrayList<Card> oldDiscardPile = this.matchEmptyPlayerList.getDiscardPile();
        matchHelp.swapPiles();

        assertEquals("piles have been swapped", this.matchEmptyPlayerList.getDrawPile(), oldDiscardPile);
        assertEquals("piles have been swapped", this.matchEmptyPlayerList.getDiscardPile(), oldDrawPile);
    }

    /**
     * testing getter and setter for match
     */
    @Test
    public void testGetterAndSetterMatch() {
        Match localMatch = new Match();
        MatchHelp localHelp = new MatchHelp(new Match());
        assertNotEquals(localMatch, localHelp.getMatch());
        localHelp.setMatch(localMatch);
        assertEquals(localMatch, localHelp.getMatch());
    }

    /**
     * testing getter and setter for match
     */
    @Test
    public void testGetterAndSetterAlternativeEnd(){
        matchHelp.setAlternativeEnd(true);
        Assert.assertTrue( matchHelp.getAlternativeEnd());
    }
}
