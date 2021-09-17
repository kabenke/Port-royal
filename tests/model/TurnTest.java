package model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Stack;

/**
 * test the main Turn Class.
 */
public class TurnTest {

    private ArrayList<Card> harbourDisplay;
    private Player activePlayer;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player checkPlayer1;
    private Player checkPlayer2;
    private Player nullPlayer;
    private Player notInListPlayer;

    private ArrayList<Player> playerList;
    private Stack<Move> undoHistory;
    private boolean phaseDisplay;
    private int[] playerMoveNumber;
    private Match match;
    private Move move1;
    private Move move2;
    private ArrayList<Move> moves;
    private AllCards allCards;
    private TurnHelp turnHelp;
    private Card personCard;
    private Card blueShipCard;
    private Card redShipCard;
    private Card nulLCard;
    private Turn turn;
    private Turn clonedTurn;
    private TurnHelp clonedTurnHelp;
    private boolean phaseDiscover;

    /**
     * initialize the setUp for testing.
     */
    @Before
    public void setUp() {
        playerList = new ArrayList<>();
        activePlayer = new Player("activeHans", PlayerType.HUMAN);
        player1 = new Player("activeAchim", PlayerType.HUMAN);
        player2 = new Player("activeAnette", PlayerType.HUMAN);
        player3 = new Player("activeGertrud", PlayerType.HUMAN);

        undoHistory = new Stack<>();

        playerList.add(activePlayer);
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);

        personCard = new Person(2, 2, Ability.PIRATE, 2, Color.UNDEFINED);
        blueShipCard = new Ship(4, 4, Color.BLUE);
        redShipCard = new Ship(4, 3, Color.RED);

        match = new Match();
        move1 = new Move(activePlayer, personCard, true, match);
        move2 = new Move(activePlayer, blueShipCard, true, match);

        turn = new Turn(this.playerList, activePlayer, match);
        Stack<Move> stack = new Stack<>();
        turn.setUndoHistory(stack);
        turnHelp = new TurnHelp(this.turn);
        notInListPlayer = new Player("notInListPlayerHans", PlayerType.HUMAN);
        allCards = new AllCards();
        turn.getUndoHistory().add(move1);
        turn.getUndoHistory().add(move2);
        redShipCard.setIsFaceUp(true);
        this.turn.getTurnHelp().addToHarborDisplay(redShipCard);
    }

    /**
     * test if the Turn Object is correct created and references are not null.
     */
    @Test
    public  void testTurnConstructor(){
        Assert.assertNotNull(turn.getHarbourDisplay());
        Assert.assertNotNull(turn.getUndoHistory());
        Assert.assertNotNull(turn.getTurnHelp());
        Assert.assertNotNull(turn.getPlayerMoveNumber());
        Assert.assertNotNull(turn.getMoves());
        Assert.assertTrue(turn.getIsPhaseDiscover());
        Assert.assertEquals(turn.getPlayerList().size(), turn.getPlayerMoveNumber().length);
        Assert.assertEquals(turn, turnHelp.getTurn());

        Player checkPlayer1;
        Player checkPlayer2;

        for(int i=0; i<playerList.size(); i++) {
            checkPlayer1 = playerList.get(i);
            checkPlayer2 = turn.getPlayerList().get(i);
            checkIfPlayerContentIsEqual(checkPlayer1, checkPlayer2);

            checkCardArrayContent(checkPlayer1.getGoldCards(), checkPlayer2.getGoldCards());
            checkCardArrayContent(checkPlayer1.getNonGoldCards(), checkPlayer2.getNonGoldCards());
        }
    }

    /**
     * method to check if two Player Objects have the same content
     * @param checkPlayer1 player1 to check
     * @param checkPlayer2 player2 to check
     */
    public void checkIfPlayerContentIsEqual(Player checkPlayer1, Player checkPlayer2){
        Assert.assertEquals(checkPlayer1.getVictoryPoints(), checkPlayer2.getVictoryPoints());
        Assert.assertEquals(checkPlayer1.getTraderBonus(), checkPlayer2.getTraderBonus());
        Assert.assertEquals(checkPlayer1.getGoldCards(), checkPlayer2.getGoldCards());
        Assert.assertEquals(checkPlayer1.getPlayerType(), checkPlayer2.getPlayerType());
        Assert.assertEquals(checkPlayer1.getMoveCount(), checkPlayer2.getMoveCount());
        Assert.assertEquals(checkPlayer1.getIsActive(),checkPlayer2.getIsActive());
        Assert.assertEquals(checkPlayer1.getEligibleForHighscore(), checkPlayer2.getEligibleForHighscore());
        Assert.assertEquals(checkPlayer1.getName(),checkPlayer2.getName());
    }

    /**
     * check if 2 arrayList of cards is equal
     * @param cardArrayList1 first arrayList
     * @param cardArrayList2 second arrayList
     */

    public void checkCardArrayContent(ArrayList<Card> cardArrayList1, ArrayList<Card> cardArrayList2){
        for(int i=0; i<cardArrayList1.size(); i++) {
            Assert.assertEquals(cardArrayList1.get(i).getCoins(),cardArrayList2.get(i).getCoins());
            Assert.assertEquals(cardArrayList1.get(i).getIsFaceUp(),cardArrayList2.get(i).getIsFaceUp());
            Assert.assertEquals(cardArrayList1.get(i).getClass(), cardArrayList2.get(i).getClass());

        }
    }

    /**
     * check if the moves are equal
     * @param checkMoves1 a move to compare
     * @param checkMoves2 with the other move
     */
    public void testMovesEqual(ArrayList<Move> checkMoves1, ArrayList<Move> checkMoves2){
        for(int i=0; i<checkMoves1.size(); i++) {
            checkIfPlayerContentIsEqual( checkMoves1.get(i).getPlayer(), checkMoves2.get(i).getPlayer());
            Assert.assertEquals(checkMoves1.get(i).getPlayerIsActive(), checkMoves2.get(i).getPlayerIsActive());
        }

    }


    /**
     * help method to check two turn objects
     * @param turn the turn to clone
     * @param clonedTurn the clonedTurn
     */
    public void testClonedTurn(Turn turn, Turn clonedTurn){

        checkCardArrayContent(turn.getHarbourDisplay(),clonedTurn.getHarbourDisplay());
        for(int i=0; i < playerList.size();i++){
            checkIfPlayerContentIsEqual(turn.getPlayerList().get(i),clonedTurn.getPlayerList().get(i));
        }
        ArrayList<Move> clonedMoves = clonedTurn.getMoves();
        testMovesEqual(clonedMoves, this.turn.getMoves());

        for(int i=0; i< undoHistory.size(); i++) {
            checkIfPlayerContentIsEqual( turn.getUndoHistory().pop().getPlayer(), clonedTurn.getUndoHistory().pop().getPlayer());
            Assert.assertEquals(turn.getUndoHistory().pop().getPlayerIsActive(), clonedTurn.getUndoHistory().pop().getPlayerIsActive());
        }
        checkIfPlayerContentIsEqual(turn.getActivePlayer(), clonedTurn.getActivePlayer());

        Assert.assertEquals(this.turn.getIsPhaseDiscover(), clonedTurn.getIsPhaseDiscover());
        Assert.assertEquals(this.turn.getMatch(),this.clonedTurn.getMatch());
    }

    /**
     * test the whole turn clone method
     */
    @Test
    public void testClone(){
        turn.getTurnHelp().addToHarborDisplay(blueShipCard);
        turn.addMove(player2, blueShipCard);
        turn.setActivePlayer(activePlayer);
        turn.setActingPlayer(player2);
        turn.setPlayerList(playerList);
        match.setPlayerList(playerList);
        clonedTurn = turn.clone(match);
        clonedTurn.setPlayerList(playerList);
        clonedTurn.setActivePlayer(activePlayer);

        testClonedTurn(turn, clonedTurn);
        testClonedTurn(turn.getTurnHelp().getTurn(),clonedTurn.getTurnHelp().getTurn());

        for(int i=0; i<clonedTurn.getTurnHelp().getColorsOfShips().length; i++) {
            int turnColor = turn.getTurnHelp().getColorsOfShips()[i];
            int clonedTurnColor = clonedTurn.getTurnHelp().getColorsOfShips()[i];
            Assert.assertEquals(turnColor, clonedTurnColor);
        }
    }




    /**
     * changes the phase of the turn
     */
    @Test
    public void testChangePhase() {
        this.turn.setIsPhaseDiscover(false);
        this.turn.changePhase();
        Assert.assertTrue(this.turn.getIsPhaseDiscover());

        this.turn.setIsPhaseDiscover(true);
        this.turn.changePhase();
        Assert.assertFalse(this.turn.getIsPhaseDiscover());
    }




    /**
     * test if cardToHand throws IllegalArgumentException
     */
    @Test (expected = IllegalArgumentException.class)
    public  void testCardToHandNullPointerPlayer() {
        turn.getTurnHelp().cardToHand(nullPlayer, personCard);
    }
    /**
     * test if cardToHand throws IllegalArgumentException
     */
    @Test (expected = IllegalArgumentException.class)
    public  void testCardToHandNullpointerCard() {
        turn.getTurnHelp().addToHarborDisplay(personCard);
        turn.getTurnHelp().cardToHand(player1, nulLCard);
    }
    /**
     * test if cardToHand throws IllegalArgumentException
     */
    @Test (expected = IllegalArgumentException.class)
    public  void testCardToHandNullpointerHabour() {
        turn.getTurnHelp().addToHarborDisplay(personCard);
        turn.getTurnHelp().cardToHand(player1, blueShipCard);
    }

    /**
     * test if cardToHand throws IllegalArgumentException
     */
    @Test
    public  void testCardToHandFromHabour() {
        turn.getTurnHelp().addToHarborDisplay(personCard);
        turn.getTurnHelp().cardToHand(player1, personCard);
        Assert.assertTrue(player1.getNonGoldCards().contains(personCard));
    }

    /**
     * test if the card is added to the HarbourDisplay
     * nullPointerTest
     */
    @Test (expected = IllegalArgumentException.class)
    public void testAddToHarborDisplayNullPointer(){
        turn.getTurnHelp().addToHarborDisplay(nulLCard);
    }

    /**
     * test if the card is added to the HarbourDisplay
     */
    @Test
    public void testAddToHarborDisplay(){
        turn.getTurnHelp().addToHarborDisplay(personCard);
        Assert.assertTrue(turn.getHarbourDisplay().contains(personCard));
    }

    /**
     * test the addMove method for a card that´s null (used to represent changePhase or endTurn as a move)
     */
    @Test
    public void testAddMoveCardNull(){
        turn.addMove(player1, nulLCard);
        int indexLastMove = turn.getMoves().size()-1;
        Assert.assertEquals(turn.getMoves().get(indexLastMove).getPlayer(), player1);
        Assert.assertNull(turn.getMoves().get(indexLastMove).getCard());
    }

    /**
     * test the addMove of NullPointer
     */
    @Test (expected = IllegalArgumentException.class)
    public void testAddMoveNullPointerPlayer() {
        turn.addMove(nullPlayer,personCard);
    }
    /**
     * test the addMove if cards exist and be added
     */
    @Test
    public void testAddMove() {
        turn.getHarbourDisplay().add(personCard);
        turn.addMove(player1, personCard);
        int indexLastMove = turn.getMoves().size()-1;
        Assert.assertEquals(turn.getMoves().get(indexLastMove).getPlayer(), player1);
        Assert.assertEquals(turn.getMoves().get(indexLastMove).getCard(), personCard);
    }

    /**
     * test getter and setter harbourDisplay
     */
    @Test
    public void testSetGetHarbourDisplay() {
        turn.setHarbourDisplay(this.harbourDisplay);
        Assert.assertEquals(this.harbourDisplay,turn.getHarbourDisplay());
    }

    /**
     * test getter and setter from activePlayer
     */
    @Test
    public void testSetGetActivePlayer(){
        turn.setActivePlayer(player1);
        Assert.assertEquals(player1, turn.getActivePlayer());
    }

    /**
     *  test the getter and setter of the undoHistoryStack
     */
    @Test
    public void testSetGetUndoHistory(){
        turn.setUndoHistory(this.undoHistory);
        Assert.assertEquals(undoHistory, turn.getUndoHistory());
    }
    /**
     *  test the getter and setter of phaseDiscover
     */
    @Test
    public void testSetGetPhaseDiscover(){
        turn.setIsPhaseDiscover(true);
        Assert.assertTrue(turn.getIsPhaseDiscover());
        turn.setIsPhaseDiscover(false);
        Assert.assertFalse(turn.getIsPhaseDiscover());
    }

    /**
     * test the setter and getter of match
     */
    @Test
    public void testSetGetMatch(){
        turn.setMatch(match);
        Assert.assertEquals(match, turn.getMatch());
    }

    /**
     * test getter setter from allCards
     */
    @Test
    public void testSetGetAllCards(){
        turn.setAllCards(allCards);
        Assert.assertEquals(allCards, turn.getAllCards());
    }

    /**
     * test getter and setter from playerList
     */
    @Test
    public void testSetGetPlayerList() {
        turn.setPlayerList(playerList);
        Assert.assertEquals(playerList,turn.getPlayerList());
    }

    /**
     * getter and setter from TurnHelp
     */
    @Test
    public void testSetGetTurnHelp() {
        turn.setTurnHelp(turnHelp);
        Assert.assertEquals(turnHelp,turn.getTurnHelp());
    }

    /**
     * This method is used to test the SetActingPlayer method
     */
    @Test
    public void testSetActingPlayer() {
        turn.setActingPlayer(player3);
        Assert.assertEquals(player3, turn.getActingPlayer());
    }
}
