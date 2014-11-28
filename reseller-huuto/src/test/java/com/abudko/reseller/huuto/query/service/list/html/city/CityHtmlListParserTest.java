package com.abudko.reseller.huuto.query.service.list.html.city;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.abudko.reseller.huuto.query.service.list.HtmlParserTestUtils;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.reseller.huuto.query.service.list.html.HtmlListParser;

public class CityHtmlListParserTest {
 
    private static HtmlListParser htmlParser = new CityHtmlListParser();
    private static String html;
    private static Collection<ListResponse> responses;

    static {
        try {
            html = HtmlParserTestUtils.readHtmlFromFile("./src/test/resources/html/city/city-search.html");
            responses = htmlParser.parse(html);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ListResponse queryResponse;

    @Before
    public void setup() throws IOException {
        queryResponse = responses.iterator().next();
    }

    @Test
    public void testResponseSize() {
        assertEquals(13, responses.size());
    }
    
    @Test
    public void testDescription() {
        assertTrue(queryResponse.getDescription().length() > 0);
    }
    
    @Test
    public void testImgSrc() {
        ListResponse imgSrcResponse = new ArrayList<ListResponse>(responses).get(5);
        assertTrue("Src: " + imgSrcResponse.getImgBaseSrc(),
                imgSrcResponse.getImgBaseSrc().contains("www.citymarket.fi/images/catalog/small/viking-lasten_gore-tex_talvikengat-2052603.jpg"));
    }
    
    @Test
    public void testBrand() {
        assertEquals("VIKING", queryResponse.getBrand());
    }
    
    @Test
    public void testCurrentPrice() {
        String currentPrice = queryResponse.getCurrentPrice();
        try {
            Double.parseDouble(currentPrice);
        } catch (Exception e) {
            fail("Unable to parse current price " + currentPrice);
        }
    }
    
    @Test
    public void testItemUrl() {
        assertTrue("Url: " + queryResponse.getItemUrl(), queryResponse.getItemUrl().contains("http://www.citymarket.fi/shop/fi/kcitymarket/viking-windchill-jr-gtx-talvikenka-7204670--malli"));
    }
}