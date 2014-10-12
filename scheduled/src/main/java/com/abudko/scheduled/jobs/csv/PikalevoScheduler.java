package com.abudko.scheduled.jobs.csv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import com.abudko.scheduled.jobs.Scheduler;
import com.abudko.scheduled.service.PhotoManager;

public class PikalevoScheduler implements Scheduler {

    private Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    @Qualifier("allRandomSelectedGroupPhotoManager")
    private PhotoManager photoManager;
    
    @Value("#{scheduledProperties['pikalevoCsvFile']}")
    private String csvResourcePath;
    
    @Value("#{scheduledProperties['pikalevoDumpFile']}")
    private String dumpFileLocation;

    public void schedule() {
        log.info("********* Start PikalevoScheduler *******");
        try {

            photoManager.publish(csvResourcePath, dumpFileLocation);
            
            log.info("********* End PikalevoScheduler *******");

        } catch (Exception e) {
            log.error("Exception happened during scheduled Pikalevo scan: ", e);
        }
    }
}
