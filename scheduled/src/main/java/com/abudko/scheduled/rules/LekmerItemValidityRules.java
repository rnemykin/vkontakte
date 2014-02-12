package com.abudko.scheduled.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.service.item.QueryItemService;

@Component
public class LekmerItemValidityRules extends AbstractItemValidityRules {
    
    @Autowired
    @Qualifier("lekmerQueryItemServiceImpl")
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
