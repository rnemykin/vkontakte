package org.springframework.social.vkontakte.api;

public interface PhotosOperations {

    UploadServer getUploadServer(String groupId, String albumId);
    
    UploadedPhoto uploadPhoto(String url, String photoFileLocation);
    
    VKGenericResponse savePhoto(UploadedPhoto uploadedPhoto, String description);
    
    String deletePhoto(String photoId, String ownerId);
}
