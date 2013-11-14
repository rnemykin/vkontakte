package com.abudko.reseller.huuto.query.filter;

import java.util.Collection;

import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

public interface SearchResultFilter {

    Collection<ListResponse> apply(Collection<ListResponse> queryResponses, SearchParams searchParams);

}
