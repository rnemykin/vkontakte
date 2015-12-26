package com.abudko.reseller.huuto.query.currency;

import java.util.Map;

public class RateResponse {

    private String base;

    private String date;
    
    private Map<String, Double> rates;

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Map<String, Double> getRates() {
		return rates;
	}

	public void setRates(Map rates) {
		this.rates = rates;
	}
}
