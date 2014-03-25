package com.abudko.reseller.huuto.query.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

@Component
public class SizeFilter extends RangeFilter {

    private Logger log = LoggerFactory.getLogger(getClass());

    public Collection<ListResponse> apply(Collection<ListResponse> queryResponses, SearchParams searchParams) {
        double sizeMin = parseMin(searchParams.getSizeMin());
        double sizeMax = parseMax(searchParams.getSizeMax());
        if (sizeMin > 0 || sizeMax > 0) {
            Collection<ListResponse> filtered = filterInternal(queryResponses, sizeMin, sizeMax);
            return filtered;
        }

        return queryResponses;
    }

    private Collection<ListResponse> filterInternal(Collection<ListResponse> queryResponses, double sizeMin,
            double sizeMax) {
        Collection<ListResponse> filtered = new ArrayList<ListResponse>();
        for (ListResponse queryListResponse : queryResponses) {
            if (queryListResponse.hasManySizes()) {
                List<String> sizes = queryListResponse.getItemResponse().getSizes();
                for (String size : sizes) {
                    if (sizeMatches(size, sizeMin, sizeMax)) {
                        filtered.add(queryListResponse);
                        break;
                    }
                }
            } else if (sizeMatches(queryListResponse.getSize(), sizeMin, sizeMax)) {
                filtered.add(queryListResponse);
            }
        }
        return filtered;
    }

    private boolean sizeMatches(String sizeStr, double sizeMin, double sizeMax) {
        try {
            double size = Double.parseDouble(sizeStr);
            if (size >= sizeMin && size <= sizeMax) {
                return true;
            }
        } catch (RuntimeException e) {
            log.warn(String.format("Unable to parse size %s", sizeStr), e);
        }

        return false;
    }

}
