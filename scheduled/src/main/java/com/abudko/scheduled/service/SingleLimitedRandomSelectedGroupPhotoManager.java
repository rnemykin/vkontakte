package com.abudko.scheduled.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.abudko.scheduled.csv.PhotoData;
import com.google.common.collect.ImmutableList;

@Component
public class SingleLimitedRandomSelectedGroupPhotoManager extends RandomSelectedGroupPhotoManager {

    @Override
    protected List<PhotoData> getRandomPhotos(ImmutableList<PhotoData> list) {
        List<PhotoData> randomList = new ArrayList<PhotoData>();
        List<PhotoData> l = new ArrayList<>(list);
        Collections.shuffle(l);
        randomList.add(l.get(0));
        return randomList;
    }
}
