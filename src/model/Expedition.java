package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents an expedition card that defines an Expedition that can be fulfilled.
 */
public class Expedition extends Card implements Serializable {

	/**
	 * An ArrayList of the artifacts which are needed to fulfill the Expedition
	 */
	private ArrayList<Ability> artifacts;

	/**
	 * The victoryPoints on an Expedition card
	 */
	private int victoryPoints;

	/**
	 * Constructor for an Expedition card
	 * @param coins the rewards coins
	 * @param artifacts the artifacts for some abilities
	 * @param victoryPoints toCollect for winning
	 */
	public Expedition(int coins, ArrayList<Ability> artifacts, int victoryPoints) {
		this.coins = coins;
		this.artifacts = artifacts;
		this.victoryPoints = victoryPoints;
		this.isFaceUp = false;
	}

	/**
	 * check if a player can fullFill an expedition
	 * @param player enter the active player
	 * @return gives true for can fulfill
	 */

	public boolean canFulfill(Player player)throws IllegalArgumentException {

		if(player == null){
			throw new IllegalArgumentException();
		}

		ArrayList<Card> playerCards = player.getNonGoldCards();

		int countSettler = countGivenArtifacts(playerCards, Ability.SETTLER);
		int countCaptain = countGivenArtifacts(playerCards, Ability.CAPTAIN);
		int countPriest = countGivenArtifacts(playerCards, Ability.PRIEST);
		int countJoker = countGivenArtifacts(playerCards, Ability.JOKER);

		int needSettler = countNeededArtifacts( artifacts,  Ability.SETTLER);
		int needCaptain = countNeededArtifacts( artifacts,  Ability.CAPTAIN);
		int needPriest = countNeededArtifacts( artifacts,  Ability.PRIEST);

		int neededJoker = 0;

		if (needSettler - countSettler >0) {
			neededJoker = needSettler - countSettler;
		}
		if (needCaptain - countCaptain >0) {
			neededJoker += needCaptain - countCaptain;
		}
		if (needPriest - countPriest >0) {
			neededJoker += needPriest - countPriest;
		}
	return (neededJoker <= countJoker);
	}

	/**
	 * help method for calculating players artifacts
	 * @param playerCards from the player to check
	 * @param ability the ability to count
	 * @return amount of an given artifact
	 */
	private int countGivenArtifacts (ArrayList<Card> playerCards, Ability ability) {
		int resultCount = 0;

		for(int i=0; i<playerCards.size(); i++) {

			Card card = playerCards.get(i);

			if (card instanceof Person) {
				if (((Person) card).getAbility().equals(ability)) {
					resultCount++;
				}
			}
		}
		return resultCount;
	}

	/**
	 * helpmethod to check can fulfill
	 * @param artifacts the artifact array to check
	 * @param ability the ability to count
	 * @return amount of artifact in abilities
	 */
	private int countNeededArtifacts (ArrayList<Ability> artifacts, Ability ability){
		int resultCount = 0;
		for (int i = 0; i < artifacts.size() ; i++) {
			if(artifacts.get(i).equals(ability)) {
				resultCount++;}
		}
		return resultCount;
	}

	/**
	 * Clone method for a Expedition
	 * @return a copy of the Expedition object
	 */
	@Override
	public Expedition clone() {
		 Expedition expedition = new Expedition(this.coins, this.artifacts, this.victoryPoints);
		 expedition.isFaceUp = this.isFaceUp;
		 return expedition;
	}

	/**
	 * Get-Method for the VictoryPoints on an Expedition
	 * @return gives the amount of victoryPoints back
	 */
	public int getVictoryPoints() {
		return victoryPoints;
	}

	/**
	 * Set-Method for the VictoryPoints on an Expedition
	 * @param victoryPoints set the victory points
	 */
	public void setVictoryPoints(int victoryPoints){
		this.victoryPoints = victoryPoints;
	}

	/**
	 * Get-Method for the needed artifacts to fulfill an Expedition
	 * @return returns the necessary amount of artifacts
	 */
	public ArrayList<Ability> getArtifacts(){
		return artifacts;
	}

	/**
	 * Set-Method for the needed artifacts to fulfill an Expedition
	 * @param artifacts return the necessary amount of artifacts
	 */
	public void setArtifacts(ArrayList<Ability> artifacts) {
		this.artifacts = artifacts;
	}

	/**
	 * Method to convert an Expedition card to a String
	 * @return returns the card as a String
	 */
	@Override
	public String toString(){
		String str = "EXPEDITION: ";
		str += getCoins()+"C ";
		str += victoryPoints+"V ";
		for(int i=0; i<getArtifacts().size(); i++){
			if(getArtifacts().get(i).equals(Ability.CAPTAIN)){ str += "Cap"; }
			else if(getArtifacts().get(i).equals(Ability.SETTLER)){ str += "Set"; }
			else if(getArtifacts().get(i).equals(Ability.PRIEST)){ str += "Pri"; }
		}
		return str;
	}


}
