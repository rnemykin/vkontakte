package com.abudko.scheduled.service.huuto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

@Component
public class GroupPublishManager extends AbstractPublishManager {
    
    @Autowired
    protected AlbumMapper albumMapper;

    @Override
    protected String getImgUrl(ListResponse listResponse) {
        ItemResponse itemResponse = listResponse.getItemResponse();
        return itemResponse.getImgBaseSrc();
    }

    @Override
    protected IAlbumMapper getAlbumMapper() {
        return albumMapper;
    }
}
    