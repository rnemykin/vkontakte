package com.abudko.scheduled.rules;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.ItemStatus;
import com.abudko.reseller.huuto.query.service.item.QueryItemService;

public abstract class AbstractItemValidityRules implements ItemValidityRules {

    public abstract QueryItemService getQueryItemService();
    
    public abstract String getIdPrefix();

    @Override
    public boolean isValid(String id) {
        if (id == null) {
            return true;
        }
        
        String idPrefix = getIdPrefix();
        
        if (idPrefix != null) {
            if (id.substring(0, 2).equalsIgnoreCase(idPrefix)) {
                ItemResponse itemResponse = getQueryItemService().extractItem("/" + id.substring(2, id.length()));
                return ItemStatus.OPENED.equals(itemResponse.getItemStatus());
            }
            else {
                return true;
            }
        }
        else {
            ItemResponse itemResponse = getQueryItemService().extractItem("/" + id);
            return ItemStatus.OPENED.equals(itemResponse.getItemStatus());
        }
    }
}
