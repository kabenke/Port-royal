package controller;
import model.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class is used to test the methods of the PlayerController
 */
public class PlayerControllerTest {

    private MainController mainController;
    private PlayerController playerController;
    private Player player;

    /**
     * Creates a new test environment before each test method
     * @throws Exception initialise all Tests
     */
    @Before
    public void setUp() throws Exception{
        this.mainController = new MainController();
        this.playerController = new PlayerController(mainController);
        this.player = new Player();
    }

    /**
     * This method is used to test the NotEligible method
     */
    @Test
    public void testNotEligible() {
        Assert.assertTrue(player.getEligibleForHighscore());
        playerController.notEligible(player);
        Assert.assertFalse(player.getEligibleForHighscore());
    }

    /**
     * This method is used to test the setMainController method
     */
    @Test
    public void testSetMainController() {
        playerController.setMainController(mainController);
        Assert.assertEquals(mainController, playerController.getMainController());
    }
}