package abstractuserinterface;

/**
 * AbstractUserInterface for the game
 */
public interface GameAUI {

	/**
	 * This method is used to refresh the harbor display
	 */
	public abstract void refreshHarborDisplay();

	/**
	 * This method is used to refresh the expeditions
	 */
	public abstract void refreshExpedition();

	/**
	 * This method is used to refresh the player attributes
	 */
	public abstract void refreshPlayerAttributes();

	/**
	 * This method is used to refresh both piles
	 */
	public abstract void refreshPiles();

}
