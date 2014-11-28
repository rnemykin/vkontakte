package com.abudko.reseller.huuto.query.service.item.html.city;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.QueryConstants;
import com.abudko.reseller.huuto.query.rules.AbstractPriceRules;
import com.abudko.reseller.huuto.query.rules.CityPriceRules;
import com.abudko.reseller.huuto.query.service.item.AbstractQueryItemService;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.webclient.city.CityWebClient;

@Component
public class CityHtmlQueryItemServiceImpl extends AbstractQueryItemService {

    private Logger log = LoggerFactory.getLogger(getClass());
    
    @Resource
    protected CityPriceRules priceRules;

    @Autowired
    private CityHtmlItemParser htmlItemParser;

    @Autowired
    private CityWebClient webClient;

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

        String html = webClient.call(decode);
        
        ItemResponse itemResponse = parseResponseFromHtml(html);
        itemResponse.setItemUrl(itemUrl);

        return itemResponse;
    }

    private String constructFullItemUrl(String itemId) {
        return String.format(QueryConstants.CITY_HTML_SEARCH_URL_FORMAT, itemId);
    }

    private ItemResponse parseResponseFromHtml(String html) {
        ItemResponse itemResponse = htmlItemParser.parse(html);
        return itemResponse;
    }
    
    protected String extractIdFromUrl(String urlSuffix) {
        return null;
    }
    
    protected AbstractPriceRules getPriceRules() {
        return priceRules;
    }
}
