package com.abudko.reseller.huuto.query.service.item.html.stadium;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.html.HtmlParserConstants;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.html.AbstractHtmlItemParser;
import com.abudko.reseller.huuto.query.service.list.html.stadium.StadiumHtmlListParser;

@Component
public class StadiumHtmlItemParser extends AbstractHtmlItemParser {
    
    public void parseInternal(Document document, ItemResponse response) {
        List<String> sizes = parseSizes(document);
        response.setSizes(sizes);

        String price = parsePrice(document);
        response.setPrice(price);

        String imgSrc = parseImgSrc(document);
        response.setImgBaseSrc(imgSrc);

        String id = parseId(document);
        response.setId(id);
        
        String brand = parseBrand(document);
        response.getItemInfo().setBrand(brand);
    }

    private List<String> parseSizes(Document document) {
    	List<String> sizes = new ArrayList<>();
    	Elements elements = document.getElementsByClass("stock-web");
    	if (elements.isEmpty()) {
    		return sizes;
    	}
    	Element element = null;
    	if (elements.size() > 1) {
    		element = elements.get(1);
    	}
    	else {
    		element = elements.get(0);	
    	}
    	Elements available = element.getElementsByClass("available");
    	for (Element el: available) {
    		String size = el.text();
    		Elements sizeEU = el.getElementsByClass("size-eu");
    		if (sizeEU != null && sizeEU.size() > 0) {
    		    size = sizeEU.get(0).text();
    		}
    		if (size != null && !size.contains("Year") && !size.contains("US")) {
    		    sizes.add(size);
    		}
    	}

        return sizes;
    }

    private String parseImgSrc(Document document) {
    	Elements elements = document.getElementsByClass("product-detail-images");
    	Element element = elements.get(0);
    	Elements elements2 = element.getElementsByAttribute("src");
    	Element element2 = elements2.get(0);
    	String imgSrc = element2.attr("src");
        return formatImgSrc(imgSrc);
    }
    
    private String formatImgSrc(String s) {
        StringBuilder sb = new StringBuilder(HtmlParserConstants.STADIUM_FI);
        sb.append(s.replace(StadiumHtmlListParser.IMG_SRC_SMALL, StadiumHtmlListParser.IMG_SRC_BIG));

        return sb.toString();
    }
    
    private String parsePrice(Document document) {
    	Elements reduced = document.getElementsByClass("reduced");
    	Element price = null;
    	if (reduced != null && !reduced.isEmpty()) {
    		price = document.getElementsByClass("reduced").get(0);
    	}
    	else {
    		price = document.getElementsByClass("regular").get(0);
    	}
        String text = price.text();
        String formatPrice = formatPrice(text);

        return formatPrice;
    }
    
    private String formatPrice(String price) {
        String euro = String.format("%s ", HtmlParserConstants.EURO_CHAR);
        String priceDouble = price.replace(euro, "");
        priceDouble = priceDouble.replace(",-", "");
        return priceDouble.replace(",", ".");
    }
    
    private String parseId(Document document) {
        return "";
    }
    
    private String parseBrand(Document document) {
    	Elements elements = document.getElementsByClass("product-detail-header-heading-brand");
        if (elements.size() > 0) {
            Element element = elements.get(0);
            return element.text();
        }
        
        return null;
    }
}