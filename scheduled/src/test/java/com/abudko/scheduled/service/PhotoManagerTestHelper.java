package com.abudko.scheduled.service;

import static org.mockito.Mockito.when;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;

import com.abudko.scheduled.csv.PhotoData;
import com.abudko.scheduled.csv.PhotoDataLogger;
import com.abudko.scheduled.csv.PhotoDataReader;
import com.abudko.scheduled.vkontakte.PhotosTemplate;

public class PhotoManagerTestHelper {
    
    public static final String GROUPID1 = "1111111";
    public static final String ALBUMID1 = "1";
    public static final String ALBUMID2 = "2";
    
    public static final String GROUPID2 = "2222222";
    
    @Mock
    protected PhotoDataReader photoDataReader;

    @Mock
    protected PhotoDataLogger photoDataLogger;
    
    @Mock
    protected Logger log;

    @Mock
    protected PhotosTemplate photosTemplate;
    
    protected AbstractPhotoManager photoManager;
    
    protected List<PhotoData> testData;
    
    protected void setup() {
        when(photoDataReader.read(Mockito.anyString())).thenReturn(getTestData());
        photoManager.sleepInterval = 1;
    }
    
    protected void setupMocks() {
        setInternalState(photoManager, "photoDataReader", photoDataReader);
        setInternalState(photoManager, "photoDataLogger", photoDataLogger);
        setInternalState(photoManager, "photosTemplate", photosTemplate);
        setInternalState(photoManager, "log", log);
    }
    
    protected List<PhotoData> getTestData() {
        if (testData == null) {
            testData = populateTestData();
        }
        
        return testData;
    }
    
    private List<PhotoData> populateTestData() {
        List<PhotoData> list = new ArrayList<PhotoData>();

        PhotoData photoData11 = new PhotoData();
        photoData11.setGroupId(GROUPID1);
        photoData11.setAlbumId(ALBUMID1);
        list.add(photoData11);

        PhotoData photoData12 = new PhotoData();
        photoData12.setGroupId(GROUPID1);
        photoData12.setAlbumId(ALBUMID2);
        list.add(photoData12);

        PhotoData photoData13 = new PhotoData();
        photoData13.setGroupId(GROUPID1);
        photoData13.setAlbumId("3");
        list.add(photoData13);

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

        return list;
    }
}
