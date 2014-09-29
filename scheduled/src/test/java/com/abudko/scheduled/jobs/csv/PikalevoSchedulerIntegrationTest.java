package com.abudko.scheduled.jobs.csv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/test-app-config.xml" })
public class PikalevoSchedulerIntegrationTest {

    @Autowired
    private PikalevoScheduler pikalevoScheduler;
    
    @Test
    public void test() {
        pikalevoScheduler.schedule();
    }

}
