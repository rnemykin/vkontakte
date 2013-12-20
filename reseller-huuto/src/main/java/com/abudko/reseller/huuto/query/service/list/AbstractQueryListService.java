package com.abudko.reseller.huuto.query.service.list;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.abudko.reseller.huuto.query.filter.SearchResultFilter;
import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.rules.PriceRules;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.QueryItemService;

@Component
public abstract class AbstractQueryListService implements QueryListService {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("atomQueryItemServiceImpl")
    private QueryItemService queryItemService;

    @Autowired
    private List<SearchResultFilter> searchResultFilters;

    @Autowired
    private PriceRules priceRules;

    @Override
    public Collection<ListResponse> search(String query, SearchParams searchParams)
            throws UnsupportedEncodingException, URISyntaxException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        Collection<ListResponse> queryResponses = callAndParse(query);

        setNewPrice(queryResponses);

        validateImgBaseSrc(queryResponses);

        applySearchParamsTo(queryResponses, searchParams);

        Collection<ListResponse> filteredResponses = applyFilters(queryResponses, searchParams);

        return filteredResponses;
    }
    
    public abstract Collection<ListResponse> callAndParse(String query) throws URISyntaxException;

    private Collection<ListResponse> applyFilters(Collection<ListResponse> queryResponses, SearchParams searchParams) {
        for (SearchResultFilter filter : searchResultFilters) {
            log.info(String.format("Applying filter '%s', queryResponses before '%d'", filter, queryResponses.size()));
            queryResponses = filter.apply(queryResponses, searchParams);
        }

        return queryResponses;
    }

    private void setNewPrice(Collection<ListResponse> queryResponses) {
        for (ListResponse queryListResponse : queryResponses) {
            String fullPrice = queryListResponse.getFullPrice();
            String newPrice = priceRules.calculateNew(StringUtils.isEmpty(fullPrice) ? queryListResponse.getCurrentPrice() : fullPrice);
            queryListResponse.setNewPrice(newPrice);
        }
    }

    private void applySearchParamsTo(Collection<ListResponse> queryResponses, SearchParams searchParams) {
        for (ListResponse queryListResponse : queryResponses) {
            queryListResponse.setCondition(searchParams.getClassification());
        }
    }

    private void validateImgBaseSrc(Collection<ListResponse> queryResponses) {
        for (ListResponse queryListResponse : queryResponses) {
            if (queryListResponse.getImgBaseSrc() == null || queryListResponse.getImgBaseSrc().isEmpty()) {
                ItemResponse item = queryItemService.extractItem(queryListResponse.getItemUrl());
                String imgBaseSrc = item.getImgBaseSrc();
                queryListResponse.setImgBaseSrc(imgBaseSrc);
            }
        }
    }
}
