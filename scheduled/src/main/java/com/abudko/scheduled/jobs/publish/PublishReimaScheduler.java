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

public class PublishReimaScheduler implements Scheduler {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("reimaHtmlQueryListServiceImpl")
    private QueryListService reimaQueryListService;

    @Autowired
    @Qualifier("groupPublishManager")
    private PublishManager publishManager;

	public void schedule() {
        log.info("********* Start Publish Reima Scheduler *******");
        try {
            publishReima();

            log.info("********* End Publish Reima Scheduler *******");

        } catch (Exception e) {
            log.error("Exception happened during Publish Reima Scheduler: ", e);
            throw new RuntimeException(e);
        }
    }

    private void publishReima() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException,
            UnsupportedEncodingException, URISyntaxException, InterruptedException {
    	final String talvi = "?q=:relevance:season:Talvi&text=#";
        publishReimaInternal("Lasten-haalarit--ALE/c/o11" + talvi, "TALVIHAALARI", 0);
        publishReimaInternal("Lasten-kengät---Ale/Lasten-kengät--ALE/c/o41", "TALVIKENGAT", 0);
        publishReimaInternal("Lasten-puvut-ja-setit--ALE/c/o12" + talvi, "TALVIHAALARI", 0);
        publishReimaInternal("Lasten-housut--ALE/Reimatec®-housut--ALE/c/o1402" + talvi, "TALVIHAALARI", 0);
        publishReimaInternal("Lasten-takit--ALE/c/o13" + talvi, "TALVIHAALARI", 0);
    }

    private void publishReimaInternal(String query, String categoryenum, int limit)
            throws UnsupportedEncodingException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, URISyntaxException, InterruptedException {
        List<ListResponse> list = new ArrayList<>();
        SearchParams searchParams = new SearchParams();
        searchParams.setCategoryenum(categoryenum);
        searchParams.setDiscount(30);

        log.info(String.format("Quering search: %s", query));

        Collection<ListResponse> queryListResponses = reimaQueryListService.search(query, searchParams);

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