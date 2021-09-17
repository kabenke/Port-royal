package controller;

import java.lang.reflect.Array;
import java.util.ArrayList;

import model.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class is used to test the methods of the ParseController.
 */
public class ParseControllerTest {

    private MainController mainController;
    private ParseController parseController;

    private ArrayList<Card> cardDeck;
    private ArrayList<Ability> capcap;
    private ArrayList<Ability> capcapset;
    private ArrayList<Ability> cappriset;
    private ArrayList<Ability> pripri;
    private ArrayList<Ability> pripriset;
    private ArrayList<Ability> setset;
    private PortRoyal portRoyal;

    private Card captain, settler, priest, admiral1, admiral2, admiral3, governor, jester1, jester2, jester3, joker,
            mademoiselle1, mademoiselle2, pirate1, pirate2, pirate3, sailor1, sailor2, sailor3, blackTrader,
            blueTrader1, blueTrader2, greenTrader, redTrader, yellowTrader1, yellowTrader2, capCap, capCapSet,
            capPriSet, priPri, priPriSet, setSet, max, min, black1, black2, black3, black4, black5, blue1, blue2, blue3,
            blue4, blue5, blue6, green1, green2, green3, green4, green5, green6, red1, red2, red3, red4, red5, yellow1,
            yellow2, yellow3, yellow4, yellow5, yellow6;

    /**
     * Creates a new test environment before each test method
     * @throws Exception Possible Exceptions with setUp
     */
    @Before
    public void setUp() throws Exception {

        this.mainController = new MainController();
        this.portRoyal = new PortRoyal();
        this.mainController.setPortRoyal(portRoyal);
        this.parseController = new ParseController(mainController);
        this.mainController.setParseController(parseController);


        /*Person Cards*/
        this.captain = new Person(-4,0, Ability.CAPTAIN, 1, Color.UNDEFINED);
        this.settler = new Person(-4,0,Ability.SETTLER, 1, Color.UNDEFINED);
        this.priest = new Person(-4,0,Ability.PRIEST,1,Color.UNDEFINED);
        this.admiral1 = new Person(-5, 0, Ability.ADMIRAL, 1, Color.UNDEFINED);
        this.admiral2 = new Person(-7, 0, Ability.ADMIRAL, 2, Color.UNDEFINED);
        this.admiral3 = new Person(-9, 0, Ability.ADMIRAL, 3, Color.UNDEFINED);
        this.governor = new Person(-8, 0, Ability.GOVERNOR, 0, Color.UNDEFINED);
        this.jester1 = new Person(-5, 0, Ability.JESTER, 1, Color.UNDEFINED);
        this.jester2 = new Person(-7, 0, Ability.JESTER, 2, Color.UNDEFINED);
        this.jester3 = new Person(-9, 0, Ability.JESTER, 3, Color.UNDEFINED);
        this.joker = new Person(-6, 0, Ability.JOKER, 1, Color.UNDEFINED);
        this.mademoiselle1 = new Person(-7, 0, Ability.MADEMOISELLE, 2, Color.UNDEFINED);
        this.mademoiselle2 = new Person(-9, 0, Ability.MADEMOISELLE, 3, Color.UNDEFINED);
        this.pirate1 = new Person(-5, 2, Ability.PIRATE,1,Color.UNDEFINED);
        this.pirate2 = new Person(-7, 2, Ability.PIRATE,2,Color.UNDEFINED);
        this.pirate3 = new Person(-9, 2, Ability.PIRATE,3,Color.UNDEFINED);
        this.sailor1 = new Person(-3, 1, Ability.SAILOR,1,Color.UNDEFINED);
        this.sailor2 = new Person(-5, 1, Ability.SAILOR,2,Color.UNDEFINED);
        this.sailor3 = new Person(-7, 1, Ability.SAILOR,3,Color.UNDEFINED);
        this.blackTrader = new Person(-3, 0, Ability.TRADER, 1, Color.BLACK);
        this.blueTrader1 = new Person(-3, 0, Ability.TRADER, 1, Color.BLUE);
        this.blueTrader2 = new Person(-5, 0, Ability.TRADER, 2, Color.BLUE);
        this.greenTrader = new Person(-3, 0, Ability.TRADER, 1, Color.GREEN);
        this.redTrader = new Person(-3, 0, Ability.TRADER, 1, Color.RED);
        this.yellowTrader1 = new Person(-3, 0, Ability.TRADER, 1, Color.YELLOW);
        this.yellowTrader2 = new Person(-5, 0, Ability.TRADER, 2, Color.YELLOW);


        /*Expedition Cards*/
        this.capcap = new ArrayList<>();
        capcap.add(Ability.CAPTAIN);
        capcap.add(Ability.CAPTAIN);
        this.capCap = new Expedition(2, capcap,4);

        this.capcapset = new ArrayList<>();
        capcapset.add(Ability.CAPTAIN);
        capcapset.add(Ability.CAPTAIN);
        capcapset.add(Ability.SETTLER);
        this.capCapSet = new Expedition(3, capcapset,6);

        this.cappriset = new ArrayList<>();
        cappriset.add(Ability.CAPTAIN);
        cappriset.add(Ability.PRIEST);
        cappriset.add(Ability.SETTLER);
        this.capPriSet = new Expedition(3, cappriset,5);

        this.pripri = new ArrayList<>();
        pripri.add(Ability.PRIEST);
        pripri.add(Ability.PRIEST);
        this.priPri = new Expedition(2, pripri,4);

        this.pripriset = new ArrayList<>();
        pripriset.add(Ability.PRIEST);
        pripriset.add(Ability.PRIEST);
        pripriset.add(Ability.SETTLER);
        this.priPriSet = new Expedition(3, pripriset,6);

        this.setset = new ArrayList<>();
        setset.add(Ability.SETTLER);
        setset.add(Ability.SETTLER);
        this.setSet = new Expedition(2, setset, 4);


        /*TaxRaise Cards*/
        this.max = new TaxRaise(true);
        this.min = new TaxRaise(false);


        /*Ship Cards*/
        this.black1 = new Ship(1,2,Color.BLACK);
        this.black2 = new Ship(2,4,Color.BLACK);
        this.black3 = new Ship(3,7,Color.BLACK);
        this.black4 = new Ship(3,30,Color.BLACK);
        this.black5 = new Ship(4,30,Color.BLACK);

        this.blue1 = new Ship(1,1,Color.BLUE);
        this.blue2 = new Ship(2,1,Color.BLUE);
        this.blue3 = new Ship(2,2,Color.BLUE);
        this.blue4 = new Ship(3,2,Color.BLUE);
        this.blue5 = new Ship(3,5,Color.BLUE);
        this.blue6 = new Ship(4,5,Color.BLUE);

        this.green1 = new Ship(1, 1,Color.GREEN);
        this.green2 = new Ship(2, 1,Color.GREEN);
        this.green3 = new Ship(2, 3,Color.GREEN);
        this.green4 = new Ship(3, 3,Color.GREEN);
        this.green5 = new Ship(3, 5,Color.GREEN);
        this.green6 = new Ship(4, 5,Color.GREEN);

        this.red1 = new Ship(1, 1, Color.RED);
        this.red2 = new Ship(2, 3, Color.RED);
        this.red3 = new Ship(3, 6, Color.RED);
        this.red4 = new Ship(3, 30, Color.RED);
        this.red5 = new Ship(4, 30, Color.RED);

        this.yellow1 = new Ship(1, 1, Color.YELLOW);
        this.yellow2 = new Ship(2, 1, Color.YELLOW);
        this.yellow3 = new Ship(2, 2, Color.YELLOW);
        this.yellow4 = new Ship(3, 2, Color.YELLOW);
        this.yellow5 = new Ship(3, 4, Color.YELLOW);
        this.yellow6 = new Ship(4, 4, Color.YELLOW);


        /*Initialize Card Deck*/
        this.cardDeck = new ArrayList<>();
        cardDeck.add(blue5);
        cardDeck.add(red1);
        cardDeck.add(sailor2);
        cardDeck.add(capCapSet);
        cardDeck.add(yellow3);
        cardDeck.add(black2);
        cardDeck.add(joker);
        cardDeck.add(green1);
        cardDeck.add(red5);
        cardDeck.add(yellow2);
        cardDeck.add(pirate2);
        cardDeck.add(black1);
        cardDeck.add(jester2);
        cardDeck.add(captain);
        cardDeck.add(red3);
        cardDeck.add(sailor1);
        cardDeck.add(jester2);
        cardDeck.add(yellowTrader2);
        cardDeck.add(admiral2);
        cardDeck.add(admiral2);
        cardDeck.add(jester1);
        cardDeck.add(priPri);
        cardDeck.add(max);
        cardDeck.add(green2);
        cardDeck.add(min);
        cardDeck.add(governor);
        cardDeck.add(sailor1);
        cardDeck.add(redTrader);
        cardDeck.add(redTrader);
        cardDeck.add(green5);
        cardDeck.add(blue6);
        cardDeck.add(settler);
        cardDeck.add(red1);
        cardDeck.add(priest);
        cardDeck.add(admiral2);
        cardDeck.add(yellow5);
        cardDeck.add(blue1);
        cardDeck.add(yellow5);
        cardDeck.add(mademoiselle2);
        cardDeck.add(sailor2);
        cardDeck.add(joker);
        cardDeck.add(blue1);
        cardDeck.add(black4);
        cardDeck.add(blue3);
        cardDeck.add(blue1);
        cardDeck.add(blueTrader1);
        cardDeck.add(red4);
        cardDeck.add(black3);
        cardDeck.add(red2);
        cardDeck.add(pirate3);
        cardDeck.add(sailor1);
        cardDeck.add(blackTrader);
        cardDeck.add(green1);
        cardDeck.add(red3);
        cardDeck.add(settler);
        cardDeck.add(joker);
        cardDeck.add(captain);
        cardDeck.add(yellow4);
        cardDeck.add(green4);
        cardDeck.add(green6);
        cardDeck.add(governor);
        cardDeck.add(yellow1);
        cardDeck.add(captain);
        cardDeck.add(green3);
        cardDeck.add(admiral3);
        cardDeck.add(settler);
        cardDeck.add(settler);
        cardDeck.add(black1);
        cardDeck.add(jester2);
        cardDeck.add(governor);
        cardDeck.add(sailor1);
        cardDeck.add(admiral1);
        cardDeck.add(pirate2);
        cardDeck.add(captain);
        cardDeck.add(sailor1);
        cardDeck.add(black5);
        cardDeck.add(blue4);
        cardDeck.add(sailor3);
        cardDeck.add(jester3);
        cardDeck.add(black2);
        cardDeck.add(blue5);
        cardDeck.add(captain);
        cardDeck.add(black2);
        cardDeck.add(red2);
        cardDeck.add(green5);
        cardDeck.add(priest);
        cardDeck.add(black1);
        cardDeck.add(capCap);
        cardDeck.add(blueTrader2);
        cardDeck.add(yellow1);
        cardDeck.add(sailor1);
        cardDeck.add(black3);
        cardDeck.add(yellow1);
        cardDeck.add(mademoiselle1);
        cardDeck.add(green3);
        cardDeck.add(sailor1);
        cardDeck.add(max);
        cardDeck.add(priest);
        cardDeck.add(greenTrader);
        cardDeck.add(blackTrader);
        cardDeck.add(yellow3);
        cardDeck.add(priest);
        cardDeck.add(settler);
        cardDeck.add(min);
        cardDeck.add(yellow6);
        cardDeck.add(red2);
        cardDeck.add(greenTrader);
        cardDeck.add(blue2);
        cardDeck.add(blue3);
        cardDeck.add(priest);
        cardDeck.add(mademoiselle1);
        cardDeck.add(mademoiselle2);
        cardDeck.add(setSet);
        cardDeck.add(red1);
        cardDeck.add(green1);
        cardDeck.add(yellowTrader1);
        cardDeck.add(priPriSet);
        cardDeck.add(admiral3);
        cardDeck.add(governor);

    }

    /**
     * Help method compare
     * returns true if the content of the cards is identical, false otherwise
     * @return true if the content of the cards is identical, false otherwise
     */
    private boolean compare(Card card1, Card card2) {
        if(card1.getClass() != card2.getClass() || card1.getCoins() != card2.getCoins()) {
            return false;
        }

        if(card1 instanceof Ship) {
            return (card2 instanceof Ship && ((Ship) card1).getColor().equals(((Ship) card2).getColor())
                    && ((Ship) card1).getSwords() == ((Ship) card2).getSwords());
        }
        else if(card1 instanceof Person) {
            return (card2 instanceof Person && ((Person) card1).getAbility().equals(((Person) card2).getAbility())
                    && ((Person) card1).getColor().equals(((Person) card2).getColor())
                    && ((Person) card1).getSwords() == ((Person) card2).getSwords()
                    && ((Person) card1).getVictoryPoints() == ((Person) card2).getVictoryPoints());
        }
        else if(card1 instanceof TaxRaise) {
            return (card2 instanceof TaxRaise && ((TaxRaise) card1).getMaxSwords() == ((TaxRaise) card2).getMaxSwords());
        }
        else {
            if(card2 instanceof Expedition &&((Expedition)card1).getArtifacts().size() == ((Expedition)card2).getArtifacts().size()) {
                Expedition exp1 = (Expedition) card1;
                Expedition exp2 = (Expedition) card2;
                for(int i = 0; i < exp1.getArtifacts().size(); i++) {
                    if(!(exp1.getArtifacts().get(i).equals(exp2.getArtifacts().get(i)))) {
                        return false;
                    }
                }
            } else {
                return false;
            }

            return (((Expedition) card1).getVictoryPoints() == ((Expedition) card2).getVictoryPoints());
        }
    }

    /**
     * This method is used to test the help method Compare
     */
    @Test
    public void testCompare(){
        Assert.assertTrue(compare(joker, joker));
        Assert.assertFalse(compare(joker, black2));
        Assert.assertTrue(compare(sailor2,sailor2));
    }

    /**
     * This method is used to test the parseCsvToPile method with a csv file with 120 cards
     */
    @Test
    public void testParseCsvToPile120Cards(){
        ArrayList<Card> csv = parseController.parseCsvToPile();
        for(int i = 0; i < csv.size(); i++) {
            Assert.assertTrue(compare(cardDeck.get(i),csv.get(i)));
        }
    }

    /**
     * This method is used to test the parseCsvToPile method with a csv file with 119 cards
     */
    @Test
    public void testParseCsvToPile119Cards(){
        ArrayList<Card> csv = parseController.parseCsvToPile();
        Assert.assertEquals(119, csv.size());
    }

    /**
     * This method tests if the parseCsvToPile method actually returns null if the filePath is invalid
     */
    @Test
    public void testException(){
        parseController.setFilePath("");
        Assert.assertNull(parseController.parseCsvToPile());
    }

    /**
     * This method tests if the parseCsvToPile method actually returns null if the Pile has no 120 or 119 cards in it
     */
    @Test
    public void testParseCsvToPileLessThan119Cards() {
        parseController.setFilePath("118cards.CSV");
        Assert.assertNull(parseController.parseCsvToPile());
    }

    /**
     * This method tests the getMainController method
     */
    @Test
    public void testGetMainController(){
        parseController.setMainController(mainController);
        Assert.assertEquals(mainController, parseController.getMainController());
    }

    /**
     * This method tests the getFilePath method
     */
    @Test
    public void getFilePath() {
        parseController.setFilePath("a");
        Assert.assertEquals("a",parseController.getFilePath());
    }
}
