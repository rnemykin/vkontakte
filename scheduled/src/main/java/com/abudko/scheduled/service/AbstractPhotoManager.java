package com.abudko.scheduled.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.abudko.scheduled.csv.PhotoData;
import com.abudko.scheduled.csv.PhotoDataLogger;
import com.abudko.scheduled.csv.PhotoDataReader;
import com.abudko.scheduled.vkontakte.Photo;
import com.abudko.scheduled.vkontakte.PhotosTemplate;
import com.abudko.scheduled.vkontakte.SavedPhoto;
import com.abudko.scheduled.vkontakte.UploadedPhoto;

public abstract class AbstractPhotoManager implements PhotoManager {

    int sleepInterval = 1000;

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected PhotoDataReader photoDataReader;

    @Autowired
    protected PhotoDataLogger photoDataLogger;

    @Autowired
    protected PhotosTemplate photosTemplate;

    @Override
    public void publish(String csvResourcePath, String dumpFileLocation, String token) throws InterruptedException {
        if (token != null) {
            photosTemplate.setToken(token);
        }

        deleteAll(dumpFileLocation);

        Map<String, String> photoIdGroupIdMap = new HashMap<String, String>();
        try {
            publishAll(csvResourcePath, photoIdGroupIdMap);
        } finally {
            if (photoIdGroupIdMap.isEmpty() == false) {
                photoDataLogger.dump(photoIdGroupIdMap, dumpFileLocation);
            }
        }
    }

    protected void publishAll(String csvResourcePath, Map<String, String> photoIdGroupIdMap)
            throws InterruptedException {
        List<PhotoData> photoDataList = photoDataReader.read(csvResourcePath);

        for (PhotoData photoData : photoDataList) {
            SavedPhoto savedPhoto = publishPhoto(photoData);

            if (savedPhoto != null && savedPhoto.getPhotoId() != null && savedPhoto.getOwnerId() != null) {
                log.info(String.format("Saved photo id '%s'", savedPhoto));
                photoIdGroupIdMap.put(savedPhoto.getPhotoId(), savedPhoto.getOwnerId());
            }
        }
    }

    public void deleteAll(String dumpFileLocation) throws InterruptedException {
        Map<String, String> map = photoDataLogger.read(dumpFileLocation);
        Set<Entry<String, String>> entrySet = map.entrySet();

        for (Entry<String, String> entry : entrySet) {
            deletePhoto(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void deletePhoto(String photoId, String groupId) throws InterruptedException {
        String ownerId = getOwnerId(groupId);
        int comments = photosTemplate.getCommentsCount(photoId, ownerId);

        log.info(String.format("Deleting a photo id['%s'],  group['%s'], comments '%d'", photoId, groupId, comments));

        if (comments == 0) {
            Thread.sleep(sleepInterval);
            photosTemplate.deletePhoto(photoId, ownerId);
        } else {
            log.info(String.format("Can't delete a photo id['%s'],  group['%s'] because it has comments", photoId,
                    groupId));
        }
    }

    @Override
    public void deletePhotoForce(String photoId, String groupId) throws InterruptedException {
        String ownerId = getOwnerId(groupId);

        log.info(String.format("Deleting a photo id['%s'],  group['%s']", photoId, groupId));

        photosTemplate.deletePhoto(photoId, ownerId);

    }

    @Override
    public SavedPhoto publishPhoto(PhotoData photoData) throws InterruptedException {
        try {
            log.info(String.format("Publishing photo '%s'", photoData));

            String uploadUrl = photosTemplate.getUploadServer(photoData.getGroupId(), photoData.getAlbumId());

            log.info(String.format("Got upload URL '%s'", uploadUrl));

            UploadedPhoto uploadedPhoto = photosTemplate.uploadPhoto(uploadUrl, photoData.getFileResource());

            log.info(String.format("Got uploadedPhoto '%s'", uploadedPhoto));

            Thread.sleep(sleepInterval);

            SavedPhoto savedPhoto = photosTemplate.savePhoto(uploadedPhoto, photoData.getDescription());

            return savedPhoto;
        } catch (Throwable e) {
            log.error(String.format("Exception happened while publishing a photo '%s'", photoData), e);
        }

        return null;
    }

    @Override
    public List<Photo> getPhotos(String ownerId, String albumId) throws InterruptedException {
        log.info(String.format("Getting photos: groupid '%s', albumid '%s'", ownerId, albumId));

        Thread.sleep(sleepInterval);
        List<Photo> photos = photosTemplate.getPhotos(getOwnerId(ownerId), albumId);

        return photos;
    }

    @Override
    public List<String> getAlbumIds(String groupId) {
        log.info(String.format("Getting albums: groupid '%s'", groupId));

        List<String> albumIds = photosTemplate.getAlbumIds(getOwnerId(groupId));

        return albumIds;
    }

    protected String getOwnerId(String id) {
        if (id.charAt(0) == '-') {
            return id;
        }
        return "-" + id;
    }
}
