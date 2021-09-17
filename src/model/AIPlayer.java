package model;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is a class that represents an AI-Player.
 */
public class AIPlayer extends Player implements Serializable {

	/**
	 * ArrayList to keep track of the remaining cards
	 */
	private ArrayList<Card> remainCards;

	/**
	 * ArrayList to keep track of the discarded cards
	 */
	private ArrayList<Card> discardedCards;

	/**
	 * Constructor that creates an AI-Player
	 * @param playerType the difficulty of the AI
	 */
	public AIPlayer(PlayerType playerType){
		this.name = "";
		this.playerType = playerType;
		this.moveCount = 0;
		this.goldCards = new ArrayList<>();
		this.nonGoldCards = new ArrayList<>();
		this.eligibleForHighscore = true;
		this.traderBonus = new int[5];
		this.victoryPoints = 0;
		this.didExp = false;
		this.swords = 0;
	}

	/**
	 * clones AIPlayer object
	 * @return a AIPlayer object that represents the current state of the calling one
	 */
	@Override
	public AIPlayer clone() {
		AIPlayer clonedPlayer = new AIPlayer(this.playerType);
		clonedPlayer.name = this.name;
		clonedPlayer.moveCount = this.moveCount;
		clonedPlayer.eligibleForHighscore = this.eligibleForHighscore;
		clonedPlayer.victoryPoints = this.victoryPoints;
		clonedPlayer.didExp = this.didExp;
		clonedPlayer.swords = this.swords;

		ArrayList<Card> clonedGoldCards = new ArrayList<>();
		for(Card card : goldCards) {
			clonedGoldCards.add(card.clone());
		}
		clonedPlayer.goldCards = clonedGoldCards;

		ArrayList<Card> clonedNonGoldCards = new ArrayList<>();
		for(Card card : nonGoldCards) {
			clonedNonGoldCards.add(card.clone());
		}
		clonedPlayer.nonGoldCards = clonedNonGoldCards;

		int[] clonedTraderBonus = new int[traderBonus.length];
		for(int i = 0; i < traderBonus.length; i++) {
			clonedTraderBonus[i] = traderBonus[i];
		}
		clonedPlayer.traderBonus = clonedTraderBonus;

		ArrayList<Card> clonedRemainCards = new ArrayList<>();
		for(Card card : remainCards) {
			clonedRemainCards.add(card.clone());
		}
		clonedPlayer.remainCards = clonedRemainCards;

		ArrayList<Card> clonedDiscardedCards = new ArrayList<>();
		for(Card card : remainCards) {
			clonedDiscardedCards.add(card.clone());
		}
		clonedPlayer.discardedCards = clonedDiscardedCards;

		return clonedPlayer;
	}

	/**
	 * get-Method remainCards
	 * @return the remainCards ArrayList
	 */
	public ArrayList<Card> getRemainCards() {
		return remainCards;
	}

	/**
	 * set-Method remainCards
	 * @param remainCards the List of remaining Cards
	 */
	public void setRemainCards(ArrayList<Card> remainCards) {
		this.remainCards = remainCards;
	}

	/**
	 * get-Method discardedCards
	 * @return the discardedCards ArrayList
	 */
	public ArrayList<Card> getDiscardedCards() {
		return discardedCards;
	}

	/**
	 * set-Method discardCards
	 * @param discardedCards the List of discarded Cards
	 */
	public void setDiscardedCards(ArrayList<Card> discardedCards) {
		this.discardedCards = discardedCards;
	}
}
