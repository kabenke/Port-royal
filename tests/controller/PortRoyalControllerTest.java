package controller;
import abstractuserinterface.MenuAUI;
import model.Match;
import model.Player;
import model.PortRoyal;
import model.Score;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

/**
 * Test class for PortRoyalController
 */
public class PortRoyalControllerTest {

    private MainController mainController ;
    private PortRoyalController portRoyalController ;
    private Match match,matchToRemove ;
    private MenuAUI menuAUI;
    private Player player1, player2, player3, player4, player5, player6, player7, player8, player9, player10, player11;
    private PortRoyal portRoyal;
    private Score score1, score2, score3, score4, score5, score6, score7, score8, score9, score10, score11;
    private ArrayList<Score> highscore;



    /**
     * Creates a new test environment before each test method
     * @throws Exception initialise all Tests
     */
    @Before
    public void setUp() throws Exception{
        this.mainController = new MainController();
        this.portRoyalController = new PortRoyalController(mainController);
        this.match = new Match();
        this.matchToRemove = new Match();
        this.portRoyal = new PortRoyal();
        this.player1 = new Player();
        this.player2 = new Player();
        this.player3 = new Player();
        this.player4 = new Player();
        this.player5 = new Player();
        this.player6 = new Player();
        this.player7 = new Player();
        this.player8 = new Player();
        this.player9 = new Player();
        this.player10 = new Player();
        this.player11 = new Player();
        this.mainController.setPortRoyal(portRoyal);
        this.portRoyal.addMatch(match);
        this.portRoyal.setActiveMatch(match);
        this.match.getMatchHelp().addPlayer(player1);
        this.menuAUI = new MenuAUI() {
            @Override
            public void refreshMatchList() {

            }
        };
        this.highscore = new ArrayList<>();
        this.score1 = new Score(player1);
        score1.setScore(10);
        highscore.add(score1);
        this.score2 = new Score(player2);
        score2.setScore(20);
        highscore.add(score2);
        this.score3 = new Score(player3);
        score3.setScore(30);
        highscore.add(score3);
        this.score4 = new Score(player4);
        score4.setScore(40);
        highscore.add(score4);
        this.score5 = new Score(player5);
        score5.setScore(50);
        highscore.add(score5);
        this.score6 = new Score(player6);
        score6.setScore(60);
        highscore.add(score6);
        this.score7 = new Score(player7);
        score7.setScore(70);
        highscore.add(score7);
        this.score8 = new Score(player8);
        score8.setScore(80);
        highscore.add(score8);
        this.score9 = new Score(player9);
        score9.setScore(90);
        highscore.add(score9);
        this.score10 = new Score(player10);
        score10.setScore(100);
        highscore.add(score10);
        this.score11 = new Score(player11);
        score11.setScore(15);
    }

    /**
     * Test method  addMatch in PortRoyalController
     */
    @Test
    public void addMatchTest(){
        portRoyalController.addMatch(match);
        ArrayList<Match> matchList= mainController.getPortRoyal().getMatchList();
        Assert.assertEquals(matchList.get(matchList.size()-1),match);
    }

    /**
     * Test method removeMatch in PortRoyalController
     */
    @Test
    public void removeMatchTest(){
        portRoyalController.addMatch(match);
        portRoyalController.addMatch(matchToRemove);
        portRoyalController.removeMatch(matchToRemove);
        ArrayList<Match> matchList= mainController.getPortRoyal().getMatchList();
        Assert.assertEquals(matchList.get(matchList.size()-1),match);

    }

    /**
     * This method is used to test the AddPlayerToMatch method
     */
    @Test
    public void testAddPlayerToMatch() {

        portRoyalController.addPlayerToMatch(player1);
        ArrayList<Player> PlayerList = mainController.getPortRoyal().getActiveMatch().getPlayerList();
        Assert.assertEquals(PlayerList.get(0), player1);
    }

    /**
     * This method is used to test the AddScore method when the highscore list has less than 10 scores in it
     */
    @Test
    public void testAddScoreLessThan10Scores() {
        portRoyalController.addScore(score1);
        ArrayList<Score> HighScore = mainController.getPortRoyal().getHighscore();
        Assert.assertEquals(HighScore.get(0), score1);
        Assert.assertEquals(10, HighScore.get(0).getScore());
    }

    /**
     * This method is used to test the AddScore method when the highscore list already has 10 players in it
     * In this case the highscore list is (10, 20, 30, 40, 50, 60, 70, 80, 90, 100) before we add a new score
     * After we added the score 15, the list should be (15, 20, 30, 40, 50, 60, 70, 80, 90, 100)
     */
    @Test
    public void testAddScore10Scores() {
        portRoyal.setHighscore(highscore);
        Assert.assertEquals(10, mainController.getPortRoyal().getHighscore().size());
        Assert.assertEquals(10, mainController.getPortRoyal().getHighscore().get(0).getScore());
        portRoyalController.addScore(score11);
        Assert.assertEquals(15, mainController.getPortRoyal().getHighscore().get(0).getScore());
    }

    /**
     * This method is used to test the setMenuAUI method
     */
    @Test
    public void testSetMenuAUI() {
        portRoyalController.setMenuAUI(menuAUI);
        Assert.assertEquals(menuAUI, portRoyalController.getMenuAUI());
    }
}