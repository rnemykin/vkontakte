package com.abudko.scheduled.vkontakte;

import org.springframework.core.io.Resource;

public interface PhotosOperations {

    String getUploadServer(String groupId, String albumId);
    
    UploadedPhoto uploadPhoto(String url, Resource photoFileResource);
    
    SavedPhoto savePhoto(UploadedPhoto uploadedPhoto, String description);
    
    String deletePhoto(String photoId, String ownerId);
    
    int getCommentsCount(String photoId, String ownerId);
}
