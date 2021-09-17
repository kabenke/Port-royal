package model;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

import java.util.ArrayList;

/**
 * This class is used to test the methods of PortRoyal.
 */
public class PortRoyalTest {

    private PortRoyal portRoyal1, portRoyal2, portRoyal3;
    private ArrayList<Match> matchList1, matchList2;
    private ArrayList<Score> highscore1, highscore2;
    private Match match1, match2;
    private Score score1, score2;
    private Player player1, player2;

    /**
     * Creates a new test environment before each test method
     * @throws Exception Possible Exceptions with setUp
     */
    @Before
    public void setUp() throws Exception{
        this.player1 = new Player();
        this.player2 = new Player();
        this.matchList1 = new ArrayList<Match>();
        this.highscore1 = new ArrayList<Score>();
        this.matchList2 = new ArrayList<Match>();
        this.highscore2 = new ArrayList<Score>();
        this.match1 = new Match();
        this.match2 = new Match();
        this.score1 = new Score(player1);
        this.score2 = new Score(player2);

        this.portRoyal1 = new PortRoyal();
        this.portRoyal2 = new PortRoyal();

        score1.setScore(200);
        score2.setScore(100);

        matchList2.add(match1);
        matchList2.add(match2);
        highscore2.add(score1);
        highscore2.add(score2);

        portRoyal1.setMatchList(matchList1);
        portRoyal1.setHighscore(highscore1);
        portRoyal2.setMatchList(matchList2);
        portRoyal2.setHighscore(highscore2);

    }

    /**
     * This method is used to test the Constructor of PortRoyal
     */
    @Test
    public void testConstructor(){
        portRoyal3 = new PortRoyal();
        Assert.assertNotEquals(null, portRoyal3);
        Assert.assertNotEquals(null, portRoyal3.getHighscore());
        Assert.assertNotEquals(null, portRoyal3.getMatchList());
    }

    /**
     * Method to test the addMatch method of PortRoyal
     * param match - valid
     * matchList1 is empty before we add match1, then the size of matchList1 gets 1
     */
    @Test
    public void testAddMatch(){
        Assert.assertEquals(0,matchList1.size());
        portRoyal1.addMatch(match1);
        Assert.assertEquals(1,matchList1.size());
    }

    /**
     * Method to test if the addMatch method of PortRoyal throws the expected Exception when the match is null
     * param match - null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddMatchNull(){
        portRoyal1.addMatch(null);
    }

    /**
     * Method to test the addScore method of PortRoyal
     * param score - valid
     * highscore1 is empty before we add score1, then the size of highscore1 get 1
     */
    @Test
    public void testAddScore(){
        Assert.assertEquals(0,highscore1.size());
        portRoyal1.addScore(score1);
        Assert.assertEquals(1,highscore1.size());
    }

    /**
     * Method to test if the addSCore method of PortRoyal throws the expected Exception when the score is null
     * param score - null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddScoreNull(){
        portRoyal1.addScore(null);
    }

    /**
     * Method to test the removeScore method of PortRoyal
     * highscore2 has 2 scores before we remove one
     * Afterwards highscore2 has 1 score and the score that got removed was the last added score score2
     */
    @Test
    public void testRemoveScore(){
        Assert.assertEquals(2,highscore2.size());
        portRoyal2.removeScore();
        Assert.assertEquals(1,highscore2.size());
        Assert.assertEquals(score2, highscore2.get(0));
    }

    /**
     * Method to test the removeMatch method of PortRoyal
     * param match - valid
     * matchList2 has 2 matches before we remove one
     * Afterwards matchList2 has 1 match and match2 got removed so only match1 is left in matchList2
     */
    @Test
    public void testRemoveMatch(){
        Assert.assertEquals(2,matchList2.size());
        portRoyal2.removeMatch(match2);
        Assert.assertEquals(1,matchList2.size());
        Assert.assertEquals(match1, matchList2.get(0));
    }

    /**
     * Method to test the sortScore method of PortRoyal
     * highscore2 has the score 200 at index 0
     * Afterwards score 100 is at index 0 because its a lower score than 200
     */
    @Test
    public void testSortScore(){
        Assert.assertEquals(200,highscore2.get(0).getScore());
        portRoyal2.sortScore();
        Assert.assertEquals(100,highscore2.get(0).getScore());
    }

    /**
     * Method to test the setActiveMatch method of PortRoyal
     * param match - valid
     */
    @Test
    public void testSetActiveMatch(){
        portRoyal2.setActiveMatch(match1);
        Assert.assertEquals(match1, portRoyal2.getActiveMatch());
    }

    /**
     * Method to test the setMatchList method of PortRoyal
     * param matchList - valid
     */
    @Test
    public void testSetMatchList(){
        portRoyal2.setMatchList(matchList1);
        Assert.assertEquals(matchList1, portRoyal2.getMatchList());
    }

    /**
     * Method to test the setHighscore method of PortRoyal
     * param highscore - valid
     */
    @Test
    public void testSetHighscore(){
        portRoyal2.setHighscore(highscore1);
        Assert.assertEquals(highscore1, portRoyal2.getHighscore());
    }
}
