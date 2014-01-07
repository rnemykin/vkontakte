package com.abudko.scheduled.jmx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.stereotype.Component;

import com.abudko.scheduled.jobs.csv.DayIntervalScheduler;
import com.abudko.scheduled.jobs.csv.HourIntervalScheduler;
import com.abudko.scheduled.jobs.csv.PikalevoScheduler;
import com.abudko.scheduled.jobs.csv.RandomlySelectedScheduler;
import com.abudko.scheduled.jobs.huuto.CleanScheduler;
import com.abudko.scheduled.jobs.huuto.PublishScheduler;
import com.abudko.scheduled.jobs.huuto.UsedPublishScheduler;

@Component
public class ScheduledMbean {

    @Autowired
    private HourIntervalScheduler hourIntervalScheduler;

    @Autowired
    private DayIntervalScheduler dayIntervalScheduler;

    @Autowired
    private RandomlySelectedScheduler randomlySelectedScheduler;
    
    @Autowired
    private PublishScheduler publishScheduler;

    @Autowired
    private UsedPublishScheduler usedPublishScheduler;
    
    @Autowired
    private CleanScheduler cleanScheduler;

    @Autowired
    private PikalevoScheduler pikalevoScheduler;

    @ManagedOperation
    public void startHourJob() {
        hourIntervalScheduler.schedule();
    }
    
    @ManagedOperation
    public void startHourDeleteAll() {
        hourIntervalScheduler.clean();
    }
    
    @ManagedOperation
    public void startDayJob() {
        dayIntervalScheduler.schedule();
    }
    
    @ManagedOperation
    public void startRandomJob() {
        randomlySelectedScheduler.schedule();
    }
    
    @ManagedOperation
    public void startPublishJob() {
        publishScheduler.schedule();
    }
    
    @ManagedOperation
    public void startUsedPublishJob() {
        usedPublishScheduler.schedule();
    }

    @ManagedOperation
    public void startCleanJob() {
        cleanScheduler.schedule();
    }
    
    @ManagedOperation
    public void startPikalevoJob() {
        pikalevoScheduler.schedule();
    }
}