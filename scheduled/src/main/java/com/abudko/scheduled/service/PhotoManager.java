package com.abudko.scheduled.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abudko.scheduled.csv.PhotoData;
import com.abudko.scheduled.csv.PhotoDataLogger;
import com.abudko.scheduled.csv.PhotoDataReader;
import com.abudko.scheduled.vkontakte.PhotosTemplate;
import com.abudko.scheduled.vkontakte.UploadedPhoto;

@Component
public class PhotoManager {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PhotoDataReader photoDataReader;

    @Autowired
    private PhotoDataLogger photoDataLogger;

    @Autowired
    private PhotosTemplate photosTemplate;

    public void publish() throws InterruptedException {
        deleteAll();

        List<PhotoData> photoDataList = photoDataReader.read();
        Map<String, String> photoIdGroupIdMap = new HashMap<String, String>();

        try {
            for (PhotoData photoData : photoDataList) {
                log.info(String.format("Publishing photo '%s'", photoData));

                String uploadUrl = photosTemplate.getUploadServer(photoData.getGroupId(), photoData.getAlbumId());

                log.info(String.format("Got upload URL '%s'", uploadUrl));

                UploadedPhoto uploadedPhoto = photosTemplate.uploadPhoto(uploadUrl,
                        "/photos/" + photoData.getFileLocation());

                log.info(String.format("Got uploadedPhoto '%s'", uploadedPhoto));

                Thread.sleep(1000);
                
                String photoId = photosTemplate.savePhoto(uploadedPhoto, photoData.getDescription());

                log.info(String.format("Saved photo id '%s'", photoId));

                photoIdGroupIdMap.put(photoId, photoData.getGroupId());
            }
        } catch (Throwable e) {
            log.error("Exception happened while publishing a photo", e);
            throw e;
        } finally {
            photoDataLogger.dump(photoIdGroupIdMap);
        }
    }

    private void deleteAll() throws InterruptedException {
        Map<String, String> map = photoDataLogger.read();
        Set<Entry<String, String>> entrySet = map.entrySet();

        for (Entry<String, String> entry : entrySet) {
            String photoId = entry.getKey();
            String groupId = entry.getValue();

            int comments = photosTemplate.getCommentsCount(photoId, "-" + groupId);

            log.info(String
                    .format("Deleting a photo id['%s'],  group['%s'], comments '%d'", photoId, groupId, comments));

            if (comments == 0) {
                Thread.sleep(1000);
                photosTemplate.deletePhoto(photoId, "-" + groupId);
            } else {
                log.info(String.format("Can't delete a photo id['%s'],  group['%s'] because it has comments", photoId,
                        groupId));
            }
        }
    }
}
