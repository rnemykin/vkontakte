package com.abudko.scheduled.jobs.publish;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.abudko.reseller.huuto.query.enumeration.Category;
import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.reseller.huuto.query.service.list.QueryListService;
import com.abudko.scheduled.jobs.Scheduler;
import com.abudko.scheduled.service.huuto.PublishManager;

public class PublishLekmerScheduler implements Scheduler {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("lekmerHtmlQueryListServiceImpl")
    private QueryListService lekmerQueryListService;

    @Autowired
    @Qualifier("lekmerPublishManager")
    private PublishManager lekmerPublishManager;

    public void schedule() {
        log.info("********* Start Publish Lekmer Scheduler *******");
        try {
            publishLekmer();

            log.info("********* End Publish Lekmer Scheduler *******");

        } catch (Exception e) {
            log.error("Exception happened during Publish Lekmer Scheduler: ", e);
            throw new RuntimeException(e);
        }
    }

    private void publishLekmer() throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException, URISyntaxException,
            InterruptedException {
        SearchParams searchParams = new SearchParams();
        
        List<ListResponse> list = new ArrayList<ListResponse>();

        String query = "talvihaalari";
        searchParams.setCategoryenum("TALVIHAALARI");
        log.info(String.format("Quering search: %s", query));
        Collection<ListResponse> queryListResponses = lekmerQueryListService.search(query, searchParams);
        
        int i = 0;
        for (Iterator<ListResponse> iterator = queryListResponses.iterator(); iterator.hasNext();) {
            ListResponse response = iterator.next();
            if (i++ < 35) {
                list.add(response);
            }
        }
        
        Category category = Category.valueOf(searchParams.getCategoryenum());
        if (category != null) {
            lekmerPublishManager.publishResults(category, list);
        } else {
            log.warn(String.format("Can't find category for '%s'", searchParams.getCategoryenum()));
        }
        
        list = new ArrayList<ListResponse>();
        query = "välikausihaalari";
        searchParams.setCategoryenum("VALIKAUSIHAALARI");
        log.info(String.format("Quering search: %s", query));
        queryListResponses = lekmerQueryListService.search(query, searchParams);
        list.addAll(queryListResponses);
        
        category = Category.valueOf(searchParams.getCategoryenum());
        if (category != null) {
            lekmerPublishManager.publishResults(category, list);
        } else {
            log.warn(String.format("Can't find category for '%s'", searchParams.getCategoryenum()));
        }
    }
}