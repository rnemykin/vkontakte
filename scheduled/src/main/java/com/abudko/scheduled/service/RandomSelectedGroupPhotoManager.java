package com.abudko.scheduled.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.abudko.scheduled.csv.PhotoData;
import com.abudko.scheduled.vkontakte.SavedPhoto;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;

@Component
public class RandomSelectedGroupPhotoManager extends AbstractPhotoManager {

    private static final int RANDOM_PHOTO_COUNT = 3;

    @Override
    protected void publishAll(String csvResourcePath, Map<String, String> photoIdGroupIdMap) throws InterruptedException {
        List<PhotoData> photoDataList = photoDataReader.read(csvResourcePath);
        ImmutableListMultimap<String, PhotoData> groupIdPhotoDataMap = getGroupIdPhotoDataMap(photoDataList);

        ImmutableSet<String> keySet = groupIdPhotoDataMap.keySet();
        for (String groupId : keySet) {
            ImmutableList<PhotoData> list = groupIdPhotoDataMap.get(groupId);
            List<PhotoData> randomPhotoDataList = getRandomPhotos(list);
            for (PhotoData photoData : randomPhotoDataList) {
                SavedPhoto savedPhoto = publishPhoto(photoData);

                log.info(String.format("Saved photo id '%s'", savedPhoto));

                photoIdGroupIdMap.put(savedPhoto.getPhotoId(), photoData.getGroupId());
            }
        }
    }

    private ImmutableListMultimap<String, PhotoData> getGroupIdPhotoDataMap(List<PhotoData> photos) {
        ImmutableListMultimap<String, PhotoData> groupIdToPhotos = Multimaps.index(photos, GetPhotoIdFunction.INSTANCE);
        return ImmutableListMultimap.copyOf(groupIdToPhotos);
    }

    private List<PhotoData> getRandomPhotos(ImmutableList<PhotoData> list) {
        List<PhotoData> randomList = new ArrayList<PhotoData>();
        for (int i = 0; i < RANDOM_PHOTO_COUNT; i++) {
            PhotoData photoData = null;
            do {
                List<PhotoData> l = new ArrayList<>(list);
                Collections.shuffle(l);
                photoData = l.get(0);
            } while (randomList.contains(photoData));
            randomList.add(photoData);
        }
        return randomList;
    }

    private enum GetPhotoIdFunction implements Function<PhotoData, String> {
        INSTANCE;

        @Override
        public String apply(PhotoData photoData) {
            return photoData.getGroupId();
        }
    }
}
