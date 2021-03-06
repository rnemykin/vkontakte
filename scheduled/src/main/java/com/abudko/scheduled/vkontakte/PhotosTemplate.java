package com.abudko.scheduled.vkontakte;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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

        JsonNode response = restTemplate.getForObject(uri, JsonNode.class);

        log.info(String.format("photos.getUploadServer, response '%s'", response));

        try {
            return response.get("response").get("upload_url").asText();
        } catch (Throwable e) {
            String errorMsg = String.format("getUploadServer request failed. Got response '%s'", response);
            log.error(errorMsg);
            throw e;
        }
    }

    public UploadedPhoto uploadPhoto(String url, Resource photoFileResource) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
        params.add("file1", photoFileResource);

        UploadedPhoto uploadedPhoto = restTemplate.postForObject(url, params, UploadedPhoto.class);

        return uploadedPhoto;
    }

    public SavedPhoto savePhoto(UploadedPhoto uploadedPhoto, String description) {
        Properties props = new Properties();

        props.put("aid", uploadedPhoto.getAid());
        props.put("server", uploadedPhoto.getServer());
        props.put("photos_list", uploadedPhoto.getPhotos_list());
        props.put("hash", uploadedPhoto.getHash());

        if (uploadedPhoto.getGid() != null) {
            props.put("gid", uploadedPhoto.getGid());
        }

        props.put("caption", description);

        URI uri = makeOperationURL("photos.save", props);

        JsonNode response = restTemplate.getForObject(uri, JsonNode.class);

        JsonNode error = response.get("error");
        if (error != null) {
            throw new PhotosOperationsException(String.format("Unable to save photo '%s', description '%s'",
                    uploadedPhoto, description), response);
        }
        
        log.info(String.format("photos.save, response '%s'", response));

        String photoId = response.get("response").get(0).get("pid").asText();
        String ownerId = response.get("response").get(0).get("owner_id").asText();

        SavedPhoto savedPhoto = new SavedPhoto();
        savedPhoto.setPhotoId(photoId);
        savedPhoto.setOwnerId(ownerId);

        return savedPhoto;
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

    @Override
    public int getCommentsCount(String photoId, String ownerId) {
        Properties props = new Properties();

        props.put("photo_id", photoId);
        props.put("owner_id", ownerId);

        URI uri = makeOperationURL("photos.getComments", props);

        JsonNode response = restTemplate.getForObject(uri, JsonNode.class);

        log.info(String.format("photos.getComments, response '%s'", response));

        response = response.get("response");

        if (response == null) {
            return 0;
        }

        if (response.get(0) != null) {
            return response.get(0).asInt();
        }

        return response.asInt();
    }
    
    @Override
    public Calendar getCreated(String photoId, String ownerId, String albumId) {
        Properties props = new Properties();

        props.put("owner_id", ownerId);
        props.put("album_id", albumId);

        URI uri = makeOperationURL("photos.get", props);
    	
    	JsonNode response = restTemplate.getForObject(uri, JsonNode.class);
    	
    	log.info(String.format("photos.get, response '%s'", response));
    	
    	response = response.get("response");
    	
    	if (response == null || response.size() == 0) {
    		return null;
    	}
    	
    	JsonNode jsonNode = response.get(0);
    	
    	JsonNode createdNode = jsonNode.get("created");
    	if (createdNode != null && createdNode.asText() != null) {
    	    Calendar calendar = Calendar.getInstance();
    	    long created = Long.parseLong(createdNode.asText());
    	    calendar.setTimeInMillis(created * 1000l);
    	    return calendar;
    	}

    	return null;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public List<Photo> getPhotos(String ownerId, String albumId, int offset) {
        Properties props = new Properties();

        props.put("owner_id", ownerId);
        props.put("album_id", albumId);
        props.put("offset", offset);

        URI uri = makeOperationURL("photos.get", props);

        JsonNode response = restTemplate.getForObject(uri, JsonNode.class);

        log.info(String.format("photos.get, request '%s', response '%s'", uri, response));

        JsonNode error = response.get("error");
        if (error != null) {
            throw new PhotosOperationsException(String.format("Unable to get photos for group '%s',  album '%s'",
                    ownerId, albumId), response);
        }

        response = response.get("response");

        List<Photo> photos = new ArrayList<Photo>();
        if (response != null) {
            for (JsonNode jsonNode : response) {
                Photo photo = new Photo();
                photo.setPhotoId(jsonNode.get("pid").asText());
                photo.setDescription(jsonNode.get("text").textValue());
                photo.setUserId(jsonNode.get("user_id").asText());
                
                Calendar calendar = Calendar.getInstance();
                long created = Long.parseLong(jsonNode.get("created").asText());
                calendar.setTimeInMillis(created * 1000l);
                photo.setCreated(calendar);
                
                photos.add(photo);
            }
        }

        return photos;
    }

    @Override
    public List<String> getAlbumIds(String ownerId) {
        Properties props = new Properties();

        props.put("owner_id", ownerId);

        URI uri = makeOperationURL("photos.getAlbums", props);

        JsonNode response = restTemplate.getForObject(uri, JsonNode.class);

        log.info(String.format("photos.getAlbums, response '%s'", response));

        response = response.get("response");

        List<String> albumIds = new ArrayList<String>();
        if (response != null) {
            for (JsonNode jsonNode : response) {
                albumIds.add(jsonNode.get("aid").asText());
            }
        }

        return albumIds;
    }
}
