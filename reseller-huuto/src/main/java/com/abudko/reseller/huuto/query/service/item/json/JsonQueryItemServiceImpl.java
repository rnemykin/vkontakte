package com.abudko.reseller.huuto.query.service.item.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.abudko.reseller.huuto.query.QueryConstants;
import com.abudko.reseller.huuto.query.rules.AbstractPriceRules;
import com.abudko.reseller.huuto.query.service.item.AbstractQueryItemService;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;

@Component
public class JsonQueryItemServiceImpl extends AbstractQueryItemService {

    @Autowired
    private RestTemplate restTemplate;
    
    @Override
    protected ItemResponse callAndParse(String urlSuffix) {
        String itemUrl = constructFullItemUrl(urlSuffix);

        log.info(String.format("Quering item: %s", itemUrl));

        JsonItemResponse jsonResponse = restTemplate.getForObject(itemUrl, JsonItemResponse.class);

        ItemResponse itemResponse = jsonResponse.getItemResponse();
        itemResponse.setItemUrl(itemUrl);

        return itemResponse;
    }

    private String constructFullItemUrl(String urlSuffix) {
        String id = extractIdFromUrl(urlSuffix);
        StringBuilder sb = new StringBuilder(QueryConstants.JSON_QUERY_URL);
        sb.append("/");
        sb.append(id);

        return sb.toString();
    }
    
    protected String extractIdFromUrl(String urlSuffix) {
        int index = urlSuffix.lastIndexOf("/");
        return urlSuffix.substring(index + 1);
    }
    
    protected AbstractPriceRules getPriceRules() {
        return defaultPriceRules;
    }
}
