package com.abudko.reseller.huuto.query.service.item.html;

import static com.abudko.reseller.huuto.query.html.HtmlParserConstants.HUUTO_NET;
import static com.abudko.reseller.huuto.query.html.HtmlParserConstants.ITEM_URL_CONTEXT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.springframework.web.client.RestTemplate;

import com.abudko.reseller.huuto.query.rules.PriceRules;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.QueryItemService;

@RunWith(MockitoJUnitRunner.class)
public class HtmlQueryItemServiceTest {

    @Mock
    private Logger log;

    @Mock
    private HtmlItemParser htmlItemParser;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private PriceRules priceRules;

    @InjectMocks
    private QueryItemService service = new HtmlQueryItemServiceImpl();

    /**
     * Test for {@link HtmlQueryItemServiceImpl#extractItem(String)}.
     */
    @Test
    public void testExtractItem() {
        String itemUrl = "itemUrl";
        String response = "response";
        when(restTemplate.getForObject(HUUTO_NET + ITEM_URL_CONTEXT + itemUrl, String.class)).thenReturn(response);
        ItemResponse itemResponse = new ItemResponse();
        when(htmlItemParser.parse(response)).thenReturn(itemResponse);

        ItemResponse itemResponseActual = service.extractItem(itemUrl);

        assertEquals(itemResponseActual, itemResponse);
        verify(log).info(Mockito.contains(itemUrl));
        verify(restTemplate).getForObject(HUUTO_NET + ITEM_URL_CONTEXT + itemUrl, String.class);
        verify(htmlItemParser).parse(response);
    }

    @Test
    public void testExtractItemUrlSet() {
        String itemUrl = "itemUrl";
        String response = "response";
        when(restTemplate.getForObject(HUUTO_NET + ITEM_URL_CONTEXT + itemUrl, String.class)).thenReturn(response);
        ItemResponse itemResponse = new ItemResponse();
        when(htmlItemParser.parse(response)).thenReturn(itemResponse);

        ItemResponse itemResponseActual = service.extractItem(itemUrl);

        assertEquals(itemResponseActual.getItemUrl(), itemResponse.getItemUrl());
    }

    @Test
    public void testExtractItemIdSet() {
        String itemUrl = "itemUrl/234244232";
        String response = "response";
        when(restTemplate.getForObject(HUUTO_NET + ITEM_URL_CONTEXT + itemUrl, String.class)).thenReturn(response);
        ItemResponse itemResponse = new ItemResponse();
        when(htmlItemParser.parse(response)).thenReturn(itemResponse);

        ItemResponse itemResponseActual = service.extractItem(itemUrl);

        assertEquals(itemResponseActual.getId(), itemResponse.getId());
    }

    @Test
    public void testExtractIdValidUrl() {
        String id = "92820";
        String itemUrl = "blabla/" + id;
        String response = "response";
        when(restTemplate.getForObject(HUUTO_NET + ITEM_URL_CONTEXT + itemUrl, String.class)).thenReturn(response);
        ItemResponse itemResponse = new ItemResponse();
        when(htmlItemParser.parse(response)).thenReturn(itemResponse);

        ItemResponse itemResponseActual = service.extractItem(itemUrl);

        assertEquals(id, itemResponseActual.getId());
    }

    @Test
    public void testExtractIdInvalidUrl() {
        String itemUrl = "invalid";
        String response = "response";
        when(restTemplate.getForObject(HUUTO_NET + ITEM_URL_CONTEXT + itemUrl, String.class)).thenReturn(response);
        ItemResponse itemResponse = new ItemResponse();
        when(htmlItemParser.parse(response)).thenReturn(itemResponse);

        ItemResponse itemResponseActual = service.extractItem(itemUrl);

        assertNull("Id: " + itemResponseActual.getId(), itemResponseActual.getId());
    }
    
    @Test
    public void testExtractItemNewPriceCalculated() {
        String itemUrl = "blabla/23232";
        String response = "response";
        when(restTemplate.getForObject(HUUTO_NET + ITEM_URL_CONTEXT + itemUrl, String.class)).thenReturn(response);
        ItemResponse itemResponse = new ItemResponse();
        String price = "835";
        itemResponse.setPrice(price);
        when(htmlItemParser.parse(response)).thenReturn(itemResponse);
        when(priceRules.calculateNew(price)).thenReturn("3453");
        
        ItemResponse extractedItem = service.extractItem(itemUrl);
        
        assertNotNull(extractedItem.getNewPrice());
    }
}
