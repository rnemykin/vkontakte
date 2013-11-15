package com.abudko.reseller.huuto.query.filter;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

public class PriceFilterTest {

    private SearchParams searchParams;

    private List<ListResponse> queryResponses;

    private SearchResultFilter filter = new PriceFilter();

    @Before
    public void setup() {
        searchParams = new SearchParams();
        queryResponses = new ArrayList<ListResponse>();
        queryResponses.add(new ListResponse());
        queryResponses.add(new ListResponse());
    }

    @Test
    public void testFilterMax() {
        String priceMaxSearch = "10";
        String priceResponseBigger = "11";
        String priceResponseSmaller = "9";
        searchParams.setPrice_max(priceMaxSearch);
        queryResponses.get(0).setNewPrice(priceResponseBigger);
        queryResponses.get(1).setNewPrice(priceResponseSmaller);

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(priceResponseSmaller, new ArrayList<ListResponse>(filtered).get(0).getNewPrice());
    }

    @Test
    public void testFilterMin() {
        String priceMinSearch = "10";
        String priceResponseBigger = "11";
        String priceResponseSmaller = "9";
        searchParams.setPrice_min(priceMinSearch);
        queryResponses.get(0).setNewPrice(priceResponseBigger);
        queryResponses.get(1).setNewPrice(priceResponseSmaller);

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(priceResponseBigger, new ArrayList<ListResponse>(filtered).get(0).getNewPrice());
    }

    @Test
    public void testFilter() {
        String priceMinSearch = "9";
        String priceMaxSearch = "10";
        String priceResponseBigger = "11";
        String priceResponseSmaller = "9";
        searchParams.setPrice_min(priceMinSearch);
        searchParams.setPrice_max(priceMaxSearch);
        queryResponses.get(0).setNewPrice(priceResponseBigger);
        queryResponses.get(1).setNewPrice(priceResponseSmaller);

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(priceResponseSmaller, new ArrayList<ListResponse>(filtered).get(0).getNewPrice());
    }

    @Test
    public void testSortingAfterFilter() {
        String priceResponseBigger = "11";
        String priceResponseMiddle = "9";
        String priceResponseSmaller = "8";
        searchParams.setPrice_min("8");
        searchParams.setPrice_max("10");
        queryResponses.add(new ListResponse());
        queryResponses.get(0).setNewPrice(priceResponseMiddle);
        queryResponses.get(1).setNewPrice(priceResponseBigger);
        queryResponses.get(2).setNewPrice(priceResponseSmaller);

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(priceResponseSmaller, new ArrayList<ListResponse>(filtered).get(0).getNewPrice());
    }

    @Test
    public void testFilterSearchParamMinPriceNull() {
        queryResponses.get(0).setNewPrice("1");
        queryResponses.get(1).setNewPrice("1");
        searchParams.setPrice_min(null);
        searchParams.setPrice_max("100");
        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(queryResponses, filtered);
    }

    @Test
    public void testFilterSearchParamMinPriceEmpty() {
        queryResponses.get(0).setNewPrice("1");
        queryResponses.get(1).setNewPrice("1");
        searchParams.setPrice_min("");
        searchParams.setPrice_max("100");

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(queryResponses, filtered);
    }

    @Test
    public void testFilterSearchParamMaxPriceNull() {
        queryResponses.get(0).setNewPrice("100");
        queryResponses.get(1).setNewPrice("100");
        searchParams.setPrice_min("1");
        searchParams.setPrice_max(null);
        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(queryResponses, filtered);
    }

    @Test
    public void testFilterSearchParamMaxPriceEmpty() {
        queryResponses.get(0).setNewPrice("100");
        queryResponses.get(1).setNewPrice("100");
        searchParams.setPrice_min("1");
        searchParams.setPrice_max("");

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(queryResponses, filtered);
    }

    @Test
    public void testFilterSearchParamPricesNull() {
        queryResponses.get(0).setNewPrice("100");
        queryResponses.get(1).setNewPrice("100");
        searchParams.setPrice_min("");
        searchParams.setPrice_max("");

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(queryResponses, filtered);
    }

    @Test
    public void testFilterSearchParamPricesEmpty() {
        queryResponses.get(0).setNewPrice("100");
        queryResponses.get(1).setNewPrice("100");
        searchParams.setPrice_min(null);
        searchParams.setPrice_max(null);

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(queryResponses, filtered);
    }

    @Test
    public void testFilterResponsePriceEmpty() {
        searchParams.setPrice_max("9.3");
        queryResponses.get(0).setFullPrice("");
        queryResponses.get(1).setFullPrice("");

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertTrue(filtered.isEmpty());
    }

    @Test
    public void testFilterResponsePriceNull() {
        searchParams.setPrice_max("9");
        queryResponses.get(0).setFullPrice(null);
        queryResponses.get(1).setFullPrice(null);

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertTrue(new ArrayList<ListResponse>(filtered).isEmpty());
    }
}
