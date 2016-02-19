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
    private QueryListService queryListService;

    @Autowired
    @Qualifier("groupPublishManager")
    private PublishManager publishManager;

    public void schedule() {
        log.info("********* Start Publish Lekmer Scheduler *******");
        try {
            publish();

            log.info("********* End Publish Lekmer Scheduler *******");

        } catch (Exception e) {
            log.error("Exception happened during Publish Lekmer Scheduler: ", e);
            throw new RuntimeException(e);
        }
    }

    private void publish() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException,
            UnsupportedEncodingException, URISyntaxException, InterruptedException {
    	publishInternal("haalari", "TALVIHAALARI", 0);
//      publishLekmerInternal("toppahaalari", "TALVIHAALARI", 0);
//      publishLekmerInternal("toppapuku", "TALVIHAALARI", 0);
//      publishLekmerInternal("kaksiosainen setti talvi", "TALVIHAALARI", 0);
      publishInternal("Viking kengät", "TALVIKENGAT", 0);
      publishInternal("Reima bootsit", "TALVIKENGAT", 0);
      publishInternal("Superfit saappaat", "TALVIKENGAT", 0);
      publishInternal("Timberland talvisaappaat", "TALVIKENGAT", 0);
      publishInternal("Reima talvisaappaat", "TALVIKENGAT", 0);
      publishInternal("Columbia talvisaappaat", "TALVIKENGAT", 0);
      publishInternal("Sorel kengät", "TALVIKENGAT", 0);
//      publishLekmerInternal("talvitakkeja", "TALVITAKKI", 75);
//      publishLekmerInternal("toppahousut", "TALVIHOUSUT", 65);
    }

    private void publishInternal(String query, String categoryenum, int limit)
            throws UnsupportedEncodingException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, URISyntaxException, InterruptedException {
        List<ListResponse> list = new ArrayList<ListResponse>();
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