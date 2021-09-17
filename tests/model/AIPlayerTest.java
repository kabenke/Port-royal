package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * class to test AIPlayer class methods.
 */
public class AIPlayerTest {

    /**
     * aiPlayer object
     */
    private AIPlayer aiPlayer;

    /**
     * setup to initialize objects for the following tests
     * @throws Exception if something goes wrong
     */
    @Before
    public void setUp() throws Exception {
        aiPlayer = new AIPlayer(PlayerType.EASY_AI);
        aiPlayer.addCard(new Ship(1, 2, Color.GREEN));

        Ship shipFaceUp = new Ship(2, 3, Color.BLACK);
        shipFaceUp.flip();
        aiPlayer.addCard(shipFaceUp);

        ArrayList<Card> discaredCards = new ArrayList<>();
        discaredCards.add(new Ship(3, 3, Color.BLACK));
        aiPlayer.setDiscardedCards(discaredCards);

        ArrayList<Card> remainCards = new ArrayList<>();
        remainCards.add(new Ship(3, 3, Color.RED));
        aiPlayer.setRemainCards(remainCards);
    }

    /**
     * testing if the constructor initializes everything correct
     */
    @Test
    public void testConstructor() {
        AIPlayer localAIPlayer = new AIPlayer(PlayerType.EASY_AI);
        assertNotNull(localAIPlayer);
        assertEquals(PlayerType.EASY_AI, localAIPlayer.getPlayerType());
        assertEquals("", localAIPlayer.getName());
        assertEquals(0, localAIPlayer.getMoveCount());
        assertNotNull(localAIPlayer.getGoldCards());
        assertNotNull(localAIPlayer.getNonGoldCards());
        assertTrue(localAIPlayer.getEligibleForHighscore());
        assertNotNull(localAIPlayer.getTraderBonus());
        assertEquals(0, localAIPlayer.getVictoryPoints());
        assertFalse(localAIPlayer.getDidExp());
        assertEquals(0, localAIPlayer.getSwords());
    }

    /**
     * testing if clone creates a correct object
     */
    @Test
    public void testClone() {
        AIPlayer clonedPlayer = aiPlayer.clone();

        assertEquals(aiPlayer.getGoldCards().size(), clonedPlayer.getGoldCards().size());
        for(int i = 0; i < aiPlayer.getGoldCards().size(); i++) {
            Card playerCard = aiPlayer.getGoldCards().get(i);
            Card clonedCard = clonedPlayer.getGoldCards().get(i);
            assertEquals(playerCard.getCoins(), clonedCard.getCoins());
            assertEquals(playerCard.getIsFaceUp(), clonedCard.getIsFaceUp());
            assertEquals(playerCard.getClass(), clonedCard.getClass());
        }

        assertEquals(aiPlayer.getNonGoldCards().size(), clonedPlayer.getNonGoldCards().size());
        for(int i = 0; i < aiPlayer.getNonGoldCards().size(); i++) {
            Card playerCard = aiPlayer.getNonGoldCards().get(i);
            Card clonedCard = clonedPlayer.getNonGoldCards().get(i);
            assertEquals(playerCard.getCoins(), clonedCard.getCoins());
            assertEquals(playerCard.getIsFaceUp(), clonedCard.getIsFaceUp());
            assertEquals(playerCard.getClass(), clonedCard.getClass());
        }

        assertEquals(aiPlayer.getDiscardedCards().size(), clonedPlayer.getDiscardedCards().size());
        for(int i = 0; i < aiPlayer.getDiscardedCards().size(); i++) {
            Card playerCard = aiPlayer.getDiscardedCards().get(i);
            Card clonedCard = clonedPlayer.getDiscardedCards().get(i);
            assertEquals(playerCard.getCoins(), clonedCard.getCoins());
            assertEquals(playerCard.getIsFaceUp(), clonedCard.getIsFaceUp());
            assertEquals(playerCard.getClass(), clonedCard.getClass());
        }

        assertEquals(aiPlayer.getRemainCards().size(), clonedPlayer.getRemainCards().size());
        for(int i = 0; i < aiPlayer.getRemainCards().size(); i++) {
            Card playerCard = aiPlayer.getRemainCards().get(i);
            Card clonedCard = clonedPlayer.getRemainCards().get(i);
            assertEquals(playerCard.getCoins(), clonedCard.getCoins());
            assertEquals(playerCard.getIsFaceUp(), clonedCard.getIsFaceUp());
            assertEquals(playerCard.getClass(), clonedCard.getClass());
        }

        for(int i = 0; i < aiPlayer.getTraderBonus().length; i++) {
            assertEquals(aiPlayer.getTraderBonus()[i], clonedPlayer.getTraderBonus()[i]);
        }

        assertEquals(clonedPlayer.name, aiPlayer.name);
        assertEquals(clonedPlayer.playerType, aiPlayer.playerType);
        assertEquals(clonedPlayer.moveCount, aiPlayer.moveCount);
        assertEquals(clonedPlayer.eligibleForHighscore, aiPlayer.eligibleForHighscore);
        assertEquals(clonedPlayer.victoryPoints, aiPlayer.victoryPoints);
        assertEquals(clonedPlayer.didExp, aiPlayer.didExp);
        assertEquals(clonedPlayer.swords, aiPlayer.swords);
    }

    /**
     * testing getter and setter for remainCards
     */
    @Test
    public void getterAndSetterRemainCards() {
        AIPlayer localAIPlayer = new AIPlayer(PlayerType.HARD_AI);
        ArrayList<Card> remainCards = new ArrayList<>();
        remainCards.add(new Ship(1, 2, Color.BLACK));
        localAIPlayer.setRemainCards(remainCards);
        assertEquals(remainCards, localAIPlayer.getRemainCards());
    }

    /**
     * testing getter and setter for discardedCards
     */
    @Test
    public void getterAndSetterDiscardedCards() {
        AIPlayer localAIPlayer = new AIPlayer(PlayerType.HARD_AI);
        ArrayList<Card> discardedCards = new ArrayList<>();
        discardedCards.add(new Ship(1, 2, Color.BLACK));
        localAIPlayer.setDiscardedCards(discardedCards);
        assertEquals(discardedCards, localAIPlayer.getDiscardedCards());
    }

}
