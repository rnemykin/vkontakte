package org.springframework.social.vkontakte.api.impl;

import java.net.URI;
import java.util.Properties;

import org.springframework.social.vkontakte.api.PhotosOperations;
import org.springframework.social.vkontakte.api.UploadServer;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PhotosTemplate extends AbstractVKontakteOperations implements PhotosOperations {

    private final RestTemplate restTemplate;

    public PhotosTemplate(RestTemplate restTemplate, String accessToken, ObjectMapper objectMapper, boolean isAuthorizedForUser) {
        super(isAuthorizedForUser, accessToken, objectMapper);
        this.restTemplate = restTemplate;
    }
    
    public UploadServer getUploadServer() {
        requireAuthorization();
        Properties props = new Properties();

        props.put("album_id", "166279845");
        props.put("group_id", "11004536");
        
        URI uri = makeOperationURL("photos.getUploadServer", props);

        UploadServer uploadServer = restTemplate.getForObject(uri, UploadServer.class);
        checkForError(uploadServer);

        return uploadServer;
    }

}
