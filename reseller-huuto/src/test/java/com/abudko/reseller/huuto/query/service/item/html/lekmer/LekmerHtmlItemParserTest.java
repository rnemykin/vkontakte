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
        assertEquals("LE219152223", response.getId());
    }
    
    @Test
    public void testBrand() {
        assertEquals("Geggamoja", response.getItemInfo().getBrand());
    }

    @Test
    public void testPrice() {
        assertEquals("45.43", response.getPrice());
    }
    
    @Test
    public void testSizes() {
        assertEquals(Arrays.asList("74", "80", "86", "92"), response.getSizes());
    }
    
    @Test
    public void testImgSrc() {
        assertEquals("http://lekmer.fi/mediaarchive/1163811/productmanMeasurement465x500/geggamoja-tuulenpitava-haalari-vauvan-cerise.jpg", response.getImgBaseSrc());
    }
    
    @Test
    public void testFormatImgSrc() {
    	String imgSrc = "/1135889/productmanMeasurement465x500/viking-talvisaappaat-goretex-fonn.jpg";
    	String imgSrcLekmer = "http://lekmer.fi/1135889/productmanMeasurement465x500/viking-talvisaappaat-goretex-fonn.jpg";
    	assertEquals(imgSrcLekmer, htmlParser.formatImgSrc(imgSrc));
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
