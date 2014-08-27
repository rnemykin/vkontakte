package com.abudko.reseller.huuto.query.rules.html;

import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.rules.SearchQueryRules;

@Component
public class HtmlWebSearchQueryRules implements SearchQueryRules {

    @Override
    public void apply(SearchParams searchParams) {
        searchParams.setClassification("new");
    }
}
