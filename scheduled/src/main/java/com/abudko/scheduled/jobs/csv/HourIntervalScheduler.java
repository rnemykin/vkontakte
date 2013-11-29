package com.abudko.scheduled.jobs.csv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import com.abudko.scheduled.jobs.Scheduler;
import com.abudko.scheduled.service.PhotoManager;

public class HourIntervalScheduler implements Scheduler {

    private Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    @Qualifier("randomSelectedGroupPhotoManager")
    private PhotoManager photoManager;
    
    @Value("#{scheduledProperties['hourIntervalCsvFile']}")
    private String csvResourcePath;
    
    @Value("#{scheduledProperties['hourIntervalDumpFile']}")
    private String dumpFileLocation;

    public void schedule() {
        log.info("********* Start HourIntervalScheduler *******");
        try {

            photoManager.publish(csvResourcePath, dumpFileLocation, null);
            
            log.info("********* End HourIntervalScheduler *******");

        } catch (Exception e) {
            log.error("Exception happened during scheduled scan: ", e);
        }
    }
}
