package com.abudko.reseller.huuto.query.service.item.html.reima;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.abudko.reseller.huuto.query.html.HtmlParserConstants;
import com.abudko.reseller.huuto.query.rules.AbstractPriceRules;
import com.abudko.reseller.huuto.query.rules.LekmerPriceRules;
import com.abudko.reseller.huuto.query.service.item.AbstractQueryItemService;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.ItemStatus;

@Component
public class ReimaHtmlQueryItemServiceImpl extends AbstractQueryItemService {

    private Logger log = LoggerFactory.getLogger(getClass());
    
    @Resource
    protected LekmerPriceRules lekmerPriceRules;

    @Autowired
    private ReimaHtmlItemParser htmlItemParser;

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

		String html = "";

		try {
			html = restTemplate.getForObject(decode, String.class);
		} catch (HttpClientErrorException e) {
			if (HttpStatus.NOT_FOUND_404 != e.getStatusCode().value()) {
				log.info("Exeption during calling reima list : " + e);
			}
		}
		
        ItemResponse itemResponse = parseResponseFromHtml(html);
        itemResponse.setItemUrl(itemUrl);
        
        if (itemResponse.getId() != null && !itemResponse.getSizes().isEmpty()) {
        	itemResponse.setItemStatus(ItemStatus.OPENED);
        }

        return itemResponse;
    }

    private String constructFullItemUrl(String itemUrl) {
        StringBuilder sb = new StringBuilder(HtmlParserConstants.REIMA_FI);
        sb.append("/");
        if (!itemUrl.contains("/")) {
        	sb.append("p/");
        }
        sb.append(itemUrl);

        return sb.toString();
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
