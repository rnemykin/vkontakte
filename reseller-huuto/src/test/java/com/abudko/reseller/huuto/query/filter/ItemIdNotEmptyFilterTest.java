package com.abudko.reseller.huuto.query.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

public class ItemIdNotEmptyFilterTest {

    @Test
    public void testFilter() {
        ListResponse listResponseWithItemId = new ListResponse();
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setId("id");
        listResponseWithItemId.setItemResponse(itemResponse);
        ListResponse listResponseWithoutItemId = new ListResponse();
        listResponseWithoutItemId.setItemResponse(new ItemResponse());
        List<ListResponse> queryResponses = new ArrayList<ListResponse>();
        queryResponses.add(listResponseWithItemId);
        queryResponses.add(listResponseWithoutItemId);

        Collection<ListResponse> filtered = new ItemIdNotEmptyFilter().apply(queryResponses, null);

        assertEquals(1, filtered.size());
        assertNotNull(filtered.iterator().next().getItemResponse().getId());
    }
    
    @Test
    public void testFilterItemResponseNull() {
        ListResponse listResponseNoItemResponse = new ListResponse();
        List<ListResponse> queryResponses = new ArrayList<ListResponse>();
        queryResponses.add(listResponseNoItemResponse);
        
        Collection<ListResponse> filtered = new ItemIdNotEmptyFilter().apply(queryResponses, null);
        
        assertEquals(1, filtered.size());
        assertNull(filtered.iterator().next().getItemResponse());
    }

}
