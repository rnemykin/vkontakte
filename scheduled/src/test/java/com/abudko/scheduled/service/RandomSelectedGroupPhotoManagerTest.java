package com.abudko.scheduled.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.abudko.scheduled.vkontakte.SavedPhoto;
import com.abudko.scheduled.vkontakte.UploadedPhoto;

@RunWith(MockitoJUnitRunner.class)
public class RandomSelectedGroupPhotoManagerTest extends PhotoManagerTestHelper {

    @Before
    public void setup() {
        photoManager = new RandomSelectedGroupPhotoManager();
        super.setup();
        setupMocks();
        when(photosTemplate.savePhoto(Mockito.any(UploadedPhoto.class), Mockito.anyString())).thenReturn(
                new SavedPhoto());
    }

    @Test
    public void testPublish() throws Exception {
        photoManager.publish("csvResourcePath", "dumpFileLocation", null);

        verify(photosTemplate, times(3)).getUploadServer(Mockito.eq(GROUPID1), Mockito.anyString());
        verify(photosTemplate, times(3)).getUploadServer(Mockito.eq(GROUPID2), Mockito.anyString());
    }

}
