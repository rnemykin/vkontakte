package com.abudko.scheduled.vkontakte;

import java.net.URI;
import java.util.Map;
import java.util.Properties;

import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class PhotosTemplate implements PhotosOperations {

    private final static String VK_REST_URL = "https://api.vk.com/method/";

    private Logger log = LoggerFactory.getLogger(getClass());
    
    @Value("#{tokenProperties['token']}")
    private String token; 

    @Autowired
    private RestTemplate restTemplate;

    public String getUploadServer(String groupId, String albumId) {
        Properties props = new Properties();

        props.put("album_id", albumId);
        props.put("group_id", groupId);

        URI uri = makeOperationURL("photos.getUploadServer", props);

        JsonNode response = restTemplate.getForObject(uri, JsonNode.class).get("response");
        
        log.info(String.format("photos.getUploadServer, response '%s'", response));

        return response.get("upload_url").asText();
    }

    public UploadedPhoto uploadPhoto(String url, String photoFileLocation) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
        params.add("file1", new FileSystemResource(photoFileLocation));

        UploadedPhoto uploadedPhoto = restTemplate.postForObject(url, params, UploadedPhoto.class);

        return uploadedPhoto;
    }

    public String savePhoto(UploadedPhoto uploadedPhoto, String description) {
        Properties props = new Properties();

        props.put("aid", uploadedPhoto.getAid());
        props.put("server", uploadedPhoto.getServer());
        props.put("photos_list", uploadedPhoto.getPhotos_list());
        props.put("hash", uploadedPhoto.getHash());
        props.put("gid", uploadedPhoto.getGid());
        props.put("caption", description);

        URI uri = makeOperationURL("photos.save", props);

        JsonNode response = restTemplate.getForObject(uri, JsonNode.class).get("response");
        
        log.info(String.format("photos.save, response '%s'", response));

        return response.get(0).get("pid").asText();
    }

    public String deletePhoto(String photoId, String ownerId) {
        Properties props = new Properties();

        props.put("photo_id", photoId);
        props.put("owner_id", ownerId);

        URI uri = makeOperationURL("photos.delete", props);

        String response = restTemplate.getForObject(uri, String.class);

        log.info(String.format("photos.delete, response '%s'", response));
        
        return response;
    }

    protected URI makeOperationURL(String method, Properties params) {
        URIBuilder uri = URIBuilder.fromUri(VK_REST_URL + method);
        uri.queryParam("access_token", token);
        for (Map.Entry<Object, Object> objectObjectEntry : params.entrySet()) {
            uri.queryParam(objectObjectEntry.getKey().toString(), objectObjectEntry.getValue().toString());
        }
        return uri.build();
    }
}
