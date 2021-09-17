package controller;


import abstractuserinterface.GameAUI;
import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Test all methods in MatchController
 */
public class MatchControllerTest {

    public MainController mainController;
    public MatchController matchController;
    public TurnController turnController;
    public PortRoyal portRoyal;
    public Match matchTestTurn;
    public Card ship1, ship2, ship3, person1, person2, jester1, jester2, card1, card2, trader1, trader2, taxRaise1,
            taxRaise2, capCap, capCapSet, setSet, setSet2;
    public ArrayList<Ability> capcap, capcapset, setset;
    public Player playerTestTurn1, playerTestTurn2;
    public Turn turnTestTurn1, turnTestTurn2;
    public GameAUI gameAUI;

    private ArrayList<Card> cardDeck;
    private ArrayList<Ability> capcap11;
    private ArrayList<Ability> capcapset11;
    private ArrayList<Ability> cappriset;
    private ArrayList<Ability> pripri;
    private ArrayList<Ability> pripriset;
    private ArrayList<Ability> setset11;

    private Card captain, settler, priest, admiral1, admiral2, admiral3, governor, jester11, jester21, jester3, joker,
            mademoiselle1, mademoiselle2, pirate1, pirate2, pirate3, sailor1, sailor2, sailor3, blackTrader,
            blueTrader1, blueTrader2, greenTrader, redTrader, yellowTrader1, yellowTrader2, capCap11, capCapSet11,
            capPriSet, priPri, priPriSet, setSet11, max, min, black1, black2, black3, black4, black5, blue1, blue2, blue3,
            blue4, blue5, blue6, green1, green2, green3, green4, green5, green6, red1, red2, red3, red4, red5, yellow1,
            yellow2, yellow3, yellow4, yellow5, yellow6;

    /**
     * Creates a new test environment before each test method
     * @throws Exception initialise all Tests
     */
    @Before
    public void setUp() throws Exception {
        this.mainController = new MainController();
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

        this.ship1 = new Ship(1, 1, Color.YELLOW);
        this.ship2 = new Ship(1, 1, Color.BLUE);
        this.ship3 = new Ship(2, 1, Color.BLUE);

        this.person1 = new Person(-4, 0, Ability.CAPTAIN, 1, Color.UNDEFINED);
        this.person2 = new Person(-4, 0, Ability.SETTLER, 1, Color.UNDEFINED);
        this.jester1 = new Person(-5, 0, Ability.JESTER, 1, Color.UNDEFINED);
        this.jester2 = new Person(-5, 0, Ability.JESTER, 2, Color.UNDEFINED);
        this.card1 = new Person(-3, 2, Ability.PIRATE,3,Color.UNDEFINED);
        this.card2 = new Person(-3, 1, Ability.PIRATE,3,Color.UNDEFINED);
        this.trader1 = new Person(-3, 0, Ability.TRADER, 1, Color.BLUE);
        this.trader2 = new Person(-3, 0, Ability.TRADER, 1, Color.GREEN);

        this.taxRaise1 = new TaxRaise(true);
        this.taxRaise2 = new TaxRaise(false);

        this.capcap = new ArrayList<>();
        capcap.add(Ability.CAPTAIN);
        capcap.add(Ability.CAPTAIN);
        this.capCap = new Expedition(2, capcap, 4);

        this.capcapset = new ArrayList<>();
        capcapset.add(Ability.CAPTAIN);
        capcapset.add(Ability.CAPTAIN);
        capcapset.add(Ability.SETTLER);
        this.capCapSet = new Expedition(2, capcapset, 4);

        this.setset = new ArrayList<>();
        setset.add(Ability.SETTLER);
        setset.add(Ability.SETTLER);
        this.setSet = new Expedition(2, setset, 4);
        this.setSet2 = new Expedition(2, setset, 5);

        // For test addTurn
        this.matchTestTurn = new Match();
        this.portRoyal = new PortRoyal();
        portRoyal.addMatch(matchTestTurn);
        mainController.setPortRoyal(portRoyal);
        this.playerTestTurn1 = new Player();
        this.playerTestTurn2 = new Player();
        ArrayList<Player> allPlayers = new ArrayList<>();
        allPlayers.add(playerTestTurn1);
        allPlayers.add(playerTestTurn2);
        matchTestTurn.setPlayerList(allPlayers);
        this.turnTestTurn1 = new Turn(allPlayers, playerTestTurn1, matchTestTurn);
        this.turnTestTurn2 = new Turn(allPlayers, playerTestTurn2, matchTestTurn);
        ArrayList<Turn> allTurns = new ArrayList<>();
        matchTestTurn.setTurnHistory(allTurns);

    }

    /**
     * This method is used to test the help method Compare
     */
    @Test
    public void testCompare(){
        Assert.assertFalse(matchController.compare(ship1, person1)); /* not the same card class */
        Assert.assertFalse(matchController.compare(ship1, ship3)); /* not the same amount of coins */
        Assert.assertTrue(matchController.compare(ship1, ship1)); /* same ship card */
        Assert.assertFalse(matchController.compare(ship1, ship2)); /* not the same ship card */
        Assert.assertTrue(matchController.compare(person1,person1)); /* same person card */
        Assert.assertFalse(matchController.compare(person1,person2)); /* not the same person card */
        Assert.assertTrue(matchController.compare(capCap,capCap)); /* same Expedition */
        Assert.assertFalse(matchController.compare(capCap,capCapSet)); /* not the same Expedition */
        Assert.assertFalse(matchController.compare(capCap, setSet)); /* not the same Expedition */
        Assert.assertFalse(matchController.compare(setSet, setSet2)); /* same Expedition but different VP */
        Assert.assertTrue(matchController.compare(taxRaise1, taxRaise1)); /* same taxRaise card (maxSwords) */
        Assert.assertTrue(matchController.compare(taxRaise2, taxRaise2)); /* same taxRaise card (minVP) */
        Assert.assertFalse(matchController.compare(taxRaise1, taxRaise2)); /* not the same taxRaise card */
        Assert.assertFalse(matchController.compare(jester1, jester2)); /* jester with different Victory Points */
        Assert.assertFalse(matchController.compare(card1, card2)); /* different swords */
        Assert.assertFalse(matchController.compare(trader1, trader2)); /* different color */
    }

    /**
     * Test if a turn is added when the method addTurn is called
     */
    @Test
    public void testAddTurn(){
        // addTurn when turnHistory is empty
        Assert.assertTrue(matchTestTurn.getTurnHistory().isEmpty());
        matchTestTurn.setActivePlayerInLastTurn(playerTestTurn1);
        matchController.addTurn(matchTestTurn);
        Turn turn = matchTestTurn.getTurnHistory().get(matchTestTurn.getTurnHistory().size()-1);
        Assert.assertFalse(matchTestTurn.getTurnHistory().isEmpty());
        Assert.assertEquals(turn.getPlayerList(), matchTestTurn.getPlayerList());
        Assert.assertEquals(turn.getActivePlayer(), playerTestTurn1);
        Assert.assertEquals(turn.getMatch(), matchTestTurn);

        //addTurn when turnHistory is not empty
        matchController.addTurn(matchTestTurn);
        Turn turn2 = matchTestTurn.getTurnHistory().get(matchTestTurn.getTurnHistory().size()-1);
        Assert.assertEquals(2, matchTestTurn.getTurnHistory().size());
        Assert.assertEquals(turn2.getPlayerList(), matchTestTurn.getPlayerList());
        Assert.assertEquals(turn2.getActivePlayer(), playerTestTurn2);
        Assert.assertEquals(turn2.getMatch(), matchTestTurn);
    }

    /**
     * This method is used to test the CheckDrawPile method for RandomShuffle
     * It checks the size of the draw and discardPile before and after the method and if the cards get flipped correctly
     */
    @Test
    public void testCheckDrawPileRandomShuffle(){
        Match match = new Match();
        portRoyal.setActiveMatch(match);
        match.setRandomShuffle(true);
        MatchHelp matchHelp = new MatchHelp(match);
        match.setMatchHelp(matchHelp);
        ArrayList<Card> draw = new ArrayList<>();
        match.setDrawPile(draw);
        ArrayList<Card> discard = new ArrayList<>();
        ship1.setIsFaceUp(true);
        discard.add(ship1);
        ship2.setIsFaceUp(true);
        discard.add(ship2);
        ship3.setIsFaceUp(true);
        discard.add(ship3);
        match.setDiscardPile(discard);

        Assert.assertEquals(3, match.getDiscardPile().size());
        Assert.assertEquals(0, match.getDrawPile().size());
        Assert.assertTrue(match.getDiscardPile().get(0).getIsFaceUp());
        matchController.checkDrawPile(match);
        Assert.assertEquals(0, match.getDiscardPile().size());
        Assert.assertEquals(3, match.getDrawPile().size());
        Assert.assertFalse(match.getDrawPile().get(0).getIsFaceUp());
    }

    /**
     * This method is used to test the CheckDrawPile method
     * It checks the size of the draw and discardPile before and after the method and if the cards get flipped correctly
     */
    @Test
    public void testCheckDrawPile(){
        Match match = new Match();
        portRoyal.setActiveMatch(match);
        match.setRandomShuffle(false);
        MatchHelp matchHelp = new MatchHelp(match);
        match.setMatchHelp(matchHelp);
        ArrayList<Card> draw = new ArrayList<>();
        match.setDrawPile(draw);
        ArrayList<Card> discard = new ArrayList<>();
        ship1.setIsFaceUp(true);
        discard.add(ship1);
        ship2.setIsFaceUp(true);
        discard.add(ship2);
        ship3.setIsFaceUp(true);
        discard.add(ship3);
        match.setDiscardPile(discard);

        Assert.assertEquals(3, match.getDiscardPile().size());
        Assert.assertEquals(0, match.getDrawPile().size());
        Assert.assertTrue(match.getDiscardPile().get(0).getIsFaceUp());
        matchController.checkDrawPile(match);
        Assert.assertEquals(0, match.getDiscardPile().size());
        Assert.assertEquals(3, match.getDrawPile().size());
        Assert.assertFalse(match.getDrawPile().get(0).getIsFaceUp());
    }

    /**
     * This method is used to test the shufflePlayerListMethod
     */
    @Test
    public void testShufflePlayerList(){
        Match newMatch = new Match();
        portRoyal.setActiveMatch(newMatch);
        ArrayList<Player> playerList = new ArrayList<>();
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();
        Player player4 = new Player();
        Player player5 = new Player();
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);
        playerList.add(player5);

        newMatch.setPlayerList(playerList);
        Assert.assertEquals(playerList, newMatch.getPlayerList());
        matchController.shufflePlayerlist(newMatch);
        //shufflePlayerlist uses Collections.shuffle which is tested to be correct
    }

    /**
     * This method is used to test initializeDrawPile for a match with 5 players
     * Here the extra Expedition should be placed in the ExpeditionDisplay and every Player should get 3 coins so
     * in the drawPile there should be 104 Cards left
     */
    @Test
    public void testInitializeDrawPile5Player(){
        Match matchTest = new Match();
        portRoyal.addMatch(matchTest);
        portRoyal.setActiveMatch(matchTest);
        ArrayList<Player> playerList = new ArrayList<>();
        ArrayList<Expedition> expeditions = new ArrayList<>();
        ArrayList<Card> draw = new ArrayList<>();
        ArrayList<Card> discard = new ArrayList<>();
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();
        Player player4 = new Player();
        Player player5 = new Player();
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);
        playerList.add(player5);
        matchTest.setPlayerList(playerList);
        matchTest.setExpeditionDisplay(expeditions);
        matchTest.setDrawPile(draw);
        matchTest.setDiscardPile(discard);
        Assert.assertEquals(0, expeditions.size());
        matchController.initializeDrawPile(matchTest);
        Assert.assertEquals(1, expeditions.size());
        Assert.assertEquals(3, player1.getGoldCards().size());
        Assert.assertEquals(3, player2.getGoldCards().size());
        Assert.assertEquals(3, player3.getGoldCards().size());
        Assert.assertEquals(3, player4.getGoldCards().size());
        Assert.assertEquals(3, player5.getGoldCards().size());
        Assert.assertEquals(104, matchTest.getDrawPile().size());
    }

    /**
     * This method is used to test initializeDrawPile for a match with 3 players
     * Here the extra Expedition shouldn't be placed in the ExpeditionDisplay and every Player should get 3 coins so
     * in the drawPile there should be 110 Cards left
     */
    @Test
    public void testInitializeDrawPileLessThan5Player(){
        Match matchTest = new Match();
        portRoyal.addMatch(matchTest);
        portRoyal.setActiveMatch(matchTest);
        ArrayList<Player> playerList = new ArrayList<>();
        ArrayList<Expedition> expeditions = new ArrayList<>();
        ArrayList<Card> draw = new ArrayList<>();
        ArrayList<Card> discard = new ArrayList<>();
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        matchTest.setPlayerList(playerList);
        matchTest.setExpeditionDisplay(expeditions);
        matchTest.setDrawPile(draw);
        matchTest.setDiscardPile(discard);
        matchTest.setRandomShuffle(true);
        Assert.assertEquals(0, expeditions.size());
        matchController.initializeDrawPile(matchTest);
        Assert.assertEquals(0, expeditions.size());
        Assert.assertEquals(3, player1.getGoldCards().size());
        Assert.assertEquals(3, player2.getGoldCards().size());
        Assert.assertEquals(3, player3.getGoldCards().size());
        Assert.assertEquals(110, matchTest.getDrawPile().size());
    }

    /**
     * This method is used to test the Getter and Setter of the GameAUI
     */
    @Test
    public void testSetAndGetGameAUI(){
        matchController.setGameAUI(gameAUI);
        Assert.assertEquals(gameAUI, matchController.getGameAUI());
    }

    /**
     * Test the sort draw pile
     */
    @Test
    public void testSortDrawPile(){
        /*Person Cards*/
        this.captain = new Person(-4,0, Ability.CAPTAIN, 1, Color.UNDEFINED);
        this.settler = new Person(-4,0,Ability.SETTLER, 1, Color.UNDEFINED);
        this.priest = new Person(-4,0,Ability.PRIEST,1,Color.UNDEFINED);
        this.admiral1 = new Person(-5, 0, Ability.ADMIRAL, 1, Color.UNDEFINED);
        this.admiral2 = new Person(-7, 0, Ability.ADMIRAL, 2, Color.UNDEFINED);
        this.admiral3 = new Person(-9, 0, Ability.ADMIRAL, 3, Color.UNDEFINED);
        this.governor = new Person(-8, 0, Ability.GOVERNOR, 0, Color.UNDEFINED);
        this.jester11 = new Person(-5, 0, Ability.JESTER, 1, Color.UNDEFINED);
        this.jester21 = new Person(-7, 0, Ability.JESTER, 2, Color.UNDEFINED);
        this.jester3 = new Person(-9, 0, Ability.JESTER, 3, Color.UNDEFINED);
        this.joker = new Person(-6, 0, Ability.JOKER, 1, Color.UNDEFINED);
        this.mademoiselle1 = new Person(-7, 0, Ability.MADEMOISELLE, 2, Color.UNDEFINED);
        this.mademoiselle2 = new Person(-9, 0, Ability.MADEMOISELLE, 3, Color.UNDEFINED);
        this.pirate1 = new Person(-5, 2, Ability.PIRATE,1,Color.UNDEFINED);
        this.pirate2 = new Person(-7, 2, Ability.PIRATE,2,Color.UNDEFINED);
        this.pirate3 = new Person(-9, 2, Ability.PIRATE,3,Color.UNDEFINED);
        this.sailor1 = new Person(-3, 1, Ability.SAILOR,1,Color.UNDEFINED);
        this.sailor2 = new Person(-5, 1, Ability.SAILOR,2,Color.UNDEFINED);
        this.sailor3 = new Person(-7, 1, Ability.SAILOR,3,Color.UNDEFINED);
        this.blackTrader = new Person(-3, 0, Ability.TRADER, 1, Color.BLACK);
        this.blueTrader1 = new Person(-3, 0, Ability.TRADER, 1, Color.BLUE);
        this.blueTrader2 = new Person(-5, 0, Ability.TRADER, 2, Color.BLUE);
        this.greenTrader = new Person(-3, 0, Ability.TRADER, 1, Color.GREEN);
        this.redTrader = new Person(-3, 0, Ability.TRADER, 1, Color.RED);
        this.yellowTrader1 = new Person(-3, 0, Ability.TRADER, 1, Color.YELLOW);
        this.yellowTrader2 = new Person(-5, 0, Ability.TRADER, 2, Color.YELLOW);


        /*Expedition Cards*/
        this.capcap11 = new ArrayList<>();
        capcap11.add(Ability.CAPTAIN);
        capcap11.add(Ability.CAPTAIN);
        this.capCap11 = new Expedition(2, capcap11,4);

        this.capcapset11 = new ArrayList<>();
        capcapset11.add(Ability.CAPTAIN);
        capcapset11.add(Ability.CAPTAIN);
        capcapset11.add(Ability.SETTLER);
        this.capCapSet11 = new Expedition(3, capcapset11,6);

        this.cappriset = new ArrayList<>();
        cappriset.add(Ability.CAPTAIN);
        cappriset.add(Ability.PRIEST);
        cappriset.add(Ability.SETTLER);
        this.capPriSet = new Expedition(3, cappriset,5);

        this.pripri = new ArrayList<>();
        pripri.add(Ability.PRIEST);
        pripri.add(Ability.PRIEST);
        this.priPri = new Expedition(2, pripri,4);

        this.pripriset = new ArrayList<>();
        pripriset.add(Ability.PRIEST);
        pripriset.add(Ability.PRIEST);
        pripriset.add(Ability.SETTLER);
        this.priPriSet = new Expedition(3, pripriset,6);

        this.setset11 = new ArrayList<>();
        setset11.add(Ability.SETTLER);
        setset11.add(Ability.SETTLER);
        this.setSet11 = new Expedition(2, setset11, 4);


        /*TaxRaise Cards*/
        this.max = new TaxRaise(true);
        this.min = new TaxRaise(false);


        /*Ship Cards*/
        this.black1 = new Ship(1,2,Color.BLACK);
        this.black2 = new Ship(2,4,Color.BLACK);
        this.black3 = new Ship(3,7,Color.BLACK);
        this.black4 = new Ship(3,30,Color.BLACK);
        this.black5 = new Ship(4,30,Color.BLACK);

        this.blue1 = new Ship(1,1,Color.BLUE);
        this.blue2 = new Ship(2,1,Color.BLUE);
        this.blue3 = new Ship(2,2,Color.BLUE);
        this.blue4 = new Ship(3,2,Color.BLUE);
        this.blue5 = new Ship(3,5,Color.BLUE);
        this.blue6 = new Ship(4,5,Color.BLUE);

        this.green1 = new Ship(1, 1,Color.GREEN);
        this.green2 = new Ship(2, 1,Color.GREEN);
        this.green3 = new Ship(2, 3,Color.GREEN);
        this.green4 = new Ship(3, 3,Color.GREEN);
        this.green5 = new Ship(3, 5,Color.GREEN);
        this.green6 = new Ship(4, 5,Color.GREEN);

        this.red1 = new Ship(1, 1, Color.RED);
        this.red2 = new Ship(2, 3, Color.RED);
        this.red3 = new Ship(3, 6, Color.RED);
        this.red4 = new Ship(3, 30, Color.RED);
        this.red5 = new Ship(4, 30, Color.RED);

        this.yellow1 = new Ship(1, 1, Color.YELLOW);
        this.yellow2 = new Ship(2, 1, Color.YELLOW);
        this.yellow3 = new Ship(2, 2, Color.YELLOW);
        this.yellow4 = new Ship(3, 2, Color.YELLOW);
        this.yellow5 = new Ship(3, 4, Color.YELLOW);
        this.yellow6 = new Ship(4, 4, Color.YELLOW);


        /*Initialize Card Deck*/
        //* 2 cards will be exchanged to check if the order is intact after the method is called - card on index 1 (red1) <-> card on index 24(min)
        this.cardDeck = new ArrayList<>();
        cardDeck.add(blue5);
        cardDeck.add(min);
        cardDeck.add(sailor2);
        cardDeck.add(capCapSet11);
        cardDeck.add(yellow3);
        cardDeck.add(black2);
        cardDeck.add(joker);
        cardDeck.add(green1);
        cardDeck.add(red5);
        cardDeck.add(yellow2);
        cardDeck.add(pirate2);
        cardDeck.add(black1);
        cardDeck.add(jester21);
        cardDeck.add(captain);
        cardDeck.add(red3);
        cardDeck.add(sailor1);
        cardDeck.add(jester21);
        cardDeck.add(yellowTrader2);
        cardDeck.add(admiral2);
        cardDeck.add(admiral2);
        cardDeck.add(jester11);
        cardDeck.add(priPri);
        cardDeck.add(max);
        cardDeck.add(green2);
        cardDeck.add(red1);
        cardDeck.add(governor);
        cardDeck.add(sailor1);
        cardDeck.add(redTrader);
        cardDeck.add(redTrader);
        cardDeck.add(green5);
        cardDeck.add(blue6);
        cardDeck.add(settler);
        cardDeck.add(red1);
        cardDeck.add(priest);
        cardDeck.add(admiral2);
        cardDeck.add(yellow5);
        cardDeck.add(blue1);
        cardDeck.add(yellow5);
        cardDeck.add(mademoiselle2);
        cardDeck.add(sailor2);
        cardDeck.add(joker);
        cardDeck.add(blue1);
        cardDeck.add(black4);
        cardDeck.add(blue3);
        cardDeck.add(blue1);
        cardDeck.add(blueTrader1);
        cardDeck.add(red4);
        cardDeck.add(black3);
        cardDeck.add(red2);
        cardDeck.add(pirate3);
        cardDeck.add(sailor1);
        cardDeck.add(blackTrader);
        cardDeck.add(green1);
        cardDeck.add(red3);
        cardDeck.add(settler);
        cardDeck.add(joker);
        cardDeck.add(captain);
        cardDeck.add(yellow4);
        cardDeck.add(green4);
        cardDeck.add(green6);
        cardDeck.add(governor);
        cardDeck.add(yellow1);
        cardDeck.add(captain);
        cardDeck.add(green3);
        cardDeck.add(admiral3);
        cardDeck.add(settler);
        cardDeck.add(settler);
        cardDeck.add(black1);
        cardDeck.add(jester21);
        cardDeck.add(governor);
        cardDeck.add(sailor1);
        cardDeck.add(admiral1);
        cardDeck.add(pirate2);
        cardDeck.add(captain);
        cardDeck.add(sailor1);
        cardDeck.add(black5);
        cardDeck.add(blue4);
        cardDeck.add(sailor3);
        cardDeck.add(jester3);
        cardDeck.add(black2);
        cardDeck.add(blue5);
        cardDeck.add(captain);
        cardDeck.add(black2);
        cardDeck.add(red2);
        cardDeck.add(green5);
        cardDeck.add(priest);
        cardDeck.add(black1);
        cardDeck.add(capCap11);
        cardDeck.add(blueTrader2);
        cardDeck.add(yellow1);
        cardDeck.add(sailor1);
        cardDeck.add(black3);
        cardDeck.add(yellow1);
        cardDeck.add(mademoiselle1);
        cardDeck.add(green3);
        cardDeck.add(sailor1);
        cardDeck.add(max);
        cardDeck.add(priest);
        cardDeck.add(greenTrader);
        cardDeck.add(blackTrader);
        cardDeck.add(yellow3);
        cardDeck.add(priest);
        cardDeck.add(settler);
        cardDeck.add(min);
        cardDeck.add(yellow6);
        cardDeck.add(red2);
        cardDeck.add(greenTrader);
        cardDeck.add(blue2);
        cardDeck.add(blue3);
        cardDeck.add(priest);
        cardDeck.add(mademoiselle1);
        cardDeck.add(mademoiselle2);
        cardDeck.add(setSet11);
        cardDeck.add(red1);
        cardDeck.add(green1);
        cardDeck.add(yellowTrader1);
        cardDeck.add(priPriSet);
        cardDeck.add(admiral3);
        cardDeck.add(governor);


        Match match = new Match();
        portRoyal.addMatch(match);
        portRoyal.setActiveMatch(match);
        ArrayList<Player> playerList = new ArrayList<>();
        ArrayList<Expedition> expeditions = new ArrayList<>();
        ArrayList<Card> draw = new ArrayList<>();
        Player player1 = new Player();
        playerList.add(player1);

        match.setPlayerList(playerList);
        match.setExpeditionDisplay(expeditions);
        match.setDrawPile(draw);
        match.setDiscardPile(cardDeck);

        Assert.assertEquals(match.getDiscardPile().size(),mainController.getParseController().parseCsvToPile().size());
        Assert.assertFalse(matchController.compare(match.getDiscardPile().get(1),mainController.getParseController().parseCsvToPile().get(1)));
        Assert.assertFalse(matchController.compare(match.getDiscardPile().get(24),mainController.getParseController().parseCsvToPile().get(24)));
        Assert.assertTrue(matchController.compare(match.getDiscardPile().get(2),mainController.getParseController().parseCsvToPile().get(2)));

        matchController.sortDrawPile(match);

        Assert.assertEquals(118, match.getDiscardPile().size());
        Assert.assertTrue(matchController.compare(red1, mainController.getParseController().parseCsvToPile().get(1)));
        Assert.assertTrue(matchController.compare(red1, match.getDiscardPile().get(1)));
        Assert.assertTrue(matchController.compare(min, match.getDiscardPile().get(24)));
        for(int i = 0; i < match.getDiscardPile().size(); i++){
            Assert.assertTrue(matchController.compare(match.getDiscardPile().get(i),mainController.getParseController().parseCsvToPile().get(i)));
        }


    }
}
