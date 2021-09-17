package view;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import controller.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import model.Match;
import model.Player;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class LaufendeSpieleViewController extends AnchorPane {

    private Stage primaryStage;
    private MainController mainController;
    private int matchIndex;
    private MediaPlayer mediaPlayer;

    @FXML
    private AnchorPane pane;

    @FXML
    private ImageView backButton, topButton, downButton;

    @FXML
    private Text time1, time2, time3, time4, time5, time6, time7, time8, time9, time10,
            names1, names2, names3, names4, names5, names6, names7, names8, names9, names10,
            round1, round2, round3, round4, round5, round6, round7, round8, round9, round10;

    @FXML
    private ImageView play1, play2, play3, play4, play5, play6, play7, play8, play9, play10,
            delete1, delete2, delete3, delete4, delete5, delete6, delete7, delete8, delete9, delete10;

    /*
     * constructor
     */

    public LaufendeSpieleViewController(Stage primaryStage, MainController mainController, MediaPlayer player) {
        this.mainController = mainController;
        this.primaryStage = primaryStage;
        this.mediaPlayer = player;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LaufendeSpiele.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {

            e.printStackTrace();
        }
        matchIndex = 0;
        this.refresh();
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
     * refresh
     */
    public void refresh(){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        ArrayList<Match> matches = mainController.getPortRoyal().getMatchList();
        //sort match list
        Collections.sort(matches);
        //match 1
        try{
            time1.setText(format.format(matches.get(    matchIndex + 0).getTimestamp()));
            ArrayList<Player> list = matches.get(       matchIndex + 0).getPlayerList(); String str = "";
            for(int i=0; i<list.size(); i++){
                str += list.get(i).getName();
                if(!(i==list.size()-1)){ str += ", "; }
            }
            names1.setText(str);
            round1.setText(matches.get(                 matchIndex + 0).getTurnHistory().size() + "");
        }
        catch (Exception e){ time1.setText(""); names1.setText(""); round1.setText(""); }
        //match 2
        try{
            time2.setText(format.format(matches.get(    matchIndex + 1).getTimestamp()));
            ArrayList<Player> list = matches.get(       matchIndex + 1).getPlayerList(); String str = "";
            for(int i=0; i<list.size(); i++){
                str += list.get(i).getName();
                if(!(i==list.size()-1)){ str += ", "; }
            }
            names2.setText(str);
            round2.setText(matches.get(                 matchIndex + 1).getTurnHistory().size() + "");
        }
        catch (Exception e){ time2.setText(""); names2.setText(""); round2.setText(""); }
        //match 3
        try{
            time3.setText(format.format(matches.get(    matchIndex + 2).getTimestamp()));
            ArrayList<Player> list = matches.get(       matchIndex + 2).getPlayerList(); String str = "";
            for(int i=0; i<list.size(); i++){
                str += list.get(i).getName();
                if(!(i==list.size()-1)){ str += ", "; }
            }
            names3.setText(str);
            round3.setText(matches.get(                 matchIndex + 2).getTurnHistory().size() + "");
        }
        catch (Exception e){ time3.setText(""); names3.setText(""); round3.setText(""); }
        //match 4
        try{
            time4.setText(format.format(matches.get(    matchIndex + 3).getTimestamp()));
            ArrayList<Player> list = matches.get(       matchIndex + 3).getPlayerList(); String str = "";
            for(int i=0; i<list.size(); i++){
                str += list.get(i).getName();
                if(!(i==list.size()-1)){ str += ", "; }
            }
            names4.setText(str);
            round4.setText(matches.get(                 matchIndex + 3).getTurnHistory().size() + "");
        }
        catch (Exception e){ time4.setText(""); names4.setText(""); round4.setText(""); }
        //match 5
        try{
            time5.setText(format.format(matches.get(    matchIndex + 4).getTimestamp()));
            ArrayList<Player> list = matches.get(       matchIndex + 4).getPlayerList(); String str = "";
            for(int i=0; i<list.size(); i++){
                str += list.get(i).getName();
                if(!(i==list.size()-1)){ str += ", "; }
            }
            names5.setText(str);
            round5.setText(matches.get(                 matchIndex + 4).getTurnHistory().size() + "");
        }
        catch (Exception e){ time5.setText(""); names5.setText(""); round5.setText(""); }
        //match 6
        try{
            time6.setText(format.format(matches.get(    matchIndex + 5).getTimestamp()));
            ArrayList<Player> list = matches.get(       matchIndex + 5).getPlayerList(); String str = "";
            for(int i=0; i<list.size(); i++){
                str += list.get(i).getName();
                if(!(i==list.size()-1)){ str += ", "; }
            }
            names6.setText(str);
            round6.setText(matches.get(                 matchIndex + 5).getTurnHistory().size() + "");
        }
        catch (Exception e){ time6.setText(""); names6.setText(""); round6.setText(""); }
        //match 7
        try{
            time7.setText(format.format(matches.get(    matchIndex + 6).getTimestamp()));
            ArrayList<Player> list = matches.get(       matchIndex + 6).getPlayerList(); String str = "";
            for(int i=0; i<list.size(); i++){
                str += list.get(i).getName();
                if(!(i==list.size()-1)){ str += ", "; }
            }
            names7.setText(str);
            round7.setText(matches.get(                 matchIndex + 6).getTurnHistory().size() + "");
        }
        catch (Exception e){ time7.setText(""); names7.setText(""); round7.setText(""); }
        //match 8
        try{
            time8.setText(format.format(matches.get(    matchIndex + 7).getTimestamp()));
            ArrayList<Player> list = matches.get(       matchIndex + 7).getPlayerList(); String str = "";
            for(int i=0; i<list.size(); i++){
                str += list.get(i).getName();
                if(!(i==list.size()-1)){ str += ", "; }
            }
            names8.setText(str);
            round8.setText(matches.get(                 matchIndex + 7).getTurnHistory().size() + "");
        }
        catch (Exception e){ time8.setText(""); names8.setText(""); round8.setText(""); }
        //match 9
        try{
            time9.setText(format.format(matches.get(    matchIndex + 8).getTimestamp()));
            ArrayList<Player> list = matches.get(       matchIndex + 8).getPlayerList(); String str = "";
            for(int i=0; i<list.size(); i++){
                str += list.get(i).getName();
                if(!(i==list.size()-1)){ str += ", "; }
            }
            names9.setText(str);
            round9.setText(matches.get(                 matchIndex + 8).getTurnHistory().size() + "");
        }
        catch (Exception e){ time9.setText(""); names9.setText(""); round9.setText(""); }
        //match 2
        try{
            time10.setText(format.format(matches.get(    matchIndex + 9).getTimestamp()));
            ArrayList<Player> list = matches.get(        matchIndex + 9).getPlayerList(); String str = "";
            for(int i=0; i<list.size(); i++){
                str += list.get(i).getName();
                if(!(i==list.size()-1)){ str += ", "; }
            }
            names10.setText(str);
            round10.setText(matches.get(                 matchIndex + 9).getTurnHistory().size() + "");
        }
        catch (Exception e){ time10.setText(""); names10.setText(""); round10.setText(""); }
        //refresh Buttons
        play1.setVisible(!names1.getText().equals(""));
        delete1.setVisible(!names1.getText().equals(""));
        play2.setVisible(!names2.getText().equals(""));
        delete2.setVisible(!names2.getText().equals(""));
        play3.setVisible(!names3.getText().equals(""));
        delete3.setVisible(!names3.getText().equals(""));
        play4.setVisible(!names4.getText().equals(""));
        delete4.setVisible(!names4.getText().equals(""));
        play5.setVisible(!names5.getText().equals(""));
        delete5.setVisible(!names5.getText().equals(""));
        play6.setVisible(!names6.getText().equals(""));
        delete6.setVisible(!names6.getText().equals(""));
        play7.setVisible(!names7.getText().equals(""));
        delete7.setVisible(!names7.getText().equals(""));
        play8.setVisible(!names8.getText().equals(""));
        delete8.setVisible(!names8.getText().equals(""));
        play9.setVisible(!names9.getText().equals(""));
        delete9.setVisible(!names9.getText().equals(""));
        play10.setVisible(!names10.getText().equals(""));
        delete10.setVisible(!names10.getText().equals(""));
        //refresh top/down buttons
        topButton.setVisible(matchIndex>0);
        downButton.setVisible(matches.size() > matchIndex+10);
    }

    /*
     * Button onClicks
     */

    @FXML
    void onTopClick(){ matchIndex--; }

    @FXML
    void onDownClick(){ matchIndex ++; }

    @FXML
    void onPlay1Click(){
        ArrayList<Match> matches = mainController.getPortRoyal().getMatchList();
        mainController.getPortRoyal().setActiveMatch(matches.get(matchIndex + 0));
        changeSceneToGame();
    }

    @FXML
    void onPlay2Click(){
        ArrayList<Match> matches = mainController.getPortRoyal().getMatchList();
        mainController.getPortRoyal().setActiveMatch(matches.get(matchIndex + 1));
        changeSceneToGame();
    }

    @FXML
    void onPlay3Click(){
        ArrayList<Match> matches = mainController.getPortRoyal().getMatchList();
        mainController.getPortRoyal().setActiveMatch(matches.get(matchIndex + 2));
        changeSceneToGame();
    }

    @FXML
    void onPlay4Click(){
        ArrayList<Match> matches = mainController.getPortRoyal().getMatchList();
        mainController.getPortRoyal().setActiveMatch(matches.get(matchIndex + 3));
        changeSceneToGame();
    }

    @FXML
    void onPlay5Click(){
        ArrayList<Match> matches = mainController.getPortRoyal().getMatchList();
        mainController.getPortRoyal().setActiveMatch(matches.get(matchIndex + 4));
        changeSceneToGame();
    }

    @FXML
    void onPlay6Click(){
        ArrayList<Match> matches = mainController.getPortRoyal().getMatchList();
        mainController.getPortRoyal().setActiveMatch(matches.get(matchIndex + 5));
        changeSceneToGame();
    }

    @FXML
    void onPlay7Click(){
        ArrayList<Match> matches = mainController.getPortRoyal().getMatchList();
        mainController.getPortRoyal().setActiveMatch(matches.get(matchIndex + 6));
        changeSceneToGame();
    }

    @FXML
    void onPlay8Click(){
        ArrayList<Match> matches = mainController.getPortRoyal().getMatchList();
        mainController.getPortRoyal().setActiveMatch(matches.get(matchIndex + 7));
        changeSceneToGame();
    }

    @FXML
    void onPlay9Click(){
        ArrayList<Match> matches = mainController.getPortRoyal().getMatchList();
        mainController.getPortRoyal().setActiveMatch(matches.get(matchIndex + 8));
        changeSceneToGame();
    }

    @FXML
    void onPlay10Click(){
        ArrayList<Match> matches = mainController.getPortRoyal().getMatchList();
        mainController.getPortRoyal().setActiveMatch(matches.get(matchIndex + 9));
        changeSceneToGame();
    }

    @FXML
    void onDelete1(){
        ArrayList<Match> matches = mainController.getPortRoyal().getMatchList();
        matches.remove(matches.get(matchIndex + 0));
        this.refresh();
    }

    @FXML
    void onDelete2(){
        ArrayList<Match> matches = mainController.getPortRoyal().getMatchList();
        matches.remove(matches.get(matchIndex + 1));
        this.refresh();
    }

    @FXML
    void onDelete3(){
        ArrayList<Match> matches = mainController.getPortRoyal().getMatchList();
        matches.remove(matches.get(matchIndex + 2));
        this.refresh();
    }

    @FXML
    void onDelete4(){
        ArrayList<Match> matches = mainController.getPortRoyal().getMatchList();
        matches.remove(matches.get(matchIndex + 3));
        this.refresh();
    }

    @FXML
    void onDelete5(){
        ArrayList<Match> matches = mainController.getPortRoyal().getMatchList();
        matches.remove(matches.get(matchIndex + 4));
        this.refresh();
    }

    @FXML
    void onDelete6(){
        ArrayList<Match> matches = mainController.getPortRoyal().getMatchList();
        matches.remove(matches.get(matchIndex + 5));
        this.refresh();
    }

    @FXML
    void onDelete7(){
        ArrayList<Match> matches = mainController.getPortRoyal().getMatchList();
        matches.remove(matches.get(matchIndex + 6));
        this.refresh();
    }

    @FXML
    void onDelete8(){
        ArrayList<Match> matches = mainController.getPortRoyal().getMatchList();
        matches.remove(matches.get(matchIndex + 7));
        this.refresh();
    }

    @FXML
    void onDelete9(){
        ArrayList<Match> matches = mainController.getPortRoyal().getMatchList();
        matches.remove(matches.get(matchIndex + 8));
        this.refresh();
    }

    @FXML
    void onDelete10(){
        ArrayList<Match> matches = mainController.getPortRoyal().getMatchList();
        matches.remove(matches.get(matchIndex + 9));
        this.refresh();
    }

    /*
     * help methods
     */

    public void changeSceneToGame(){
        mainController.getPortRoyal().getActiveMatch().setTimestamp(new Date(System.currentTimeMillis()));
        try {
            SpielenViewController spielenViewController = new SpielenViewController(primaryStage, mainController, mediaPlayer);
            Scene scene = new Scene(spielenViewController);
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

