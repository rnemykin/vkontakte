package com.abudko.reseller.huuto.query.filter;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

@Component
public abstract class AbstractNotEmptyFilter implements SearchResultFilter {

    @Override
    public Collection<ListResponse> apply(Collection<ListResponse> queryResponses, SearchParams searchParams) {
        Collection<ListResponse> filtered = new ArrayList<ListResponse>();
        for (ListResponse queryListResponse : queryResponses) {
            if (StringUtils.hasLength(getValue(queryListResponse))) {
                filtered.add(queryListResponse);
            }
        }
        return filtered;
    }
    
    protected abstract String getValue(ListResponse queryListResponse);

}
