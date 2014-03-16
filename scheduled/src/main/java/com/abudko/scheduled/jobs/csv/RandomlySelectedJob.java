package com.abudko.scheduled.jobs.csv;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.abudko.scheduled.jobs.AbstractScheduledJob;

@Component
public class RandomlySelectedJob extends AbstractScheduledJob implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        this.callScheduler(context);
    }

    @Override
    protected String getSchedulerBeanName() {
        return "randomlySelectedScheduler";
    }
}
