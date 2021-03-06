package com.abudko.reseller.huuto.query.service.item.html.huuto;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.abudko.reseller.huuto.query.html.HtmlParserConstants;
import com.abudko.reseller.huuto.query.rules.AbstractPriceRules;
import com.abudko.reseller.huuto.query.rules.HuutoPriceRules;
import com.abudko.reseller.huuto.query.service.item.AbstractQueryItemService;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.html.HtmlItemParser;

@Component
public class HuutoHtmlQueryItemServiceImpl extends AbstractQueryItemService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("huutoHtmlItemParser")
    private HtmlItemParser htmlItemParser;

    @Resource
    private HuutoPriceRules priceRules;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    protected ItemResponse callAndParse(String urlSuffix) {
        String itemUrl = constructFullItemUrl(urlSuffix);

        log.info(String.format("Quering item: %s", itemUrl));

        String html = restTemplate.getForObject(itemUrl, String.class);
        ItemResponse itemResponse = parseResponseFromHtml(html);
        itemResponse.setItemUrl(itemUrl);

        return itemResponse;
    }

    private String constructFullItemUrl(String itemUrl) {
        StringBuilder sb = new StringBuilder(HtmlParserConstants.HUUTO_NET);
        sb.append(HtmlParserConstants.ITEM_URL_CONTEXT);
        sb.append(itemUrl);

        return sb.toString();
    }

    private ItemResponse parseResponseFromHtml(String html) {
        ItemResponse itemResponse = htmlItemParser.parse(html);
        return itemResponse;
    }
    
    protected String extractIdFromUrl(String urlSuffix) {
        if (urlSuffix == null) {
            return null;
        }

        int indexOf = urlSuffix.lastIndexOf("/");

        if (indexOf < 0) {
            return null;
        }

        String id = urlSuffix.substring(indexOf + 1);
        return id;
    }
    
    protected AbstractPriceRules getPriceRules() {
        return defaultPriceRules;
    }
}
