package controller;


import model.*;
import controller.MoveController;
import abstractuserinterface.GameAUI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * test for the move Controller
 */
public class MoveControllerTest {

    private MainController mainController;
    private MoveController moveController;
    private MoveControllerHelp moveControllerHelp;
    private GameAUI gameAUI;


    private Move move;
    private Move moveToFail;
    private Move moveWithPoorPlayer;
    private Player player;
    private Player poorPlayer;
    private Player richPlayer;
    private Player jokerPlayer;
    private Turn turn, turn1, turn2;


    private Match match;
    private Expedition fulfillAbleExpedition;
    private Expedition impossibleExpedition;
    private Ship redShip;
    private Person person1;
    private Person person2;
    private Person settler1;
    private Person captain1;
    private Person priest1;
    private Person settler2;
    private Person captain2;
    private Person priest2;
    private Person joker;
    private Person joker2;
    private Person joker3;
    private Person joker4;
    private Person sailor;
    private Person mademoiselle;

    private ArrayList<Ability> oneOfEveryArtifac;
    private ArrayList<Expedition> expeditionDisplay;
    private ArrayList<Player> playerArrayList;

    /**
     * initialize the variables and objects needed for tests
     */
    @Before
    public void setUp(){
        mainController = new MainController();
        moveController = new MoveController(mainController);
        moveControllerHelp = new MoveControllerHelp(moveController);
        gameAUI = new GameAUI() {
            @Override
            public void refreshHarborDisplay() {

            }

            @Override
            public void refreshExpedition() {

            }

            @Override
            public void refreshPlayerAttributes() {

            }

            @Override
            public void refreshPiles() {

            }
        };



        settler1 = new Person(-2, 0, Ability.SETTLER, 0, Color.UNDEFINED );
        captain1 = new Person(2, 2, Ability.CAPTAIN, 0, Color.UNDEFINED);
        priest1 = new Person(2, 0, Ability.PRIEST, 0, Color.UNDEFINED);
        settler2 = new Person(2, 0, Ability.SETTLER, 0, Color.UNDEFINED );
        captain2 = new Person(2, 0, Ability.CAPTAIN, 0, Color.UNDEFINED);
        priest2 = new Person(2, 0, Ability.PRIEST, 0, Color.UNDEFINED);
        joker = new Person(2,0, Ability.JOKER, 0, Color.UNDEFINED);
        joker2 = new Person(2,0, Ability.JOKER, 0, Color.UNDEFINED);
        joker3 = new Person(2,0, Ability.JOKER, 0, Color.UNDEFINED);
        joker4 = new Person(2,0, Ability.JOKER, 0, Color.UNDEFINED);
        sailor = new Person(2, 3, Ability.SAILOR, 1, Color.UNDEFINED);
        mademoiselle = new Person(-9, 0, Ability.MADEMOISELLE, 2, Color.UNDEFINED);


        settler1.flip();
        settler2.flip();
        captain1.flip();
        captain2.flip();
        priest1.flip();
        priest2.flip();
        joker.flip();
        joker2.flip();
        joker3.flip();
        joker4.flip();


        oneOfEveryArtifac = new ArrayList<Ability>();
        oneOfEveryArtifac.add(Ability.PRIEST);
        oneOfEveryArtifac.add(Ability.SETTLER);
        oneOfEveryArtifac.add(Ability.CAPTAIN);
        fulfillAbleExpedition = new Expedition(2, oneOfEveryArtifac, 2);

        player = new Player("Max",PlayerType.HUMAN);
        poorPlayer = new Player("Uwe", PlayerType.HUMAN);
        redShip = new Ship(2, 1, Color.RED);
        person1 = new Person(2, 1, Ability.ADMIRAL, 2, Color.UNDEFINED);
        match = new Match();

        expeditionDisplay = new ArrayList<>();

        richPlayer = new Player("Musk", PlayerType.HUMAN);
        richPlayer.addCard(settler1);
        richPlayer.addCard(priest1);
        richPlayer.addCard(captain1);
        richPlayer.addCard(settler2);
        richPlayer.addCard(priest2);
        richPlayer.addCard(captain2);
        richPlayer.addCard(joker);


        moveToFail = new Move(richPlayer, redShip, true, match);

        playerArrayList = new ArrayList<>();

        playerArrayList.add(richPlayer);

        match.setPlayerList(playerArrayList);

        turn = new Turn(playerArrayList, richPlayer, match);

        ArrayList<Turn> turnHistory = new ArrayList<>();
        turnHistory.add(turn);

        match.setTurnHistory(turnHistory);

        int[] playerMoves = {2,0};
        turn.setPlayerMoveNumber(playerMoves);



    }

    /**
     * test if the method throw IllegalArgumentException
     * moveToFail contains not and expedition card
     */
    @Test (expected = IllegalArgumentException.class)
    public void testFulfillExpeditionIllegalArgumentException(){
        moveController.fulfillExpedition(moveToFail, match);
    }

    /**
     * test if the method throw IllegalArgumentException
     *  method tests if the card in move is equal to the one in expedition display
     */
    @Test (expected =  IllegalArgumentException.class)
    public void testFulfillExpeditionIllegalArgumentExceptionNotDisplay(){


        move = new Move(richPlayer, fulfillAbleExpedition, true, match);
        moveController.fulfillExpedition(move, match);
    }

    /**
     * test if the method throw IllegalArgumentException
     *  method tests if the card in move is equal to the one in expedition display
     */
    @Test (expected =  IllegalArgumentException.class)
    public void testFulfillExpeditionIllegalArgumentExceptionPoorPlayer(){

        move = new Move(poorPlayer, fulfillAbleExpedition, true, match);
        moveController.fulfillExpedition(move, match);
    }

    /**
     * This method is used to test if the RemoveFirstArtifact method of the MoveControllerHelp really
     * throws an IllegalArgumentException when its used with a card that´s not an artifact
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveFirstArtifactNoArtifact(){
        ArrayList<Card> artifacts = new ArrayList<>();
        artifacts.add(redShip);
        moveControllerHelp.removeFirstArtifact(artifacts,Ability.SETTLER);
    }

    /**
     * test if the method throw IllegalArgumentException
     *  method tests if the card in move is equal to the one in expedition display
     */
    @Test
    public void testFulfillExpeditionSuccess(){

       expeditionDisplay.add(fulfillAbleExpedition);
       this.match.setExpeditionDisplay(expeditionDisplay);
       move = new Move(richPlayer, fulfillAbleExpedition, true, this.match);
       turn.getHarbourDisplay().add(fulfillAbleExpedition);
       this.match.getTurnHistory().add(turn);
       this.match.getDrawPile().add(settler1);
       this.match.getDrawPile().add(settler2);
       this.match.setPlayerList(playerArrayList);

       turn.addMove(richPlayer, fulfillAbleExpedition);
       moveController.fulfillExpedition(move, match);

       Assert.assertTrue(richPlayer.getDidExp());
    }
    /**
     *  method tests if the card in move is equal to the one in expedition display
     */
    @Test
    public void testFulfillExpeditionJoker(){

        expeditionDisplay.add(fulfillAbleExpedition);
        this.match.setExpeditionDisplay(expeditionDisplay);
        turn.getHarbourDisplay().add(fulfillAbleExpedition);


        jokerPlayer = new Player("jocky", PlayerType.HUMAN);
        jokerPlayer.addCard(settler1);
        //jokerPlayer.addCard(captain2);
        jokerPlayer.addCard(priest1);
        jokerPlayer.addCard(joker);
        fulfillAbleExpedition.flip();

        move = new Move(jokerPlayer, fulfillAbleExpedition, true, this.match);
        this.match.getTurnHistory().add(turn);
        this.match.getDrawPile().add(settler1);
        this.match.getDrawPile().add(settler2);
        this.match.setPlayerList(playerArrayList);

        turn.addMove(richPlayer, fulfillAbleExpedition);
        moveController.fulfillExpedition(move, match);

        Assert.assertTrue(jokerPlayer.getDidExp());
        Assert.assertEquals(2, jokerPlayer.getVictoryPoints());
        Assert.assertTrue(jokerPlayer.getNonGoldCards().contains(fulfillAbleExpedition));
    }
    /**
     *  method tests if the card in move is equal to the one in expedition display
     */
    @Test
    public void testFulfillExpeditionJokerSettler(){

        expeditionDisplay.add(fulfillAbleExpedition);
        this.match.setExpeditionDisplay(expeditionDisplay);
        turn.getHarbourDisplay().add(fulfillAbleExpedition);


        jokerPlayer = new Player("jocky", PlayerType.HUMAN);
        //jokerPlayer.addCard(settler1);
        jokerPlayer.addCard(captain2);
        jokerPlayer.addCard(priest1);
        jokerPlayer.addCard(joker);

        move = new Move(jokerPlayer, fulfillAbleExpedition, true, this.match);
        this.match.getTurnHistory().add(turn);
        this.match.getDrawPile().add(settler1);
        this.match.getDrawPile().add(settler2);
        this.match.setPlayerList(playerArrayList);

        turn.addMove(richPlayer, fulfillAbleExpedition);
        moveController.fulfillExpedition(move, match);

        Assert.assertTrue(jokerPlayer.getDidExp());
    }

    /**
     *  method tests if the card in move is equal to the one in expedition display
     */
    @Test
    public void testFulfillExpeditionJokerPriest(){

        expeditionDisplay.add(fulfillAbleExpedition);
        this.match.setExpeditionDisplay(expeditionDisplay);
        turn.getHarbourDisplay().add(fulfillAbleExpedition);


        jokerPlayer = new Player("jocky", PlayerType.HUMAN);
        jokerPlayer.addCard(settler1);
        jokerPlayer.addCard(captain2);
        //jokerPlayer.addCard(priest1);
        jokerPlayer.addCard(joker);

        move = new Move(jokerPlayer, fulfillAbleExpedition, true, this.match);
        this.match.getTurnHistory().add(turn);
        this.match.getDrawPile().add(settler1);
        this.match.getDrawPile().add(settler2);
        this.match.setPlayerList(playerArrayList);

        turn.addMove(richPlayer, fulfillAbleExpedition);
        moveController.fulfillExpedition(move, match);

        Assert.assertTrue(jokerPlayer.getDidExp());
    }

    /**
     * test if hireCrew throw an IllegalCastEx if card is not a person Object
     */
    @Test(expected =  ClassCastException.class )
    public void testHireCrewIllegalCast(){
        moveToFail.setCard(redShip);
        moveController.hireCrew(moveToFail, turn);
    }
    /**
     * test if hireCrew throw an IllegalArgEx if player cannot afford
     */
    @Test(expected =  IllegalArgumentException.class )
    public void testHireCrewIllegalArgument(){
        moveToFail.setCard(settler1);
        moveController.hireCrew(moveToFail, turn);
    }


    /**
     * test if passive player in hireCrew pays active player oldest goldCard
     */
    @Test()
    public void testHireCrewCoinToPassive(){

        ArrayList<Card> drawPile = new ArrayList<>();
        drawPile.add(joker);
        drawPile.add(joker2);
        drawPile.add(joker3);
        drawPile.add(joker4);

        this.match.setDrawPile(drawPile);

        settler1.setIsFaceUp(false);
        settler2.setIsFaceUp(false);
        priest2.setIsFaceUp(false);


        //priest1 costs 2 coins
        richPlayer = new Player("Musk", PlayerType.HUMAN);
        richPlayer.addCard(settler1);
        richPlayer.addCard(settler2);
        richPlayer.addCard(priest2);

        poorPlayer = new Player("poorPlayer", PlayerType.HUMAN);

        move = new Move(richPlayer, priest1, false, match);
        playerArrayList = new ArrayList<>();
        playerArrayList.add(richPlayer);
        playerArrayList.add(poorPlayer);
        this.match.setPlayerList(playerArrayList);
        ArrayList<Turn> turns = new ArrayList<>();
        turn1 = new Turn(playerArrayList, richPlayer, this.match);
        int[] playerMoves1 = {1,1};
        turn1.setPlayerMoveNumber(playerMoves1);
        turn2 = new Turn(playerArrayList, poorPlayer, this.match);
        int[] playerMoves2 = {1,1};
        turn2.setPlayerMoveNumber(playerMoves2);
        turns.add(turn1);
        turns.add(turn2);
        this.match.setTurnHistory(turns);

        moveController.hireCrew(move, turn2);
        Assert.assertTrue(this.match.getActiveTurn().getActivePlayer().getGoldCards().contains(settler1));

    }

    /**
     * test if trader blue bonus is increased
     */
    @Test()
    public void testHireCrewTraderBlue(){

        ArrayList<Card> drawPile = new ArrayList<>();
        drawPile.add(joker);
        drawPile.add(joker2);
        drawPile.add(joker3);
        drawPile.add(joker4);

        this.match.setDrawPile(drawPile);

        settler1.setIsFaceUp(false);
        settler2.setIsFaceUp(false);
        priest2.setIsFaceUp(false);
        //priest1 costs 2 coins
        richPlayer = new Player("Musk", PlayerType.HUMAN);
        richPlayer.addCard(settler1);
        richPlayer.addCard(settler2);
        richPlayer.addCard(priest2);

        poorPlayer = new Player("poorPlayer", PlayerType.HUMAN);
        person1 = new Person(2, 0, Ability.TRADER, 1, Color.BLUE);
        move = new Move(richPlayer, person1, false, match);
        playerArrayList = new ArrayList<>();
        playerArrayList.add(richPlayer);
        playerArrayList.add(poorPlayer);
        this.match.setPlayerList(playerArrayList);
        ArrayList<Turn> turns = new ArrayList<>();
        turn1 = new Turn(playerArrayList, richPlayer, this.match);
        int[] playerMoves1 = {1,1};
        turn1.setPlayerMoveNumber(playerMoves1);
        turn2 = new Turn(playerArrayList, poorPlayer, this.match);
        int[] playerMoves2 = {1,1};
        turn2.setPlayerMoveNumber(playerMoves2);
        turns.add(turn1);
        turns.add(turn2);
        this.match.setTurnHistory(turns);
        moveController.hireCrew(move, turn2);

        Assert.assertEquals(1, richPlayer.getTraderBonus()[0]);
    }

    /**
     * test if trader red bonus is increased
     */
    @Test()
    public void testHireCrewTraderRed(){

        ArrayList<Card> drawPile = new ArrayList<>();
        drawPile.add(joker);
        drawPile.add(joker2);
        drawPile.add(joker3);
        drawPile.add(joker4);

        this.match.setDrawPile(drawPile);

        settler1.setIsFaceUp(false);
        settler2.setIsFaceUp(false);
        priest2.setIsFaceUp(false);
        //priest1 costs 2 coins
        richPlayer = new Player("Musk", PlayerType.HUMAN);
        richPlayer.addCard(settler1);
        richPlayer.addCard(settler2);
        richPlayer.addCard(priest2);

        poorPlayer = new Player("poorPlayer", PlayerType.HUMAN);
        person1 = new Person(2, 0, Ability.TRADER, 1, Color.RED);
        move = new Move(richPlayer, person1, false, match);
        playerArrayList = new ArrayList<>();
        playerArrayList.add(richPlayer);
        playerArrayList.add(poorPlayer);
        this.match.setPlayerList(playerArrayList);
        ArrayList<Turn> turns = new ArrayList<>();
        turn1 = new Turn(playerArrayList, richPlayer, this.match);
        int[] playerMoves1 = {1,1};
        turn1.setPlayerMoveNumber(playerMoves1);
        turn2 = new Turn(playerArrayList, poorPlayer, this.match);
        int[] playerMoves2 = {1,1};
        turn2.setPlayerMoveNumber(playerMoves2);
        turns.add(turn1);
        turns.add(turn2);
        this.match.setTurnHistory(turns);
        moveController.hireCrew(move, turn2);

        Assert.assertEquals(1, richPlayer.getTraderBonus()[1]);
    }

    /**
     * test if trader green bonus is increased
     */
    @Test()
    public void testHireCrewTraderGreen(){

        ArrayList<Card> drawPile = new ArrayList<>();
        drawPile.add(joker);
        drawPile.add(joker2);
        drawPile.add(joker3);
        drawPile.add(joker4);

        this.match.setDrawPile(drawPile);

        settler1.setIsFaceUp(false);
        settler2.setIsFaceUp(false);
        priest2.setIsFaceUp(false);
        //priest1 costs 2 coins
        richPlayer = new Player("Musk", PlayerType.HUMAN);
        richPlayer.addCard(settler1);
        richPlayer.addCard(settler2);
        richPlayer.addCard(priest2);

        poorPlayer = new Player("poorPlayer", PlayerType.HUMAN);
        person1 = new Person(2, 0, Ability.TRADER, 1, Color.GREEN);
        move = new Move(richPlayer, person1, false, match);
        playerArrayList = new ArrayList<>();
        playerArrayList.add(richPlayer);
        playerArrayList.add(poorPlayer);
        this.match.setPlayerList(playerArrayList);
        ArrayList<Turn> turns = new ArrayList<>();
        turn1 = new Turn(playerArrayList, richPlayer, this.match);
        int[] playerMoves1 = {1,1};
        turn1.setPlayerMoveNumber(playerMoves1);
        turn2 = new Turn(playerArrayList, poorPlayer, this.match);
        int[] playerMoves2 = {1,1};
        turn2.setPlayerMoveNumber(playerMoves2);
        turns.add(turn1);
        turns.add(turn2);
        this.match.setTurnHistory(turns);
        moveController.hireCrew(move, turn2);

        Assert.assertEquals(1, richPlayer.getTraderBonus()[2]);
    }

    /**
     * test if trader black bonus is increased
     */
    @Test()
    public void testHireCrewTraderBlack(){

        ArrayList<Card> drawPile = new ArrayList<>();
        drawPile.add(joker);
        drawPile.add(joker2);
        drawPile.add(joker3);
        drawPile.add(joker4);

        this.match.setDrawPile(drawPile);

        settler1.setIsFaceUp(false);
        settler2.setIsFaceUp(false);
        priest2.setIsFaceUp(false);
        //priest1 costs 2 coins
        richPlayer = new Player("Musk", PlayerType.HUMAN);
        richPlayer.addCard(settler1);
        richPlayer.addCard(settler2);
        richPlayer.addCard(priest2);

        poorPlayer = new Player("poorPlayer", PlayerType.HUMAN);
        person1 = new Person(2, 0, Ability.TRADER, 1, Color.BLACK);
        move = new Move(richPlayer, person1, false, match);
        playerArrayList = new ArrayList<>();
        playerArrayList.add(richPlayer);
        playerArrayList.add(poorPlayer);
        this.match.setPlayerList(playerArrayList);
        ArrayList<Turn> turns = new ArrayList<>();
        turn1 = new Turn(playerArrayList, richPlayer, this.match);
        int[] playerMoves1 = {1,1};
        turn1.setPlayerMoveNumber(playerMoves1);
        turn2 = new Turn(playerArrayList, poorPlayer, this.match);
        int[] playerMoves2 = {1,1};
        turn2.setPlayerMoveNumber(playerMoves2);
        turns.add(turn1);
        turns.add(turn2);
        this.match.setTurnHistory(turns);
        moveController.hireCrew(move, turn2);

        Assert.assertEquals(1, richPlayer.getTraderBonus()[3]);
    }

    /**
     * test if trader yellow bonus is increased
     */
    @Test()
    public void testHireCrewTraderYellow(){

        ArrayList<Card> drawPile = new ArrayList<>();
        drawPile.add(joker);
        drawPile.add(joker2);
        drawPile.add(joker3);
        drawPile.add(joker4);

        this.match.setDrawPile(drawPile);

        settler1.setIsFaceUp(false);
        settler2.setIsFaceUp(false);
        priest2.setIsFaceUp(false);
        //priest1 costs 2 coins
        richPlayer = new Player("Musk", PlayerType.HUMAN);
        richPlayer.addCard(settler1);
        richPlayer.addCard(settler2);
        richPlayer.addCard(priest2);

        poorPlayer = new Player("poorPlayer", PlayerType.HUMAN);
        person1 = new Person(2, 0, Ability.TRADER, 1, Color.YELLOW);
        move = new Move(richPlayer, person1, false, match);
        playerArrayList = new ArrayList<>();
        playerArrayList.add(richPlayer);
        playerArrayList.add(poorPlayer);
        this.match.setPlayerList(playerArrayList);
        ArrayList<Turn> turns = new ArrayList<>();
        turn1 = new Turn(playerArrayList, richPlayer, this.match);
        int[] playerMoves1 = {1,1};
        turn1.setPlayerMoveNumber(playerMoves1);
        turn2 = new Turn(playerArrayList, poorPlayer, this.match);
        int[] playerMoves2 = {1,1};
        turn2.setPlayerMoveNumber(playerMoves2);
        turns.add(turn1);
        turns.add(turn2);
        this.match.setTurnHistory(turns);
        moveController.hireCrew(move, turn2);

        Assert.assertEquals(1, richPlayer.getTraderBonus()[4]);
    }

    /**
     * This method is used to test the hireCrew for a mademoiselle
     */
    @Test
    public void testHireMademoiselle(){
        ArrayList<Card> gold = new ArrayList<>();
        ArrayList<Card> hand = new ArrayList<>();
        mademoiselle.setIsFaceUp(true);
        settler1.setIsFaceUp(false);
        hand.add(mademoiselle);
        gold.add(settler1);
        gold.add(settler1);
        gold.add(settler1);
        gold.add(settler1);
        gold.add(settler1);
        gold.add(settler1);
        gold.add(settler1);
        gold.add(settler1);
        gold.add(settler1);
        richPlayer.setGoldCards(gold);
        richPlayer.setNonGoldCards(hand);
        turn.setActingPlayer(richPlayer);
        moveToFail.setCard(mademoiselle);
        Assert.assertEquals(9, richPlayer.getGoldCards().size());
        moveController.hireCrew(moveToFail, turn);
        Assert.assertEquals(1, richPlayer.getGoldCards().size());
        Assert.assertEquals(2, richPlayer.getVictoryPoints());
        Assert.assertTrue(richPlayer.getNonGoldCards().contains(mademoiselle));
    }

    /**
     * This method is used to test if FightShip throws an IllegalArgumentException when the card is not a ship card
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFightShipException(){
        moveToFail.setCard(joker);
        moveController.fightShip(moveToFail, turn);
    }

    /**
     * This method is used to test if FightShip works when the Player has 3 swords and the ship he draws only has 1
     */
    @Test
    public void testFightShip(){
        richPlayer.setSwords(3);
        turn.setActingPlayer(richPlayer);
        turn.getTurnHelp().addToHarborDisplay(redShip);
        moveToFail.setCard(redShip);
        Assert.assertTrue(turn.getHarbourDisplay().contains(redShip));
        moveController.fightShip(moveToFail,turn);
        Assert.assertFalse(turn.getHarbourDisplay().contains(redShip));
    }

    /**
     * This method is used to test the sellShip method if the player has no more moves to sell a ship
     */
    @Test
    public void testSellShipsNoMoves(){
        ArrayList<Card> drawPile = new ArrayList<>();
        drawPile.add(joker);
        drawPile.add(joker2);
        drawPile.add(joker3);
        drawPile.add(joker4);

        this.match.setDrawPile(drawPile);

        turn1 = new Turn(playerArrayList, poorPlayer, this.match);
        int[] playerMoves1 = {0,0};
        turn1.setPlayerMoveNumber(playerMoves1);
        moveToFail.setCard(redShip);
        Assert.assertEquals(0, poorPlayer.getNonGoldCards().size());
        moveController.sellShip(moveToFail, turn1);
        Assert.assertEquals(0, poorPlayer.getNonGoldCards().size());
    }

    /**
     * This method is used to test the sellShip method for an active player with 0 GoldCards
     * After he sells a redShip with 2 coins on it he should have 2 GoldCards
     * Also this method tests if the ship gets removed from the harbourDisplay and if it lands on the DiscardPile
     */
    @Test
    public void testSellRedShipActivePlayer(){
        ArrayList<Card> harbour = new ArrayList<>();
        redShip.setIsFaceUp(true);
        harbour.add(redShip);
        ArrayList<Card> drawPile = new ArrayList<>();
        joker.flip();
        drawPile.add(joker);
        joker2.flip();
        drawPile.add(joker2);
        joker3.flip();
        drawPile.add(joker3);
        joker4.flip();
        drawPile.add(joker4);

        ArrayList<Player> myPlayerList = new ArrayList<>();
        myPlayerList.add(poorPlayer);

        this.match.setDrawPile(drawPile);
        this.match.setPlayerList(myPlayerList);


        turn1 = new Turn(myPlayerList, poorPlayer, this.match);
        int[] playerMoves1 = {2};
        turn1.setPlayerMoveNumber(playerMoves1);
        turn1.setActingPlayer(poorPlayer);
        turn1.setHarbourDisplay(harbour);
        ArrayList<Turn> myTurns = new ArrayList<>();
        myTurns.add(turn1);
        match.setTurnHistory(myTurns);
        moveToFail.setCard(redShip);
        moveToFail.setPlayer(poorPlayer);
        Assert.assertEquals(0, poorPlayer.getGoldCards().size());
        moveController.sellShip(moveToFail, turn1);
        Assert.assertEquals(2, poorPlayer.getGoldCards().size());
        Assert.assertTrue(match.getDiscardPile().contains(redShip));
    }

    /**
     * This method is used to test the sellShip method for an active player with 0 GoldCards
     * After he sells a blueShip with 3 coins on it he should have 3 GoldCards
     */
    @Test
    public void testSellBlueShipActivePlayer(){
        Card blueShip = new Ship(3, 2, Color.BLUE);
        ArrayList<Card> drawPile = new ArrayList<>();
        joker.flip();
        drawPile.add(joker);
        joker2.flip();
        drawPile.add(joker2);
        joker3.flip();
        drawPile.add(joker3);
        joker4.flip();
        drawPile.add(joker4);

        ArrayList<Player> myPlayerList = new ArrayList<>();
        myPlayerList.add(poorPlayer);

        this.match.setDrawPile(drawPile);
        this.match.setPlayerList(myPlayerList);

        turn1 = new Turn(myPlayerList, poorPlayer, this.match);
        int[] playerMoves1 = {2};
        turn1.setPlayerMoveNumber(playerMoves1);
        turn1.setActingPlayer(poorPlayer);
        ArrayList<Turn> myTurns = new ArrayList<>();
        myTurns.add(turn1);
        match.setTurnHistory(myTurns);
        moveToFail.setCard(blueShip);
        moveToFail.setPlayer(poorPlayer);
        Assert.assertEquals(0, poorPlayer.getGoldCards().size());
        moveController.sellShip(moveToFail, turn1);
        Assert.assertEquals(3, poorPlayer.getGoldCards().size());
    }

    /**
     * This method is used to test the sellShip method for an active player with 0 GoldCards
     * After he sells a blackShip with 4 coins on it he should have 4 GoldCards
     */
    @Test
    public void testSellBlackShipActivePlayer(){
        Card blackShip = new Ship(4, 3, Color.BLACK);
        ArrayList<Card> drawPile = new ArrayList<>();
        joker.flip();
        drawPile.add(joker);
        joker2.flip();
        drawPile.add(joker2);
        joker3.flip();
        drawPile.add(joker3);
        joker4.flip();
        drawPile.add(joker4);

        ArrayList<Player> myPlayerList = new ArrayList<>();
        myPlayerList.add(poorPlayer);

        this.match.setDrawPile(drawPile);
        this.match.setPlayerList(myPlayerList);

        turn1 = new Turn(myPlayerList, poorPlayer, this.match);
        int[] playerMoves1 = {2};
        turn1.setPlayerMoveNumber(playerMoves1);
        turn1.setActingPlayer(poorPlayer);
        ArrayList<Turn> myTurns = new ArrayList<>();
        myTurns.add(turn1);
        match.setTurnHistory(myTurns);
        moveToFail.setCard(blackShip);
        moveToFail.setPlayer(poorPlayer);
        Assert.assertEquals(0, poorPlayer.getGoldCards().size());
        moveController.sellShip(moveToFail, turn1);
        Assert.assertEquals(4, poorPlayer.getGoldCards().size());
    }

    /**
     * This method is used to test the sellShip method for an active player with 0 GoldCards
     * After he sells a greenShip with 1 coin on it he should have 1 GoldCards
     */
    @Test
    public void testSellGreenShipActivePlayer(){
        Card greenShip = new Ship(1, 2, Color.GREEN);
        ArrayList<Card> drawPile = new ArrayList<>();
        joker.flip();
        drawPile.add(joker);
        joker2.flip();
        drawPile.add(joker2);
        joker3.flip();
        drawPile.add(joker3);
        joker4.flip();
        drawPile.add(joker4);

        ArrayList<Player> myPlayerList = new ArrayList<>();
        myPlayerList.add(poorPlayer);

        this.match.setDrawPile(drawPile);
        this.match.setPlayerList(myPlayerList);

        turn1 = new Turn(myPlayerList, poorPlayer, this.match);
        int[] playerMoves1 = {2};
        turn1.setPlayerMoveNumber(playerMoves1);
        turn1.setActingPlayer(poorPlayer);
        ArrayList<Turn> myTurns = new ArrayList<>();
        myTurns.add(turn1);
        match.setTurnHistory(myTurns);
        moveToFail.setCard(greenShip);
        moveToFail.setPlayer(poorPlayer);
        Assert.assertEquals(0, poorPlayer.getGoldCards().size());
        moveController.sellShip(moveToFail, turn1);
        Assert.assertEquals(1, poorPlayer.getGoldCards().size());
    }

    /**
     * This method is used to test the sellShip method for an active player with 0 GoldCards
     * After he sells a yellowShip with 2 coins on it he should have 2 GoldCards
     */
    @Test
    public void testSellYellowShipActivePlayer(){
        Card yellowShip = new Ship(2, 2, Color.YELLOW);
        ArrayList<Card> drawPile = new ArrayList<>();
        joker.flip();
        drawPile.add(joker);
        joker2.flip();
        drawPile.add(joker2);
        joker3.flip();
        drawPile.add(joker3);
        joker4.flip();
        drawPile.add(joker4);

        ArrayList<Player> myPlayerList = new ArrayList<>();
        myPlayerList.add(poorPlayer);

        this.match.setDrawPile(drawPile);
        this.match.setPlayerList(myPlayerList);

        turn1 = new Turn(myPlayerList, poorPlayer, this.match);
        int[] playerMoves1 = {2};
        turn1.setPlayerMoveNumber(playerMoves1);
        turn1.setActingPlayer(poorPlayer);
        ArrayList<Turn> myTurns = new ArrayList<>();
        myTurns.add(turn1);
        match.setTurnHistory(myTurns);
        moveToFail.setCard(yellowShip);
        moveToFail.setPlayer(poorPlayer);
        Assert.assertEquals(0, poorPlayer.getGoldCards().size());
        moveController.sellShip(moveToFail, turn1);
        Assert.assertEquals(2, poorPlayer.getGoldCards().size());
    }

    /**
     * This method is used to test the sellShip method for a passive player with 0 GoldCards
     * After he sells a redShip with 2 coins on it he should have 1 GoldCard, as well as the active player
     */
    @Test
    public void testSellShipNotActivePlayer(){
        ArrayList<Card> drawPile = new ArrayList<>();
        joker.flip();
        drawPile.add(joker);
        joker2.flip();
        drawPile.add(joker2);
        joker3.flip();
        drawPile.add(joker3);
        joker4.flip();
        drawPile.add(joker4);

        ArrayList<Player> myPlayerList = new ArrayList<>();
        myPlayerList.add(poorPlayer);
        myPlayerList.add(richPlayer);

        this.match.setDrawPile(drawPile);
        this.match.setPlayerList(myPlayerList);

        turn1 = new Turn(myPlayerList, poorPlayer, this.match);
        int[] playerMoves1 = {1,1};
        turn1.setPlayerMoveNumber(playerMoves1);
        turn1.setActingPlayer(richPlayer);
        ArrayList<Turn> myTurns = new ArrayList<>();
        myTurns.add(turn1);
        match.setTurnHistory(myTurns);
        moveToFail.setCard(redShip);
        moveToFail.setPlayer(richPlayer);
        Assert.assertEquals(0, poorPlayer.getGoldCards().size());
        Assert.assertEquals(0, richPlayer.getGoldCards().size());
        moveController.sellShip(moveToFail, turn1);
        Assert.assertEquals(1, poorPlayer.getGoldCards().size());
        Assert.assertEquals(1, richPlayer.getGoldCards().size());
    }

    /**
     * This method is used to test if sellShip throws an IllegalArgumentException when we try to sell a card that
     * is not a ship
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSellShipNoShip(){
        moveToFail.setCard(sailor);
        moveController.sellShip(moveToFail, turn);
    }

    /**
     * Getter and Setter of the MainController
     */
    @Test
    public void testGetAndSetMainController(){
        moveController.setMainController(mainController);
        Assert.assertEquals(mainController, moveController.getMainController());
    }

    /**
     * Getter and Setter of the MoveControllerHelp
     */
    @Test
    public void testGetAndSetMoveControllerHelp(){
        moveController.setMoveControllerHelp(moveControllerHelp);
        Assert.assertEquals(moveControllerHelp, moveController.getMoveControllerHelp());
    }

    /**
     * Getter and Setter of the GameAUI
     */
    @Test
    public void testGetAndSetGameAUI(){
        moveController.setGameAUI(gameAUI);
        Assert.assertEquals(gameAUI, moveController.getGameAUI());
    }

    /**
     * Getter and Setter for the MoveController
     */
    @Test
    public void testGetAndSetMoveController(){
        moveControllerHelp.setMoveController(moveController);
        Assert.assertEquals(moveController, moveControllerHelp.getMoveController());
    }
}
