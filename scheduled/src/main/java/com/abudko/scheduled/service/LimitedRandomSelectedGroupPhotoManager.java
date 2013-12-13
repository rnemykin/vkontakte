package com.abudko.scheduled.service;

import org.springframework.stereotype.Component;

@Component
public class LimitedRandomSelectedGroupPhotoManager extends RandomSelectedGroupPhotoManager {

    @Override
    protected int getRandomPhotoCount() {
        return 5;
    }
}
