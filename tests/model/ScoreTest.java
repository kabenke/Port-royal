package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class is used to test the methods of the Score.
 */
public class ScoreTest {

    private Score score1, score2, score3;
    private Player player1, player2;

    /**
     * Creates a new test environment before each test method
     * @throws Exception Possible Exceptions with setUp
     */
    @Before
    public void setUp() throws Exception {
        this.player1 = new Player();
        this.player2 = new Player();
        this.score1 = new Score(player1);
        this.score3 = new Score(player2);
    }

    /**
     * This method is used to test the constructor of Score
     */
    @Test
    public void testConstructorValid(){
        score2 = new Score(player2);
        Assert.assertNotEquals(null, score2);
        Assert.assertNotEquals(null, score2.getPlayer());
    }

    /**
     * This method is used to test the setScore method of Score
     */
    @Test
    public void testSetScore(){
        score1.setScore(100);
        Assert.assertEquals(100, score1.getScore());
    }

    /**
     * This method is used to test the setPlayer method of Score
     */
    @Test
    public void testSetPlayer(){
        Assert.assertEquals(player1, score1.getPlayer());
        score1.setPlayer(player2);
        Assert.assertEquals(player2, score1.getPlayer());
    }

    /**
     * This method is used to test the CompareTo method of Score
     */
    @Test
    public void testCompareTo(){
        score1.setScore(100);
        score3.setScore(50);
        int i = score1.compareTo(score3);
        Assert.assertEquals(-50, i);

    }
}
