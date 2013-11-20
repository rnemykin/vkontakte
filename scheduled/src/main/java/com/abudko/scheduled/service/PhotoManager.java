package com.abudko.scheduled.service;

import com.abudko.scheduled.csv.PhotoData;
import com.abudko.scheduled.vkontakte.SavedPhoto;

public interface PhotoManager {

    void publish(String csvResourcePath, String dumpFileLocation, String token) throws InterruptedException;
    
    SavedPhoto publishPhoto(PhotoData photoData) throws InterruptedException;

}