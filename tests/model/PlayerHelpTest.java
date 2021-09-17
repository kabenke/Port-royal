package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * test class to test the methods of PlayerHelp.
 */
public class PlayerHelpTest {

    /**
     * PlayerHelp object to test with
     */
    private PlayerHelp playerHelp;

    /**
     * Player object to test with
     */
    private Player player;

    /**
     * setup to initialize objects to test with
     * @throws Exception if something goes wrong
     */
    @Before
    public void setup() throws Exception{
        player = new Player();
        playerHelp = player.getPlayerHelp();
    }

    /**
     * testing whether the hand cards get counted properly
     */
    @Test
    public void testGetCardCounts() {
        int[] empty = {0, 0, 0, 0, 0, 0, 0, 0};
        int[] some = {1, 0, 0, 1, 1, 1, 0, 0};
        int[] all = {1, 1, 1, 1, 1, 1, 1, 1};
        int[] someDouble = {2, 1, 2, 2, 2, 1, 1, 1};
        for(int i = 0; i < empty.length; i++) {
            assertEquals(empty[i], playerHelp.getCardCounts()[i]);
        }
        Person joker = new Person(1,1, Ability.JOKER, 1, Color.UNDEFINED);
        Person governor = new Person(1,1, Ability.GOVERNOR, 1, Color.UNDEFINED);
        Person settler = new Person(1,1, Ability.SETTLER, 1, Color.UNDEFINED);
        Person mademoiselle = new Person(1,1, Ability.MADEMOISELLE, 1, Color.UNDEFINED);
        joker.flip();
        governor.flip();
        settler.flip();
        mademoiselle.flip();
        player.addCard(joker);
        player.addCard(governor);
        player.addCard(settler);
        player.addCard(mademoiselle);
        for(int i = 0; i < some.length; i++) {
            assertEquals(some[i], playerHelp.getCardCounts()[i]);
        }
        Person captain = new Person(1,1, Ability.CAPTAIN, 1, Color.UNDEFINED);
        Person priest = new Person(1,1, Ability.PRIEST, 1, Color.UNDEFINED);
        Person jester = new Person(1,1, Ability.JESTER, 1, Color.UNDEFINED);
        Person admiral = new Person(1,1, Ability.ADMIRAL, 1, Color.UNDEFINED);
        captain.flip();
        priest.flip();
        jester.flip();
        admiral.flip();
        player.addCard(captain);
        player.addCard(priest);
        player.addCard(jester);
        player.addCard(admiral);
        for(int i = 0; i < all.length; i++) {
            assertEquals(all[i], playerHelp.getCardCounts()[i]);
        }
        player.addCard(joker);
        player.addCard(priest);
        player.addCard(mademoiselle);
        player.addCard(settler);
        for(int i = 0; i < someDouble.length; i++) {
            assertEquals(someDouble[i], playerHelp.getCardCounts()[i]);
        }
    }

    /**
     * testing getterAndSetter for player
     */
    @Test
    public void testGetAndSetPlayer() {
        Player localPlayer = new Player();
        PlayerHelp localPlayerHelp = new PlayerHelp(new Player());
        localPlayerHelp.setPlayer(localPlayer);
        assertEquals(localPlayer, localPlayerHelp.getPlayer());
    }
}
