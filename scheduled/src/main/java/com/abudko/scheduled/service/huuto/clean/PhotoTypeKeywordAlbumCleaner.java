package com.abudko.scheduled.service.huuto.clean;

import org.springframework.stereotype.Component;

import com.abudko.scheduled.vkontakte.Photo;

@Component
public class PhotoTypeKeywordAlbumCleaner extends AbstractAlbumCleaner {
    
    private String photoIdTypeStartPrefix;
    
    private String keyword;

    protected boolean isValid(Photo photo) {
        String description = photo.getDescription();
        
        String id = publishManagerUtils.getId(description);
        
        if (photoIdTypeStartPrefix != null && id.startsWith(photoIdTypeStartPrefix)) {
            if (description != null && description.contains(keyword)) {
                return false;
            }
        }
        
        return true;
    }

    public String getPhotoIdTypeStartPrefix() {
        return photoIdTypeStartPrefix;
    }

    public void setPhotoIdTypeStartPrefix(String photoIdTypeStartPrefix) {
        this.photoIdTypeStartPrefix = photoIdTypeStartPrefix;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
