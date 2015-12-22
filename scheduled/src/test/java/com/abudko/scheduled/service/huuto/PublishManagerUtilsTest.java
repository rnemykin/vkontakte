package com.abudko.scheduled.service.huuto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

public class PublishManagerUtilsTest {
    
    private PublishManagerUtils utils = new PublishManagerUtils();

    @Test
    public void testGetId() {
        assertEquals("999", utils.getId("2-dspik  sxs2323 332323 [999]|bGluZGJlcmctb3ZlcmFsbC12ZXJtb250LWdyZWVuLW5hdnk=|"));
    }
    
    @Test
    public void testGetIdDash() {
        assertEquals("522123-9990", utils.getId("2-dspik  sxs2323 332323 [522123-9990]|bGluZGJlcmctb3ZlcmFsbC12ZXJtb250LWdyZWVuLW5hdnk=|"));
    }
    
    @Test
    public void testGetIdDashWord() {
        assertEquals("522123-RED", utils.getId("2-dspik  sxs2323 332323 [522123-RED]|bGluZGJlcmctb3ZlcmFsbC12ZXJtb250LWdyZWVuLW5hdnk=|"));
    }
    
    @Test
    public void testGetIdNegative() {
        assertNull(utils.getId("2-dspik  sxs2323 332323 9t26723"));
    }
    
    @Test
    public void testGetIdWords() {
        assertEquals("skdjhbk9", utils.getId("2-dspik  sxs2323 332323 [skdjhbk9]|bGluZGJlcmctb3ZlcmFsbC12ZXJtb250LWdyZWVuLW5hdnk=|"));
    }
    
    @Test
    public void testGetIdLekmer() {
        assertEquals("LE102-405-133 NAV", utils.getId("2-dspik  sxs2323 332323 [LE102-405-133 NAV]|bGluZGJlcmctb3ZlcmFsbC12ZXJtb250LWdyZWVuLW5hdnk=|"));
    }
    
    @Test
    public void testGetIdLekmerManySizes() {
        assertEquals("LE510097-6982", utils.getId("Зим. комбинезоны: Reima (размер 74, 80) цена 2440 руб. [LE510097-6982]|bGluZGJlcmctb3ZlcmFsbC12ZXJtb250LWdyZWVuLW5hdnk=|"));
    }
    
    @Test
    public void testGetIdReimaManySizes() {
    	assertEquals("RE510097-6982", utils.getId("Зим. комбинезоны: Reima (размер 74, 80) цена 2440 руб. [RE510097-6982]|bGluZGJlcmctb3ZlcmFsbC12ZXJtb250LWdyZWVuLW5hdnk=|"));
    }
    
    @Test
    public void testGetIdDigits() {
        final String descriptionId = "82372082372038972309782902790273237";
        assertEquals(descriptionId, utils.getId(String.format("[%s]", descriptionId)));
    }
    
    @Test
    public void testGetDecodedURL() {
    	assertEquals("lindberg-overall-vermont-green-navy", utils.getDecodedURL("Зим. комбинезоны: Reima (размер 74, 80) цена 2440 руб. [RE510097-6982]|DwEPCgUAGxNOBxcLFQQFGE4eBBwKCgcATg8TCwILRBoCHhg=|"));
    }
    
    @Test
    public void testEncodeURLHuuto() {
    	ListResponse listResponse = new ListResponse();
    	ItemResponse itemResponse = new ItemResponse();
    	listResponse.setItemResponse(itemResponse);
    	listResponse.setItemUrl("http://api.huuto.net/1.0/items/387773498");
    	
    	assertTrue(utils.encodeBase64(listResponse).isEmpty());
    }
    
    @Test
    public void testEncodeDecodeURL() {
    	ListResponse listResponse = new ListResponse();
    	ItemResponse itemResponse = new ItemResponse();
    	listResponse.setItemResponse(itemResponse);
    	final String url = "lindberg-overall-vermont-green-navy";
    	listResponse.setItemUrl(url);
    	
    	String base64 = utils.encodeBase64(listResponse);
    	
    	assertEquals(url, utils.decodeBase64(base64));
    }
    
    @Test
    public void testEncodeDecodeURL_UTF8() {
    	ListResponse listResponse = new ListResponse();
    	ItemResponse itemResponse = new ItemResponse();
    	listResponse.setItemResponse(itemResponse);
    	final String url = "geggamoja-tuulenpitävä-haalari-vauvan-cerise";
    	listResponse.setItemUrl(url);
    	
    	String base64 = utils.encodeBase64(listResponse);
    	
    	String decodeBase64 = utils.decodeBase64(base64);
    	assertEquals(url, decodeBase64);
    }
}
