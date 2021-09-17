package ai;

import abstractuserinterface.GameAUI;
import controller.MainController;
import controller.MoveController;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class AIController {

	private MainController mainController;

	private GameAUI gameAUI;

	private EvaluationTree evaluationTree;

	private MoveController moveController;


	public AIController(MainController mainController) {
		this.mainController = mainController;
		this.moveController = mainController.getMoveController();
	}


	public Move showHint(Match match, Player activePlayer) {
		activePlayer.setEligibleForHighscore(!activePlayer.getEligibleForHighscore());
		return calculateMove(match);
	}


	public void drawHarborDisplay(Match match) {
		mainController.getTurnController().drawCardToHarborDisplay(match.getActiveTurn());
	}


	public void makeMove(Match match, Turn turn) {
		Move move = new Move(null, null, false, match);
		if(turn.getActingPlayer().getPlayerType().equals(PlayerType.HARD_AI)) {
			move = calculateMove(match);
		} else if(turn.getActingPlayer().getPlayerType().equals(PlayerType.NORMAL_AI)) {
			double random = Math.random();
			if(random > 0.5) {
				move = calculateMove(match);
			} else {
				move = calculateBadMove(match);
			}
		} else if(turn.getActingPlayer().getPlayerType().equals(PlayerType.EASY_AI)) {
			move = calculateBadMove(match);
		}
		/*-------------------------------------------------*/
		if(move.getCard() instanceof Expedition && ((Expedition) move.getCard()).canFulfill(move.getPlayer())) {
			moveController.fulfillExpedition(move, match);
		}

		if(move.getCard() instanceof Ship && ((Ship) move.getCard()).getColor() == Color.UNDEFINED) {
			boolean invalid = true;
			while(invalid) {
				drawHarborDisplay(match);
				if(match.getActiveTurn().getHarbourDisplay().size() > 0) {
					invalid = false;
				}
			}
			ArrayList<Card> harbourDisplay = match.getActiveTurn().getHarbourDisplay();
			if(turn.getHarbourDisplay().get(turn.getHarbourDisplay().size() - 1) instanceof Ship && !match.getActiveTurn().getTurnHelp().validHarborDisplay()) {
				if(((Ship) harbourDisplay.get(harbourDisplay.size() - 1)).getSwords() <= turn.getActingPlayer().getSwords()) {
					moveController.fightShip(turn.getMoves().get(turn.getMoves().size() - 1), turn);
				}
			}
			return;
		}
		if(match.getActiveTurn().getIsPhaseDiscover()) { // first phase
			match.getActiveTurn().addMove(match.getActiveTurn().getActingPlayer(), null);
			mainController.getTurnController().changePhase(match.getActiveTurn());

		} else { // second phase
			if(move.getCard() == null) {
				//match.getActiveTurn().addMove(match.getActiveTurn().getActingPlayer(), null);
				Card endMove = null;
				mainController.getPortRoyal().getActiveMatch().getActiveTurn().addMove(mainController.getPortRoyal().getActiveMatch().getActiveTurn().getActingPlayer(), endMove);

				Turn actTurn = mainController.getPortRoyal().getActiveMatch().getActiveTurn();
				Player actingPlayer = actTurn.getActingPlayer();
				Player activePlayer = actTurn.getActivePlayer();

				Player nextActing = actTurn.getTurnHelp().nextPlayer(actingPlayer);
				actTurn.setActingPlayer(nextActing);
				actingPlayer = actTurn.getActingPlayer();
				mainController.getTurnController().applyAdmiral(actTurn);

				if(actingPlayer == activePlayer || actTurn.getIsPhaseDiscover() || actTurn.getHarbourDisplay().isEmpty()){
					mainController.getTurnController().clearHarborDisplay(actTurn);
					mainController.getMatchController().addTurn(actTurn.getMatch());

					if(matchEnd()){
						//highscores ausrechnen
						for(Player player : actTurn.getMatch().getPlayerList()){
							Score score = new Score(player);
							mainController.getScoreController().calculateScore(score);
							mainController.getScoreController().addScoreToHighscores(score);
						}
						//endreihenfolge bestimmen
						ArrayList<Player> endResult = mainController.getPortRoyal().getActiveMatch().getPlayerList();
						Collections.sort(endResult);

						if(mainController.getPortRoyal().getMatchList().contains(mainController.getPortRoyal().getActiveMatch())){
							mainController.getPortRoyal().getMatchList().remove(mainController.getPortRoyal().getActiveMatch());
						}

						mainController.getIOController().save();
					}
				}
			} else {
				Card card = move.getCard();
				turn.addMove(move.getPlayer(), move.getCard());
				if(card instanceof Ship) {
					moveController.sellShip(turn.getMoves().get(turn.getMoves().size() - 1), turn);
				} else if(card instanceof Person) {
					moveController.hireCrew(turn.getMoves().get(turn.getMoves().size() - 1), turn);
				} else if(card instanceof Expedition) {
					moveController.fulfillExpedition(turn.getMoves().get(turn.getMoves().size() - 1), match);
				}
			}
		}

		if(matchEnd()){
			Turn actTurn = mainController.getPortRoyal().getActiveMatch().getActiveTurn();
			//highscores ausrechnen
			for(Player player : actTurn.getMatch().getPlayerList()){
				Score score = new Score(player);
				mainController.getScoreController().calculateScore(score);
				mainController.getScoreController().addScoreToHighscores(score);
			}
			//endreihenfolge bestimmen
			ArrayList<Player> endResult = mainController.getPortRoyal().getActiveMatch().getPlayerList();
			Collections.sort(endResult);

			if(mainController.getPortRoyal().getMatchList().contains(mainController.getPortRoyal().getActiveMatch())){
				mainController.getPortRoyal().getMatchList().remove(mainController.getPortRoyal().getActiveMatch());
			}

			mainController.getIOController().save();
		}
	}


	public boolean matchEnd(){
		ArrayList<Player> playerList = mainController.getPortRoyal().getActiveMatch().getPlayerList();
		ArrayList<Turn> turnList = mainController.getPortRoyal().getActiveMatch().getTurnHistory();
		boolean victory = false;
		final int VP_TO_WIN = 12;
		for(Player player : playerList){
			if(player.getVictoryPoints()>=VP_TO_WIN){ victory = true; }
		}
		boolean end = false;
		if(turnList.size() % playerList.size() == 1){
			end = true;
		}
		return (victory && end);
	}


	/**
	 * calculates the best possible move for the ai
	 * @param match the match to calculate the move in
	 * @return the best possible move
	 */
	private Move calculateMove(Match match) {
		Turn turn = match.getActiveTurn();
		Player player = turn.getActingPlayer();
		int score = 0, scoreBefore = 0;
		Card toDraw = new Expedition(0, null, 0);
		boolean goodEnough = false;

		if(!turn.getTurnHelp().validHarborDisplay()) {
			return new Move(player, null, player.equals(turn.getActivePlayer()), match);
		}

		for (Expedition expedition : match.getExpeditionDisplay()) {
			if (match.getActiveTurn().getActivePlayer().equals(player) && expedition.canFulfill(player)) {
				return new Move(player, expedition, true, match);
			}
		}
		if(!match.getActiveTurn().getIsPhaseDiscover() && match.getActiveTurn().getHarbourDisplay().isEmpty()) {
			return new Move(player, null, turn.getActivePlayer().equals(player), match);
		}

		int playerIndex = -1;
		for(Player acting : match.getActiveTurn().getPlayerList()) {
			if(acting.equals(match.getActiveTurn().getActingPlayer())) {
				playerIndex = match.getActiveTurn().getPlayerList().indexOf(acting);
			}
		}

		if (!turn.getHarbourDisplay().isEmpty()) {
			int max = -10000;

			for (Card card : turn.getHarbourDisplay()) {
				score = 4 * player.getSwords() + 10 * player.getVictoryPoints() + player.getCoins();
				scoreBefore = score;
				if (card instanceof Ship) {
					if (player.getCoins() < 9) {
						if (turn.getActivePlayer().equals(player)) {
							score += card.getCoins();
						} else {
							score += card.getCoins() - 1;
						}
					}
				} else if (card instanceof Person) {
					if (((Person) card).canAfford(player)) {
						score += 10 * ((Person) card).getVictoryPoints();
						if (player.getSwords() < 5) {
							score += 4 * ((Person) card).getSwords();
						}
					} else {
						score = 0;
					}
				}
				if (score > max) {
					max = score;
					toDraw = card;
					if (score - scoreBefore >= 2) {
						goodEnough = true;
					}
				}
			}

			if (match.getActiveTurn().getIsPhaseDiscover()) { //first phase
				if (match.getActiveTurn().getActivePlayer().equals(player) && !goodEnough) {
					return new Move(player, new Ship(1, 1, Color.UNDEFINED), true, match); //draw a new card if the move isnt good
				} else {  //change phase if the move is good enough
					return new Move(player, null, true, match);
				}
			} else { //second phase
				if(match.getActiveTurn().getPlayerMoveNumber()[playerIndex] == 0) {
					return new Move(player, null, match.getActiveTurn().getActivePlayer().equals(player), match);
				}
				if (goodEnough) { //make the move if good enough
					return new Move(player, toDraw, match.getActiveTurn().getActivePlayer().equals(player), match);
				} else { // end turn if not good enough
					return new Move(player, null, match.getActiveTurn().getActivePlayer().equals(player), match);
				}
			}
		}
		if(match.getActiveTurn().getIsPhaseDiscover()) {
			return new Move(player, new Ship(1, 1, Color.UNDEFINED), true, match);
		} else {
			return new Move(player, null, match.getActiveTurn().getActivePlayer().equals(player), match);
		}
	}

	/**
	 * calculates the worst move to make for the easy ai
	 * @param match the match to calculate the move in
	 * @return the move
	 */
	public Move calculateBadMove(Match match) {
		Turn turn = match.getActiveTurn();
		Player player = turn.getActingPlayer();
		int score = 0, scoreBefore = 0;
		Card toDraw = new Expedition(0, null, 0);

		if(!turn.getTurnHelp().validHarborDisplay()) {
			return new Move(player, null, player.equals(turn.getActivePlayer()), match);
		}

		if(!match.getActiveTurn().getIsPhaseDiscover() && match.getActiveTurn().getHarbourDisplay().isEmpty()) {
			return new Move(player, null, turn.getActivePlayer().equals(player), match);
		}

		int playerIndex = -1;
		for(Player acting : match.getActiveTurn().getPlayerList()) {
			if(acting.equals(match.getActiveTurn().getActingPlayer())) {
				playerIndex = match.getActiveTurn().getPlayerList().indexOf(acting);
			}
		}

		if (!turn.getHarbourDisplay().isEmpty()) {
			int min = 10000;

			for (Card card : turn.getHarbourDisplay()) {
				score = 4 * player.getSwords() + 10 * player.getVictoryPoints() + player.getCoins();
				scoreBefore = score;
				if (card instanceof Ship) {
					if (player.getCoins() < 9) {
						if (turn.getActivePlayer().equals(player)) {
							score += card.getCoins();
						} else {
							score += card.getCoins() - 1;
						}
					}
				} else if (card instanceof Person) {
					if (((Person) card).canAfford(player)) {
						score += 10 * ((Person) card).getVictoryPoints();
						if (player.getSwords() < 5) {
							score += 4 * ((Person) card).getSwords();
						}
					} else {
						score = 10000;
					}
				}
				if (score < min) {
					min = score;
					toDraw = card;
				}
			}

			if (match.getActiveTurn().getIsPhaseDiscover()) { //first phase
				if (match.getActiveTurn().getActivePlayer().equals(player) && turn.getHarbourDisplay().isEmpty()) {
					return new Move(player, new Ship(1, 1, Color.UNDEFINED), true, match); //draw a new card if the move isnt good
				} else {  //change phase if the move is good enough
					return new Move(player, null, true, match);
				}
			} else { //second phase
				if(match.getActiveTurn().getPlayerMoveNumber()[playerIndex] == 0) {
					return new Move(player, null, match.getActiveTurn().getActivePlayer().equals(player), match);
				}
				if (toDraw instanceof Person || toDraw instanceof Ship) { //make the move if good enough
					return new Move(player, toDraw, match.getActiveTurn().getActivePlayer().equals(player), match);
				} else { // end turn if cant afford
					return new Move(player, null, match.getActiveTurn().getActivePlayer().equals(player), match);
				}
			}
		}
		if(match.getActiveTurn().getIsPhaseDiscover() && turn.getActingPlayer().equals(turn.getActivePlayer())) {
			return new Move(player, new Ship(1, 1, Color.UNDEFINED), true, match);
		} else {
			return new Move(player, null, match.getActiveTurn().getActivePlayer().equals(player), match);
		}
	}


	public int calculateScore(Match match, Player player) {
		int coin_threshold = adjustCoinThreshold(player);
		final int FIRST_SWORD_THRESHOLD = 4;
		final int SECOND_SWORD_THRESHOLD = 5;
		int aiScore, opponentScore;
		int opponentIndex = match.getPlayerList().indexOf(player) == 1 ? 0 : 1;
		Player opponent = match.getPlayerList().get(opponentIndex);

		if(player.getCoins() <= coin_threshold) { // coins can be drawn

			aiScore = 10 * player.getVictoryPoints() + player.getCoins();
			opponentScore = 10 * opponent.getVictoryPoints() + opponent.getCoins();

			if(player.getSwords() <= FIRST_SWORD_THRESHOLD) { // swords can be drawn
				aiScore = 4 * player.getSwords() + 10 * player.getVictoryPoints() + player.getCoins();
				opponentScore = 4 * opponent.getSwords() + 10 * opponent.getVictoryPoints() + opponent.getCoins();
			} else if(player.getSwords() == SECOND_SWORD_THRESHOLD) {
				aiScore = 2 * player.getSwords() + 10 * player.getVictoryPoints() + player.getCoins();
				opponentScore = 2 * opponent.getSwords() + 10 * opponent.getVictoryPoints() + opponent.getCoins();
			}
			return aiScore - opponentScore;

		}
		/* coins cant be drawn */

		aiScore = 10 * player.getVictoryPoints() - (player.getCoins() - coin_threshold);
		opponentScore = 10 * opponent.getVictoryPoints() + opponent.getCoins();

		if(player.getSwords() <= FIRST_SWORD_THRESHOLD) { // swords can be drawn
			aiScore = 4 * player.getSwords() + 10 * player.getVictoryPoints() - (player.getCoins() - coin_threshold);
			opponentScore = 4 * opponent.getSwords() + 10 * opponent.getVictoryPoints() + opponent.getCoins();
		} else if(player.getSwords() == SECOND_SWORD_THRESHOLD) {
			aiScore = 2 * player.getSwords() + 10 * player.getVictoryPoints() - (player.getCoins() - coin_threshold);
			opponentScore = 2 * opponent.getSwords() + 10 * opponent.getVictoryPoints() + opponent.getCoins();
		}
		return aiScore - opponentScore;
	}


	public int adjustCoinThreshold(Player player) {
		int madCount = 0;
		int admiralCount = 0;
		int jesterCount = 0;

		for(Card card : player.getNonGoldCards()) {
			if(card instanceof Person) {
				if (((Person) card).getAbility().equals(Ability.MADEMOISELLE)) {
					madCount++;
				} else if(((Person) card).getAbility().equals(Ability.JESTER)) {
					jesterCount++;
				} else if(((Person) card).getAbility().equals(Ability.ADMIRAL)) {
					admiralCount++;
				}
			}
		}

		return 10 - madCount - admiralCount - jesterCount / 2;
	}


	public void evaluateTree(EvaluationTree tree) {
		if(!tree.isLeaf()) {
			for(EvaluationTree child : tree.getChildren()) {
				evaluateTree(child);
			}
			if(tree.getLayer() % 2 == 0) {
				int max = -10000;
				for(EvaluationTree child : tree.getChildren()) {
					max = Integer.max(max, child.getRoot().getScore());
				}
				tree.getRoot().setScore(max);
			} else {
				int min = 10000;
				for(EvaluationTree child : tree.getChildren()) {
					min = Integer.min(min, child.getRoot().getScore());
				}
				tree.getRoot().setScore(min);
			}
		} else {
			Match match = tree.getRoot().getMatchState();
			int indexOfOpponent = match.getPlayerList().get(0).getPlayerType().equals(PlayerType.HUMAN) ? 0 : 1;
			int indexOfAi = indexOfOpponent == 0 ? 1 : 0;
			Player opponent = match.getPlayerList().get(indexOfOpponent);
			Player ai = match.getPlayerList().get(indexOfAi);
			if(tree.getLayer() % 2 == 0) {
				tree.getRoot().setScore(calculateScore(match, ai)); //positive because its us
			} else {
				tree.getRoot().setScore(-calculateScore(match, opponent)); //negative because its them
			}
		}
	}


	public void initializeTree(int height, Match match) {
		this.evaluationTree = new EvaluationTree(height, 0, match);
		evaluationTree.initializeTree(height);
	}


	public MainController getMainController() {
		return mainController;
	}


	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}


	public GameAUI getGameAUI() {
		return gameAUI;
	}


	public void setGameAUI(GameAUI gameAUI) {
		this.gameAUI = gameAUI;
	}


	public EvaluationTree getEvaluationTree() {
		return evaluationTree;
	}


	public void setEvaluationTree(EvaluationTree evaluationTree) {
		this.evaluationTree = evaluationTree;
	}
}
