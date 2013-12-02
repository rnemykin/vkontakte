package com.abudko.scheduled.vkontakte;

public class Photo {
    
    private String photoId;

    private String description;

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Photo [photoId=" + photoId + ", description=" + description + "]";
    }
}
