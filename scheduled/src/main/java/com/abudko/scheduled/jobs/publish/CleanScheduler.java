package com.abudko.scheduled.jobs.publish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.abudko.scheduled.jobs.Scheduler;
import com.abudko.scheduled.service.huuto.clean.ItemValidityRulesAlbumCleaner;
import com.abudko.scheduled.service.huuto.clean.PhotoTypeKeywordAlbumCleaner;

public class CleanScheduler implements Scheduler {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected ItemValidityRulesAlbumCleaner itemValidityRulesAlbumCleaner;
    
    @Autowired
    protected PhotoTypeKeywordAlbumCleaner photoTypeKeywordAlbumCleaner;

    public void schedule() {
        try {
            log.info("********* Start CleanScheduler *******");

            itemValidityRulesAlbumCleaner.clean();

            log.info("********* End CleanScheduler *******");

        } catch (Exception e) {
            log.error("Exception happened during CleanScheduler: ", e);
            throw new RuntimeException(e);
        }
    }
    
    public void clean(String photoIdTypeStartPrefix, String keyword) {
        photoTypeKeywordAlbumCleaner.setKeyword(keyword);
        photoTypeKeywordAlbumCleaner.setPhotoIdTypeStartPrefix(photoIdTypeStartPrefix);
        
        photoTypeKeywordAlbumCleaner.clean();
    }
}
