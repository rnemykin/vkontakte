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

public class PublishStadiumScheduler implements Scheduler {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("stadiumHtmlQueryListServiceImpl")
    private QueryListService queryListService;

    @Autowired
    @Qualifier("groupPublishManager")
    private PublishManager publishManager;

    public void schedule() {
        log.info("********* Start Publish Stadium Scheduler *******");
        try {
            publish();

            log.info("********* End Publish Stadium Scheduler *******");

        } catch (Exception e) {
            log.error("Exception happened during Publish Stadium Scheduler: ", e);
            throw new RuntimeException(e);
        }
    }

    private void publish() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException,
            UnsupportedEncodingException, URISyntaxException, InterruptedException {
        publishInternal("overall", "TALVIHAALARI", 0);
        publishInternal("coverall", "TALVIHAALARI", 0);
        publishInternal("talvikenkä", "TALVIKENGAT", 0);
    }

    private void publishInternal(String query, String categoryenum, int limit)
            throws UnsupportedEncodingException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, URISyntaxException, InterruptedException {
        List<ListResponse> list = new ArrayList<>();
        SearchParams searchParams = new SearchParams();
        searchParams.setCategoryenum(categoryenum);
        searchParams.setDiscount(30);

        log.info(String.format("Quering search: %s", query));

        Collection<ListResponse> queryListResponses = queryListService.search(query, searchParams);

        if (limit == 0) {
            list.addAll(queryListResponses);
        }
        else {
            int i = 0;
            for (Iterator<ListResponse> iterator = queryListResponses.iterator(); iterator.hasNext();) {
                ListResponse response = iterator.next();
                if (i++ < limit) {
                    list.add(response);
                }
            }
        }

        Category category = Category.valueOf(categoryenum);
        if (category != null) {
        	getPublishManager().publishResults(category, list);
        } else {
            log.warn(String.format("Can't find category for '%s'", categoryenum));
        }
    }
    
    protected PublishManager getPublishManager() {
		return publishManager;
	}
}