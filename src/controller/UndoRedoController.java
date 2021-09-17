package controller;

import model.*;

import java.util.ArrayList;

/**
 * This class is used to manage the UndoRedo functions of the game.
 */
public class UndoRedoController {

	/**
	 * The MainController
	 */
	private MainController mainController;

	/**
	 * The UndoRedoController
	 * @param mainController mainController
	 */
	public UndoRedoController(MainController mainController) {
		this.mainController = mainController;
	}

	/**
	 * Method to undo a turn
	 * @param match - current match
	 */
	public void undo(Match match) {
		if(match.getActiveTurn().getMoves().size() == 0) {
			if (match.getTurnHistory().size() <= 1) {
				throw new IllegalArgumentException("The turn can't be undone.");
			}
			Turn turn = match.getTurnHistory().get(match.getTurnHistory().size() - 1);
			match.getUndoTurnHistory().push(turn);
			match.getTurnHistory().remove(turn);
		}
		Player playerInMove = match.getActiveTurn().getMoves().get(match.getActiveTurn().getMoves().size() - 1).getPlayer();
		Player restore = playerInMove.clone();
		match.getActiveTurn().getMoves().get(match.getActiveTurn().getMoves().size() - 1).setPlayerRedo(restore);
		undoMove(match.getActiveTurn());
	}

	/**
	 * This method is used to redo a turn of a match
	 * @param match the match in that we want to redo something
	 */
	public void redo(Match match) {
		if(match.getActiveTurn().getUndoHistory().size() == 0) {
			throw new IllegalArgumentException("No more turns to redo!");
		}
		if(match.getUndoTurnHistory().size() > 0){
			Turn activeTurnAfterMove = match.getActiveTurn().getUndoHistory().peek().getMatchAfterMove().getActiveTurn();
			if(!activeTurnAfterMove.equals(match.getActiveTurn())) {
				Turn turn = match.getUndoTurnHistory().pop();
				this.mainController.getPortRoyal().setActiveMatch(turn.getMatch());
				this.mainController.getPortRoyal().getActiveMatch().getTurnHistory().add(turn);
			}
		}
		Move move = match.getActiveTurn().getUndoHistory().peek();
		Player playerInMove = move.getMatchBeforeMove().getActiveTurn().getActingPlayer();
		Player restore = playerInMove.clone();
		match.getActiveTurn().getUndoHistory().peek().setPlayerUndo(restore);
		redoMove(this.mainController.getPortRoyal().getActiveMatch().getActiveTurn());
		}


	/**
	 * This method is used to undo a move in a turn
	 * @param turn - the turn in which we want to undo a move
	 * @throws IllegalArgumentException - Exception will be thrown if
	 */
	public void undoMove(Turn turn) throws IllegalArgumentException {
		if(turn.getMoves().size()==0){
			throw new IllegalArgumentException("no Move to remove");
		}
		Move move = turn.getMoves().get(turn.getMoves().size()-1);
		PortRoyal portRoyal = this.mainController.getPortRoyal();
		portRoyal.setActiveMatch(move.getMatchBeforeMove());
		Player player = portRoyal.getActiveMatch().getActiveTurn().getActingPlayer();
		if(move.getPlayerUndo() != null){
			player.setGoldCards(move.getPlayerUndo().getGoldCards());
			player.setVictoryPoints(move.getPlayerUndo().getVictoryPoints());
			player.setSwords(move.getPlayerUndo().getSwords());
			player.setNonGoldCards(move.getPlayerUndo().getNonGoldCards());
			player.setAvatarID(move.getPlayerUndo().getAvatarID());
			player.setTraderBonus(move.getPlayerUndo().getTraderBonus());
		}
		if(!move.getPlayerIsActive()) {
			Player activePlayer = portRoyal.getActiveMatch().getActiveTurn().getActivePlayer();
			Card toRemove = activePlayer.getGoldCards().get(activePlayer.getGoldCards().size()-1);
			activePlayer.getGoldCards().remove(toRemove);
		}
		portRoyal.getActiveMatch().getActiveTurn().getUndoHistory().push(move);
	}

	/**
	 * This method is used to redo a move in a turn
	 * @param turn the turn in which we want to redo a move
	 */
	public void redoMove(Turn turn) {
		 if (turn.getUndoHistory().size() == 0)
		 {
			throw new IllegalArgumentException("The moves history for this turn is empty");
		 }
		 else
		 {
		 	Move move = turn.getUndoHistory().pop();
		 	PortRoyal portRoyal = this.mainController.getPortRoyal();
		 	portRoyal.setActiveMatch(move.getMatchAfterMove());
		 	portRoyal.getActiveMatch().getActiveTurn().getMoves().add(move);
		 	Player player = portRoyal.getActiveMatch().getActiveTurn().getActingPlayer();

		 	if(!move.getPlayerIsActive()) {
				Player activePlayer = portRoyal.getActiveMatch().getActiveTurn().getActivePlayer();
		 		if(move.getCard() instanceof Ship) {
					Card toPayActive = player.getGoldCards().get(0);
					activePlayer.getGoldCards().add(toPayActive);
				} else if(move.getCard() instanceof Person) {
					int cost = move.getCard().getCoins();
					Card toPayActive = player.getGoldCards().get(-cost);
					activePlayer.getGoldCards().add(toPayActive);
				}
		 	}

		 	if(move.getPlayerRedo() != null) {
		 		player.setGoldCards(move.getPlayerRedo().getGoldCards());
				player.setVictoryPoints(move.getPlayerRedo().getVictoryPoints());
				player.setSwords(move.getPlayerRedo().getSwords());
				player.setNonGoldCards(move.getPlayerRedo().getNonGoldCards());
				player.setAvatarID(move.getPlayerRedo().getAvatarID());
				player.setTraderBonus(move.getPlayerRedo().getTraderBonus());
			}
		 }
	}

	/**
	 * returns whether the active match has a move to be undone
	 * @return true if a move can be undone, false otherwise
	 */
	public boolean canUndo() {
		Match currentMatch = this.mainController.getPortRoyal().getActiveMatch();
		boolean canUndo = currentMatch.getActiveTurn().getMoves().size() > 0;
		if(!canUndo) {
			canUndo = currentMatch.getTurnHistory().size() > 1;
		}
		return canUndo;
	}

	/**
	 * returns whether the active match has a move to be redone
	 * @return true if a move can be redone, false otherwise
	 */
	public boolean canRedo() {
		Match currentMatch = this.mainController.getPortRoyal().getActiveMatch();
		return currentMatch.getActiveTurn().getUndoHistory().size() > 0;
	}

	/**
	 * get-Method mainController
	 * @return the mainController
	 */
	public MainController getMainController() { return mainController; }

	/**
	 * set-Method mainController
	 * @param mainController the mainController
	 */
	public void setMainController(MainController mainController) { this.mainController = mainController; }
}
