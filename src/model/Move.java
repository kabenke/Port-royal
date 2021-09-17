package model;


import java.io.Serializable;

/**
 * This class represents a move that is a single card activity from a player.
 */
public class Move implements Serializable {

	/**
	 * a card of the game
	 */
	private Card card;

	/**
	 * boolean if the player is the active Player who drew cards from the drawPile
	 */
	private boolean playerIsActive;

	/**
	 * a player of the game
	 */
	private Player player;

	/**
	 * the match before the move
	 */
	private Match matchBeforeMove;

	/**
	 * the match after the move
	 */
	private Match matchAfterMove;

	/**
	 * player's to safe and restore the player attributes after undo/redo
	 */
	private Player playerUndo, playerRedo;


	/**
	 * Constructor for a move
	 * @param player the player who did the move
	 * @param card the card chosen
	 * @param playerIsActive for adding additional coins
	 * @param match the match we are in
	 */
	public Move(Player player, Card card, boolean playerIsActive, Match match) {
		this.player = player;
		this.card = card;
		this.playerIsActive = playerIsActive;
		this.matchBeforeMove = match;
	}

	/**
	 * Get-Method for a card in the move
	 * @return the card chosen in the move
	 */
	public Card getCard() {
		return card;
	}

	/**
	 * Set-Method for a card in the move
	 * @param card the card we want to set
	 */
	public void setCard(Card card) {
		this.card = card;
	}

	/**
	 * Get-Method if the player in the move is active
	 * @return the active player
	 */
	public boolean getPlayerIsActive() {
		return playerIsActive;
	}

	/**
	 * Set-Method to set the boolean if the player is active or not
	 * @param playerIsActive boolean if the player is the active player
	 */
	public void setPlayerIsActive(boolean playerIsActive) {
		this.playerIsActive = playerIsActive;
	}

	/**
	 * Get-Method for the player of a move
	 * @return the player of the move
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Set-Method for the player of a move
	 * @param player set the player for the move
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * get-Method matchBeforeMove
	 * @return matchBeforeMove
	 */
	public Match getMatchBeforeMove() {
		return matchBeforeMove;
	}

	/**
	 * set-Method matchBeforeMove
	 * @param matchBeforeMove the match before the move
	 */
	public void setMatchBeforeMove(Match matchBeforeMove) {
		this.matchBeforeMove = matchBeforeMove;
	}

	/**
	 * get-Method matchAfterMove
	 * @return the matchAfterMove
	 */
	public Match getMatchAfterMove() {
		return matchAfterMove;
	}

	/**
	 * set-Method matchAfterMove
	 * @param matchAfterMove the matchAfterMove
	 */
	public void setMatchAfterMove(Match matchAfterMove) {
		this.matchAfterMove = matchAfterMove;
	}

	/**
	 * get-Method PlayerUndo
	 * @return playerUndo
	 */
	public Player getPlayerUndo() {
		return playerUndo;
	}

	/**
	 * set-Method PlayerUndo
	 * @param playerUndo playerUndo
	 */
	public void setPlayerUndo(Player playerUndo) {
		this.playerUndo = playerUndo;
	}

	/**
	 * get-Method PlayerRedo
	 * @return playerRedo
	 */
	public Player getPlayerRedo() {
		return playerRedo;
	}

	/**
	 * set-Method PlayerRedo
	 * @param playerRedo playerRedo
	 */
	public void setPlayerRedo(Player playerRedo) {
		this.playerRedo = playerRedo;
	}
}
