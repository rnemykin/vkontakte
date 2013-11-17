package com.abudko.scheduled.service;

import static com.abudko.scheduled.service.PhotoManagerTestHelper.GROUPID1;
import static com.abudko.scheduled.service.PhotoManagerTestHelper.GROUPID2;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.abudko.scheduled.csv.PhotoDataLogger;
import com.abudko.scheduled.csv.PhotoDataReader;
import com.abudko.scheduled.vkontakte.PhotosTemplate;
import com.abudko.scheduled.vkontakte.SavedPhoto;
import com.abudko.scheduled.vkontakte.UploadedPhoto;

@RunWith(MockitoJUnitRunner.class)
public class RandomSelectedGroupPhotoManagerTest {

    @Mock
    private PhotoDataReader photoDataReader;

    @Mock
    private PhotoDataLogger photoDataLogger;

    @Mock
    private PhotosTemplate photosTemplate;

    @InjectMocks
    private PhotoManager photoManager = new RandomSelectedGroupPhotoManager();

    @Before
    public void setup() {
        when(photoDataReader.read(Mockito.anyString())).thenReturn(PhotoManagerTestHelper.getTestData());

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
