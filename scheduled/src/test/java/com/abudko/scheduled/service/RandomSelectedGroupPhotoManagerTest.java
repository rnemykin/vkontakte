package com.abudko.scheduled.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.abudko.scheduled.csv.PhotoData;
import com.abudko.scheduled.csv.PhotoDataLogger;
import com.abudko.scheduled.csv.PhotoDataReader;
import com.abudko.scheduled.vkontakte.PhotosTemplate;
import com.abudko.scheduled.vkontakte.SavedPhoto;
import com.abudko.scheduled.vkontakte.UploadedPhoto;

@RunWith(MockitoJUnitRunner.class)
public class RandomSelectedGroupPhotoManagerTest {

    private static final String GROUPID1 = "1111111";
    private static final String GROUPID2 = "2222222";

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
        when(photoDataReader.read(Mockito.anyString())).thenReturn(getTestData());

        when(photosTemplate.savePhoto(Mockito.any(UploadedPhoto.class), Mockito.anyString())).thenReturn(
                new SavedPhoto());
    }

    private List<PhotoData> getTestData() {
        List<PhotoData> list = new ArrayList<PhotoData>();

        PhotoData photoData11 = new PhotoData();
        photoData11.setGroupId(GROUPID1);
        photoData11.setAlbumId("1");
        list.add(photoData11);

        PhotoData photoData12 = new PhotoData();
        photoData12.setGroupId(GROUPID1);
        photoData12.setAlbumId("2");
        list.add(photoData12);

        PhotoData photoData13 = new PhotoData();
        photoData13.setGroupId(GROUPID1);
        photoData13.setAlbumId("3");
        list.add(photoData13);

        PhotoData photoData14 = new PhotoData();
        photoData14.setGroupId(GROUPID1);
        photoData14.setAlbumId("4");
        list.add(photoData14);

        PhotoData photoData21 = new PhotoData();
        photoData21.setGroupId(GROUPID2);
        photoData21.setAlbumId("a");
        list.add(photoData21);

        PhotoData photoData22 = new PhotoData();
        photoData22.setGroupId(GROUPID2);
        photoData22.setAlbumId("b");
        list.add(photoData22);

        PhotoData photoData23 = new PhotoData();
        photoData23.setGroupId(GROUPID2);
        photoData23.setAlbumId("c");
        list.add(photoData23);

        PhotoData photoData24 = new PhotoData();
        photoData24.setGroupId(GROUPID2);
        photoData24.setAlbumId("d");
        list.add(photoData24);

        return list;
    }

    @Test
    public void testPublish() throws Exception {
        photoManager.publish("csvResourcePath", "dumpFileLocation");

        verify(photosTemplate, times(3)).getUploadServer(Mockito.eq(GROUPID1), Mockito.anyString());
        verify(photosTemplate, times(3)).getUploadServer(Mockito.eq(GROUPID2), Mockito.anyString());
    }

}
