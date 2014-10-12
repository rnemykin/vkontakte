package com.abudko.scheduled.jobs.csv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import com.abudko.scheduled.jobs.Scheduler;
import com.abudko.scheduled.service.PhotoManager;

public class DayIntervalScheduler implements Scheduler {

    private Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    @Qualifier("personPhotoManager")
    private PhotoManager photoManager;
    
    @Value("#{scheduledProperties['dayIntervalCsvFile']}")
    private String csvResourcePath;
    
    @Value("#{scheduledProperties['dayIntervalDumpFile']}")
    private String dumpFileLocation;

    public void schedule() {
        log.info("********* Start DayIntervalScheduler *******");
        try {

            photoManager.publish(csvResourcePath, dumpFileLocation);
            
            log.info("********* End DayIntervalScheduler *******");

        } catch (Exception e) {
            log.error("Exception happened during scheduled scan: ", e);
        }
    }
}
