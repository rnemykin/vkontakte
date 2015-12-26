package com.abudko.reseller.huuto.query.service.item;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.rules.AbstractPriceRules;
import com.abudko.reseller.huuto.query.rules.HuutoPriceRules;

@Component
public abstract class AbstractQueryItemService implements QueryItemService {
    
    protected Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    protected HuutoPriceRules defaultPriceRules;

    @Override
    public ItemResponse extractItem(String urlSuffix) {

        ItemResponse itemResponse = callAndParse(urlSuffix);

        itemResponse.setNewPrice(getPriceRules().calculateNew(itemResponse));
        setResponseIdFromUrlSuffix(urlSuffix, itemResponse);

        return itemResponse;
    }
    
    private void setResponseIdFromUrlSuffix(String itemUrl, ItemResponse itemResponse) {
        String id = extractIdFromUrl(itemUrl);
        
        if (id != null) {
            itemResponse.setId(id);
        }
    }
    
    protected String decodeUrl(String itemUrl) {
    	String decode = null;
		try {
			decode = URLDecoder.decode(itemUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("", e);
		}
		
		return decode;
    }
    
    protected abstract String extractIdFromUrl(String urlSuffix);
    
    protected abstract ItemResponse callAndParse(String urlSuffix);
    
    protected abstract AbstractPriceRules getPriceRules(); 
}
