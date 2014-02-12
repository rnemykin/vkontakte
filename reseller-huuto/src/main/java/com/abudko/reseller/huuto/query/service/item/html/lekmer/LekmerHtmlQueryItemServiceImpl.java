package com.abudko.reseller.huuto.query.service.item.html.lekmer;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.abudko.reseller.huuto.query.html.HtmlParserConstants;
import com.abudko.reseller.huuto.query.rules.PriceRules;
import com.abudko.reseller.huuto.query.service.item.AbstractQueryItemService;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;

@Component
public class LekmerHtmlQueryItemServiceImpl extends AbstractQueryItemService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private LekmerHtmlItemParser htmlItemParser;

    @Resource
    private PriceRules priceRules;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ItemResponse callAndParse(String urlSuffix) {
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
}
