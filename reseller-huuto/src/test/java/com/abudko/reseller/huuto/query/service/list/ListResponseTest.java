package com.abudko.reseller.huuto.query.service.list;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.abudko.reseller.huuto.query.enumeration.Classification;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

public class ListResponseTest {

    private static final String BIDS = "bids";

    private static final String BRAND = "brand";

    private static final String CONDITION = "1";

    private static final String CURRENT_PRICE = "currentprice";

    private static final String FULL_PRICE = "fullPrice";

    private static final String DESCRIPTION = "description";

    private static final String IMG_BASE_SRC = "imgBaseSrc";

    private static final String SIZE = "size";

    private static final String ITEM_URL = "itemUrl";

    private static final String LAST = "last";

    private static final String SELLER = "seller";

    private ListResponse testResponse;

    private ItemResponse itemResponse;

    @Before
    public void setup() {
        testResponse = new ListResponse();

        testResponse.setBids(BIDS);
        testResponse.setBrand(BRAND);
        testResponse.setCondition(CONDITION);
        testResponse.setCurrentPrice(CURRENT_PRICE);
        testResponse.setDescription(DESCRIPTION);
        testResponse.setImgBaseSrc(IMG_BASE_SRC);
        testResponse.setSize(SIZE);
        testResponse.setFullPrice(FULL_PRICE);
        testResponse.setItemUrl(ITEM_URL);
        testResponse.setLast(LAST);

        itemResponse = new ItemResponse();
        itemResponse.setSeller(SELLER);
        testResponse.setItemResponse(itemResponse);
    }

    @Test
    public void testComparable() {
        List<ListResponse> responses = new ArrayList<ListResponse>();
        ListResponse response1 = new ListResponse();
        response1.setFullPrice("33");
        ListResponse response2 = new ListResponse();
        response2.setFullPrice("22");
        ListResponse response3 = new ListResponse();
        response3.setFullPrice("44");
        responses.add(response1);
        responses.add(response2);
        responses.add(response3);

        Collections.sort(responses);

        assertEquals(response2, responses.get(0));
    }

    @Test
    public void testDumpDescription() {
        assertTrue(testResponse.dump().contains(DESCRIPTION));
    }

    @Test
    public void testDumpBids() {
        assertTrue(testResponse.dump().contains(BIDS));
    }

    @Test
    public void testDumpCurrentPrice() {
        assertTrue(testResponse.dump().contains(CURRENT_PRICE));
    }

    @Test
    public void testDumpFullPrice() {
        assertTrue(testResponse.dump().contains(FULL_PRICE));
    }

    @Test
    public void testDumpLast() {
        assertTrue(testResponse.dump().contains(LAST));
    }

    @Test
    public void testDumpCondition() {
        assertTrue(testResponse.dump().contains(Classification.getClassification(CONDITION).name()));
    }
 
    @Test
    public void testDumpItemUrl() {
        assertTrue(testResponse.dump().contains(ITEM_URL));
    }
    
    @Test
    public void testDumpSeller() {
        assertTrue(testResponse.dump().contains(SELLER));
    }
    
    @Test
    public void testDumpItemResponseNull() {
        testResponse.setItemResponse(null);
        
        assertFalse(testResponse.dump().contains(SELLER));
    }
    
    @Test
    public void testEquals() {
        String itemUrl = "itemUrl";
        testResponse.setItemUrl(itemUrl);
        ListResponse response = new ListResponse();
        response.setItemUrl(itemUrl);
        
        assertEquals(response, testResponse);
    }
    
    @Test
    public void testEqualsNegative() {
        ListResponse response = new ListResponse();
        response.setItemUrl(testResponse.getItemUrl() + "3y7re982");
        
        assertFalse(testResponse.equals(response));
    }
    
    
}
