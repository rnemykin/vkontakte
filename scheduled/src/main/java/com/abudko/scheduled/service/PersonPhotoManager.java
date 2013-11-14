package com.abudko.scheduled.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.abudko.scheduled.csv.PhotoData;
import com.abudko.scheduled.vkontakte.SavedPhoto;

@Component
public class PersonPhotoManager extends AbstractPhotoManager implements PhotoManager {

    public void publish(String csvResourcePath, String dumpFileLocation) throws InterruptedException {
        deleteAll(dumpFileLocation);

        List<PhotoData> photoDataList = photoDataReader.read(csvResourcePath);
        Map<String, String> photoIdGroupIdMap = new HashMap<String, String>();

        try {
            for (PhotoData photoData : photoDataList) {
                SavedPhoto savedPhoto = publishPhoto(photoData);

                log.info(String.format("Saved photo id '%s'", savedPhoto));

                photoIdGroupIdMap.put(savedPhoto.getPhotoId(), savedPhoto.getOwnerId());
            }
        } catch (Throwable e) {
            log.error("Exception happened while publishing a photo", e);
            throw e;
        } finally {
            photoDataLogger.dump(photoIdGroupIdMap, dumpFileLocation);
        }
    }
    
    protected String getOwnerId(String id) {
        return id;
    }
}
