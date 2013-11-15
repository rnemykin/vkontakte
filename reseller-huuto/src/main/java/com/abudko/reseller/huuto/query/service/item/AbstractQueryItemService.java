package com.abudko.reseller.huuto.query.service.item;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.rules.PriceRules;

@Component
public abstract class AbstractQueryItemService implements QueryItemService {
    
    protected Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private PriceRules priceRules;

    @Override
    public ItemResponse extractItem(String urlSuffix) {

        ItemResponse itemResponse = callAndParse(urlSuffix);

        itemResponse.setNewPrice(priceRules.calculateNew(itemResponse.getPrice()));
        setResponseIdFromUrlSuffix(urlSuffix, itemResponse);

        return itemResponse;
    }
    
    private void setResponseIdFromUrlSuffix(String itemUrl, ItemResponse itemResponse) {
        String id = extractIdFromUrl(itemUrl);
        itemResponse.setId(id);
    }
    
    protected abstract String extractIdFromUrl(String urlSuffix);
    
    public abstract ItemResponse callAndParse(String urlSuffix);
}