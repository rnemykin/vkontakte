package com.abudko.reseller.huuto.query.rules.atom;

import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.rules.SearchQueryRules;

@Component
public class AtomSearchQueryRules implements SearchQueryRules {

    public void apply(SearchParams searchParams) {
        searchParams.setLocation("Helsinki%20OR%20Espoo%20OR%20Vantaa%20OR%20Kauniainen");
        searchParams.setClassification("new");
        searchParams.setSellstyle("buy_now");
    }
}
