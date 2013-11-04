package com.abudko.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.abudko.scheduled.service.PhotoManager;

public class ScanScheduler {

    private Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private PhotoManager photoManager;

    public void scheduledScan() {
        log.info("********* Start scheduled scanning *******");
        try {

            photoManager.publish();
            
            log.info("********* End scheduled scanning *******");

        } catch (Exception e) {
            log.error("Exception happened during scheduled scan: ", e);
        }
    }
}
