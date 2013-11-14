package com.abudko.scheduled.vkontakte;

public class SavedPhoto {
    
    private String photoId;
    
    private String ownerId;

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "SavedPhoto [photoId=" + photoId + ", ownerId=" + ownerId + "]";
    }
}
