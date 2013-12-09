package com.abudko.scheduled.service.huuto;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

import com.abudko.reseller.huuto.query.enumeration.Category;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

public interface PublishManager {

    void publishResults(Category category, Collection<ListResponse> queryResponses) throws InterruptedException,
            UnsupportedEncodingException;

}