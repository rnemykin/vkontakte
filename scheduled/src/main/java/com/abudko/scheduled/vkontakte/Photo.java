package com.abudko.scheduled.vkontakte;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Photo {
    
    private static SimpleDateFormat format = new SimpleDateFormat();
    
    private String photoId;

    private String description;
    
    private String userId;
    
    private Calendar created;    

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Calendar getCreated() {
        return created;
    }
    
    public void setCreated(Calendar created) {
        this.created = created;
    }
    
    public boolean wasPhotoCreatedAfter(int amountOfDaysBefore) {
    	if (this.getCreated() == null) {
    		return true;
    	}
    	
    	Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, -amountOfDaysBefore);
        if (this.getCreated().before(now)) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "Photo [photoId=" + photoId + ", description=" + description + ", userId=" + userId + ", created="
                + format.format(created.getTime()) + "]";
    }
}
