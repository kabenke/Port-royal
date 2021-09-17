package ai;

import model.*;

import java.util.ArrayList;


public class TreeNode {


    private int score;


    private ArrayList<Move> moves;


    private Match matchState;


    private boolean ourTurn;


    private TreeNodeHelp treeNodeHelp;


    public TreeNode(ArrayList<Move> moves, Match match) {
        this.score = -10000;
        this.moves = moves;
        this.treeNodeHelp = new TreeNodeHelp(this);
        matchState = match;
        makeMove();
    }


    public void makeMove() {
        Match match = matchState;
        Turn activeTurn = match.getActiveTurn();
        for(Move move : moves) {
            Player player = move.getPlayer();
            Card card = move.getCard();

            //Phase 1
            if(match.getActiveTurn().getIsPhaseDiscover()) {
                if(card instanceof Expedition && match.getExpeditionDisplay().contains(card)) {
                    treeNodeHelp.fulfillExpedition(move, match);
                } else if(move.getCard() == null) { // Change phase in turn 1
                    match.getActiveTurn().setIsPhaseDiscover(!match.getActiveTurn().getIsPhaseDiscover());
                }
            } else { //Phase 2
                if(card instanceof Person) {
                    treeNodeHelp.hireCrew(move, activeTurn);
                } else if(card instanceof Ship) {
                    treeNodeHelp.sellShip(move, activeTurn);
                } else if(card instanceof Expedition) { //Expedition
                    treeNodeHelp.fulfillExpedition(move, match);
                } else if(card == null) { //end turn in phase 2
                    //Code den die GUI verwendet um den nächsten actingPlayer zubestimmen
                }
            }

            move.setMatchAfterMove(match);
            match.getActiveTurn().getMoves().add(move);
        }

    }


    public int getScore() {
        return score;
    }


    public void setScore(int score) {
        this.score = score;
    }


    public ArrayList<Move> getMoves() {
        return moves;
    }


    public void setMoves(ArrayList<Move> move) {
        this.moves = move;
    }


    public Match getMatchState() {
        return matchState;
    }


    public void setMatchState(Match matchState) {
        this.matchState = matchState;
    }


    public boolean isOurTurn() {
        return ourTurn;
    }


    public void setOurTurn(boolean ourTurn) {
        this.ourTurn = ourTurn;
    }


    public TreeNodeHelp getTreeNodeHelp() {
        return treeNodeHelp;
    }


    public void setTreeNodeHelp(TreeNodeHelp treeNodeHelp) {
        this.treeNodeHelp = treeNodeHelp;
    }
}
