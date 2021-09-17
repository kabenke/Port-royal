package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * This class is used to test the class ship and its methods.
 */
public class ShipTest{

    private Ship ship;
    private Player player;

    /**
     * setup method to initialize objects
     * @throws Exception if something goes wrong
     */
    @Before
    public void setup() throws Exception
    {
        player = new Player();
        ship = new Ship(2 , 3 , Color.RED);
    }

    /**
     * Test of the constructor
     */
    @Test
    public void testShipConstructor()
    {
         Ship localShip = new Ship(3,3,Color.RED);
         assertNotNull(localShip);
         assertEquals(Color.RED, localShip.getColor());
         assertEquals(3, localShip.getCoins());
         assertEquals(3, localShip.getSwords());
    }

    /**
     * Test of getColor
     */
    @Test
    public void testGetColor()
    {
        Ship localShip = new Ship(1, 1, Color.BLACK);
        localShip.setColor(Color.RED);
        Assert.assertEquals(Color.RED , localShip.getColor());
    }

    /**
     * Test of getCoins
     */
    @Test
    public void testGetCoins()
    {
        Ship localShip = new Ship(1, 2, Color.GREEN);
        localShip.setCoins(2);
        Assert.assertEquals(2, localShip.getCoins());
    }

    /**
     * Test of getSwords
     */
    @Test
    public void testGetSwords()
    {
        Ship localShip = new Ship(1, 2, Color.GREEN);
        localShip.setSwords(3);
        Assert.assertEquals(3, localShip.getSwords());
    }

    /**
     * Test of clone
     */
    @Test
    public void testShipClone()
    {
        Ship shipClone = ship.clone();
        Assert.assertEquals(ship.getCoins() , shipClone.getCoins());
        Assert.assertEquals(ship.getColor() , shipClone.getColor());
        Assert.assertEquals(ship.getSwords() , shipClone.getSwords());
        Assert.assertEquals(ship.getIsFaceUp() , shipClone.getIsFaceUp());
    }

    /**
     * Test of fight with a player with no swords
     */
    @Test
    public void testFightWithoutSwords()
    {
        Assert.assertFalse(ship.fight(player));
    }

    /**
     * Test of fight with sufficient swords
     */
    @Test
    public void testFightWithEnoughSwords()
    {
        player.setSwords(4);
        Assert.assertTrue(ship.fight(player));
    }

    /**
     * Test of fight with a player with insufficient swords
     */
    @Test
    public void testFightWithoutEnoughSwords()
    {
        player.setSwords(1);
        Assert.assertFalse(ship.fight(player));
    }

    /**
     * testing fight with argument given being null
     */
    @Test (expected = IllegalArgumentException.class)
    public void testFightPlayerNull() {
        ship.fight(null);
    }

    /**
     *  Test for toString method of Ship
     */
    @Test
    public void testToString() {
        Assert.assertEquals("SHIP: " + "RED " + "2" + "C " + "3" + "S",ship.toString());
    }
}
