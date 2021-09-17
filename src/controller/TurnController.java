package controller;

import abstractuserinterface.GameAUI;
import model.*;

import java.util.ArrayList;

/**
 * Class that provides methods to control the games Turn logic.
 */
public class TurnController {

	/**
	 * The MainController
	 */
	private MainController mainController;

	/**
	 * The GameAUI
	 */
	private GameAUI gameAUI;

	/**
	 * The match in that we want to control the turn logic
	 */
	private Match match;

	/**
	 * The TurnHelp method to assist the TurnController
	 */
	private TurnHelp turnHelp;

	/**
	 * constructor that instantiates attributes
	 * @param mainController mainController
	 */
	public TurnController(MainController mainController) {
		this.mainController = mainController;
	}

	/**
	 * change phase to trade and hire, generate the MoveNumbers for all Players and apply the admiral for the active
	 * player
	 * @param turn current turn
	 */
	public void changePhase(Turn turn) {
		turn.setIsPhaseDiscover(!turn.getIsPhaseDiscover());
		turn.getTurnHelp().generateMoveNumber();
		applyAdmiral(turn);
	}

	/**
	 * add a move to the current Turn
	 * @param player player how did the move
	 * @param card card he picks
	 * @param turn turn to add the move
	 */
	public void addMove(Player player, Card card, Turn turn) {
		turn.addMove(player, card);
	}

	/**
	 * This method is used to apply the Admiral card when the passive Player is able to take a card in the second Phase
	 * and the harbourDisplay has at least 5 cards in it
	 * @param turn the turn in which we apply the Admiral
	 */
	public void applyAdmiral(Turn turn){
		final int ADMIRAL = 5;
		if(turn.getHarbourDisplay().size() >= ADMIRAL && turn.getTurnHelp().validHarborDisplay()){
			ArrayList<Card> crew = turn.getActingPlayer().getNonGoldCards();
			for(Card card : crew){
				if(card instanceof Person && ((Person)card).getAbility().equals(Ability.ADMIRAL)){
					mainController.getMatchController().checkDrawPile(turn.getMatch());
					turn.getActingPlayer().addCard(turn.getMatch().drawGoldCard());
					mainController.getMatchController().checkDrawPile(turn.getMatch());
					turn.getActingPlayer().addCard(turn.getMatch().drawGoldCard());
				}
			}
		}
	}

	/**
	 * This method is used to apply the Jester effect when either the harbourDisplay is invalid and
	 * everyone with a Jester gets a coin or if a passive player takes turn to take a card out of the
	 * harbourDisplay, but its empty
	 * @param turn the turn in which the effect occurs
	 */
	public void applyJester(Turn turn){
		final int JESTER = 0;
		if(!turn.getTurnHelp().validHarborDisplay() && turn.getHarbourDisplay().size() > JESTER){
			for(Player player : turn.getPlayerList()){
				ArrayList<Card> cards = player.getNonGoldCards();
				for(int i = 0; i < cards.size(); i++){
					if(cards.get(i) instanceof Person && ((Person) cards.get(i)).getAbility().equals(Ability.JESTER)) {
						mainController.getMatchController().checkDrawPile(turn.getMatch());
						player.getGoldCards().add(turn.getMatch().drawGoldCard());
					}
				}
			}
		}
		else if(turn.getHarbourDisplay().size() == JESTER){
			ArrayList<Card> cards = turn.getActingPlayer().getNonGoldCards();
			for(int i = 0; i < cards.size(); i++){
				if(cards.get(i) instanceof Person && ((Person) cards.get(i)).getAbility().equals(Ability.JESTER)) {
					mainController.getMatchController().checkDrawPile(turn.getMatch());
					turn.getActingPlayer().getGoldCards().add(turn.getMatch().drawGoldCard());
				}
			}

		}
	}

	/**
	 * is used to clear the harbourDisplay by putting all cards on the DiscardPile
	 * @param turn the turn in which we want to clear the harbourDisplay
	 */
	public void clearHarborDisplay(Turn turn) {
		Match currentMatch = turn.getMatch();
		ArrayList<Card> harbourDisplay = turn.getHarbourDisplay();
		applyJester(turn);
		while(!harbourDisplay.isEmpty()){
			currentMatch.toDiscardPile(harbourDisplay.remove(0));
		}
	}

	/**
	 * draws a card from the draw pile and adds it to the harbour/expedition display or applies the taxRaise
	 * @param turn the turn that has the harbourDisplay to add to
	 */
	public void drawCardToHarborDisplay(Turn turn) {
		Match currentMatch = turn.getMatch();
		getMainController().getMatchController().checkDrawPile(turn.getMatch());
		Move move = new Move(turn.getActivePlayer(), turn.getMatch().getDrawPile().get(0), true, turn.getMatch().clone());
		currentMatch.drawFromPile(turn);
		ArrayList<Card> harbourDisplay = turn.getHarbourDisplay();
		Card drawnCard = harbourDisplay.get(harbourDisplay.size()-1);
		if(drawnCard instanceof Expedition) {
			harbourDisplay.remove(harbourDisplay.size()-1);
			currentMatch.getExpeditionDisplay().add((Expedition) drawnCard);
		} else if(drawnCard instanceof TaxRaise) {
			harbourDisplay.remove(harbourDisplay.size()-1);
			applyTaxRaise((TaxRaise) drawnCard, turn);
		}
		turn.getTurnHelp().setLastDrawnCard(drawnCard);
		getMainController().getMatchController().checkDrawPile(turn.getMatch());
		move.setMatchAfterMove(turn.getMatch().clone());
		turn.getMoves().add(move);
		//clear undoStacks when making a move
		turn.getUndoHistory().clear();
		turn.getMatch().getUndoTurnHistory().clear();
	}

	/**
	 * When a TaxRaise card is flipped, all players who have more than 11 Gold Cards should return half of them (under rounded)
	 ** if MaxSwords = true -> the player/players with the most Swords receive 1 gold
	 ** if MaxSword = false -> the player/players with the least amount of victory points receive 1 gold
	 * @param card - a TaxRaise card
	 * @param turn - a turn in a match
	 */
	private void applyTaxRaise(TaxRaise card, Turn turn) {

		for(int i = 0; i < turn.getPlayerList().size(); i++){
			Player player = turn.getPlayerList().get(i);
			ArrayList<Card> goldCards = player.getGoldCards();
			ArrayList<Player> playersMaxSwords = turn.getTurnHelp().maxSwords();
			ArrayList<Player> playersMinPoints = turn.getTurnHelp().minVictoryPoints();
			match = getMainController().getPortRoyal().getActiveMatch();

			final int THRESHOLD = 11;

			if(player.getCoins() > THRESHOLD){
				int toRemove = player.getCoins()/2;
				while(toRemove > 0){
					goldCards.get(0).flip();
					match.toDiscardPile(goldCards.get(0));
					goldCards.remove(goldCards.get(0));
					toRemove--;
				}
			}

			if(card.getMaxSwords()){
				if(playersMaxSwords.contains(player)){
					getMainController().getMatchController().checkDrawPile(match);
					player.getGoldCards().add(match.drawGoldCard());
				}
			}else{
				if(playersMinPoints.contains(player)){
					getMainController().getMatchController().checkDrawPile(match);
					player.getGoldCards().add(match.drawGoldCard());
				}
			}
		}
		turn.getMatch().toDiscardPile(card);
	}

	/**
	 * Get-Method of the mainController
	 * @return mainController
	 */
	public MainController getMainController() { return mainController; }

	/**
	 * Set-Method of the mainController
	 * @param mainController mainController
	 */
	public void setMainController(MainController mainController) { this.mainController = mainController; }

	/**
	 * Get-Method for the GameAUI
	 * @return gameAUI
	 */
	public GameAUI getGameAUI() {return gameAUI;}

	/**
	 * Set-Method for the GameAUI
	 * @param gameAUI gameAUI
	 */
	public void setGameAUI(GameAUI gameAUI) {this.gameAUI = gameAUI;}

	/**
	 * Get-Method for the match
	 * @return match
	 */
	public Match getMatch() {return match;}

	/**
	 * Set-Method for the match
	 * @param match match
	 */
	public void setMatch(Match match) {this.match = match;}

	/**
	 * Get-Method for the TurnHelp
	 * @return turnHelp
	 */
	public TurnHelp getTurnHelp() {return turnHelp;}

	/**
	 * Set-Method for the TurnHelp
	 * @param turnHelp turnHelp
	 */
	public void setTurnHelp(TurnHelp turnHelp) {this.turnHelp = turnHelp;}
}
