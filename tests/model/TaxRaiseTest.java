package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This class is used to test the methods of the TaxRaise class.
 */
public class TaxRaiseTest {
    private TaxRaise taxRaiseCardF, taxRaiseCardT;


    /**
     * setup method to initialize objects
     * @throws Exception if something goes wrong
     */
    @Before
    public void setup() throws Exception
    {
        taxRaiseCardF = new TaxRaise(false);
        taxRaiseCardT = new TaxRaise(true);
    }


    /**
     * Test TaxRaise constructor
     */
    @Test
    public void testTaxRaiseCardConstructor()
    {
        TaxRaise taxRaisecard1 = new TaxRaise(true);
        TaxRaise taxRaisecard2 = new TaxRaise(false);

        assertNotNull("Object created", taxRaisecard1);
        assertNotNull("Object created", taxRaisecard2);

        assertEquals(true, taxRaisecard1.getMaxSwords());
        assertEquals(false, taxRaisecard2.getMaxSwords());

    }

    /**
     * Test TaxRaise clone
     */
    @Test
    public void testTaxRaiseCone() {

        TaxRaise taxRaiseCloned = taxRaiseCardF.clone();
        Assert.assertEquals(taxRaiseCloned.getMaxSwords(), taxRaiseCardF.getMaxSwords());
        Assert.assertEquals(taxRaiseCloned.getIsFaceUp(), taxRaiseCardF.getIsFaceUp());
    }

    /**
     * Test Set and Get MaxSwards
     */
    @Test
    public void testSetGetMaxSwards() {
        TaxRaise taxRaise1 = new TaxRaise(true);
        taxRaise1.setMaxSwords(false);
        Assert.assertEquals(false, taxRaise1.getMaxSwords());
    }

    /**
     * Test for toString method of TaxRaise
     */
    @Test
    public void testToString() {
        Assert.assertEquals("TAX RAISE: " + "Max Sword", taxRaiseCardT.toString());
        Assert.assertEquals("TAX RAISE: " + "Min VP",taxRaiseCardF.toString());
    }
}
