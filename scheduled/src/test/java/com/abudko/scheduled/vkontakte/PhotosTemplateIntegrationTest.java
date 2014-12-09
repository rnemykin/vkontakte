package com.abudko.scheduled.vkontakte;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/test-app-config.xml" })
public class PhotosTemplateIntegrationTest {
    
    @Autowired
    private PhotosTemplate photosTemplate;

    @Test(expected = RuntimeException.class)
    public void testGetPhotosNegative() {
        photosTemplate.getPhotos("ownerId", "albumId", 0);
    }

}
