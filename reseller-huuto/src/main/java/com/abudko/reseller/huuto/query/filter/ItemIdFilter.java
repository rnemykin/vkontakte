package com.abudko.reseller.huuto.query.filter;

import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

@Component
public class ItemIdFilter extends AbstractNotEmptyFilter {

    @Override
    protected String getValue(ListResponse queryListResponse) {
        ItemResponse itemResponse = queryListResponse.getItemResponse();
        if (itemResponse == null) {
            return " ";
        }
        return itemResponse.getId();
    }
}
