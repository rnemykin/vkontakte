package com.abudko.scheduled.service.huuto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

@Component
public class PublishManagerUtils {

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
    	if (itemUrl == null) {
    		return null;
    	}
    	String base64String = Base64.encodeBase64String(itemUrl.getBytes());
    	return base64String;
    }
    
    private String decodeBase64(String encodeURL) {
    	byte[] decodeBase64 = Base64.decodeBase64(encodeURL);
    	return new String(decodeBase64);
    }
}
