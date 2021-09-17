package controller;

import abstractuserinterface.GameAUI;
import model.*;

import java.util.Collections;

import java.util.ArrayList;


/**
 * This class is used to manage a match with its players and the DrawPile.
 */
public class MatchController {

	/**
	 * The Main Controller
	 */
	private MainController mainController;

	/**
	 * The GameAUI
	 */
	private GameAUI gameAUI;

	/**
	 * Constructor for the MatchController
	 * @param mainController Main Controller
	 */
	public MatchController(MainController mainController) {
		this.mainController = mainController;
	}

	/**
	 * This method checks if the drawpile is empty and shuffles
	 * @param match the match where the Pile should get checked
	 */
	public void checkDrawPile(Match match){
		if(match.getDrawPile().isEmpty()){
			if(match.getRandomShuffle()){
				match.getMatchHelp().swapPiles();
				shuffleDrawPile(match);
			}
			else{
				sortDrawPile();
				match.getMatchHelp().swapPiles();
			}
		}
	}

	/**
	 * This method is used to shuffle the PlayerList of a match
	 * @param match the match where the PlayerList should get shuffled
	 */
	public void shufflePlayerlist(Match match) {
		ArrayList<Player> toShuffleList = match.getPlayerList();
		Collections.shuffle(toShuffleList);
		match.setPlayerList(toShuffleList);


	}

	/**
	 * This method is used to initialize the DrawPile for a match
	 * @param match the match where the DrawPile should be initialized
	 */
	public void initializeDrawPile(Match match) {
		ArrayList<Card> drawPile;
		drawPile = mainController.getParseController().parseCsvToPile();
		mainController.getPortRoyal().getActiveMatch().setDrawPile(drawPile);

		ArrayList<Ability> artifacts = new ArrayList<>();
		artifacts.add(Ability.CAPTAIN); artifacts.add(Ability.PRIEST); artifacts.add(Ability.SETTLER);
		final int MAX_PLAYERS = 5;
		if(match.getPlayerList().size()==MAX_PLAYERS){
			match.getExpeditionDisplay().add(new Expedition(3, artifacts, 5));
		}

		for(int i=0; i<match.getPlayerList().size(); i++){
			for(int k=0; k<3; k++){
				match.getPlayerList().get(i).addCard(match.drawGoldCard());
			}
		}

		if(match.getRandomShuffle()){
			shuffleDrawPile(match);
		}
	}

	/**
	 * This method is used to sort the DrawPile back to the initial Pile we generated from the CsvFile
	 */
	public void sortDrawPile(Match match) {
		ArrayList<Card> initialPile;
		initialPile = mainController.getParseController().parseCsvToPile();

		ArrayList<Card> discardPile;
		discardPile = mainController.getPortRoyal().getActiveMatch().getDiscardPile();

		ArrayList<Card> newDiscardPile = new ArrayList<>();

		for(int i = 0; i < initialPile.size()-1; i++){
			for(Card card1 : discardPile){
				Card card = initialPile.get(i);
				if(compare(card1, card)) {
					card1.flip();
					newDiscardPile.add(card1);
					discardPile.remove(card1);
					break;
				}
			}
		}

		match.setDiscardPile(newDiscardPile);
	}

	/**
	 * returns true if the content of the cards is identical, false otherwise
	 * @param card1 this is the first card
	 * @param card2 this is the second card
	 * @return true if the content of the cards is identical, false otherwise
	 */
	public boolean compare(Card card1, Card card2) {
		if(card1.getClass() != card2.getClass() || card1.getCoins() != card2.getCoins()) {
			return false;
		}

		if(card1 instanceof Ship) {
			return (card2 instanceof Ship && ((Ship) card1).getColor().equals(((Ship) card2).getColor())
												&& ((Ship) card1).getSwords() == ((Ship) card2).getSwords());
		}
		else if(card1 instanceof Person) {
			return (card2 instanceof Person && ((Person) card1).getAbility().equals(((Person) card2).getAbility())
											&& ((Person) card1).getColor().equals(((Person) card2).getColor())
											&& ((Person) card1).getSwords() == ((Person) card2).getSwords()
											&& ((Person) card1).getVictoryPoints() == ((Person) card2).getVictoryPoints());
		}
		else if(card1 instanceof TaxRaise) {
			return (card2 instanceof TaxRaise && ((TaxRaise) card1).getMaxSwords() == ((TaxRaise) card2).getMaxSwords());
		}
		else {
			if(card2 instanceof Expedition &&((Expedition)card1).getArtifacts().size() == ((Expedition)card2).getArtifacts().size()) {
				Expedition exp1 = (Expedition) card1;
				Expedition exp2 = (Expedition) card2;
				for(int i = 0; i < exp1.getArtifacts().size(); i++) {
					if(!(exp1.getArtifacts().get(i).equals(exp2.getArtifacts().get(i)))) {
						return false;
					}
				}
			} else {
				return false;
			}

			return (((Expedition) card1).getVictoryPoints() == ((Expedition) card2).getVictoryPoints());
		}
	}

	/**
	 * This method is used to shuffle the DrawPile of the match
	 * @param match the match where the DrawPile should get shuffled
	 */
	public void shuffleDrawPile(Match match) {
		ArrayList<Card> input = match.getDrawPile();
		ArrayList<Card> output = new ArrayList<>();
		while(input.size()>0){
			int rand = (int) (Math.random() * input.size());
			output.add(input.get(rand));
			input.remove(rand);
		}
		for(int i=0; i<output.size(); i++){
			if(output.get(i).getIsFaceUp())
			output.get(i).flip();
		}
		match.setDrawPile(output);
	}

	/**
	 * This method is used to add a turn to a match
	 * @param match the match where we want to add a new turn
	 */
	public void addTurn(Match match) {
		Turn turn;
		if(!match.getTurnHistory().isEmpty()) {
			turn = new Turn(match.getPlayerList(),match.getMatchHelp().nextActivePlayer(),match);
		} else {
			turn = new Turn(match.getPlayerList(), match.getPlayerList().get(0), match);
			match.setActivePlayerInLastTurn(match.getPlayerList().get(0));
		}

		Player activePlayer =  turn.getActivePlayer();

		for(int i=0; i<turn.getPlayerList().size();i++) {
			Player playerAtIndex = turn.getPlayerList().get(i);
			if(playerAtIndex.equals(activePlayer)){
				playerAtIndex.setIsActive(true);}
			else{
				playerAtIndex.setIsActive(false);
			}
		}

		match.getTurnHistory().add(turn);

	}

	/**
	 * Get-Method for the GameAUI
	 * @return gameAUI
	 */
	public GameAUI getGameAUI() {return gameAUI;}

	/**
	 * Set-Method for the GameAUI
	 * @param gameAUI GameAUI
	 */
	public void setGameAUI(GameAUI gameAUI) {this.gameAUI = gameAUI;}
}
