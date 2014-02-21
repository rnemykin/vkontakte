package com.abudko.reseller.huuto.query.service.item.html.lekmer;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.ItemStatus;
import com.abudko.reseller.huuto.query.service.item.html.HtmlItemParser;
import com.abudko.reseller.huuto.query.service.list.HtmlParserTestUtils;

public class LekmerHtmlItemParserTest {
    
    static {
        try {
            html = HtmlParserTestUtils.readHtmlFromFile("./src/test/resources/html/lekmer/lekmer-found.html");
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
    public void testItemStatusOpened() {
        assertEquals(ItemStatus.OPENED, response.getItemStatus());
    }

    @Test
    public void testItemStatusClosed() {
        try {
            html = HtmlParserTestUtils.readHtmlFromFile("./src/test/resources/html/lekmer/lekmer-notfound.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
        HtmlItemParser htmlParser = new LekmerHtmlItemParser();
        response = htmlParser.parse(html);
        
        assertEquals(ItemStatus.CLOSED, response.getItemStatus());
    }

}
