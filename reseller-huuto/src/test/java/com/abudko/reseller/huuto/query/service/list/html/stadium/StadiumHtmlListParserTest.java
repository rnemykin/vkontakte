package com.abudko.reseller.huuto.query.service.list.html.stadium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

public class StadiumHtmlListParserTest {

    private static HtmlListParser htmlParser = new StadiumHtmlListParser();
    private static String html;
    private static Collection<ListResponse> responses;

    static {
        try {
            html = HtmlParserTestUtils.readHtmlFromFile("./src/test/resources/html/stadium/stadium-search.html");
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
        assertEquals(22, responses.size());
    }
    
    @Test
    public void testDescription() {
        assertTrue(queryResponse.getDescription().length() > 0);
    }
    
    @Test
    public void testImgSrc() {
        ListResponse imgSrcResponse = new ArrayList<ListResponse>(responses).get(0);
        assertTrue("Src: " + imgSrcResponse.getImgBaseSrc(),
                imgSrcResponse.getImgBaseSrc().contains(StadiumHtmlListParser.IMG_SRC_BIG));
    }
    
    @Test
    public void testBrand() {
    	ListResponse brandResponse = new ArrayList<ListResponse>(responses).get(0);
        assertNotNull("Brand: " + brandResponse.getBrand(), brandResponse.getBrand());
    }
    
    @Test
    public void testDiscount() {
    	ListResponse discountResponse = new ArrayList<ListResponse>(responses).get(0);
    	assertEquals("43", discountResponse.getDiscount());
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
        assertTrue("Url: " + queryResponse.getItemUrl(), queryResponse.getItemUrl().contains("http://www.stadium.fi/"));
    }
}
