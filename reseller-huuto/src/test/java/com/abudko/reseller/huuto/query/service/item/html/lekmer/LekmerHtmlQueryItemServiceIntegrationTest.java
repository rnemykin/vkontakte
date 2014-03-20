package com.abudko.reseller.huuto.query.service.item.html.lekmer;

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
public abstract class LekmerHtmlQueryItemServiceIntegrationTest {
    
    @Autowired
    @Qualifier("lekmerHtmlQueryItemServiceImpl")
    private QueryItemService service;

    @Test
    public void testCall() {
        ItemResponse itemResponse = service.extractItem("http://lekmer.fi/lastenvaatteet/Ulkovaatteet/haalarit/abeko-haalari-vuorillinen-ja-vedenpitävä-lennon-sininen");
    }

}
