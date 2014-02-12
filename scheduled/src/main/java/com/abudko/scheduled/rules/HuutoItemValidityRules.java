package com.abudko.scheduled.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.service.item.QueryItemService;

@Component
public class HuutoItemValidityRules extends AbstractItemValidityRules {
    
    @Autowired
    @Qualifier("atomQueryItemServiceImpl")
    private QueryItemService atomQueryItemService;

    @Override
    public String getIdPrefix() {
        return null;
    }

    @Override
    public QueryItemService getQueryItemService() {
        return atomQueryItemService;
    }
}
