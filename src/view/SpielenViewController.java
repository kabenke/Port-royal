package view;

import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import controller.MainController;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.*;
import javafx.scene.media.MediaPlayer;
import model.Color;

public class SpielenViewController extends AnchorPane {

    private Stage primaryStage;
    private MainController mainController;
    ArrayList<Card> toShowHarbor;
    ArrayList<Expedition> toShowExp;
    ArrayList<Card> toShowHand;
    private int harborIndex;
    private int handIndex;
    private ImageView selectedImg;
    private Card selectedCard;
    private Card lastDrawn;
    private final int VP_TO_WIN = 12;
    private MediaPlayer mediaPlayer;
    private Player lastActing;
    private boolean isPaused;

    @FXML //background
    private AnchorPane pane;

    @FXML //texts for Player1-Attributes
    private Text coinText1, victoryText1, swordText1, jokerText1, captainText1, priestText1, settlerText1, mademoiselleText1, governorText1,
            jesterText1, admiralText1, traderBlueText1, traderRedText1, traderGreenText1, traderBlackText1, traderYellowText1, nameText1;

    @FXML //texts for Player2-Attributes
    private Text coinText2, victoryText2, swordText2, jokerText2, captainText2, priestText2, settlerText2, mademoiselleText2, governorText2,
            jesterText2, admiralText2, traderBlueText2, traderRedText2, traderGreenText2, traderBlackText2, traderYellowText2, nameText2;

    @FXML //texts for Player3-Attributes
    private Text coinText3, victoryText3, swordText3, jokerText3, captainText3, priestText3, settlerText3, mademoiselleText3, governorText3,
            jesterText3, admiralText3, traderBlueText3, traderRedText3, traderGreenText3, traderBlackText3, traderYellowText3, nameText3;

    @FXML //texts for Player4-Attributes
    private Text coinText4, victoryText4, swordText4, jokerText4, captainText4, priestText4, settlerText4, mademoiselleText4, governorText4,
            jesterText4, admiralText4, traderBlueText4, traderRedText4, traderGreenText4, traderBlackText4, traderYellowText4, nameText4;

    @FXML //texts for Player5-Attributes
    private Text coinText5, victoryText5, swordText5, jokerText5, captainText5, priestText5, settlerText5, mademoiselleText5, governorText5,
            jesterText5, admiralText5, traderBlueText5, traderRedText5, traderGreenText5, traderBlackText5, traderYellowText5, nameText5;

    @FXML //imageviews for Player3-Icons
    private ImageView backgroundImg3, coinImg3, victoryImg3, swordImg3, jokerImg3, captainImg3, priestImg3, settlerImg3,
            mademoiselleImg3, governorImg3, jesterImg3, admiralImg3;

    @FXML //imageviews for Player4-Icons
    private ImageView backgroundImg4, coinImg4, victoryImg4, swordImg4, jokerImg4, captainImg4, priestImg4, settlerImg4,
            mademoiselleImg4, governorImg4, jesterImg4, admiralImg4;

    @FXML //imageviews for Player5-Icons
    private ImageView backgroundImg5, coinImg5, victoryImg5, swordImg5, jokerImg5, captainImg5, priestImg5, settlerImg5,
            mademoiselleImg5, governorImg5, jesterImg5, admiralImg5;

    @FXML //imageviews for harbor display
    private ImageView harbor1, harbor2, harbor3, harbor4, harbor5, harbor6, harbor7, harbor8, harbor9, harbor10, harbor11, harbor12;

    @FXML //imageviews for expedition display
    private ImageView exp1, exp2, exp3, exp4, exp5, exp6;

    @FXML //imageviews for hand display
    private ImageView hand1, hand2, hand3, hand4, hand5, hand6;

    @FXML //imageviews for shift index
    private ImageView harborUpButton, harborDownButton, leftButton, rightButton, hintButton;

    @FXML //imageviews for player avatars
    private ImageView player1Img, player2Img, player3Img, player4Img, player5Img;

    @FXML
    private ImageView pauseButton, pauseBackground, backToGameButton, saveButton, closeGameButton, toStartButton, toHighscoreButton;

    @FXML
    private ImageView bigPlayerView, bubble, undoButton, redoButton;

    @FXML
    private ImageView drawpileImg, discardPile, rulesButton, infoButton;

    @FXML
    private ImageView bigCoin, bigVictory, bigSaber, bigJack, bigCaptain, bigPriest, bigSettler,
                      bigMade, bigGov, bigJester, bigAdmiral;

    @FXML
    private Text bigCoinText, bigVictoryText, bigSaberText, bigJackText, bigCaptainText, bigPriestText,
            bigSettlerText, bigMadeText, bigGovText, bigJesterText, bigAdmiralText;

    @FXML
    private Text playerCoinCount, drawPileCount, discardPileCount, phaseText, playerHelpText, pauseText,
            end1Text, end2Text, end3Text, end4Text, end5Text, vp1, vp2, vp3, vp4, vp5,
            coin1, coin2, coin3, coin4, coin5, animationName, hintText;

    @FXML //imageviews for playerAction Butttons
    private ImageView repelButton, hireButton, fullfillButton, endDiscoverButton, sellShipButton, endMoveButton;

    @FXML
    private ImageView animationBackground;

    @FXML
    private ListView<String> listOfMoves;

    /*
     * constructor
     */

    public SpielenViewController(Stage primaryStage, MainController mainController, MediaPlayer player) {
        this.mainController = mainController;
        this.primaryStage = primaryStage;
        this.mediaPlayer = player;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Spielen.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {

            e.printStackTrace();
        }
        bigPlayerView.setVisible(false);
        this.onExitPause();
        harborIndex = 0;
        handIndex = 0;
        selectedCard = null;
        selectedImg = null;
        lastDrawn = null;
        lastActing = null;
        isPaused = false;
        ArrayList<Turn> turnHistory = mainController.getPortRoyal().getActiveMatch().getTurnHistory();
        toShowHarbor = turnHistory.get(turnHistory.size()-1).getHarbourDisplay();
        toShowExp = mainController.getPortRoyal().getActiveMatch().getExpeditionDisplay();
        end1Text.setVisible(false); end2Text.setVisible(false); end3Text.setVisible(false); end4Text.setVisible(false); end5Text.setVisible(false);
        vp1.setVisible(false); vp2.setVisible(false); vp3.setVisible(false); vp4.setVisible(false); vp5.setVisible(false);
        coin1.setVisible(false); coin2.setVisible(false); coin3.setVisible(false); coin4.setVisible(false); coin5.setVisible(false);
        toStartButton.setVisible(false); toHighscoreButton.setVisible(false);
        animationBackground.setVisible(false); animationName.setVisible(false);
        hintText.setVisible(false); bubble.setVisible(false);
        this.hidePlayerView(1);
        this.refresh();
    }

    /*
     * refreshes
     */

    public void refresh(){
        this.refreshHarborDisplay();
        this.refreshExpedition();
        this.refreshPlayerAttributes();
        this.refreshPlayerHand();
        this.refreshPiles();
        this.refreshSelectedCard();
        this.refreshUndoRedoButtons();
        this.refreshPlayerHelpText();
        this.refreshShowMoves();
        if(mainController.getPortRoyal().getActiveMatch().getActiveTurn().getActingPlayer() == lastActing) {
            this.refreshPlayerActionButtons();
            this.refreshAIMoves();
        }
        else if(mainController.getPortRoyal().getActiveMatch().getActiveTurn().getActingPlayer() == null){
            this.refreshPlayerActionButtons();
            this.refreshAIMoves();
            this.actingPlayerAnimation();
            lastActing = mainController.getPortRoyal().getActiveMatch().getActiveTurn().getActingPlayer();
        }
        else{
            if(!matchEnd()){
                this.actingPlayerAnimation();
                lastActing = mainController.getPortRoyal().getActiveMatch().getActiveTurn().getActingPlayer();
            }
        }
        mainController.getIOController().save();
    }

    public void refreshShowMoves(){
        listOfMoves.getItems().clear();
        ArrayList<Move> moves = new ArrayList<>();
        Match match = mainController.getPortRoyal().getActiveMatch();
        for(Turn turn : match.getTurnHistory()){
            boolean discover = true;
            for(Move move : turn.getMoves()){
                moves.add(move);
                listOfMoves.getItems().add(moveToString(move, discover));
                if(move.getCard()==null){ discover = false; }
            }
        }
        if(listOfMoves.getItems().isEmpty()){
            listOfMoves.getItems().add("Keine Züge vorhanden.");
        }
        if(match.getPlayerList().size()<=2){
            listOfMoves.scrollTo(listOfMoves.getItems().size()-1);
            listOfMoves.setVisible(true);
        }
        else{
            listOfMoves.setVisible(false);
        }
    }

    public void refreshHarborDisplay(){
        toShowHarbor = mainController.getPortRoyal().getActiveMatch().getActiveTurn().getHarbourDisplay();
        try{ harbor1.setImage(new Image(getCardUrl(toShowHarbor.get(harborIndex+0)))); harbor1.setVisible(true); }
        catch(IndexOutOfBoundsException e){ harbor1.setVisible(false); }
        try{ harbor2.setImage(new Image(getCardUrl(toShowHarbor.get(harborIndex+1)))); harbor2.setVisible(true); }
        catch(IndexOutOfBoundsException e){ harbor2.setVisible(false); }
        try{ harbor3.setImage(new Image(getCardUrl(toShowHarbor.get(harborIndex+2)))); harbor3.setVisible(true); }
        catch(IndexOutOfBoundsException e){ harbor3.setVisible(false); }
        try{ harbor4.setImage(new Image(getCardUrl(toShowHarbor.get(harborIndex+3)))); harbor4.setVisible(true); }
        catch(IndexOutOfBoundsException e){ harbor4.setVisible(false); }
        try{ harbor5.setImage(new Image(getCardUrl(toShowHarbor.get(harborIndex+4)))); harbor5.setVisible(true); }
        catch(IndexOutOfBoundsException e){ harbor5.setVisible(false); }
        try{ harbor6.setImage(new Image(getCardUrl(toShowHarbor.get(harborIndex+5)))); harbor6.setVisible(true); }
        catch(IndexOutOfBoundsException e){ harbor6.setVisible(false); }
        try{ harbor7.setImage(new Image(getCardUrl(toShowHarbor.get(harborIndex+6)))); harbor7.setVisible(true); }
        catch(IndexOutOfBoundsException e){ harbor7.setVisible(false); }
        try{ harbor8.setImage(new Image(getCardUrl(toShowHarbor.get(harborIndex+7)))); harbor8.setVisible(true); }
        catch(IndexOutOfBoundsException e){ harbor8.setVisible(false); }
        try{ harbor9.setImage(new Image(getCardUrl(toShowHarbor.get(harborIndex+8)))); harbor9.setVisible(true); }
        catch(IndexOutOfBoundsException e){harbor9.setVisible(false);}
        try{ harbor10.setImage(new Image(getCardUrl(toShowHarbor.get(harborIndex+9)))); harbor10.setVisible(true); }
        catch(IndexOutOfBoundsException e){ harbor10.setVisible(false); }
        try{ harbor11.setImage(new Image(getCardUrl(toShowHarbor.get(harborIndex+10)))); harbor11.setVisible(true); }
        catch(IndexOutOfBoundsException e){ harbor11.setVisible(false); }
        try{ harbor12.setImage(new Image(getCardUrl(toShowHarbor.get(harborIndex+11)))); harbor12.setVisible(true); }
        catch(IndexOutOfBoundsException e){ harbor12.setVisible(false); }
        harborUpButton.setVisible(harborIndex>0);
        harborDownButton.setVisible((toShowHarbor.size()>(harborIndex+12)));
        if(mainController.getPortRoyal().getActiveMatch().getActiveTurn().getIsPhaseDiscover()){ phaseText.setText("Entdecken"); }
        else{ phaseText.setText("Handeln & Heuern"); }
    }

    public void refreshExpedition(){
        toShowExp = mainController.getPortRoyal().getActiveMatch().getExpeditionDisplay();
        try{ exp1.setImage(new Image(getCardUrl(toShowExp.get(0)))); exp1.setVisible(true); }
        catch(Exception e){ exp1.setVisible(false); }
        try{ exp2.setImage(new Image(getCardUrl(toShowExp.get(1)))); exp2.setVisible(true); }
        catch(Exception e){ exp2.setVisible(false); }
        try{ exp3.setImage(new Image(getCardUrl(toShowExp.get(2)))); exp3.setVisible(true); }
        catch(Exception e){ exp3.setVisible(false); }
        try{ exp4.setImage(new Image(getCardUrl(toShowExp.get(3)))); exp4.setVisible(true); }
        catch(Exception e){ exp4.setVisible(false); }
        try{ exp5.setImage(new Image(getCardUrl(toShowExp.get(4)))); exp5.setVisible(true); }
        catch(Exception e){ exp5.setVisible(false); }
        try{ exp6.setImage(new Image(getCardUrl(toShowExp.get(5)))); exp6.setVisible(true); }
        catch(Exception e){ exp6.setVisible(false); }
    }

    public void refreshPlayerAttributes(){
        int playerListSize = mainController.getPortRoyal().getActiveMatch().getPlayerList().size();

        Player slot1 = mainController.getPortRoyal().getActiveMatch().getActiveTurn().getActingPlayer();
        nameText1.setText(          slot1.getName());                       coinText1.setText(          slot1.getCoins()+"");
        playerCoinCount.setText(    slot1.getCoins()+"");                   victoryText1.setText(       slot1.getVictoryPoints()+"");
        swordText1.setText(         slot1.getSwords()+"");
        int[] persons =             slot1.getPlayerHelp().getCardCounts();
        jokerText1.setText(         persons[0]+"");                         captainText1.setText(       persons[1]+"");
        priestText1.setText(        persons[2]+"");                         settlerText1.setText(       persons[3]+"");
        mademoiselleText1.setText(  persons[4]+"");                         governorText1.setText(      persons[5]+"");
        jesterText1.setText(        persons[6]+"");                         admiralText1.setText(       persons[7]+"");
        traderBlueText1.setText(    "+" + slot1.getTraderBonus()[0]);       traderRedText1.setText(     "+" + slot1.getTraderBonus()[1]);
        traderGreenText1.setText(   "+" + slot1.getTraderBonus()[2]);       traderBlackText1.setText(   "+" + slot1.getTraderBonus()[3]);
        traderYellowText1.setText(  "+" + slot1.getTraderBonus()[4]);
        player1Img.setImage(getAvatar(slot1.getAvatarID()));

        Player slot2 = mainController.getPortRoyal().getActiveMatch().getActiveTurn().getTurnHelp().nextPlayer(slot1);
        nameText2.setText(          slot2.getName());                       coinText2.setText(          slot2.getCoins()+"");
        victoryText2.setText(       slot2.getVictoryPoints()+"");           swordText2.setText(         slot2.getSwords()+"");
        int[] persons2 =             slot2.getPlayerHelp().getCardCounts();
        jokerText2.setText(         persons2[0]+"");                        captainText2.setText(       persons2[1]+"");
        priestText2.setText(        persons2[2]+"");                        settlerText2.setText(       persons2[3]+"");
        mademoiselleText2.setText(  persons2[4]+"");                        governorText2.setText(      persons2[5]+"");
        jesterText2.setText(        persons2[6]+"");                        admiralText2.setText(       persons2[7]+"");
        traderBlueText2.setText(    "+" + slot2.getTraderBonus()[0]);       traderRedText2.setText(     "+" + slot2.getTraderBonus()[1]);
        traderGreenText2.setText(   "+" + slot2.getTraderBonus()[2]);       traderBlackText2.setText(   "+" + slot2.getTraderBonus()[3]);
        traderYellowText2.setText(  "+" + slot2.getTraderBonus()[4]);
        player2Img.setImage(getAvatar(slot2.getAvatarID()));

        boolean vis3 = playerListSize>=3;
        nameText3.setVisible(vis3); coinText3.setVisible(vis3); victoryText3.setVisible(vis3); swordText3.setVisible(vis3); jokerText3.setVisible(vis3);
        captainText3.setVisible(vis3); priestText3.setVisible(vis3); settlerText3.setVisible(vis3); mademoiselleText3.setVisible(vis3);
        governorText3.setVisible(vis3); jesterText3.setVisible(vis3); admiralText3.setVisible(vis3); traderBlueText3.setVisible(vis3);
        traderRedText3.setVisible(vis3); traderGreenText3.setVisible(vis3); traderBlackText3.setVisible(vis3); traderYellowText3.setVisible(vis3);
        coinImg3.setVisible(vis3); victoryImg3.setVisible(vis3); swordImg3.setVisible(vis3); jokerImg3.setVisible(vis3); captainImg3.setVisible(vis3);
        priestImg3.setVisible(vis3); settlerImg3.setVisible(vis3); mademoiselleImg3.setVisible(vis3); governorImg3.setVisible(vis3);
        jesterImg3.setVisible(vis3); admiralImg3.setVisible(vis3); backgroundImg3.setVisible(vis3);
        player3Img.setVisible(vis3);

        boolean vis4 = playerListSize>=4;
        nameText4.setVisible(vis4); coinText4.setVisible(vis4); victoryText4.setVisible(vis4); swordText4.setVisible(vis4); jokerText4.setVisible(vis4);
        captainText4.setVisible(vis4); priestText4.setVisible(vis4); settlerText4.setVisible(vis4); mademoiselleText4.setVisible(vis4);
        governorText4.setVisible(vis4); jesterText4.setVisible(vis4); admiralText4.setVisible(vis4); traderBlueText4.setVisible(vis4);
        traderRedText4.setVisible(vis4); traderGreenText4.setVisible(vis4); traderBlackText4.setVisible(vis4); traderYellowText4.setVisible(vis4);
        coinImg4.setVisible(vis4); victoryImg4.setVisible(vis4); swordImg4.setVisible(vis4); jokerImg4.setVisible(vis4); captainImg4.setVisible(vis4);
        priestImg4.setVisible(vis4); settlerImg4.setVisible(vis4); mademoiselleImg4.setVisible(vis4); governorImg4.setVisible(vis4);
        jesterImg4.setVisible(vis4); admiralImg4.setVisible(vis4); backgroundImg4.setVisible(vis4);
        player4Img.setVisible(vis4);

        boolean vis5 = playerListSize>=5;
        nameText5.setVisible(vis5); coinText5.setVisible(vis5); victoryText5.setVisible(vis5); swordText5.setVisible(vis5); jokerText5.setVisible(vis5);
        captainText5.setVisible(vis5); priestText5.setVisible(vis5); settlerText5.setVisible(vis5); mademoiselleText5.setVisible(vis5);
        governorText5.setVisible(vis5); jesterText5.setVisible(vis5); admiralText5.setVisible(vis5); traderBlueText5.setVisible(vis5);
        traderRedText5.setVisible(vis5); traderGreenText5.setVisible(vis5); traderBlackText5.setVisible(vis5); traderYellowText5.setVisible(vis5);
        coinImg5.setVisible(vis5); victoryImg5.setVisible(vis5); swordImg5.setVisible(vis5); jokerImg5.setVisible(vis5); captainImg5.setVisible(vis5);
        priestImg5.setVisible(vis5); settlerImg5.setVisible(vis5); mademoiselleImg5.setVisible(vis5); governorImg5.setVisible(vis5);
        jesterImg5.setVisible(vis5); admiralImg5.setVisible(vis5); backgroundImg5.setVisible(vis5);
        player5Img.setVisible(vis5);

        if(playerListSize>=3){
            Player slot3 = mainController.getPortRoyal().getActiveMatch().getActiveTurn().getTurnHelp().nextPlayer(slot2);
            nameText3.setText(          slot3.getName());                   coinText3.setText(          slot3.getCoins()+"");
            victoryText3.setText(       slot3.getVictoryPoints()+"");       swordText3.setText(         slot3.getSwords()+"");
            int[] persons3 =             slot3.getPlayerHelp().getCardCounts();
            jokerText3.setText(         persons3[0]+"");                    captainText3.setText(       persons3[1]+"");
            priestText3.setText(        persons3[2]+"");                    settlerText3.setText(       persons3[3]+"");
            mademoiselleText3.setText(  persons3[4]+"");                    governorText3.setText(      persons3[5]+"");
            jesterText3.setText(        persons3[6]+"");                    admiralText3.setText(       persons3[7]+"");
            traderBlueText3.setText(    "+" + slot3.getTraderBonus()[0]);   traderRedText3.setText(     "+" + slot3.getTraderBonus()[1]);
            traderGreenText3.setText(   "+" + slot3.getTraderBonus()[2]);   traderBlackText3.setText(   "+" + slot3.getTraderBonus()[3]);
            traderYellowText3.setText(  "+" + slot3.getTraderBonus()[4]);
            player3Img.setImage(getAvatar(slot3.getAvatarID()));

            if(playerListSize>=4){
                Player slot4 = mainController.getPortRoyal().getActiveMatch().getActiveTurn().getTurnHelp().nextPlayer(slot3);
                nameText4.setText(          slot4.getName());                   coinText4.setText(          slot4.getCoins()+"");
                victoryText4.setText(       slot4.getVictoryPoints()+"");       swordText4.setText(         slot4.getSwords()+"");
                int[] persons4 =             slot4.getPlayerHelp().getCardCounts();
                jokerText4.setText(         persons4[0]+"");                    captainText4.setText(       persons4[1]+"");
                priestText4.setText(        persons4[2]+"");                    settlerText4.setText(       persons4[3]+"");
                mademoiselleText4.setText(  persons4[4]+"");                    governorText4.setText(      persons4[5]+"");
                jesterText4.setText(        persons4[6]+"");                    admiralText4.setText(       persons4[7]+"");
                traderBlueText4.setText(    "+" + slot4.getTraderBonus()[0]);   traderRedText4.setText(     "+" + slot4.getTraderBonus()[1]);
                traderGreenText4.setText(   "+" + slot4.getTraderBonus()[2]);   traderBlackText4.setText(   "+" + slot4.getTraderBonus()[3]);
                traderYellowText4.setText(  "+" + slot4.getTraderBonus()[4]);
                player4Img.setImage(getAvatar(slot4.getAvatarID()));

                if(playerListSize>=5){
                    Player slot5 = mainController.getPortRoyal().getActiveMatch().getActiveTurn().getTurnHelp().nextPlayer(slot4);
                    nameText5.setText(          slot5.getName());                   coinText5.setText(          slot5.getCoins()+"");
                    victoryText5.setText(       slot5.getVictoryPoints()+"");       swordText5.setText(         slot5.getSwords()+"");
                    int[] persons5 =             slot5.getPlayerHelp().getCardCounts();
                    jokerText5.setText(         persons5[0]+"");                    captainText5.setText(       persons5[1]+"");
                    priestText5.setText(        persons5[2]+"");                    settlerText5.setText(       persons5[3]+"");
                    mademoiselleText5.setText(  persons5[4]+"");                    governorText5.setText(      persons5[5]+"");
                    jesterText5.setText(        persons5[6]+"");                    admiralText5.setText(       persons5[7]+"");
                    traderBlueText5.setText(    "+" + slot5.getTraderBonus()[0]);   traderRedText5.setText(     "+" + slot5.getTraderBonus()[1]);
                    traderGreenText5.setText(   "+" + slot5.getTraderBonus()[2]);   traderBlackText5.setText(   "+" + slot5.getTraderBonus()[3]);
                    traderYellowText5.setText(  "+" + slot5.getTraderBonus()[4]);
                    player5Img.setImage(getAvatar(slot5.getAvatarID()));
                }
            }
        }
    }

    public void refreshPlayerHand(){
        toShowHand = mainController.getPortRoyal().getActiveMatch().getActiveTurn().getActingPlayer().getNonGoldCards();
        if(handIndex == 0){
            try{
                hand1.setImage(new Image("/view/cards/backsite.jpg"));
                hand1.setVisible(true);
                playerCoinCount.setVisible(true);
            }
            catch(IndexOutOfBoundsException e){ hand1.setVisible(false); }
        }
        else{
            try{
                hand1.setImage(new Image(getCardUrl(toShowHand.get(handIndex-1))));
                hand1.setVisible(true);
                playerCoinCount.setVisible(false);
            }
            catch(IndexOutOfBoundsException e){ hand1.setVisible(false); }
        }
        try{ hand2.setImage(new Image(getCardUrl(toShowHand.get(handIndex)))); hand2.setVisible(true); }
        catch(IndexOutOfBoundsException e){ hand2.setVisible(false); }
        try{ hand3.setImage(new Image(getCardUrl(toShowHand.get(handIndex+1)))); hand3.setVisible(true); }
        catch(IndexOutOfBoundsException e){ hand3.setVisible(false); }
        try{ hand4.setImage(new Image(getCardUrl(toShowHand.get(handIndex+2)))); hand4.setVisible(true); }
        catch(IndexOutOfBoundsException e){ hand4.setVisible(false); }
        try{ hand5.setImage(new Image(getCardUrl(toShowHand.get(handIndex+3)))); hand5.setVisible(true); }
        catch(IndexOutOfBoundsException e){ hand5.setVisible(false); }
        try{ hand6.setImage(new Image(getCardUrl(toShowHand.get(handIndex+4)))); hand6.setVisible(true); }
        catch(IndexOutOfBoundsException e){ hand6.setVisible(false); }
        leftButton.setVisible(handIndex > 0);
        rightButton.setVisible(toShowHand.size() >= handIndex+6);
    }

    public void refreshPiles(){
        // Draw Pile
        ArrayList<Card> toShowDraw = mainController.getPortRoyal().getActiveMatch().getDrawPile();
        if(toShowDraw.size() == 0) {
            drawpileImg.setVisible(false);
            drawPileCount.setVisible(false);
        }
        else{
            drawpileImg.setImage(new Image("/view/cards/backsite.jpg"));
            drawPileCount.setText(toShowDraw.size()+"");
            drawpileImg.setVisible(true);
            drawPileCount.setVisible(true);
        }

        // Discard Pile
        ArrayList<Card> toShowDiscard = mainController.getPortRoyal().getActiveMatch().getDiscardPile();
        if(toShowDiscard.size() == 0){
            discardPile.setVisible(false);
        }
        else{
            discardPile.setImage(new Image(getCardUrl(toShowDiscard.get(0))));
            discardPile.setVisible(true);
        }
        //discardPileCount.setText(mainController.getPortRoyal().getActiveMatch().getDiscardPile().size()+"");
        discardPileCount.setText("");
    }

    public void refreshSelectedCard(){
        if(selectedCard != null){
            if(cardOnHarborPosition()!=(-1)){
                if((cardOnHarborPosition()-harborIndex) == 0) { selectedImg = harbor1; }
                else if((cardOnHarborPosition()-harborIndex) == 1) { selectedImg = harbor2; }
                else if((cardOnHarborPosition()-harborIndex) == 2) { selectedImg = harbor3; }
                else if((cardOnHarborPosition()-harborIndex) == 3) { selectedImg = harbor4; }
                else if((cardOnHarborPosition()-harborIndex) == 4) { selectedImg = harbor5; }
                else if((cardOnHarborPosition()-harborIndex) == 5) { selectedImg = harbor6; }
                else if((cardOnHarborPosition()-harborIndex) == 6) { selectedImg = harbor7; }
                else if((cardOnHarborPosition()-harborIndex) == 7) { selectedImg = harbor8; }
                else if((cardOnHarborPosition()-harborIndex) == 8) { selectedImg = harbor9; }
                else if((cardOnHarborPosition()-harborIndex) == 9) { selectedImg = harbor10; }
                else if((cardOnHarborPosition()-harborIndex) == 10) { selectedImg = harbor11; }
                else if((cardOnHarborPosition()-harborIndex) == 11) { selectedImg = harbor12; }
                else{ selectedImg = null; }
            }
            else if(cardOnHandPosition()!=(-1)){
                if((cardOnHandPosition()-handIndex) == -1) { selectedImg = hand1; }
                else if((cardOnHandPosition()-handIndex) == 0) { selectedImg = hand2; }
                else if((cardOnHandPosition()-handIndex) == 1) { selectedImg = hand3; }
                else if((cardOnHandPosition()-handIndex) == 2) { selectedImg = hand4; }
                else if((cardOnHandPosition()-handIndex) == 3) { selectedImg = hand5; }
                else if((cardOnHandPosition()-handIndex) == 4) { selectedImg = hand6; }
                else{ selectedImg = null; }
            }
            if(selectedImg != null) {
                String url = selectedImg.getImage().getUrl();
                selectedImg.setImage(new Image(url.substring(0, url.length()-4)+"_sel.jpg"));
            }
        }
    }

    public void refreshPlayerActionButtons(){
        Turn actTurn = mainController.getPortRoyal().getActiveMatch().getActiveTurn();
        Player actingPlayer = mainController.getPortRoyal().getActiveMatch().getActiveTurn().getActingPlayer();
        repelButton.setVisible((selectedCard != null)
                && (cardOnHarborPosition()!=(-1))
                && (selectedCard instanceof Ship)
                && (selectedCard == toShowHarbor.get(toShowHarbor.size()-1))
                && (mainController.getPortRoyal().getActiveMatch().getActiveTurn().getTurnHelp().getLastDrawnCard()==selectedCard)
                && (mainController.getPortRoyal().getActiveMatch().getActiveTurn().getIsPhaseDiscover())
                && (actingPlayer.getPlayerType()==PlayerType.HUMAN)
        );
        if(repelButton.isVisible()){
            if(selectedCard instanceof Ship
                    && ((Ship) selectedCard).getSwords() <= mainController.getPortRoyal().getActiveMatch().getActiveTurn().getActingPlayer().getSwords()
            ){ repelButton.setImage(new Image("/view/buttons/repelButton.png")); }
            else{ repelButton.setImage(new Image("/view/buttons/repelButtonDisable.png")); }
        }

        hireButton.setVisible((selectedCard != null)
                && (cardOnHarborPosition()!=(-1))
                && (mainController.getMoveController().getMoveControllerHelp().hasMoreMoves(actTurn.getActingPlayer(), actTurn))
                && (selectedCard instanceof Person)
                && (!mainController.getPortRoyal().getActiveMatch().getActiveTurn().getIsPhaseDiscover())
                && (actingPlayer.getPlayerType()==PlayerType.HUMAN)
        );
        if(hireButton.isVisible()){
            if(selectedCard instanceof Person && ((Person) selectedCard).canAfford(actingPlayer)){
                hireButton.setImage(new Image("/view/buttons/hireButton.png"));
            }
            else{
                hireButton.setImage(new Image("/view/buttons/hireButtonDisable.png"));
            }
        }

        fullfillButton.setVisible((selectedCard != null)
                && selectedCard instanceof Expedition
                && (actTurn.getActingPlayer() == actTurn.getActivePlayer())
                && (mainController.getPortRoyal().getActiveMatch().getExpeditionDisplay().contains(selectedCard))
                && (actingPlayer.getPlayerType()==PlayerType.HUMAN)
        );
        if(fullfillButton.isVisible()){
            if(selectedCard instanceof Expedition && ((Expedition) selectedCard).canFulfill(mainController.getPortRoyal().getActiveMatch().getActiveTurn().getActingPlayer())){
                fullfillButton.setImage(new Image("view/buttons/fullfillButton.png"));
            }
            else{
                fullfillButton.setImage(new Image("view/buttons/fullfillButtonDisable.png"));
            }
        }

        endDiscoverButton.setVisible( (actTurn.getTurnHelp().getLastDrawnCard()!=null)
                && (mainController.getPortRoyal().getActiveMatch().getActiveTurn().getIsPhaseDiscover())
                && (mainController.getPortRoyal().getActiveMatch().getActiveTurn().getTurnHelp().validHarborDisplay())
                && (actingPlayer.getPlayerType()==PlayerType.HUMAN)
        );

        endMoveButton.setVisible( ( (mainController.getPortRoyal().getActiveMatch().getActiveTurn().getIsPhaseDiscover() && !mainController.getPortRoyal().getActiveMatch().getActiveTurn().getTurnHelp().validHarborDisplay())
                || ( !mainController.getPortRoyal().getActiveMatch().getActiveTurn().getIsPhaseDiscover() ) && (actingPlayer.getPlayerType()==PlayerType.HUMAN) )
        );

        sellShipButton.setVisible((selectedCard != null)
                && (mainController.getMoveController().getMoveControllerHelp().hasMoreMoves(actTurn.getActingPlayer(), actTurn))
                && (selectedCard instanceof Ship)
                && (!mainController.getPortRoyal().getActiveMatch().getActiveTurn().getIsPhaseDiscover())
                && (actingPlayer.getPlayerType()==PlayerType.HUMAN)
        );

        playerHelpText.setVisible(actingPlayer.getPlayerType()==PlayerType.HUMAN);
        hintText.setVisible(false);
        bubble.setVisible(false);
    }

    public void refreshUndoRedoButtons(){
        if(mainController.getUndoRedoController().canUndo()){ undoButton.setImage(new Image("/view/buttons/undoButton.png")); }
        else{ undoButton.setImage(new Image("/view/buttons/undoButtonDisable.png")); }
        if(mainController.getUndoRedoController().canRedo()){ redoButton.setImage(new Image("/view/buttons/redoButton.png")); }
        else{ redoButton.setImage(new Image("/view/buttons/redoButtonDisable.png")); }
    }

    public void refreshPlayerHelpText(){
        Turn turn = mainController.getPortRoyal().getActiveMatch().getActiveTurn();
        if(turn.getIsPhaseDiscover() && (turn.getActivePlayer() == turn.getActingPlayer())) {
            if (turn.getTurnHelp().validHarborDisplay()) {
                if(turn.getTurnHelp().getLastDrawnCard()==null){
                    playerHelpText.setText("Du musst mindestens eine Karte aufdecken!");
                }
                else{
                    playerHelpText.setText("Decke weitere Karten auf oder beende die Entdecken-Phase!");
                }
            }
            else {
                playerHelpText.setText("Die Hafenauslage ist ungültig. Du musst deinen Zug beenden!");
            }
        } else{
            if(mainController.getMoveController().getMoveControllerHelp().hasMoreMoves(turn.getActingPlayer(), turn)){
                playerHelpText.setText("Du kannst Schiffe verkaufen, Personen anheuern und Expeditionen erfüllen!");
            }
            else{
                playerHelpText.setText("Du hast keine Züge mehr. Beende den Zug!");
            }
        }
    }

    public void refreshAIMoves(){
        Match match = mainController.getPortRoyal().getActiveMatch();
        Turn turn = match.getActiveTurn();
        Player actingPlayer = turn.getActingPlayer();

        if(!(actingPlayer.getPlayerType() == PlayerType.HUMAN) && !isPaused){
            //AI-Anweisungen
            if(matchEnd()){
                showEndScreen();
            }
            else{
                mainController.getAIController().makeMove(match, turn);

                animationBackground.setVisible(false);
                FadeTransition ft = new FadeTransition(Duration.millis(2005-(match.getAiSpeed()*200)), animationBackground);
                ft.setFromValue(0.0); ft.setToValue(1.0);
                ft.setCycleCount(2); ft.setAutoReverse(true);
                ft.onFinishedProperty().set(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        refresh();
                    }
                });
                ft.play();
            }
        }
    }

    /*
     * i dont know
     */
    @FXML
    void onDrawPileClick(){
        ArrayList<Turn> turnList = mainController.getPortRoyal().getActiveMatch().getTurnHistory();
        Turn actTurn = turnList.get(turnList.size()-1);
        if(actTurn.getIsPhaseDiscover() && actTurn.getTurnHelp().validHarborDisplay()){
            mainController.getTurnController().drawCardToHarborDisplay(actTurn);
            if(toShowHarbor.size()<13){ harborIndex = 0; }
            else{ harborIndex = (toShowHarbor.size()-7)-((toShowHarbor.size()-7)%6); }
            deselectCards();
        }
        this.refresh();
    }

    private String moveToString(Move move, boolean discover){
        if(discover){
            if(move.getCard() instanceof Expedition){
                return move.getPlayer().getName() + ": Expedition [" + move.getCard().toString() + "] gezogen.";
            }
            else if(move.getCard() instanceof Person){
                return move.getPlayer().getName() + ": Person [" + move.getCard().toString() + "] gezogen.";
            }
            else if(move.getCard() instanceof Ship){
                return move.getPlayer().getName() + ": Schiff [" + move.getCard().toString() + "] gezogen.";
            }
            else if(move.getCard() instanceof TaxRaise){
                return move.getPlayer().getName() + ": Steuererhöhung [" + move.getCard().toString() + "] gezogen.";
            }
            else if(move.getCard()==null){
                return move.getPlayer().getName() + ": Phase gewechselt.";
            }
            else{
                return move.getPlayer().getName() + ": Unbekannter Move.";
            }
        }
        else{
            if(move.getCard() instanceof Expedition){
                return move.getPlayer().getName() + ": Expedition [" + move.getCard().toString() + "] erfüllt.";
            }
            else if(move.getCard() instanceof Person){
                return move.getPlayer().getName() + ": Person [" + move.getCard().toString() + "] angeheuert.";
            }
            else if(move.getCard() instanceof Ship){
                return move.getPlayer().getName() + ": Schiff [" + move.getCard().toString() + "] gekauft.";
            }
            else if(move.getCard()==null){
                return move.getPlayer().getName() + ": Zug beendet.";
            }
            else{
                return move.getPlayer().getName() + ": Unbekannter Move.";
            }
        }
    }

    private String moveToTippString(Move move){
        boolean discover = mainController.getPortRoyal().getActiveMatch().getActiveTurn().getIsPhaseDiscover();
        if(discover){
            if(move.getCard() instanceof Expedition || move.getCard() instanceof Ship || move.getCard() instanceof Person || move.getCard() instanceof TaxRaise) {
                return "Tipp:\nKarte ziehen.";
            }
            else if(move.getCard()==null){
                if(mainController.getPortRoyal().getActiveMatch().getActiveTurn().getTurnHelp().validHarborDisplay()){
                    return "Tipp:\nPhase wechseln.";
                }
                return "Tipp:\nZug beenden.";
            }
            else{
                return null;
            }
        }
        else{
            if(move.getCard() instanceof Expedition){
                return "Tipp:\n"+move.getCard().toString() + " erfüllen.";
            }
            else if(move.getCard() instanceof Person){
                return "Tipp:\n"+ move.getCard().toString() + " anheuern.";
            }
            else if(move.getCard() instanceof Ship){
                return "Tipp:\n"+move.getCard().toString() + " kaufen.";
            }
            else if(move.getCard()==null){
                return "Tipp: Zug beenden.";
            }
            else{
                return null;
            }
        }
    }

    @FXML
    void onEndDiscoverClick(){
        Turn actTurn = mainController.getPortRoyal().getActiveMatch().getActiveTurn();
        actTurn.addMove(actTurn.getActivePlayer(), null);
        mainController.getTurnController().changePhase(mainController.getPortRoyal().getActiveMatch().getActiveTurn());
        this.refresh();
        actTurn.getMoves().get(actTurn.getMoves().size()-1).setMatchAfterMove(actTurn.getMatch());
        deselectCards();
    }

    @FXML
    void onEndMoveClick(){
        Card endMove = null;
        mainController.getPortRoyal().getActiveMatch().getActiveTurn().addMove(mainController.getPortRoyal().getActiveMatch().getActiveTurn().getActingPlayer(), endMove);

        Turn actTurn = mainController.getPortRoyal().getActiveMatch().getActiveTurn();
        Player actingPlayer = actTurn.getActingPlayer();
        Player activePlayer = actTurn.getActivePlayer();

        Player nextActing = actTurn.getTurnHelp().nextPlayer(actingPlayer);
        actTurn.setActingPlayer(nextActing);
        actingPlayer = actTurn.getActingPlayer();
        mainController.getTurnController().applyAdmiral(actTurn);

        if(actingPlayer == activePlayer || actTurn.getIsPhaseDiscover() || actTurn.getHarbourDisplay().isEmpty()){
           mainController.getTurnController().clearHarborDisplay(actTurn);
           mainController.getMatchController().addTurn(actTurn.getMatch());

           if(matchEnd()){
                //highscores ausrechnen
                for(Player player : actTurn.getMatch().getPlayerList()){
                    if(player.getPlayerType()==PlayerType.HUMAN){
                        Score score = new Score(player);
                        mainController.getScoreController().calculateScore(score);
                        mainController.getScoreController().addScoreToHighscores(score);
                    }
                }
                //endreihenfolge bestimmen
                ArrayList<Player> endResult = mainController.getPortRoyal().getActiveMatch().getPlayerList();
                Collections.sort(endResult);

                this.showEndScreen();

                if(mainController.getPortRoyal().getMatchList().contains(mainController.getPortRoyal().getActiveMatch())){
                    mainController.getPortRoyal().getMatchList().remove(mainController.getPortRoyal().getActiveMatch());
                }

                mainController.getIOController().save();
           }
        }
        deselectCards();

        this.refresh();

        actTurn.getMoves().get(actTurn.getMoves().size()-1).setMatchAfterMove(actTurn.getMatch());
    }

    @FXML
    void onSellShipClick(){
        Match match = mainController.getPortRoyal().getActiveMatch();
        Turn turn = match.getActiveTurn();
        Player actingPlayer = turn.getActingPlayer();
        Player activePlayer = turn.getActivePlayer();

        turn.addMove(actingPlayer, selectedCard);
        Move move = turn.getMoves().get(turn.getMoves().size()-1);
        mainController.getMoveController().sellShip(move, turn);

        deselectCards();
        this.refresh();
    }

    @FXML
    void onHirePersonClick(){
        if(!hireButton.getImage().getUrl().endsWith("/view/buttons/hireButtonDisable.png")){
            Match match = mainController.getPortRoyal().getActiveMatch();
            Turn turn = match.getActiveTurn();
            Player actingPlayer = turn.getActingPlayer();
            Player activePlayer = turn.getActivePlayer();

            turn.addMove(actingPlayer, selectedCard);
            Move move = turn.getMoves().get(turn.getMoves().size()-1);
            mainController.getMoveController().hireCrew(move, turn);

            deselectCards();
            this.refresh();
        }
    }

    @FXML
    void onFullfillExpeditionClick(){
        if(!fullfillButton.getImage().getUrl().endsWith("/view/buttons/fullfillButtonDisable.png")){
            Match match = mainController.getPortRoyal().getActiveMatch();
            Turn turn = match.getActiveTurn();
            Player actingPlayer = turn.getActingPlayer();
            Player activePlayer = turn.getActivePlayer();

            turn.addMove(actingPlayer, selectedCard);
            Move move = turn.getMoves().get(turn.getMoves().size()-1);
            mainController.getMoveController().fulfillExpedition(move, match);

            deselectCards();
            this.refresh();
        }
    }

    @FXML
    void onRepelShipClick(){
        if(!repelButton.getImage().getUrl().endsWith("/view/buttons/repelButtonDisable.png")){
            Match match = mainController.getPortRoyal().getActiveMatch();
            Turn turn = match.getActiveTurn();
            Player actingPlayer = turn.getActingPlayer();
            Player activePlayer = turn.getActivePlayer();

            turn.addMove(actingPlayer, selectedCard);
            Move move = turn.getMoves().get(turn.getMoves().size()-1);
            mainController.getMoveController().fightShip(move, turn);

            deselectCards();
            this.refresh();
        }
    }

    private boolean matchEnd(){
        Match match = mainController.getPortRoyal().getActiveMatch();
        boolean matchEndVariant = match.getMatchHelp().getAlternativeEnd();
        ArrayList<Player> playerList = mainController.getPortRoyal().getActiveMatch().getPlayerList();
        ArrayList<Turn> turnList = mainController.getPortRoyal().getActiveMatch().getTurnHistory();
        boolean victory = false;
        if(matchEndVariant){
            for (Player player : playerList) {
                if (player.getVictoryPoints() >= VP_TO_WIN) {
                    if (player.getDidExp()){
                        victory = true;
                    }
                }
            }
        }else{
            for(Player player : playerList) {
                if (player.getVictoryPoints() >= VP_TO_WIN) { victory = true; }
            }
        }
        boolean end = false;
        if(turnList.size() % playerList.size() == 1){
            end = true;
        }
        return (victory && end);
    }

    private void showEndScreen(){
        ArrayList<Player> playerList = mainController.getPortRoyal().getActiveMatch().getPlayerList();
        pauseBackground.setImage(new Image("/view/images/backgroundPause.png"));
        pauseBackground.setVisible(true);
        pauseBackground.setViewOrder(-1);
        pauseText.setText("Spiel beendet");
        pauseText.setViewOrder(-2);
        pauseText.setVisible(true);

        int showPlace1, showPlace2, showPlace3, showPlace4, showPlace5;
        showPlace1 = 1; showPlace2 = 2; showPlace3 = 3; showPlace4 = 4; showPlace5 = 5;
        try{
            if(playerList.get(1).compareTo(playerList.get(0)) == 0){ showPlace2 = showPlace1; }
            if(playerList.get(2).compareTo(playerList.get(1)) == 0){ showPlace3 = showPlace2; }
            if(playerList.get(3).compareTo(playerList.get(2)) == 0){ showPlace4 = showPlace3; }
            if(playerList.get(4).compareTo(playerList.get(3)) == 0){ showPlace5 = showPlace4; }
        } catch(Exception e){}
        try{ end1Text.setText(showPlace1+". " + playerList.get(0).getName()); end1Text.setVisible(true); end1Text.setViewOrder(-2);}
        catch(Exception e){ end1Text.setVisible(false); }
        try{ end2Text.setText(showPlace2+". " + playerList.get(1).getName()); end2Text.setVisible(true); end2Text.setViewOrder(-2);}
        catch(Exception e){ end2Text.setVisible(false); }
        try{ end3Text.setText(showPlace3+". " + playerList.get(2).getName()); end3Text.setVisible(true); end3Text.setViewOrder(-2);}
        catch(Exception e){ end3Text.setVisible(false); }
        try{ end4Text.setText(showPlace4+". " + playerList.get(3).getName()); end4Text.setVisible(true); end4Text.setViewOrder(-2);}
        catch(Exception e){ end4Text.setVisible(false); }
        try{ end5Text.setText(showPlace5+". " + playerList.get(4).getName()); end5Text.setVisible(true); end5Text.setViewOrder(-2);}
        catch(Exception e){ end5Text.setVisible(false); }
        try{ vp1.setText(playerList.get(0).getVictoryPoints() + "VP"); vp1.setVisible(true); vp1.setViewOrder(-2);}
        catch(Exception e){ vp1.setVisible(false); }
        try{ vp2.setText(playerList.get(1).getVictoryPoints() + "VP"); vp2.setVisible(true); vp2.setViewOrder(-2);}
        catch(Exception e){ vp2.setVisible(false); }
        try{ vp3.setText(playerList.get(2).getVictoryPoints() + "VP"); vp3.setVisible(true); vp3.setViewOrder(-2);}
        catch(Exception e){ vp3.setVisible(false); }
        try{ vp4.setText(playerList.get(3).getVictoryPoints() + "VP"); vp4.setVisible(true); vp4.setViewOrder(-2);}
        catch(Exception e){ vp4.setVisible(false); }
        try{ vp5.setText(playerList.get(4).getVictoryPoints() + "VP"); vp5.setVisible(true); vp5.setViewOrder(-2);}
        catch(Exception e){ vp5.setVisible(false); }
        try{ coin1.setText(playerList.get(0).getCoins() + "C"); coin1.setVisible(true); coin1.setViewOrder(-2); }
        catch(Exception e){ coin1.setVisible(false); }
        try{ coin2.setText(playerList.get(1).getCoins() + "C"); coin2.setVisible(true); coin2.setViewOrder(-2); }
        catch(Exception e){ coin2.setVisible(false); }
        try{ coin3.setText(playerList.get(2).getCoins() + "C"); coin3.setVisible(true); coin3.setViewOrder(-2); }
        catch(Exception e){ coin3.setVisible(false); }
        try{ coin4.setText(playerList.get(3).getCoins() + "C"); coin4.setVisible(true); coin4.setViewOrder(-2); }
        catch(Exception e){ coin4.setVisible(false); }
        try{ coin5.setText(playerList.get(4).getCoins() + "C"); coin5.setVisible(true); coin5.setViewOrder(-2); }
        catch(Exception e){ coin5.setVisible(false); }
        toStartButton.setVisible(true); toHighscoreButton.setVisible(true);
        toStartButton.setViewOrder(-2); toHighscoreButton.setViewOrder(-2);
    }

    @FXML
    void onInfoClick(){

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
    void onRulesClick(){
        InputStream inputStream = this.getClass().getResourceAsStream("rules.pdf");

        try {
            File file = File.createTempFile("rules", ".pdf");
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
    void onHintClick(){
        Match match = mainController.getPortRoyal().getActiveMatch();
        Player acting = match.getActiveTurn().getActingPlayer();
        String hint = this.moveToTippString(mainController.getAIController().showHint(match, acting));
        if(hint != null){
            hintText.setText(hint);
            bubble.setVisible(!bubble.isVisible());
            hintText.setVisible(!hintText.isVisible());
            if(hintText.isVisible()){
                for(Player player : mainController.getPortRoyal().getActiveMatch().getPlayerList()){
                    player.setEligibleForHighscore(false);
                }
            }
        }
        else{
            bubble.setVisible(false);
            hintText.setVisible(false);
        }
    }

    public void hideHint(){
        bubble.setVisible(false);
        hintText.setVisible(false);
    }

    @FXML
    void onToHighscore(){
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
    void onToStart(){
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
    void toStartEnter(){ toStartButton.setImage( new Image("/view/buttons/toStartButtonHover.png")); }

    @FXML
    void toStartExit(){ toStartButton.setImage( new Image("/view/buttons/toStartButton.png")); }

    @FXML
    void toHighscoreEnter(){ toHighscoreButton.setImage(new Image("/view/buttons/highscoresButtonHover.png")); }

    @FXML
    void toHighscoreExit(){ toHighscoreButton.setImage(new Image("/view/buttons/highscoresButton.png")); }

    /*
     * animations
     */
    public void actingPlayerAnimation(){
        animationName.setText(mainController.getPortRoyal().getActiveMatch().getActiveTurn().getActingPlayer().getName());
        animationBackground.setVisible(true);
        animationName.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(750), animationBackground);
        ft.setFromValue(0.0); ft.setToValue(1.0);
        ft.setCycleCount(2); ft.setAutoReverse(true);
        ft.onFinishedProperty().set(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                refresh();
            }
        });
        ft.play();
        FadeTransition ft2 = new FadeTransition(Duration.millis(750), animationName);
        ft2.setFromValue(0.0); ft2.setToValue(1.0);
        ft2.setCycleCount(2); ft2.setAutoReverse(true);
        ft2.play();
    }

    /*
     * undo/redo
     */
    @FXML
    void onUndoClick(){
        if(mainController.getUndoRedoController().canUndo()){
            mainController.getUndoRedoController().undo(mainController.getPortRoyal().getActiveMatch());
            for(Player player : mainController.getPortRoyal().getActiveMatch().getPlayerList()){
                player.setEligibleForHighscore(false);
            }
        }
        this.refresh();
    }

    @FXML
    void onRedoClick(){
        if(mainController.getUndoRedoController().canRedo()) {
            mainController.getUndoRedoController().redo(mainController.getPortRoyal().getActiveMatch());
            for(Player player : mainController.getPortRoyal().getActiveMatch().getPlayerList()){
                player.setEligibleForHighscore(false);
            }
        }
        this.refresh();
    }

    /*
     * window changes
     */

    //

    /*
     * Card select effects
     */

    public void deselectCards(){
        if(selectedCard!=null && selectedImg!=null){
            selectedImg = null; selectedCard = null;
            hideHint();
            refresh();
        }
    }

    public void selectCard(ImageView image, Card card){
        if(image == selectedImg){
                deselectCards();
        }
        else{
            deselectCards();
            selectedImg = image;
            selectedCard = card;
        }
        this.refresh();
    }

    public int cardOnHarborPosition(){
        for(int i=0; i<toShowHarbor.size(); i++){
            if(toShowHarbor.get(i) == selectedCard){
                return i;
            }
        }
        return -1;
    }

    public int cardOnHandPosition(){
        for(int i=0; i<toShowHand.size(); i++){
            if(toShowHand.get(i) == selectedCard){
                return i;
            }
        }
        return -1;
    }

    @FXML
    void onHarbor1Click(){  selectCard(harbor1, toShowHarbor.get(0+harborIndex)); }

    @FXML
    void onHarbor2Click(){ selectCard(harbor2, toShowHarbor.get(1+harborIndex)); }

    @FXML
    void onHarbor3Click(){ selectCard(harbor3, toShowHarbor.get(2+harborIndex)); }

    @FXML
    void onHarbor4Click(){ selectCard(harbor4, toShowHarbor.get(3+harborIndex)); }

    @FXML
    void onHarbor5Click(){ selectCard(harbor5, toShowHarbor.get(4+harborIndex)); }

    @FXML
    void onHarbor6Click(){ selectCard(harbor6, toShowHarbor.get(5+harborIndex)); }

    @FXML
    void onHarbor7Click(){ selectCard(harbor7, toShowHarbor.get(6+harborIndex)); }

    @FXML
    void onHarbor8Click(){ selectCard(harbor8, toShowHarbor.get(7+harborIndex)); }

    @FXML
    void onHarbor9Click(){ selectCard(harbor9, toShowHarbor.get(8+harborIndex)); }

    @FXML
    void onHarbor10Click(){ selectCard(harbor10, toShowHarbor.get(9+harborIndex)); }

    @FXML
    void onHarbor11Click(){ selectCard(harbor11, toShowHarbor.get(10+harborIndex)); }

    @FXML
    void onHarbor12Click(){ selectCard(harbor12, toShowHarbor.get(11+harborIndex)); }

    @FXML
    void onExp1Click(){ selectCard(exp1, toShowExp.get(0)); }

    @FXML
    void onExp2Click(){ selectCard(exp2, toShowExp.get(1)); }

    @FXML
    void onExp3Click(){ selectCard(exp3, toShowExp.get(2)); }

    @FXML
    void onExp4Click(){ selectCard(exp4, toShowExp.get(3)); }

    @FXML
    void onExp5Click(){ selectCard(exp5, toShowExp.get(4)); }

    @FXML
    void onExp6Click(){ selectCard(exp6, toShowExp.get(5)); }

    @FXML
    void onHand1Click(){
        if(!hand1.getImage().getUrl().endsWith("backsite.jpg")){
            selectCard(hand1, toShowHand.get(-1+handIndex));
        }
    }

    @FXML
    void onHand2Click(){ selectCard(hand2, toShowHand.get(0+handIndex)); }

    @FXML
    void onHand3Click(){ selectCard(hand3, toShowHand.get(1+handIndex)); }

    @FXML
    void onHand4Click(){ selectCard(hand4, toShowHand.get(2+handIndex)); }

    @FXML
    void onHand5Click(){ selectCard(hand5, toShowHand.get(3+handIndex)); }

    @FXML
    void onHand6Click(){ selectCard(hand6, toShowHand.get(4+handIndex)); }

    /*
     * avatar help
     */
    public Image getAvatar(int avatarID){
        try{
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
     * Harbor Effects
     */

    @FXML
    void onHarborDown(MouseEvent event){
        harborIndex += 6;
        this.refresh();
    }

    @FXML
    void onHarborUp(MouseEvent event){
        harborIndex -= 6;
        this.refresh();
    }

    @FXML
    void onHarbor1Enter(MouseEvent event){ this.hoverHarborEnter(harbor1); }

    @FXML
    void onHarbor1Exit(MouseEvent event){ this.hoverHarborExit(harbor1); }

    @FXML
    void onHarbor2Enter(MouseEvent event){ this.hoverHarborEnter(harbor2); }

    @FXML
    void onHarbor2Exit(MouseEvent event){ this.hoverHarborExit(harbor2); }

    @FXML
    void onHarbor3Enter(MouseEvent event){ this.hoverHarborEnter(harbor3); }

    @FXML
    void onHarbor3Exit(MouseEvent event){ this.hoverHarborExit(harbor3); }

    @FXML
    void onHarbor4Enter(MouseEvent event){ this.hoverHarborEnter(harbor4); }

    @FXML
    void onHarbor4Exit(MouseEvent event){ this.hoverHarborExit(harbor4);  }

    @FXML
    void onHarbor5Enter(MouseEvent event){ this.hoverHarborEnter(harbor5); }

    @FXML
    void onHarbor5Exit(MouseEvent event){ this.hoverHarborExit(harbor5); }

    @FXML
    void onHarbor6Enter(MouseEvent event){ this.hoverHarborEnter(harbor6); }

    @FXML
    void onHarbor6Exit(MouseEvent event){ this.hoverHarborExit(harbor6); }

    @FXML
    void onHarbor7Enter(MouseEvent event){ this.hoverHarborEnter(harbor7); }

    @FXML
    void onHarbor7Exit(MouseEvent event){ this.hoverHarborExit(harbor7); }

    @FXML
    void onHarbor8Enter(MouseEvent event){ this.hoverHarborEnter(harbor8); }

    @FXML
    void onHarbor8Exit(MouseEvent event){ this.hoverHarborExit(harbor8); }

    @FXML
    void onHarbor9Enter(MouseEvent event){ this.hoverHarborEnter(harbor9); }

    @FXML
    void onHarbor9Exit(MouseEvent event){ this.hoverHarborExit(harbor9); }

    @FXML
    void onHarbor10Enter(MouseEvent event){ this.hoverHarborEnter(harbor10); }

    @FXML
    void onHarbor10Exit(MouseEvent event){ this.hoverHarborExit(harbor10); }

    @FXML
    void onHarbor11Enter(MouseEvent event){ this.hoverHarborEnter(harbor11); }

    @FXML
    void onHarbor11Exit(MouseEvent event){ this.hoverHarborExit(harbor11); }

    @FXML
    void onHarbor12Enter(MouseEvent event){ this.hoverHarborEnter(harbor12); }

    @FXML
    void onHarbor12Exit(MouseEvent event){ this.hoverHarborExit(harbor12); }

    public void hoverHarborEnter(ImageView card){
        double zoomFactor = 1.45;
        card.setFitWidth(card.getFitWidth()*zoomFactor);
        card.setFitHeight(card.getFitHeight()*zoomFactor);
        card.setLayoutX(card.getLayoutX()-((card.getFitWidth()-(card.getFitWidth()/zoomFactor))/2));
        card.setLayoutY(card.getLayoutY()-((card.getFitHeight()-(card.getFitHeight()/zoomFactor))/2));
        card.setViewOrder(-1);
    }

    public void hoverHarborExit(ImageView card){
        double zoomFactor = 1.45;
        card.setLayoutX(card.getLayoutX()+((card.getFitWidth()-(card.getFitWidth()/zoomFactor))/2));
        card.setLayoutY(card.getLayoutY()+((card.getFitHeight()-(card.getFitHeight()/zoomFactor))/2));
        card.setFitWidth(card.getFitWidth()*(1/zoomFactor));
        card.setFitHeight(card.getFitHeight()*(1/zoomFactor));
        card.setViewOrder(0);
    }

    /*
     * pause effects
     */

    @FXML
    void onPauseClick(){
        pauseBackground.setImage(new Image("/view/images/backgroundPause.png"));
        pauseBackground.setVisible(true);
        pauseBackground.setViewOrder(-1);
        backToGameButton.setVisible(true);
        backToGameButton.setViewOrder(-1);
        saveButton.setVisible(true);
        saveButton.setViewOrder(-1);
        closeGameButton.setVisible(true);
        closeGameButton.setViewOrder(-1);
        pauseText.setVisible(true);
        pauseText.setViewOrder(-1);
        pauseText.setText("Pause");
        Match actMatch = mainController.getPortRoyal().getActiveMatch();
        if(mainController.getPortRoyal().getMatchList().contains(actMatch)){
            saveButton.setImage(new Image("/view/buttons/savedButton.png"));
        }
        else{
            saveButton.setImage(new Image("/view/buttons/saveButton.png"));
        }
        isPaused = true;
        refresh();
    }

    @FXML
    void onSave(){
        Match actMatch = mainController.getPortRoyal().getActiveMatch();
        if(mainController.getPortRoyal().getMatchList().contains(actMatch)){
            mainController.getPortRoyal().getMatchList().remove(actMatch);
        }
        else{
            actMatch.setTimestamp(new Date(System.currentTimeMillis()));
            mainController.getPortRoyal().getMatchList().add(actMatch);
        }
        this.onPauseClick();
    }

    @FXML
    void onCloseGame(){
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
    void onBackToGame(){
        this.onExitPause();
    }

    @FXML
    void onExitPause(){
        pauseBackground.setViewOrder(0);
        pauseBackground.setVisible(false);
        backToGameButton.setViewOrder(0);
        backToGameButton.setVisible(false);
        saveButton.setViewOrder(0);
        saveButton.setVisible(false);
        closeGameButton.setViewOrder(0);
        closeGameButton.setVisible(false);
        pauseText.setVisible(false);
        isPaused = false;
        refresh();
    }

    public void onEsc(){
        if(pauseBackground.isVisible()){ this.onExitPause(); }
        else{ this.onPauseClick(); }
    }

    @FXML
    void backToGameEnter(){  backToGameButton.setImage(new Image("/view/buttons/backToGameButtonHover.png")); }

    @FXML
    void backToGameExit(){  backToGameButton.setImage(new Image("/view/buttons/backToGameButton.png")); }

    @FXML
    void closeGameEnter(){  closeGameButton.setImage(new Image("/view/buttons/closeGameButtonHover.png")); }

    @FXML
    void closeGameExit(){ closeGameButton.setImage(new Image("/view/buttons/closeGameButton.png")); }

    @FXML
    void saveEnter(){
        if(saveButton.getImage().getUrl().endsWith("/view/buttons/saveButton.png")){
            saveButton.setImage(new Image("/view/buttons/saveButtonHover.png"));
        }
    }

    @FXML
    void saveExit(){
        if(saveButton.getImage().getUrl().endsWith("/view/buttons/saveButtonHover.png")){
            saveButton.setImage(new Image("/view/buttons/saveButton.png"));
        }
    }

    /*
     * expeditions hover effects
     */

    @FXML
    void exp1Enter(MouseEvent event){ hoverExpEnter(exp1); }

    @FXML
    void exp1Exit(MouseEvent event){ hoverExpExit(exp1); }

    @FXML
    void exp2Enter(MouseEvent event){ hoverExpEnter(exp2); }

    @FXML
    void exp2Exit(MouseEvent event){ hoverExpExit(exp2); }

    @FXML
    void exp3Enter(MouseEvent event){ hoverExpEnter(exp3); }

    @FXML
    void exp3Exit(MouseEvent event){ hoverExpExit(exp3); }

    @FXML
    void exp4Enter(MouseEvent event){ hoverExpEnter(exp4); }

    @FXML
    void exp4Exit(MouseEvent event){ hoverExpExit(exp4); }

    @FXML
    void exp5Enter(MouseEvent event){ hoverExpEnter(exp5); }

    @FXML
    void exp5Exit(MouseEvent event){ hoverExpExit(exp5); }

    @FXML
    void exp6Enter(MouseEvent event){ hoverExpEnter(exp6); }

    @FXML
    void exp6Exit(MouseEvent event){ hoverExpExit(exp6); }

    public void hoverExpEnter(ImageView card){
        double zoomFactor = 1.3;
        card.setFitWidth(card.getFitWidth()*zoomFactor);
        card.setFitHeight(card.getFitHeight()*zoomFactor);
        card.setLayoutX(card.getLayoutX()-((card.getFitWidth()-(card.getFitWidth()/zoomFactor))/2));
        card.setViewOrder(-1);
    }

    public void hoverExpExit(ImageView card){
        double zoomFactor = 1.3;
        card.setLayoutX(card.getLayoutX()+((card.getFitWidth()-(card.getFitWidth()/zoomFactor))/2));
        card.setFitWidth(card.getFitWidth()*(1/zoomFactor));
        card.setFitHeight(card.getFitHeight()*(1/zoomFactor));
        card.setViewOrder(0);
    }

    /*
     * hand effects
     */

    @FXML
    void onRightClick(MouseEvent event){
        handIndex++;
        this.refresh();
    }

    @FXML
    void onLeftClick(MouseEvent event){
        handIndex--;
        this.refresh();
    }

    @FXML
    void hand1Enter(MouseEvent event){ hoverHandEnter(hand1); }

    @FXML
    void hand1Exit(MouseEvent event){ hoverHandExit(hand1); }

    @FXML
    void hand2Enter(MouseEvent event){ hoverHandEnter(hand2); }

    @FXML
    void hand2Exit(MouseEvent event){ hoverHandExit(hand2); }

    @FXML
    void hand3Enter(MouseEvent event){ hoverHandEnter(hand3); }

    @FXML
    void hand3Exit(MouseEvent event){ hoverHandExit(hand3); }

    @FXML
    void hand4Enter(MouseEvent event){ hoverHandEnter(hand4); }

    @FXML
    void hand4Exit(MouseEvent event){ hoverHandExit(hand4); }

    @FXML
    void hand5Enter(MouseEvent event){ hoverHandEnter(hand5); }

    @FXML
    void hand5Exit(MouseEvent event){ hoverHandExit(hand5); }

    @FXML
    void hand6Enter(MouseEvent event){ hoverHandEnter(hand6); }

    @FXML
    void hand6Exit(MouseEvent event){ hoverHandExit(hand6); }

    public void hoverHandEnter(ImageView card){
        if(!card.getImage().getUrl().endsWith("view/cards/backsite.jpg")){
            double zoomFactor = 1.45;
            card.setFitWidth(card.getFitWidth()*zoomFactor);
            card.setFitHeight(card.getFitHeight()*zoomFactor);
            card.setLayoutY(card.getLayoutY()-(card.getFitHeight()-(card.getFitHeight()/zoomFactor)));
            card.setLayoutX(card.getLayoutX()-((card.getFitWidth()-(card.getFitWidth()/zoomFactor))/2));
            card.setViewOrder(-1);
        }
    }

    public void hoverHandExit(ImageView card){
        if(!card.getImage().getUrl().endsWith("view/cards/backsite.jpg")){
            double zoomFactor = 1.45;
            card.setLayoutX(card.getLayoutX()+((card.getFitWidth()-(card.getFitWidth()/zoomFactor))/2));
            card.setLayoutY(card.getLayoutY()+(card.getFitHeight()-(card.getFitHeight()/zoomFactor)));
            card.setFitWidth(card.getFitWidth()*(1/zoomFactor));
            card.setFitHeight(card.getFitHeight()*(1/zoomFactor));
            card.setViewOrder(0);
        }
    }

    /*
     * PlayerViews effects
     */

    @FXML
    void player1ImgEnter(){ showPlayerView(1); }

    @FXML
    void player1ImgExit(){ hidePlayerView(1); }

    @FXML
    void player2ImgEnter(){ showPlayerView(2); }

    @FXML
    void player2ImgExit(){ hidePlayerView(2); }

    @FXML
    void player3ImgEnter(){ showPlayerView(3); }

    @FXML
    void player3ImgExit(){ hidePlayerView(3); }

    @FXML
    void player4ImgEnter(){ showPlayerView(4); }

    @FXML
    void player4ImgExit(){ hidePlayerView(4); }

    @FXML
    void player5ImgEnter(){ showPlayerView(5); }

    @FXML
    void player5ImgExit(){ hidePlayerView(5); }

    public void showPlayerView(int playerInt){
        Player player = mainController.getPortRoyal().getActiveMatch().getPlayerList().get(playerInt-1);
        bigPlayerView.setVisible(true); bigPlayerView.setViewOrder(-4); bigCoin.setVisible(true); bigCoin.setViewOrder(-4);
        bigVictory.setVisible(true); bigVictory.setViewOrder(-4); bigSaber.setVisible(true); bigSaber.setViewOrder(-4);
        bigJack.setVisible(true); bigJack.setViewOrder(-4); bigCaptain.setVisible(true); bigCaptain.setViewOrder(-4);
        bigPriest.setVisible(true); bigPriest.setViewOrder(-4); bigSettler.setVisible(true); bigSettler.setViewOrder(-4);
        bigMade.setVisible(true); bigMade.setViewOrder(-4); bigGov.setVisible(true); bigGov.setViewOrder(-4);
        bigJester.setVisible(true); bigJester.setViewOrder(-4); bigAdmiral.setVisible(true); bigAdmiral.setViewOrder(-4);
        bigCoinText.setVisible(true); bigCoinText.setViewOrder(-4);
        bigVictoryText.setVisible(true); bigVictoryText.setViewOrder(-4); bigSaberText.setVisible(true); bigSaberText.setViewOrder(-4);
        bigJackText.setVisible(true); bigJackText.setViewOrder(-4); bigCaptainText.setVisible(true); bigCaptainText.setViewOrder(-4);
        bigPriestText.setVisible(true); bigPriestText.setViewOrder(-4); bigSettlerText.setVisible(true); bigSettlerText.setViewOrder(-4);
        bigMadeText.setVisible(true); bigMadeText.setViewOrder(-4); bigGovText.setVisible(true); bigGovText.setViewOrder(-4);
        bigJesterText.setVisible(true); bigJesterText.setViewOrder(-4); bigAdmiralText.setVisible(true); bigAdmiralText.setViewOrder(-4);
        if(playerInt > 1){  bigPlayerView.setLayoutX(556); bigPlayerView.setLayoutY(17+((playerInt-2)*150)); }
        else{ bigPlayerView.setLayoutX(26); bigPlayerView.setLayoutY(370); }
        double viewX = bigPlayerView.getLayoutX();
        double viewY = bigPlayerView.getLayoutY();
        bigCoin.setLayoutX(         viewX +  96);   bigCoin.setLayoutY(         viewY +  10);
        bigVictory.setLayoutX(      viewX + 200);   bigVictory.setLayoutY(      viewY +  10);
        bigSaber.setLayoutX(        viewX + 304);   bigSaber.setLayoutY(        viewY +  10);
        bigJack.setLayoutX(         viewX +  44);   bigJack.setLayoutY(         viewY +  90);
        bigCaptain.setLayoutX(      viewX + 148);   bigCaptain.setLayoutY(      viewY +  90);
        bigPriest.setLayoutX(       viewX + 252);   bigPriest.setLayoutY(       viewY +  90);
        bigSettler.setLayoutX(      viewX + 356);   bigSettler.setLayoutY(      viewY +  90);
        bigMade.setLayoutX(         viewX +  44);   bigMade.setLayoutY(         viewY + 170);
        bigGov.setLayoutX(          viewX + 148);   bigGov.setLayoutY(          viewY + 170);
        bigJester.setLayoutX(       viewX + 252);   bigJester.setLayoutY(       viewY + 170);
        bigAdmiral.setLayoutX(      viewX + 356);   bigAdmiral.setLayoutY(      viewY + 170);
        bigCoinText.setLayoutX(         viewX +  96);   bigCoinText.setLayoutY(         viewY +  85);
        bigVictoryText.setLayoutX(      viewX + 200);   bigVictoryText.setLayoutY(      viewY +  85);
        bigSaberText.setLayoutX(        viewX + 304);   bigSaberText.setLayoutY(        viewY +  85);
        bigJackText.setLayoutX(         viewX +  44);   bigJackText.setLayoutY(         viewY + 165);
        bigCaptainText.setLayoutX(      viewX + 148);   bigCaptainText.setLayoutY(      viewY + 165);
        bigPriestText.setLayoutX(       viewX + 252);   bigPriestText.setLayoutY(       viewY + 165);
        bigSettlerText.setLayoutX(      viewX + 356);   bigSettlerText.setLayoutY(      viewY + 165);
        bigMadeText.setLayoutX(         viewX +  44);   bigMadeText.setLayoutY(         viewY + 245);
        bigGovText.setLayoutX(          viewX + 148);   bigGovText.setLayoutY(          viewY + 245);
        bigJesterText.setLayoutX(       viewX + 252);   bigJesterText.setLayoutY(       viewY + 245);
        bigAdmiralText.setLayoutX(      viewX + 356);   bigAdmiralText.setLayoutY(      viewY + 245);
        bigCoinText.setText(player.getCoins()+"");
        bigVictoryText.setText(player.getVictoryPoints()+"");
        bigSaberText.setText(player.getSwords()+"");
        bigJackText.setText(player.getPlayerHelp().getCardCounts()[0]+"");
        bigCaptainText.setText(player.getPlayerHelp().getCardCounts()[1]+"");
        bigPriestText.setText(player.getPlayerHelp().getCardCounts()[2]+"");
        bigSettlerText.setText(player.getPlayerHelp().getCardCounts()[3]+"");
        bigMadeText.setText(player.getPlayerHelp().getCardCounts()[4]+"");
        bigGovText.setText(player.getPlayerHelp().getCardCounts()[5]+"");
        bigJesterText.setText(player.getPlayerHelp().getCardCounts()[6]+"");
        bigAdmiralText.setText(player.getPlayerHelp().getCardCounts()[7]+"");
    }

    public void hidePlayerView(int player){
        bigPlayerView.setVisible(false);
        bigPlayerView.setViewOrder(0);
        bigCoin.setVisible(false);
        bigVictory.setVisible(false);
        bigSaber.setVisible(false);
        bigJack.setVisible(false);
        bigCaptain.setVisible(false);
        bigPriest.setVisible(false);
        bigSettler.setVisible(false);
        bigMade.setVisible(false);
        bigGov.setVisible(false);
        bigJester.setVisible(false);
        bigAdmiral.setVisible(false);
        bigCoinText.setVisible(false);
        bigVictoryText.setVisible(false);
        bigSaberText.setVisible(false);
        bigJackText.setVisible(false);
        bigCaptainText.setVisible(false);
        bigPriestText.setVisible(false);
        bigSettlerText.setVisible(false);
        bigMadeText.setVisible(false);
        bigGovText.setVisible(false);
        bigJesterText.setVisible(false);
        bigAdmiralText.setVisible(false);
    }

    /*
     * drawpile effects
     */

    @FXML
    void drawpileImgEnter(MouseEvent event){
        if(drawpileImg.isVisible() && mainController.getPortRoyal().getActiveMatch().getActiveTurn().getIsPhaseDiscover()){
            Image image = new Image("view/cards/backsiteHover.jpg");
            drawpileImg.setImage(image);
        }
    }

    @FXML
    void drawpileImgExit(MouseEvent event){
        Image image = new Image("view/cards/backsite.jpg");
        drawpileImg.setImage(image);
    }

    public String getCardUrl(Card card){
        String url = "/view/cards/";
        if(card instanceof TaxRaise){
            url += "tax/";
            if(((TaxRaise) card).getMaxSwords())    { url += "max.jpg"; }
            else                                    { url += "min.jpg"; }
        }
        else if(card instanceof Ship){
            url += "ship/";
            int coins = ((Ship) card).getCoins();
            int swords = ((Ship) card).getSwords();
            String color = "";
            if      (((Ship) card).getColor().equals(Color.BLACK))  { color = "black"; }
            else if (((Ship) card).getColor().equals(Color.BLUE))   { color = "blue"; }
            else if (((Ship) card).getColor().equals(Color.GREEN))  { color = "green"; }
            else if (((Ship) card).getColor().equals(Color.RED))    { color = "red"; }
            else                                                    { color = "yellow"; }
            url += color + "_" + coins + "_" + swords + ".jpg";
        }
        else if(card instanceof Expedition){
            url += "expedition/";
            ArrayList<Ability> artifcats = ((Expedition) card).getArtifacts();
            for(int i=0; i<artifcats.size(); i++){
                if(artifcats.get(i).equals(Ability.CAPTAIN))        { url += "cap"; }
                else if(artifcats.get(i).equals(Ability.SETTLER))   { url += "set"; }
                else if(artifcats.get(i).equals(Ability.PRIEST))    { url += "pri"; }
            }
            url += ".jpg";
        }
        else if(card instanceof Person){
            url += "person/";
            if(((Person) card).getAbility().equals(Ability.PRIEST)){ url += "priest/priest.jpg"; }
            else if(((Person) card).getAbility().equals(Ability.CAPTAIN)){ url += "captain/cap.jpg"; }
            else if(((Person) card).getAbility().equals(Ability.GOVERNOR)){ url += "governor/gov.jpg"; }
            else if(((Person) card).getAbility().equals(Ability.SETTLER)){ url += "settler/settler.jpg"; }
            else if(((Person) card).getAbility().equals(Ability.JOKER)){ url += "joker/joker.jpg"; }
            else{
                if(((Person) card).getAbility().equals(Ability.TRADER)){
                    if      (((Person) card).getColor().equals(Color.BLACK)){ url += "trader/black"; }
                    else if (((Person) card).getColor().equals(Color.BLUE)){ url += "trader/blue"; }
                    else if (((Person) card).getColor().equals(Color.GREEN)){ url += "trader/green"; }
                    else if (((Person) card).getColor().equals(Color.RED)){ url += "trader/red"; }
                    else if (((Person) card).getColor().equals(Color.YELLOW)){ url += "trader/yellow"; }
                    url += "_" + ((Person) card).getVictoryPoints() + ".jpg";
                }
                else{
                    int coins = (-1)*card.getCoins();
                    int points = ((Person) card).getVictoryPoints();
                    if(((Person) card).getAbility().equals(Ability.JESTER)){ url += "jester/" + coins + "_" + points + ".jpg"; }
                    else if(((Person) card).getAbility().equals(Ability.MADEMOISELLE)){ url += "mademoiselle/" + coins + "_" + points + ".jpg"; }
                    else if(((Person) card).getAbility().equals(Ability.PIRATE)){ url += "pirate/" + coins + "_" + points + ".jpg"; }
                    else if(((Person) card).getAbility().equals(Ability.SAILOR)){ url += "sailor/" + coins + "_" + points + ".jpg"; }
                    else if(((Person) card).getAbility().equals(Ability.ADMIRAL)){ url += "admiral/" + coins + "_" + points + ".jpg"; }
                }
            }
        }
        else{ return null; };
        return url;
    }

    /*
     * last hover effects lmao
     */

    @FXML
    void onPauseButtonEnter(){
        pauseButton.setImage(new Image("/view/buttons/pauseButtonHover.png"));
    }

    @FXML
    void onPauseButtonExit(){
        pauseButton.setImage(new Image("/view/buttons/pauseButton.png"));
    }

    @FXML
    void onRulesEnter(){
        rulesButton.setImage(new Image("/view/buttons/rulesButtonHover.png"));
    }

    @FXML
    void onRulesExit(){
        rulesButton.setImage(new Image("/view/buttons/rulesButton.png"));
    }

    @FXML
    void onInfoEnter(){
        infoButton.setImage(new Image("/view/buttons/infoButtonHover.png"));
    }

    @FXML
    void onInfoExit(){
        infoButton.setImage(new Image("/view/buttons/infoButton.png"));
    }

    @FXML
    void onHintEnter(){
        hintButton.setImage(new Image("/view/buttons/tipButtonHover.png"));
    }

    @FXML
    void onHintExit(){
        hintButton.setImage(new Image("/view/buttons/tipButton.png"));
    }

    @FXML
    void onUndoEnter(){
        if(!undoButton.getImage().getUrl().endsWith("/view/buttons/undoButtonDisable.png")){
            undoButton.setImage(new Image("/view/buttons/undoButtonHover.png"));
        }
    }

    @FXML
    void onUndoExit(){
        if(!undoButton.getImage().getUrl().endsWith("/view/buttons/undoButtonDisable.png")){
            undoButton.setImage(new Image("/view/buttons/undoButton.png"));
        }
    }

    @FXML
    void onRedoEnter(){
        if(!redoButton.getImage().getUrl().endsWith("/view/buttons/redoButtonDisable.png")){
            redoButton.setImage(new Image("/view/buttons/redoButtonHover.png"));
        }
    }

    @FXML
    void onRedoExit(){
        if(!redoButton.getImage().getUrl().endsWith("/view/buttons/redoButtonDisable.png")){
            redoButton.setImage(new Image("/view/buttons/redoButton.png"));
        }
    }

    @FXML
    void endDiscovEnter(){
        endDiscoverButton.setImage(new Image("/view/buttons/endDiscoverButtonHover.png"));
    }

    @FXML
    void endDiscovExit(){
        endDiscoverButton.setImage(new Image("/view/buttons/endDiscoverButton.png"));
    }

    @FXML
    void endMoveEnter(){
        endMoveButton.setImage(new Image("/view/buttons/endMoveButtonHover.png"));
    }

    @FXML
    void endMoveExit(){
        endMoveButton.setImage(new Image("/view/buttons/endMoveButton.png"));
    }

    @FXML
    void repelEnter(){
        if(!repelButton.getImage().getUrl().endsWith("/view/buttons/repelButtonDisable.png")){
            repelButton.setImage(new Image("/view/buttons/repelButtonHover.png"));
        }
    }

    @FXML
    void repelExit(){
        if(!repelButton.getImage().getUrl().endsWith("/view/buttons/repelButtonDisable.png")){
            repelButton.setImage(new Image("/view/buttons/repelButton.png"));
        }
    }

    @FXML
    void sellEnter(){
        sellShipButton.setImage(new Image("/view/buttons/sellShipButtonHover.png"));
    }

    @FXML
    void sellExit(){
        sellShipButton.setImage(new Image("/view/buttons/sellShipButton.png"));
    }

    @FXML
    void hireEnter(){
        if(!hireButton.getImage().getUrl().endsWith("/view/buttons/hireButtonDisable.png")){
            hireButton.setImage(new Image("/view/buttons/hireButtonHover.png"));
        }
    }

    @FXML
    void hireExit(){
        if(!hireButton.getImage().getUrl().endsWith("/view/buttons/hireButtonDisable.png")){
            hireButton.setImage(new Image("/view/buttons/hireButton.png"));
        }
    }

    @FXML
    void fullfillEnter(){
        if(!fullfillButton.getImage().getUrl().endsWith("/view/buttons/fullfillButtonDisable.png")){
            fullfillButton.setImage(new Image(("/view/buttons/fullfillButtonHover.png")));
        }
    }

    @FXML
    void fullfillExit(){
        if(!fullfillButton.getImage().getUrl().endsWith("/view/buttons/fullfillButtonDisable.png")){
            fullfillButton.setImage(new Image(("/view/buttons/fullfillButton.png")));
        }
    }

}
