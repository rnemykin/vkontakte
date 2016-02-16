package com.abudko.reseller.huuto.query.filter;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

public class DiscountFilterTest {

    private SearchParams searchParams;

    private List<ListResponse> queryResponses;

    private SearchResultFilter filter = new DiscountFilter();

    @Before
    public void setup() {
        searchParams = new SearchParams();
        queryResponses = new ArrayList<ListResponse>();
        queryResponses.add(new ListResponse());
        queryResponses.add(new ListResponse());
    }

    @Test
    public void testFilterNull() {
        Integer discountSearch = 10;
        String discountSearchMatch = "-10%";
        searchParams.setDiscount(discountSearch);
        queryResponses.get(0).setDiscount(null);
        queryResponses.get(1).setDiscount(discountSearchMatch);

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(1, filtered.size());
        assertEquals(discountSearchMatch, new ArrayList<ListResponse>(filtered).get(0).getDiscount());
    }
    
    @Test
    public void testFilter() {
        Integer discountParam = 10;
        searchParams.setDiscount(discountParam);
        queryResponses.get(0).setDiscount("5");
        queryResponses.get(1).setDiscount("11");
        
        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);
        
        assertEquals(1, filtered.size());
        assertEquals("11", new ArrayList<ListResponse>(filtered).get(0).getDiscount());
    }
}
