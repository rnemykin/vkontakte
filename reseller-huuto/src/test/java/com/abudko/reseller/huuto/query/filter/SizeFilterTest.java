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

public class SizeFilterTest {

    private SearchParams searchParams;

    private List<ListResponse> queryResponses;

    private SearchResultFilter filter = new SizeFilter();

    @Before
    public void setup() {
        searchParams = new SearchParams();
        queryResponses = new ArrayList<ListResponse>();
        queryResponses.add(new ListResponse());
        queryResponses.add(new ListResponse());
    }

    @Test
    public void testFilterMax() {
        String sizeMaxSearch = "10";
        String sizeResponseBigger = "11";
        String sizeResponseSmaller = "9";
        searchParams.setSizeMax(sizeMaxSearch);
        queryResponses.get(0).setSize(sizeResponseBigger);
        queryResponses.get(1).setSize(sizeResponseSmaller);

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(sizeResponseSmaller, new ArrayList<ListResponse>(filtered).get(0).getSize());
    }

    @Test
    public void testFilterMin() {
        String sizeMinSearch = "10";
        String sizeResponseBigger = "11";
        String sizeResponseSmaller = "9";
        searchParams.setSizeMin(sizeMinSearch);
        queryResponses.get(0).setSize(sizeResponseBigger);
        queryResponses.get(1).setSize(sizeResponseSmaller);

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(sizeResponseBigger, new ArrayList<ListResponse>(filtered).get(0).getSize());
    }

    @Test
    public void testFilter() {
        String sizeMinSearch = "9";
        String sizeMaxSearch = "10";
        String sizeResponseBigger = "11";
        String sizeResponseSmaller = "9";
        searchParams.setSizeMin(sizeMinSearch);
        searchParams.setSizeMax(sizeMaxSearch);
        queryResponses.get(0).setSize(sizeResponseBigger);
        queryResponses.get(1).setSize(sizeResponseSmaller);

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(sizeResponseSmaller, new ArrayList<ListResponse>(filtered).get(0).getSize());
    }

    @Test
    public void testFilterSearchParamMinSizeNull() {
        queryResponses.get(0).setSize("1");
        queryResponses.get(1).setSize("1");
        searchParams.setSizeMin(null);
        searchParams.setSizeMax("100");
        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(queryResponses, filtered);
    }

    @Test
    public void testFilterSearchParamMinSizeEmpty() {
        queryResponses.get(0).setSize("1");
        queryResponses.get(1).setSize("1");
        searchParams.setSizeMin("");
        searchParams.setSizeMax("100");

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(queryResponses, filtered);
    }

    @Test
    public void testFilterSearchParamMaxSizeNull() {
        queryResponses.get(0).setSize("100");
        queryResponses.get(1).setSize("100");
        searchParams.setSizeMin("1");
        searchParams.setSizeMax(null);
        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(queryResponses, filtered);
    }

    @Test
    public void testFilterSearchParamMaxSizeEmpty() {
        queryResponses.get(0).setSize("100");
        queryResponses.get(1).setSize("100");
        searchParams.setSizeMin("1");
        searchParams.setSizeMax("");

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(queryResponses, filtered);
    }

    @Test
    public void testFilterSearchParamSizesNull() {
        queryResponses.get(0).setSize("100");
        queryResponses.get(1).setSize("100");
        searchParams.setSizeMin("");
        searchParams.setSizeMax("");

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(queryResponses, filtered);
    }

    @Test
    public void testFilterSearchParamSizesEmpty() {
        queryResponses.get(0).setSize("100");
        queryResponses.get(1).setSize("100");
        searchParams.setSizeMin(null);
        searchParams.setSizeMax(null);

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertEquals(queryResponses, filtered);
    }

    @Test
    public void testFilterResponseSizeEmpty() {
        searchParams.setSizeMax("9.3");
        queryResponses.get(0).setSize("");
        queryResponses.get(1).setSize("");

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertTrue(filtered.isEmpty());
    }

    @Test
    public void testFilterResponseSizeNull() {
        searchParams.setSizeMax("9");
        queryResponses.get(0).setSize(null);
        queryResponses.get(1).setSize(null);

        Collection<ListResponse> filtered = filter.apply(queryResponses, searchParams);

        assertTrue(filtered.isEmpty());
    }
}
