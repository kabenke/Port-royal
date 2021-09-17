package controller;

import abstractuserinterface.GameAUI;
import model.*;

import java.util.ArrayList;

/**
 * controller to control move logic.
 */
public class MoveController {

	/**
	 * The MainController
	 */
	private MainController mainController;

	/**
	 * helping object
	 */
	private MoveControllerHelp moveControllerHelp;

	/**
	 * The GameAUI
	 */
	private GameAUI gameAUI;

	/**
	 * constructor that initializes all attributes of MoveController
	 * @param mainController the MainController of the project
	 */
	public MoveController(MainController mainController) {
		this.mainController = mainController;
		this.moveControllerHelp = new MoveControllerHelp(this);
	}

	/**
	 * method to sell ships from the harbour display
	 * @param move the move the card got picked up in
	 * @param turn the turn that the move is in
	 */
	public void sellShip(Move move, Turn turn) {

		Player playerBefore = turn.getActingPlayer();
		Player restore = playerBefore.clone();
		if(moveControllerHelp.hasMoreMoves(move.getPlayer(), turn)){

			Card cardToPayActive;
			if(move.getCard() instanceof Ship) {

				if(turn.getHarbourDisplay().remove(move.getCard())) {
					turn.getMatch().toDiscardPile(move.getCard());
				}

				int traderBonus = 0;
				Player player = move.getPlayer();
				Ship card = (Ship)move.getCard();

				switch (card.getColor()) {
					case BLUE: traderBonus += player.getTraderBonus()[0];
						break;

					case RED: traderBonus += player.getTraderBonus()[1];
						break;

					case GREEN: traderBonus += player.getTraderBonus()[2];
						break;

					case BLACK: traderBonus += player.getTraderBonus()[3];
						break;

					case YELLOW: traderBonus += player.getTraderBonus()[4];
						break;

					default: /* No trader so cant happen */
				}

				moveControllerHelp.adjustCoins(move.getPlayer(), move.getCard().getCoins() + traderBonus, turn);

				if (!move.getPlayer().equals(turn.getActivePlayer())) {
					cardToPayActive = move.getPlayer().getGoldCards().remove(0);
					turn.getActivePlayer().addCard(cardToPayActive);
				}

			}
			else {throw new IllegalArgumentException("not a Ship Card");}
			move.setMatchAfterMove(turn.getMatch().clone());
			move.setPlayerUndo(restore);
			//clear undoStacks when making a move
			turn.getUndoHistory().clear();
			turn.getMatch().getUndoTurnHistory().clear();
		}
		moveControllerHelp.adjustMoveCount(turn);
	}

	/**
	 * method to fight a ship
	 * @param move the move the card got picked up in
	 * @param turn the turn that the move is in
	 */
	public void fightShip(Move move, Turn turn){
		Player playerBefore = turn.getActingPlayer();
		Player restore = playerBefore.clone();

		if(move.getCard() instanceof Ship){
			if(((Ship) move.getCard()).getSwords() <= turn.getActingPlayer().getSwords()) {
				if(turn.getHarbourDisplay().remove(move.getCard())) {
					turn.getMatch().toDiscardPile(move.getCard());
				}
			}
			move.setMatchAfterMove(turn.getMatch().clone());
			move.setPlayerUndo(restore);
			//clear undoStacks when making a move
			turn.getUndoHistory().clear();
			turn.getMatch().getUndoTurnHistory().clear();
		}
		else{
			throw new IllegalArgumentException("not a Ship Card");
		}
	}

	/**
	 * to pay a person and consider the activePlayer coin
	 * @param move current move of the hiring player
	 * @param turn current turn
	 */
	public void hireCrew(Move move, Turn turn) {

		Player playerBefore = turn.getActingPlayer();
		Player restore = playerBefore.clone();

		if(moveControllerHelp.hasMoreMoves(move.getPlayer(), turn)) {

			Card cardToPayActive;
			Person person = (Person) move.getCard();
			if (!person.canAfford(move.getPlayer())) {
				throw new IllegalArgumentException("can not afford person");
			}
			int mademoiselle = 0;
			Player player1 = move.getPlayer();
			ArrayList<Card> crew = player1.getNonGoldCards();
			for(Card card : crew){
				if(card instanceof Person && ((Person)card).getAbility().equals(Ability.MADEMOISELLE)){
					mademoiselle += 1;
				}

			}

			moveControllerHelp.adjustCoins(move.getPlayer(), move.getCard().getCoins() + mademoiselle, turn);
			if (!move.getPlayer().equals(turn.getActivePlayer())) {
				cardToPayActive = move.getPlayer().getGoldCards().remove(0);
				turn.getActivePlayer().addCard(cardToPayActive);
			}
			turn.getHarbourDisplay().remove(move.getCard());

			/* adjusting attributes */
			Player player = move.getPlayer();
			Person card = (Person) move.getCard();
			player.addCard(person);
			int oldSwords = player.getSwords();
			player.setSwords(oldSwords + card.getSwords());
			int oldVictoryPoints = player.getVictoryPoints();
			player.setVictoryPoints(oldVictoryPoints + card.getVictoryPoints());
			if (card.getAbility().equals(Ability.TRADER)) {
				switch (card.getColor()) {
					case BLUE:
						player.getTraderBonus()[0]++;
						break;

					case RED:
						player.getTraderBonus()[1]++;
						break;

					case GREEN:
						player.getTraderBonus()[2]++;
						break;

					case BLACK:
						player.getTraderBonus()[3]++;
						break;

					case YELLOW:
						player.getTraderBonus()[4]++;
						break;

					default: /* No trader so cant happen */
				}
			}
			move.setMatchAfterMove(turn.getMatch().clone());
			move.setPlayerUndo(restore);
			//clear undoStacks when making a move
			turn.getUndoHistory().clear();
			turn.getMatch().getUndoTurnHistory().clear();
		}
		moveControllerHelp.adjustMoveCount(turn);
	}

	/**
	 * fulfill a expedition and discard the artifacts cards
	 * @param move move with the expedition
	 * @param match current match of the move
	 */
	public void fulfillExpedition(Move move, Match match) {
		Player playerBefore = match.getActiveTurn().getActingPlayer();
		Player restore = playerBefore.clone();

		if(move.getCard() instanceof Expedition){

			Expedition expedition = (Expedition) move.getCard();
			Expedition removedExp;
			if(!expedition.canFulfill(move.getPlayer())){
				throw new IllegalArgumentException("can not fulfill");
			}

			if(match.getExpeditionDisplay().contains(expedition)) {
				int index = match.getExpeditionDisplay().indexOf(expedition);
				removedExp = match.getExpeditionDisplay().remove(index);
			} else {
				throw new IllegalArgumentException("Exp not in display");
			}

			ArrayList<Ability> artifacts = expedition.getArtifacts();

			moveControllerHelp.adjustCards(move, match, artifacts);

			/* adjusting coins and victoryPoints */
			move.getPlayer().addCard(removedExp);
			ArrayList<Turn> turns = match.getTurnHistory();
			moveControllerHelp.adjustCoins(move.getPlayer(), removedExp.getCoins(), turns.get(turns.size()-1));
			moveControllerHelp.newVictoryPoints(move);
			move.getPlayer().setDidExp(true);
			move.setMatchAfterMove(match.clone());
			move.setPlayerUndo(restore);
			//clear undoStacks when making a move
			match.getActiveTurn().getUndoHistory().clear();
			match.getUndoTurnHistory().clear();
		}
		else
		{
			throw new IllegalArgumentException("not a Expedition card");
		}
	}

	/**
	 * Get-Method for the mainController
	 * @return mainController
	 */
	public MainController getMainController() { return mainController; }

	/**
	 * Set-Method for the mainController
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
	 * Get-Method for the MoveControllerHelp
	 * @return moveControllerHelp
	 */
	public MoveControllerHelp getMoveControllerHelp() {
		return moveControllerHelp;
	}

	/**
	 * Set-Method for the MoveControllerHelp
	 * @param moveControllerHelp moveControllerHelp
	 */
	public void setMoveControllerHelp(MoveControllerHelp moveControllerHelp) {
		this.moveControllerHelp = moveControllerHelp;
	}
}
