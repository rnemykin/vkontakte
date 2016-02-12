package com.abudko.scheduled.jmx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;

import com.abudko.scheduled.jobs.csv.CleanAllScheduler;
import com.abudko.scheduled.jobs.csv.DayIntervalScheduler;
import com.abudko.scheduled.jobs.csv.HourIntervalScheduler;
import com.abudko.scheduled.jobs.csv.PikalevoScheduler;
import com.abudko.scheduled.jobs.csv.RandomlySelectedScheduler;
import com.abudko.scheduled.jobs.csv.RandomlySelectedSchedulerLimit;
import com.abudko.scheduled.jobs.publish.Clean2Scheduler;
import com.abudko.scheduled.jobs.publish.CleanScheduler;
import com.abudko.scheduled.jobs.publish.PublishHuuto2InternalScheduler;
import com.abudko.scheduled.jobs.publish.PublishHuuto2Scheduler;
import com.abudko.scheduled.jobs.publish.PublishHuutoScheduler;
import com.abudko.scheduled.jobs.publish.PublishLekmer2Scheduler;
import com.abudko.scheduled.jobs.publish.PublishLekmerScheduler;
import com.abudko.scheduled.jobs.publish.PublishReima2Scheduler;
import com.abudko.scheduled.jobs.publish.PublishReimaScheduler;
import com.abudko.scheduled.jobs.publish.PublishStadium2Scheduler;
import com.abudko.scheduled.jobs.publish.PublishStadiumScheduler;
import com.abudko.scheduled.jobs.publish.PublishXxl2Scheduler;
import com.abudko.scheduled.jobs.publish.PublishXxlScheduler;

public class ScheduledMbean {

    @Autowired
    private HourIntervalScheduler hourIntervalScheduler;

    @Autowired
    private DayIntervalScheduler dayIntervalScheduler;

    @Autowired
    private RandomlySelectedScheduler randomlySelectedScheduler;

    @Autowired
    private RandomlySelectedSchedulerLimit randomlySelectedSchedulerLimit;
    
    @Autowired
    private PublishHuutoScheduler publishHuutoScheduler;
    
    @Autowired
    private PublishHuuto2Scheduler publishHuuto2Scheduler;

    @Autowired
    private PublishHuuto2InternalScheduler publishHuuto2InternalScheduler;
    
    @Autowired
    private PublishLekmerScheduler publishLekmerScheduler;
    
    @Autowired
    private PublishLekmer2Scheduler publishLekmer2Scheduler;

    @Autowired
    private PublishStadiumScheduler publishStadiumScheduler;
    
    @Autowired
    private PublishStadium2Scheduler publishStadium2Scheduler;
    
    @Autowired
    private PublishReimaScheduler publishReimaScheduler;
    
    @Autowired
    private PublishReima2Scheduler publishReima2Scheduler;

    @Autowired
    private PublishXxlScheduler publishXxlScheduler;
    
    @Autowired
    private PublishXxl2Scheduler publishXxl2Scheduler;
    
    @Autowired
    private CleanScheduler cleanScheduler;
    
    @Autowired
    private Clean2Scheduler clean2Scheduler;

    @Autowired
    private CleanAllScheduler cleanAllScheduler;

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
    public void startRandomLimitJob() {
        randomlySelectedSchedulerLimit.schedule();
    }
    
    @ManagedOperation
    public void startPublishHuutoJob() {
        publishHuutoScheduler.schedule();
    }
    
    @ManagedOperation
    public void startPublishHuuto2Job() {
        publishHuuto2Scheduler.schedule();
    }
    
    @ManagedOperation
    public void startPublishHuuto2InternalJob() {
        publishHuuto2InternalScheduler.schedule();
    }
    
    @ManagedOperation
    public void startPublishLekmerJob() {
        publishLekmerScheduler.schedule();
    }
    
    @ManagedOperation
    public void startPublishLekmer2Job() {
        publishLekmer2Scheduler.schedule();
    }
    
    @ManagedOperation
    public void startPublishStadiumJob() {
    	publishStadiumScheduler.schedule();
    }
    
    @ManagedOperation
    public void startPublishStadium2Job() {
    	publishStadium2Scheduler.schedule();
    }
    
    @ManagedOperation
    public void startPublishReimaJob() {
    	publishReimaScheduler.schedule();
    }
    
    @ManagedOperation
    public void startPublishReima2Job() {
    	publishReima2Scheduler.schedule();
    }

    @ManagedOperation
    public void startPublishXxlJob() {
    	publishXxlScheduler.schedule();
    }
    
    @ManagedOperation
    public void startPublishXxl2Job() {
    	publishXxl2Scheduler.schedule();
    }
    
    @ManagedOperation
    public void startCleanJob() {
        cleanScheduler.schedule();
    }
    
    @ManagedOperation
    public void startClean2Job() {
        clean2Scheduler.schedule();
    }
    
    @ManagedOperation
    public void startCustomCleanJob(String photoIdTypeStartPrefix, String keyword) {
        cleanScheduler.clean(photoIdTypeStartPrefix, keyword);
    }
    
    @ManagedOperation
    public void startCustomClean2Job(String photoIdTypeStartPrefix, String keyword) {
        clean2Scheduler.clean(photoIdTypeStartPrefix, keyword);
    }
    
    @ManagedOperation
    public void startCleanAllJob() {
        cleanAllScheduler.schedule();
    }
    
    @ManagedOperation
    public void startPikalevoJob() {
        pikalevoScheduler.schedule();
    }
}