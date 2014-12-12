package com.abudko.scheduled.jobs.publish;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/test-app-config.xml" })
public class CleanSchedulerIntegrationTest {

    @Autowired
    private CleanScheduler cleanScheduler;
    
    @Autowired
    private Clean2Scheduler clean2Scheduler;
    
    @Test
    @Ignore
    public void testClean() throws IOException {
        cleanScheduler.schedule();
    }
}
