package com.abudko.reseller.huuto.query.service.list.html.huuto;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
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
public class HuutoHtmlQueryListServiceIntegrationTest {

    @Autowired
    private ParamMapper queryParamMapper;

    @Autowired
    @Qualifier("csvParamBuilder")
    private ParamBuilder csvParamBuilder;

    @Autowired
    @Qualifier("huutoHtmlQueryListServiceImpl")
    private QueryListService queryService;

    @Test
    public void testScan() throws Exception {
        List<SearchParams> queryParamsList = queryParamMapper.getSearchParams();

        for (SearchParams searchParams : queryParamsList) {
            String query = getQuery(searchParams);
            Collection<ListResponse> collection = queryService.search(query, searchParams);
            assertNotNull(collection);
        }
    }

    private String getQuery(SearchParams searchParams) throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        return csvParamBuilder.buildQuery(searchParams);
    }
}
