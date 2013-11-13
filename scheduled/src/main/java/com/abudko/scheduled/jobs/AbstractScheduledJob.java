package com.abudko.scheduled.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractScheduledJob<T extends Scheduler> {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    protected void callScheduler(JobExecutionContext context) {
        try {
            SchedulerContext schedulerContext = context.getScheduler().getContext();
            T scheduler = (T) schedulerContext.get(getSchedulerBeanName());
            scheduler.schedule();
        } catch (SchedulerException e) {
            log.error("Scheduler 'ScanJob' throwed an exception: ", e);
        }
    }
    
    protected abstract String getSchedulerBeanName();
}