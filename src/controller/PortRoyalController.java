package controller;

import abstractuserinterface.MenuAUI;
import model.Match;
import model.Player;
import model.Score;

/**
 * This class is used to manage the matchLists, scores and to add players to a match.
 */
public class PortRoyalController {

	/**
	 * The Main Controller
	 */
	private MainController mainController;

	/**
	 * The MenuAUI
	 */
	private MenuAUI menuAUI;

	/**
	 * Constructor for the PortRoyalController
	 * @param mainController Main Controller
	 */
	public PortRoyalController(MainController mainController) {
		this.mainController = mainController;
	}

	/**
	 * This method is used to add a new match to the game
	 * @param match the new match which gets added
	 */
	public void addMatch(Match match) {
		mainController.getPortRoyal().addMatch(match);
		match.setInitialCardOrder(mainController.getParseController().parseCsvToPile());
	}

	/**
	 * This method is used to remove a match from the game
	 * @param match the match to be removed
	 */
	public void removeMatch(Match match) {
		for(int i = 0; i < mainController.getPortRoyal().getMatchList().size(); i++){
			if(mainController.getPortRoyal().getMatchList().get(i).equals(match)){
				mainController.getPortRoyal().getMatchList().remove(i);
			}
		}
	}

	/**
	 * This method is used to add a score to the highscore
	 * Afterwards the highscore list gets sorted and the worst score gets removed
	 * @param score the score to be added to the highscore
	 */
	public void addScore(Score score) {
		int maxHighscoreSize = 10;
		if(mainController.getPortRoyal().getHighscore().size() == maxHighscoreSize) {
			mainController.getPortRoyal().addScore(score);
			mainController.getPortRoyal().sortScore();
			mainController.getPortRoyal().removeScore();
		}
		else {
			mainController.getPortRoyal().addScore(score);
		}
	}

	/**
	 * This method is used to add a player to a match
	 * @param player the player we want to add
	 */
	public void addPlayerToMatch(Player player) {
		mainController.getPortRoyal().getActiveMatch().getMatchHelp().addPlayer(player);
	}

	/**
	 * Get-Method for the MenuAUI
	 * @return MenuAUI
	 */
	public MenuAUI getMenuAUI() { return menuAUI; }

	/**
	 * Set-Method for the MenuAUI
	 * @param menuAUI MenuAUI
	 */
	public void setMenuAUI(MenuAUI menuAUI) { this.menuAUI = menuAUI; }
}
