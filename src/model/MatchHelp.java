package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Helping class to help a Match object.
 */
public class MatchHelp implements Serializable {

    /**
     * match that is provided with help methods
     */
    private Match match;

    /**
     * if the alternative End should be used
     */
    private boolean alternativeEnd;

    /**
     * Constructor that creates a help object for a given match
     * @param match the match to provide with helping methods
     */
    public MatchHelp(Match match) {
        this.match = match;
    }

    /**
     * returns the player which will be the next active player and updates the activePlayerInLastTurn attribute
     * @return the player that will be active next
     */
    public Player nextActivePlayer() {
        if(!match.getPlayerList().isEmpty()) {
            int currentIndex = match.getPlayerList().indexOf(match.getActivePlayerInLastTurn());
            if(currentIndex == match.getPlayerList().size() - 1) { /* current player is the last player in the list*/
                this.match.setActivePlayerInLastTurn(this.match.getPlayerList().get(0));
            } else {
                this.match.setActivePlayerInLastTurn(this.match.getPlayerList().get(currentIndex + 1));
            }
        } else {
            throw new IllegalArgumentException("No players in the match yet");
        }
        return this.match.getActivePlayerInLastTurn();
    }

    /**
     * swaps two cards at given indices in the draw pile
     * @param index1 the index of the first card
     * @param index2 the index of the second card
     */
    public void swapCards(int index1, int index2) {
        if(index1 == index2) {
            throw new IllegalArgumentException("Indices cant be the same");
        }
        if(index1 < 0 || index1 > this.match.getDrawPile().size()-1 || index2 < 0 || index2 > this.match.getDrawPile().size()-1) {
            throw new IllegalArgumentException("Invalid indices");
        }
        if(index2 < index1) {
            int tmp = index2;
            index2 = index1;
            index1 = tmp;
        }
        Card firstCard = this.match.getDrawPile().remove(index1);
        Card secondCard = this.match.getDrawPile().remove(index2 - 1);
        this.match.getDrawPile().add(index1, secondCard);
        this.match.getDrawPile().add(index2, firstCard);
    }

    /**
     * swaps the discard and the draw pile
     */
    public void swapPiles() {
        ArrayList<Card> tempPile = this.match.getDiscardPile();
        this.match.setDiscardPile(this.match.getDrawPile());
        this.match.setDrawPile(tempPile);
    }

    /**
     * adds a player to the player list of the match
     * @param player the player that is to be added
     */
    public void addPlayer(Player player) {
        final int MAX_PLAYERS = 5;
        if(this.match.getPlayerList().size() < MAX_PLAYERS) {
            if(this.match.getPlayerList().isEmpty()) {
                this.getMatch().setActivePlayerInLastTurn(player);
            }
            this.match.getPlayerList().add(player);
        } else {
            throw new IllegalArgumentException("List already full");
        }
    }

    /**
     * get-Method match
     * @return the match
     */
    public Match getMatch() {
        return match;
    }

    /**
     * set-Method match
     * @param match the match
     */
    public void setMatch(Match match) {
        this.match = match;
    }

    /**
     * get-Method alternative end
     * @return if the alternative end should be used
     */
    public boolean getAlternativeEnd(){
        return this.alternativeEnd;
    }

    /**
     * set-Method alternative end
     * @param alternativeEnd alternative end
     */
    public void setAlternativeEnd(boolean alternativeEnd) {
        this.alternativeEnd = alternativeEnd;
    }

}
