package model;

import java.io.Serializable;

/**
 * This is an abstract class that should ensure,
 * that all cards have a coin and a isFaceUp attribute.
 */
public abstract class Card implements Serializable {

	/**
	 * coins of a card
	 */
	protected int coins;

	/**
	 * boolean if the card is faceUp, true when faceUp and false when faceDown
	 */
	protected boolean isFaceUp;

	/**
	 * turn a Card around
	 */
	public void flip() {
		this.isFaceUp = !this.isFaceUp;
	}

	/**
	 * to ensure every card has a clone
	 * @return gives the clone of card back
	 */
	@Override
	public abstract Card clone();

	/**
	 * Get-Method if a card is face up, if not its probably a gold card
	 * @return true if face up, false if face down
	 */
	public boolean getIsFaceUp(){
		return this.isFaceUp;
	}

	/**
	 * Method to set a card faceUp or faceDown
	 * @param isFaceUp true for faceUp or false for faceDown
	 */
	public void setIsFaceUp(boolean isFaceUp) {
		this.isFaceUp = isFaceUp;
	}

	/**
	 * Get-Method for the coins of a card
	 * @return coins for each card
	 */
	public int getCoins() {
		return coins;
	}

	/**
	 * Set-Method for the coins of a card
	 * @param coins set the coins for a card, should be negative if the card costs coins to hire
	 */
	public void setCoins(int coins) {
		this.coins = coins;
	}
}
