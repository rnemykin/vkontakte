package com.abudko.reseller.huuto.query.service.item.html.stadium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
public class StadiumHtmlQueryItemServiceIntegrationTest {
    
    @Autowired
    @Qualifier("stadiumHtmlQueryItemServiceImpl")
    private QueryItemService service;

    @Test
    public void testCall() {
        ItemResponse itemResponse = service.extractItem("http://www.stadium.fi/vaatteet/haalarit/219873103/didriksons.k-migisi-coverall.black");
        
        assertNotNull(itemResponse);
    }
    
    @Test
    public void testExtractId() {
    	String id = new StadiumHtmlQueryItemServiceImpl().extractIdFromUrl("http://www.stadium.fi/vaatteet/haalarit/219873103/didriksons.k-migisi-coverall.black");
    	assertEquals("219873103", id);
    }

}
