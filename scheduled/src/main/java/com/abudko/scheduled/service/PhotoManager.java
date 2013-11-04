package com.abudko.scheduled.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abudko.scheduled.csv.PhotoData;
import com.abudko.scheduled.csv.PhotoDataReader;
import com.abudko.scheduled.vkontakte.PhotosTemplate;
import com.abudko.scheduled.vkontakte.UploadedPhoto;

@Component
public class PhotoManager {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PhotoDataReader photoDataReader;

    @Autowired
    private PhotosTemplate photosTemplate;

    public void publish() {
        List<PhotoData> photoDataList = photoDataReader.read();

        for (PhotoData photoData : photoDataList) {
            log.info(String.format("Publishing photo '%s'", photoData));

            String uploadUrl = photosTemplate.getUploadServer(photoData.getGroupId(), photoData.getAlbumId());

            log.info(String.format("Got upload URL '%s'", uploadUrl));

            UploadedPhoto uploadedPhoto = photosTemplate.uploadPhoto(uploadUrl,
                    "src/main/resources/" + photoData.getFileLocation());

            log.info(String.format("Got uploadedPhoto '%s'", uploadedPhoto));

            String photoId = photosTemplate.savePhoto(uploadedPhoto, photoData.getDescription());

            log.info(String.format("Saved photo id '%s'", photoId));
        }
    }
}
