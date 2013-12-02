package com.abudko.scheduled.service;

import java.util.List;

import com.abudko.scheduled.csv.PhotoData;
import com.abudko.scheduled.vkontakte.Photo;
import com.abudko.scheduled.vkontakte.SavedPhoto;

public interface PhotoManager {

    void publish(String csvResourcePath, String dumpFileLocation, String token) throws InterruptedException;
    
    SavedPhoto publishPhoto(PhotoData photoData) throws InterruptedException;
    
    List<Photo> getPhotos(String groupId, String albumId);
    
    void deletePhoto(String photoId, String groupId) throws InterruptedException;

}