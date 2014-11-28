package com.abudko.reseller.huuto.query.service.item.html.city;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.QueryItemService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/test-app-config.xml" })
public abstract class CityHtmlQueryItemServiceIntegrationTest {
    
    @Autowired
    @Qualifier("cityHtmlQueryItemServiceImpl")
    private QueryItemService service;

    @Test
    public void testCall() {
        ItemResponse itemResponse = service.extractItem("6016299");
        
        System.out.println(itemResponse);
    }

}
