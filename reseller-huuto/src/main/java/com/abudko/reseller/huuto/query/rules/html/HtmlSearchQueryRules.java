package com.abudko.reseller.huuto.query.rules.html;

import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.rules.SearchQueryRules;

@Component
public class HtmlSearchQueryRules implements SearchQueryRules {

    @Override
    public void apply(SearchParams searchParams) {
        searchParams.setArea("Helsinki%20OR%20Espoo%20OR%20Vantaa%20OR%20KauniainenOR%20KeravaOR%20Tuusula");
        searchParams.setClassification("new");
//        searchParams.setSellstyle("buy-now");
    }
}
