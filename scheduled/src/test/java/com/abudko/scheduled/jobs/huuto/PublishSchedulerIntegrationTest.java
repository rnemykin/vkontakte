package com.abudko.scheduled.jobs.huuto;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/test-app-config.xml" })
public abstract class PublishSchedulerIntegrationTest {

    @Autowired
    private PublishScheduler publishScheduler;
    
    @Test
    public void testPublish() throws IOException {
       publishScheduler.schedule();
    }
}