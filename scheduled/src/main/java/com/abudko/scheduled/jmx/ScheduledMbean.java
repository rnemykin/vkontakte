package com.abudko.scheduled.jmx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.abudko.scheduled.service.PhotoManager;

@ManagedResource
public class ScheduledMbean {

    private Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private PhotoManager photoManager;
    
    @Value("#{scheduledProperties['hourIntervalCsvFile']}")
    private String csvResourcePathHour;
    
    @Value("#{scheduledProperties['hourIntervalDumpFile']}")
    private String dumpFileLocationHour;
    
    @Value("#{scheduledProperties['dayIntervalCsvFile']}")
    private String csvResourcePathDay;
    
    @Value("#{scheduledProperties['dayIntervalDumpFile']}")
    private String dumpFileLocationDay;

    @ManagedOperation
    public void startHourJob() {
        publish(csvResourcePathHour, dumpFileLocationHour);
    }
    
    @ManagedOperation
    public void startDayJob() {
        publish(csvResourcePathDay, dumpFileLocationDay);
    }
    
    private void publish(String csvResourcePath, String dumpFileLocation) {
        try {
            photoManager.publish(csvResourcePath, dumpFileLocation);
        } catch (Exception e) {
            log.error("Exception happened during JMX scheduled scan: ", e);
        }
    }
}