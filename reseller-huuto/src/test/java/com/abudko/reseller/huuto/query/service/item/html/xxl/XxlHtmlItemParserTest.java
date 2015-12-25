package com.abudko.reseller.huuto.query.service.item.html.xxl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.HtmlParserTestUtils;

public class XxlHtmlItemParserTest {
    
    static {
        try {
            html = HtmlParserTestUtils.readHtmlFromFile("./src/test/resources/html/xxl/xxl-item.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String html;

    private ItemResponse response;
    
    private XxlHtmlItemParser htmlParser;

    @Before
    public void setup() throws IOException {
        htmlParser = new XxlHtmlItemParser();
        response = htmlParser.parse(html);
    }

    @Test
    public void testId() {
        assertEquals("XX1111581_1", response.getId());
    }
    
    @Test
    public void testBrand() {
        assertEquals("Reima", response.getItemInfo().getBrand());
    }

    @Test
    public void testPrice() {
        assertEquals("59.90", response.getPrice());
    }
    
    @Test
    public void testSizes() {
        assertEquals(Arrays.asList("92", "98", "110", "122"), response.getSizes());
    }
    
    @Test
    public void testImgSrc() {
        assertEquals("http://xxl.fi/mam/celum/celum_assets/9075175522334_1111581_Mid_Blue_1_135825_jpg.jpg?1", response.getImgBaseSrc());
    }
}
