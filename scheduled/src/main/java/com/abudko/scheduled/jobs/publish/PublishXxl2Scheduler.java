package com.abudko.scheduled.jobs.publish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.abudko.scheduled.service.huuto.PublishManager;

public class PublishXxl2Scheduler extends PublishXxlScheduler {

    @Autowired
    @Qualifier("group2PublishManager")
    private PublishManager publishManager;

	@Override
	protected PublishManager getPublishManager() {
		return publishManager;
	}
}