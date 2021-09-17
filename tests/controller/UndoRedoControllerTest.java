package controller;

import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Stack;


/**
 * This method is used to test the UndoRedoController
 */
public class UndoRedoControllerTest {

    public MainController mainController;
    public UndoRedoController undoRedoController;
    public Match matchWithoutMove, matchWithMove, match3;
    public PortRoyal portRoyal;
    public Turn turn, turn1, turn2;
    public Move move, move2, move3, move4;
    public Stack<Move> undoHistory;
    public Player player1, player2;
    public Card card, card1;

    /**
     * Creates a new test environment before each test method
     * @throws Exception initialise all Tests
     */
    @Before
    public void setUp() throws Exception {
        this.mainController = new MainController();
        this.undoRedoController = new UndoRedoController(mainController);
        this.portRoyal = new PortRoyal();

        this.matchWithoutMove = new Match();
        this.matchWithMove = new Match();
        this.match3 = new Match();

        this.player1 = new Player();
        this.player2 = new Player();
        this.card = new Ship(2, 2, Color.RED);
        this.card1 = new Person(-2, 2, Ability.SETTLER, 3, Color.UNDEFINED);

        this.move = new Move(player1, card, true, matchWithMove);
        this.move2 = new Move(player1, card, true, matchWithMove);
        this.move3 = new Move(player1, card, false, matchWithoutMove);
        this.move4 = new Move(player1, card1, false, matchWithoutMove);

        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);

        matchWithoutMove.setPlayerList(playerList);
        matchWithMove.setPlayerList(playerList);
        match3.setPlayerList(playerList);


        this.turn = new Turn(playerList,player1,matchWithMove);
        this.turn1 = new Turn(playerList, player1, match3);
        this.turn2 = new Turn(playerList, player2, matchWithoutMove);

        mainController.setPortRoyal(portRoyal);
        portRoyal.addMatch(matchWithoutMove);
        portRoyal.addMatch(matchWithMove);
        portRoyal.addMatch(match3);
        portRoyal.setActiveMatch(matchWithoutMove);

        ArrayList<Turn> turns = new ArrayList<>();
        turns.add(turn);
        ArrayList<Turn> turns1 = new ArrayList<>();
        turns1.add(turn);

        matchWithoutMove.setTurnHistory(turns);
        matchWithMove.setTurnHistory(turns);
        match3.setTurnHistory(turns1);

        ArrayList<Move> moves = new ArrayList<>();
        moves.add(move);
        moves.add(move2);

        this.undoHistory = new Stack<>();
        undoHistory.push(move2);

        turn.setUndoHistory(undoHistory);
        turn.setMoves(moves);
    }

    /**
     * This method is used to test the Getter and Setter for the mainController
     */
    @Test
    public void testSetAndGetMainController(){
        undoRedoController.setMainController(mainController);
        Assert.assertEquals(mainController, undoRedoController.getMainController());
    }

    /**
     * This method is used to test if the IllegalArgumentException is thrown correctly when the UndoHistory size is 0
     */
    @Test (expected = IllegalArgumentException.class)
    public void testRedoMoveUndoHistorySize0(){
        Stack<Move> undoHistoryEmpty = new Stack<>();
        turn.setUndoHistory(undoHistoryEmpty);
        undoRedoController.redoMove(turn);
    }

    /**
     * This method is used to test if the IllegalArgumentException is thrown correctly when the turn.getMoves size is 0
     */
    @Test (expected = IllegalArgumentException.class)
    public void testUndoMoveUndoHistorySize0(){
        ArrayList<Move> moves = new ArrayList<>();
        turn.setMoves(moves);
        undoRedoController.undoMove(turn);
    }

    /**
     * This method is used to test the undoMove and redoMove method
     */
    @Test
    public void testUndoRedoMove(){
        Player playerUndo = new Player();

        ArrayList<Card> gold = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            gold.add(card);
        }
        int [] trades = new int[5];
        playerUndo.setGoldCards(gold);
        playerUndo.setVictoryPoints(4);
        playerUndo.setSwords(2);
        playerUndo.setNonGoldCards(gold);
        playerUndo.setAvatarID(1);
        playerUndo.setTraderBonus(trades);
        move2.setPlayerUndo(playerUndo);

        Assert.assertEquals(2, turn.getMoves().size());
        undoRedoController.undoMove(turn);

        Assert.assertEquals(move2.getPlayer().getGoldCards() ,move2.getPlayerUndo().getGoldCards());
        Assert.assertEquals(move2.getPlayer().getVictoryPoints() ,move2.getPlayerUndo().getVictoryPoints());
        Assert.assertEquals(move2.getPlayer().getSwords() ,move2.getPlayerUndo().getSwords());
        Assert.assertEquals(move2.getPlayer().getNonGoldCards() ,move2.getPlayerUndo().getNonGoldCards());
        Assert.assertEquals(move2.getPlayer().getAvatarID() ,move2.getPlayerUndo().getAvatarID());
        Assert.assertEquals(move2.getPlayer().getTraderBonus() ,move2.getPlayerUndo().getTraderBonus());

        move2.setMatchAfterMove(matchWithMove);
        move2.setPlayerRedo(move2.getPlayer());
        undoRedoController.redoMove(turn);

        Assert.assertEquals(move2.getPlayer().getGoldCards() ,move2.getPlayerRedo().getGoldCards());
        Assert.assertEquals(move2.getPlayer().getVictoryPoints() ,move2.getPlayerRedo().getVictoryPoints());
        Assert.assertEquals(move2.getPlayer().getSwords() ,move2.getPlayerRedo().getSwords());
        Assert.assertEquals(move2.getPlayer().getNonGoldCards() ,move2.getPlayerRedo().getNonGoldCards());
        Assert.assertEquals(move2.getPlayer().getAvatarID() ,move2.getPlayerRedo().getAvatarID());
        Assert.assertEquals(move2.getPlayer().getTraderBonus() ,move2.getPlayerRedo().getTraderBonus());
    }

    /**
     * This method is used to test the undoMove and redoMove method when the player of the move is not active and
     * he had to give a coin to the active Player in the move we redone because he sold a ship
     */
    @Test
    public void testRedoMoveNotActivePlayerShip(){
        ArrayList<Card> gold = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            gold.add(card);
        }
        player1.setGoldCards(gold);
        player2.setGoldCards(gold);
        Assert.assertEquals(8, player1.getGoldCards().size());
        Assert.assertEquals(8, player2.getGoldCards().size());
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(move3);
        moves.add(move4);
        turn2.setUndoHistory(undoHistory);
        turn2.setMoves(moves);


        Assert.assertEquals(2, turn2.getMoves().size());
        undoRedoController.undoMove(turn2);
        Assert.assertEquals(7, turn2.getActivePlayer().getGoldCards().size());
        move4.setMatchAfterMove(matchWithoutMove);
        undoRedoController.redoMove(turn2);
        Assert.assertEquals(8, turn2.getActivePlayer().getGoldCards().size());
    }

    /**
     * This method is used to test the undoMove and redoMove method when the player of the move is not active and
     * he had to give a coin to the active Player in the move we undone/redone because he hired a person
     */
    @Test
    public void testRedoMoveNotActivePlayerPerson(){
        ArrayList<Card> gold = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            gold.add(card);
        }
        player1.setGoldCards(gold);
        player2.setGoldCards(gold);
        Assert.assertEquals(8, player1.getGoldCards().size());
        Assert.assertEquals(8, player2.getGoldCards().size());
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(move4);
        moves.add(move3);
        turn2.setUndoHistory(undoHistory);
        turn2.setMoves(moves);


        Assert.assertEquals(2, turn2.getMoves().size());
        undoRedoController.undoMove(turn2);
        Assert.assertEquals(7, turn2.getActivePlayer().getGoldCards().size());
        move3.setMatchAfterMove(matchWithoutMove);
        undoRedoController.redoMove(turn2);
        Assert.assertEquals(8, turn2.getActivePlayer().getGoldCards().size());
    }

    /**
     * This method is used to test if redo throws an IllegalArgumentException as intended when the UndoHistory is empty
     */
    @Test (expected = IllegalArgumentException.class)
    public void testRedoUndoHistorySize0(){
        Stack<Move> undo = new Stack<Move>();
        matchWithoutMove.getActiveTurn().setUndoHistory(undo);
        undoRedoController.redo(matchWithoutMove);
    }

    /**
     * This method is used to test if undo throws an IllegalArgumentException as intended when the Moves and the
     * TurnHistory are empty
     */
    @Test (expected = IllegalArgumentException.class)
    public void testUndoTurnHistorySize1(){
        ArrayList<Move> moves = new ArrayList<>();
        ArrayList<Turn> turns = new ArrayList<>();
        turns.add(turn);
        matchWithoutMove.getActiveTurn().setMoves(moves);
        matchWithoutMove.setTurnHistory(turns);
        undoRedoController.undo(matchWithoutMove);
    }

    /**
     * This method is used to test the undo method when there are no more moves in a turn to undo
     */
    @Test
    public void testUndoNoMoreMovesInTurn(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        ArrayList<Card> gold = new ArrayList<>();
        gold.add(card);
        player1.setGoldCards(gold);
        player2.setGoldCards(gold);
        Turn turn1 = new Turn(players, player2, matchWithoutMove);
        ArrayList<Card> harbour = new ArrayList<>();
        harbour.add(card);
        turn1.setHarbourDisplay(harbour);
        turn1.addMove(player1, card);
        Turn turn2 = new Turn(players, player1, matchWithoutMove);
        ArrayList<Turn> turns = new ArrayList<>();
        turns.add(turn1);
        turns.add(turn2);
        matchWithoutMove.setTurnHistory(turns);
        Assert.assertEquals(2,matchWithoutMove.getTurnHistory().size());
        undoRedoController.undo(matchWithoutMove);
    }

    /**
     * This method is used to test the redo method when there are turns on the UndoTurnHistory Stack
     */
    @Test
    public void testRedoMoreTurnsToRedo(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        ArrayList<Card> gold = new ArrayList<>();
        gold.add(card);
        player1.setGoldCards(gold);
        player2.setGoldCards(gold);

        Turn turn1 = new Turn(players, player2, matchWithoutMove);
        ArrayList<Turn> turns = new ArrayList<>();
        turns.add(turn1);
        turns.add(turn2);
        matchWithoutMove.setTurnHistory(turns);
        Stack<Turn> undoTurnHistory = new Stack<Turn>();
        undoTurnHistory.push(turn1);
        Stack<Move> undoHistory = new Stack<Move>();
        undoHistory.push(move);
        matchWithoutMove.getActiveTurn().setUndoHistory(undoHistory);
        matchWithoutMove.getActiveTurn().getUndoHistory().peek().setMatchAfterMove(match3);
        matchWithoutMove.setUndoTurnHistory(undoTurnHistory);
        Assert.assertNotEquals(matchWithoutMove.getActiveTurn(), matchWithoutMove.getActiveTurn().getUndoHistory().peek().getMatchAfterMove().getActiveTurn());
        undoRedoController.redo(matchWithoutMove);
    }

    /**
     * This method is used to test if canRedo gives back true correctly
     */
    @Test
    public void testCanRedoTrue() {
        portRoyal.setActiveMatch(match3);
        Stack<Move> undoHistory = new Stack<Move>();
        undoHistory.push(move2);
        match3.getActiveTurn().setUndoHistory(undoHistory);
        Assert.assertTrue(undoRedoController.canRedo());
    }

    /**
     * This method is used to test if canRedo gives back false correctly
     */
    @Test
    public void testCanRedoFalse() {
        portRoyal.setActiveMatch(match3);
        Stack<Move> undoHistory = new Stack<>();
        match3.getActiveTurn().setUndoHistory(undoHistory);
        Assert.assertFalse(undoRedoController.canRedo());
    }

    /**
     * This method is used to test if canUndo gives back true correctly
     */
    @Test
    public void testCanUndoTrue() {
        portRoyal.setActiveMatch(matchWithoutMove);
        Assert.assertEquals(2, matchWithoutMove.getActiveTurn().getMoves().size());
        Assert.assertTrue(undoRedoController.canUndo());
    }

    /**
     * This method is used to test if canUndo gives back false correctly
     */
    @Test
    public void testCanUndoFalse() {
        portRoyal.setActiveMatch(matchWithMove);
        ArrayList<Move> moves = new ArrayList<>();
        matchWithMove.getActiveTurn().setMoves(moves);
        Assert.assertEquals(1, matchWithMove.getTurnHistory().size());
        Assert.assertFalse(undoRedoController.canUndo());
    }

    /**
     * This method is used to test if canUndo gives back true correctly
     */
    @Test
    public void testCanUndoTrue2(){
        portRoyal.setActiveMatch(matchWithMove);
        ArrayList<Move> moves = new ArrayList<>();
        matchWithMove.getActiveTurn().setMoves(moves);
        ArrayList<Turn> turns = new ArrayList<>();
        turns.add(turn1);
        turns.add(turn2);
        matchWithMove.setTurnHistory(turns);
        Assert.assertTrue(undoRedoController.canUndo());
    }
}


