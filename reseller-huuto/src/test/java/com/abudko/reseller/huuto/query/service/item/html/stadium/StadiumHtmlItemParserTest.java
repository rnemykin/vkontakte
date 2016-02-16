package com.abudko.reseller.huuto.query.service.item.html.stadium;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.html.HtmlItemParser;
import com.abudko.reseller.huuto.query.service.list.HtmlParserTestUtils;

public class StadiumHtmlItemParserTest {
    
    static {
        try {
            html = HtmlParserTestUtils.readHtmlFromFile("./src/test/resources/html/stadium/stadium-item.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String html;

    private ItemResponse response;
    
    private HtmlItemParser htmlParser;

    @Before
    public void setup() throws IOException {
        htmlParser = new StadiumHtmlItemParser();
        response = htmlParser.parse(html);
    }

    @Test
    public void testId() {
        assertEquals("", response.getId());
    }
    
    @Test
    public void testBrand() {
        assertEquals("TIMBERLAND", response.getItemInfo().getBrand());
    }

    @Test
    public void testPrice() {
        assertEquals("59.90", response.getPrice());
    }
    
    @Test
    public void testSizes() {
        assertEquals(Arrays.asList("28", "30", "32"), response.getSizes());
    }
    
    @Test
    public void testImgSrc() {
        assertEquals("http://stadium.fi/INTERSHOP/static/WFS/Stadium-FinlandB2C-Site/-/Stadium/fi_FI/Detail/208333_103_TIMBERLAND_JIMINY%20GTX%20BOOT.png", response.getImgBaseSrc());
    }
}
