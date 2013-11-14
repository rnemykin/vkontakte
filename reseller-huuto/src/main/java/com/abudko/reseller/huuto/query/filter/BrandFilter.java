package com.abudko.reseller.huuto.query.filter;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.abudko.reseller.huuto.query.enumeration.Brand;
import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

@Component
public class BrandFilter implements SearchResultFilter {

    @Override
    public Collection<ListResponse> apply(Collection<ListResponse> queryResponses, SearchParams searchParams) {
        String brandParam = searchParams.getBrand();
        if (StringUtils.hasLength(brandParam) == false) {
            return queryResponses;
        }
        String brand = Brand.valueOf(brandParam.toUpperCase()).getParseName();

        Collection<ListResponse> filtered = new ArrayList<ListResponse>();
        for (ListResponse queryListResponse : queryResponses) {
            if (StringUtils.hasLength(queryListResponse.getBrand())) {
                if (queryListResponse.getBrand().toLowerCase().contains(brand.toLowerCase())) {
                    filtered.add(queryListResponse);
                }
            }
        }
        return filtered;
    }

}