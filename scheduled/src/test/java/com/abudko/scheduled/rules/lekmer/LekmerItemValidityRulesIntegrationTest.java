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
    public void testIdNotLekmer() {
        assertTrue(rules.isValid("45264242"));;
    }
    
    @Test
    public void testLekmerIdValid() {
        assertTrue(rules.isValid("LE521274-6541"));
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
        assertTrue(rules.isValid("LEHW 13103 VIOLET"));
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