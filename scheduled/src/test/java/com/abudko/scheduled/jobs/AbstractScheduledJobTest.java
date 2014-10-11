package com.abudko.scheduled.jobs;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.abudko.scheduled.jobs.publish.Clean2Job;
import com.abudko.scheduled.jobs.publish.PublishHuuto2Job;

public class AbstractScheduledJobTest {

    @Test
    public void testCleanSchedulerName() {
        assertEquals("clean2Scheduler", new Clean2Job().getSchedulerBeanName());
    }

    @Test
    public void testPublishSchedulerName() {
        assertEquals("publishHuuto2Scheduler", new PublishHuuto2Job().getSchedulerBeanName());
    }
}
