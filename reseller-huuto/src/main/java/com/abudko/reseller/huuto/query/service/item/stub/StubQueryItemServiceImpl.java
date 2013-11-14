package com.abudko.reseller.huuto.query.service.item.stub;

import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.service.item.AbstractQueryItemService;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;

@Component
public class StubQueryItemServiceImpl extends AbstractQueryItemService {

    @Override
    public ItemResponse callAndParse(String itemUrl) {
        return getStubItem();
    }

    private ItemResponse getStubItem() {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setLocation("location");
        itemResponse.setCondition("condition");
        itemResponse.setImgBaseSrc("imgSrc");
        itemResponse.setItemUrl("itemUrl");
        itemResponse.setPrice("2.00");

        return itemResponse;
    }
    
    protected String extractIdFromUrl(String urlSuffix) {
        return "65322623";
    }

}
