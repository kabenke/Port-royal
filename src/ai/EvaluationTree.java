package ai;

import model.*;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import java.lang.reflect.Array;
import java.nio.file.attribute.AclEntryPermission;
import java.util.ArrayList;


public class EvaluationTree {


    private TreeNode root;


    private ArrayList<EvaluationTree> children;


    private int height;

    private Match match;

    private int layer;


    public EvaluationTree(int height, int layer, Match match) {
        this.layer = layer;
        this.height = height;
        this.children = new ArrayList<>();
        this.root = new TreeNode(new ArrayList<>(), match);
        this.match = match;
    }


    public boolean addChild(ArrayList<Move> moves) {
        if(height > 0) {
            EvaluationTree child = new EvaluationTree(this.height - 1, this.layer + 1, moves.get(0).getMatchBeforeMove());
            child.getRoot().setMoves(moves);
            child.getRoot().makeMove();
            this.children.add(child);
            return true;
        }
        return false;
    }


    public EvaluationTree getChild(int index) {
        if(index < this.children.size()) {
            return this.children.get(index);
        }
        throw new IllegalArgumentException("invalid index");
    }


    public void initializeTree(int height) {
        if(height > 0) {
            ArrayList<ArrayList<Move>> possibleMoves = possibleMoves(this.root.getMatchState());
            for(ArrayList<Move> child : possibleMoves) {
                addChild(child);
            }
            for(EvaluationTree child : children) {
                child.initializeTree(this.height - 1);
            }
        }
    }




    public ArrayList <ArrayList<Move>> possibleMoves(Match givenMatch) {

        this.match = givenMatch.clone();

        Turn activeTurn = this.match.getActiveTurn();
        ArrayList<ArrayList<Move>> allMoves = new ArrayList<>();
        ArrayList<Move> movesToAdd = new ArrayList<>();
        Player activePlayer = activeTurn.getActivePlayer();
        Player actingPlayer = activeTurn.getActingPlayer();
        ArrayList<Expedition> expeditionDisplay = match.getExpeditionDisplay();
        boolean playerIsActive = activePlayer.equals(activeTurn.getActingPlayer());

        //Phase 1
        if (match.getActiveTurn().getIsPhaseDiscover()) {
            ArrayList<Card> habourDisplay = activeTurn.getHarbourDisplay();
            movesToAdd = new ArrayList<>();

            if(activeTurn.getHarbourDisplay().size()>0){
                Card card = activeTurn.getHarbourDisplay().get(activeTurn.getHarbourDisplay().size()-1);
                if (card instanceof Ship) {
                    Ship ship = (Ship) card;
                    if (ship.getSwords() <= actingPlayer.getSwords()) {
                       System.out.println("SHIP TO FEND OFF ADDED");
                      movesToAdd.add(new Move(actingPlayer, card, true, match));
                    }
                }
            }

            // fulfillExpedition
            for(int i=0; i< expeditionDisplay.size(); i++) {
                Expedition expedition = expeditionDisplay.get(i);
                if (expedition.canFulfill(actingPlayer)) {
                    Match cloneMatch =match.clone();
                    movesToAdd.add(new Move(actingPlayer, expedition, true, cloneMatch));
                }
            }

            movesToAdd.add(new Move(actingPlayer, null, true, match)); // changePhase

            allMoves.add(movesToAdd);

        }

        else{ //Phase 2

            ArrayList<Card> habourDisplay = match.getActiveTurn().getHarbourDisplay();
            ArrayList<ArrayList<Move>> possibleMoves = new ArrayList<>();

            ArrayList<Move> movesUntilNow = new ArrayList<>();


                Match matchClone = match.clone();
                possibleMoves.add(0, possibleMovesPhase2(movesUntilNow, matchClone));


            for(int i=0; i< expeditionDisplay.size(); i++) {
                Expedition expedition = expeditionDisplay.get(i);
                if (canFulfillPlusMoves(actingPlayer, possibleMoves.get(0), expedition)) {
                    Match cloneMatch =match.clone();
                    int size = possibleMoves.get(0).size();
                    possibleMoves.get(0).add(size, new Move(actingPlayer, expedition, true, cloneMatch));
                }
            }

            allMoves =possibleMoves;

        }
        return allMoves;
    }


    private ArrayList<ArrayList<Move>> removeRedundantArrays (ArrayList<ArrayList<Move>> movess){
        ArrayList<ArrayList<Move>> result = new ArrayList<>();

        if(movess.size()<2){return movess;}

        for(int i=0; i<movess.size()-1;i++){
            boolean same = true;
            for(int j=0; j<movess.get(i).size(); j++){
                if(cardInMoves(movess.get(i),movess.get(i+1).get(j).getCard())){
                    same = same&true;}
                else{
                    same = same&false;
                }
            }
            if (same) {result.add(movess.get(i));
            }
        }

        return result;
    }


    private ArrayList<Move> possibleMovesPhase2(ArrayList<Move> movesUN, Match match){

        ArrayList<Move> possibleMoves = new ArrayList<>();

        ArrayList<Move> movesUntilNow = movesUN;
        ArrayList<Move> nextMoves = new ArrayList<>();
        Turn activeTurn = match.getActiveTurn();
        Player actingPlayer = activeTurn.getActingPlayer();
        boolean isActive = activeTurn.getActivePlayer().equals(actingPlayer);

        for(int h=0; h<match.getActiveTurn().getHarbourDisplay().size(); h++) {
            //if(h==i){
            nextMoves = new ArrayList<>();
            Card card = match.getActiveTurn().getHarbourDisplay().get(h);
            if (card instanceof Ship) {
                Ship ship = (Ship) card;

                if (!cardInMoves(movesUntilNow, ship)) {
                    nextMoves.add(new Move(actingPlayer, ship, isActive, match));
                    movesUntilNow.addAll(nextMoves);
                    System.out.println("SHIP Sold: " + ship.getColor() + "  with " + ship.getCoins() + "coins");
                }
                activeTurn.getHarbourDisplay().remove(ship);

                nextMoves.addAll(nextMoves = possibleMovesPhase2(movesUntilNow, match));

                return nextMoves;

            }
            if (card instanceof Person) {
                Person person = (Person) card;
                if (canAffordWithMoves(actingPlayer, movesUntilNow, activeTurn, person)) {

                    if (!cardInMoves(movesUntilNow, person)) {
                        nextMoves.add(new Move(actingPlayer, person, isActive, match));
                        movesUntilNow.addAll(nextMoves);
                        System.out.println("person hired:  " + person.getAbility() + " swords:  " + person.getSwords());
                    }
                    activeTurn.getHarbourDisplay().remove(person);

                    nextMoves.addAll(nextMoves = possibleMovesPhase2(movesUntilNow, match));

                    return nextMoves;
                }
            }

        }
        //System.out.println("keine person oder kein Ship keine person oder kein Ship keine person oder kein Ship ");
        //if(nextMoves.size()==0 | activeTurn.getHarbourDisplay().size()==0){
         //   movesUntilNow = new ArrayList<>();}
        return  movesUntilNow;
    }





    private boolean cardInMoves(ArrayList<Move> moves, Card card){
        for (int i=0; i<moves.size(); i++){
            if(moves.get(i).getCard() instanceof  Ship && card instanceof Ship){
                Ship ship1 = (Ship)moves.get(i).getCard();
                Ship ship2 = (Ship)card;

                if(ship1.getCoins()==ship2.getCoins()) {
                    if(ship1.getSwords()==ship2.getSwords()){
                        if(ship1.getColor()==ship2.getColor()){
                            return true; }
                    }
                }

            }
            if(moves.get(i).getCard() instanceof  Person && card instanceof Person){
                Person person1 = (Person) moves.get(i).getCard();
                Person person2 = (Person)card;

                if(person1.getAbility()==person2.getAbility()){
                    if(person1.getSwords()==person2.getSwords()) {
                        if(person1.getVictoryPoints()==person2.getVictoryPoints()){
                            if(person1.getColor()==person2.getColor()){
                                return true;
                            }
                        }
                    }
                }

            }
        }
        return  false;
    }

    public boolean canAffordWithMoves(Player player, ArrayList<Move> moves, Turn turn, Person person)throws IllegalArgumentException {
        int coins = person.getCoins();
        int impactRecentMoves =0;
        Ship ship = new Ship(2, 2, Color.UNDEFINED); // because Java is not checking its initialized

        if(player == null){
            throw new IllegalArgumentException();
        }

        int mademoiselle = 0;
        ArrayList<Card> crew = player.getNonGoldCards();
        for(Card card : crew){
            if(((Person)card).getAbility().equals(Ability.MADEMOISELLE)){
                mademoiselle += 1;
            }
        }

        for(Move move:  moves) {
            Card card = move.getCard();

            if (move.getCard() instanceof Ship) {
                ship = (Ship) card;
                impactRecentMoves = ship.getCoins();
            }
            if (move.getCard() instanceof Person) {
                person = (Person)card;
                if(person.getAbility().equals(Ability.MADEMOISELLE)) {
                    mademoiselle +=1;
                }
            }
        }


        if(turn.getActingPlayer().equals(turn.getActivePlayer())){
            return player.getGoldCards().size() + coins + mademoiselle + impactRecentMoves >= 0;
        }
        return player.getGoldCards().size() + coins + mademoiselle + impactRecentMoves> 0;
    }




    public boolean canFulfillPlusMoves(Player player, ArrayList<Move> move, Expedition expedition)throws IllegalArgumentException {
        ArrayList<Ability> artifacts = expedition.getArtifacts();
        if(player == null){
            throw new IllegalArgumentException();
        }

        ArrayList<Card> playerCards = player.getNonGoldCards();

        int countSettler = countGivenArtifacts(playerCards, Ability.SETTLER);
            countSettler += countArtifactsInMoves(move,Ability.SETTLER);
        int countCaptain = countGivenArtifacts(playerCards, Ability.CAPTAIN);
            countCaptain += countArtifactsInMoves(move,Ability.CAPTAIN);
        int countPriest = countGivenArtifacts(playerCards, Ability.PRIEST);
            countPriest += countArtifactsInMoves(move,Ability.PRIEST);
        int countJoker = countGivenArtifacts(playerCards, Ability.JOKER);
            countJoker += countArtifactsInMoves(move,Ability.JOKER);

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


    public boolean canFulfill(Player player, Expedition expedition)throws IllegalArgumentException {

        if(player == null){
            throw new IllegalArgumentException();
        }

        ArrayList<Card> playerCards = player.getNonGoldCards();
        ArrayList<Ability> artifacts = expedition.getArtifacts();

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


    private int countArtifactsInMoves(ArrayList<Move> moves, Ability artifact){
        int count = 0;
        for(int i=0; i<moves.size(); i++){
            if(moves.get(i).getCard() instanceof Person) {
                Person person = (Person)moves.get(i).getCard();
                if(person.getAbility().equals(artifact)){
                    count++;
                }
            }
        }
        return count;
    }


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


    private int countNeededArtifacts (ArrayList<Ability> artifacts, Ability ability){
        int resultCount = 0;
        for (int i = 0; i < artifacts.size() ; i++) {
            if(artifacts.get(i).equals(ability)) {
                resultCount++;}
        }
        return resultCount;
    }




    public int getChildCount() {
        return this.children.size();
    }


    public boolean isLeaf() {
        return this.children.size() == 0;
    }


    public TreeNode getRoot() {
        return root;
    }


    public void setRoot(TreeNode root) {
        this.root = root;
    }


    public ArrayList<EvaluationTree> getChildren() {
        return children;
    }


    public void setChildren(ArrayList<EvaluationTree> children) {
        this.children = children;
    }


    public int getHeight() {
        return height;
    }


    public void setHeight(int height) {
        this.height = height;
    }


    public Match getMatch() {
        return match;
    }


    public void setMatch(Match match) {
        this.match = match;
    }


    public int getLayer() {
        return layer;
    }


    public void setLayer(int layer) {
        this.layer = layer;
    }
}
