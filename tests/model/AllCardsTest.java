package model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

/**
 * Test class to test the methods of AllCards.
 */
public class AllCardsTest {

    private AllCards allCards ;

    /**
     * setup method to initialize objects
     * @throws Exception if something goes wrong
     */
    @Before
    public void setUp() throws Exception{
        allCards=new AllCards();

    }

    /**
     * test for getTaxRaiseCards and setTaxRaiseCards of Allcards
     */
    @Test
    public void testGetSetTaxRaiseCards(){
        ArrayList<TaxRaise> taxRaises=new ArrayList<>();
        allCards.setTaxRaiseCards(taxRaises);
        Assert.assertEquals(allCards.getTaxRaiseCards(),taxRaises);

    }

    /**
     * test getExpeditionCards and setExpeditionCards of Allcards
     */
    @Test
    public void testGetSetExpeditionCards(){
        ArrayList<Expedition> expeditions =new ArrayList<>();
        allCards.setExpeditionCards(expeditions);
        Assert.assertEquals(allCards.getExpeditionCards(),expeditions);
    }

    /**
     * test for getPersonCards and setPersonCards of Allcards
     */
    @Test
    public void testGetSetPersonCards (){
        ArrayList<Person> personArrayList=new ArrayList<>();
        allCards.setPersonCards(personArrayList);
        Assert.assertEquals(allCards.getPersonCards(),personArrayList);
    }

    /**
     * test for test getShipCards and setShipCards of Allcards
     */
    @Test
    public void testGetSetShipCards(){
        ArrayList<Ship> ships=new ArrayList<>();
        allCards.setShipCards(ships);
        Assert.assertEquals(allCards.getShipCards(),ships);
    }

}
