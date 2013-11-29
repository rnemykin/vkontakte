package com.abudko.scheduled.jobs.huuto;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.abudko.reseller.huuto.query.builder.ParamBuilder;
import com.abudko.reseller.huuto.query.enumeration.Category;
import com.abudko.reseller.huuto.query.mapper.ParamMapper;
import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.QueryItemService;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.reseller.huuto.query.service.list.QueryListService;
import com.abudko.scheduled.jobs.Scheduler;
import com.abudko.scheduled.service.huuto.PublishManager;

public class PublishScheduler implements Scheduler {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ParamMapper searchParamMapper;

    @Autowired
    @Qualifier("csvParamBuilder")
    private ParamBuilder paramBuilder;

    @Autowired
    @Qualifier("htmlQueryListServiceImpl")
    private QueryListService queryListService;

    @Autowired
    @Qualifier("atomQueryItemServiceImpl")
    private QueryItemService atomQueryItemService;

    @Autowired
    private PublishManager publishManager;

    public void schedule() {
        log.info("********* Start scheduled scanning *******");
        try {

            List<SearchParams> searchParamsList = searchParamMapper.getSearchParams();

            for (SearchParams searchParams : searchParamsList) {
                applySearchParamsRules(searchParams);

                String query = getQuery(searchParams);

                log.info(String.format("Quering search: %s", query));

                Collection<ListResponse> queryListResponses = queryListService.search(query, searchParams);
                extractItemResponse(queryListResponses);

                publishManager.publishResults(Category.getCategoryFromValue(searchParams.getWords()).name(),
                        queryListResponses);
            }

            log.info("********* End scheduled scanning *******");

        } catch (Exception e) {
            log.error("Exception happened during scheduled scan: ", e);
            throw new RuntimeException(e);
        }
    }

    private void applySearchParamsRules(SearchParams searchParams) {
        searchParams.setBrand("NO_BRAND");
    }

    private String getQuery(SearchParams searchParams) throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        return paramBuilder.buildQuery(searchParams);
    }

    private void extractItemResponse(Collection<ListResponse> queryListResponses) {
        for (ListResponse queryListResponse : queryListResponses) {
            ItemResponse itemResponse = atomQueryItemService.extractItem(queryListResponse.getItemUrl());
            queryListResponse.setItemResponse(itemResponse);
        }
    }
}
