package controller;

import model.*;
import java.io.*;

/**
 * This class is used to Load and Save the moves.
 */
public class IOController {

    /**
     * save file for save/load
     */
    private static final File SAVE_FILE = new File("SAVE_FILE");


    /**
     * The Main controller
     */
    private MainController mainController;

    /**
     * Shows if save should work or not
     */
    private boolean fail;

    /**
     * Constructor for the main controller
     * @param mainController Constructor for the main controller
     */
    public IOController(MainController mainController) {
        this.mainController = mainController;
        this.fail = false;
    }

    /**
     * Ability to load the game state
     */
    public void load() {
        if (!SAVE_FILE.exists()){
            return;
        }
        try {
            ObjectInputStream stream = new ObjectInputStream(new FileInputStream(SAVE_FILE));
            PortRoyal portRoyal = (PortRoyal) stream.readObject();
            mainController.setPortRoyal(portRoyal);
            stream.close();

        } catch (Exception ioException) {
            ioException.printStackTrace();
            System.out.println("error while loading");
        }

    }

    /**
     * Ability to save the game state
     */
    public void save() {
        try{
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(SAVE_FILE));
            stream.writeObject(mainController.getPortRoyal());
            if(fail){
                throw new IOException();
            }
            stream.close();
        }
        catch (IOException ioException){
            ioException.printStackTrace();
            System.out.println("error while saving");
        }

    }

    /**
     * get-Method mainController
     * @return mainController
     */
    public MainController getMainController() {return mainController;}

    /**
     * set-Method mainController
     * @param mainController the mainController
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Get-Method fail
     * @return fail
     */
    public boolean isFail() {
        return fail;
    }

    /**
     * Set-Method fail
     * @param fail fail
     */
    public void setFail(boolean fail) {
        this.fail = fail;
    }
}
