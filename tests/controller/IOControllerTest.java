package controller;


import model.PortRoyal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;



/**
 * This class is used to test the methods of the IOController
 */
public class IOControllerTest {

    private MainController mainController;
    private PortRoyal portRoyal;
    private IOController ioController, ioController2;

    /**
     * Creates a new test environment before each test method
     * @throws Exception initialise all Tests
     */
    @Before
    public void setUp() throws Exception {
        this.mainController = new MainController();
        this.portRoyal = new PortRoyal();
        this.mainController.setPortRoyal(portRoyal);
        this.ioController = new IOController(mainController);
    }

    /**
     * This method is used to test the IOController when the save File does not exist
     */
    @Test
    public void testIOControllerSaveFileDoesNotExist(){
        this.ioController2 = new IOController(mainController);
        ioController2.load();
    }

    /**
     * This method is used to test the save and load methods of the IOController
     */
    @Test
    public void testIOControllerSaveAndLoad() {
        ioController.save();
        ioController.load();
    }

    /**
     * This method is used to test the failure of the IOController when loading
     */
    @Test
    public void testIOControllerLoadFails(){
        ioController.save();
        this.ioController = new IOController(null);
        ioController.load();
    }

    /**
     * This method is used to test the failure of the IOController when saving
     */
    @Test
    public void testIOControllerSaveFails(){
        ioController.setFail(true);
        ioController.save();
    }

    /**
     * This method is used to test the setMainController method
     */
    @Test
    public void testSetMainController() {
        ioController.setMainController(mainController);
        Assert.assertEquals(mainController, ioController.getMainController());
    }

    /**
     * This method is used to test the setFail method
     */
    @Test
    public void testSetFail() {
        ioController.setFail(true);
        Assert.assertTrue(ioController.isFail());
    }
}
