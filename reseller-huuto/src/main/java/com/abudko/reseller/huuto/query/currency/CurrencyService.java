package com.abudko.reseller.huuto.query.currency;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CurrencyService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final BigDecimal DEFAULT_RATE = new BigDecimal(50);

    private static final String URL = "http://rate-exchange.appspot.com/currency?from=EUR&to=RUB";

    @Autowired
    private RestTemplate restTemplate;

    @Cacheable("currencyRate")
    public BigDecimal getRate() {
        BigDecimal rate = DEFAULT_RATE;

        try {
            rate = restTemplate.getForObject(URL, RateResponse.class).getRate();
        } catch (Throwable e) {
            log.error(String.format("Unable to extract currency rate, returning DEFAULT '%s'", DEFAULT_RATE), e);
        }

        return rate;
    }
}
