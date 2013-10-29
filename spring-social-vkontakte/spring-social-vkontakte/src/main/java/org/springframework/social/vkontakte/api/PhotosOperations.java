package org.springframework.social.vkontakte.api;

public interface PhotosOperations {

    UploadServer getUploadServer();
    
    UploadedPhoto uploadPhoto(String url, String photoFileLocation);
    
    String savePhoto(UploadedPhoto uploadedPhoto, String description);
    
    String deletePhoto(String photoId, String ownerId);
}
