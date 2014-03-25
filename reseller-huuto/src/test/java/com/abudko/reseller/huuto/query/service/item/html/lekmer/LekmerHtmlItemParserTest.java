package com.abudko.reseller.huuto.query.service.item.html.lekmer;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.html.HtmlItemParser;
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

    @Before
    public void setup() throws IOException {
        HtmlItemParser htmlParser = new LekmerHtmlItemParser();
        response = htmlParser.parse(html);
    }

    @Test
    public void testId() {
        assertEquals("LE102-405-133 NAV", response.getId());
    }

    @Test
    public void testPrice() {
        assertEquals("129.90", response.getPrice());
    }
    
    @Test
    public void testSizes() {
        assertEquals(Arrays.asList("116", "128", "134"), response.getSizes());
    }
    
    @Test
    public void testImgSrc() {
        assertEquals("http://lekmer.fi/mediaarchive/1084106/productmanMeasurement465x500/ticket-outdoor-haalari-baggie-rugged-suit-navy-iris.jpg", response.getImgBaseSrc());
    }
}
