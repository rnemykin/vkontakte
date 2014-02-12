package com.abudko.scheduled.jobs.huuto;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.abudko.scheduled.jobs.Scheduler;
import com.abudko.scheduled.rules.ItemValidityRules;
import com.abudko.scheduled.service.PhotoManager;
import com.abudko.scheduled.service.huuto.AlbumMapper;
import com.abudko.scheduled.service.huuto.PublishManagerUtils;
import com.abudko.scheduled.vkontakte.Photo;

public class CleanScheduler implements Scheduler {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    @Qualifier("groupPhotoManager")
    private PhotoManager photoManager;

    @Autowired
    private PublishManagerUtils publishManagerUtils;

    @Autowired
    private List<ItemValidityRules> itemValidityRules;

    public void schedule() {
        log.info("********* Start CleanScheduler *******");
        try {
            List<String> albumIds = photoManager.getAlbumIds(AlbumMapper.GROUP_ID);
            for (String albumId : albumIds) {
                List<Photo> photos = photoManager.getPhotos(AlbumMapper.GROUP_ID, albumId);
                Thread.sleep(1000);
                for (Photo photo : photos) {
                    String id = publishManagerUtils.getId(photo.getDescription());

                    if (isValid(id) == false) {
                        String info = String.format("Photo ['%s'] is not valid", photo.getDescription());
                        log.info(info);

                        photoManager.deletePhotoForce(photo.getPhotoId(), AlbumMapper.GROUP_ID);
                        Thread.sleep(1000);
                    } else {
                        String info = String.format("Photo is VALID: '%s'", photo.getDescription());
                        log.info(info);
                    }
                }
            }

            log.info("********* End CleanScheduler *******");

        } catch (Exception e) {
            log.error("Exception happened during CleanScheduler: ", e);
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
