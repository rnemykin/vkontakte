package com.abudko.scheduled.service.huuto.clean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abudko.scheduled.rules.AbstractItemValidityRules;
import com.abudko.scheduled.rules.ItemValidityRules;
import com.abudko.scheduled.vkontakte.Photo;

@Component
@Scope(value = "prototype")
public class ItemValidityRulesAlbumCleaner extends AbstractAlbumCleaner {
    
    @Autowired
    private List<ItemValidityRules> itemValidityRules;

    protected boolean isValid(Photo photo) {
        if (!photo.wasPhotoCreatedAfter(21)) {
            return false;
        }
        
        String description = photo.getDescription();
        String id = publishManagerUtils.getId(description);
        String urlKeyword = publishManagerUtils.getDecodedURL(description);
        if (urlKeyword != null && urlKeyword.contains("/")) {
        	int lastIndexOf = urlKeyword.lastIndexOf("/");
        	urlKeyword = urlKeyword.substring(lastIndexOf+1, urlKeyword.length());
        }
        boolean valid = true;
        for (ItemValidityRules rule : itemValidityRules) {
            if (!rule.isValid(id)) {
            	String idPrefix = ((AbstractItemValidityRules) rule).getIdPrefix();
            	if (idPrefix != null) {
            		urlKeyword = idPrefix + urlKeyword;	
            	}
            	
            	if (urlKeyword != null && !urlKeyword.isEmpty()) {
            		if (!rule.isValid(urlKeyword)) {
            			String substring = urlKeyword.substring(0, urlKeyword.length() - 1);
            			if (!rule.isValid(substring)) {	
            				valid = false;
            			}
            		}
            	}
            }
        }
        
        return valid;
    }
}
