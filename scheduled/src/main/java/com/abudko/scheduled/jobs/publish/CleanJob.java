package com.abudko.scheduled.jobs.publish;

import org.springframework.stereotype.Component;

import com.abudko.scheduled.jobs.AbstractScheduledJob;

@Component
public class CleanJob extends AbstractScheduledJob<CleanScheduler> {

}
