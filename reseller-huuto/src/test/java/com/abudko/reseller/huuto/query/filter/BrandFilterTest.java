package com.abudko.reseller.huuto.query.filter;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

public class BrandFilterTest {

    private SearchParams searchParams;

    private List<ListResponse> queryResponses;

    private SearchResultFilter filter = new BrandFilter();

    @Before
    public void setup() {
        searchParams = new SearchParams();
        queryResponses = new ArrayList<ListResponse>();
        queryResponses.add(new ListResponse());
        queryResponses.add(new ListResponse());
    }

    @Test
    public void testFilter() {
        String brandSearch = "Reima";
        String brandResponseMatch = "Reimatec";
        String brandResponseNotMatch = "Ticket";
        searchParams.setBrand(brandSearch);
        queryResponses.get(0).setBrand(brandResponseMatch);
        queryResponses.get(1).setBrand(brandResponseNotMatch);

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(brandResponseMatch, new ArrayList<ListResponse>(filtered).get(0).getBrand());
    }

    @Test
    public void testFilterLowerCase() {
        String brandSearch = "reima";
        String brandResponseMatch = "Reimatec";
        String brandResponseNotMatch = "Ticket";
        searchParams.setBrand(brandSearch);
        queryResponses.get(0).setBrand(brandResponseMatch);
        queryResponses.get(1).setBrand(brandResponseNotMatch);

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(brandResponseMatch, new ArrayList<ListResponse>(filtered).get(0).getBrand());
    }
    
    @Test
    public void testFilterNoBrandShouldNotFilter() {
        String brandSearch = "NO_BRAND";
        String brandResponseMatch = "Reimatec";
        String brandResponseNotMatch = "Unknown brand";
        searchParams.setBrand(brandSearch);
        queryResponses.get(0).setBrand(brandResponseMatch);
        queryResponses.get(1).setBrand(brandResponseNotMatch);

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(2, new ArrayList<ListResponse>(filtered).size());
    }

}
