package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Stack;

/**
 * This class is used to test the methods of the TurnHelp class.
 */
public class TurnHelpTest {

    private Turn turn1, turn2;
    private TurnHelp turnHelp,turnHelp1;
    private ArrayList<Player> playerList1;
    private Player player1, activePlayer1, player2, player3;
    private Match match1;
    private int[] moveNumber, moveNumber1, colorsOfShips;
    private ArrayList<Card> harbour, harbour2, harbour3;
    private boolean phaseDiscover;
    private Stack<Move> undoHistory;
    private ArrayList<Move> moves;
    private Card card1, card2, card3, card4, card5, card6, governor;
    private Card card7, card8, card9, card10, card11;

    /**
     * Creates a new test environment before each test method
     * @throws Exception Possible Exceptions with setUp
     */
    @Before
    public void setUp() throws Exception {
        this.phaseDiscover = true;
        this.turnHelp = new TurnHelp(turn2);
        this.playerList1 = new ArrayList<Player>();
        this.player1 = new Player();
        this.activePlayer1 = new Player();
        this.player2 = new Player();
        player1.setVictoryPoints(5);
        player1.setSwords(4);
        activePlayer1.setSwords(5);
        activePlayer1.setVictoryPoints(1);
        player2.setSwords(3);
        player2.setVictoryPoints(4);
        this.moveNumber = new int[]{1,0,1};
        this.colorsOfShips = new int[]{};

        this.harbour = new ArrayList<>();
        this.harbour2 = new ArrayList<>();
        this.harbour3 = new ArrayList<>();
        this.card1 = new Ship(1,1,Color.GREEN);
        this.card2 = new Ship(3,2,Color.YELLOW);
        this.card3 = new Ship(3,30,Color.RED);
        this.card4 = new Ship(3,30,Color.BLACK);
        this.card5 = new Ship(1, 1, Color.BLUE);
        this.card6 = new Ship(3, 5, Color.BLUE);
        this.card7 = new Ship(3, 5, Color.GREEN);
        this.card8 = new Ship(3, 5, Color.YELLOW);
        this.card9 = new Ship(3, 5, Color.RED);
        this.card10 = new Ship(3, 5, Color.BLACK);
        this.card11 = new Person(2,2, Ability.PIRATE, 0, Color.UNDEFINED);

        this.governor = new Person(8,0,Ability.GOVERNOR,0,Color.UNDEFINED );
        governor.flip();
        harbour.add(card1);
        harbour.add(card2);
        harbour.add(card3);
        harbour.add(card4);
        harbour.add(card5);
        harbour.add(card11);

        harbour2.add(card1);
        harbour2.add(card2);
        harbour2.add(card3);
        harbour2.add(card4);

        harbour3.add(card1);
        harbour3.add(card2);

        playerList1.add(player1);
        playerList1.add(activePlayer1);
        playerList1.add(player2);

        this.match1 = new Match();
        this.turn1 = new Turn(playerList1,activePlayer1,match1);

        turn1.setTurnHelp(turnHelp1);
        turn1.setPlayerMoveNumber(moveNumber);
        turn1.setHarbourDisplay(harbour);
        turn1.setIsPhaseDiscover(phaseDiscover);
        turn1.setUndoHistory(undoHistory);
        turn1.setMoves(moves);
        turnHelp.setTurn(turn1);
    }

    /**
     * This method is used to test the constructor of TurnHelp
     */
    @Test
    public void testConstructor() {
        turnHelp1 = new TurnHelp(turn1);
        Assert.assertNotEquals(null, turnHelp1);
        Assert.assertNotEquals(null, turnHelp1.getTurn());
    }

    /**
     * This method is used to test the setTurn method of TurnHelp
     */
    @Test
    public void testSetTurn() {
        turnHelp.setTurn(turn1);
        Assert.assertEquals(turn1, turnHelp.getTurn());
    }

    /**
     * This method is used to test the validHarbourDisplay method of TurnHelp
     * In the tested HarbourDisplay are 5 ship cards with different colors so its valid
     */
    @Test
    public void testValidHarbourDisplayValid() {
        Assert.assertTrue(turnHelp.validHarborDisplay());
    }

    /**
     * This method is used to test the validHarbourDisplay method of TurnHelp
     * Here we added a ship with a color that was already in the HarbourDisplay so it gets invalid
     */
    @Test
    public void testValidHarbourDisplayInvalid() {
        Assert.assertTrue(turnHelp.validHarborDisplay());
        harbour.add(card6);                                         //test blue
        Assert.assertFalse(turnHelp.validHarborDisplay());
    }
    /**
     * This method is used to test the validHarbourDisplay method of TurnHelp
     * Here we added a ship with a color that was already in the HarbourDisplay so it gets invalid
     */
    @Test
    public void testValidHarbourDisplayInvalidGreen() {
        Assert.assertTrue(turnHelp.validHarborDisplay());
        harbour.add(card7);                                         //test green
        Assert.assertFalse(turnHelp.validHarborDisplay());
    }
    /**
     * This method is used to test the validHarbourDisplay method of TurnHelp
     * Here we added a ship with a color that was already in the HarbourDisplay so it gets invalid
     */
    @Test
    public void testValidHarbourDisplayInvalidYellow() {
        Assert.assertTrue(turnHelp.validHarborDisplay());
        harbour.add(card8);                                         //test yellow
        Assert.assertFalse(turnHelp.validHarborDisplay());
    }
    /**
     * This method is used to test the validHarbourDisplay method of TurnHelp
     * Here we added a ship with a color that was already in the HarbourDisplay so it gets invalid
     */
    @Test
    public void testValidHarbourDisplayInvalidRed() {
        Assert.assertTrue(turnHelp.validHarborDisplay());
        harbour.add(card9);                                         //test red
        Assert.assertFalse(turnHelp.validHarborDisplay());
    }
    /**
     * This method is used to test the validHarbourDisplay method of TurnHelp
     * Here we added a ship with a color that was already in the HarbourDisplay so it gets invalid
     */
    @Test
    public void testValidHarbourDisplayInvalidBlack() {
        Assert.assertTrue(turnHelp.validHarborDisplay());
        harbour.add(card10);                                         //test black
        Assert.assertFalse(turnHelp.validHarborDisplay());
    }

    /**
     *  test if card is instance of Ship
     */
    @Test
    public void testValidHarborDisplayInstanceOfShip(){
        this.match1 = new Match();
        this.turn1 = new Turn(playerList1,activePlayer1,match1);

        turn1.setTurnHelp(turnHelp1);
        turn1.setPlayerMoveNumber(moveNumber);
        turn1.setHarbourDisplay(harbour);
        turn1.setIsPhaseDiscover(phaseDiscover);
        turn1.setUndoHistory(undoHistory);
        turn1.setMoves(moves);
        turnHelp.setTurn(turn1);

    }


    /**
     * This method is used to test the maxSwords method of TurnHelp
     * Here the player activePlayer1 has the most Swords with a value of 5
     */
    @Test
    public void testMaxSwordsOnePlayer() {
        ArrayList<Player> maxSwords = turnHelp.maxSwords();
        Assert.assertEquals(activePlayer1, maxSwords.get(0));
    }

    /**
     * This method is used to test the maxSwords method of TurnHelp
     * Here two Players have the same amount of swords (player3 has 5 and activePlayer1 has 5) so both
     * should be returned from maxSwords
     */
    @Test
    public void testMaxSwordsMultiplePlayer() {
        Player player3 = new Player();
        player3.setSwords(5);
        playerList1.add(player3);
        ArrayList<Player> maxSwords = turnHelp.maxSwords();
        Assert.assertEquals(2,maxSwords.size());
    }

    /**
     * This method is used to test the minVictoryPoints method of TurnHelp
     * Here the player activePlayer1 has the least VictoryPoints with a value of 1
     */
    @Test
    public void testMinVictoryPointsOnePlayer() {
        ArrayList<Player> minVP = turnHelp.minVictoryPoints();
        Assert.assertEquals(activePlayer1, minVP.get(0));
    }

    /**
     * This method is used to test the minVictoryPoints method of TurnHelp
     * Here two Players have the same amount of VictoryPoints (player3 has 1 and activePlayer1 has 1) so both
     * should be returned from minVictoryPoints
     */
    @Test
    public void testMinVictoryPointsMultiplePlayer() {
        Player player3 = new Player();
        player3.setVictoryPoints(1);
        playerList1.add(player3);
        ArrayList<Player> minVP = turnHelp.minVictoryPoints();
        Assert.assertEquals(2,minVP.size());
    }

    /**
     * This method is used to test the nextPlayerToMove method of TurnHelp
     * PlayerList - (Player1, activePlayer1, Player 2)
     * active Player - activePlayer1
     * nextPlayerToMove - Player 2
     */
    @Test
    public void testNextPlayerToMoveBehindActivePlayer() {
        Assert.assertEquals(player2 ,turnHelp.nextPlayerToMove());
    }

    /**
     * This method is used to test the nextPlayerToMove method of TurnHelp
     * PlayerList - (Player1, activePlayer1, Player 2)
     * active Player - activePlayer1
     * nextPlayerToMove - Player 1
     */
    @Test
    public void testNextPlayerToMoveBeforeActivePlayer() {
        moveNumber1 = new int[]{1,0,0};
        turn1.setPlayerMoveNumber(moveNumber1);
        Assert.assertEquals(player1, turnHelp.nextPlayerToMove());
    }

    /**
     * This method is used to test the nextPlayerToMove method of TurnHelp
     * PlayerList - (Player1, activePlayer1, Player2)
     * active Player - activePlayer1
     * nextPlayerToMove - null
     */
    @Test
    public void testNextPlayerToMoveNull() {
        moveNumber1 = new int[]{0,0,0};
        turn1.setPlayerMoveNumber(moveNumber1);
        Assert.assertNull(turnHelp.nextPlayerToMove());
    }

    /**
     * This method is used to test the generateMoveNumber method of TurnHelp
     * Here the HarbourDisplay is invalid so all Players should have no moves in this turn
     */
    @Test
    public void testGenerateMoveNumberInvalidHarbourDisplay() {
        moveNumber1 = new int[]{0,0,0};
        harbour.add(card6);
        turnHelp.generateMoveNumber();
        for(int i = 0; i < moveNumber1.length; i++) {
            Assert.assertEquals(moveNumber1[i],turnHelp.getTurn().getPlayerMoveNumber()[i]);
        }
    }

    /**
     * This method is used to test the generateMoveNumber method of TurnHelp
     * Here the HarbourDisplay has four different ships in it, so activePlayer1 should get two moves and the rest one
     * move because they don`t have Governors in their hand
     */
    @Test
    public void testGenerateMoveNumberFourShips() {
        turnHelp.getTurn().setHarbourDisplay(harbour2);
        turnHelp.generateMoveNumber();
        moveNumber1 = new int[]{1,2,1};
        for(int i = 0; i < moveNumber1.length; i++) {
            Assert.assertEquals(moveNumber1[i],turnHelp.getTurn().getPlayerMoveNumber()[i]);
        }
    }

    /**
     * This method is used to test the generateMoveNumber method of TurnHelp
     * Here the HarbourDisplay has five different ships in it, so activePlayer1 should get three moves and the rest one
     * move because they don`t have Governors in their hand
     */
    @Test
    public void testGenerateMoveNumberFiveShips() {
        turnHelp.generateMoveNumber();
        moveNumber1 = new int[]{1,3,1};
        for(int i = 0; i < moveNumber1.length; i++) {
            Assert.assertEquals(moveNumber1[i],turnHelp.getTurn().getPlayerMoveNumber()[i]);
        }
    }

    /**
     * This method is used to test the generateMoveNumber method of TurnHelp
     * Here the HarbourDisplay has five different ships in it and Player1 owns a Governor, so activePlayer1 should get
     * three moves, Player1 two moves and Player 2 one move because he doesn't own a Governor
     */
    @Test
    public void testGenerateMoveNumberNonActivePlayerHasGovernor() {
        player1.addCard(governor);
        turnHelp.generateMoveNumber();
        moveNumber1 = new int[]{2,3,1};
        for(int i = 0; i < moveNumber1.length; i++) {
            Assert.assertEquals(moveNumber1[i],turnHelp.getTurn().getPlayerMoveNumber()[i]);
        }
    }

    /**
     * This method is used to test the generateMoveNumber method of TurnHelp
     * Here the HarbourDisplay has two different ships in it and no one owns a Governor, so every Player should get
     * one move
     */
    @Test
    public void testGenerateMoveNumberUnderFourShips() {
        turnHelp.getTurn().setHarbourDisplay(harbour3);
        turnHelp.generateMoveNumber();
        moveNumber1 = new int[]{1,1,1};
        for(int i = 0; i < moveNumber1.length; i++) {
            Assert.assertEquals(moveNumber1[i],turnHelp.getTurn().getPlayerMoveNumber()[i]);
        }
    }

    /**
     * This method is used to test the setColorsOfShip method of TurnHelp
     */
    @Test
    public void testSetColorsOfShip() {
        turnHelp.setColorsOfShips(colorsOfShips);
        Assert.assertEquals(colorsOfShips,turnHelp.getColorsOfShips());
    }


    /**
     *  test next player of the playerList in turnHelp
     */
    @Test
    public void testNextPlayer() {
        this.turnHelp.setTurn(turn1);
        player1 = new Player("erster", PlayerType.HUMAN);
        player2 = new Player("zweiter", PlayerType.HUMAN);
        player3 = new Player("dritter", PlayerType.HUMAN);
        ArrayList<Player> playerArrayList = new ArrayList<>();
        playerArrayList.add(player1);
        playerArrayList.add(player2);
        playerArrayList.add(player3);
        turn1.setPlayerList(playerArrayList);

        Assert.assertEquals(player1, turnHelp.nextPlayer(player3));
        Assert.assertEquals(player2, turnHelp.nextPlayer(player1));
        Assert.assertEquals(player3, turnHelp.nextPlayer(player2));

    }

    /**
     * This method is used to test the Set-Method for the LastDrawnCard
     */
    @Test
    public void testSetLastDrawnCard(){
        turnHelp.setLastDrawnCard(card1);
        Assert.assertEquals(card1, turnHelp.getLastDrawnCard());
    }
}
