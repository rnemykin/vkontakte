package com.abudko.scheduled.service.huuto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

@Component
public class PublishManagerUtils {
	
	private static final String KEY = "changeit";

    public String getId(String description) {
        Pattern pattern = Pattern.compile("\\[(.+)\\]");
        Matcher matcher = pattern.matcher(description);
        while (matcher.find()) {
            return matcher.group(1);
        }
        
        return null;
    }
    
    public String getDecodedURL(String description) {
    	Pattern pattern = Pattern.compile("\\|(.+)\\|");
    	Matcher matcher = pattern.matcher(description);
    	while (matcher.find()) {
    		String encodedURL = matcher.group(1);
    		return decodeBase64(encodedURL);
    	}
    	
    	return null;
    }
    
    public String encodeBase64(ListResponse listResponse) {
    	ItemResponse itemResponse = listResponse.getItemResponse();
    	String itemUrl = itemResponse.getItemUrl() != null ? itemResponse.getItemUrl() : listResponse.getItemUrl();
    	if (itemUrl == null || itemUrl.contains("huuto.net")) {
    		return "";
    	}
    	
    	String output = xorWithKey(itemUrl, KEY);
    	
    	String base64String = Base64.encodeBase64String(output.getBytes());
    	return base64String;
    }
    
    private String xorWithKey(final String s, final String key) {
    	String output = "";
    	for (int i = 0, len = s.length(); i < len; i++) {
    		char c = s.charAt(i);
    		char cKey = key.charAt(i % key.length());
    		char xor = (char)(c ^ cKey);
    		output += xor;
    	}
    	
    	return output;
    }
    
    public String decodeBase64(String encodeURL) {
    	byte[] decodeBase64 = Base64.decodeBase64(encodeURL);
    	String s = new String(decodeBase64);
    	
    	return xorWithKey(s, KEY);
    }
}
