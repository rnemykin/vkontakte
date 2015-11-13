package com.abudko.reseller.huuto.query.rules;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.abudko.reseller.huuto.query.currency.CurrencyService;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;

@Component
public abstract class AbstractPriceRules {

    @Autowired
    private CurrencyService currencyService;

    private DecimalFormat priceFormat = new DecimalFormat("#");

    public String calculateNew(String price, BigDecimal add) {
        if (StringUtils.hasLength(price)) {
        	BigDecimal priceBefore = new BigDecimal(price);
            BigDecimal newPrice = priceBefore.add(getAddInEuro()).add(add);
            BigDecimal priceInRub = newPrice.multiply(currencyService.getRate());
            
            priceInRub = round(priceInRub);

            return priceFormat.format(priceInRub);
        }
        return price;
    }
    
    BigDecimal round(BigDecimal priceInRub) {
    	if (priceInRub.intValue() < 1000) {
    		return priceInRub; 
    	}
    	
    	BigDecimal bigDecimal10 = new BigDecimal("10");
    	BigDecimal bigDecimal250 = new BigDecimal("250");
    	BigDecimal bigDecimal500 = new BigDecimal("500");
    	BigDecimal bigDecimal1000 = new BigDecimal("1000");
    	
    	BigDecimal before = priceInRub;
    	priceInRub = priceInRub.divide(bigDecimal1000);
    	priceInRub = priceInRub.setScale(0, BigDecimal.ROUND_HALF_EVEN);
    	priceInRub = priceInRub.multiply(bigDecimal1000);
    	
    	BigDecimal diff = priceInRub.subtract(before);
    	
    	if (diff.intValue() > bigDecimal250.intValue()) {
    		priceInRub = priceInRub.subtract(bigDecimal500);
    	}
    	else if (diff.intValue() < -bigDecimal250.intValue()) {
    		priceInRub = priceInRub.add(bigDecimal500);
    	}
    	
    	return priceInRub.subtract(bigDecimal10); 
    }

    public String calculateNew(ItemResponse response) {
        if (StringUtils.hasLength(response.getCurrentPrice())) {
            return calculateNew(response.getCurrentPrice(), new BigDecimal(10));
        }

        return calculateNew(response.getPrice(), new BigDecimal(0));
    }

    protected abstract BigDecimal getAddInEuro();
}
