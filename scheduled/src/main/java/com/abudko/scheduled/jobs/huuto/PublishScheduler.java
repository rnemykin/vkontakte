package com.abudko.scheduled.jobs.huuto;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import com.abudko.reseller.huuto.query.builder.ParamBuilder;
import com.abudko.reseller.huuto.query.mapper.ParamMapper;
import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.QueryItemService;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.reseller.huuto.query.service.list.QueryListService;
import com.abudko.scheduled.csv.PhotoData;
import com.abudko.scheduled.jobs.Scheduler;
import com.abudko.scheduled.service.PhotoManager;

public class PublishScheduler implements Scheduler {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ParamMapper searchParamMapper;

    @Autowired
    @Qualifier("atomParamBuilder")
    private ParamBuilder paramBuilder;

    @Autowired
    @Qualifier("atomQueryListServiceImpl")
    private QueryListService queryListService;

    @Autowired
    @Qualifier("atomQueryItemServiceImpl")
    private QueryItemService atomQueryItemService;
    
    @Autowired
    @Qualifier("groupPhotoManager")
    private PhotoManager photoManager;
    
    @Value("#{scheduledProperties['huuto.comment']}")
    private String commentTemplate;

    public void schedule() {
        log.info("********* Start scheduled scanning *******");
        try {

            List<SearchParams> searchParamsList = searchParamMapper.getSearchParams();

            for (SearchParams searchParams : searchParamsList) {
                String query = getQuery(searchParams);

                log.info(String.format("Quering search: %s", query));

                Collection<ListResponse> queryListResponses = queryListService.search(query, searchParams);
                extractItemResponse(queryListResponses);
                
                publishResults(queryListResponses);
            }

            log.info("********* End scheduled scanning *******");

        } catch (Exception e) {
            log.error("Exception happened during scheduled scan: ", e);
        }
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
    
    private void publishResults(Collection<ListResponse> queryResponses) throws InterruptedException {
        for (ListResponse queryResponse : queryResponses) {
            log.info(String.format("Publishing query response: %s", queryResponse.toString()));

            PhotoData photoData = convert(queryResponse);
            photoManager.publishPhoto(photoData);
        }
    }
    
    private PhotoData convert(ListResponse listResponse) {
        PhotoData photoData = new PhotoData();
        //photoData.setDescription(description)
        
        ItemResponse itemResponse = listResponse.getItemResponse();
        String newPrice = itemResponse.getNewPrice();
        String id = itemResponse.getId();
        String brand = listResponse.getBrand();
        String size = listResponse.getSize();
        
        String comment = MessageFormat.format(commentTemplate, brand, size, newPrice, id);
        log.info(comment);
        
        return photoData;
    }
}
