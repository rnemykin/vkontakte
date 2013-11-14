package com.abudko.reseller.huuto.query.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

@Component
public class PriceFilter extends RangeFilter {

    private Logger log = LoggerFactory.getLogger(getClass());

    public Collection<ListResponse> apply(Collection<ListResponse> queryResponses, SearchParams searchParams) {
        double minPrice = parseMin(searchParams.getPrice_min());
        double maxPrice = parseMax(searchParams.getPrice_max());
        if (minPrice > 0 || maxPrice > 0) {
            Collection<ListResponse> filtered = filterInternal(queryResponses, minPrice, maxPrice);
            return sort(filtered);
        }

        return sort(queryResponses);
    }

    private Collection<ListResponse> filterInternal(Collection<ListResponse> queryResponses, double minPrice,
            double maxPrice) {
        Collection<ListResponse> filtered = new ArrayList<ListResponse>();
        for (ListResponse queryListResponse : queryResponses) {
            String newPriceStr = queryListResponse.getNewPrice();
            try {
                double newPrice = Double.parseDouble(newPriceStr);
                if (newPrice >= minPrice && newPrice <= maxPrice) {
                    filtered.add(queryListResponse);
                }
            } catch (RuntimeException e) {
                log.warn(String.format("Unable to parse price %s", newPriceStr), e);
            }
        }
        return filtered;
    }

    private List<ListResponse> sort(Collection<ListResponse> toBeSorted) {
        List<ListResponse> sortedList = new ArrayList<ListResponse>(toBeSorted);
        Collections.sort(sortedList);

        return sortedList;
    }
}
