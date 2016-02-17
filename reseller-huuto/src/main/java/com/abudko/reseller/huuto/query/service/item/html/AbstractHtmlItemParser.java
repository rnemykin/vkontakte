package com.abudko.reseller.huuto.query.service.item.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;

public abstract class AbstractHtmlItemParser implements HtmlItemParser {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	public ItemResponse parse(String html) {
		ItemResponse response = new ItemResponse();
        Document document = Jsoup.parse(html);
        
        try {
        	parseInternal(document, response);
        }
        catch (Exception e) {
        	log.error("Error while parsing item response " + response, e);
        	throw e;
        }
        
		return response;
	}
	
	abstract public void parseInternal(Document document, ItemResponse response);

}
