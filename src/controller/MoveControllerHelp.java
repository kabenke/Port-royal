package controller;

import model.*;

import java.util.ArrayList;

/**
 * helping class to avoid a god class.
 */
public class MoveControllerHelp {

    private MoveController moveController;

    /**
     * constructor that creates a help object for a moveController
     * @param moveController the moveController
     */
    public MoveControllerHelp(MoveController moveController) {
        this.moveController = moveController;
    }

    /**
     * helpMethod to check can fullfill
     * @param artifacts the artifact array to check
     * @param ability the ability to count
     * @return amount of artifact in abilities
     */
    public int countNeededArtifacts (ArrayList<Ability> artifacts, Ability ability){
        int resultCount = 0;
        for (int i = 0; i < artifacts.size() ; i++) {
            if(artifacts.get(i).equals(ability)) {
                resultCount++;}
        }
        return resultCount;
    }

    /**
     * help method for fulfillExpedition
     * @param playerCards playerCards of the move Player
     * @param ability ability to count
     * @return return the number of abilities
     */
    public int countGivenArtifacts (ArrayList<Card> playerCards, Ability ability) {
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
     * remove the first/ oldest Artifact
     * @param playerCards ArrayList card
     * @param artifact artifacts to remove
     * @return the removed artifact
     */
    public Person removeFirstArtifact(ArrayList<Card> playerCards, Ability artifact){
        for(int i=0; i<playerCards.size(); i++) {
            if(playerCards.get(i) instanceof Person){
                Person person = (Person) playerCards.get(i);
                if(artifact.equals(person.getAbility())) {
                    playerCards.remove(i);
                    return person;
                }
            }
        }
        throw new IllegalArgumentException("Artifact nicht gefunden");

    }

    /**
     * adjusts the coins of the player, positive means new coins and negative means discardCards
     * @param player player who get more or less coins
     * @param coins amount of coins positive is plus, negative minus
     * @param turn the turn in which we want to adjust the coins
     */
    public void adjustCoins(Player player, int coins, Turn turn) {
        if(coins < 0) {
            for(int i = 0; i < (-coins); i++) {
                Card removed = player.getGoldCards().remove(0);
                removed.flip();
                turn.getMatch().toDiscardPile(removed);
            }
        }
        else{
            for(int i = 0; i < coins; i++){
                moveController.getMainController().getMatchController().checkDrawPile(turn.getMatch());
                player.addCard(turn.getMatch().drawGoldCard());
            }
        }
    }

    /**
     * This method checks if the acting player has more moves to sell a ship or hire a Person
     * @param player the acting player
     * @param turn the active turn
     * @return true if the acting player has more moves, false if not
     */
    public boolean hasMoreMoves(Player player, Turn turn){
        int[] playerMoveNumber = turn.getPlayerMoveNumber();
        ArrayList<Player> playerList = turn.getPlayerList();

        int playerMoves = 0;
        for(int i = 0; i < playerList.size(); i++){
            if(playerList.get(i) == turn.getActingPlayer()){
                playerMoves = playerMoveNumber[i];
            }
        }
        if(playerMoves > 0){
            return true;
        }
        return false;
    }

    /**
     * This method is used to adjust the moveCount of a player after he took a card
     * @param turn the active turn
     */
    public void adjustMoveCount(Turn turn){
        int[] playerMoveNumber = turn.getPlayerMoveNumber();
        ArrayList<Player> playerList = turn.getPlayerList();

        for(int i = 0; i < playerList.size(); i++){
            if(playerList.get(i) == turn.getActingPlayer()){
                playerMoveNumber[i]--;
            }
        }
        /******************************/
        turn.setPlayerMoveNumber(playerMoveNumber);
    }

    /**
     * method that removes the artifact cards
     * @param removeCards ArrayList containing the amounts of cards to remove
     * @param match the match
     * @param player the player to remove the cards for
     */
    public void removeCards(ArrayList<Integer> removeCards, Match match, Player player) {
        Person removedSettler, removedPriest, removedCaptain, removedJoker;
        ArrayList<Card> playerCards = player.getNonGoldCards();

        for (int i = 0; i < removeCards.get(0); i++) {
            removedSettler = removeFirstArtifact(playerCards, Ability.SETTLER);
            match.toDiscardPile(removedSettler);
        }
        for (int i = 0; i < removeCards.get(1); i++) {
            removedPriest = removeFirstArtifact(playerCards, Ability.PRIEST);
            match.toDiscardPile(removedPriest);
        }
        for (int i = 0; i < removeCards.get(2); i++) {
            removedCaptain = removeFirstArtifact(playerCards, Ability.CAPTAIN);
            match.toDiscardPile(removedCaptain);
        }
        for (int i = 0; i < removeCards.get(3); i++) {
            removedJoker = removeFirstArtifact(playerCards, Ability.JOKER);
            match.toDiscardPile(removedJoker);
        }
    }

    /**
     * This method is used to calculate the new VictoryPoints of a player after he fulfills an Expedition
     * @param move the move in which the player fulfills the Expedition
     */
    public void newVictoryPoints(Move move){
        int victoryPoints = 0;
        ArrayList<Card> hand = move.getPlayer().getNonGoldCards();
        for(Card card : hand){
            if(card instanceof Person) {
                victoryPoints += ((Person) card).getVictoryPoints();
            }
            if(card instanceof Expedition){
                victoryPoints += ((Expedition) card).getVictoryPoints();
            }
        }
        move.getPlayer().setVictoryPoints(victoryPoints);
    }

    /**
     * This method is used to adjust the cards of a player after he fulfills an Expedition
     * @param move the move in which he fulfills an Expedition
     * @param match the active match
     * @param artifacts the artifacts he used to fulfill the Expedition
     */
    public void adjustCards(Move move, Match match, ArrayList<Ability> artifacts) {

        Player player = move.getPlayer();
        ArrayList<Card> playerCards = player.getNonGoldCards();

        int countSettler = countGivenArtifacts(playerCards, Ability.SETTLER);
        int countCaptain = countGivenArtifacts(playerCards, Ability.CAPTAIN);
        int countPriest = countGivenArtifacts(playerCards, Ability.PRIEST);

        int needSettler = countNeededArtifacts( artifacts,  Ability.SETTLER);
        int needCaptain = countNeededArtifacts( artifacts,  Ability.CAPTAIN);
        int needPriest = countNeededArtifacts( artifacts,  Ability.PRIEST);

        int neededJoker = 0;

        int removeSettler = needSettler;
        int removeCaptain = needCaptain;
        int removePriest = needPriest;

        if (needSettler - countSettler >0) {
            neededJoker = needSettler - countSettler;
            removeSettler = countSettler;
        }
        if (needCaptain - countCaptain >0) {
            neededJoker += needCaptain - countCaptain;
            removeCaptain = countCaptain;
        }
        if (needPriest - countPriest >0) {
            neededJoker += needPriest - countPriest;
            removePriest =countPriest;
        }

        ArrayList<Integer> removeCards = new ArrayList<>();
        removeCards.add(removeSettler);
        removeCards.add(removePriest);
        removeCards.add(removeCaptain);
        removeCards.add(neededJoker);

        removeCards(removeCards, match, player);
    }

    /**
     * get-Method moveController
     * @return the moveController to provide with helping methods
     */
    public MoveController getMoveController() {
        return moveController;
    }

    /**
     * set-Method moveController
     * @param moveController the moveController to provide with helping methods
     */
    public void setMoveController(MoveController moveController) {
        this.moveController = moveController;
    }
}
