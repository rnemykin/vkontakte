package com.abudko.reseller.huuto.query.builder.csv;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abudko.reseller.huuto.query.builder.ParamBuilder;
import com.abudko.reseller.huuto.query.mapper.ParamMapper;
import com.abudko.reseller.huuto.query.params.SearchParams;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/test-app-config.xml" })
public class CsvParamBuilderIntegrationTest {

    @Autowired
    private ParamMapper searchParamMapper;

    @Autowired
    @Qualifier("csvParamBuilder")
    private ParamBuilder csvParamBuilder;

    @Test
    public void testDefaultNotDefinedParameters() throws Exception {
        SearchParams searchParams = searchParamMapper.getSearchParams().get(2);

        String query = csvParamBuilder.buildQuery(searchParams);

        assertEquals("/addtime/-30000/words/gaba", query);
    }

}
