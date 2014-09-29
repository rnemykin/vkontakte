package com.abudko.scheduled.vkontakte;

import com.fasterxml.jackson.databind.JsonNode;

public class PhotosOperationsException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    private JsonNode response;

    public PhotosOperationsException(String message, JsonNode response) {
        super(message);
        this.response = response;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        String.format("Error '%s', Response '%s'", message, response);
        return super.getMessage();
    }
}
