package com.abudko.scheduled.jobs.csv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abudko.scheduled.jobs.Scheduler;

public class MinuteIntervalScheduler implements Scheduler {

    private Logger log = LoggerFactory.getLogger(getClass());
    

    public void schedule() {
        log.info("********* Start MinuteIntervalScheduler *******");
        try {

            // TODO
            
            log.info("********* End MinuteIntervalScheduler *******");

        } catch (Exception e) {
            log.error("Exception happened during scheduled scan: ", e);
        }
    }
}
