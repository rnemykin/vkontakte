package com.abudko.reseller.huuto.query.rules;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.abudko.reseller.huuto.query.currency.CurrencyService;

@Component
public class PriceRules {
    
    private static final BigDecimal ADD_IN_EURO = new BigDecimal(5);
    
    @Autowired
    private CurrencyService currencyService;
    
    private DecimalFormat priceFormat = new DecimalFormat("#");

    public String calculateNew(String price) {
        if (StringUtils.hasLength(price)) {
            BigDecimal newPrice = new BigDecimal(price).add(ADD_IN_EURO);
            BigDecimal priceInRub = newPrice.multiply(currencyService.getRate());
            
            return priceFormat.format(priceInRub);
        }
        return price;
    }
}
