package controller;

import model.Player;

/**
 * This class provides methods to control player logic.
 */
public class PlayerController {

	/**
	 * The MainController
	 */
	private MainController mainController;

	/**
	 * constructor that instantiates attributes
	 * @param mainController the mainController
	 */
	public PlayerController(MainController mainController) {
		this.mainController = mainController;
	}

	/**
	 * flips the boolean value of eligibleForHighscore of a given player
	 * @param player the player of whom to flip the attribute
	 */
	public void notEligible(Player player) {
		player.setEligibleForHighscore(false);
	}

	/**
	 * Get-Method for the mainController
	 * @return mainController
	 */
	public MainController getMainController() { return mainController; }

	/**
	 * Set-Method for the mainController
	 * @param mainController mainController
	 */
	public void setMainController(MainController mainController) { this.mainController = mainController; }
}
