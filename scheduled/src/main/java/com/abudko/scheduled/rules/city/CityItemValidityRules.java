package com.abudko.scheduled.rules.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.html.HtmlParserConstants;
import com.abudko.reseller.huuto.query.service.item.QueryItemService;
import com.abudko.scheduled.rules.AbstractItemValidityRules;

@Component
public class CityItemValidityRules extends AbstractItemValidityRules {
    
    @Autowired
    @Qualifier("cityHtmlQueryItemServiceImpl")
    private QueryItemService queryItemService;

    @Override
    public String getIdPrefix() {
        return HtmlParserConstants.CITY_ID_PREFIX;
    }

    @Override
    public QueryItemService getQueryItemService() {
        return queryItemService;
    }
}
