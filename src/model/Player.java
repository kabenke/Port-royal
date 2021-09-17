package model;
import java.util.ArrayList;
import java.io.Serializable;
/**
 * This class represents a Player of the Game.
 */
public class Player implements Serializable, Comparable<Player>{

	/**
	 * the Swords a Player collected
	 */
	protected int swords;

	/**
	 * whether the Player did an Expedition or not
	 */
	protected boolean didExp;

	/**
	 * the Victory Points a Player collected
	 */
	protected int victoryPoints;

	/**
	 * the Trader Bonuses a Player collected [BLUE, RED, GREEN, BLACK, YELLOW]
	 */
	protected int[] traderBonus;

	/**
	 * whether the Player is eligible for the Highscore
	 */
	protected boolean eligibleForHighscore;

	/**
	 * Player´s name
	 */
	protected String name;

	/**
	 * whether the Player is a Human or an AI
	 */
	protected PlayerType playerType;

	/**
	 * The non GoldCards a Player has in his hand
	 */
	protected ArrayList<Card> nonGoldCards;

	/**
	 * The GoldCards a Player has in his hand
	 */
	protected ArrayList<Card> goldCards;

	/**
	 * whether the Player is the Active or Passive Player
	 */
	protected boolean isActive;

	/**
	 * Number of Moves
	 */
	protected int moveCount;

	/**
	 * id of the avatar
	 */
	protected int avatarID;

	/**
	 * helping object to prevent god class
	 */
	private PlayerHelp playerHelp;

	/**
	 * Constructor to create a Player
	 * @param name Name of the Player
	 * @param playerType Player Type of the Player
	 */
	public Player(String name, PlayerType playerType) {
		this.name = name;
		this.playerType = playerType;
		this.moveCount = 0;
		this.goldCards = new ArrayList<>();
		this.nonGoldCards = new ArrayList<>();
		this.eligibleForHighscore = true;
		this.traderBonus = new int[5];
		this.victoryPoints = 0;
		this.didExp = false;
		this.swords = 0;
		this.playerHelp = new PlayerHelp(this);
	}

	/**
	 * Constructor for a Player
	 */
	public Player() {
		this.moveCount = 0;
		this.goldCards = new ArrayList<>();
		this.nonGoldCards = new ArrayList<>();
		this.eligibleForHighscore = true;
		this.traderBonus = new int[5];
		this.victoryPoints = 0;
		this.didExp = false;
		this.swords = 0;
		this.playerHelp = new PlayerHelp(this);
	}

	/**
	 * method to compare which player is better
	 * @param other an other player
	 * @return if the player is better
	 */
	@Override
	public int compareTo(Player other) {
		if(this.getVictoryPoints()>other.getVictoryPoints()){ return -1; }
		else if(this.getVictoryPoints()<other.getVictoryPoints()){ return 1; }
		else{
			if(this.getGoldCards().size()>other.getGoldCards().size()){ return -1; }
			else if(this.getGoldCards().size()<other.getGoldCards().size()){ return 1; }
			else{ return 0; }
		}
	}

	/**
	 * This method adds a new Card to the Hand of the Player
	 * @param card the added card
	 */
	public void addCard(Card card) throws IllegalArgumentException {
		if(card == null) {
			throw new IllegalArgumentException();
		}
		if(!card.isFaceUp){
			goldCards.add(card);
		}
		else
		{
			nonGoldCards.add(card);
		}
	}

	/**
	 * This method removes a Card from the Player´s hand
	 * @param card the card to remove
	 */
	public void removeCard(Card card) {
		if(card == null) {
			throw new IllegalArgumentException();
		}
		if(!card.isFaceUp){
			goldCards.remove(card);
		}
		else
		{
			nonGoldCards.remove(card);
		}
	}

	/**
	 * This method is used to create a clone of the Player
	 * @return Clone of the Player
	 */
	@Override
	public Player clone() {
		Player playerClone = new Player(this.name,this.playerType);
		playerClone.setDidExp(this.getDidExp());
		playerClone.setGoldCards(new ArrayList<>());
		for(Card card : this.getGoldCards()) { /* clones every Card in goldCards */
			playerClone.addCard(card.clone());
		}
		playerClone.setIsActive(this.isActive);
		playerClone.setEligibleForHighscore(this.eligibleForHighscore);
		playerClone.setMoveCount(this.getMoveCount());
		playerClone.setNonGoldCards(new ArrayList<>());
		for(Card card : this.getNonGoldCards()) { /* clones every Card in nonGoldCards */
			playerClone.addCard(card.clone());
		}
		playerClone.setSwords(this.getSwords());

		int[] clonedBonus = new int[5];
		for(int i = 0; i < clonedBonus.length; i++) {
			clonedBonus[i] = traderBonus[i];
		}
		playerClone.setTraderBonus(clonedBonus);
		playerClone.setVictoryPoints(this.getVictoryPoints());
		playerClone.setAvatarID(this.getAvatarID());
		return playerClone;
	}

	/**
	 * get-Method Name
	 * @return name
	 */
	public String getName() {return name;}

	/**
	 * set-Method Name
	 * @param name name of the player
	 */
	public void setName(String name) {this.name = name;}

	/**
	 * get-Method Swords
	 * @return swords
	 */
	public int getSwords() {return swords;}

	/**
	 * set-Method Swords
	 * @param swords swords
	 */
	public void setSwords(int swords) {this.swords = swords;}

	/**
	 * get-Method Did Expedition
	 * @return didExp
	 */
	public boolean getDidExp() {return didExp;}

	/**
	 * set-Method Did Expedition
	 * @param didExp did Expedition
	 */
	public void setDidExp(boolean didExp) {this.didExp = didExp;}

	/**
	 * get-Method VictoryPoints
	 * @return victoryPoints
	 */
	public int getVictoryPoints() {return victoryPoints;}

	/**
	 * set-Method VictoryPoints
	 * @param victoryPoints victoryPoints
	 */
	public void setVictoryPoints(int victoryPoints) {this.victoryPoints = victoryPoints;}

	/**
	 * get-Method TraderBonus
	 * @return traderBonus
	 */
	public int[] getTraderBonus() {return traderBonus;}

	/**
	 * set-Method Trader Bonus
	 * @param traderBonus traderBonus
	 */
	public void setTraderBonus(int[] traderBonus) {this.traderBonus = traderBonus;}

	/**
	 * get-Method Eligible for Highscore
	 * @return eligibleForHighscore
	 */
	public boolean getEligibleForHighscore() {return eligibleForHighscore;}

	/**
	 * set-Method Eligible for Highscore
	 * @param eligibleForHighscore eligible for highscore
	 */
	public void setEligibleForHighscore(boolean eligibleForHighscore) {this.eligibleForHighscore = eligibleForHighscore;}

	/**
	 * get-Method PlayerType
	 * @return playerType
	 */
	public PlayerType getPlayerType() {return playerType;}

	/**
	 * set-Method Player Type
	 * @param playerType playerType
	 */
	public void setPlayerType(PlayerType playerType) {this.playerType = playerType;}

	/**
	 * get-Method Non Gold Cards
	 * @return nonGoldCards
	 */
	public ArrayList<Card> getNonGoldCards() {return nonGoldCards;}

	/**
	 * set-Method Non Gold Cards
	 * @param nonGoldCards non GoldCards
	 */
	public void setNonGoldCards(ArrayList<Card> nonGoldCards) {this.nonGoldCards = nonGoldCards;}

	/**
	 * get-Method Is Active
	 * @return isActive
	 */
	public boolean getIsActive() {return isActive;}

	/**
	 * set-Method Is Active
	 * @param isActive is active
	 */
	public void setIsActive(boolean isActive) {this.isActive = isActive;}

	/**
	 * get-Method Amount of Moves
	 * @return moveCount
	 */
	public int getMoveCount() {return moveCount;}

	/**
	 * set-Method Amount of Moves
	 * @param moveCount how often a player has the chance to pull a Card
	 */
	public void setMoveCount(int moveCount) {this.moveCount = moveCount;}

	/**
	 * get-Method Gold Cards
	 * @return goldCards
	 */
	public ArrayList<Card> getGoldCards() {return goldCards;}

	/**
	 * set-Method Gold Cards
	 * @param goldCards GoldCards
	 */
	public void setGoldCards(ArrayList<Card> goldCards) {this.goldCards = goldCards;}

	/**
	 * get Coins, based on the goldCards in the Hands
	 * @return int coins
	 */
	public int getCoins(){
		return  goldCards.size();
	}

	/**
	 * get-Method playerHelp
	 * @return the playerHelp object
	 */
	public PlayerHelp getPlayerHelp() {
		return playerHelp;
	}

	/**
	 * set-Method playerHelp
	 * @param playerHelp the playerHelp object
	 */
	public void setPlayerHelp(PlayerHelp playerHelp) {
		this.playerHelp = playerHelp;
	}

	/**
	 * id of the profile pic of the player
	 * @return the reference of the id.
	 */
	public int getAvatarID() {
		return avatarID;
	}

	/**
	 * set the avatarID of the players profile
	 * @param avatarID int avatar id
	 */
	public void setAvatarID(int avatarID) {
		this.avatarID = avatarID;
	}
}
