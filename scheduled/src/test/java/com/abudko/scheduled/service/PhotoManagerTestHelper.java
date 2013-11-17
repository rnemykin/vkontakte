package com.abudko.scheduled.service;

import java.util.ArrayList;
import java.util.List;

import com.abudko.scheduled.csv.PhotoData;

public class PhotoManagerTestHelper {
    
    public static final String GROUPID1 = "1111111";
    
    public static final String GROUPID2 = "2222222";
    
    public static List<PhotoData> getTestData() {
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
}
