package com.abudko.reseller.huuto.query.filter;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

@Component
public class DiscountFilter implements SearchResultFilter {

    @Override
    public Collection<ListResponse> apply(Collection<ListResponse> queryResponses, SearchParams searchParams) {
        Integer discount = searchParams.getDiscount();
        if (discount == null || discount == 0) {
            return queryResponses;
        }
        Collection<ListResponse> filtered = new ArrayList<>();
        for (ListResponse queryListResponse : queryResponses) {
            if (shouldBeIncluded(queryListResponse, discount)) {
            	filtered.add(queryListResponse);
            }
        }
        return filtered;
    }
    
    private boolean shouldBeIncluded(ListResponse queryListResponse, Integer discount) {
        String responseDiscount = queryListResponse.getDiscount();
        
        if (!StringUtils.hasLength(responseDiscount)) {
            return false;
        }
        
        responseDiscount = responseDiscount.replace("%", "");
        responseDiscount = responseDiscount.replace("-", "");

        boolean result = Integer.valueOf(responseDiscount) >= discount;
        
        return result;
    }

}