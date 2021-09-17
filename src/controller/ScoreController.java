package controller;

import abstractuserinterface.ScoreAUI;
import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * This class is used to manage score of all players.
 */
public class ScoreController {

	/**
	 * The Main Controller
	 */
	private MainController mainController;

	/**
	 * The ScoreAUI
	 */
	private ScoreAUI scoreAUI;

	/**
	 * Constructor for the ScoreController
	 * @param mainController Main Controller
	 */
	public ScoreController(MainController mainController) {
		this.mainController = mainController;
	}

	/**
	 * Calculate the score of the player
	 * high score = victory points * 10 + gold + gold of the PersonCards in players hand + swards of the PersonCards in players hand
	 *              - 0,5 * all the turns the player has made during the game
	 * @param score - the score of the player
	 */
	public void calculateScore(Score score) {
		Player player = score.getPlayer();
		int victoryPoints = player.getVictoryPoints();
		int moves = player.getMoveCount();
		int gold = player.getGoldCards().size();
		int swords = player.getSwords();
		double total = 0.0;

		for (int i = 0; i < player.getNonGoldCards().size(); i++){
			Card card = player.getNonGoldCards().get(i);
			if (card instanceof Person) {
				gold -= card.getCoins();
			}
		}
		if (player.getEligibleForHighscore()) {
			total = 10 * victoryPoints + gold + 3 * swords - 0.5 * moves;
			final double ZERO_VALUE = 0.0;
			if (total < ZERO_VALUE) {
				total = 0.0;
			}
			score.setScore((int) total);
		}
	}

	/**
	 * method to add a score to the highscore list
	 * @param score score to add to the highscore list
	 */
	public void addScoreToHighscores(Score score){
		ArrayList<Score> highscores = mainController.getPortRoyal().getHighscore();
		highscores.add(score);
		Collections.sort(highscores);
		final int MAX_SCORES = 10;
		while(highscores.size() > MAX_SCORES){
			highscores.remove(highscores.size()-1);
		}
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

	/**
	 * Get-Method for the ScoreAUI
	 * @return scoreAUI
	 */
	public ScoreAUI getScoreAUI() {return scoreAUI;}

	/**
	 * Set-Method for the ScoreAUI
	 * @param scoreAUI scoreAUI
	 */
	public void setScoreAUI(ScoreAUI scoreAUI) {this.scoreAUI = scoreAUI;}
}
