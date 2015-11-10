package com.abudko.scheduled.service;

import java.util.List;

import com.abudko.scheduled.csv.PhotoData;
import com.abudko.scheduled.vkontakte.Photo;
import com.abudko.scheduled.vkontakte.SavedPhoto;

public interface PhotoManager {
    
    void cleanAll(String csvResourcePath, String userId) throws InterruptedException;
    
    void deleteAll(String dumpFileLocation) throws InterruptedException;

    void publish(String csvResourcePath, String dumpFileLocation) throws InterruptedException;
    
    SavedPhoto publishPhoto(PhotoData photoData) throws InterruptedException;
    
    List<Photo> getPhotos(String groupId, String albumId) throws InterruptedException;
    
    List<String> getAlbumIds(String groupId);
    
    void deletePhoto(Photo photo, String groupId, String albumId) throws InterruptedException;
    
    void deletePhotoForce(String photoId, String groupId) throws InterruptedException;

}