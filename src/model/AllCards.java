package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is a collection of all cards in the game.
 */
public class AllCards implements Serializable {

	/**
	 * ArrayList of all TaxRaise cards
	 */
	private ArrayList<TaxRaise> taxRaiseCards;

	/**
	 * ArrayList of all Expedition cards
	 */
	private ArrayList<Expedition> expeditionCards;

	/**
	 * ArrayList of all Person cards
	 */
	private ArrayList<Person> personCards;

	/**
	 * ArrayList of all Ship cards
	 */
	private ArrayList<Ship> shipCards;

	/**
	 * Constructor of AllCards
	 */
	public AllCards() {

	}

	/**
	 * get-Method TaxRaiseCards
	 * @return ArrayList of TaxRaiseCards
	 */
	public ArrayList<TaxRaise> getTaxRaiseCards() {
		return taxRaiseCards;
	}

	/**
	 * set-Method TaxRaiseCards
	 * @param taxRaiseCards ArrayList of TaxRaiseCards
	 */
	public void setTaxRaiseCards(ArrayList<TaxRaise> taxRaiseCards) {
		this.taxRaiseCards = taxRaiseCards;
	}

	/**
	 * get-Method ExpeditionCards
	 * @return ArrayList of ExpeditionCards
	 */
	public ArrayList<Expedition> getExpeditionCards() {
		return expeditionCards;
	}

	/**
	 * set-Method ExpeditionCards
	 * @param expeditionCards ArrayList of ExpeditionCards
	 */
	public void setExpeditionCards(ArrayList<Expedition> expeditionCards) {
		this.expeditionCards = expeditionCards;
	}

	/**
	 * get-Method PersonCards
	 * @return ArrayList PersonCards
	 */
	public ArrayList<Person> getPersonCards() {
		return personCards;
	}

	/**
	 * set-Method PersonCards
	 * @param personCards ArrayList PersonCards
	 */
	public void setPersonCards(ArrayList<Person> personCards) {
		this.personCards = personCards;
	}

	/**
	 * get-Method ShipCards
	 * @return ArrayList ShipCards
	 */
	public ArrayList<Ship> getShipCards() {
		return shipCards;
	}

	/**
	 * set-Method ShipCards
	 * @param shipCards ArrayList ShipCards
	 */
	public void setShipCards(ArrayList<Ship> shipCards) {
		this.shipCards = shipCards;
	}
}
