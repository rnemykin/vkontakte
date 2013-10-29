package org.springframework.social.vkontakte.api;

public interface PhotosOperations {

    UploadServer getUploadServer();
    
    void uploadPhoto(String url, String photoFileLocation);
}
