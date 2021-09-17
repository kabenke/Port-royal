package view;

import java.io.IOException;
import java.util.ArrayList;

import controller.MainController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Match;
import model.Player;
import model.PlayerType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class NeuesSpielViewController extends AnchorPane {

    private Stage primaryStage;
    private MainController mainController;
    private int showPlayers;
    private int simSpeed;
    private boolean standardIsChecked;
    private boolean kIIsChecked;
    private boolean playerShuffle;
    private boolean alternativeEnd;
    private MediaPlayer mediaPlayer;

    @FXML
    private AnchorPane pane;

    @FXML
    private Text newGameText;

    @FXML
    private ImageView avatarImg1, avatarImg2, avatarImg3, avatarImg4, avatarImg5;

    @FXML
    private TextField playerNameField1, playerNameField2, playerNameField3, playerNameField4, playerNameField5;

    @FXML
    private Text playerType1, playerType2, playerType3, playerType4, playerType5;

    @FXML
    private ImageView left1, left2, left3, left4, left5, right1, right2, right3, right4, right5;

    @FXML
    private ImageView minus1, minus2, minus3, minus4, minus5;

    @FXML
    private ImageView plusButton, backButton, createGameButton, alternativeEndCheck;

    @FXML
    private ImageView standardCheck, kICheck, plusSim, minusSim, playerShuffleCheck;

    @FXML
    private Text simText;

    @FXML
    private Label simSpeedCaption;

    /*
     * constructor
     */
    public NeuesSpielViewController(Stage primaryStage, MainController mainController, MediaPlayer player) {
        this.mainController = mainController;
        this.primaryStage = primaryStage;
        this.mediaPlayer = player;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NeuesSpiel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {

            e.printStackTrace();
        }
        this.showPlayers = 2;
        this.updatePlayerList();
        this.standardIsChecked = true;
        this.kIIsChecked = false;
        this.simSpeed = 5;
        this.playerShuffle = true;
        playerNameField1.setText(getRandomName());
        playerNameField2.setText(getRandomName());
        this.alternativeEnd = false;
        alternativeEndCheck.setImage(new Image("/view/buttons/checkboxButtonUnchecked.png"));
        this.updatePlayerList();
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

    @FXML
    void onCreateGame(MouseEvent event){

        if(!playerNameField1.getText().equals("") && !playerNameField2.getText().equals("")
                && (showPlayers<3 || !playerNameField3.getText().equals(""))
                && (showPlayers<4 || !playerNameField4.getText().equals(""))
                && (showPlayers<5 || !playerNameField5.getText().equals(""))
                && (!createGameButton.getImage().getUrl().endsWith("/view/buttons/createGameButtonDisable.png")) )
        {

            //create new game
            Match match = new Match();
            mainController.getPortRoyal().setActiveMatch(match);
            //set AI-speed
            match.setAiSpeed(Integer.parseInt(simText.getText()));
            //set shuffletype
            match.setRandomShuffle(standardIsChecked);
            //set End
            match.getMatchHelp().setAlternativeEnd(this.alternativeEnd);
            //add players
            addPlayersToPlayerList();
            //ggf shuffle playerlist
            if(playerShuffle){ mainController.getMatchController().shufflePlayerlist(match); }
            //set drawpile
            mainController.getMatchController().initializeDrawPile(match);
            //add new Turn
            mainController.getMatchController().addTurn(match);

            //Scene wechseln
            try {
                SpielenViewController spielenViewController = new SpielenViewController(primaryStage, mainController, mediaPlayer);
                Scene scene = new Scene(spielenViewController);
                scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
                primaryStage.setTitle("Port Royal");
                scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if(event.getCode() == KeyCode.ESCAPE){
                            spielenViewController.onEsc();
                        }
                    }
                });
                primaryStage.setScene(scene);
                primaryStage.show();
                spielenViewController.actingPlayerAnimation();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * Help Methods
     */
    public void addPlayersToPlayerList(){
        //Player 1
        Player player1 = new Player();
        player1.setName(playerNameField1.getText());
        player1.setAvatarID(this.getAvatarID(avatarImg1));
        player1.setPlayerType(this.getPlayerType(playerType1.getText()));
        mainController.getPortRoyalController().addPlayerToMatch(player1);
        //Player 2
        Player player2 = new Player();
        player2.setName(playerNameField2.getText());
        player2.setAvatarID(this.getAvatarID(avatarImg2));
        player2.setPlayerType(this.getPlayerType(playerType2.getText()));
        mainController.getPortRoyalController().addPlayerToMatch(player2);
        //Player 3
        if(showPlayers >= 3){
            Player player3 = new Player();
            player3.setName(playerNameField3.getText());
            player3.setAvatarID(this.getAvatarID(avatarImg3));
            player3.setPlayerType(this.getPlayerType(playerType3.getText()));
            mainController.getPortRoyalController().addPlayerToMatch(player3);
        }
        //Player 4
        if(showPlayers >= 4){
            Player player4 = new Player();
            player4.setName(playerNameField4.getText());
            player4.setAvatarID(this.getAvatarID(avatarImg4));
            player4.setPlayerType(this.getPlayerType(playerType4.getText()));
            mainController.getPortRoyalController().addPlayerToMatch(player4);
        }
        //Player 5
        if(showPlayers >= 5){
            Player player5 = new Player();
            player5.setName(playerNameField5.getText());
            player5.setAvatarID(this.getAvatarID(avatarImg5));
            player5.setPlayerType(this.getPlayerType(playerType5.getText()));
            mainController.getPortRoyalController().addPlayerToMatch(player5);
        }
    }

    public int getAvatarID(ImageView image){
        return Integer.parseInt(image.getImage().getUrl().substring(image.getImage().getUrl().length()-6, image.getImage().getUrl().length()-4));
    }

    public PlayerType getPlayerType(String type){
        if(type.equals("Mensch")){ return PlayerType.HUMAN; }
        else if(type.equals("KI: Leicht")){ return PlayerType.EASY_AI; }
        else if(type.equals("KI: Mittel")){ return PlayerType.NORMAL_AI; }
        else { return PlayerType.HARD_AI; }
    }

    @FXML
    void onAlternativeEndCheck(){
        this.alternativeEnd = !this.alternativeEnd;
        if(this.alternativeEnd){
            alternativeEndCheck.setImage(new Image("/view/buttons/checkboxButtonChecked.png"));
        }
        else{
            alternativeEndCheck.setImage(new Image("/view/buttons/checkboxButtonUnchecked.png"));
        }
    }

    /*
     * add player
     */

    @FXML
    void onPlusClick(MouseEvent event){
        if(showPlayers < 5){
            showPlayers++;
        }
        if(showPlayers == 3){
            playerNameField3.setText(getRandomName());
            playerType3.setText("Mensch");
            avatarImg3.setImage(new Image("/view/avatars/avatar03.png"));
        }
        if(showPlayers == 4){
            playerNameField4.setText(getRandomName());
            playerType4.setText("Mensch");
            avatarImg4.setImage(new Image("/view/avatars/avatar02.png"));
        }
        if(showPlayers == 5){
            playerNameField5.setText(getRandomName());
            playerType5.setText("Mensch");
            avatarImg5.setImage(new Image("/view/avatars/avatar01.png"));
        }
        this.updatePlayerList();
    }

    /*
     * playerList update
     */

    public void updatePlayerList(){
        avatarImg5.setVisible(showPlayers >= 5);
        playerNameField5.setVisible(showPlayers >= 5);
        playerType5.setVisible(showPlayers >= 5);
        left5.setVisible(showPlayers >= 5);
        right5.setVisible(showPlayers >= 5);

        avatarImg4.setVisible(showPlayers >= 4);
        playerNameField4.setVisible(showPlayers >= 4);
        playerType4.setVisible(showPlayers >= 4);
        left4.setVisible(showPlayers >= 4);
        right4.setVisible(showPlayers >= 4);

        avatarImg3.setVisible(showPlayers >= 3);
        playerNameField3.setVisible(showPlayers >= 3);
        playerType3.setVisible(showPlayers >= 3);
        left3.setVisible(showPlayers >= 3);
        right3.setVisible(showPlayers >= 3);

        plusButton.setVisible(showPlayers < 5);
        if (showPlayers == 2) {plusButton.setLayoutY(400);}
        else if (showPlayers == 3) {plusButton.setLayoutY(500);}
        else {plusButton.setLayoutY(600);}

        minus1.setVisible(showPlayers > 2);
        minus2.setVisible(showPlayers > 2);
        minus3.setVisible(showPlayers > 2);
        minus4.setVisible(showPlayers > 3);
        minus5.setVisible(showPlayers > 4);

        if(showPlayers <= 2 && ((!playerType1.getText().equals("Mensch"))||(!playerType2.getText().equals("Mensch")))){
            simSpeedCaption.setVisible(true);
            simText.setVisible(true);
            minusSim.setVisible(true);
            plusSim.setVisible(true);
        }
        else{
            simSpeedCaption.setVisible(false);
            simText.setVisible(false);
            minusSim.setVisible(false);
            plusSim.setVisible(false);
        }

        if(showPlayers > 2 && kiNumber() > 0){
            createGameButton.setImage(new Image("/view/buttons/createGameButtonDisable.png"));
        }
        else{
            createGameButton.setImage(new Image("/view/buttons/createGameButton.png"));
        }

    }

    public int kiNumber(){
        int num = 0;
        if(playerType1.isVisible() && !playerType1.getText().equals("Mensch")){
            num++;
        }
        if(playerType2.isVisible() && !playerType2.getText().equals("Mensch")){
            num++;
        }
        if(playerType3.isVisible() && !playerType3.getText().equals("Mensch")){
            num++;
        }
        if(playerType4.isVisible() && !playerType4.getText().equals("Mensch")){
            num++;
        }
        if(playerType5.isVisible() && !playerType5.getText().equals("Mensch")){
            num++;
        }
        return num;
    }

    /*
     * change Playertypes
     */

    @FXML
    void onPrev1(MouseEvent event){
        playerType1.setText(getPrevType(playerType1.getText()));
        this.updatePlayerList();
    }

    @FXML
    void onPrev2(MouseEvent event){
        playerType2.setText(getPrevType(playerType2.getText()));
        this.updatePlayerList();
    }

    @FXML
    void onPrev3(MouseEvent event){
        playerType3.setText(getPrevType(playerType3.getText()));
        this.updatePlayerList();
    }

    @FXML
    void onPrev4(MouseEvent event){
        playerType4.setText(getPrevType(playerType4.getText()));
        this.updatePlayerList();
    }

    @FXML
    void onPrev5(MouseEvent event){
        playerType5.setText(getPrevType(playerType5.getText()));
        this.updatePlayerList();
    }

    @FXML
    void onNext1(MouseEvent event){
        playerType1.setText(getNextType(playerType1.getText()));
        this.updatePlayerList();
    }

    @FXML
    void onNext2(MouseEvent event){
        playerType2.setText(getNextType(playerType2.getText()));
        this.updatePlayerList();
    }

    @FXML
    void onNext3(MouseEvent event){
        playerType3.setText(getNextType(playerType3.getText()));
        this.updatePlayerList();
    }

    @FXML
    void onNext4(MouseEvent event){
        playerType4.setText(getNextType(playerType4.getText()));
        this.updatePlayerList();
    }

    @FXML
    void onNext5(MouseEvent event){
        playerType5.setText(getNextType(playerType5.getText()));
        this.updatePlayerList();
    }

    public String getPrevType(String type){
        if(type.equals("KI: Schwierig")){ return "KI: Mittel"; }
        else if(type.equals("KI: Mittel")){ return "KI: Leicht"; }
        else if(type.equals("KI: Leicht")){ return "Mensch"; }
        else { return "KI: Schwierig"; }
    }

    public String getNextType(String type){
        if(type.equals("KI: Schwierig")){ return "Mensch"; }
        else if(type.equals("KI: Mittel")){ return "KI: Schwierig"; }
        else if(type.equals("KI: Leicht")){ return "KI: Mittel"; }
        else { return "KI: Leicht"; }
    }

    /*
     * game rules
     */
    @FXML
    void onStandardCheckClick(){
        standardIsChecked = true;
        kIIsChecked = false;
        updateChecks();
    }

    @FXML
    void onKICheckClick(){
        standardIsChecked = false;
        kIIsChecked = true;
        updateChecks();
    }

    /*
     * simulation speed
     */
    @FXML
    void onSimMinusClick(){
        if(simSpeed>0){ simSpeed--; }
        updateSim();
    }

    @FXML
    void onSimPlusClick(){
        if(simSpeed<10){ simSpeed++; }
        updateSim();
    }

    public void updateSim(){
        simText.setText(simSpeed+"");
        if(simSpeed==0){ minusSim.setImage(new Image("view/buttons/minusButtonDisable.png")); }
        else{ minusSim.setImage(new Image("view/buttons/minusButton.png")); }
        if(simSpeed==10){ plusSim.setImage(new Image("view/buttons/plusButtonDisable.png")); }
        else{ plusSim.setImage(new Image("view/buttons/plusButton.png")); }
    }

    public void updateChecks(){
        if(standardIsChecked){ standardCheck.setImage(new Image("/view/buttons/checkboxButtonChecked.png")); }
        else{ standardCheck.setImage(new Image("/view/buttons/checkboxButtonUnchecked.png")); }
        if(kIIsChecked){ kICheck.setImage(new Image("/view/buttons/checkboxButtonChecked.png")); }
        else{ kICheck.setImage(new Image("/view/buttons/checkboxButtonUnchecked.png")); }
    }

    /*
     * playerlist shuffle option
     */

    @FXML
    void onPlayerShuffleCheck(){
        playerShuffle = !playerShuffle;
        if(playerShuffle){ playerShuffleCheck.setImage(new Image("/view/buttons/checkboxButtonChecked.png")); }
        else{ playerShuffleCheck.setImage(new Image("/view/buttons/checkboxButtonUnchecked.png")); }
    }


    /*
     * change Avatars
     */

    @FXML
    void onAvatarClick1(MouseEvent event){
        if(getNextAvatar(avatarImg1.getImage()) != null){
            avatarImg1.setImage(getNextAvatar(avatarImg1.getImage()));
        }
    }

    @FXML
    void onAvatarClick2(MouseEvent event){
        if(getNextAvatar(avatarImg2.getImage()) != null){
            avatarImg2.setImage(getNextAvatar(avatarImg2.getImage()));
        }
    }

    @FXML
    void onAvatarClick3(MouseEvent event){
        if(getNextAvatar(avatarImg3.getImage()) != null){
            avatarImg3.setImage(getNextAvatar(avatarImg3.getImage()));
        }
    }

    @FXML
    void onAvatarClick4(MouseEvent event){
        if(getNextAvatar(avatarImg4.getImage()) != null){
            avatarImg4.setImage(getNextAvatar(avatarImg4.getImage()));
        }
    }

    @FXML
    void onAvatarClick5(MouseEvent event){
        if(getNextAvatar(avatarImg5.getImage()) != null){
            avatarImg5.setImage(getNextAvatar(avatarImg5.getImage()));
        }
    }

    public Image getNextAvatar(Image image){
        int maxAvatar = 4;
        String url = image.getUrl();
        String id = url.substring(url.length()-6, url.length()-4);
        try{
            int avatarID = Integer.parseInt(id);
            avatarID++;
            if(avatarID > maxAvatar) {avatarID = 1;}
            Image newImage;
            if(avatarID>9) {
                newImage = new Image("/view/avatars/avatar" + avatarID + ".png");
            }
            else{
                newImage = new Image("/view/avatars/avatar0" + avatarID + ".png");
            }
            return newImage;
        }
        catch(Exception e){
            return null;
        }
    }

    /*
     * delete Player from List
     */

    @FXML
    void onDelete1(MouseEvent event){
        if(showPlayers >= 2){
            playerNameField1.setText(playerNameField2.getText());
            playerType1.setText(playerType2.getText());
            avatarImg1.setImage(avatarImg2.getImage());
        }
        if(showPlayers >= 3){
            playerNameField2.setText(playerNameField3.getText());
            playerType2.setText(playerType3.getText());
            avatarImg2.setImage(avatarImg3.getImage());
        }
        if(showPlayers >= 4){
            playerNameField3.setText(playerNameField4.getText());
            playerType3.setText(playerType4.getText());
            avatarImg3.setImage(avatarImg4.getImage());
        }
        if(showPlayers >= 5){
            playerNameField4.setText(playerNameField5.getText());
            playerType4.setText(playerType5.getText());
            avatarImg4.setImage(avatarImg5.getImage());
        }
        showPlayers--;
        updatePlayerList();
    }

    @FXML
    void onDelete2(MouseEvent event){
        if(showPlayers >= 3){
            playerNameField2.setText(playerNameField3.getText());
            playerType2.setText(playerType3.getText());
            avatarImg2.setImage(avatarImg3.getImage());
        }
        if(showPlayers >= 4){
            playerNameField3.setText(playerNameField4.getText());
            playerType3.setText(playerType4.getText());
            avatarImg3.setImage(avatarImg4.getImage());
        }
        if(showPlayers >= 5){
            playerNameField4.setText(playerNameField5.getText());
            playerType4.setText(playerType5.getText());
            avatarImg4.setImage(avatarImg5.getImage());
        }
        showPlayers--;
        updatePlayerList();
    }

    @FXML
    void onDelete3(MouseEvent event){
        if(showPlayers >= 4){
            playerNameField3.setText(playerNameField4.getText());
            playerType3.setText(playerType4.getText());
            avatarImg3.setImage(avatarImg4.getImage());
        }
        if(showPlayers >= 5){
            playerNameField4.setText(playerNameField5.getText());
            playerType4.setText(playerType5.getText());
            avatarImg4.setImage(avatarImg5.getImage());
        }
        showPlayers--;
        updatePlayerList();
    }

    @FXML
    void onDelete4(MouseEvent event){
        if(showPlayers >= 5){
            playerNameField4.setText(playerNameField5.getText());
            playerType4.setText(playerType5.getText());
            avatarImg4.setImage(avatarImg5.getImage());
        }
        showPlayers--;
        updatePlayerList();
    }


    @FXML
    void onDelete5(MouseEvent event){
        showPlayers--;
        updatePlayerList();
    }

    /*
     * Button HoverEffects
     */

    @FXML
    void onPlusEnter(MouseEvent event){
        Image image = new Image("/view/buttons/plusButtonHover.png");
        plusButton.setImage(image);
    }

    @FXML
    void onPlusExit(MouseEvent event){
        Image image = new Image("/view/buttons/plusButton.png");
        plusButton.setImage(image);
    }

    @FXML
    void onMinus1Enter(MouseEvent event){
        Image image = new Image("/view/buttons/minusButtonHover.png");
        minus1.setImage(image);
    }

    @FXML
    void onMinus1Exit(MouseEvent event){
        Image image = new Image("/view/buttons/minusButton.png");
        minus1.setImage(image);
    }

    @FXML
    void onMinus2Enter(MouseEvent event){
        Image image = new Image("/view/buttons/minusButtonHover.png");
        minus2.setImage(image);
    }

    @FXML
    void onMinus2Exit(MouseEvent event){
        Image image = new Image("/view/buttons/minusButton.png");
        minus2.setImage(image);
    }

    @FXML
    void onMinus3Enter(MouseEvent event){
        Image image = new Image("/view/buttons/minusButtonHover.png");
        minus3.setImage(image);
    }

    @FXML
    void onMinus3Exit(MouseEvent event){
        Image image = new Image("/view/buttons/minusButton.png");
        minus3.setImage(image);
    }

    @FXML
    void onMinus4Enter(MouseEvent event){
        Image image = new Image("/view/buttons/minusButtonHover.png");
        minus4.setImage(image);
    }

    @FXML
    void onMinus4Exit(MouseEvent event){
        Image image = new Image("/view/buttons/minusButton.png");
        minus4.setImage(image);
    }

    @FXML
    void onMinus5Exit(MouseEvent event){
        Image image = new Image("/view/buttons/minusButton.png");
        minus5.setImage(image);
    }

    @FXML
    void onMinus5Enter(MouseEvent event){
        Image image = new Image("/view/buttons/minusButtonHover.png");
        minus5.setImage(image);
    }

    @FXML
    void onBackExit(MouseEvent event){
        Image image = new Image("/view/buttons/arrowLeftButton.png");
        backButton.setImage(image);
    }

    @FXML
    void onBackEnter(MouseEvent event){
        Image image = new Image("/view/buttons/arrowLeftButtonHover.png");
        backButton.setImage(image);
    }

    @FXML
    void onCreateGameExit(MouseEvent event){
        if(!createGameButton.getImage().getUrl().endsWith("/view/buttons/createGameButtonDisable.png")){
            Image image = new Image("/view/buttons/createGameButton.png");
            createGameButton.setImage(image);
        }
    }

    @FXML
    void onCreateGameEnter(MouseEvent event){
        if(!createGameButton.getImage().getUrl().endsWith("/view/buttons/createGameButtonDisable.png")){
            Image image = new Image("/view/buttons/createGameButtonHover.png");
            createGameButton.setImage(image);
        }
    }

    private String getRandomName() {
        ArrayList<String> names = new ArrayList<>();
        names.add("Stiefelriemen");
        names.add("Schwarzstiefel");
        names.add("Rotbart");
        names.add("Schwarzbart");
        names.add("Säbelzahn");
        names.add("Holzbein");
        names.add("Stoppelbart");
        int rand = (int)(Math.random()*names.size());
        String name = names.get(rand);
        boolean alreadyUsed = false;
        if(playerNameField1.getText().equals(name)){ alreadyUsed = true; }
        if(playerNameField2.getText().equals(name)){ alreadyUsed = true; }
        if(playerNameField3.getText().equals(name)){ alreadyUsed = true; }
        if(playerNameField4.getText().equals(name)){ alreadyUsed = true; }
        if(playerNameField5.getText().equals(name)){ alreadyUsed = true; }
        if(!alreadyUsed){  return name; }
        else{ return getRandomName(); }
    }
}