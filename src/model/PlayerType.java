package model;

import java.io.Serializable;

/**
 * This enum is used to assign a PlayerType to a player. Quick Links:
 * {@link #HUMAN}
 * {@link #EASY_AI}
 * {@link #NORMAL_AI}
 * {@link #HARD_AI}
 */
public enum PlayerType implements Serializable {

    /**
     * This is a human player.
     */
    HUMAN,

    /**
     * This is the AI with difficulty level easy.
     */
    EASY_AI,

    /**
     * This is the AI with difficulty level normal.
     */
    NORMAL_AI,

    /**
     * This is the AI with difficulty level hard.
     */
    HARD_AI;

}
