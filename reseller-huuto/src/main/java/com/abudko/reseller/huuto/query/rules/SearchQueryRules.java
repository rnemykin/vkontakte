package com.abudko.reseller.huuto.query.rules;

import com.abudko.reseller.huuto.query.params.SearchParams;

public interface SearchQueryRules {

    void apply(SearchParams searchParams);

}