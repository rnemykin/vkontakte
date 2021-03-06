package com.abudko.scheduled.service;

import java.util.ArrayList;
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
    public void publish(String csvResourcePath, String dumpFileLocation) throws InterruptedException {
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
    
    public void cleanAll(String csvResourcePath, String userId) throws InterruptedException {
        List<String> processed = new ArrayList<>(); 
        
        List<PhotoData> photoDataList = photoDataReader.read(csvResourcePath);
        for (PhotoData photoData : photoDataList) {
            String groupId = photoData.getGroupId();
            String albumId = photoData.getAlbumId();
            
            if (processed.contains(groupId + " " + albumId)) {
                continue;
            }
            
            try {
                List<Photo> photos = this.getPhotos(groupId, albumId);

                for (Photo photo : photos) {
                    if (userId.equals(getOwnerId(photo.getUserId()))) {
                        this.deletePhoto(photo, groupId, albumId);
                    }
                }

                processed.add(groupId + " " + albumId);
            } catch (Exception e) {
                log.error(String.format("Can't clean photos in groupid [%s], albumid [%s]", groupId, albumId), e);
            }
        }
    }

    public void deleteAll(String dumpFileLocation) throws InterruptedException {
        Map<String, String> map = photoDataLogger.read(dumpFileLocation);
        Set<Entry<String, String>> entrySet = map.entrySet();

        for (Entry<String, String> entry : entrySet) {
        	Photo photo = new Photo();
        	String photoId = entry.getKey();
        	photo.setPhotoId(photoId);
            deletePhoto(photo, entry.getValue(), null);
        }
    }

    @Override
    public void deletePhoto(Photo photo, String groupId, String albumId) throws InterruptedException {
    	String photoId = photo.getPhotoId();
        String ownerId = getOwnerId(groupId);
        int comments = photosTemplate.getCommentsCount(photoId, ownerId);
        
        log.info(String.format("Deleting a photo ['%s'],  group['%s'], comments '%d', albumId '%s'", photo, groupId, comments, albumId));

        if (comments == 0 || (albumId != null && !photo.wasPhotoCreatedAfter(100))) {
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

        Thread.sleep(sleepInterval);
        
        photosTemplate.deletePhoto(photoId, ownerId);

    }

    @Override
    public SavedPhoto publishPhoto(PhotoData photoData) throws InterruptedException {
        try {
            log.info(String.format("Publishing photo '%s'", photoData));

            Thread.sleep(sleepInterval);
            
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
        List<Photo> photos = new ArrayList<>();
        
        int n = 4;
        int max_amount = 1000;
        int offset = 0;
        
        for (int i = 0; i < n; i++) {
            List<Photo> photosInternal = getPhotosInternal(ownerId, albumId, offset); 
            photos.addAll(photosInternal);
            offset += max_amount;
        }
        
        return photos;
    }

    private List<Photo> getPhotosInternal(String ownerId, String albumId, int offset) throws InterruptedException {
        log.info(String.format("Getting photos: groupid '%s', albumid '%s'", ownerId, albumId));

        Thread.sleep(sleepInterval);
        List<Photo> photos = photosTemplate.getPhotos(getOwnerId(ownerId), albumId, offset);

        return photos;
    }

    @Override
    public List<String> getAlbumIds(String groupId) {
        log.info(String.format("Getting albums: groupid '%s'", groupId));

        List<String> albumIds = photosTemplate.getAlbumIds(getOwnerId(groupId));

        return albumIds;
    }

    String getOwnerId(String id) {
        if (id.startsWith("user-")) {
            return id.substring(5);
        }
        if (id.charAt(0) == '-') {
            return id;
        }
        return "-" + id;
    }

    public PhotosTemplate getPhotosTemplate() {
        return photosTemplate;
    }
}
