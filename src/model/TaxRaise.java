package model;

import java.io.Serializable;

/**
 * This class represents a TaxRaise card in the game.
 */
public class TaxRaise extends Card implements Serializable {

	/**
	 * boolean if the TaxRaise has maxSwords on it or minVP
	 */
	private boolean maxSwords;

	/**
	 * create a new object of the TaxRaise card
	 * @param maxSwords to differentiate between the maxSwords and min victoryPoints
	 */
	public TaxRaise(boolean maxSwords) {
		this.maxSwords = maxSwords;
		this.isFaceUp = false;
	}

	/**
	 * Method to clone a TaxRaise card
	 * @return return a copy of a TaxRaise Card for the history
	 */
	public TaxRaise clone(){
		TaxRaise taxRaise = new TaxRaise(this.maxSwords);
		taxRaise.isFaceUp = this.isFaceUp;
		return  taxRaise;
	}

	/**
	 * Set the boolean if the TaxRaise card has maxSwords on it or minVP
	 * @param maxSwords set the param maxSwords
	 */
	public void setMaxSwords(boolean maxSwords) {
		this.maxSwords = maxSwords;
	}

	/**
	 * Get the boolean of the TaxRaise if its maxSwords or minVP
	 * @return gives access to the maxSwords attribute which define the function of the TaxRaise card
	 */
	public boolean getMaxSwords(){
		return maxSwords;
	}

	/**
	 * Converts a TaxRaise card to a String
	 * @return return the card as a String
	 */
	@Override
	public String toString(){
		String str = "TAX RAISE: ";
		if(maxSwords){ str += "Max Sword"; }
		else{ str += "Min VP"; }
		return str;
	}

}
