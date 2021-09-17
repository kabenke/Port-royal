package model;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * This class represents the administration of PortRoyal.
 */
public class PortRoyal implements Serializable {

	/**
	 * The active match
	 */
	private Match activeMatch;

	/**
	 * A list of all unfinished matches
	 */
	private ArrayList<Match> matchList;

	/**
	 * A list of the best 10 scores
	 */
	private ArrayList<Score> highscore;

	/**
	 * Constructor of our PortRoyal game
	 */
	public PortRoyal() {
		this.matchList = new ArrayList<>();
		this.highscore = new ArrayList<>();
	}

	/**
	 * This method adds a new match to the list of all unfinished matches
	 * @param match the match to be added
	 */
	public void addMatch(Match match) {
		if(match == null) {
			throw new IllegalArgumentException();
		}
		matchList.add(match);
	}

	/**
	 * This method adds a new score to the highscore list
	 * @param score the score to be added
	 */
	public void addScore(Score score) {
		if(score == null) {
			throw new IllegalArgumentException();
		}
		highscore.add(score);
	}

	/**
	 * This method removes the lowest score from the highscore list
	 */
	public void removeScore(){
		if(highscore.size() != 0){
			highscore.remove(0);
		}
	}

	/**
	 * This method removes a match from the match list
	 * @param match the match to be removed
	 */
	public void removeMatch(Match match) {
		if(matchList.size() != 0){
			matchList.remove(match);
		}
	}

	/**
	 * This method is used to sort the highscore list after adding a new score
	 */
	public void sortScore() {
		for(int i = highscore.size()-1; i>0; i--){
			for(int n = 0; n < i;n++){
				int left = highscore.get(n).getScore();
				int right = highscore.get(n+1).getScore();

				if(left > right){
					highscore.get(n+1).setScore(left);
					highscore.get(n).setScore(right);
				}
			}
		}
	}

	/**
	 * get-Method active match
	 * @return activeMatch
	 */
	public Match getActiveMatch() {return activeMatch;}

	/**
	 * set-Method active match
	 * @param activeMatch the active Match
	 */
	public void setActiveMatch(Match activeMatch) {this.activeMatch = activeMatch;}

	/**
	 * get-Method match list
	 * @return matchList
	 */
	public ArrayList<Match> getMatchList() {return matchList;}

	/**
	 * set-Method match list
	 * @param matchList list of matches
	 */
	public void setMatchList(ArrayList<Match> matchList) {this.matchList = matchList;}

	/**
	 * get-Method highscore
	 * @return highscore
	 */
	public ArrayList<Score> getHighscore() {return highscore;}

	/**
	 * set-Method highscore
	 * @param highscore highscore
	 */
	public void setHighscore(ArrayList<Score> highscore) {this.highscore = highscore;}
}
