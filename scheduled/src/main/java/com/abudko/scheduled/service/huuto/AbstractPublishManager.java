package com.abudko.scheduled.service.huuto;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;

import com.abudko.reseller.huuto.image.ImageManipulator;
import com.abudko.reseller.huuto.query.enumeration.Category;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.scheduled.csv.PhotoData;
import com.abudko.scheduled.service.PhotoManager;
import com.abudko.scheduled.vkontakte.Photo;

public abstract class AbstractPublishManager implements PublishManager {

    protected static final String COMMENT_KEY = "photo.description";
    protected static final String GROUP_URL = "vk.com/kombezi.finland";

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("groupPhotoManager")
    protected PhotoManager photoManager;

    @Autowired
    protected ApplicationContext context;

    @Value("#{scheduledProperties['imageTempFile']}")
    protected String imageTempFileLocation;

    @Autowired
    private ImageManipulator imageManipulator;

    @Autowired
    protected PublishManagerUtils publishManagerUtils;

    @Override
    public void publishResults(Category category, Collection<ListResponse> queryResponses) throws InterruptedException,
            UnsupportedEncodingException {
        for (ListResponse queryResponse : queryResponses) {
            try {
                log.info(String.format("Publishing query response: %s", queryResponse.toString()));

                List<PhotoData> photoDatas = convert(category, queryResponse);
                for (PhotoData photoData : photoDatas) {
                    if (photoData.getAlbumId() == null) {
                        log.warn(String.format("Unable to extract albumid for '%s' for category '%s'", queryResponse,
                                category.name()));
                        continue;
                    }

                    String id = queryResponse.getItemResponse().getId();
                    if (isPhotoPublished(id, photoData) == false) {
                        log.info(String.format("Photo with id '%s' is not published", id));
                        photoManager.publishPhoto(photoData);
                    } else {
                        log.info(String.format("Photo with id '%s' already published", id));
                    }
                }
            } catch (Throwable e) {
                String error = String.format("Error '%s' happened while publishing queryResponse '%s'", e.getMessage(),
                        queryResponse);
                log.error(error, e.getMessage());
                throw e;
            }
        }
    }

    protected List<PhotoData> convert(Category category, ListResponse listResponse) throws UnsupportedEncodingException {
        cropImage(listResponse);
        
        List<PhotoData> results = new ArrayList<>();
        results.add(getPhotoData(category, listResponse));
        
        PhotoData photoDataForBrand = getPhotoDataForBrand(category, listResponse);
        if (photoDataForBrand != null) {
        	// no need to duplicate
            // results.add(photoDataForBrand);
        }
        
        return results;
    }

    protected abstract String getImgUrl(ListResponse listResponse);

    private void cropImage(ListResponse listResponse) {
        String url = getImgUrl(listResponse);
        
        if (url != null && url.contains("huuto.net")) {
        	// no need to add advert.
        	imageManipulator.storeImage(url, "file:" + imageTempFileLocation, null);
        }
    }

    protected PhotoData getPhotoData(Category category, ListResponse listResponse) {
        PhotoData photoData = new PhotoData();
        photoData.setGroupId(getAlbumMapper().getGroupId());
        photoData.setAlbumId(getAlbumMapper().getAlbumId(category.name(), listResponse));
        photoData.setDescription(getDescription(category, listResponse));
        photoData.setFileResource(new FileSystemResource(imageTempFileLocation));

        return photoData;
    }
    
    protected PhotoData getPhotoDataForBrand(Category category, ListResponse listResponse) {
        String albumIdForBrand = getAlbumMapper().getAlbumIdForBrand(listResponse);
        if (albumIdForBrand == null) {
            return null;
        }
        PhotoData photoData = new PhotoData();
        photoData.setGroupId(getAlbumMapper().getGroupId());
        photoData.setAlbumId(albumIdForBrand);
        photoData.setDescription(getDescription(category, listResponse));
        photoData.setFileResource(new FileSystemResource(imageTempFileLocation));
        
        return photoData;
    }

    protected String getDescription(Category category, ListResponse listResponse) {
        ItemResponse itemResponse = listResponse.getItemResponse();
        String newPrice = itemResponse.getNewPrice();
        String id = itemResponse.getId();
        String size = listResponse.hasManySizes() ? listResponse.getItemResponse().getSizes().toString()
                .replaceAll("\\[|\\]", "") : listResponse.getSize();
        String brand = listResponse.getBrand();
        String title = category.getCommonLabel();
        String base64 = publishManagerUtils.encodeBase64(listResponse);

        String description = context.getMessage(COMMENT_KEY, new Object[] { title, brand, size, newPrice, id, base64 },
                Locale.getDefault());

        return description;
    }

    protected boolean isPhotoPublished(String id, PhotoData photoData) throws InterruptedException {
        List<Photo> photos = photoManager.getPhotos(photoData.getGroupId(), photoData.getAlbumId());
        for (Photo photo : photos) {
            String description = photo.getDescription();
            String descriptionId = publishManagerUtils.getId(description);
            if (id.equals(descriptionId)) {
                return true;
            }
        }

        return false;
    }
    
    protected abstract IAlbumMapper getAlbumMapper();
}
