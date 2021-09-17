package view;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.io.*;
import controller.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Match;
import model.Player;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.*;

public class StartScreenViewController extends AnchorPane {

    private Stage primaryStage;
    private MainController mainController;
    private MediaPlayer mediaPlayer;
    private double audioStep1 = 0.05;
    private double audioStep2 = 0.10;
    private double audioStep3 = 0.15;

    @FXML
    private ImageView portRoyalImg;

    @FXML
    private ImageView newGameButton, loadGameButton, highscoresButton, settingsButton, questionButton, closeButton,
            soundButton;

    @FXML
    private AnchorPane pane;

    /*
     *  constructors
     */

    public StartScreenViewController(Stage primaryStage) {
        this.mainController = new MainController();
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StartScreen.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {

            e.printStackTrace();
        }
        this.music();
        mediaPlayer.setVolume(audioStep1);
    }

    public StartScreenViewController(Stage primaryStage, MainController mainController, MediaPlayer player) {
        this.mainController = mainController;
        this.primaryStage = primaryStage;
        this.mediaPlayer = player;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StartScreen.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /*
     * window changes
     */

    @FXML
    void onNewGameClick(MouseEvent event){
        try {
            NeuesSpielViewController neuesSpielViewController = new NeuesSpielViewController(primaryStage, mainController, mediaPlayer);
            Scene scene = new Scene(neuesSpielViewController);
            scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
            primaryStage.setTitle("Port Royal");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onHighscoreClick(MouseEvent event){
        try {
            HighScoreViewController highScoreViewController = new HighScoreViewController(primaryStage, mainController, mediaPlayer);
            Scene scene = new Scene(highScoreViewController);
            scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
            primaryStage.setTitle("Port Royal");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onLoadGameClick(MouseEvent event){
        try {
            LaufendeSpieleViewController laufendeSpieleViewController = new LaufendeSpieleViewController(primaryStage, mainController, mediaPlayer);
            Scene scene = new Scene(laufendeSpieleViewController);
            scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
            primaryStage.setTitle("Port Royal");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onQuestionClick(MouseEvent event){
        InputStream inputStream = this.getClass().getResourceAsStream("produktbeschreibung.pdf");

        try {
            File file = File.createTempFile("produktbeschreibung", ".pdf");
            file.deleteOnExit();
            OutputStream out = new FileOutputStream(file);
            try {
                byte[] buffer = new byte[1024];
                int length;

                while((length = inputStream.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }

            } finally {
                out.close();
            }
            Desktop desktop = Desktop.getDesktop();
            if (desktop != null && desktop.isSupported(Desktop.Action.OPEN)) { desktop.open(file); }
        } catch (IOException ex) { }
    }

    @FXML
    void onCloseClick(MouseEvent event){ primaryStage.close(); }

    /*
     * background music
     */
    public void music() {
        String s = "home.mp3";

        Media h = new Media(this.getClass().getResource(s).toExternalForm());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    @FXML
    void onSoundClick(){
        if(mediaPlayer.getVolume() == 0){ mediaPlayer.setVolume(audioStep1); }
        else if(mediaPlayer.getVolume() == audioStep1){ mediaPlayer.setVolume(audioStep2); }
        else if(mediaPlayer.getVolume() == audioStep2){ mediaPlayer.setVolume(audioStep3); }
        else if(mediaPlayer.getVolume() == audioStep3){ mediaPlayer.setVolume(0); }
        if(mediaPlayer.getVolume()==0){ soundButton.setImage(new Image("/view/buttons/sound0Button.png")); }
        else if(mediaPlayer.getVolume()==audioStep1){ soundButton.setImage(new Image("/view/buttons/sound1Button.png")); }
        else if(mediaPlayer.getVolume()==audioStep2){ soundButton.setImage(new Image("/view/buttons/sound2Button.png")); }
        else if(mediaPlayer.getVolume()==audioStep3){ soundButton.setImage(new Image("/view/buttons/sound3Button.png")); }
    }

    /*
     * button click effect
     */
    public void buttonSound() {
        MediaPlayer player;
        String s = "click.mp3";
        Media h = new Media(Paths.get(s).toUri().toString());
        player = new MediaPlayer(h);
        player.setCycleCount(1);
        player.play();
        System.out.println("sound");
    }

    /*
     * Button HoverEffects
     */

    @FXML
    void onNewGameEnter(MouseEvent event){
        Image image = new Image("/view/buttons/newGameButtonHover.png");
        newGameButton.setImage(image);
    }

    @FXML
    void onNewGameExit(MouseEvent event){
        Image image = new Image("/view/buttons/newGameButton.png");
        newGameButton.setImage(image);
    }

    @FXML
    void onSettingsEnter(MouseEvent event){
        Image image = new Image("/view/buttons/settingsButtonHover.png");
        settingsButton.setImage(image);
    }

    @FXML
    void onSettingsExit(MouseEvent event){
        Image image = new Image("/view/buttons/settingsButton.png");
        settingsButton.setImage(image);
    }

    @FXML
    void onHighscoreEnter(MouseEvent event){
        Image image = new Image("/view/buttons/highscoresButtonHover.png");
        highscoresButton.setImage(image);
    }

    @FXML
    void onHighscoreExit(MouseEvent event){
        Image image = new Image("/view/buttons/highscoresButton.png");
        highscoresButton.setImage(image);
    }

    @FXML
    void onLoadGameEnter(MouseEvent event){
        Image image = new Image("/view/buttons/loadGameButtonHover.png");
        loadGameButton.setImage(image);
    }

    @FXML
    void onLoadGameExit(MouseEvent event){
        Image image = new Image("/view/buttons/loadGameButton.png");
        loadGameButton.setImage(image);
    }

    @FXML
    void onCloseEnter(MouseEvent event){
        Image image = new Image("/view/buttons/closeButtonHover.png");
        closeButton.setImage(image);
    }

    @FXML
    void onCloseExit(MouseEvent event){
        Image image = new Image("/view/buttons/closeButton.png");
        closeButton.setImage(image);
    }

    @FXML
    void onQuestionEnter(MouseEvent event){
        Image image = new Image("/view/buttons/questionButtonHover.png");
        questionButton.setImage(image);
    }

    @FXML
    void onQuestionExit(MouseEvent event){
        Image image = new Image("/view/buttons/questionButton.png");
        questionButton.setImage(image);
    }

    @FXML
    void onSoundEnter(){
        if(soundButton.getImage().getUrl().endsWith("0Button.png")){
            soundButton.setImage(new Image("/view/buttons/sound0ButtonHover.png"));
        }
        else if(soundButton.getImage().getUrl().endsWith("1Button.png")){
            soundButton.setImage(new Image("/view/buttons/sound1ButtonHover.png"));
        }
        else if(soundButton.getImage().getUrl().endsWith("2Button.png")){
            soundButton.setImage(new Image("/view/buttons/sound2ButtonHover.png"));
        }
        else{
            soundButton.setImage(new Image("/view/buttons/sound3ButtonHover.png"));
        }
    }

    @FXML
    void onSoundExit(){
        if(mediaPlayer.getVolume()==0){
            soundButton.setImage(new Image("/view/buttons/sound0Button.png"));
        }
        else if(mediaPlayer.getVolume()==audioStep1){
            soundButton.setImage(new Image("/view/buttons/sound1Button.png"));
        }
        else if(mediaPlayer.getVolume()==audioStep2){
            soundButton.setImage(new Image("/view/buttons/sound2Button.png"));
        }
        else{
            soundButton.setImage(new Image("/view/buttons/sound3Button.png"));
        }
    }

}
