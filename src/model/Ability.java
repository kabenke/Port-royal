package model;

import java.io.Serializable;

/**
 * This enum is used to assign a Ability to a card. Quick Links:
 * {@link #SETTLER}
 * {@link #CAPTAIN}
 * {@link #PRIEST}
 * {@link #JOKER}
 * {@link #ADMIRAL}
 * {@link #TRADER}
 * {@link #GOVERNOR}
 * {@link #JESTER}
 * {@link #PIRATE}
 * {@link #SAILOR}
 * {@link #MADEMOISELLE}
 */
public enum Ability implements Serializable {
	/**
	 * This is the settler. This card is an artifact to fulfill expeditions.
	 */
	SETTLER, 

	/**
	 * This is the captain. This card is an artifact to fulfill expeditions.
	 */
	CAPTAIN, 

	/**
	 * This is the priest. This card is an artifact to fulfill expeditions.
	 */
	 PRIEST, 

	/**
	 * This is the joker. This card can be used as a Settler, Captain or a Priest to fulfill expeditions.
	 */
	 JOKER, 

	/**
	 * This is the admiral. If you have this card in your hand when there are five or more cards in the harbour display
	 * and it is your turn to take a card out of it you get 2 gold.
	 */
	 ADMIRAL, 

	/**
	 * This is the trader. If you have this card in your hand and you take a ship with the same color you get one more
	 * gold as displayed on the ship card.
	 */
	 TRADER, 

	/**
	 * This is the governor. If you have this card in your hand, you are able to draw one additional card every time
	 * it`s your turn.
	 */
	 GOVERNOR, 

	/**
	 * This is the jester. If you are currently not the active player and during Phase 2 it is your tun to take a card
	 * and there are no cards left to take, you get one coin for every jester in your hand.
	 * If the harbour display is invalid and the active player can´t repel the ship, every Player get´s one coin for
	 * each Jester in his hand.
	 */
	 JESTER, 

	/**
	 *This is the pirate. This card has two swords on it to repel ships.
	 */
	 PIRATE, 

	/**
	 *This is the sailor. This card has one sword on it to repel ships.
	 */
	 SAILOR, 

	/**
	 * This is the mademoiselle. This card discounts the cost of a Person by one for every Mademoiselle in the hand of
	 * a player. Cards can never cost less than zero with this card.
	 */
	 MADEMOISELLE;
}
