package controller;
import abstractuserinterface.ScoreAUI;
import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;

/**
 * Test class for ScoreController
 */
public class ScoreControllerTest {
    private MainController mainController ;
    private ScoreController scoreController;
    private Score score1,score2,score3;
    private Player  playerEligibleForHighscore,playerNotEligibleForHighscore,playerWithNegativScore ,player;
    private ArrayList<Card> goldCardPlayer,goldCardNegativ ;
    private ArrayList<Card> nonGoldCard ;
    private Ship gold;
    private Person person1,person2;
    private Score score4,score5,score6,score7,score8,score9,score10,score11,score12;
    private ScoreAUI scoreAUI;

    /**
     * Creates a new test environment before each test method
     * @throws Exception Possible Exceptions with setUp
     */
    @Before
    public void setUp () throws Exception {
        mainController=new MainController();
        scoreController=new ScoreController(mainController);
        scoreAUI = new ScoreAUI() {
            @Override
            public void refreshList() {

            }
        };
        playerEligibleForHighscore=new Player();
        playerNotEligibleForHighscore=new Player();
        playerWithNegativScore=new Player();
        score1=new Score(playerEligibleForHighscore);
        score2=new Score(playerNotEligibleForHighscore);
        score3=new Score(playerWithNegativScore);
        gold=new Ship(4,3,Color.RED);
        gold.setIsFaceUp(false);
        goldCardPlayer =new ArrayList<>();
        goldCardNegativ=new ArrayList<>();
        nonGoldCard=new ArrayList<>();
        person1=new Person(4,4, Ability.CAPTAIN,4,Color.RED);
        person2=new Person(2,3,Ability.MADEMOISELLE,3,Color.BLACK);
        nonGoldCard.add(person1);
        nonGoldCard.add(person2);
        //player with EligibleForHighscore
        for(int i=0 ;i<10;i++){
            goldCardPlayer.add(gold);
        }

        playerEligibleForHighscore.setVictoryPoints(5);
        playerEligibleForHighscore.setSwords(4);
        playerEligibleForHighscore.setMoveCount(10);
        playerEligibleForHighscore.setNonGoldCards(nonGoldCard);
        playerEligibleForHighscore.setGoldCards(goldCardPlayer);
        //player Not EligibleForHighscore


        playerNotEligibleForHighscore.setEligibleForHighscore(false);
        playerNotEligibleForHighscore.setVictoryPoints(5);
        playerNotEligibleForHighscore.setSwords(4);
        playerNotEligibleForHighscore.setMoveCount(10);
        playerNotEligibleForHighscore.setNonGoldCards(nonGoldCard);
        playerNotEligibleForHighscore.setGoldCards(goldCardPlayer);
        //player With Negativ Score
        goldCardNegativ.add(gold);
        goldCardNegativ.add(gold);
        playerWithNegativScore.setNonGoldCards(nonGoldCard);
        playerWithNegativScore.setGoldCards(goldCardNegativ);
        player=new Player();
        score4 = new Score(player);
        score5 = new Score(player);
        score6 = new Score(player);
        score7 = new Score(player);
        score8 = new Score(player);
        score9 = new Score(player);
        score10 = new Score(player);
        score11 = new Score(player);
        score12 = new Score(player);
        score1.setScore(8);
        score2.setScore(0);
        score3.setScore(10);
        score4.setScore(6);
        score5.setScore(1);
        score6.setScore(4);
        score7.setScore(3);
        score8.setScore(6);
        score9.setScore(4);
        score10.setScore(3);
        score11.setScore(2);
        score12.setScore(7);
        mainController.getPortRoyal().getHighscore().add(score1);
        mainController.getPortRoyal().getHighscore().add(score2);
        mainController.getPortRoyal().getHighscore().add(score3);
        mainController.getPortRoyal().getHighscore().add(score4);
        mainController.getPortRoyal().getHighscore().add(score5);
        mainController.getPortRoyal().getHighscore().add(score6);
        mainController.getPortRoyal().getHighscore().add(score7);
        mainController.getPortRoyal().getHighscore().add(score8);
        mainController.getPortRoyal().getHighscore().add(score9);
        mainController.getPortRoyal().getHighscore().add(score10);
        mainController.getPortRoyal().getHighscore().add(score11);




    }

    /**
     * Test calculateScore for normal player
     */
    @Test
    public void calculateScoreEligibleForHighscore(){
        scoreController.calculateScore(score1);
        Assert.assertEquals(61,score1.getScore());

    }

    /**
     * Test calculateScore player Not Eligible For Highscore
     */
    @Test
    public void calculateScoreNotEligibleForHighscore(){
        scoreController.calculateScore(score2);
        Assert.assertEquals(0,score2.getScore());
    }

    /**
     *  Test calculateScore NegativeScore
     */
    @Test
    public void calculateScoreNegativeScore(){
        scoreController.calculateScore(score3);
        Assert.assertEquals(0,score3.getScore());

    }
    /**
     * Test method to add Score To Highscores
     */
    @Test
    public void addScoreToHighscoresTest (){
        scoreController.addScoreToHighscores(score12);
        ArrayList<Score> highscores = mainController.getPortRoyal().getHighscore();
        Assert.assertEquals(10,highscores.size());
        for(int i=1;i<highscores.size()-1;i++){
            assert(highscores.get(i-1).getScore()>=highscores.get(i).getScore());
        }
        assert (highscores.contains(score1));
        assert (highscores.contains(score11));
        assert (highscores.contains(score3));
        assert (highscores.contains(score4));
        assert (highscores.contains(score6));
        assert (highscores.contains(score7));
        assert (highscores.contains(score8));
        assert (highscores.contains(score9));
        assert (highscores.contains(score10));
        assert (highscores.contains(score12));

    }

    /**
     * This method is used to test the Getter and Setter of the mainController
     */
    @Test
    public void testSetAndGetMainController(){
        scoreController.setMainController(mainController);
        Assert.assertEquals(mainController, scoreController.getMainController());
    }

    /**
     * This method is used to test the Getter and Setter of the scoreAUI
     */
    @Test
    public void testSetAndGetScoreAUI(){
        scoreController.setScoreAUI(scoreAUI);
        Assert.assertEquals(scoreAUI,scoreController.getScoreAUI() );
    }

}
