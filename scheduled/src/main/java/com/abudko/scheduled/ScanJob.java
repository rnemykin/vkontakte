package com.abudko.scheduled;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ScanJob implements Job {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            SchedulerContext schedulerContext = context.getScheduler().getContext();
            ScanScheduler scanScheduler = (ScanScheduler) schedulerContext.get("scanScheduler");
            scanScheduler.scheduledScan();
        } catch (SchedulerException e) {
            log.error("Scheduler 'ScanJob' throwed an exception: ", e);
        }
    }

}
