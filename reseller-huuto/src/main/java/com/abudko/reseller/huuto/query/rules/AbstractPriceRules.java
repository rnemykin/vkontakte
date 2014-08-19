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
            BigDecimal newPrice = new BigDecimal(price).add(getAddInEuro()).add(add);
            BigDecimal priceInRub = newPrice.multiply(currencyService.getRate());

            return priceFormat.format(priceInRub);
        }
        return price;
    }

    public String calculateNew(ItemResponse response) {
        if (StringUtils.hasLength(response.getCurrentPrice())) {
            return calculateNew(response.getCurrentPrice(), new BigDecimal(10));
        }

        return calculateNew(response.getPrice(), new BigDecimal(0));
    }

    protected abstract BigDecimal getAddInEuro();
}
