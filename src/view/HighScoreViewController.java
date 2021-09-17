package view;

import java.io.IOException;
import java.util.ArrayList;

import controller.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Player;
import model.Score;
import javafx.scene.text.Text;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class HighScoreViewController extends AnchorPane {

    private Stage primaryStage;
    private MainController mainController;
    private MediaPlayer mediaPlayer;

    @FXML
    private AnchorPane pane;

    @FXML
    private ImageView backButton, platz1, platz2, platz3;

    @FXML
    private Text score1, score2, score3, score4, score5, score6, score7, score8, score9, score10;

    @FXML
    private Label platz4, platz5, platz6, platz7, platz8, platz9, platz10,
                  player1, player2, player3, player4, player5, player6, player7, player8, player9, player10;

    /*
     * constructor
     */

    public HighScoreViewController(Stage primaryStage, MainController mainController, MediaPlayer player) {
        this.mainController = mainController;
        this.primaryStage = primaryStage;
        this.mediaPlayer = player;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HighScore.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {

            e.printStackTrace();
        }
        this.refresh();
    }

    /*
     * refresh
     */

    public void refresh(){
        Player test1 = new Player();
        test1.setName("Stiefelriemen");
        Score testScore1 = new Score(test1);
        testScore1.setScore(150);
        mainController.getScoreController().addScoreToHighscores(testScore1);

        Player test2 = new Player();
        test2.setName("Schwarzstiefel");
        Score testScore2 = new Score(test2);
        testScore2.setScore(137);
        mainController.getScoreController().addScoreToHighscores(testScore2);

        Player test3 = new Player();
        test3.setName("Rotbart");
        Score testScore3 = new Score(test3);
        testScore3.setScore(129);
        mainController.getScoreController().addScoreToHighscores(testScore3);

        Player test4 = new Player();
        test4.setName("Schwarzbart");
        Score testScore4 = new Score(test4);
        testScore4.setScore(113);
        mainController.getScoreController().addScoreToHighscores(testScore4);

        Player test5 = new Player();
        test5.setName("Säbelzahn");
        Score testScore5 = new Score(test5);
        testScore5.setScore(107);
        mainController.getScoreController().addScoreToHighscores(testScore5);

        Player test6 = new Player();
        test6.setName("Stiefelriemen");
        Score testScore6 = new Score(test6);
        testScore6.setScore(102);
        mainController.getScoreController().addScoreToHighscores(testScore6);

        Player test7 = new Player();
        test7.setName("Holzbein");
        Score testScore7 = new Score(test7);
        testScore7.setScore(98);
        mainController.getScoreController().addScoreToHighscores(testScore7);

        Player test8 = new Player();
        test8.setName("Holzbein");
        Score testScore8 = new Score(test8);
        testScore8.setScore(93);
        mainController.getScoreController().addScoreToHighscores(testScore8);

        Player test9 = new Player();
        test9.setName("Stoppelbart");
        Score testScore9 = new Score(test9);
        testScore9.setScore(86);
        mainController.getScoreController().addScoreToHighscores(testScore9);

        Player test10 = new Player();
        test10.setName("Säbelzahn");
        Score testScore10 = new Score(test10);
        testScore10.setScore(75);
        mainController.getScoreController().addScoreToHighscores(testScore10);

        ArrayList<Score> scores = mainController.getPortRoyal().getHighscore();
        //Platz 1
        try{
            score1.setText(scores.get(0).getScore()+"");
            player1.setText(scores.get(0).getPlayer().getName());
            player1.setVisible(true); platz1.setVisible(true); score1.setVisible(true);
        }
        catch(Exception e){ player1.setVisible(false); platz1.setVisible(false); score1.setVisible(false); }
        //Platz 2
        try{
            score2.setText(scores.get(1).getScore()+"");
            player2.setText(scores.get(1).getPlayer().getName());
            player2.setVisible(true); platz2.setVisible(true); score2.setVisible(true);
        }
        catch(Exception e){ player2.setVisible(false); platz2.setVisible(false); score2.setVisible(false); }
        //Platz 3
        try{
            score3.setText(scores.get(2).getScore()+"");
            player3.setText(scores.get(2).getPlayer().getName());
            player3.setVisible(true); platz3.setVisible(true); score3.setVisible(true);
        }
        catch(Exception e){ player3.setVisible(false); platz3.setVisible(false); score3.setVisible(false); }
        //Platz 4
        try{
            score4.setText(scores.get(3).getScore()+"");
            player4.setText(scores.get(3).getPlayer().getName());
            player4.setVisible(true); platz4.setVisible(true); score4.setVisible(true);
        }
        catch(Exception e){ player4.setVisible(false); platz4.setVisible(false); score4.setVisible(false); }
        //Platz 5
        try{
            score5.setText(scores.get(4).getScore()+"");
            player5.setText(scores.get(4).getPlayer().getName());
            player5.setVisible(true); platz5.setVisible(true); score5.setVisible(true);
        }
        catch(Exception e){ player5.setVisible(false); platz5.setVisible(false); score5.setVisible(false); }
        //Platz 6
        try{
            score6.setText(scores.get(5).getScore()+"");
            player6.setText(scores.get(5).getPlayer().getName());
            player6.setVisible(true); platz6.setVisible(true); score6.setVisible(true);
        }
        catch(Exception e){ player6.setVisible(false); platz6.setVisible(false); score6.setVisible(false); }
        //Platz 7
        try{
            score7.setText(scores.get(6).getScore()+"");
            player7.setText(scores.get(6).getPlayer().getName());
            player7.setVisible(true); platz7.setVisible(true); score7.setVisible(true);
        }
        catch(Exception e){ player7.setVisible(false); platz7.setVisible(false); score7.setVisible(false); }
        //Platz 8
        try{
            score8.setText(scores.get(7).getScore()+"");
            player8.setText(scores.get(7).getPlayer().getName());
            player8.setVisible(true); platz8.setVisible(true); score8.setVisible(true);
        }
        catch(Exception e){ player8.setVisible(false); platz8.setVisible(false); score8.setVisible(false); }
        //Platz 9
        try{
            score9.setText(scores.get(8).getScore()+"");
            player9.setText(scores.get(8).getPlayer().getName());
            player9.setVisible(true); platz9.setVisible(true); score9.setVisible(true);
        }
        catch(Exception e){ player9.setVisible(false); platz9.setVisible(false); score9.setVisible(false); }
        //Platz 10
        try{
            score10.setText(scores.get(9).getScore()+"");
            player10.setText(scores.get(9).getPlayer().getName());
            player10.setVisible(true); platz10.setVisible(true); score10.setVisible(true);
        }
        catch(Exception e){ player10.setVisible(false); platz10.setVisible(false); score10.setVisible(false); }
    }

    /*
     * window changes
     */

    @FXML
    void onBackClick(MouseEvent event){
        try {
            StartScreenViewController startScreenViewController = new StartScreenViewController(primaryStage, mainController, mediaPlayer);
            Scene scene = new Scene(startScreenViewController);
            scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
            primaryStage.setTitle("Port Royal");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Button HoverEffects
     */

    @FXML
    void onBackEnter(MouseEvent event){
        Image image = new Image("/view/buttons/arrowLeftButtonHover.png");
        backButton.setImage(image);
    }

    @FXML
    void onBackExit(MouseEvent event) {
        Image image = new Image("/view/buttons/arrowLeftButton.png");
        backButton.setImage(image);
    }

}
