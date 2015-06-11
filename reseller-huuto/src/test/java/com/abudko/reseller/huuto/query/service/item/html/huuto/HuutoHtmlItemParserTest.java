package com.abudko.reseller.huuto.query.service.item.html.huuto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.html.HtmlItemParser;
import com.abudko.reseller.huuto.query.service.list.HtmlParserTestUtils;

/**
 * Tests for {@link HuutoHtmlItemParser}.
 * 
 * @author Alexei
 * 
 */
public class HuutoHtmlItemParserTest {

    static {
        try {
            html = HtmlParserTestUtils.readHtmlFromFile("./src/test/resources/html/huuto/test-item.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String html;

    private ItemResponse response;

    @Before
    public void setup() throws IOException {
        HtmlItemParser htmlParser = new HuutoHtmlItemParser();
        response = htmlParser.parse(html);
        System.out.println(response);
    }

    @Test
    public void testHv() {
        assertFalse(response.isHv());
    }

    @Test
    public void testCondition() {
        assertEquals("Uusi", response.getCondition());
    }

    @Test
    public void testLocation() {
        assertEquals("05830 Hyvinkää   ", response.getLocation());
    }

    @Test
    public void testPrice() {
        assertEquals("88.00", response.getPrice());
    }

    @Test
    public void testImgBaseSrc() {
        assertTrue("Src: " + response.getImgBaseSrc(), response.getImgBaseSrc().contains("http://"));
    }

    @Test
    public void testImgBaseSrcWithoutSuffix() {
        assertFalse("Src: " + response.getImgBaseSrc(), response.getImgBaseSrc().contains(".jpg"));
    }
    
    @Test
    public void testSeller() {
        assertEquals("Chili82", response.getSeller());
    }
}
