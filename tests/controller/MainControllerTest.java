package controller;


import ai.AIController;
import model.PortRoyal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class is used to test the methods of the MainController
 */
public class MainControllerTest {

    private MainController mainController;
    private IOController ioController;
    private ParseController parseController;
    private MatchController matchController;
    private TurnController turnController;
    private PortRoyalController portRoyalController;
    private MoveController moveController;
    private AIController aiController;
    private PlayerController playerController;
    private ScoreController scoreController;
    private UndoRedoController undoRedoController;
    private PortRoyal portRoyal;

    /**
     * Creates a new test environment before each test method
     * @throws Exception Possible Exceptions with setUp
     */
    @Before
    public void setUp() throws Exception {
        this.mainController = new MainController();
        this.ioController = new IOController(mainController);
        this.parseController = new ParseController(mainController);
        this.matchController = new MatchController(mainController);
        this.turnController = new TurnController(mainController);
        this.portRoyalController = new PortRoyalController(mainController);
        this.moveController = new MoveController(mainController);
        this.aiController = new AIController(mainController);
        this.playerController = new PlayerController(mainController);
        this.scoreController = new ScoreController(mainController);
        this.undoRedoController = new UndoRedoController(mainController);
        this.portRoyal = new PortRoyal();
    }

    /**
     * This method is used to test the setIOController method
     */
    @Test
    public void testSetIOController() {
        mainController.setiOController(ioController);
        Assert.assertEquals(ioController, mainController.getIOController());
    }

    /**
     * This method is used to test the setParseController method
     */
    @Test
    public void testSetParseController() {
        mainController.setParseController(parseController);
        Assert.assertEquals(parseController, mainController.getParseController());
    }

    /**
     * This method is used to test the setMatchController method
     */
    @Test
    public void testSetMatchController() {
        mainController.setMatchController(matchController);
        Assert.assertEquals(matchController, mainController.getMatchController());
    }

    /**
     * This method is used to test the setTurnController method
     */
    @Test
    public void testSetTurnController() {
        mainController.setTurnController(turnController);
        Assert.assertEquals(turnController, mainController.getTurnController());
    }

    /**
     * This method is used to test the setPortRoyalController method
     */
    @Test
    public void testSetPortRoyalController() {
        mainController.setPortRoyalController(portRoyalController);
        Assert.assertEquals(portRoyalController, mainController.getPortRoyalController());
    }

    /**
     * This method is used to test the setMoveController method
     */
    @Test
    public void testSetMoveController() {
        mainController.setMoveController(moveController);
        Assert.assertEquals(moveController, mainController.getMoveController());
    }

    /**
     * This method is used to test the setAIController method
     */
    @Test
    public void testSetAIController() {
        mainController.setAIController(aiController);
        Assert.assertEquals(aiController, mainController.getAIController());
    }

    /**
     * This method is used to test the setPlayerController method
     */
    @Test
    public void testSetPlayerController() {
        mainController.setPlayerController(playerController);
        Assert.assertEquals(playerController, mainController.getPlayerController());
    }

    /**
     * This method is used to test the setScoreController method
     */
    @Test
    public void testSetScoreController() {
        mainController.setScoreController(scoreController);
        Assert.assertEquals(scoreController, mainController.getScoreController());
    }

    /**
     * This method is used to test the setUndoRedoController method
     */
    @Test
    public void testSetUndoRedoController() {
        mainController.setUndoRedoController(undoRedoController);
        Assert.assertEquals(undoRedoController, mainController.getUndoRedoController());
    }

    /**
     * This method is used to test the setPortRoyal method
     */
    @Test
    public void testSetPortRoyal() {
        mainController.setPortRoyal(portRoyal);
        Assert.assertEquals(portRoyal, mainController.getPortRoyal());
    }
}
