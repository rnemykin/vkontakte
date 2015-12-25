package com.abudko.reseller.huuto.query.service.list.html.xxl;

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
import com.abudko.reseller.huuto.query.service.list.html.lekmer.LekmerHtmlListParser;

public class XxlHtmlListParserTest {

	private static HtmlListParser htmlParser = new XxlHtmlListParser();
    private static String html;
    private static Collection<ListResponse> responses;

    static {
        try {
            html = HtmlParserTestUtils.readHtmlFromFile("./src/test/resources/html/xxl/xxl-search.html");
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
        assertEquals(4, responses.size());
    }
    
    @Test
    public void testDescription() {
        assertTrue(queryResponse.getDescription().length() > 0);
    }
    
    @Test
    public void testImgSrc() {
        ListResponse imgSrcResponse = new ArrayList<ListResponse>(responses).get(0);
        assertTrue("Src: " + imgSrcResponse.getImgBaseSrc(),
                imgSrcResponse.getImgBaseSrc().contains("http://xxl.fi/mam/celum/celum_assets/9075177390110_1111580_Denim_Blue_1_135819_jpg.jpg?1"));
    }
    
    @Test
    public void testBrand() {
    	ListResponse brandResponse = new ArrayList<ListResponse>(responses).get(0);
        assertNotNull("Brand: " + brandResponse.getBrand(), brandResponse.getBrand());
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
        assertTrue("Url: " + queryResponse.getItemUrl(), queryResponse.getItemUrl().contains("/reima-knoppi-reimatec-overall-lasten-talvihaalari/p/1111580_1_style"));
    }
}
