package com.abudko.reseller.huuto.query.enumeration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class BrandTest {

    @Test
    public void testGetBrandFromReima() {
        String testStr = "Bla bla Reima bla bla bla";

        assertEquals(Brand.REIMA, Brand.getBrandFrom(testStr));
    }

    @Test
    public void testGetBrandFromReimatec() {
        String testStr = "Reimatec bla bla bla";

        assertEquals(Brand.REIMATEC, Brand.getBrandFrom(testStr));
    }

    @Test
    public void testGetBrandFromLowerCase() {
        String testStr = "ticket bla bla bla";

        assertEquals(Brand.TICKET, Brand.getBrandFrom(testStr));
    }
    
    @Test
    public void testGetBrandFromStart() {
        String testStr = "Legowear, Toppahousut, Justin 613, Liila";
        
        assertEquals(Brand.LEGO, Brand.getBrandFrom(testStr));
    }

    @Test
    public void testGetBrandFromUpperCase() {
        String testStr = "bla blaLENNE bla";

        assertEquals(Brand.LENNE, Brand.getBrandFrom(testStr));
    }

    @Test
    public void testGetBrandFromNegative() {
        String testStr = "bla bla bla";

        assertNull(Brand.getBrandFrom(testStr));
    }

    @Test
    public void testGetBrandFromNoBrand() {
        String testStr = "NO_BRAND";

        assertNull(Brand.getBrandFrom(testStr));
    }

    @Test
    public void testGetBrandFromBrandLast() {
        String testStr = "Uusi välikausihaalari koko 86 Reima";

        assertEquals(Brand.REIMA, Brand.getBrandFrom(testStr));
    }

    @Test
    public void testGetBrandFromCommaEnd() {
        String testStr = "Uusi välikausihaalari Reima, koko 86";

        assertEquals(Brand.REIMA, Brand.getBrandFrom(testStr));
    }

    @Test
    public void testGetBrandFromDotEnd() {
        String testStr = "Uusi välikausihaalari Reima. koko 86";

        assertEquals(Brand.REIMA, Brand.getBrandFrom(testStr));
    }
}
