package com.abudko.scheduled.rules.reima;

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
public class ReimaItemValidityRulesIntegrationTest {
    
    @Autowired
    @Qualifier("reimaItemValidityRules")
    private ItemValidityRules rules;
    
    @Test
    public void testIdWithoutPrefix() {
        assertTrue(rules.isValid("45264242"));;
    }
    
    @Test
    public void testLekmerIdValid() {
        assertTrue(rules.isValid("LE521370-2710"));
    }
    
    @Test
    public void testIdReimaInvalid() {
        assertFalse(rules.isValid("REsds"));
    }
    
    @Test
    public void testIdReimaValid() {
    	assertTrue(rules.isValid("RE510144-4620"));
    }
    
    @Test
    public void testIdNull() {
        assertTrue(rules.isValid(null));;
    }
}