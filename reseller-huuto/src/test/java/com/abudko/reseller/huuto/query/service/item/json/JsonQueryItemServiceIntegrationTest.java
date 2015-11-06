package com.abudko.reseller.huuto.query.service.item.json;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.ItemStatus;
import com.abudko.reseller.huuto.query.service.item.QueryItemService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/test-app-config.xml" })
public class JsonQueryItemServiceIntegrationTest {
	
    private static final String ITEM_URL = "382102643";
    private static final String ITEM_URL_NO_OSTA_HETI = "382159724";

    @Autowired
    @Qualifier("jsonQueryItemServiceImpl")
    private QueryItemService jsonQueryItemService;

    private ItemResponse response;

    @Before
    public void setup() {
        response = jsonQueryItemService.extractItem(ITEM_URL);
    }

    @Test
    public void testItemResponseSeller() {
        assertEquals("gabca123", response.getSeller());
    }

    @Test
    public void testItemResponseLocation() {
        assertEquals("Espoo", response.getLocation());
    }

    @Test
    public void testItemResponseCondition() {
        assertEquals("new", response.getCondition());
    }

    @Test
    public void testItemResponseCurrectPrice() {
    	assertEquals("54", response.getCurrentPrice());
    }
    
    @Test
    public void testItemResponsePrice() {
        assertEquals("59", response.getPrice());
    }
    
    @Test
    public void testItemResponsePriceNoOstaHeti() {
        response = jsonQueryItemService.extractItem(ITEM_URL_NO_OSTA_HETI);
        assertEquals("0", response.getPrice());
    }
    
    @Test
    public void testItemResponsePriceNoOstaHetiCurrentPriceSet() {
        response = jsonQueryItemService.extractItem(ITEM_URL_NO_OSTA_HETI);
        assertEquals("77", response.getCurrentPrice());
    }

    @Test
    public void testItemResponseImgSrc() {
        assertEquals("http://kuvat.huuto.net/b/a6/78914488563e04d8a9dc9aa1a0816", response.getImgBaseSrc());
    }
    
    @Test
    public void testItemResponseID() {
        assertEquals(ITEM_URL.substring(ITEM_URL.lastIndexOf("/") + 1, ITEM_URL.length()), response.getId());
    }

    @Test
    public void testItemResponseItemStatus() {
        assertEquals(ItemStatus.CLOSED, response.getItemStatus());
    }

    @Test
    public void testParseImgBaseSrcWithoutSuffix() {
        assertFalse("Src: " + response.getImgBaseSrc(), response.getImgBaseSrc().contains(".jpg"));
    }
}
