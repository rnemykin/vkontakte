package com.abudko.scheduled.jobs.publish;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import com.abudko.scheduled.jobs.Scheduler;
import com.abudko.scheduled.rules.ItemValidityRules;
import com.abudko.scheduled.service.AbstractPhotoManager;
import com.abudko.scheduled.service.PhotoManager;
import com.abudko.scheduled.service.huuto.AlbumMapper2;
import com.abudko.scheduled.service.huuto.PublishManagerUtils;
import com.abudko.scheduled.vkontakte.Photo;

public class Clean2Scheduler implements Scheduler {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("groupPhotoManager")
    private PhotoManager photoManager;

    @Autowired
    private PublishManagerUtils publishManagerUtils;

    @Autowired
    private List<ItemValidityRules> itemValidityRules;
    
    @Value("#{tokenCustomProperties['customToken']}")
    private String customToken;

    public void schedule() {
        log.info("********* Start Clean2Scheduler *******");
        try {
            ((AbstractPhotoManager) photoManager).getPhotosTemplate().setToken(customToken);
            
            List<String> albumIds = photoManager.getAlbumIds(AlbumMapper2.GROUP_ID);
            for (String albumId : albumIds) {
                List<Photo> photos = photoManager.getPhotos(AlbumMapper2.GROUP_ID, albumId);
                Thread.sleep(1000);
                for (Photo photo : photos) {
                    try {
                        String id = publishManagerUtils.getId(photo.getDescription());

                        if (!isValid(id)) {
                            String info = String.format("Photo ['%s'] is not valid", photo.getDescription());
                            log.info(info);

                            photoManager.deletePhotoForce(photo.getPhotoId(), AlbumMapper2.GROUP_ID);
                            Thread.sleep(1000);
                        } else {
                            String info = String.format("Photo is VALID: '%s'", photo.getDescription());
                            log.info(info);
                        }
                    } catch (Exception e) {
                        log.error(String.format("Exception happened during cleaning photo '%s' in album '%s'", photo,
                                albumId), e);
                        throw e;
                    }
                }
            }

            log.info("********* End Clean2Scheduler *******");

        } catch (Exception e) {
            log.error("Exception happened during Clean2Scheduler: ", e);
            throw new RuntimeException(e);
        }
    }

    private boolean isValid(String id) {
        boolean valid = true;
        for (ItemValidityRules rule : itemValidityRules) {
            if (!rule.isValid(id)) {
                valid = false;
            }
        }

        return valid;
    }
}
