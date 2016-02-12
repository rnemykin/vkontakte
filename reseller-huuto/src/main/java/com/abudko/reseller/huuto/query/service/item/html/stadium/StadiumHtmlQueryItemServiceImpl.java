package com.abudko.reseller.huuto.query.service.item.html.stadium;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.abudko.reseller.huuto.query.html.HtmlParserConstants;
import com.abudko.reseller.huuto.query.rules.AbstractPriceRules;
import com.abudko.reseller.huuto.query.rules.ReimaPriceRules;
import com.abudko.reseller.huuto.query.service.item.AbstractQueryItemService;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;

@Component
public class StadiumHtmlQueryItemServiceImpl extends AbstractQueryItemService {

    private Logger log = LoggerFactory.getLogger(getClass());
    
    @Resource
    protected ReimaPriceRules priceRules;

    @Autowired
    private StadiumHtmlItemParser htmlItemParser;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    protected ItemResponse callAndParse(String itemId) {
        String itemUrl = constructFullItemUrl(itemId);

        log.info(String.format("Quering item: %s", itemUrl));
        
        String decode = decodeUrl(itemUrl);
        
        String html = restTemplate.getForObject(decode, String.class);
        ItemResponse itemResponse = parseResponseFromHtml(html);
        itemResponse.setItemUrl(decode);

        return itemResponse;
    }

    private String constructFullItemUrl(String itemId) {
        return itemId;
    }

    private ItemResponse parseResponseFromHtml(String html) {
        ItemResponse itemResponse = htmlItemParser.parse(html);
        return itemResponse;
    }
    
    protected String extractIdFromUrl(String urlSuffix) {
    	int lastIndex = urlSuffix.lastIndexOf("/");
    	urlSuffix = urlSuffix.substring(0, lastIndex);
    	int lastIndex2 = urlSuffix.lastIndexOf("/");
        String id = urlSuffix.substring(lastIndex2 + 1, urlSuffix.length());
        
        return HtmlParserConstants.STADIUM_ID_PREFIX + id;
    }
    
    protected AbstractPriceRules getPriceRules() {
        return priceRules;
    }
}
