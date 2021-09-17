package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Test class for the class Match.
 */
public class MatchTest {

    /**
     * match that is used for testing
     */
    private Match match;

    /**
     * match with empty draw pile
     */
    private Match matchEmptyDrawPile;

    /**
     * match with empty player list
     */
    private Match matchEmptyPlayerList;

    /**
     * setUp to initialize objects
     * @throws Exception if something goes wrong
     */
    @Before
    public void setUp() throws Exception {
        match = new Match();
        matchEmptyDrawPile = new Match();
        matchEmptyPlayerList = new Match();
        Player player1 = new Player("Tobi", PlayerType.HUMAN);
        Player player2 = new Player("Tobi_AI", PlayerType.HARD_AI);
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
        matchEmptyDrawPile.setPlayerList(playerList);
        ArrayList<Card> drawPile = new ArrayList<>();
        drawPile.add(new Ship(1, 1, Color.YELLOW));
        drawPile.add(new Ship(2, 3, Color.BLACK));
        drawPile.add(new Ship(3, 4, Color.GREEN));

        ArrayList<Card> discardPile = new ArrayList<>();
        discardPile.add(new Person(-9, 2, Ability.ADMIRAL, 2, Color.UNDEFINED));

        ArrayList<Ability> artifacts = new ArrayList<>();
        artifacts.add(Ability.PRIEST);
        artifacts.add(Ability.CAPTAIN);
        artifacts.add(Ability.CAPTAIN);

        ArrayList<Expedition> expeditions = new ArrayList<>();
        expeditions.add(new Expedition(2, artifacts, 6));

        ArrayList<Turn> turnHistory = new ArrayList<>();
        turnHistory.add(new Turn(playerList, playerList.get(0), match));
        turnHistory.add(new Turn(playerList, playerList.get(0), match));

        match.setDrawPile(drawPile);
        match.setDiscardPile(discardPile);
        match.setExpeditionDisplay(expeditions);
        match.setTurnHistory(turnHistory);
        match.setPlayerList(playerList);
        match.setActivePlayerInLastTurn(player1);
    }

    /**
     * testing if the constructor instantiates list correctly
     */
    @Test
    public void testConstructor() {
        Match testMatch = new Match();
        assertNotNull("object created", testMatch);
        assertNotNull("ArrayList instantiated", testMatch.getExpeditionDisplay());
        assertNotNull("ArrayList instantiated", testMatch.getPlayerList());
        assertNotNull("ArrayList instantiated", testMatch.getTurnHistory());
        assertNotNull("ArrayList instantiated", testMatch.getDiscardPile());
        assertNotNull("ArrayList instantiated", testMatch.getDrawPile());
        assertEquals("aiSpeed initialized with 5", 5, testMatch.getAiSpeed());
    }

    /**
     * testing if cards get added to the discard pile
     */
    @Test
    public void toDiscardPileValidTest() {
        ArrayList<Ability> artifacts = new ArrayList<>();
        artifacts.add(Ability.PRIEST);
        artifacts.add(Ability.CAPTAIN);
        artifacts.add(Ability.CAPTAIN);

        Expedition expedition = new Expedition(2, artifacts, 6);

        assertEquals("Size should be one before adding", match.getDiscardPile().size(), 1);

        match.toDiscardPile(new Ship(1, 1, Color.BLUE));
        match.toDiscardPile(new Person(-4, 2, Ability.ADMIRAL, 2, Color.UNDEFINED));
        match.toDiscardPile(expedition);

        assertEquals("3 cards have been added to the discard pile", match.getDiscardPile().size(), 4);
        assertEquals("last added card is on top", match.getDiscardPile().get(0), expedition);
    }

    /**
     * testing if you cant draw from an empty pile
     */
    @Test (expected = IllegalArgumentException.class)
    public void drawFromPileEmptyTest() {
        ArrayList<Player> localPlayerList = matchEmptyDrawPile.getPlayerList();
        matchEmptyDrawPile.drawFromPile(new Turn(localPlayerList, localPlayerList.get(0), matchEmptyDrawPile));
    }

    /**
     * testing if you can draw a card from a draw pile
     */
    @Test
    public void drawFromPileTest() {
        ArrayList<Player> localPlayerList = matchEmptyDrawPile.getPlayerList();
        Turn turn = new Turn(localPlayerList, localPlayerList.get(0), match);
        match.drawFromPile(turn);

        assertEquals("card has been removed", match.getDrawPile().size(), 2);
        assertEquals("card has been added to turn", turn.getHarbourDisplay().size(), 1);
    }

    /**
     * testing whether clone method clones properly
     */
    @Test
    public void cloneTest() {
        Match clonedMatch = match.clone();

        assertNotEquals("objects cant be the same", clonedMatch, match);

        for(int i = 0; i < match.getDrawPile().size(); i++) {
            Card matchCard = match.getDrawPile().get(i);
            Card cloneCard = clonedMatch.getDrawPile().get(i);
            assertEquals(matchCard.getCoins(), cloneCard.getCoins());
            assertEquals(matchCard.getIsFaceUp(), cloneCard.getIsFaceUp());
        }

        for(int i = 0; i < match.getDiscardPile().size(); i++) {
            Card matchCard = match.getDiscardPile().get(i);
            Card cloneCard = match.getDiscardPile().get(i);
            assertEquals(matchCard.getCoins(), cloneCard.getCoins());
            assertEquals(matchCard.getIsFaceUp(), cloneCard.getIsFaceUp());
        }

        for(int i = 0; i < match.getExpeditionDisplay().size(); i++) {
            Expedition matchExpedition = match.getExpeditionDisplay().get(i);
            Expedition clonedExpedition = match.getExpeditionDisplay().get(i);
            assertEquals(matchExpedition.getCoins(), clonedExpedition.getCoins());
            assertEquals(matchExpedition.getIsFaceUp(), clonedExpedition.getIsFaceUp());
            assertEquals(matchExpedition.getVictoryPoints(), clonedExpedition.getVictoryPoints());
            assertEquals(matchExpedition.getArtifacts(), clonedExpedition.getArtifacts());
        }

        for(int i = 0; i < match.getTurnHistory().size(); i++) {
            Turn matchTurn = match.getTurnHistory().get(i);
            Turn clonedTurn = clonedMatch.getTurnHistory().get(i);
            for(int j = 0; j < matchTurn.getPlayerList().size(); j++) {
                Player matchPlayer = matchTurn.getPlayerList().get(j);
                Player clonedPlayer = clonedTurn.getPlayerList().get(j);
                assertEquals(matchPlayer.moveCount, clonedPlayer.moveCount);
                assertEquals(matchPlayer.name, clonedPlayer.name);
                assertEquals(matchPlayer.playerType, clonedPlayer.playerType);
                assertEquals(matchPlayer.swords, clonedPlayer.swords);
                assertEquals(matchPlayer.victoryPoints, clonedPlayer.victoryPoints);
                assertEquals(matchPlayer.didExp, clonedPlayer.didExp);
                assertEquals(matchPlayer.isActive, clonedPlayer.isActive);
                for(int k = 0; k < matchPlayer.nonGoldCards.size(); k++) {
                    Card playerCard = matchPlayer.getNonGoldCards().get(k);
                    Card clonedCard = clonedPlayer.getNonGoldCards().get(k);
                    assertEquals(playerCard.getCoins(), clonedCard.getCoins());
                    assertEquals(playerCard.getIsFaceUp(), clonedCard.getIsFaceUp());
                }
                assertEquals(matchPlayer.getGoldCards().size(), clonedPlayer.getGoldCards().size());
                assertEquals(matchPlayer.moveCount, clonedPlayer.moveCount);
                assertEquals(matchPlayer.eligibleForHighscore, clonedPlayer.eligibleForHighscore);
                for(int k = 0; k < matchPlayer.getTraderBonus().length; k++) {
                    assertEquals(matchPlayer.traderBonus[k], clonedPlayer.traderBonus[k]);
                }
            }
            assertEquals(matchTurn.getActivePlayer().moveCount, clonedTurn.getActivePlayer().getMoveCount());
            for(int j = 0; j < matchTurn.getMoves().size(); j++) {
                Move matchMove = matchTurn.getMoves().get(j);
                Move clonedMove = clonedTurn.getMoves().get(j);
                assertEquals(matchMove.getCard(), clonedMove.getCard());
                assertEquals(matchMove.getPlayer(), clonedMove.getPlayer());
                assertEquals(matchMove.getPlayerIsActive(), clonedMove.getPlayerIsActive());
            }
            assertEquals(matchTurn.getIsPhaseDiscover(), clonedTurn.getIsPhaseDiscover());
            for(int j = 0; j < matchTurn.getPlayerMoveNumber().length; j++) {
                assertEquals(matchTurn.getPlayerMoveNumber()[i], clonedTurn.getPlayerMoveNumber()[i]);
            }

            for(int j = 0; j < matchTurn.getHarbourDisplay().size(); j++) {
                Card matchCard = matchTurn.getHarbourDisplay().get(j);
                Card clonedCard = clonedTurn.getHarbourDisplay().get(j);
                assertEquals(matchCard.getIsFaceUp(), clonedCard.getIsFaceUp());
                assertEquals(matchCard.getCoins(), clonedCard.getCoins());
                assertEquals(matchTurn.getHarbourDisplay().size(), clonedTurn.getHarbourDisplay().size());
            }

            assertEquals(clonedTurn.getTurnHelp().getTurn(), clonedTurn);

            for(int j = 0; j < matchTurn.getUndoHistory().size(); j++) {
                Move matchMove = matchTurn.getUndoHistory().get(j);
                Move clonedMove = matchTurn.getUndoHistory().get(j);
                assertEquals(matchMove.getPlayerIsActive(), clonedMove.getPlayerIsActive());
                assertEquals(matchMove.getCard(), clonedMove.getCard());
                assertEquals(matchMove.getPlayer(), clonedMove.getPlayer());
            }
        }

        for(int i = 0; i < match.getPlayerList().size(); i++) {
            Player matchPlayer = match.getPlayerList().get(i);
            Player clonedPlayer = clonedMatch.getPlayerList().get(i);
            assertEquals(matchPlayer.getGoldCards().size(), clonedPlayer.getGoldCards().size());
            assertEquals(matchPlayer.getNonGoldCards().size(), clonedPlayer.getNonGoldCards().size());
            assertEquals(matchPlayer.playerType, clonedPlayer.playerType);
            assertEquals(matchPlayer.moveCount, clonedPlayer.moveCount);
            assertEquals(matchPlayer.eligibleForHighscore, clonedPlayer.eligibleForHighscore);
            assertEquals(matchPlayer.isActive, clonedPlayer.isActive);
            assertEquals(matchPlayer.didExp, clonedPlayer.didExp);
            assertEquals(matchPlayer.victoryPoints, clonedPlayer.victoryPoints);
            assertEquals(matchPlayer.swords, clonedPlayer.swords);
            assertEquals(matchPlayer.name, clonedPlayer.name);
            for(int j = 0; j < matchPlayer.getTraderBonus().length; j++) {
                assertEquals(matchPlayer.traderBonus[j], clonedPlayer.traderBonus[j]);
            }
        }

        assertEquals(match.getAiSpeed(), clonedMatch.getAiSpeed());
        assertEquals(match.getInitialCardOrder(), clonedMatch.getInitialCardOrder());
    }

    /**
     * testing to add a player to a full playerlist
     */
    @Test (expected = IllegalArgumentException.class)
    public void testAddPlayerFullList() {
        Player fillPlayer = new Player("Fillip", PlayerType.HUMAN);
        match.getPlayerList().add(fillPlayer);
        match.getPlayerList().add(fillPlayer);
        match.getPlayerList().add(fillPlayer);
        assertEquals(match.getPlayerList().size(), 5);
        match.getMatchHelp().addPlayer(fillPlayer);
    }

    /**
     * testing to add a player to an empty list
     */
    @Test
    public void testAddPlayerSuccess() {
        Player fillPlayer = new Player("Fillip", PlayerType.HUMAN);
        matchEmptyPlayerList.getMatchHelp().addPlayer(fillPlayer);
        assertEquals(matchEmptyPlayerList.getPlayerList().size(), 1);
        assertEquals(matchEmptyPlayerList.getActivePlayerInLastTurn(), fillPlayer);
        matchEmptyPlayerList.getMatchHelp().addPlayer(new Player("nicht Fillip", PlayerType.HUMAN));
        assertEquals(matchEmptyPlayerList.getPlayerList().size(), 2);
        assertEquals(matchEmptyPlayerList.getActivePlayerInLastTurn(), fillPlayer);
    }

    /**
     * testing getter and setter for drawPile
     */
    @Test
    public void getAndSetDrawPileTest() {
        ArrayList<Card> drawPile = new ArrayList<>();
        drawPile.add(new Ship(1, 1, Color.GREEN));
        matchEmptyPlayerList.setDrawPile(drawPile);
        assertEquals(drawPile, matchEmptyPlayerList.getDrawPile());
    }

    /**
     * testing getter and setter for expeditionDisplay
     */
    @Test
    public void getAndSetExpeditionDisplayTest() {
        ArrayList<Expedition> expeditions = new ArrayList<>();
        ArrayList<Ability> artifacts = new ArrayList<>();
        artifacts.add(Ability.PRIEST);
        artifacts.add(Ability.CAPTAIN);
        artifacts.add(Ability.CAPTAIN);
        expeditions.add(new Expedition(2, artifacts, 6));
        matchEmptyPlayerList.setExpeditionDisplay(expeditions);
        assertEquals(expeditions, matchEmptyPlayerList.getExpeditionDisplay());
    }

    /**
     * testing getter and setter for initialCardOrder
     */
    @Test
    public void getAndSetInitialCardOrderTest() {
        ArrayList<Card> order = new ArrayList<>();
        order.add(new Ship(1, 1, Color.GREEN));
        order.add(new Ship(2, 2, Color.BLACK));
        matchEmptyPlayerList.setInitialCardOrder(order);
        assertEquals(order, matchEmptyPlayerList.getInitialCardOrder());
    }

    /**
     * testing getter and setter for aiSpeed
     */
    @Test
    public void getAndSetAISpeedTest() {
        matchEmptyPlayerList.setAiSpeed(6);
        assertEquals(6, matchEmptyPlayerList.getAiSpeed());
    }

    /**
     * testing getter and setter for playerList
     */
    @Test
    public void getAndSetPlayerListTest() {
        Player player1 = new Player("Tobi", PlayerType.HUMAN);
        Player player2 = new Player("Tobi_AI", PlayerType.HARD_AI);
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
        matchEmptyPlayerList.setPlayerList(playerList);
        assertEquals(playerList, matchEmptyPlayerList.getPlayerList());
    }

    /**
     * testing getter and setter for turnHistory
     */
    @Test
    public void getAndSetTurnHistoryTest() {
        Player player1 = new Player("Tobi", PlayerType.HUMAN);
        Player player2 = new Player("Tobi_AI", PlayerType.HARD_AI);
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
        ArrayList<Turn> turnHistory = new ArrayList<>();
        turnHistory.add(new Turn(playerList, player1, matchEmptyPlayerList));
        matchEmptyPlayerList.setTurnHistory(turnHistory);
        assertEquals(turnHistory, matchEmptyPlayerList.getTurnHistory());
    }

    /**
     * test active Turn getter
     */
    @Test
    public void testActiveTurnGetter(){
        Player player1 = new Player("Tobi", PlayerType.HUMAN);
        Player player2 = new Player("Tobi_AI", PlayerType.HARD_AI);
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
        ArrayList<Turn> turnHistory = new ArrayList<>();
        this.match.setTurnHistory(turnHistory);
        Turn newTurn = new Turn(playerList, player1, matchEmptyPlayerList);
        turnHistory.add(newTurn);
        matchEmptyPlayerList.setTurnHistory(turnHistory);
        Assert.assertEquals(newTurn, match.getActiveTurn());
    }



    /**
     * testing getter and setter for discardPile
     */
    @Test
    public void getAndSetDiscardPileTest() {
        ArrayList<Card> discardPile = new ArrayList<>();
        discardPile.add(new Ship(1, 1, Color.GREEN));
        matchEmptyPlayerList.setDiscardPile(discardPile);
        assertEquals(discardPile, matchEmptyPlayerList.getDiscardPile());
    }

    /**
     * testing getter and setter for activePlayer
     */
    @Test
    public void getAndSetActivePlayerTest() {
        Player Tobi = new Player("Tobi", PlayerType.HUMAN);
        matchEmptyPlayerList.setActivePlayerInLastTurn(Tobi);
        assertEquals(Tobi, matchEmptyPlayerList.getActivePlayerInLastTurn());
    }

    /**
     * testing whether you can pull a card from an empty pile
     */
    @Test (expected = IllegalArgumentException.class)
    public void testDrawGoldCardInvalid() {
        matchEmptyDrawPile.drawGoldCard();
    }

    /**
     * testing whether a gold card is correctly drawn from the pile
     */
    @Test
    public void testDrawGoldCard() {
        assertEquals(3, match.getDrawPile().size());
        Card card = match.drawGoldCard();
        assertFalse(card.getIsFaceUp());
        assertEquals(1, card.getCoins());
        assertEquals(Ship.class, card.getClass());
        assertEquals(2, match.getDrawPile().size());
    }

    /**
     * testing getter and setter for matchHelp
     */
    @Test
    public void testGetAndSetMatchHelp() {
        Match localMatch = new Match();
        MatchHelp localHelp = new MatchHelp(localMatch);
        assertNotEquals(localHelp, localMatch.getMatchHelp());
        localMatch.setMatchHelp(localHelp);
        assertEquals(localHelp, localMatch.getMatchHelp());
    }

    /**
     * testing getter and setter for randomShuffle
     */
    @Test
    public void testGetAndSetRandomShuffle(){
        match.setRandomShuffle(true);
        Assert.assertTrue(match.getRandomShuffle());
    }

    /**
     * testing getter and setter for undoTurnHistory
     */
    @Test
    public void testGetAndSetUndoTurnHistory(){
        Stack<Turn> testStack = new Stack<>();
        match.setUndoTurnHistory(testStack);
        Assert.assertEquals(testStack, match.getUndoTurnHistory());
    }

    /**
     * testing getter and setter for timestamp
     */
    @Test
    public void testGetAndSetTimeStamp(){
        Date date = new Date();
        match.setTimestamp(date);
        Assert.assertEquals(date, match.getTimestamp());
    }

    /**
     * This method is used to test the compareTo method to compare the timestamps of matches
     */
    @Test
    public void testCompareToOtherMatch() {
        Date date1 = new Date();
        Date date2 = new Date();
        date1.setTime(1);
        date2.setTime(0);
        match.setTimestamp(date1);
        matchEmptyDrawPile.setTimestamp(date2);
        matchEmptyPlayerList.setTimestamp(date2);
        Assert.assertEquals(-1, match.compareTo(matchEmptyDrawPile));
        Assert.assertEquals(1, matchEmptyDrawPile.compareTo(match));
        Assert.assertEquals(0, matchEmptyDrawPile.compareTo(matchEmptyPlayerList));
    }
}
