package com.abudko.scheduled.jobs.csv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.abudko.scheduled.jobs.Scheduler;
import com.abudko.scheduled.service.PhotoManager;

public class DayIntervalScheduler implements Scheduler {

    private Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private PhotoManager photoManager;
    
    @Value("#{scheduledProperties['dayIntervalCsvFile']}")
    private String csvResourcePath;
    
    @Value("#{scheduledProperties['dayIntervalDumpFile']}")
    private String dumpFileLocation;

    public void schedule() {
        log.info("********* Start scheduled scanning *******");
        try {

            photoManager.publish(csvResourcePath, dumpFileLocation, false);
            
            log.info("********* End scheduled scanning *******");

        } catch (Exception e) {
            log.error("Exception happened during scheduled scan: ", e);
        }
    }
}