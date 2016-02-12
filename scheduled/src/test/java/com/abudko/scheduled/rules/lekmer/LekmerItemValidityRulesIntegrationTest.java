package com.abudko.scheduled.rules.lekmer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abudko.scheduled.rules.ItemValidityRules;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/test-app-config.xml" })
public class LekmerItemValidityRulesIntegrationTest {
    
    @Autowired
    @Qualifier("lekmerItemValidityRules")
    private ItemValidityRules rules;
    
    @Test
    public void testIdWithoutPrefix() {
        assertTrue(rules.isValid("45264242"));;
    }
    
    @Test
    public void testStadiumIdValid() {
        assertTrue(rules.isValid("ST219873101"));
    }
    
    @Test
    public void testIdReimaValid() {
        assertTrue(rules.isValid("RE45264242"));;
    }
    
    @Test
    public void testLekmerIdValid() {
        assertTrue(rules.isValid("LE521370-2710"));
    }
    
    @Test
    public void testIdXXLValid() {
    	assertTrue(rules.isValid("XXsdfdfsd"));
    }
    
    @Test
    public void testLekmerIdShort() {
        assertTrue(rules.isValid("LE106"));
    }
    
    @Test
    public void testLekmerIdValidLongId() {
        assertTrue(rules.isValid("LE3171214311"));
    }
    
    @Test
    public void testLekmerIdWithSpacesValid() {
        assertTrue(rules.isValid("LE13103264 DARK B"));
    }
    
    @Test
    public void testLekmerURlKeywordValid() {
    	assertTrue(rules.isValid("LElindberg-overall-vermont-green-navy"));
    }
    
    @Test
    public void testLekmerURlKeywordValidUFT8() {
    	assertTrue(rules.isValid("LEgeggamoja-tuulenpitävä-haalari-vauvan-cerise"));
    }
    
    @Test
    public void testLekmerURlKeywordInvalid() {
    	assertFalse(rules.isValid("LElindberg-overall-vermont-green-nav"));
    }
    
    @Test
    public void testIdLekmerInvalid() {
        assertFalse(rules.isValid("LEsds"));
    }
    
    @Test
    public void testIdNull() {
        assertTrue(rules.isValid(null));;
    }
}