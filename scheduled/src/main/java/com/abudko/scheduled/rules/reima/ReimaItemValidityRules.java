package com.abudko.scheduled.rules.reima;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.html.HtmlParserConstants;
import com.abudko.reseller.huuto.query.service.item.QueryItemService;
import com.abudko.scheduled.rules.AbstractItemValidityRules;

@Component
public class ReimaItemValidityRules extends AbstractItemValidityRules {
    
    @Autowired
    @Qualifier("reimaHtmlQueryItemServiceImpl")
    private QueryItemService queryItemService;
    
    @Override
    public String getIdPrefix() {
        return HtmlParserConstants.REIMA_ID_PREFIX;
    }

    @Override
    public QueryItemService getQueryItemService() {
        return queryItemService;
    }
}
