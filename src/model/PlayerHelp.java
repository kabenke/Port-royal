package model;

import java.io.Serializable;

/**
 * Helping class to prevent Player from becoming a god class.
 */
public class PlayerHelp implements Serializable {

    /**
     * player to help
     */
    private Player player;

    /**
     * constructor to create a help object for a Player
     * @param player the player to help
     */
    public PlayerHelp(Player player) {
        this.player = player;
    }

    /**
     * returns an int array, that contains the amounts of times a card is in the players hand
     * @return an int array, that contains the amounts of times a card is in the players hand
     */
    public int[] getCardCounts() {
        int[] cardCounts = new int[8];
        for(Card card : player.getNonGoldCards()) {
            if(card instanceof Person) {
                switch(((Person) card).getAbility()) {
                    case JOKER: cardCounts[0]++;
                        break;

                    case CAPTAIN: cardCounts[1]++;
                        break;

                    case PRIEST: cardCounts[2]++;
                        break;

                    case SETTLER: cardCounts[3]++;
                        break;

                    case MADEMOISELLE: cardCounts[4]++;
                        break;

                    case GOVERNOR: cardCounts[5]++;
                        break;

                    case JESTER: cardCounts[6]++;
                        break;

                    case ADMIRAL: cardCounts[7]++;
                        break;
                }
            }
        }
        return cardCounts;
    }

    /**
     * get-Method player
     * @return the player object that is being helped
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * set-Method player
     * @param player the player object to help
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
