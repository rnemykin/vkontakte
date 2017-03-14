package com.abudko.scheduled.rules.stadium;

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
public abstract class StadiumItemValidityRulesIntegrationTest {
    
    @Autowired
    @Qualifier("stadiumItemValidityRules")
    private ItemValidityRules rules;
    
    @Test
    public void testIdWithoutPrefix() {
        assertTrue(rules.isValid("45264242"));;
    }
    
    @Test
    public void testIdReimaValid() {
        assertTrue(rules.isValid("RE45264242"));;
    }
    
    @Test
    public void testStadiumIdValid() {
        assertTrue(rules.isValid("ST219873101"));
    }
    
    @Test
    public void testStadiumIdInvalid() {
    	assertFalse(rules.isValid("STpihspis"));
    }
    
    @Test
    public void testIdXXLValid() {
    	assertTrue(rules.isValid("XXsdfdfsd"));
    }
    
    @Test
    public void testLekmerIdValid() {
        assertTrue(rules.isValid("LE520164A-4710"));
    }
    
    @Test
    public void testIdNull() {
        assertTrue(rules.isValid(null));;
    }
}