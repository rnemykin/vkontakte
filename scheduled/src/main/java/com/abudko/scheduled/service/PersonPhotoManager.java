package com.abudko.scheduled.service;

import org.springframework.stereotype.Component;

@Component
public class PersonPhotoManager extends AbstractPhotoManager {

    @Override
    protected String getOwnerId(String id) {
        return id;
    }
}
