package com.abudko.reseller.huuto.query.currency;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/test-app-config.xml" })
public class CurrencyServiceTest {
    
    @Autowired
    private CurrencyService currencyService;

    @Test
    public void testConvert() {
        assertTrue(currencyService.getRate().intValue() > 0);
    }
}
