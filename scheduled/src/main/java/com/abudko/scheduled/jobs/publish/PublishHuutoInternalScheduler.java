package com.abudko.scheduled.jobs.publish;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
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

public class PublishHuutoInternalScheduler implements Scheduler {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("csvUsedParamMapper")
    private ParamMapper searchParamMapper;

    @Autowired
    @Qualifier("csvParamBuilder")
    private ParamBuilder huutoParamBuilder;

    @Autowired
    @Qualifier("huutoHtmlQueryListServiceImpl")
    private QueryListService huutoQueryListService;

    @Autowired
    @Qualifier("atomQueryItemServiceImpl")
    private QueryItemService atomQueryItemService;

    @Autowired
    @Qualifier("huutoUsedPublishManager")
    private PublishManager huutoPublishManager;

    public void schedule() {
        log.info("********* Start Publish Huuto Internal Scheduler *******");
        try {
            List<SearchParams> searchParamsList = searchParamMapper.getSearchParams();

            publishHuuto(searchParamsList);

            log.info("********* End Publish Huuto Scheduler *******");

        } catch (Exception e) {
            log.error("Exception happened during Publish Huuto Internal Scheduler: ", e);
            throw new RuntimeException(e);
        }
    }

    private void publishHuuto(List<SearchParams> searchParamsList) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException, URISyntaxException,
            InterruptedException {
        for (SearchParams searchParams : searchParamsList) {
            String query = huutoParamBuilder.buildQuery(searchParams);

            log.info(String.format("Quering search: %s", query));

            Collection<ListResponse> queryListResponses = huutoQueryListService.search(query, searchParams);
            extractHuutoItemResponse(queryListResponses);

           huutoPublishManager.publishResults(null, queryListResponses);
        }
    }
    
    private void extractHuutoItemResponse(Collection<ListResponse> queryListResponses) {
        for (ListResponse queryListResponse : queryListResponses) {
            ItemResponse itemResponse = atomQueryItemService.extractItem(queryListResponse.getItemUrl());
            queryListResponse.setItemResponse(itemResponse);
        }
    }
}