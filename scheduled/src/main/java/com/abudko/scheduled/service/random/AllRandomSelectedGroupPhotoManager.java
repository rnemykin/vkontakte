package com.abudko.scheduled.service.random;

import org.springframework.stereotype.Component;


@Component
public class AllRandomSelectedGroupPhotoManager extends RandomSelectedGroupPhotoManager {
    
    @Override
    protected int getRandomPhotoCount() {
        return Integer.MAX_VALUE;
    }
}
