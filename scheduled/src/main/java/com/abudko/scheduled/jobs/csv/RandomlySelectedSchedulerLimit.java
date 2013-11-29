package com.abudko.scheduled.jobs.csv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import com.abudko.scheduled.jobs.Scheduler;
import com.abudko.scheduled.service.PhotoManager;

public class RandomlySelectedSchedulerLimit implements Scheduler {

    private Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    @Qualifier("singleLimitedRandomSelectedGroupPhotoManager")
    private PhotoManager photoManager;
    
    @Value("#{scheduledProperties['randomlySelectedLimitCsvFile']}")
    private String csvResourcePath;
    
    @Value("#{scheduledProperties['randomlySelectedLimitDumpFile']}")
    private String dumpFileLocation;
    
    public void schedule() {
        log.info("********* Start RandomlySelectedSchedulerLimit *******");
        try {

            photoManager.publish(csvResourcePath, dumpFileLocation, null);
            
            log.info("********* End RandomlySelectedSchedulerLimit *******");

        } catch (Exception e) {
            log.error("Exception happened during scheduled scan: ", e);
        }
    }
}
