package com.abudko.scheduled.jobs.publish;

import javax.annotation.PostConstruct;

import com.abudko.scheduled.jobs.Scheduler;
import com.abudko.scheduled.service.huuto.AlbumMapper2;

public class Clean2Scheduler extends CleanScheduler implements Scheduler {
    
   @PostConstruct 
   public void init() {
       String groupId = AlbumMapper2.GROUP_ID;
       
       this.photoTypeKeywordAlbumCleaner.setGroupId(groupId);
       this.itemValidityRulesAlbumCleaner.setGroupId(groupId);
   }
}
