package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * class to test the playerClass.
 */
public class PlayerTest {

    private int swords;
    private boolean didExp;
    private int victoryPoints;
    private int[] traderBonus;
    private boolean eligibleForHighscore;
    private String name;
    private PlayerType playerType;
    private ArrayList<Card> nonGoldCards;
    private ArrayList<Card> goldCards;

    private boolean isActive;
    private int moveCount;
    private Card cardTrader;
    private Card cardSettler;
    private Card cardPriest;
    private Card cardCaptain;
    private Card cardJoker;
    private Card cardPirate;
    private Card nullPointerCard;
    private Player testPlayer;
    private Player clonedPlayer;

    /**
     *
     * @throws Exception initialise all Tests
     */
    @Before
    public void setUp() throws Exception{
        cardTrader = new Person(2, 0, Ability.TRADER, 1, Color.BLUE);
        cardSettler = new Person(1, 0, Ability.SETTLER, 1, Color.UNDEFINED);
        cardPriest  = new Person(0, 0, Ability.PRIEST, 1, Color.UNDEFINED);
        cardCaptain = new Person(2, 0, Ability.CAPTAIN, 1, Color.UNDEFINED);
        cardJoker = new Person(2, 0, Ability.JOKER, 1, Color.UNDEFINED);
        cardPirate = new Person(2, 0, Ability.PIRATE, 1, Color.UNDEFINED);
        testPlayer = new Player("testPlayerHans", PlayerType.HUMAN);
        nonGoldCards = new ArrayList<>();
        goldCards = new ArrayList<>();
        clonedPlayer = new Player();
    }


    /**
     * test the player constructor with attributes
     */
    @Test
    public void testPlayer() {

        assertEquals("testPlayerHans", testPlayer.getName());
        assertEquals(PlayerType.HUMAN, testPlayer.getPlayerType());
        assertEquals(moveCount, testPlayer.getMoveCount());
        assertEquals(this.goldCards , testPlayer.getGoldCards());
        assertEquals(this.nonGoldCards, testPlayer.getNonGoldCards());
        assertEquals(true, testPlayer.getEligibleForHighscore());
        Assert.assertNotNull(testPlayer.getTraderBonus());
        assertEquals(0, testPlayer.getVictoryPoints());
        assertEquals(false, testPlayer.getDidExp());
        assertEquals(0, testPlayer.getSwords());

    }

    /**
     * test if the cloned Player is the same as the original one
     */
    @Test
    public void testClonePlayer(){
        cardCaptain.setCoins(4);
        testPlayer.addCard(cardCaptain);
        testPlayer.addCard(cardPriest);

        Card card1 = cardCaptain.clone();
        Card card2 = cardPriest.clone();
        card1.flip();
        card2.flip();

        testPlayer.addCard(card1);
        testPlayer.addCard(card2);

        clonedPlayer= testPlayer.clone();

        assertEquals(testPlayer.getName(), clonedPlayer.getName());
        assertEquals(testPlayer.getSwords(), clonedPlayer.getSwords());
        assertEquals(testPlayer.getPlayerType(),this.clonedPlayer.getPlayerType());

        for(int i=0; i<testPlayer.getTraderBonus().length; i++) {
            assertEquals(testPlayer.getTraderBonus()[i], clonedPlayer.getTraderBonus()[i]);
        }
        assertEquals(testPlayer.getDidExp(), this.clonedPlayer.getDidExp());
        assertEquals(testPlayer.getGoldCards().size(), clonedPlayer.getGoldCards().size());

       for(int i=0; i< testPlayer.getGoldCards().size(); i++) {
           Card testCard = testPlayer.getGoldCards().get(i);
           Card testClonedCard = clonedPlayer.getGoldCards().get(i);
           assertEquals(testCard.getCoins(), testClonedCard.getCoins());
           assertEquals(testCard.getIsFaceUp(), testClonedCard.getIsFaceUp());
           assertEquals(testCard.getClass(),testClonedCard.getClass());
       }
        for(int i=0; i< testPlayer.getNonGoldCards().size(); i++) {
            Card testCard = testPlayer.getNonGoldCards().get(i);
            Card testClonedCard = clonedPlayer.getNonGoldCards().get(i);
            assertEquals(testCard.getCoins(), testClonedCard.getCoins());
            assertEquals(testCard.getIsFaceUp(), testClonedCard.getIsFaceUp());
            assertEquals(testCard.getClass(),testClonedCard.getClass());
        }

        assertEquals(testPlayer.getEligibleForHighscore(),clonedPlayer.getEligibleForHighscore());
        assertEquals(testPlayer.getIsActive(),clonedPlayer.getIsActive());
        assertEquals(testPlayer.getMoveCount(),clonedPlayer.getMoveCount());
        assertEquals(testPlayer.getVictoryPoints(),clonedPlayer.getVictoryPoints());
    }



    /**
     * test for adding nonGoldCards
     */
    @Test
    public void testAddNonGoldCard(){
            cardTrader.flip();
            testPlayer.addCard(cardTrader);
            assertEquals(cardTrader, testPlayer.getNonGoldCards().get(0));
    }

    /**
     * test for adding GoldCards
     */
    @Test
    public void testAddGoldCard(){
        testPlayer.addCard(cardTrader);
        assertEquals(cardTrader, testPlayer.getGoldCards().get(0));
    }

    /**
     * test if adding is Object is null
     */
    @Test (expected = IllegalArgumentException.class)
    public void testNullpointerAddCard(){
        cardTrader.flip();
        testPlayer.addCard(nullPointerCard);
    }

    /**
     * test if removing is Object is null
     */
    @Test (expected = IllegalArgumentException.class)
    public void testNullpointerRemoveCard(){
        testPlayer.removeCard(nullPointerCard);
    }


    /**
     * test if you can remove a Card from NonGoldCards
     */
    @Test
    public void testRemoveNonGoldCard() {
        cardPirate.flip();
        testPlayer.addCard(cardPirate);
        testPlayer.removeCard(cardPirate);
        Assert.assertTrue(testPlayer.getNonGoldCards().size()==0);
    }

    /**
     * test if you can remove a Card from GoldCards
     */
    @Test
    public void testRemoveGoldCard() {
        testPlayer.addCard(cardPirate);
        testPlayer.removeCard(cardPirate);
        Assert.assertTrue(testPlayer.getGoldCards().size()==0);
    }

    /**
     * test the getter and setter of name together
     */
    @Test
    public void testGetSetName(){
        testPlayer.setName("Hans");
        assertEquals("Hans", testPlayer.getName());
    }

    /**
     * test the getter and setter of swords
     */
    @Test
    public void testGetSetSwords(){
        testPlayer.setSwords(2);
        assertEquals(2, testPlayer.getSwords());
    }

    /**
     * test the getter and setter didExp
     */
    @Test
    public void testGetSetDidExp(){
        testPlayer.setDidExp(false);
        assertEquals(false, testPlayer.getDidExp());
        testPlayer.setDidExp(true);
        assertEquals(true, testPlayer.getDidExp());
    }

    /**
     * test the getter and setter of the Trade Bonus
     */
    @Test
    public void testGetSetTraderBonus(){
        testPlayer.setTraderBonus(traderBonus);
        assertEquals(traderBonus, testPlayer.getTraderBonus());
    }

    /**
     * test the getter and setter eligible for highscore
     */
    @Test
    public void testEligibleForHighscore(){
        testPlayer.setEligibleForHighscore(false);
        assertEquals(false, testPlayer.getEligibleForHighscore());
    }

    /**
     * test the getter and setter for playerType
     */
    @Test
    public void testGetSetPlayerType(){
        testPlayer.setPlayerType(PlayerType.HUMAN);
        assertEquals(PlayerType.HUMAN, testPlayer.getPlayerType());
    }


    /**
     * test the getter and setter for NonGoldCards
     */
    @Test
    public void testGetSetNonGoldCards(){
        nonGoldCards.add(cardPirate);
        nonGoldCards.add(cardCaptain);
        testPlayer.setNonGoldCards(nonGoldCards);
        assertEquals(nonGoldCards, testPlayer.getNonGoldCards());
    }



    /**
     * test the getter and setter for goldCards
     */
    @Test
    public void testGetSetGoldCards(){
        goldCards.add(cardPirate);
        goldCards.add(cardCaptain);
        testPlayer.setGoldCards(goldCards);
        assertEquals(goldCards, testPlayer.getGoldCards());
    }

    /**
     * test the getter and setter for isActive
     */
    @Test
    public void testGetSetIsActive(){
        testPlayer.setIsActive(false);
        assertEquals(false, testPlayer.getIsActive());
        testPlayer.setIsActive(true);
        assertEquals(true, testPlayer.getIsActive());
    }

    /**
     * test the getter and setter for isActive
     */
    @Test
    public void testGetSetMoveCount(){
        testPlayer.setMoveCount(5);
        assertEquals(5, testPlayer.getMoveCount());
    }

    /**
     * test the get Coins
     */
    @Test
    public void testGetCoins(){
        assertEquals(testPlayer.getGoldCards().size(), testPlayer.getCoins());
    }

    /**
     * test the getter and setter for playerHelp
     */
    @Test
    public void testGetSetPlayerHelp() {
        Player localPlayer = new Player();
        PlayerHelp localHelp = new PlayerHelp(localPlayer);
        localPlayer.setPlayerHelp(localHelp);
        assertEquals(localHelp, localPlayer.getPlayerHelp());
    }

    /**
     * test if the getter and setter of the avatarID
     */
    @Test
    public void testSetGetAvatarID() {
        testPlayer.setAvatarID(5);
        Assert.assertEquals(5, testPlayer.getAvatarID());
    }

    /**
     * This method is used to compare the victoryPoints and coins of two players to decide who won the game or if
     * both of them win
     */
    @Test
    public void testCompareTo() {
        ArrayList<Card> poor = new ArrayList<>();
        ArrayList<Card> rich = new ArrayList<>();

        poor.add(cardPirate);
        for(int i = 0; i <= 10; i++){
            rich.add(cardPirate);
        }

        testPlayer.setVictoryPoints(12);
        testPlayer.setGoldCards(poor);

        Player noob = new Player();
        noob.setVictoryPoints(10);
        noob.setGoldCards(poor);

        Player richy = new Player();
        richy.setVictoryPoints(12);
        richy.setGoldCards(rich);

        Assert.assertEquals(-1, testPlayer.compareTo(noob));
        Assert.assertEquals(1, noob.compareTo(testPlayer));
        Assert.assertEquals(-1, richy.compareTo(testPlayer));
        Assert.assertEquals(1, testPlayer.compareTo(richy));
        Assert.assertEquals(0, testPlayer.compareTo(testPlayer));
    }
}
