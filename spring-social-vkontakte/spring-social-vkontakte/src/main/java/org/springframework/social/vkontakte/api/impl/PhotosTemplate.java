package org.springframework.social.vkontakte.api.impl;

import java.net.URI;
import java.util.Properties;

import org.springframework.core.io.FileSystemResource;
import org.springframework.social.vkontakte.api.PhotosOperations;
import org.springframework.social.vkontakte.api.UploadServer;
import org.springframework.social.vkontakte.api.UploadedPhoto;
import org.springframework.social.vkontakte.api.VKGenericResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PhotosTemplate extends AbstractVKontakteOperations implements PhotosOperations {

    private final RestTemplate restTemplate;

    public PhotosTemplate(RestTemplate restTemplate, String accessToken, ObjectMapper objectMapper, boolean isAuthorizedForUser) {
        super(isAuthorizedForUser, accessToken, objectMapper);
        this.restTemplate = restTemplate;
    }
    
    public UploadServer getUploadServer(String groupId, String albumId) {
        requireAuthorization();
        Properties props = new Properties();

        props.put("album_id", albumId);
        props.put("group_id", groupId);
        
        URI uri = makeOperationURL("photos.getUploadServer", props);

        VKGenericResponse response = restTemplate.getForObject(uri, VKGenericResponse.class);
        checkForError(response);
        
        UploadServer uploadServer = new UploadServer();
        uploadServer.setUpload_url(response.getResponse().get("upload_url").asText());
        uploadServer.setAid(response.getResponse().get("aid").asText());
        uploadServer.setMid(response.getResponse().get("mid").asText());

        return uploadServer;
    }

    public UploadedPhoto uploadPhoto(String url, String photoFileLocation) {
        requireAuthorization();
        
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
        params.add("file1", new FileSystemResource(photoFileLocation));
        
        System.err.println(restTemplate.postForObject(url, params, String.class));
        UploadedPhoto uploadedPhoto = restTemplate.postForObject(url, params, UploadedPhoto.class);
        
        
        return uploadedPhoto;
    }

    public VKGenericResponse savePhoto(UploadedPhoto uploadedPhoto, String description) {
        requireAuthorization();
        Properties props = new Properties();

        props.put("aid", uploadedPhoto.getAid());
        props.put("server", uploadedPhoto.getServer());
        props.put("photos_list", uploadedPhoto.getPhotos_list());
        props.put("hash", uploadedPhoto.getHash());
        props.put("gid", uploadedPhoto.getGid());
        props.put("caption", description);
        
        URI uri = makeOperationURL("photos.save", props);
        
        VKGenericResponse response = restTemplate.getForObject(uri, VKGenericResponse.class);
        
        return response;
    }

    public String deletePhoto(String photoId, String ownerId) {
        requireAuthorization();
        Properties props = new Properties();
        
        props.put("photo_id", photoId);
        props.put("owner_id", ownerId);
        
        URI uri = makeOperationURL("photos.delete", props);
        
        String url = "https://oauth.vk.com/authorize?response_type=token&redirect_uri=https://oauth.vk.com/blank.html&client_id=218841714&display=page&scope=notify,friends,photos,audio,video,notes,pages,wall,offline&v=5.2";
        //String url = "https://oauth.vk.com/authorize?response_type=token&redirect_uri=https://oauth.vk.com/blank.html&client_id=4564889&display=page&scope=notify,friends,photos,audio,video,notes,pages,wall,offline&v=5.2";
        String accessTokenStandaloneResponse = this.restTemplate.getForObject(url, String.class);
        
        System.out.println(accessTokenStandaloneResponse);
        
        
        String response = restTemplate.getForObject(uri, String.class);
        
        System.err.println(response);
        
        return response;
    }

}
