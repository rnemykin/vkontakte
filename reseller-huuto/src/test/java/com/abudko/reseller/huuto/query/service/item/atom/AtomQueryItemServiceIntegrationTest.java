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

    private static final String ITEM_URL = "ticket-to-heaven-toppahaalari-koko-80-uusi/327072985";
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
        assertEquals("Pauliina000", response.getSeller());
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
        assertEquals("95.00", response.getPrice());
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
        assertEquals("http://kuvat2.huuto.net/9/72/7df2e8e94252fd23ac509067571fc", response.getImgBaseSrc());
    }
    
    @Test
    public void testItemResponseID() {
        assertEquals("327072985", response.getId());
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
