package com.abudko.reseller.huuto.query.filter;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

public class ImageFilterTest {

    @Test
    public void testFilterImageSrcNull() {
        SearchParams searchParams = new SearchParams();
        List<ListResponse> queryResponses = new ArrayList<ListResponse>();
        ListResponse listResponseWithImage = new ListResponse();
        listResponseWithImage.setImgBaseSrc("imgBaseSrc");
        queryResponses.add(listResponseWithImage);
        ListResponse listResponseWithoutImage = new ListResponse();
        listResponseWithoutImage.setImgBaseSrc(null);
        queryResponses.add(listResponseWithoutImage);

        Collection<ListResponse> resultList = new ImageFilter().apply(queryResponses, searchParams);

        assertEquals(1, resultList.size());
    }
    
    @Test
    public void testFilterImageSrcEmpty() {
        SearchParams searchParams = new SearchParams();
        List<ListResponse> queryResponses = new ArrayList<ListResponse>();
        ListResponse listResponseWithImage = new ListResponse();
        listResponseWithImage.setImgBaseSrc("imgBaseSrc");
        queryResponses.add(listResponseWithImage);
        ListResponse listResponseWithoutImage = new ListResponse();
        listResponseWithoutImage.setImgBaseSrc("");
        queryResponses.add(listResponseWithoutImage);
        
        Collection<ListResponse> resultList = new ImageFilter().apply(queryResponses, searchParams);
        
        assertEquals(1, resultList.size());
    }

}
