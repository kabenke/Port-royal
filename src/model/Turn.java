package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

/**
 * This class represents a Turn in a Match.
 */

public class Turn implements Serializable {

	/**
	 * Array list of all cards displayed on the harbor
	 */
	private ArrayList<Card> harbourDisplay;

	/**
	 * The player who is doing the turn
	 */
	private Player activePlayer;

	/**
	 * The player who is acting
	 */
	private Player actingPlayer;

	/**
	 * Previous moves
	 */
	private static Stack<Move> undoHistory = new Stack<>();

	/**
	 * boolean whether the turn is in the first phase
	 */
	private boolean phaseDiscover;

	/**
	 * the amount of moves for each player
	 */
	private int[] playerMoveNumber;

	/**
	 * match that the turn is made in
	 */
	private Match match;

	/**
	 * ArrayList with all moves that are done in this turn
	 */
	private ArrayList<Move> moves;

	/**
	 * allCards (might be redundant)
	 */
	private AllCards allCards;

	/**
	 * ArrayList with all players
	 */
	private ArrayList<Player> playerList;

	/**
	 * helping object to avoid god class
	 */
	private TurnHelp turnHelp;

	/**
	 * Constructor that creates a new Turn object for an activePlayer and passive players
	 * @param playerList all players (aktiv and passiv)
	 * @param activePlayer the active player
	 * @param match the match we are in
	 */
	public Turn(ArrayList<Player> playerList, Player activePlayer, Match match) {
		this.playerList = playerList;
		this.activePlayer = activePlayer;
		this.moves = new ArrayList<>();
		this.match = match;
		this.playerMoveNumber = new int[playerList.size()];
		this.phaseDiscover = true;
		this.harbourDisplay = new ArrayList<>();
		this.turnHelp = new TurnHelp(this);
		this.actingPlayer = activePlayer;
	}

	/**
	 * changes the phase of the turn
	 */
	public void changePhase() {
		this.phaseDiscover = !this.phaseDiscover;
	}

	/**
	 * returns an object, that represents the current state of this Turn object
	 * @return an object, that represents the current state of this Turn object
	 * @param match match
	 */
	public Turn clone(Match match) {
		ArrayList<Card> clonedHarbourDisplay = new ArrayList<>();
		ArrayList<Player> clonedPlayerList = new ArrayList<>();
		ArrayList<Move> clonedMoves = new ArrayList<>();
		Stack<Move> clonedUndoHistory = new Stack<>();

		Player cloneActing = new Player();
		Player cloneActive = new Player();

		int activePlayerIndex = 0;
		int actingPlayerIndex = 0;

		clonedPlayerList = this.match.getPlayerList();

		for(Player player : playerList) {
			if(player.equals(activePlayer)) {
				activePlayerIndex = playerList.indexOf(player);
			}
			if(player.equals(actingPlayer)) {
				actingPlayerIndex = playerList.indexOf(player);
			}
		}

		cloneActive = clonedPlayerList.get(activePlayerIndex);
		cloneActing = clonedPlayerList.get(actingPlayerIndex);

		Turn clonedTurn = new Turn(clonedPlayerList, cloneActive, match);

		for(Card card : harbourDisplay) {
			clonedHarbourDisplay.add(card.clone());
		}
		clonedTurn.setHarbourDisplay(clonedHarbourDisplay);

		for(Move move : moves) {
			Move clone = new Move(move.getPlayer(), move.getCard(), move.getPlayerIsActive(), move.getMatchBeforeMove());

			clone.setPlayerRedo(move.getPlayerRedo());

			clone.setPlayerUndo(move.getPlayerUndo());

			clone.setMatchAfterMove(move.getMatchAfterMove());
			clonedMoves.add(clone);
		}
		clonedTurn.setMoves(clonedMoves);

		clonedTurn.setIsPhaseDiscover(this.phaseDiscover);

		int[] clonedPlayerMoveNumber = new int[playerMoveNumber.length];
		for(int i = 0; i < playerMoveNumber.length; i++) {
			clonedPlayerMoveNumber[i] = playerMoveNumber[i];
		}
		clonedTurn.setPlayerMoveNumber(clonedPlayerMoveNumber);

		clonedTurn.setUndoHistory(clonedUndoHistory);

		clonedTurn.setTurnHelp(new TurnHelp(clonedTurn));

		clonedTurn.setActingPlayer(cloneActing);

		clonedTurn.getTurnHelp().setLastDrawnCard(turnHelp.getLastDrawnCard());

		return clonedTurn;
	}

	/**
	 * adds a move for a given player and card to the move list
	 * @param player the given player that makes the move
	 * @param card the card the player chose for the move
	 */
	public void addMove(Player player, Card card) {
		if(player == null) {
			throw new IllegalArgumentException("the arguments cant be null");
		}
		if(card == null) {
			this.moves.add(new Move(player, card, player.equals(activePlayer), this.match.clone()));
		} else {
			this.moves.add(new Move(player, card, player.equals(activePlayer), this.match.clone()));
			player.setMoveCount(player.getMoveCount() + 1);
		}
	}

	/**
	 * get-Method harbourDisplay
	 * @return the ArrayList that represents the harbourDisplay
	 */
	public ArrayList<Card> getHarbourDisplay() {
		return harbourDisplay;
	}

	/**
	 * set-Method harbourDisplay
	 * @param harbourDisplay the ArrayList that represents the harbourDisplay
	 */
	public void setHarbourDisplay(ArrayList<Card> harbourDisplay) {
		this.harbourDisplay = harbourDisplay;
	}

	/**
	 * get-Method activePlayer
	 * @return the object that represents the activePlayer
	 */
	public Player getActivePlayer() {
		return activePlayer;
	}

	/**
	 * set-Method activePlayer
	 * @param activePlayer the object that represents the activePlayer
	 */
	public void setActivePlayer(Player activePlayer) {
		this.activePlayer = activePlayer;
	}

	/**
	 * get-Method undoHistory
	 * @return the stack that contains the undoHistory
	 */
	public Stack<Move> getUndoHistory() {
		return undoHistory;
	}

	/**
	 * set-Method undoHistory
	 * @param undoHistory the stack that contains the undoHistory
	 */
	public void setUndoHistory(Stack<Move> undoHistory) {
		this.undoHistory = undoHistory;
	}

	/**
	 * get-Method phaseDiscover
	 * @return the value of the boolean phaseDiscover
	 */
	public boolean getIsPhaseDiscover() {
		return phaseDiscover;
	}

	/**
	 * set-Method phaseDiscover
	 * @param phaseDiscover the value to set phaseDiscover to
	 */
	public void setIsPhaseDiscover(boolean phaseDiscover) {
		this.phaseDiscover = phaseDiscover;
	}

	/**
	 * get-Method playerMoveNumber
	 * @return the int-array that contains the player move numbers
	 */
	public int[] getPlayerMoveNumber() {
		return playerMoveNumber;
	}

	/**
	 * set-Method playerMoveNumber
	 * @param playerMoveNumber the int-array that contains the player move numbers
	 */
	public void setPlayerMoveNumber(int[] playerMoveNumber) {
		this.playerMoveNumber = playerMoveNumber;
	}

	/**
	 * get-Method match
	 * @return the Match object that the turn is being played in
	 */
	public Match getMatch() {
		return match;
	}

	/**
	 * set-Method match
	 * @param match the Match object that the turn is being played in
	 */
	public void setMatch(Match match) {
		this.match = match;
	}

	/**
	 * get-Method moves
	 * @return the ArrayList that contains all moves
	 */
	public ArrayList<Move> getMoves() {
		return moves;
	}

	/**
	 * set-Method moves
	 * @param moves the ArrayList that contains all moves
	 */
	public void setMoves(ArrayList<Move> moves) {
		this.moves = moves;
	}

	/**
	 * get-Method allCards
	 * @return allCards object
	 */
	public AllCards getAllCards() {
		return allCards;
	}

	/**
	 * set-Method allCards
	 * @param allCards an allCards object
	 */
	public void setAllCards(AllCards allCards) {
		this.allCards = allCards;
	}

	/**
	 * get-Method playerList
	 * @return the ArrayList that contains all players
	 */
	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	/**
	 * set-Method playerList
	 * @param playerList the ArrayList that contains all players
	 */
	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

	/**
	 * get-Method turnHelp
	 * @return the helping TurnHelp object
	 */
	public TurnHelp getTurnHelp() {
		return turnHelp;
	}

	/**
	 * set-Method turnHelp
	 * @param turnHelp the helping turnHelp object
	 */
	public void setTurnHelp(TurnHelp turnHelp) {
		this.turnHelp = turnHelp;
	}

	/**
	 * get-Method actingPlayer
	 * @return returns the acting Player
	 */
	public Player getActingPlayer(){
		return actingPlayer;
	}

	/**
	 * set-Method actingPlayer
	 * @param actingPlayer the acting Player
	 */
	public void setActingPlayer(Player actingPlayer){
		this.actingPlayer = actingPlayer;
	}


}
