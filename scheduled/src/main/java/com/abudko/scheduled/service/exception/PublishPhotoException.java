package com.abudko.scheduled.service.exception;

import com.abudko.scheduled.csv.PhotoData;

public class PublishPhotoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private PhotoData photoData;

    public PublishPhotoException(PhotoData photoData) {
        this.photoData = photoData;
    }

    @Override
    public String getMessage() {
        return String.format("Exception happened while publishing a photo '%s'", photoData);
    }
}
