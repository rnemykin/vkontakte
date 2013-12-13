package com.abudko.scheduled.service.random;

import org.springframework.stereotype.Component;


@Component
public class SingleLimitedRandomSelectedGroupPhotoManager extends RandomSelectedGroupPhotoManager {
    
    @Override
    protected int getRandomPhotoCount() {
        return 1;
    }
}
