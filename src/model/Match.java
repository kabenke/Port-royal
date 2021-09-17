package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

/**
 * This class represents a match.
 */
public class Match implements Serializable, Comparable<Match> {

	/**
	 * the pile from which cards will be drawn
	 */
	private ArrayList<Card> drawPile;

	/**
	 * all drawn expedition cards
	 */
	private ArrayList<Expedition> expeditionDisplay;

	/**
	 * the determined initial card order
	 */
	private ArrayList<Card> initialCardOrder;

	/**
	 * the speed of the simulation, if it is a simulation
	 */
	private int aiSpeed;

	/**
	 * Previous turns
	 */
	private static Stack<Turn> undoTurnHistory = new Stack<>();

	/**
	 * the list of players
	 */
	private ArrayList<Player> playerList;

	/**
	 * the history containing all made turns
	 */
	private ArrayList<Turn> turnHistory;

	/**
	 * the pile to which cards will be discarded
	 */
	private ArrayList<Card> discardPile;

	/**
	 * the player that was the active player in the last turn
	 */
	private Player activePlayerInLastTurn;

	/**
	 * provides helping methods
	 */
	private MatchHelp matchHelp;

	/**
	 * the shuffletype
	 */
	private boolean randomShuffle;

	/**
	 * the timestamp of the match
	 */
	private Date timestamp;

	/**
	 * Constructor that creates a new Match
	 */
	public Match() {
		this.playerList = new ArrayList<>();
		this.turnHistory = new ArrayList<>();
		this.discardPile = new ArrayList<>();
		this.drawPile = new ArrayList<>();
		this.expeditionDisplay = new ArrayList<>();
		this.aiSpeed = 5;
		this.initialCardOrder = new ArrayList<>();
		this.matchHelp = new MatchHelp(this);
		this.timestamp = new Date(System.currentTimeMillis());
	}

	/**
	 * adds a card to the discard pile
	 * @param card the card to be added to the discard pile
	 */
	public void toDiscardPile(Card card) throws IllegalArgumentException{
		discardPile.add(0, card); /* adds the given card at the beginning of the list */
	}

	/**
	 * adds a card from the draw pile to the harbour display in the given turn
	 * @param turn the turn that the card will be drawn to
	 * @throws IllegalArgumentException if the draw pile is empty
	 */
	public void drawFromPile(Turn turn) {
		if(this.drawPile.isEmpty()) {
			throw new IllegalArgumentException("Draw pile is empty");
		}
		Card drawnCard = this.drawPile.remove(0);
		turn.getTurnHelp().addToHarborDisplay(drawnCard);
	}

	/**
	 * removes a card from the draw pile and returns it
	 * @return the card that got removed
	 */
	public Card drawGoldCard() {
		if(this.drawPile.isEmpty()) {
			throw new IllegalArgumentException("draw pile is empty");
		}
		return this.drawPile.remove(0);
	}

	/**
	 * clones the state of the object and returns it
	 * @return a new object containing the state of the calling object
	 */
	@Override
	public Match clone() {
		Match clonedMatch = new Match();
		ArrayList<Card> clonedDiscardPile = new ArrayList<>();
		ArrayList<Card> clonedDrawPile = new ArrayList<>();
		ArrayList<Expedition> clonedExpeditionDisplay = new ArrayList<>();
		ArrayList<Turn> clonedTurnHistory = new ArrayList<>();

		for(Card card : this.discardPile) { /* clones every card from the discard pile */
			clonedDiscardPile.add(card.clone());
		}
		clonedMatch.setDiscardPile(clonedDiscardPile);

		for(Card card : this.drawPile) { /* clones every card from the draw pile */
			clonedDrawPile.add(card.clone());
		}
		clonedMatch.setDrawPile(clonedDrawPile);

		for(Expedition expedition : this.expeditionDisplay) { /* clones every card from the expedition display */
			clonedExpeditionDisplay.add(expedition.clone());
		}
		clonedMatch.setExpeditionDisplay(clonedExpeditionDisplay);

		for(Player player : this.playerList) { /* clones every player from the player list */
			Player clone = player.clone();
			clonedMatch.getMatchHelp().addPlayer(clone);
			if(player.equals(this.activePlayerInLastTurn)) {
				clonedMatch.setActivePlayerInLastTurn(clone);
			}
		}

		clonedMatch.aiSpeed = this.aiSpeed; /* sets the aiSpeed */

		clonedMatch.setInitialCardOrder(this.initialCardOrder);

		for(Turn turn : this.turnHistory) { /* clones every turn from the turn history */
			clonedTurnHistory.add(turn.clone(clonedMatch));
		}
		clonedMatch.setTurnHistory(clonedTurnHistory);

		return clonedMatch;
	}

	/**
	 * get-Method drawPile
	 * @return the ArrayList that represents the drawPile
	 */
	public ArrayList<Card> getDrawPile() {
		return drawPile;
	}

	/**
	 * set-Method drawPile
	 * @param drawPile the ArrayList to set the drawPile to
	 */
	public void setDrawPile(ArrayList<Card> drawPile) {
		this.drawPile = drawPile;
	}

	/**
	 * get-Method expeditionDisplay
	 * @return the ArrayList that represents the expeditionDisplay
	 */
	public ArrayList<Expedition> getExpeditionDisplay() {
		return expeditionDisplay;
	}

	/**
	 * set-Method expeditionDisplay
	 * @param expeditionDisplay the ArrayList to set the expeditionDisplay to
	 */
	public void setExpeditionDisplay(ArrayList<Expedition> expeditionDisplay) {
		this.expeditionDisplay = expeditionDisplay;
	}

	/**
	 * get-Method initialCardOrder
	 * @return the ArrayList that contains the initial card order
	 */
	public ArrayList<Card> getInitialCardOrder() {
		return initialCardOrder;
	}

	/**
	 * set-Method drawPile
	 * @param initialCardOrder the card order that the draw pile should have initially
	 */
	public void setInitialCardOrder(ArrayList<Card> initialCardOrder) {
		this.initialCardOrder = initialCardOrder;
	}

	/**
	 * get-Method aiSpeed
	 * @return the simulation speed if the game is a simulation
	 */
	public int getAiSpeed() {
		return aiSpeed;
	}

	/**
	 * set-Method aiSpeed
	 * @param aiSpeed a value between 0 and 10 to represent the speed of the simulation
	 */
	public void setAiSpeed(int aiSpeed) {
		this.aiSpeed = aiSpeed;
	}

	/**
	 * get-Method playerList
	 * @return the ArrayList that contains all the players of the match
	 */
	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	/**
	 * set-Method playerList
	 * @param playerList the ArrayList of players to add to the match
	 */
	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

	/**
	 * get-Method turnHistory
	 * @return the ArrayList of all turns that have been made in the match
	 */
	public ArrayList<Turn> getTurnHistory() {
		return turnHistory;
	}

	/**
	 * set-Method turnHistory
	 * @param turnHistory the ArrayList containing turns to represent the turn history
	 */
	public void setTurnHistory(ArrayList<Turn> turnHistory) {
		this.turnHistory = turnHistory;
	}

	/**
	 * get-Method discardPile
	 * @return the ArrayList of cards that represents the discard pile
	 */
	public ArrayList<Card> getDiscardPile() {
		return discardPile;
	}

	/**
	 * set-Method discardPile
	 * @param discardPile the ArrayList of Cards to represent the discard pile
	 */
	public void setDiscardPile(ArrayList<Card> discardPile) {
		this.discardPile = discardPile;
	}

	/**
	 * get-Method activePlayerInLastTurn
	 * @return the Player that was active in the last Turn of the history
	 */
	public Player getActivePlayerInLastTurn() {
		return activePlayerInLastTurn;
	}

	/**
	 * set-Method activePlayerInLastTurn
	 * @param activePlayerInLastTurn the Player that was active in the last Turn of the turn history
	 */
	public void setActivePlayerInLastTurn(Player activePlayerInLastTurn) {
		this.activePlayerInLastTurn = activePlayerInLastTurn;
	}

	/**
	 * get-Method matchHelp
	 * @return the matchHelp object
	 */
	public MatchHelp getMatchHelp() {
		return matchHelp;
	}

	/**
	 * set-Method matchHelp
	 * @param matchHelp the object to set to
	 */
	public void setMatchHelp(MatchHelp matchHelp) {
		this.matchHelp = matchHelp;
	}

	/**
	 * Get-Method for the active Turn in the turnHistory
	 * @return return the active turn in the history
	 */
	public Turn getActiveTurn(){
		return turnHistory.get(turnHistory.size()-1);
	}

	/**
	 * set-Method for the shuffletype
	 * @param randomShuffle the shuffletype
	 */
	public void setRandomShuffle(boolean randomShuffle){ this.randomShuffle = randomShuffle; }

	/**
	 * Get-Method for the shuffletype
	 * @return return the shuffletype of the match
	 */
	public boolean getRandomShuffle(){ return this.randomShuffle; }

	/**
	 * Get-Method for the undoTurnHistory Stack
	 * @return the Stack that contains the undoTurnHistory
	 */
	public Stack<Turn> getUndoTurnHistory() { return undoTurnHistory; }

	/**
	 * Set-Method for the undoTurnHistory Stack
	 * @param undoTurnHistory Stack for the undoTurnHistory
	 */
	public void setUndoTurnHistory(Stack<Turn> undoTurnHistory) { this.undoTurnHistory = undoTurnHistory; }

	/**
	 * Set-Method for the timestamp of the match
	 * @return the timestamp
	 */
	public Date getTimestamp(){
		return this.timestamp;
	}

	/**
	 * Get-Method for the timestamp of the match
	 * @param timestamp the timestamp
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Method to compare the timestaps to sort the match list
	 * @param other match to compare
	 * @return returns if the timestamp is before or after
	 */
	@Override
	public int compareTo(Match other){
		if(this.getTimestamp().after(other.getTimestamp())){ return -1; }
		else if(this.getTimestamp().before(other.getTimestamp())){ return 1; }
		return 0;
	}
}
