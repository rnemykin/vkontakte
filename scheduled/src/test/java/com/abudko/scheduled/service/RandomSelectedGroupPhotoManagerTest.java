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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.abudko.scheduled.service.random.LimitedRandomSelectedGroupPhotoManager;
import com.abudko.scheduled.vkontakte.SavedPhoto;
import com.abudko.scheduled.vkontakte.UploadedPhoto;

@RunWith(MockitoJUnitRunner.class)
public class RandomSelectedGroupPhotoManagerTest extends PhotoManagerTestHelper {

    @Before
    public void setup() {
        Mockito.reset(photoDataReader);
        Mockito.reset(photoDataLogger);
        Mockito.reset(photosTemplate);
        photoManager = new LimitedRandomSelectedGroupPhotoManager();
        super.setup();
        setupMocks();
        when(photosTemplate.savePhoto(Mockito.any(UploadedPhoto.class), Mockito.anyString())).thenReturn(
                new SavedPhoto());
    }

    @Test
    public void testRandomSelection() throws Exception {
        photoManager.publish("csvResourcePath", "dumpFileLocation");

        verify(photosTemplate, times(3)).getUploadServer(Mockito.eq(GROUPID1), Mockito.anyString());
        verify(photosTemplate, times(3)).getUploadServer(Mockito.eq(GROUPID2), Mockito.anyString());
    }

    @Test
    public void testReadDumpFile() throws Exception {
        final String dumpFileLocation = "dumpFileLocation";
        photoManager.publish("csvResourcePath", dumpFileLocation);

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

        photoManager.publish("csvResourcePath", dumpFileLocation);

        verify(photosTemplate).getCommentsCount(photoId1, "-" + groupId1);
        verify(photosTemplate).getCommentsCount(photoId2, "-" + groupId2);
        verify(photosTemplate).deletePhoto(photoId1, "-" + groupId1);
        verify(photosTemplate).deletePhoto(photoId2, "-" + groupId2);
    }

    @Test
    public void testCannotDeleteBecauseOfComment() throws Exception {
        final String dumpFileLocation = "dumpFileLocation";
        Map<String, String> dump = new HashMap<String, String>();
        final String photoId1 = "photoId1";
        final String groupId1 = "groupId1";
        dump.put(photoId1, groupId1);
        when(photoDataLogger.read(dumpFileLocation)).thenReturn(dump);
        when(photosTemplate.getCommentsCount(photoId1, groupId1)).thenReturn(1);

        photoManager.publish("csvResourcePath", dumpFileLocation);

        verify(photosTemplate, times(0)).deletePhoto(photoId1, groupId1);
    }

    @Test
    public void testDumpAllWhenException() throws Exception {
        final String dumpFileLocation = "dumpFileLocation";
        final Resource fileResource = new ClassPathResource("location");
        getTestData().get(0).setFileResource(fileResource);
        final String description = "description";
        getTestData().get(0).setDescription(description);
        final String uploadUrl = "uploadUrl";
        when(photosTemplate.getUploadServer(Mockito.eq(GROUPID1), Mockito.anyString())).thenReturn(uploadUrl);
        final UploadedPhoto uploadedPhoto = new UploadedPhoto();
        when(photosTemplate.uploadPhoto(uploadUrl, fileResource)).thenReturn(uploadedPhoto);
        SavedPhoto savedPhoto = new SavedPhoto();
        final String ownerId = "ownerId";
        savedPhoto.setOwnerId(ownerId);
        final String photoId = "photoId";
        savedPhoto.setPhotoId(photoId);
        when(photosTemplate.savePhoto(uploadedPhoto, description)).thenReturn(savedPhoto);
        when(photosTemplate.getUploadServer(Mockito.eq(GROUPID2), Mockito.anyString())).thenThrow(
                new RuntimeException());

        photoManager.publish("csvResourcePath", dumpFileLocation);

        verify(photoDataLogger).dump(Mockito.anyMap(), Mockito.eq(dumpFileLocation));
        verify(log, times(3)).error(Mockito.anyString(), Mockito.any(Throwable.class));
    }

    @Test
    public void testNoDumpWhenDumpMapIsEmpty() throws Exception {
        final String dumpFileLocation = "dumpFileLocation";
        when(photosTemplate.getUploadServer(Mockito.anyString(), Mockito.anyString()))
                .thenThrow(new RuntimeException());

        photoManager.publish("csvResourcePath", dumpFileLocation);

        verify(photoDataLogger, times(0)).dump(Mockito.anyMap(), Mockito.eq(dumpFileLocation));
        verify(log, times(6)).error(Mockito.anyString(), Mockito.any(Throwable.class));
    }
}
