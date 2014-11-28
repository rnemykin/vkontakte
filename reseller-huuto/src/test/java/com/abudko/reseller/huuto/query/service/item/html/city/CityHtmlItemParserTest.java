package com.abudko.reseller.huuto.query.service.item.html.city;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.ItemStatus;
import com.abudko.reseller.huuto.query.service.list.HtmlParserTestUtils;

public class CityHtmlItemParserTest {

    static {
        try {
            html = HtmlParserTestUtils.readHtmlFromFile("./src/test/resources/html/city/city-item.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String html;

    private ItemResponse response;

    private CityHtmlItemParser htmlParser;

    @Before
    public void setup() throws IOException {
        htmlParser = new CityHtmlItemParser();
        response = htmlParser.parse(html);
    }

    @Test
    public void testId() {
        assertEquals("CI6016299", response.getId());
    }

    @Test
    public void testPrice() {
        assertEquals("49.95", response.getPrice());
    }

    @Test
    public void testSizes() {
        assertEquals(Arrays.asList("22", "26"), response.getSizes());
    }
    
    @Test
    public void testItemStatus() {
        assertEquals(ItemStatus.OPENED, response.getItemStatus());
    }

    @Test
    public void testImgSrc() {
        assertEquals(
                "http://www.citymarket.fi/images/catalog/large/viking-penguin_gtx_lasten_talvikenka-6016299.jpg",
                response.getImgBaseSrc());
    }
}