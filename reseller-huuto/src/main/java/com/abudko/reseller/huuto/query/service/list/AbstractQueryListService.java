package com.abudko.reseller.huuto.query.service.list;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
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
import com.abudko.reseller.huuto.query.rules.AbstractPriceRules;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.QueryItemService;

@Component
public abstract class AbstractQueryListService implements QueryListService {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private List<SearchResultFilter> searchResultFilters;
    
    @Autowired
    @Qualifier("descriptionKeywordExclusionFilter") 
    private SearchResultFilter descriptionKeywordExclusionFilter;

    @Autowired
    @Qualifier("huutoPriceRules")
    protected AbstractPriceRules defaultPriceRules;

    @Override
    public Collection<ListResponse> search(String query, SearchParams searchParams)
            throws UnsupportedEncodingException, URISyntaxException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        Collection<ListResponse> queryResponses = callAndParse(query);

        setNewPrice(queryResponses);
        
        queryResponses = applyDescriptionKeywordExclusionFilter(queryResponses, searchParams);

        validate(queryResponses);

        applySearchParamsTo(queryResponses, searchParams);
        
        boolean resultsBeforeFiltering = !queryResponses.isEmpty();

        Collection<ListResponse> filteredResponses = applyFilters(queryResponses, searchParams);
        
        if (resultsBeforeFiltering && filteredResponses.isEmpty()) {
            log.warn("Results were filtered to 0 for query: " + query);
        }

        return filteredResponses;
    }

    public abstract Collection<ListResponse> callAndParse(String query) throws URISyntaxException;

    protected abstract QueryItemService getQueryItemService();

    protected abstract AbstractPriceRules getPriceRules();
    
    private Collection<ListResponse> applyDescriptionKeywordExclusionFilter(Collection<ListResponse> queryResponses, SearchParams searchParams) {
        return descriptionKeywordExclusionFilter.apply(queryResponses, searchParams);
    }

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
            String newPrice = getPriceRules()
                    .calculateNew(StringUtils.isEmpty(fullPrice) ? queryListResponse.getCurrentPrice() : fullPrice,
                            new BigDecimal(0));
            queryListResponse.setNewPrice(newPrice);
        }
    }

    private void applySearchParamsTo(Collection<ListResponse> queryResponses, SearchParams searchParams) {
        for (ListResponse queryListResponse : queryResponses) {
            queryListResponse.setCondition(searchParams.getClassification());
        }
    }

    private void validate(Collection<ListResponse> queryResponses) {
        for (ListResponse queryListResponse : queryResponses) {
            if (!StringUtils.isEmpty(queryListResponse.getItemUrl())
                    && (StringUtils.isEmpty(queryListResponse.getImgBaseSrc()) || StringUtils.isEmpty(queryListResponse
                            .getSize()))) {
                ItemResponse item = getQueryItemService().extractItem(queryListResponse.getItemUrl());
                String imgBaseSrc = item.getImgBaseSrc();
                queryListResponse.setImgBaseSrc(imgBaseSrc);
                queryListResponse.setItemResponse(item);
            }
        }
    }
}
