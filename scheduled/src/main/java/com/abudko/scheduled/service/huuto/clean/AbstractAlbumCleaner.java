package com.abudko.scheduled.service.huuto.clean;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.abudko.scheduled.service.PhotoManager;
import com.abudko.scheduled.service.huuto.AlbumMapper;
import com.abudko.scheduled.service.huuto.PublishManagerUtils;
import com.abudko.scheduled.vkontakte.Photo;

public abstract class AbstractAlbumCleaner {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("groupPhotoManager")
    private PhotoManager photoManager;
    
    @Autowired
    protected PublishManagerUtils publishManagerUtils;
    
    private String groupId = AlbumMapper.GROUP_ID;

    public void clean() {
        log.info("********* Start CleanScheduler *******");
        try {
            List<String> albumIds = photoManager.getAlbumIds(groupId);
            for (String albumId : albumIds) {
                List<Photo> photos = photoManager.getPhotos(groupId, albumId);
                Thread.sleep(1000);
                for (Photo photo : photos) {
                    try {
                        if (!isValid(photo)) {
                            String info = String.format("Photo ['%s'] is not valid", photo.getDescription());
                            log.info(info);

                            photoManager.deletePhoto(photo.getPhotoId(), groupId, albumId);
                            Thread.sleep(1000);
                        } else {
                            String info = String.format("Photo is VALID: '%s'", photo.getDescription());
                            log.info(info);
                        }
                    } catch (Exception e) {
                        log.error(String.format("Exception happened during cleaning photo '%s' in album '%s'", photo,
                                albumId), e);
                    }
                }
            }

            log.info("********* End CleanScheduler *******");

        } catch (Exception e) {
            log.error("Exception happened during CleanScheduler: ", e);
            throw new RuntimeException(e);
        }
    }

    protected abstract boolean isValid(Photo photo);
    
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
