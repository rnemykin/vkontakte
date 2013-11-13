package com.abudko.scheduled.jobs.warm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.abudko.scheduled.jobs.Scheduler;

public class WarmScheduler implements Scheduler {
    
    private static final String URI = "http://reseller.alexbud.cloudbees.net/items/search";
    private static final String URI2 = "http://kombezi.finland.cloudbees.net/items/search";

    private Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private RestTemplate restTemplate;
    
    public void schedule() {
        log.info("********* Start scheduled scanning *******");
        try {
            log.info("Warming: " + URI);
            restTemplate.getForObject(URI, String.class);
        
            log.info("Warming: " + URI2);
            restTemplate.getForObject(URI2, String.class);
            
            log.info("********* End scheduled scanning *******");

        } catch (Exception e) {
            log.error("Exception happened during scheduled scan: ", e);
        }
    }
}
