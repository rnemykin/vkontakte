package com.abudko.scheduled.service.random;

import org.springframework.stereotype.Component;


@Component
public class LimitedRandomSelectedGroupPhotoManager extends RandomSelectedGroupPhotoManager {

    @Override
    protected int getRandomPhotoCount() {
        return 3;
    }
}
