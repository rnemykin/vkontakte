package com.abudko.scheduled.vkontakte;

import java.util.Calendar;
import java.util.List;

import org.springframework.core.io.Resource;

public interface PhotosOperations {

    String getUploadServer(String groupId, String albumId);
    
    UploadedPhoto uploadPhoto(String url, Resource photoFileResource);
    
    SavedPhoto savePhoto(UploadedPhoto uploadedPhoto, String description);
    
    String deletePhoto(String photoId, String ownerId);
    
    int getCommentsCount(String photoId, String ownerId);
    
    Calendar getCreated(String photoId, String ownerId);
    
    List<Photo> getPhotos(String ownerId, String albumId, int offset);
    
    List<String> getAlbumIds(String ownerId);
}
