package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a person card in the game.
 */

public class Person extends Card implements Serializable {

	/**
	 * The swords on a Person card
	 */
	private int swords;

	/**
	 * The ability of a Person card
	 */
	private Ability ability;

	/**
	 * The victoryPoints of a Person card
	 */
	private int victoryPoints;

	/**
	 * The color of a Person card
	 */
	private Color color;

	/**
	 * Constructor for the Person
	 * @param coins the coin costs
	 * @param swords the swords if it is a
	 * @param ability the artifacts
	 * @param victoryPoints if necessary a comment
	 * @param color color of ships or trader
	 */
	public Person(int coins, int swords, Ability ability, int victoryPoints, Color color) {
			this.coins = coins;
			this.swords = swords;
			this.ability = ability;
			this.victoryPoints = victoryPoints;
			this.color = color;
			this.isFaceUp = false;
	}

	/**
	 * checks if a player can afford new cards
	 * @param player gives the player to check
	 * @return gives true for he can Afford
	 */
	public boolean canAfford(Player player)throws IllegalArgumentException {

		if(player == null){
			throw new IllegalArgumentException();
		}

		int mademoiselle = 0;
		ArrayList<Card> crew = player.getNonGoldCards();
		for(Card card : crew){
			if(card instanceof Person && ((Person)card).getAbility().equals(Ability.MADEMOISELLE)){
				mademoiselle += 1;
			}

		}

		if(player.isActive){
			return player.getGoldCards().size() + coins + mademoiselle >= 0;
		}
		return player.getGoldCards().size() + coins + mademoiselle > 0;
	}

	/**
	 * Clone method for a Person card
	 * @return clone the Person for the history
	 */
	public Person clone() {
		Person person = new Person(	this.coins, this.swords, this.ability ,this.victoryPoints, this.color);
		person.isFaceUp = this.isFaceUp;
		return person;
	}


	/**
	 * gives access to the ability of the card
	 * @return the ability of the Person
	 */
	public Ability getAbility(){
		return ability;
	}

	/**
	 * can set the ability
	 * @param ability new ability
	 */
	public void setAbility(Ability ability){
		this.ability = ability;
	}

	/**
	 * Get-Method for the swords of a Person
	 * @return swards
	 */
	public int getSwords() {return swords; }

	/**
	 * Get-Method for the VictoryPoints of a Person
	 * @return victoryPoints
	 */
	public int getVictoryPoints(){return  victoryPoints;}

	/**
	 * Set-Method for the swords of a Person
	 * @param swords to setSwords
	 */
	public void setSwords(int swords) {
		this.swords = swords;
	}

	/**
	 * Set-Method for the VictoryPoints of a Person
	 * @param victoryPoints access to victory Points of the Person
	 */
	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}

	/**
	 * Get-Method for the color of a Person
	 * @return the color of a person(trader)
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Set-Method for the color of a Person
 	 * @param color set the color of a person(trader)
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Converts a Person card to a String
	 * @return returns the card as a String
	 */
	@Override
	public String toString(){
		String str = getAbility()+": "+getCoins()+"C "+getVictoryPoints()+"V ";
		if(getAbility().equals(Ability.PIRATE) || getAbility().equals(Ability.SAILOR)){
			str += getSwords()+"S ";
		}
		if(!getColor().equals(Color.UNDEFINED)){
			str += getColor();
		}
		return str;
	}
}
