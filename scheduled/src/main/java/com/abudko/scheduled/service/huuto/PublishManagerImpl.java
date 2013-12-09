package com.abudko.scheduled.service.huuto;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.enumeration.Category;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.scheduled.csv.PhotoData;

@Component
public class PublishManagerImpl extends AbstractPublishManager {

    @Override
    protected PhotoData getPhotoData(Category category, ListResponse listResponse) {
        PhotoData photoData = new PhotoData();
        photoData.setGroupId(AlbumMapper.GROUP_ID);
        photoData.setAlbumId(albumMapper.getAlbumId(category.name(), Integer.valueOf(listResponse.getSize())));
        photoData.setDescription(getDescription(category, listResponse));
        photoData.setFileResource(new FileSystemResource(imageTempFileLocation));
        
        return photoData;
    }
}
