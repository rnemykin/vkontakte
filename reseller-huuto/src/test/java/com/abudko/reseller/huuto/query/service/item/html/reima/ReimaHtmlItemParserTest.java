package com.abudko.reseller.huuto.query.service.item.html.reima;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.HtmlParserTestUtils;

public class ReimaHtmlItemParserTest {
    
    static {
        try {
            html = HtmlParserTestUtils.readHtmlFromFile("./src/test/resources/html/reima/reima-item.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String html;

    private ItemResponse response;
    
    private ReimaHtmlItemParser htmlParser;

    @Before
    public void setup() throws IOException {
        htmlParser = new ReimaHtmlItemParser();
        response = htmlParser.parse(html);
    }

    @Test
    public void testId() {
        assertEquals("RE510144-4620", response.getId());
    }
    
    @Test
    public void testBrand() {
        assertEquals("REIMA", response.getItemInfo().getBrand());
    }

    @Test
    public void testPrice() {
        assertEquals("45.00", response.getPrice());
    }
    
    @Test
    public void testSizes() {
        assertEquals(Arrays.asList("62", "68"), response.getSizes());
    }
    
    @Test
    public void testImgSrc() {
        assertEquals("/medias/sys_master/images/8856059052062.png", response.getImgBaseSrc());
    }
    
    @Test
    public void testGetValidId() throws Exception {
        final String id = "510144-4620";
        assertEquals("510144-4620", htmlParser.getValidId(id));
    }
    
    @Test
    public void testGetValidIdNotDigit() throws Exception {
        final String id = "12345678901s23456789";
        assertEquals(id, htmlParser.getValidId(id));
    }
}
