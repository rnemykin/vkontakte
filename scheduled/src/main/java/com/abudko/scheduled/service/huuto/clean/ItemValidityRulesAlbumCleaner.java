package com.abudko.scheduled.service.huuto.clean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
        String id = publishManagerUtils.getId(photo.getDescription());
        boolean valid = true;
        for (ItemValidityRules rule : itemValidityRules) {
            if (!rule.isValid(id)) {
                valid = false;
            }
        }

        return valid;
    }
}
