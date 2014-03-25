package com.abudko.scheduled.service.huuto;

import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

@Component
public class HuutoPublishManager extends AbstractPublishManager {

    @Override
    protected String getImgUrl(ListResponse listResponse) {
        ItemResponse itemResponse = listResponse.getItemResponse();
        return itemResponse.getImgBaseSrc() + "-orig.jpg";
    }
}
