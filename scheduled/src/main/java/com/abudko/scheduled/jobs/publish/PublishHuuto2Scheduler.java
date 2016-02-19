package com.abudko.scheduled.jobs.publish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.abudko.scheduled.service.huuto.PublishManager;

public class PublishHuuto2Scheduler extends PublishHuutoScheduler {

    @Autowired
    @Qualifier("huuto2PublishManager")
    private PublishManager publishManager;

	@Override
	protected PublishManager getPublishManager() {
		return publishManager;
	}
}