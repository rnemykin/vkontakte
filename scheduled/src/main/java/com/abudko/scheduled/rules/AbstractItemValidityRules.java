package com.abudko.scheduled.rules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.ItemStatus;
import com.abudko.reseller.huuto.query.service.item.QueryItemService;

public abstract class AbstractItemValidityRules implements ItemValidityRules {
    
    protected Logger log = LoggerFactory.getLogger(getClass());

    public abstract QueryItemService getQueryItemService();
    
    public abstract String getIdPrefix();

    @Override
    public boolean isValid(String id) {
        if (id == null) {
            return true;
        }
        
        String idPrefix = getIdPrefix();
        
        if (idPrefix != null) {
            return checkOthers(idPrefix, id);
        }
        else {
            return checkHuuto(id);
        }
    }
    
    private boolean checkHuuto(String id) {
        try {
            Long.parseLong(id);
            return isValidInternal("/" + id);
        }
        catch (HttpClientErrorException e) {
            if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
                return false;
            }
            return true;
        }
        catch (Throwable e) {
            return true;
        }
    }
    
    private boolean checkOthers(String idPrefix, String id) {
        if (id.substring(0, 2).equalsIgnoreCase(idPrefix)) {
            return isValidInternal(id.substring(2, id.length()));
        }
        return true;
    }
    
    protected boolean isValidInternal(String id) {
        ItemResponse itemResponse = getQueryItemService().extractItem(id);
        return ItemStatus.OPENED.equals(itemResponse.getItemStatus());
    }
}
