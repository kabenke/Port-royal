package model;

import java.io.Serializable;

/**
 * This class is used for the score of a player.
 */
public class Score implements Serializable, Comparable<Score> {

	/**
	 * The score of a player
	 */
	private int score;

	/**
	 * The player who owns the score
	 */
	private Player player;

	/**
	 * Constructor to create a score for a player
	 * @param player the player which reached the score
	 */
	public Score(Player player) {
		this.player = player;
	}

	/**
	 * get-Method Score
	 * @return score
	 */
	public int getScore() {return score;}

	/**
	 * set-Method Score
	 * @param score the reached Score
	 */
	public void setScore(int score){this.score = score;}

	/**
	 * get-Method Player
	 * @return player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * set-Method Player
	 * @param player the player that reached the score
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Compare method to sort the highscore list
	 * @param other Score object to compare with
	 * @return returns if its higher or lower
	 */
	@Override
	public int compareTo(Score other){
		return other.getScore() - this.getScore();
	}
}
