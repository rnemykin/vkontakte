package com.abudko.scheduled.service.huuto.clean;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abudko.scheduled.rules.ItemValidityRules;
import com.abudko.scheduled.vkontakte.Photo;

@Component
public class ItemValidityRulesAlbumCleaner extends AbstractAlbumCleaner {
    
    @Autowired
    private List<ItemValidityRules> itemValidityRules;

    protected boolean isValid(Photo photo) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, -21);
        if (photo.getCreated().before(now)) {
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
