package com.abudko.reseller.huuto.query.rules;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class AbstractPriceRulesTest {

    private AbstractPriceRules rules = new LekmerPriceRules();
    
    @Test
    public void testRound() {
    	assertEquals(1490, rules.round(new BigDecimal("1301")).intValue());
    	assertEquals(990, rules.round(new BigDecimal("1201")).intValue());
    	assertEquals(2990, rules.round(new BigDecimal("2777")).intValue());
    	assertEquals(2490, rules.round(new BigDecimal("2737")).intValue());
    	assertEquals(12490, rules.round(new BigDecimal("12599")).intValue());
    }
}
