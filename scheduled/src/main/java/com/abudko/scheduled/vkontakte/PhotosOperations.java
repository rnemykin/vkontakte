package com.abudko.scheduled.vkontakte;

public interface PhotosOperations {

    String getUploadServer(String groupId, String albumId);
    
    UploadedPhoto uploadPhoto(String url, String photoFileLocation);
    
    String savePhoto(UploadedPhoto uploadedPhoto, String description);
    
    String deletePhoto(String photoId, String ownerId);
}
