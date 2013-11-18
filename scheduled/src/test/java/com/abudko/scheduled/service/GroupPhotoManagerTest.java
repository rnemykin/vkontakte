package com.abudko.scheduled.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.abudko.scheduled.vkontakte.SavedPhoto;
import com.abudko.scheduled.vkontakte.UploadedPhoto;

@RunWith(MockitoJUnitRunner.class)
public class GroupPhotoManagerTest extends PhotoManagerTestHelper {

    @Before
    public void setup() {
        photoManager = new GroupPhotoManager();
        super.setup();        
        setupMocks();
        when(photosTemplate.savePhoto(Mockito.any(UploadedPhoto.class), Mockito.anyString())).thenReturn(
                new SavedPhoto());
    }

    @Test
    public void testReadDumpFile() throws Exception {
        final String dumpFileLocation = "dumpFileLocation";
        photoManager.publish("csvResourcePath", dumpFileLocation, null);

        verify(photoDataLogger).read(dumpFileLocation);
    }
    
    @Test
    public void testDeleteAll() throws Exception {
        final String dumpFileLocation = "dumpFileLocation";
        Map<String, String> dump = new HashMap<String, String>();
        final String photoId1 = "photoId1";
        final String photoId2 = "photoId2";
        final String groupId1 = "groupId1";
        final String groupId2 = "groupId2";
        dump.put(photoId1, groupId1);
        dump.put(photoId2, groupId2);
        when(photoDataLogger.read(dumpFileLocation)).thenReturn(dump);

        photoManager.publish("csvResourcePath", dumpFileLocation, null);

        verify(photosTemplate).getCommentsCount(photoId1, "-"+groupId1);
        verify(photosTemplate).getCommentsCount(photoId2, "-"+groupId2);
        verify(photosTemplate).deletePhoto(photoId1, "-"+groupId1);
        verify(photosTemplate).deletePhoto(photoId2, "-"+groupId2);
    }
    
    @Test
    public void testCannotDeleteBecauseOfComment() throws Exception {
        final String dumpFileLocation = "dumpFileLocation";
        Map<String, String> dump = new HashMap<String, String>();
        final String photoId1 = "photoId1";
        final String groupId1 = "groupId1";
        dump.put(photoId1, groupId1);
        when(photoDataLogger.read(dumpFileLocation)).thenReturn(dump);
        when(photosTemplate.getCommentsCount(photoId1, "-"+groupId1)).thenReturn(1);
        
        photoManager.publish("csvResourcePath", dumpFileLocation, null);
        
        verify(photosTemplate, times(0)).deletePhoto(photoId1, "-"+groupId1);
    }
}
