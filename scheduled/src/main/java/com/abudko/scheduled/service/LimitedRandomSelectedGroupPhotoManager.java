package com.abudko.scheduled.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.abudko.scheduled.csv.PhotoData;
import com.google.common.collect.ImmutableList;

@Component
public class LimitedRandomSelectedGroupPhotoManager extends RandomSelectedGroupPhotoManager {

    static final int RANDOM_PHOTO_COUNT = 5;

    @Override
    protected List<PhotoData> getRandomPhotos(ImmutableList<PhotoData> list) {
        List<PhotoData> randomList = new ArrayList<PhotoData>();
        List<PhotoData> l = new ArrayList<>(list);
        Collections.shuffle(l);
        final int count = l.size() > RANDOM_PHOTO_COUNT ? RANDOM_PHOTO_COUNT : l.size();
        for (int i = 0; i < count; i++) {
            randomList.add(l.get(i));
        }
        return randomList;
    }
}
