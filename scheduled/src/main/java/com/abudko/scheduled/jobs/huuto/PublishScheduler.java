package com.abudko.scheduled.jobs.huuto;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
import com.abudko.reseller.huuto.query.rules.SearchQueryRules;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.QueryItemService;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.reseller.huuto.query.service.list.QueryListService;
import com.abudko.scheduled.jobs.Scheduler;
import com.abudko.scheduled.service.huuto.PublishManager;

public class PublishScheduler implements Scheduler {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("csvParamMapper")
    private ParamMapper searchParamMapper;

    @Autowired
    @Qualifier("csvParamBuilder")
    private ParamBuilder huutoParamBuilder;

    @Autowired
    @Qualifier("lekmerHtmlParamBuilder")
    private ParamBuilder lekmerBuilder;

    @Autowired
    @Qualifier("huutoHtmlQueryListServiceImpl")
    private QueryListService huutoQueryListService;

    @Autowired
    @Qualifier("lekmerHtmlQueryListServiceImpl")
    private QueryListService lekmerQueryListService;

    @Autowired
    @Qualifier("atomQueryItemServiceImpl")
    private QueryItemService atomQueryItemService;

    @Autowired
    @Qualifier("htmlSearchQueryRules")
    private SearchQueryRules searchQueryRules;

    @Autowired
    @Qualifier("huutoPublishManager")
    private PublishManager huutoPublishManager;

    @Autowired
    @Qualifier("lekmerPublishManager")
    private PublishManager lekmerPublishManager;

    public void schedule() {
        log.info("********* Start PublishScheduler *******");
        try {
            List<SearchParams> searchParamsList = searchParamMapper.getSearchParams();

            publishHuuto(searchParamsList);
            publishLekmer(searchParamsList);

            log.info("********* End PublishScheduler *******");

        } catch (Exception e) {
            log.error("Exception happened during PublishScheduler: ", e);
            throw new RuntimeException(e);
        }
    }

    public void publishHuuto(List<SearchParams> searchParamsList) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException, URISyntaxException,
            InterruptedException {
        for (SearchParams searchParams : searchParamsList) {
            applyHuutoSearchParamsRules(searchParams);

            String query = huutoParamBuilder.buildQuery(searchParams);

            log.info(String.format("Quering search: %s", query));

            Collection<ListResponse> queryListResponses = huutoQueryListService.search(query, searchParams);
            extractHuutoItemResponse(queryListResponses);

            Category category = Category.valueOf(searchParams.getCategoryenum());
            if (category != null) {
                huutoPublishManager.publishResults(category, queryListResponses);
            } else {
                log.warn(String.format("Can't find category for '%s'", searchParams.getCategoryenum()));
            }
        }
    }
    
    private void applyHuutoSearchParamsRules(SearchParams searchParams) {
        searchParams.setBrand("NO_BRAND");
        searchQueryRules.apply(searchParams);
    }
    
    private void extractHuutoItemResponse(Collection<ListResponse> queryListResponses) {
        for (ListResponse queryListResponse : queryListResponses) {
            ItemResponse itemResponse = atomQueryItemService.extractItem(queryListResponse.getItemUrl());
            queryListResponse.setItemResponse(itemResponse);
        }
    }

    public void publishLekmer(List<SearchParams> searchParamsList) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException, URISyntaxException,
            InterruptedException {
        SearchParams searchParams = searchParamsList.get(0);
        String query = "talvihaalari";//lekmerBuilder.buildQuery(searchParams);

        log.info(String.format("Quering search: %s", query));

        Collection<ListResponse> queryListResponses = lekmerQueryListService.search(query, searchParams);
        
        ListResponse next = queryListResponses.iterator().next();
        List<ListResponse> list = new ArrayList<ListResponse>();
        list.add(next);
        
        Category category = Category.valueOf(searchParams.getCategoryenum());
        if (category != null) {
            lekmerPublishManager.publishResults(category, list);
        } else {
            log.warn(String.format("Can't find category for '%s'", searchParams.getCategoryenum()));
        }
    }
}