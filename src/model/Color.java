package model;

import java.io.Serializable;

/**
 * This enum is used to assign a color to a card. Quick Links:
 * {@link #BLUE}
 * {@link #RED}
 * {@link #GREEN}
 * {@link #BLACK}
 * {@link #YELLOW}
 * {@link #UNDEFINED}
 */
public enum Color implements Serializable {

	/**
	 * This is a blue ship or trader.
	 */
	BLUE,

	/**
	 * This is a red ship or trader.
	 */
	RED,

	/**
	 * This is a green ship or trader.
	 */
	GREEN,

	/**
	 * This is a black ship or trader.
	 */
	BLACK,

	/**
	 * This is a yellow ship or trader.
	 */
	YELLOW,

	/**
	 * This is a person which is not a trader.
	 */
	UNDEFINED;
}
