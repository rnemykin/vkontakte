package com.abudko.scheduled.service.huuto;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.enumeration.Category;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.scheduled.csv.PhotoData;
import com.abudko.scheduled.vkontakte.Photo;

@Component
public class HuutoUsedPublishManager extends HuutoPublishManager {
    
    @Value("#{userProperties['userid']}")
    private String userid;

    @Override
    public void publishResults(Category category, Collection<ListResponse> queryResponses) throws InterruptedException,
            UnsupportedEncodingException {
        for (ListResponse queryResponse : queryResponses) {
            try {
                log.info(String.format("Publishing query response: %s", queryResponse.toString()));

                List<PhotoData> photoDatas = convert(category, queryResponse);
                for (PhotoData photoData : photoDatas) {

                    String id = queryResponse.getItemResponse().getId();
                    if (isPhotoPublished(id, photoData) == false) {
                        photoManager.publishPhoto(photoData);
                    } else {
                        log.info(String.format("Photo with id '%s' already published", id));
                    }
                }
            } catch (Throwable e) {
                String error = String.format("Error '%s' happened while publishing queryResponse '%s'", e.getMessage(), queryResponse);
                log.error(error, e.getMessage());
                throw e;
            }
        }
    }
    
    @Override
    protected boolean isPhotoPublished(String id, PhotoData photoData) throws InterruptedException {
        List<Photo> photos = photoManager.getPhotos("user" + photoData.getGroupId(), photoData.getAlbumId());
        for (Photo photo : photos) {
            String description = photo.getDescription();
            String descriptionId = publishManagerUtils.getId(description);
            if (id.equals(descriptionId)) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected PhotoData getPhotoData(Category category, ListResponse listResponse) {
        PhotoData photoData = new PhotoData();
        photoData.setGroupId(userid);
        photoData.setAlbumId(AlbumMapper2.USED_ALBUM_ID);
        photoData.setDescription(getDescription(listResponse));
        photoData.setFileResource(new FileSystemResource(imageTempFileLocation));

        return photoData;
    }
    
    protected String getDescription(ListResponse listResponse) {
        ItemResponse itemResponse = listResponse.getItemResponse();
        String newPrice = itemResponse.getNewPrice();
        String id = itemResponse.getId();
        String size = listResponse.hasManySizes() ? listResponse.getItemResponse().getSizes().toString()
                .replaceAll("\\[|\\]", "") : listResponse.getSize();
        String brand = listResponse.getBrand();
        String title = "USED";
        String base64 = publishManagerUtils.encodeBase64(listResponse);

        String description = context.getMessage(COMMENT_KEY, new Object[] { title, brand, size, newPrice, id, base64 },
                Locale.getDefault());

        return description;
    }
}
