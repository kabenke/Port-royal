package ai;

import model.*;

import java.util.ArrayList;


public class TreeNodeHelp {

    private TreeNode treeNode;


    public TreeNodeHelp(TreeNode treeNode) {
        this.treeNode = treeNode;
    }


    public void hireCrew(Move move, Turn turn) {

        Card cardToPayActive;
        Person person = (Person)move.getCard();
        if(!person.canAfford(move.getPlayer())) {
            throw new IllegalArgumentException("can not afford person");}

        adjustCoins(move.getPlayer(), move.getCard().getCoins(), turn);
        if(!move.getPlayer().equals(turn.getActivePlayer())) {
            cardToPayActive = move.getPlayer().getGoldCards().remove(0);
            turn.getActivePlayer().addCard(cardToPayActive);
        }
        if(turn.getHarbourDisplay().remove(move.getCard())) {
            turn.getMatch().toDiscardPile(move.getCard());
        }

        /* adjusting attributes */
        Player player = move.getPlayer();
        Person card = (Person)move.getCard();
        player.addCard(person);
        int oldSwords = player.getSwords();
        player.setSwords(oldSwords + card.getSwords());
        int oldVictoryPoints = player.getVictoryPoints();
        player.setVictoryPoints(oldVictoryPoints + card.getVictoryPoints());
        if(card.getAbility().equals(Ability.TRADER)) {
            switch (card.getColor()) {
                case BLUE: player.getTraderBonus()[0]++;
                    break;

                case RED: player.getTraderBonus()[1]++;
                    break;

                case GREEN: player.getTraderBonus()[2]++;
                    break;

                case BLACK: player.getTraderBonus()[3]++;
                    break;

                case YELLOW: player.getTraderBonus()[4]++;
                    break;

                default: /* No trader so cant happen */
            }
        }
    }


    public void fulfillExpedition(Move move, Match match) {
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

            Player player = move.getPlayer();
            ArrayList<Ability> artifacts = expedition.getArtifacts();
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

            /* adjusting coins and victoryPoints */
            move.getPlayer().addCard(removedExp);
            ArrayList<Turn> turns = match.getTurnHistory();
            adjustCoins(move.getPlayer(), removedExp.getCoins(), turns.get(turns.size()-1));
            int oldVP = move.getPlayer().getVictoryPoints();
            move.getPlayer().setVictoryPoints(oldVP + ((Expedition) move.getCard()).getVictoryPoints());
        }
        else
        {
            throw new IllegalArgumentException("not a Expedition card");
        }
    }


    public void removeCards(ArrayList<Integer> removeCards, Match match, Player player) {
        Person removedSettler, removedPriest, removedCaptain, removedJoker;
        ArrayList<Card> playerCards = player.getNonGoldCards();

        for(int i=0; i<removeCards.get(0);i++){
            removedSettler = removeFirstArtifact(playerCards,Ability.SETTLER);
            match.toDiscardPile(removedSettler);
        }
        for(int i=0; i<removeCards.get(1);i++){
            removedPriest = removeFirstArtifact(playerCards,Ability.PRIEST);
            match.toDiscardPile(removedPriest);
        }
        for(int i=0; i<removeCards.get(2);i++){
            removedCaptain = removeFirstArtifact(playerCards,Ability.CAPTAIN);
            match.toDiscardPile(removedCaptain);
        }
        for(int i=0; i<removeCards.get(3);i++){
            removedJoker = removeFirstArtifact(playerCards,Ability.JOKER);
            match.toDiscardPile(removedJoker);
        }
    }


    public int countNeededArtifacts (ArrayList<Ability> artifacts, Ability ability){
        int resultCount = 0;
        for (int i = 0; i < artifacts.size() ; i++) {
            if(artifacts.get(i).equals(ability)) {
                resultCount++;}
        }
        return resultCount;
    }


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
                player.addCard(turn.getMatch().drawGoldCard());
            }
        }
    }


    public void sellShip(Move move, Turn turn) {

        Card cardToPayActive;
        if(move.getCard() instanceof Ship) {

            if(turn.getHarbourDisplay().remove(move.getCard())) {
                turn.getMatch().toDiscardPile(move.getCard());
            }

            adjustCoins(move.getPlayer(), move.getCard().getCoins(), turn);

            if (!move.getPlayer().equals(turn.getActivePlayer())) {
                cardToPayActive = move.getPlayer().getGoldCards().remove(0);
                turn.getActivePlayer().addCard(cardToPayActive);
            }

        }
        else {throw new IllegalArgumentException("not a Ship Card");}
    }


    public TreeNode getTreeNode() {
        return treeNode;
    }


    public void setTreeNode(TreeNode treeNode) {
        this.treeNode = treeNode;
    }
}
