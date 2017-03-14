package com.abudko.scheduled.rules.xxl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abudko.scheduled.rules.ItemValidityRules;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/test-app-config.xml" })
public abstract class XxlItemValidityRulesIntegrationTest {

	@Autowired
    @Qualifier("xxlItemValidityRules")
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
    public void testIdReimaValid() {
        assertTrue(rules.isValid("REsds"));
    }
    
    @Test
    public void testIdXXLInvalid() {
    	assertFalse(rules.isValid("XXyrsddr"));
    }
    
    @Test
    public void testIdXXLValid() {
    	assertTrue(rules.isValid("XX1098856_4"));
    }
    
    @Test
    public void testIdNull() {
        assertTrue(rules.isValid(null));;
    }
}
