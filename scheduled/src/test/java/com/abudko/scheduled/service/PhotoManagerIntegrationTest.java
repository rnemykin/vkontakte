package com.abudko.scheduled.service;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abudko.scheduled.vkontakte.Photo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/test-app-config.xml" })
public class PhotoManagerIntegrationTest {

    @Autowired
    @Qualifier("groupPhotoManager")
    private AbstractPhotoManager photoManager;
    
    @Value("#{scheduledProperties['hourIntervalCsvFile']}")
    private String csvResourcePath;
    
    @Value("#{userProperties['userid']}")
    private String userId;
    
    @Test
    @Ignore
    public void testPublish() throws InterruptedException {
        photoManager.publish(csvResourcePath, "classpath:/csv/photos-testlog.csv");
    }
    
    @Test
    @Ignore
    public void testCleanAllFromAlbum() throws InterruptedException {
    	final String groupId = "6832456";
    	final String albumId = "123502768";
    	
        List<Photo> photos = photoManager.getPhotos(groupId, albumId);

        for (Photo photo : photos) {
            if (userId.equals(photoManager.getOwnerId(photo.getUserId()))) {
            	photoManager.deletePhoto(photo, groupId, albumId);
            }
        }
    }
}
