package com.abudko.reseller.huuto.query.rules;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.apache.http.client.HttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.abudko.reseller.huuto.query.currency.CurrencyService;
import com.abudko.reseller.huuto.query.currency.RateResponse;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;

@RunWith(MockitoJUnitRunner.class)
public class HuutoPriceRulesTest {

    @Mock
    private CurrencyService currencyService;
    
    @Mock
    private RestTemplate restTemplate;
    
    @Mock
    private HttpClient httpClient;

    @InjectMocks
    private AbstractPriceRules rules = new HuutoPriceRules();
    
    @Before
    public void setup() {
        when(currencyService.getRate()).thenReturn(new BigDecimal("1"));
    }

    @Test
    public void testRoundUp() {
        assertEquals("16", rules.calculateNew("5.51", new BigDecimal(0)));
    }

    @Test
    public void testRoundDown() {
        assertEquals("15", rules.calculateNew("5.49", new BigDecimal(0)));
    }

    @Test
    public void testZero() {
        assertEquals("15", rules.calculateNew("5.0", new BigDecimal(0)));
    }
    
    @Test
    public void testAddMore() {
        assertEquals("25", rules.calculateNew("5.0", new BigDecimal(10)));
    }
    
    @Test
    public void testItemResponseNoCurrentPrice() {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setPrice("10");
        
        assertEquals("20", rules.calculateNew(itemResponse));
    }
    
    @Test
    public void testItemResponseCurrentPrice() {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setCurrentPrice("10");
        
        assertEquals("30", rules.calculateNew(itemResponse));
    }
    
    @Test
    public void testCurrencyServiceUnavailable() {
        CurrencyService currencyServiceReal = new CurrencyService();
        ReflectionTestUtils.setField(currencyServiceReal, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(currencyServiceReal, "client", httpClient);
        when(restTemplate.getForObject(Mockito.any(String.class), eq(RateResponse.class))).thenThrow(new RuntimeException());
        ReflectionTestUtils.setField(rules, "currencyService", currencyServiceReal);
        
        assertEquals("780", rules.calculateNew("5.0", new BigDecimal(0)));
    }
}
