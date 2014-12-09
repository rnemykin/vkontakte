package com.abudko.scheduled.jobs.csv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import com.abudko.scheduled.jobs.Scheduler;
import com.abudko.scheduled.service.PhotoManager;

public class CleanAllScheduler implements Scheduler {

    private Logger log = LoggerFactory.getLogger(getClass());
    
    @Value("#{userProperties['userid']}")
    private String userid;

    @Value("#{scheduledProperties['hourIntervalCsvFile']}")
    private String csvResourcePathHour;
    
    @Value("#{scheduledProperties['randomlySelectedCsvFile']}")
    private String csvResourcePathRandomly;
    
    @Value("#{scheduledProperties['randomlySelectedLimitCsvFile']}")
    private String csvResourcePathRandomlyLimit;
    
    @Value("#{scheduledProperties['pikalevoCsvFile']}")
    private String csvResourcePathPikalevo;
    
    @Autowired
    @Qualifier("randomSelectedGroupPhotoManager")
    private PhotoManager photoManager;

    public void schedule() {
        log.info("********* Start CleanAllScheduler *******");
        try {
            
            photoManager.cleanAll(csvResourcePathHour, userid);
            photoManager.cleanAll(csvResourcePathRandomly, userid);
//            photoManager.cleanAll(csvResourcePathRandomlyLimit, userid);
            photoManager.cleanAll(csvResourcePathPikalevo, userid);

            log.info("********* End CleanAllScheduler *******");

        } catch (Exception e) {
            log.error("Exception happened during CleanAllScheduler: ", e);
            throw new RuntimeException(e);
        }
    }
}
