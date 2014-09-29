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
public class AtomQueryItemServiceIntegrationTest {

    private static final String ITEM_URL = "reima-toppahaalari-122/332227118";
    private static final String ITEM_URL_NO_OSTA_HETI = "reimatec-talvihousut-110cm/325784667";

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
        assertEquals("vrmaki1", response.getSeller());
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
        assertEquals("82.00", response.getPrice());
    }
    
    @Test
    public void testItemResponsePriceCurrentPriceNotSet() {
        assertNull(response.getCurrentPrice());
    }

    @Test
    public void testItemResponsePriceNoOstaHeti() {
        response = atomQueryItemService.extractItem(ITEM_URL_NO_OSTA_HETI);
        assertEquals("41.00", response.getPrice());
    }
    
    @Test
    public void testItemResponsePriceNoOstaHetiCurrentPriceSet() {
        response = atomQueryItemService.extractItem(ITEM_URL_NO_OSTA_HETI);
        assertEquals("41.00", response.getCurrentPrice());
    }

    @Test
    public void testItemResponseImgSrc() {
        assertEquals("http://kuvat2.huuto.net/1/7f/9a8a1b85839a052fd65efc01781ef", response.getImgBaseSrc());
    }
    
    @Test
    public void testItemResponseID() {
        assertEquals("332227118", response.getId());
    }

    @Test
    public void testItemResponseItemStatus() {
        assertEquals(ItemStatus.OPENED, response.getItemStatus());
    }

    @Test
    public void testParseImgBaseSrcWithoutSuffix() {
        assertFalse("Src: " + response.getImgBaseSrc(), response.getImgBaseSrc().contains(".jpg"));
    }

}
