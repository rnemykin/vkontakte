package com.abudko.scheduled.rules.huuto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.service.item.QueryItemService;
import com.abudko.scheduled.rules.AbstractItemValidityRules;

@Component
public class HuutoItemValidityRules extends AbstractItemValidityRules {
    
    @Autowired
    @Qualifier("jsonQueryItemServiceImpl")
    private QueryItemService queryItemService;

    @Override
    public String getIdPrefix() {
        return null;
    }

    @Override
    public QueryItemService getQueryItemService() {
        return queryItemService;
    }
}
