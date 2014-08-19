package com.abudko.reseller.huuto.query.service.list.html.huuto;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.abudko.reseller.huuto.query.QueryConstants;
import com.abudko.reseller.huuto.query.filter.SearchResultFilter;
import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.rules.HuutoPriceRules;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.QueryItemService;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.reseller.huuto.query.service.list.QueryListService;

@RunWith(MockitoJUnitRunner.class)
public class HuutoHtmlQueryListServiceTest {

    @Mock
    private Logger log;

    @Mock
    private HuutoHtmlListParser htmlListParser;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private QueryItemService queryItemService;

    @Mock
    private SearchResultFilter filter;

    @Mock
    private HuutoPriceRules priceRules;

    @InjectMocks
    private QueryListService service = new HuutoHtmlQueryListServiceImpl();

    @Before
    public void setup() {
        List<SearchResultFilter> filters = new ArrayList<SearchResultFilter>();
        filters.add(filter);

        ReflectionTestUtils.setField(service, "searchResultFilters", filters);
    }

    @Test
    public void testScanImgSrcAndSizeExistsInSearch() throws Exception {
        SearchParams params = new SearchParams();
        params.setWords("keyword");
        params.setPrice_min("10");
        params.setPrice_max("20");
        String query = "query";
        String responseList = "responseList";
        when(restTemplate.getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/1"), String.class))
                .thenReturn(responseList);
        ListResponse response = new ListResponse();
        response.setDescription("description");
        response.setImgBaseSrc("imgBaseSrc");
        response.setSize("size");
        List<ListResponse> queryResponses = new ArrayList<ListResponse>();
        queryResponses.add(response);
        when(htmlListParser.parse(responseList)).thenReturn(queryResponses);
        when(filter.apply(queryResponses, params)).thenReturn(queryResponses);

        service.search(query, params);

        verify(restTemplate).getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/1"), String.class);
        verify(htmlListParser).parse(responseList);
        verify(queryItemService, times(0)).extractItem(Mockito.anyString());
    }

    @Test
    public void testScanImgSrcDoesNotExistInSearch() throws Exception {
        SearchParams params = new SearchParams();
        params.setWords("keyword");
        String query = "query";
        String responseList = "responseList";
        when(restTemplate.getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/1"), String.class))
                .thenReturn(responseList);
        ListResponse response = new ListResponse();
        response.setItemUrl("itemUrl");
        response.setDescription("description");
        response.setSize("size");
        List<ListResponse> queryResponses = new ArrayList<ListResponse>();
        queryResponses.add(response);
        when(htmlListParser.parse(responseList)).thenReturn(queryResponses);
        String itemImgBaseSrc = "itemImgBaseSrc";
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setImgBaseSrc(itemImgBaseSrc);
        when(queryItemService.extractItem(response.getItemUrl())).thenReturn(itemResponse);
        when(filter.apply(new LinkedHashSet<ListResponse>(queryResponses), params)).thenReturn(queryResponses);

        Collection<ListResponse> responses = service.search(query, params);

        assertEquals(itemImgBaseSrc, responses.toArray(new ListResponse[100])[0].getImgBaseSrc());
        verify(restTemplate).getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/1"), String.class);
        verify(htmlListParser).parse(responseList);
        verify(queryItemService).extractItem(Mockito.anyString());
    }

    @Test
    public void testScanSizeDoesNotExistInSearch() throws Exception {
        SearchParams params = new SearchParams();
        params.setWords("keyword");
        String query = "query";
        String responseList = "responseList";
        when(restTemplate.getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/1"), String.class))
        .thenReturn(responseList);
        ListResponse response = new ListResponse();
        response.setItemUrl("itemUrl");
        response.setDescription("description");
        response.setImgBaseSrc("imgBaseSrc");
        List<ListResponse> queryResponses = new ArrayList<ListResponse>();
        queryResponses.add(response);
        when(htmlListParser.parse(responseList)).thenReturn(queryResponses);
        String size = "size";
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setSizes(Arrays.asList(size));
        when(queryItemService.extractItem(response.getItemUrl())).thenReturn(itemResponse);
        when(filter.apply(new LinkedHashSet<ListResponse>(queryResponses), params)).thenReturn(queryResponses);
        
        service.search(query, params);
        
        verify(restTemplate).getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/1"), String.class);
        verify(htmlListParser).parse(responseList);
        verify(queryItemService).extractItem(Mockito.anyString());
    }
    
    @Test
    public void testScanSizeDoesNotExistInSearchButItemURLIsNull() throws Exception {
        SearchParams params = new SearchParams();
        params.setWords("keyword");
        String query = "query";
        String responseList = "responseList";
        when(restTemplate.getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/1"), String.class))
        .thenReturn(responseList);
        ListResponse response = new ListResponse();
        response.setDescription("description");
        response.setImgBaseSrc("imgBaseSrc");
        response.setItemUrl("");
        List<ListResponse> queryResponses = new ArrayList<ListResponse>();
        queryResponses.add(response);
        when(htmlListParser.parse(responseList)).thenReturn(queryResponses);
        String size = "size";
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setSizes(Arrays.asList(size));
        when(queryItemService.extractItem(response.getItemUrl())).thenReturn(itemResponse);
        when(filter.apply(new LinkedHashSet<ListResponse>(queryResponses), params)).thenReturn(queryResponses);
        
        service.search(query, params);
        
        verify(restTemplate).getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/1"), String.class);
        verify(htmlListParser).parse(responseList);
        verify(queryItemService, times(0)).extractItem(Mockito.anyString());
    }

    @Test
    public void testPriceRulesAppliedAfterSearch() throws Exception {
        SearchParams params = new SearchParams();
        params.setWords("keyword");
        String query = "query";
        String responseList = "responseList";
        when(restTemplate.getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/1"), String.class))
                .thenReturn(responseList);
        ListResponse response = new ListResponse();
        String fullPrice = "fullPrice";
        response.setFullPrice(fullPrice);
        response.setDescription("description");
        response.setImgBaseSrc("imgBaseSrc");
        response.setSize("size");
        List<ListResponse> queryResponses = new ArrayList<ListResponse>();
        queryResponses.add(response);
        when(htmlListParser.parse(responseList)).thenReturn(queryResponses);

        service.search(query, params);

        verify(priceRules).calculateNew(fullPrice, new BigDecimal(0));
    }

    @Test
    public void testSearchResultsLessThan50() throws Exception {
        String query = "query";
        setupListResponses(query, 10);

        service.search(query, new SearchParams());

        verify(restTemplate, times(1)).getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/1"),
                String.class);
        verify(restTemplate, times(0)).getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/2"),
                String.class);
    }

    @Test
    public void testSearchResults50() throws Exception {
        String query = "query";
        setupListResponses(query, 50);

        service.search(query, new SearchParams());

        verify(restTemplate, times(1)).getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/1"),
                String.class);
        verify(restTemplate, times(1)).getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/2"),
                String.class);
        verify(restTemplate, times(0)).getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/3"),
                String.class);
    }

    @Test
    public void testSearchResultsMoreThan50() throws Exception {
        String query = "query";
        setupListResponses(query, 70);

        service.search(query, new SearchParams());

        verify(restTemplate, times(1)).getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/1"),
                String.class);
        verify(restTemplate, times(1)).getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/2"),
                String.class);
        verify(restTemplate, times(0)).getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/3"),
                String.class);
    }

    @Test
    public void testSearchResultsMoreThan200() throws Exception {
        String query = "query";
        setupListResponses(query, 210);

        service.search(query, new SearchParams());

        verify(restTemplate, times(1)).getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/1"),
                String.class);
        verify(restTemplate, times(1)).getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/2"),
                String.class);
        verify(restTemplate, times(1)).getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/3"),
                String.class);
        verify(restTemplate, times(1)).getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/4"),
                String.class);
        verify(restTemplate, times(1)).getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/5"),
                String.class);
        verify(restTemplate, times(0)).getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/6"),
                String.class);
    }

    private void setupListResponses(String query, int responseCount) throws RestClientException, URISyntaxException {
        final int maxItemOnPage = 50;
        int pageCount = responseCount / maxItemOnPage + 1;

        for (int i = 1; i < pageCount + 1; i++) {
            String responseList = "responseList" + i;
            when(restTemplate.getForObject(new URI(QueryConstants.HUUTO_HTML_SEARCH_URL + query + "/page/" + i), String.class))
                    .thenReturn(responseList);
            List<ListResponse> queryResponses = new ArrayList<ListResponse>();
            int count = i == pageCount ? responseCount % maxItemOnPage : maxItemOnPage;
            for (int j = 0; j < count; j++) {
                ListResponse response = new ListResponse();
                response.setSize("size");
                response.setImgBaseSrc("imgBaseSrc");
                queryResponses.add(response);
            }
            when(htmlListParser.parse(responseList)).thenReturn(queryResponses);
        }
    }

}
