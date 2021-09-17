package model;

import java.io.Serializable;

/**
 * This class represents a ship card in the game.
 */
public class Ship extends Card implements Serializable {

	/**
	 * The amount of swords of a ship card
	 */
	private int swords;

	/**
	 * The color of a ship card
	 */
	private Color color;

	/**
	 * Constructor of a ship card
	 * @param coins amount of coins you get from selling the ship
	 * @param swords amount of swords the ship has on his card
	 * @param color the color of the ship
	 */
	public Ship(int coins, int swords, Color color) {
		this.coins = coins;
		this.swords = swords;
		this.color = color;
		this.isFaceUp = false;
	}

	/**
	 * This method is used to determine if a player wins the fight against the drawn ship
	 * @param activePlayer the player who is drawing cards
	 * @return true when the active Player has enough swords to fight the ship
	 */
	public boolean fight(Player activePlayer) {
		if(activePlayer == null) {
			throw new IllegalArgumentException();
		}
		if(activePlayer.getSwords() >= swords){
			return true;
		}
		return false;
	}

	/**
	 * This method is used to clone a ship card
	 * @return clone of the ship
	 */
	@Override
	public Ship clone() {
		Ship shipClone = new Ship(this.coins, this.swords, this.color);
		shipClone.isFaceUp = this.isFaceUp;
		return shipClone;
	}

	/**
	 * get-Method coins attribute of a ship
	 * @return amount of coins
	 */

	public int getSwords(){
		return swords;
	}

	/**
	 * set-Method swords of a ship card
	 * @param swords swords
	 */
	public void setSwords(int swords){
		this.swords = swords;
	}

	/**
	 * get-Method color
	 * @return the ships color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * set-Method color
	 * @param color the color to be set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Converts a ship card to a String
	 * @return returns the card
	 */
	@Override
	public String toString(){
		return "SHIP: "+getColor()+" "+getCoins()+"C "+getSwords()+"S";
	}
}
