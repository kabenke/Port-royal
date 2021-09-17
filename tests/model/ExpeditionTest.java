package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

/**
 * This class is used to test the class Expedition and its methods
 */
public class ExpeditionTest {

    private Expedition expedition;
    private ArrayList<Ability>  artifacts;
    private Player player;

    /**
     * setup method to initialize object
     * @throws Exception if something goes wrong
     */
    @Before
    public void setUp() throws Exception {

        artifacts = new ArrayList<>();
        artifacts.add(Ability.CAPTAIN);
        artifacts.add(Ability.PRIEST);
        artifacts.add(Ability.SETTLER);
        player = new Player("player",PlayerType.HUMAN);
        expedition = new Expedition(3,artifacts , 4);
    }

    /**
     * Test of the contructor
     */
    @Test
    public void testExpeditionContructor() throws NullPointerException {

        Assert.assertNotNull(expedition);
    }

    /**
     * Test of getCoins
     *
     */
    @Test
    public void testGetCoins() {

        Assert.assertEquals(3, expedition.getCoins());
    }

    /**
     * Test of getVictoryPoints
     */
    @Test
    public void testGetVictoryPoints() {

        Assert.assertEquals(4, expedition.getVictoryPoints());
    }

    /**
     * Test of getArtifacts
     */
    @Test
    public void testGetArtifacts() {

        Assert.assertSame(artifacts,expedition.getArtifacts());
    }

    /**
     * Test of getisFaceup
     */
    @Test
    public void testGetIsFaceUp() {
        Assert.assertFalse(expedition.getIsFaceUp());
    }

    /**
     * Test of setCoins
     */
    @Test
    public void testSetCoins() {
        expedition.setCoins(2);
        Assert.assertEquals(2, expedition.getCoins());
    }

    /**
     * Test of setVictoryPoints
     */
    @Test
    public void testSetVictoryPoints() {
        expedition.setVictoryPoints(2);
        Assert.assertEquals(2,expedition.getVictoryPoints());
    }

    /**
     * Test for the toString method of Expedition
     */
    @Test
    public void testToString() {
        Assert.assertEquals("EXPEDITION: " + "3" + "C " + "4" + "V " + "Cap" + "Pri" + "Set",expedition.toString());
    }

    /**
     * Test of setArtifacts
     */
    @Test
    public void testSetArtifacts() {

        //the localExpedition contain the original artifact list
        Expedition localExpedition = new Expedition(1,artifacts,2);

        ArrayList<Ability> newArtifacts = new ArrayList<Ability>();
        newArtifacts.add(Ability.MADEMOISELLE);
        newArtifacts.add(Ability.PRIEST);

        //The artifact list haved been changed
        localExpedition.setArtifacts(newArtifacts);

        Assert.assertNotEquals(artifacts,localExpedition.getArtifacts());
    }
    /**
     * Test of canFufill
     * expedition
     * without player object
     */
    @Test
    (expected = IllegalArgumentException.class)
    public void testCantFulfillBecauseOfNullPointer()
    {
        Assert.assertFalse(expedition.canFulfill(null));
    }

    /**
     * Test of canFufill
     * with a player without
     * cards
     */
    @Test
    public void testCantFulfillExpedition()
    {

        Assert.assertFalse(expedition.canFulfill(player));
    }
    /**
     * Test of canFufill
     * with a player with
     * only jokers cards
     */
    @Test
    public void testCantFulfillExpeditionWithOneJoker()
    {
        Card cardJoker = new Person(2, 0, Ability.JOKER, 1, Color.UNDEFINED);

        ArrayList<Card> nonGoldCard = new ArrayList<Card>();
        nonGoldCard.add(cardJoker);

        Assert.assertFalse(expedition.canFulfill(player));
    }
    /**
     * Test of canFufill
     * with a player in
     * possession
     * of enough artefacts
     *
     */
    @Test
    public void testCanFulfillExpedition()
    {

        Card cardTrader = new Person(2, 0, Ability.TRADER, 1, Color.BLUE);
        Card cardJoker = new Person(2, 0, Ability.JOKER, 1, Color.UNDEFINED);
        Card cardPriest  = new Person(0, 0, Ability.PRIEST, 1, Color.UNDEFINED);
        Card cardSettler = new Person(1, 0, Ability.SETTLER, 1, Color.UNDEFINED);
        Card cardCaptain = new Person(2, 0, Ability.CAPTAIN, 1, Color.UNDEFINED);

        ArrayList<Card> nonGoldCard = new ArrayList<Card>();

        nonGoldCard.add(cardCaptain);
        nonGoldCard.add(cardSettler);
        nonGoldCard.add(cardTrader);
        nonGoldCard.add(cardPriest);
        nonGoldCard.add(cardJoker);

        player.setNonGoldCards(nonGoldCard);

        Assert.assertTrue(expedition.canFulfill(player));
    }


}
