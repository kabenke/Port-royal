package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class provides helping methods for a Turn.
 */
public class TurnHelp implements Serializable {

    /**
     * the turn to help
     */
    private Turn turn;

    /**
     * contains the amount of colored ships
     */
    private int[] colorsOfShips;

    /**
     * contains the lastDrawn card of the turn
     */
    private Card lastDrawnCard;


    /**
     * constructor that creates a TurnHelp object for a given turn
     * @param turn the turn to help
     */
    public TurnHelp(Turn turn) {
        this.turn = turn;
        this.colorsOfShips = new int[5];
    }

    /**
     * returns whether the turns harbourDisplay is valid
     * @return true if the display is valid, false otherwise
     */
    public boolean validHarborDisplay() {
        this.colorsOfShips = new int[5];
        for(Card card : this.turn.getHarbourDisplay()) {
            if (card instanceof Ship) {
                switch (((Ship) card).getColor()) {
                    case BLUE:
                        this.colorsOfShips[0]++;
                        break;

                    case RED:
                        this.colorsOfShips[1]++;
                        break;

                    case GREEN:
                        this.colorsOfShips[2]++;
                        break;

                    case BLACK:
                        this.colorsOfShips[3]++;
                        break;

                    case YELLOW:
                        this.colorsOfShips[4]++;
                }
            }
        }
        return checkValid(this.colorsOfShips);
    }

    /**
     * helps to check whether a ship color is present more than once
     * @param colorsOfShips the int array to check
     * @return true if the display is valid, false otherwise
     */
    private boolean checkValid(int[] colorsOfShips) {
        for(int amount : colorsOfShips) {
            if(amount > 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * returns an ArrayList containing all players who are tied for maxSwords
     * @return the ArrayList containing all players who are tied for maxSwords
     */
    public ArrayList<Player> maxSwords() {
        int maxSwords = 0;
        ArrayList<Player> maxSwordPlayers = new ArrayList<>();
        for(Player player : this.turn.getPlayerList()) {
            if(player.getSwords() > maxSwords) {
                maxSwords = player.getSwords();
            }
        }
        for(Player player : this.turn.getPlayerList()) {
            if(player.getSwords() == maxSwords) {
                maxSwordPlayers.add(player);
            }
        }
        return maxSwordPlayers;
    }

    /**
     * returns an ArrayList containing all players who are tied for minimum victoryPoints
     * @return the ArrayList containing all players who are tied for minimum victoryPoints
     */
    public ArrayList<Player> minVictoryPoints() {
        int minVictoryPoints = 100; 	/* big value that replace infinity */
        ArrayList<Player> minVictoryPointsPlayers = new ArrayList<>();
        for(Player player : this.turn.getPlayerList()) {
            if(player.getVictoryPoints() < minVictoryPoints) {
                minVictoryPoints = player.getVictoryPoints();
            }
        }
        for(Player player : this.turn.getPlayerList()) {
            if(player.getVictoryPoints() == minVictoryPoints) {
                minVictoryPointsPlayers.add(player);
            }
        }
        return minVictoryPointsPlayers;
    }

    /**
     * returns the next player that will be allowed to move
     * @return the next player that will be allowed to move, or null if no player is allowed to move
     */
    public Player nextPlayerToMove() {
        int indexOfActivePlayer = this.turn.getPlayerList().indexOf(this.turn.getActivePlayer());
        int indexOfNextPlayer;
        for(int i = indexOfActivePlayer; i < this.turn.getPlayerList().size(); i++) {
            if(this.turn.getPlayerMoveNumber()[i] != 0) {
                indexOfNextPlayer = i;
                return this.turn.getPlayerList().get(indexOfNextPlayer);
            }
        }
        for(int i = 0; i < indexOfActivePlayer; i++) {
            if(this.turn.getPlayerMoveNumber()[i] != 0) {
                indexOfNextPlayer = i;
                return this.turn.getPlayerList().get(indexOfNextPlayer);
            }
        }
        return null;
    }


    /**
     * returns the next player that will be allowed to move
     * @param player the active player
     * @return the next player that will be allowed to move, or null if no player is allowed to move
     */
    public Player nextPlayer(Player player) {
        int indexOfActivePlayer = this.turn.getPlayerList().indexOf(player);
        int indexOfNextPlayer = (indexOfActivePlayer+1)%this.turn.getPlayerList().size();

        return this.turn.getPlayerList().get(indexOfNextPlayer);

    }

    /**
     * generates the amount of legal moves each player has
     */
    public void generateMoveNumber() {
        int[] moveNumber = new int[turn.getPlayerList().size()];
        final int TWO_CARDS = 4;
        final int THREE_CARDS = 5;
        if(!validHarborDisplay()) {
            this.turn.setPlayerMoveNumber(moveNumber);
            return;
        }
        for(int i = 0; i < turn.getPlayerList().size(); i++) {
            Player player = turn.getPlayerList().get(i);
            int count = 1;
            if(player.equals(turn.getActivePlayer())) {
                int shipCount = 0;
                for(int j = 0; j < this.colorsOfShips.length; j++) {
                    if(this.colorsOfShips[j] != 0) {
                        shipCount++;
                    }
                }
                if(shipCount >= TWO_CARDS) {
                    count++;
                }
                if(shipCount == THREE_CARDS) {
                    count++;
                }
            }
            for(Card card : player.getNonGoldCards()) {
                if(card instanceof Person && ((Person) card).getAbility().equals(Ability.GOVERNOR)) {
                    count++;
                }
            }
            moveNumber[i] = count;
        }
        this.turn.setPlayerMoveNumber(moveNumber);
    }

    /**
     * moves a card from the harbourDisplay to the given players hand
     * @param player the given player
     * @param card the card to be removed from the display
     */
    public void cardToHand(Player player, Card card) {
        if(player == null || card == null) {
            throw new IllegalArgumentException("arguments cant be null");
        }
        if(!this.turn.getHarbourDisplay().contains(card)) {
            throw  new IllegalArgumentException("card isn't in the harbourDisplay");
        }
        this.turn.getHarbourDisplay().remove(card);
        player.addCard(card);
    }

    /**
     * adds a given card to the harbourDisplay of the turn
     * @param card the card to be added to the harbourDisplay
     */
    public void addToHarborDisplay(Card card) {
        if(card == null) {
            throw new IllegalArgumentException("card cant be null");
        }
        card.flip();
        this.turn.getHarbourDisplay().add(card);
        //matchzustand in den letzten move
    }

    /**
     * get-Method turn
     * @return the turn of this help object
     */
    public Turn getTurn() {
        return turn;
    }

    /**
     * set-Method turn
     * @param turn the turn of this help object
     */
    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    /**
     * get-Method colorsOfShips
     * @return the int array containing colors of the ships
     */
    public int[] getColorsOfShips() {
        return colorsOfShips;
    }

    /**
     * set-Method colorsOfShips
     * @param colorsOfShips the in array containing colors of the ships
     */
    public void setColorsOfShips(int[] colorsOfShips) {
        this.colorsOfShips = colorsOfShips;
    }

    /**
     * set-Method lastDrawnCard
     * @param lastDrawnCard the last card drawn in the turn
     */
    public void setLastDrawnCard(Card lastDrawnCard){ this.lastDrawnCard = lastDrawnCard; }

    /**
     * get-Method lastDrawnCard
     * @return lastDrawnCard
     */
    public Card getLastDrawnCard(){ return this.lastDrawnCard; }

}
