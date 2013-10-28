package org.springframework.social.vkontakte.api.impl;

import java.net.URI;
import java.util.Properties;

import org.springframework.social.vkontakte.api.PhotosOperations;
import org.springframework.social.vkontakte.api.UploadServer;
import org.springframework.social.vkontakte.api.VKGenericResponse;
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

        VKGenericResponse response = restTemplate.getForObject(uri, VKGenericResponse.class);
        checkForError(response);
        
        UploadServer uploadServer = new UploadServer();
        uploadServer.setUpload_url(response.getResponse().get("upload_url").asText());
        uploadServer.setAid(response.getResponse().get("aid").asText());
        uploadServer.setMid(response.getResponse().get("mid").asText());

        return uploadServer;
    }

}
