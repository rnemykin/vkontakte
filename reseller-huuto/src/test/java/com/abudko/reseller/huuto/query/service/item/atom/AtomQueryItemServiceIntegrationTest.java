package com.abudko.reseller.huuto.query.service.item.atom;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

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
public abstract class AtomQueryItemServiceIntegrationTest {

    private static final String ITEM_URL = "uusi-poikien-talvihaalari-everest-koko-104/365596671";
    private static final String ITEM_URL_NO_OSTA_HETI = "beige-talvihaalari-110/365322988";

    @Autowired
    @Qualifier("atomQueryItemServiceImpl")
    private QueryItemService atomQueryItemService;

    private ItemResponse response;

    @Before
    public void setup() {
        response = atomQueryItemService.extractItem(ITEM_URL);
    }

    @Test
    public void testItemResponseSeller() {
        assertEquals("gabca123", response.getSeller());
    }

    @Test
    public void testItemResponseLocation() {
        assertEquals("", response.getLocation());
    }

    @Test
    public void testItemResponseCondition() {
        assertEquals("NEW", response.getCondition());
    }

    @Test
    public void testItemResponsePrice() {
        assertEquals("69.00", response.getPrice());
    }
    
    @Test
    public void testItemResponsePriceCurrentPriceNotSet() {
        assertNull(response.getCurrentPrice());
    }

    @Test
    public void testItemResponsePriceNoOstaHeti() {
        response = atomQueryItemService.extractItem(ITEM_URL_NO_OSTA_HETI);
        assertEquals("3.00", response.getPrice());
    }
    
    @Test
    public void testItemResponsePriceNoOstaHetiCurrentPriceSet() {
        response = atomQueryItemService.extractItem(ITEM_URL_NO_OSTA_HETI);
        assertEquals("3.00", response.getCurrentPrice());
    }

    @Test
    public void testItemResponseImgSrc() {
        assertEquals("http://kuvat2.huuto.net/b/a6/78914488563e04d8a9dc9aa1a0816", response.getImgBaseSrc());
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
