package controller;

import abstractuserinterface.GameAUI;
import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Test the TurnController class.
 */
public class TurnControllerTest {
    private MainController mainController;
    private MatchController matchController;
    private PortRoyal portRoyal;
    private Match match, matchTax;
    private Player player, player1, player2, player3;
    private Turn turn, turnTax;
    private Turn isPhaseDiscover;
    private TurnController turnController;
    private Card card1, card2, card3, card4, card5, goldCard, ship, jester, admiral;
    private Expedition expedition, expedition2;
    private TaxRaise taxRaise, taxRaiseF;
    private Ability artefact;
    private TurnHelp turnHelp;
    private GameAUI gameAUI;

    /**
     * Creates a new test environment before each test method
     * @throws Exception Possible Exceptions with setUp
     */
    @Before
    public void setUp() throws Exception {
        this.mainController = new MainController();
        this.turnController = new TurnController(mainController);
        this.matchController = new MatchController(mainController);
        this.gameAUI = new GameAUI() {
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

        //create players and player list
        ArrayList<Player> allPLayers = new ArrayList<>();
        this.player1 = new Player();
        this.player2 = new Player();
        this.player3 = new Player();
        allPLayers.add(player1);
        allPLayers.add(player2);
        allPLayers.add(player3);

        // create match + set playerList
        this.match = new Match();
        this.portRoyal = new PortRoyal();
        mainController.setPortRoyal(portRoyal);
        portRoyal.addMatch(match);
        match.setPlayerList(allPLayers);

        this.matchTax = new Match();
        portRoyal.addMatch(matchTax);
        matchTax.setPlayerList(allPLayers);

        //create turn + setTurnHistory
        this.turn = new Turn(allPLayers, player1, match);
        ArrayList <Turn> turns = new ArrayList<>();
        turns.add(turn);
        match.setTurnHistory(turns);
        this.turnHelp = new TurnHelp(turn);

        this.turnTax = new Turn(allPLayers, player2, matchTax);
        ArrayList <Turn> turnsTax = new ArrayList<>();
        turnsTax.add(turnTax);
        matchTax.setTurnHistory(turnsTax);

        // Create some cards
        this.card1 = new Card() {
            @Override
            public Card clone() {
                return card1;
            }
        };
        this.card2 = new Card() {
            @Override
            public Card clone() {
                return card2;
            }
        };
        this.card3 = new Card() {
            @Override
            public Card clone() {
                return card3;
            }
        };
        this.card4 = new Card() {
            @Override
            public Card clone() {
                return card4;
            }
        };
        this.card5 = new Card() {
            @Override
            public Card clone() {
                return card5;
            }
        };

        // Create an expedition card
        ArrayList<Ability> abilities = new ArrayList<>();
        this.artefact = Ability.SETTLER;
        abilities.add(artefact);
        this.expedition = new Expedition(2, abilities, 4 );
        this.expedition2 = new Expedition(3, abilities, 3);

        //Create GoldcardArray with 12 gold cards
        ArrayList <Card> goldCards = new ArrayList<>();
        this.goldCard = new Card() {
            @Override
            public Card clone() {
                return goldCard;
            }
        };
        goldCard.setIsFaceUp(false);
        goldCards.add(goldCard);

        // Set needed info for testDrawCardToHarborDisplayTaxRaise
        // as create an TaxRaise card and set players swords,coins
        this.taxRaise = new TaxRaise(true);
        this.taxRaiseF = new TaxRaise(false);
        player1.setSwords(1);
        player1.setVictoryPoints(1);
        player3.setSwords(2);
        player3.setVictoryPoints(2);
        player2.setSwords(2);
        player2.setVictoryPoints(2);
        player2.setGoldCards(goldCards);

        // Add cards to harbor
        turn.getTurnHelp().addToHarborDisplay(card1);
        turn.getTurnHelp().addToHarborDisplay(card2);

        //Create and set drawPile and expeditionDisplay
        ArrayList <Card> drawPile = new ArrayList<>();
        ArrayList <Card> discardPile = new ArrayList<>();
        ArrayList <Card> harbor = new ArrayList<>();
        drawPile.add(expedition);
        drawPile.add(card2);
        drawPile.add(card3);
        drawPile.add(card4);
        match.setDrawPile(drawPile);
        match.setDiscardPile(discardPile);
        ArrayList <Expedition> expDisplay = new ArrayList<>();
        expDisplay.add(expedition2);
        match.setExpeditionDisplay(expDisplay);
        turn.setHarbourDisplay(harbor);

        ArrayList <Card> drawPileTax = new ArrayList<>();
        ArrayList <Card> discardPileTax = new ArrayList<>();
        ArrayList <Card> harborTax = new ArrayList<>();
        drawPileTax.add(taxRaise);
        drawPileTax.add(card4);
        drawPileTax.add(card5);
        drawPileTax.add(taxRaiseF);
        drawPileTax.add(card3);
        drawPileTax.add(card1);

        matchTax.setDrawPile(drawPileTax);
        matchTax.setDiscardPile(discardPileTax);
        turnTax.setHarbourDisplay(harborTax);
        turn.setIsPhaseDiscover(true);
        turnTax.setIsPhaseDiscover(true);

        //setUp AddMove
        player=new Player("playerTest",PlayerType.HUMAN);
        ship = new Ship(1,1, Color.RED) ;
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(player);
        turn=new Turn(playerList,player,match);
        turn.getHarbourDisplay().add(ship);

        this.admiral = new Person(8, 0, Ability.ADMIRAL,1,Color.UNDEFINED);
        admiral.setIsFaceUp(true);

    }

    /**
     * Test if the phase is successfully changed
     */
    @Test
    public void testChangePhase(){
        portRoyal.setActiveMatch(match);
        Assert.assertEquals(true, turn.getIsPhaseDiscover());
        turnController.changePhase(turn);
        Assert.assertEquals(false,turn.getIsPhaseDiscover());
        turnController.changePhase(turn);
        Assert.assertEquals(true, turn.getIsPhaseDiscover());
    }

    /**
     * Test if an expedition card is added to the expedition display
     */
    @Test
    public void testDrawCardToHarborDisplayExpedition() {
        // test expedition
        portRoyal.setActiveMatch(match);
        Assert.assertTrue(match.getDrawPile().contains(expedition));
        turnController.drawCardToHarborDisplay(turn);
        Assert.assertTrue(match.getExpeditionDisplay().contains(expedition));
        Assert.assertFalse(match.getDrawPile().contains(expedition));
    }


    /**
     * Test if the taxRaise card is directly applied after drawn
     */
     @Test
     public void testDrawCardToHarborDisplayTaxRaise(){
     //test TaxRaise
         portRoyal.setActiveMatch(matchTax);
         Assert.assertTrue(matchTax.getDrawPile().contains(taxRaise));
         Assert.assertEquals(1, player2.getGoldCards().size());
         turnController.drawCardToHarborDisplay(turnTax);
         Assert.assertEquals(2, player2.getGoldCards().size());
     }

    /**
     * This method is used to test the Getter and Setter for the match
     */
    @Test
    public void testSetAndGetMatch(){
         turnController.setMatch(match);
         Assert.assertEquals(match, turnController.getMatch());
    }

    /**
     * This method is used to test the Getter and Setter for TurnHelp
     */
    @Test
    public void testSetAndGetTurnHelp(){
         turnController.setTurnHelp(turnHelp);
         Assert.assertEquals(turnHelp, turnController.getTurnHelp());
    }

    /**
     * This method is used to test the Getter and Setter for the GameAUI
     */
    @Test
    public void testSetAndGetGameAUI(){
         turnController.setGameAUI(gameAUI);
         Assert.assertEquals(gameAUI, turnController.getGameAUI());
    }

    /**
     * This method is used to test the Getter and Setter for the MainController
     */
    @Test
    public void testSetAndGetMainController(){
         turnController.setMainController(mainController);
         Assert.assertEquals(mainController, turnController.getMainController());
    }

    /**
     * Test the ClearHarbourDisplay the HarbourDisplay cleared
     */
    @Test
    public void testClearHarborDisplay(){
        turn.getHarbourDisplay().add(card1);
        turn.getHarbourDisplay().add(card2);
        Assert.assertTrue(!turn.getHarbourDisplay().isEmpty());
        turnController.clearHarborDisplay(turn);
        Assert.assertTrue(turn.getHarbourDisplay().isEmpty());
    }

    /**
     * Test of the method addMove
     * turn can not be Null
     * the case where player or ship are Null was tested in TurnTest
     */
    @Test
    public void addMoveTest () {
        turnController.addMove(player,ship,turn);
        int indexLastMove = turn.getMoves().size()-1;
        Assert.assertEquals(turn.getMoves().get(indexLastMove).getPlayer(), player);
        Assert.assertEquals(turn.getMoves().get(indexLastMove).getCard(),ship);
    }

    /**
     * This method is used to test the applyJester method when the harbourDisplay is invalid and every player
     * with a jester should get a coin
     */
    @Test
    public void testApplyJesterInvalidHarbourDisplay() {
        portRoyal.setActiveMatch(match);
        ArrayList<Card> draw = new ArrayList<>();
        ship.setIsFaceUp(false);
        draw.add(ship);
        draw.add(ship);
        draw.add(ship);
        match.setDrawPile(draw);
        turn.setPlayerList(match.getPlayerList());
        turn.getHarbourDisplay().add(ship);
        Assert.assertEquals(2, turn.getHarbourDisplay().size());
        this.jester = new Person(2, 0,Ability.JESTER, 2, Color.UNDEFINED);
        jester.setIsFaceUp(true);
        player1.addCard(jester);
        player2.addCard(jester);
        Assert.assertEquals(0, player1.getGoldCards().size());
        Assert.assertEquals(1, player2.getGoldCards().size());
        Assert.assertTrue(player1.getNonGoldCards().contains(jester));
        Assert.assertTrue(player2.getNonGoldCards().contains(jester));
        turnController.applyJester(turn);
        Assert.assertEquals(1, player1.getGoldCards().size());
        Assert.assertEquals(2, player2.getGoldCards().size());
    }

    /**
     * This method is used to test the applyJester method when the harbourDisplay is still valid but a passive
     * player has an empty harbourDisplay in front of him when it`s his turn to take a card and he owns a jester
     */
    @Test
    public void testApplyJesterValidHarbourDisplayButEmpty() {
        portRoyal.setActiveMatch(match);
        ArrayList<Card> draw = new ArrayList<>();
        ship.setIsFaceUp(false);
        draw.add(ship);
        draw.add(ship);
        draw.add(ship);
        match.setDrawPile(draw);
        turn.getHarbourDisplay().remove(0);
        turn.setPlayerList(match.getPlayerList());
        Assert.assertEquals(0, turn.getHarbourDisplay().size());
        this.jester = new Person(2, 0,Ability.JESTER, 2, Color.UNDEFINED);
        jester.setIsFaceUp(true);
        player1.addCard(jester);
        player2.addCard(jester);
        turn.setActingPlayer(player2);
        Assert.assertEquals(0, player1.getGoldCards().size());
        Assert.assertEquals(1, player2.getGoldCards().size());
        Assert.assertTrue(player1.getNonGoldCards().contains(jester));
        Assert.assertTrue(player2.getNonGoldCards().contains(jester));
        turnController.applyJester(turn);
        Assert.assertEquals(0, player1.getGoldCards().size());
        Assert.assertEquals(2, player2.getGoldCards().size());
    }

    /**
     * Test the applyTaxRaise method
     */
    @Test
    public void testApplyTaxRaise(){
        portRoyal.setActiveMatch(matchTax);
        ArrayList<Card> coins = new ArrayList<>();
        for(int i = 0; i <= 12; i++){
            goldCard.setIsFaceUp(false);
            coins.add(goldCard);
        }
        player2.setGoldCards(coins);
        Assert.assertTrue(matchTax.getDrawPile().contains(taxRaise));
        Assert.assertEquals(0, player3.getGoldCards().size());
        Assert.assertEquals(13, player2.getGoldCards().size());
        Assert.assertEquals(0, player1.getGoldCards().size());
        turnController.drawCardToHarborDisplay(turnTax);
        Assert.assertEquals(1, player3.getGoldCards().size());
        Assert.assertEquals(8, player2.getGoldCards().size());
        Assert.assertEquals(0, player1.getGoldCards().size());
        turnController.drawCardToHarborDisplay(turnTax);
        Assert.assertEquals(1, player3.getGoldCards().size());
        Assert.assertEquals(8, player2.getGoldCards().size());
        Assert.assertEquals(1, player1.getGoldCards().size());
    }

    /**
     * Test apply Admiral
     * cards less than 5 5, ActingPlayer does not have an Admiral card
     */
    @Test
    public void testApplyAdmiralConditionsNotFulfilled(){
        portRoyal.setActiveMatch(match);
        ArrayList<Card> draw = new ArrayList<>();
        ship.setIsFaceUp(false);
        draw.add(ship);
        draw.add(card1);
        draw.add(card3);
        draw.add(card4);
        match.setDrawPile(draw);
        turn.getHarbourDisplay().add(ship);
        turn.getHarbourDisplay().add(card1);
        turn.getHarbourDisplay().add(card2);
        Assert.assertEquals(0, turn.getActingPlayer().getCoins());
        Assert.assertEquals(4,turn.getHarbourDisplay().size());
        turnController.applyAdmiral(turn);
        Assert.assertEquals(0, turn.getActingPlayer().getCoins());
    }

    /**
     * Test apply Admiral
     * // cards less than 5, Acting player does have an Admiral card
     */
    @Test
    public void testApplyAdmiralConditionsPartiallyFulfilled(){
        portRoyal.setActiveMatch(match);
        ArrayList<Card> draw = new ArrayList<>();
        ship.setIsFaceUp(false);
        draw.add(ship);
        draw.add(card1);
        draw.add(card3);
        draw.add(card4);
        match.setDrawPile(draw);
        turn.getHarbourDisplay().add(ship);
        turn.getHarbourDisplay().add(card1);
        turn.getHarbourDisplay().add(card2);
        turn.getActingPlayer().addCard(admiral);
        admiral.setIsFaceUp(true);
        Assert.assertEquals(0, turn.getActingPlayer().getCoins());
        Assert.assertEquals(4,turn.getHarbourDisplay().size());
        Assert.assertTrue(turn.getActivePlayer().getNonGoldCards().contains(admiral));
        turnController.applyAdmiral(turn);
        Assert.assertEquals(0, turn.getActingPlayer().getCoins());
    }
    /**
     * Test apply Admiral
     * cards more or equal 5, ActingPlayer does have an Admiral card
     */
    @Test
    public void testApplyAdmiralConditionsFulfilled(){
        portRoyal.setActiveMatch(match);
        ArrayList<Card> draw1 = new ArrayList<>();
        draw1.add(ship);
        draw1.add(ship);
        draw1.add(ship);
        match.setDrawPile(draw1);
        ArrayList<Card> harbor = new ArrayList<>();
        harbor.add(card1);
        harbor.add(card2);
        harbor.add(card3);
        harbor.add(card4);
        harbor.add(card5);
        turn.setHarbourDisplay(harbor);
        turn.setPlayerList(match.getPlayerList());
        Assert.assertEquals(5, turn.getHarbourDisplay().size());
        admiral.setIsFaceUp(true);
        player1.addCard(admiral);
        turn.setActingPlayer(player1);
        Assert.assertTrue(player1.getNonGoldCards().contains(admiral));
        Assert.assertEquals(true, admiral.getIsFaceUp());
        Assert.assertEquals(5,turn.getHarbourDisplay().size());
        Assert.assertEquals(0, turn.getActingPlayer().getGoldCards().size());
        turnController.applyAdmiral(turn);
        Assert.assertEquals(2, turn.getActingPlayer().getGoldCards().size());
    }
}

