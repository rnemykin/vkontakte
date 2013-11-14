package com.abudko.reseller.huuto.query.currency;

import java.math.BigDecimal;

public class RateResponse {

    private BigDecimal rate;

    private String from;
    
    private String to;

    public BigDecimal getRate() {
        return rate;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
