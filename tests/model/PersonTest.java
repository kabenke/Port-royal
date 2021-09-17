package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Test class to test the methods of Person.
 */
public class PersonTest {

    private int swords,coins;
    private Person person, personPirate;
    private Person personCanAfford;
    private Person personCanNotAfford;
    private Person clonedPerson;

    private Ability ability;
    private Color color;
    private Player player, player2 ;

    private int victoryPoints;


    /**
     * setup to initialize objects to test on
     * @throws Exception if something goes wrong
     */
    @Before
    public void setUp() throws Exception{
        player=new Player("testPlayer",PlayerType.HUMAN);
        player2 = new Player("testPlayer2",PlayerType.HUMAN);
        player.isActive = true;
        ArrayList<Card> goldCards = new ArrayList<> ();
        Ship ship=new Ship(2,2,Color.RED);
        ship.setIsFaceUp(false);
        player.addCard(ship);
        player.addCard(ship);
        coins=1;
        swords=1 ;
        ability=Ability.SAILOR;
        color=Color.UNDEFINED;
        victoryPoints=2 ;


        person = new Person(coins,swords,ability,victoryPoints,color);
        personCanAfford=new Person(-2,0,Ability.TRADER,2,Color.RED);
        personCanNotAfford=new Person(-3,0,Ability.TRADER,2,Color.RED);
        personPirate = new Person(-7, 2, Ability.PIRATE, 2, Color.UNDEFINED);
    }

    /**
     * test of the constructor of Person
     */
    @Test
    public void personTest (){
        Assert.assertEquals(this.coins,person.getCoins());
        Assert.assertEquals(this.swords,person.getSwords());
        Assert.assertEquals(this.victoryPoints,person.getVictoryPoints());
        Assert.assertEquals(this.ability,person.getAbility());
        Assert.assertEquals(this.color,person.getColor());
    }

    /**
     * test of clone method of Person
      */
    @Test
    public void cloneTest (){
        clonedPerson = person.clone();
        Assert.assertNotEquals(clonedPerson,person);
        Assert.assertEquals(person.getAbility(), clonedPerson.getAbility());
        Assert.assertEquals(person.getCoins(), clonedPerson.getCoins());
        Assert.assertEquals(person.getSwords(),clonedPerson.getSwords());
        Assert.assertEquals(person.getIsFaceUp(), clonedPerson.getIsFaceUp());
        Assert.assertEquals(person.getAbility(),clonedPerson.getAbility());
        Assert.assertEquals(person.getVictoryPoints(),clonedPerson.getVictoryPoints());

    }
    /**
     * test for canAfford method when the person can be Afforded
     */
    @Test
    public void canAffordTest (){
        Assert.assertTrue(personCanAfford.canAfford(player));
    }

    /**
     * test for canAfford method when the person can not be Afforded
     */
    @Test
    public  void canNotAffordTest () {
        Assert.assertFalse(personCanNotAfford.canAfford(player2));
        Assert.assertFalse(personPirate.canAfford(player));
    }

    /**
     * test if adding is Object is null
     */
    @Test(expected=IllegalArgumentException.class)
    public void canAffordNullTest() {
        person.canAfford(null);

    }

    /**
     * test getCoins and setCoins methods of Person
     */
    @Test
    public void testGetSetCoins(){
        person.setCoins(5);
        Assert.assertEquals(5,person.getCoins());

    }

    /**
     * test getSwords and setSwards methods of Person
     */
    @Test
    public void testGetSetSwords(){
        person.setSwords(3);
        Assert.assertEquals(3,person.getSwords());
    }

    /**
     * test getVictoryPoints and setVictoryPoints of Person
     */
    @Test
    public void testGetVictorypoints() {
        person.setVictoryPoints(4);
        Assert.assertEquals(4,person.getVictoryPoints());
    }

    /**
     * test getColor and setColor methods of Person
     */
    @Test
    public void testColor (){
        person.setColor(Color.BLACK);
        Assert.assertTrue(person.getColor().equals(Color.BLACK));
    }

    /**
     * test getAbility and setAbility methods of Person
     */
    @Test
    public void testAbility(){
        person.setAbility(Ability.CAPTAIN);
        Assert.assertTrue(person.getAbility().equals(Ability.CAPTAIN));
    }

    /**
     * Test for toString method of Ship
     */
    @Test
    public void testToString(){
        Assert.assertEquals("SAILOR" + ": " + "1" + "C " + "2" + "V " + "1" + "S ",person.toString());
        Assert.assertEquals("TRADER" + ": " + "-2" + "C " + "2" + "V " + "RED",personCanAfford.toString());
        Assert.assertEquals("PIRATE" + ": " + "-7" + "C " + "2" + "V " + "2" + "S ",personPirate.toString());
    }
}
