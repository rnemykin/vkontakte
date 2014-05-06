package com.abudko.reseller.huuto.query.filter;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

@Component
public class ItemIdExclusionFilter extends AbstractNotEmptyFilter {
    
    private static final List<String> EXCLUSION_LIST = Arrays.asList("LE", "LE475521 056", "LE13092862 BRIGHT", "LE13092852 BRIGHT");

    @Override
    protected String getValue(ListResponse queryListResponse) {
        ItemResponse itemResponse = queryListResponse.getItemResponse();
        if (itemResponse == null) {
            return " ";
        }
        if (shouldInclude(itemResponse)) {
            return itemResponse.getId();
        }
        
        return null;
    }
    
    private boolean shouldInclude(ItemResponse itemResponse) {
        return !EXCLUSION_LIST.contains(itemResponse.getId());
    }
}
