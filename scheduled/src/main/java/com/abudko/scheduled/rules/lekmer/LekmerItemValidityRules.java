package com.abudko.scheduled.rules.lekmer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.service.item.QueryItemService;
import com.abudko.scheduled.rules.AbstractItemValidityRules;

@Component
public class LekmerItemValidityRules extends AbstractItemValidityRules {
    
    @Autowired
    @Qualifier("lekmerHtmlQueryItemServiceImpl")
    private QueryItemService queryItemService;

    @Override
    public String getIdPrefix() {
        return "LE";
    }

    @Override
    public QueryItemService getQueryItemService() {
        return queryItemService;
    }
}
