package com.abudko.scheduled.jobs;

import java.lang.reflect.ParameterizedType;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractScheduledJob<T extends Scheduler> implements Job {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            SchedulerContext schedulerContext = context.getScheduler().getContext();
            Scheduler scheduler = (Scheduler) schedulerContext.get(getSchedulerBeanName());
            scheduler.schedule();
        } catch (SchedulerException e) {
            log.error("Scheduler 'ScanJob' throwed an exception: ", e);
        }
    }

    public String getSchedulerBeanName() {
        String name = getGenericName();

        StringBuilder sb = new StringBuilder(name.substring(0, 1).toLowerCase());
        sb.append(name.substring(1, name.length()));

        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    protected String getGenericName() {
        return ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0])
                .getSimpleName();
    }
}
