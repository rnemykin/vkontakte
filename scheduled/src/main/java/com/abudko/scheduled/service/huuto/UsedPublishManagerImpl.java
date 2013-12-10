package com.abudko.scheduled.service.huuto;

import java.util.Locale;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.enumeration.Category;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.scheduled.csv.PhotoData;

@Component
public class UsedPublishManagerImpl extends AbstractPublishManager {

    @Override
    protected PhotoData getPhotoData(Category category, ListResponse listResponse) {
        PhotoData photoData = new PhotoData();
        photoData.setGroupId(AlbumMapper.GROUP_ID);
        photoData.setAlbumId(AlbumMapper.USED_ALBUM_ID);
        photoData.setDescription(getDescription(category, listResponse));
        photoData.setFileResource(new FileSystemResource(imageTempFileLocation));
        
        return photoData;
    }

    @Override
    protected String getDescription(Category category, ListResponse listResponse) {
        ItemResponse itemResponse = listResponse.getItemResponse();
        String newPrice = itemResponse.getNewPrice();
        String id = itemResponse.getId();
        String size = listResponse.getSize();
        String brand = listResponse.getBrand();
        String title = "Б.У";
        
        String description = context.getMessage(COMMENT_KEY, new Object[] { title, brand, size, newPrice, id },
                Locale.getDefault());
        
        return description;
    }

}
