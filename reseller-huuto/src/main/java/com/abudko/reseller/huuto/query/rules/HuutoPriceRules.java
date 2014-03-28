package com.abudko.reseller.huuto.query.rules;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class HuutoPriceRules extends AbstractPriceRules {

    @Override
    protected BigDecimal getAddInEuro() {
        return new BigDecimal(10);
    }
}
