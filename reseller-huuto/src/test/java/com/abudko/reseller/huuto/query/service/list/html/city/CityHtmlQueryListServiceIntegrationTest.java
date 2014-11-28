package com.abudko.reseller.huuto.query.service.list.html.city;

import static org.junit.Assert.assertFalse;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abudko.reseller.huuto.query.builder.ParamBuilder;
import com.abudko.reseller.huuto.query.mapper.ParamMapper;
import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.reseller.huuto.query.service.list.QueryListService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/test-app-config.xml" })
public abstract class CityHtmlQueryListServiceIntegrationTest {

    @Autowired
    private ParamMapper queryParamMapper;

    @Autowired
    @Qualifier("simpleHtmlParamBuilder")
    private ParamBuilder paramBuilder;

    @Autowired
    @Qualifier("cityHtmlQueryListServiceImpl")
    private QueryListService queryService;

    @Test
    public void testScan() throws Exception {
        SearchParams searchParams = getSearchParams();
        String query = getQuery(searchParams);
        
        Collection<ListResponse> results = queryService.search(query, searchParams);
        
        assertFalse(results.isEmpty());
    }

    private String getQuery(SearchParams searchParams) throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        return paramBuilder.buildQuery(searchParams);
    }
    
    private SearchParams getSearchParams() {
        SearchParams searchParams = new SearchParams();
        searchParams.setWords("viking lasten talvi");
        
        return searchParams;
    }
}