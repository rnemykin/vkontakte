package com.abudko.reseller.huuto.query.rules;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.abudko.reseller.huuto.query.currency.CurrencyService;

@Component
public abstract class AbstractPriceRules {
    
    @Autowired
    private CurrencyService currencyService;
    
    private DecimalFormat priceFormat = new DecimalFormat("#");

    public String calculateNew(String price) {
        if (StringUtils.hasLength(price)) {
            BigDecimal newPrice = new BigDecimal(price).add(getAddInEuro());
            BigDecimal priceInRub = newPrice.multiply(currencyService.getRate());
            
            return priceFormat.format(priceInRub);
        }
        return price;
    }
    
    protected abstract BigDecimal getAddInEuro();
}
