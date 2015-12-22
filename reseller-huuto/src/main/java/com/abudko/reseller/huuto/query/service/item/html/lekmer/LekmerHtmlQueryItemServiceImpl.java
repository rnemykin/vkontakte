package com.abudko.reseller.huuto.query.service.item.html.lekmer;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.abudko.reseller.huuto.query.rules.AbstractPriceRules;
import com.abudko.reseller.huuto.query.rules.LekmerPriceRules;
import com.abudko.reseller.huuto.query.service.item.AbstractQueryItemService;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;

@Component
public class LekmerHtmlQueryItemServiceImpl extends AbstractQueryItemService {

    private Logger log = LoggerFactory.getLogger(getClass());
    
    @Resource
    protected LekmerPriceRules lekmerPriceRules;

    @Autowired
    private LekmerHtmlItemParser htmlItemParser;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    protected ItemResponse callAndParse(String itemId) {
        String itemUrl = constructFullItemUrl(itemId);

        log.info(String.format("Quering item: %s", itemUrl));
        
        String decode = null;
        try {
            decode = URLDecoder.decode(itemUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }

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
        return null;
    }
    
    protected AbstractPriceRules getPriceRules() {
        return lekmerPriceRules;
    }
}
