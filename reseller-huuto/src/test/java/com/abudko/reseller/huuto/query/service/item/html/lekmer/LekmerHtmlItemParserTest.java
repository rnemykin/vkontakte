package com.abudko.reseller.huuto.query.service.item.html.lekmer;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.HtmlParserTestUtils;

public class LekmerHtmlItemParserTest {
    
    static {
        try {
            html = HtmlParserTestUtils.readHtmlFromFile("./src/test/resources/html/lekmer/lekmer-item.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String html;

    private ItemResponse response;
    
    private LekmerHtmlItemParser htmlParser;

    @Before
    public void setup() throws IOException {
        htmlParser = new LekmerHtmlItemParser();
        response = htmlParser.parse(html);
    }

    @Test
    public void testId() {
        assertEquals("LE317121071", response.getId());
    }

    @Test
    public void testPrice() {
        assertEquals("54.95", response.getPrice());
    }
    
    @Test
    public void testSizes() {
        assertEquals(Arrays.asList("80", "90"), response.getSizes());
    }
    
    @Test
    public void testImgSrc() {
        assertEquals("http://lekmer.fi/mediaarchive/1082264/productmanMeasurement465x500/abeko-haalari-vuorillinen-ja-vedenpitava-lennon-sininen.jpg", response.getImgBaseSrc());
    }
    
    @Test
    public void testGetValidId() throws Exception {
        final String id = "1234567890123456789";
        assertEquals("123456789", htmlParser.getValidId(id));
    }
    
    @Test
    public void testGetValidIdNotDigit() throws Exception {
        final String id = "12345678901s23456789";
        assertEquals(id, htmlParser.getValidId(id));
    }
}
