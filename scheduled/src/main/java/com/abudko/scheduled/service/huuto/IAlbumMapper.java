package com.abudko.scheduled.service.huuto;

import java.util.Collection;

import com.abudko.reseller.huuto.query.service.list.ListResponse;

public interface IAlbumMapper {
    
    String getGroupId();
    
    Collection<String> getAlbumIds(String category);
    
    String getAlbumId(String category, ListResponse listResponse);
    
    String getAlbumIdForBrand(ListResponse listResponse);

}
