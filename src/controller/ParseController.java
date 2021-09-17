package controller;

import model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * This Controller is used to parse a csv file to a pile.
 */
public class ParseController {

    /**
     * The Main controller
     */
    private MainController mainController;

    /**
     * path of the csv file
     */
    private String filePath;

    /**
     * Constructor for the main controller
     * @param mainController the mainController
     */
    public ParseController(MainController mainController){
        this.mainController = mainController;
        this.filePath = "tunier_stack1.txt";
    }

    /**
     * Ability to convert a csv-file to a pile
     * @return return the pile from the csv-file
     */
    public ArrayList<Card> parseCsvToPile() {
        ArrayList<Card> pile = new ArrayList<Card>();
        BufferedReader reader;
        try{
            //reader = new BufferedReader(new FileReader(filePath));
            InputStreamReader inputStreamReader = new InputStreamReader(getClass().getResourceAsStream(filePath));
            reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line!=null){
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                String token  = tokenizer.nextToken();
                String token2 = tokenizer.nextToken();
                String token3 = tokenizer.nextToken();
                csvCheckTokens(token, token2, token3, pile);
                line = reader.readLine();
            }
            reader.close();
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
        int wantedPileSize = 119;
        if(pile.size() == wantedPileSize){
            return pile;
        }
        else{
            return null;
        }
    }

    /**
     * Help method for parseCsvToPile
     * @param token Token 1
     * @param token2 Token 2
     * @param token3 Token 3
     * @param pile Pile
     */
    private void csvCheckTokens(String token, String token2, String token3, ArrayList<Card> pile){
        if(token.equals("Expedition")){
            this.csvParseExpedition(token2, token3, pile);
        }
        else if(token.equals("Tax Increase")){
            if(token2.equals("Max Sword")){ pile.add(new TaxRaise(true)); }
            else if(token2.equals("Min VP")){ pile.add(new TaxRaise(false)); }
        }
        else if(token.equals("Admiral")){
            int points = Integer.parseInt(token2.substring(0,1));
            int coins  = -1 * Integer.parseInt(token3);
            pile.add(new Person(coins, 0, Ability.ADMIRAL, points, Color.UNDEFINED));
        }
        else if(token.equals("Captain")){
            int points = Integer.parseInt(token2.substring(0,1));
            int coins  = -1 * Integer.parseInt(token3);
            pile.add(new Person(coins, 0, Ability.CAPTAIN, points, Color.UNDEFINED));
        }
        else if(token.equals("Governor")){
            int points = Integer.parseInt(token2.substring(0,1));
            int coins  = -1 * Integer.parseInt(token3);
            pile.add(new Person(coins, 0, Ability.GOVERNOR, points, Color.UNDEFINED));
        }
        else if(token.equals("Jack of All Trades")){
            int points = Integer.parseInt(token2.substring(0,1));
            int coins  = -1 * Integer.parseInt(token3);
            pile.add(new Person(coins, 0, Ability.JOKER, points, Color.UNDEFINED));
        }
        else if(token.equals("Jester")){
            int points = Integer.parseInt(token2.substring(0,1));
            int coins  = -1 * Integer.parseInt(token3);
            pile.add(new Person(coins, 0, Ability.JESTER, points, Color.UNDEFINED));
        }
        else if(token.equals("Mademoiselle")){
            int points = Integer.parseInt(token2.substring(0,1));
            int coins  = -1 * Integer.parseInt(token3);
            pile.add(new Person(coins, 0, Ability.MADEMOISELLE, points, Color.UNDEFINED));
        }
        else if(token.equals("Pirate")){
            int points = Integer.parseInt(token2.substring(0,1));
            int coins  = -1 * Integer.parseInt(token3);
            pile.add(new Person(coins, 2, Ability.PIRATE, points, Color.UNDEFINED));
        }
        else{
            csvCheckTokensHelp(token, token2, token3, pile);
        }
    }

    /**
     * Help method for parseCsvToPile
     * @param token Token 1
     * @param token2 Token 2
     * @param token3 Token 3
     * @param pile Pile
     */
    private void csvCheckTokensHelp(String token, String token2, String token3, ArrayList<Card> pile) {
        if(token.equals("Priest")){
            int points = Integer.parseInt(token2.substring(0,1));
            int coins  = -1 * Integer.parseInt(token3);
            pile.add(new Person(coins, 0, Ability.PRIEST, points, Color.UNDEFINED));
        }
        else if(token.equals("Sailor")){
            int points = Integer.parseInt(token2.substring(0,1));
            int coins  = -1 * Integer.parseInt(token3);
            pile.add(new Person(coins, 1, Ability.SAILOR, points, Color.UNDEFINED));
        }
        else if(token.equals("Settler")){
            int points = Integer.parseInt(token2.substring(0,1));
            int coins  = -1 * Integer.parseInt(token3);
            pile.add(new Person(coins, 0, Ability.SETTLER, points, Color.UNDEFINED));
        }
        else if(token.equals("Trader")){
            csvParseTrader(token2, token3, pile);
        }
        else if(token.equals("Flute")){
            int swords = Integer.parseInt(token2.substring(0,1));
            int coins  = Integer.parseInt(token3);
            pile.add(new Ship(coins, swords, Color.BLUE));
        }
        else if(token.equals("Frigate")){
            int swords;
            try{ swords = Integer.parseInt(token2.substring(0,1)); }
            catch(NumberFormatException e){ swords = 30; }
            int coins  = Integer.parseInt(token3);
            pile.add(new Ship(coins, swords, Color.RED));
        }
        else if(token.equals("Galleon")){
            int swords;
            try{ swords = Integer.parseInt(token2.substring(0,1)); }
            catch(NumberFormatException e){ swords = 30; }
            int coins  = Integer.parseInt(token3);
            pile.add(new Ship(coins, swords, Color.BLACK));
        }
        else if(token.equals("Pinnace")){
            int swords = Integer.parseInt(token2.substring(0,1));
            int coins  = Integer.parseInt(token3);
            pile.add(new Ship(coins, swords, Color.YELLOW));
        }
        else if(token.equals("Skiff")){
            int swords = Integer.parseInt(token2.substring(0,1));
            int coins  = Integer.parseInt(token3);
            pile.add(new Ship(coins, swords, Color.GREEN));
        }
    }

    /**
     * Help method for parseCsvToPile
     * @param token2 Token 2
     * @param token3 Token 3
     * @param pile Pile
     */
    private void csvParseExpedition(String token2, String token3, ArrayList<Card> pile){
        int coins = Integer.parseInt(token3);
        int points = 0;
        ArrayList<Ability> abs = new ArrayList<>();
        if(token2.equals("House Pair")){
            points = 4;
            abs.add(Ability.SETTLER); abs.add(Ability.SETTLER);
        }
        else if(token2.equals("Cross Pair")){
            points = 4;
            abs.add(Ability.PRIEST); abs.add(Ability.PRIEST);
        }
        else if(token2.equals("Anchor Pair")){
            points = 4;
            abs.add(Ability.CAPTAIN); abs.add(Ability.CAPTAIN);
        }
        else if(token2.equals("Cross Pair + House")){
            points = 6;
            abs.add(Ability.PRIEST); abs.add(Ability.PRIEST); abs.add(Ability.SETTLER);
        }
        else if(token2.equals("Anchor Pair + House")){
            points = 6;
            abs.add(Ability.CAPTAIN); abs.add(Ability.CAPTAIN); abs.add(Ability.SETTLER);
        }
        pile.add(new Expedition(coins, abs, points));
    }

    /**
     * Help method for parseCsvToPile
     * @param token2 Token 2
     * @param token3 Token 3
     * @param pile Pile
     */
    private void csvParseTrader(String token2, String token3, ArrayList<Card> pile){
        int points = Integer.parseInt(token2.substring(token2.length()-4, token2.length()-3));
        int coins  = -1 * Integer.parseInt(token3);
        Color color = Color.UNDEFINED;
        if(token2.startsWith("Black")){color = Color.BLACK; }
        else if(token2.startsWith("Blue")){color = Color.BLUE; }
        else if(token2.startsWith("Green")){color = Color.GREEN; }
        else if(token2.startsWith("Red")){color = Color.RED; }
        else if(token2.startsWith("Yellow")){color = Color.YELLOW; }
        pile.add(new Person(coins, 0, Ability.TRADER, points, color));
    }

    /**
     * get-Method mainController
     * @return mainController
     */
    public MainController getMainController() {return mainController;}

    /**
     * set-Method mainController
     * @param mainController the mainController
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * get-Method filePath
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * set-Method filePath
     * @param filePath the given filePath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
