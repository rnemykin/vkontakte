package com.abudko.reseller.huuto.query.service.list.html.huuto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

/**
 * Tests for {@link HuutoHtmlListParser}.
 * 
 * @author Alexei
 * 
 */
public class HuutoHtmlListParserTest {

    private static HtmlListParser htmlParser = new HuutoHtmlListParser();
    private static String html;
    private static Collection<ListResponse> responses;

    static {
        try {
            html = HtmlParserTestUtils.readHtmlFromFile("./src/test/resources/html/huuto/test-resultlist.html");
            responses = htmlParser.parse(html);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ListResponse queryResponse;

    @Before
    public void setup() throws IOException {
        queryResponse = new ArrayList<ListResponse>(responses).get(3);
    }

    @Test
    public void testNoSearchResults() {
        String htmlNoSearchResults = null;
        try {
            htmlNoSearchResults = HtmlParserTestUtils
                    .readHtmlFromFile("./src/test/resources/html/huuto/no-search-results.html");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collection<ListResponse> responseList = htmlParser.parse(htmlNoSearchResults);

        assertTrue(responseList.isEmpty());
    }

    @Test
    public void testResponseSize() {
        assertEquals(15, responses.size());
    }

    @Test
    public void testDescription() {
        assertTrue(queryResponse.getDescription().length() > 0);
    }

    @Test
    public void testItemUrl() {
        assertFalse("Url: " + queryResponse.getItemUrl(),
                queryResponse.getItemUrl().contains("http://www.huuto.net/kohteet"));
        assertTrue("Url: " + queryResponse.getItemUrl(), queryResponse.getItemUrl().contains("/"));
    }

//    @Test
//    public void testBids() {
//        queryResponse = new ArrayList<ListResponse>(responses).get(5);
//        assertEquals("Bids: " + queryResponse.getBids(), "Ei huutajia", queryResponse.getBids());
//    }

    @Test
    public void testLast() {
        assertTrue("Last: " + queryResponse.getLast(), queryResponse.getLast().length() > 0);
    }

    @Test
    public void testCurrentPriceOstaHeti() {
        String currentPrice = queryResponse.getCurrentPrice();
        try {
            Double.parseDouble(currentPrice);
        } catch (Exception e) {
            fail("Unable to parse current price " + currentPrice);
        }
    }
    
    @Test
    public void testCurrentPriceHuuto() {
    	queryResponse = new ArrayList<ListResponse>(responses).get(1);
    	String currentPrice = queryResponse.getCurrentPrice();
    	try {
    		Double.parseDouble(currentPrice);
    	} catch (Exception e) {
    		fail("Unable to parse current price " + currentPrice);
    	}
    }
    
    @Test
    public void testCurrentPriceHuutajaExists() {
    	queryResponse = new ArrayList<ListResponse>(responses).get(5);
    	String currentPrice = queryResponse.getCurrentPrice();
    	try {
    		Double.parseDouble(currentPrice);
    	} catch (Exception e) {
    		fail("Unable to parse current price " + currentPrice);
    	}
    }

    @Test
    public void testFullPrice() {
        ListResponse fullPriceQueryResponse = new ArrayList<ListResponse>(responses).get(5);
        String fullPrice = fullPriceQueryResponse.getFullPrice();
        try {
            Double.parseDouble(fullPrice);
        } catch (Exception e) {
            fail("Unable to parse full price " + fullPrice);
        }
    }

    @Test
    public void testCompareFullPriceCurrentPrice() {
        ListResponse fullPriceQueryResponse = new ArrayList<ListResponse>(responses).get(4);
        String currentPrice = fullPriceQueryResponse.getCurrentPrice();
        String fullPrice = fullPriceQueryResponse.getFullPrice();

        double current = 0;
        try {
            current = Double.parseDouble(currentPrice);
        } catch (Exception e) {
            fail("Unable to parse current price " + fullPrice);
        }

        double full = 0;
        try {
            full = Double.parseDouble(fullPrice);
        } catch (Exception e) {
            fail("Unable to parse full price " + fullPrice);
        }

        assertTrue(full == current);
    }

    @Test
    public void testFullPriceNoComma() {
        ListResponse fullPriceQueryResponse = new ArrayList<ListResponse>(responses).get(5);
        String fullPrice = fullPriceQueryResponse.getFullPrice();

        assertFalse(fullPrice.contains(","));
    }

    @Test
    public void testImgSrc() {
        ListResponse imgSrcResponse = new ArrayList<ListResponse>(responses).get(5);
        assertTrue("Src: " + imgSrcResponse.getImgBaseSrc(),
                imgSrcResponse.getImgBaseSrc().contains("http://kuvat.huuto.net/"));
    }

    @Test
    public void testSize() {
        String size = queryResponse.getSize();
        try {
            Double.parseDouble(size);
        } catch (Exception e) {
            fail("Unable to parse size " + size);
        }
    }

    @Test
    public void testBrand() {
        assertNotNull("Brand: " + queryResponse.getBrand(), queryResponse.getBrand());
    }
}