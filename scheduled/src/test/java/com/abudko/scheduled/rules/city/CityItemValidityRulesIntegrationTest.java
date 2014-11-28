package com.abudko.scheduled.rules.city;

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
public class CityItemValidityRulesIntegrationTest {

    @Autowired
    @Qualifier("cityItemValidityRules")
    private ItemValidityRules rules;

    @Test
    public void testIdNotCity() {
        assertTrue(rules.isValid("45264242"));
    }
    
    @Test
    public void testIdCityValid() {
        assertTrue(rules.isValid("CI7398746"));
    }
}
