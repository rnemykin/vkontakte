package com.abudko.reseller.huuto.query.service.item.html;

import java.util.List;

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
            String id = parseId(document);
            response.setId(id);
            
            String imgSrc = parseImgSrc(document);
            response.setImgBaseSrc(imgSrc);
            
            List<String> sizes = parseSizes(document);
            response.setSizes(sizes);

            String price = parsePrice(document);
            response.setPrice(price);

            String brand = parseBrand(document);
            response.getItemInfo().setBrand(brand);
        }
        catch (Exception e) {
        	log.error("Error while parsing item response " + response, e);
        	throw e;
        }
        
		return response;
	}
	
	protected abstract String parseId(Document document);
	protected abstract String parsePrice(Document document);
	protected abstract List<String> parseSizes(Document document);
	protected abstract String parseImgSrc(Document document);
	protected abstract String parseBrand(Document document);

}
