package com.abudko.reseller.huuto.query.service.list.atom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abudko.reseller.huuto.query.builder.ParamBuilder;
import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.reseller.huuto.query.service.list.QueryListService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/test-app-config.xml" })
public class AtomQueryListServiceIntegrationTest {

    @Autowired
    @Qualifier("atomParamBuilder")
    private ParamBuilder paramBuilder;

    @Autowired
    @Qualifier("atomQueryListServiceImpl")
    private QueryListService queryService;
    
    private static ListResponse response;

    @Before
    public void setup() throws UnsupportedEncodingException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, URISyntaxException {
        if (response == null) {
            SearchParams params = getSearchParams();
            Collection<ListResponse> result = queryService.search(getQuery(params), params);
            response = result.iterator().next();
        }
    }
    
    @Test
    public void testDescription() throws Exception {
        assertEquals("*UUSI* Everest talvihaalari Stadiumista,110 cm, katso kuvat!", response.getDescription());
    }

    @Test
    public void testCurrentPrice() throws Exception {
        assertEquals("59.95", response.getCurrentPrice());
    }
    
    @Test
    public void testFullPrice() throws Exception {
        assertEquals("59.95", response.getFullPrice());
    }
    
    @Test
    public void testBrand() throws Exception {
        assertEquals("Everest", response.getBrand());
    }
    
    @Test
    public void testSize() throws Exception {
        assertEquals("110", response.getSize());
    }
    
    @Test
    public void testCondition() throws Exception {
        assertEquals("new", response.getCondition());
    }
    
    @Test
    public void testItemUrl() throws Exception {
        assertEquals("277480818", response.getItemUrl());
    }
    
    @Test
    public void testImgBaseSrc() throws Exception {
        assertEquals("http://kuvat2.huuto.net/9/26/7f761541cc89f20b2230c691acdd5", response.getImgBaseSrc());
    }
    
    @Test
    public void testNewPrice() throws Exception {
        assertNotNull(response.getNewPrice());
    }
    
    private SearchParams getSearchParams() {
        SearchParams searchParams = new SearchParams();
        searchParams.setWords("TALVIHAALARI");
        searchParams.setLocation("Helsinki");
        searchParams.setClassification("new");
        searchParams.setSellstyle("buy_now");
        searchParams.setBrand("EVEREST");
        return searchParams;
    }

    private String getQuery(SearchParams searchParams) throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        return paramBuilder.buildQuery(searchParams);
    }
}