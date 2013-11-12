package com.abudko.scheduled.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/test-app-config.xml" })
public class PhotoManagerIntegrationTest {

    @Autowired
    private PhotoManager photoManager;
    
    @Value("#{scheduledProperties['hourIntervalCsvFile']}")
    private String csvResourcePath;
    
    @Value("#{scheduledProperties['hourIntervalDumpFile']}")
    private String dumpFileLocation;

    @Test
    public void testPublish() throws InterruptedException {
        photoManager.publish(csvResourcePath, dumpFileLocation);
    }
}
