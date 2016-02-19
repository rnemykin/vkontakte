package com.abudko.reseller.huuto.query.service.item.html.stadium;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.html.HtmlParserConstants;
import com.abudko.reseller.huuto.query.service.item.html.AbstractHtmlItemParser;
import com.abudko.reseller.huuto.query.service.list.html.stadium.StadiumHtmlListParser;

@Component
public class StadiumHtmlItemParser extends AbstractHtmlItemParser {
    
	@Override
    protected List<String> parseSizes(Document document) {
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
    		    String sizeEUText = sizeEU.get(0).text();
    		    if (sizeEUText != null && sizeEUText.length() > 0) {
    		    	size = sizeEUText;
    		    }
    		}
    		if (size != null && !size.contains("Year") && !size.contains("US")) {
    		    sizes.add(size);
    		}
    	}

        return sizes;
    }

	@Override
    protected String parseImgSrc(Document document) {
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
    
    @Override
    protected String parsePrice(Document document) {
    	Elements reduced = document.getElementsByClass("reduced");
    	Element price = null;
    	if (reduced != null && !reduced.isEmpty()) {
    		price = reduced.get(0);
    	}
    	else {
    	    Elements regular = document.getElementsByClass("regular");
    	    if (regular != null && !regular.isEmpty()) {
    	        price = regular.get(0);
    	    }
    	    else {
    	        return "";
    	    }
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
    
    @Override
    protected String parseId(Document document) {
        return "";
    }
    
    @Override
    protected String parseBrand(Document document) {
    	Elements elements = document.getElementsByClass("product-detail-header-heading-brand");
        if (elements.size() > 0) {
            Element element = elements.get(0);
            return element.text();
        }
        
        return null;
    }
}