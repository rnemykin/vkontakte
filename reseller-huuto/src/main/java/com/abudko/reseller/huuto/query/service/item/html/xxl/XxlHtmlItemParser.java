package com.abudko.reseller.huuto.query.service.item.html.xxl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.abudko.reseller.huuto.query.enumeration.Brand;
import com.abudko.reseller.huuto.query.html.HtmlParserConstants;
import com.abudko.reseller.huuto.query.service.item.html.AbstractHtmlItemParser;

@Component
public class XxlHtmlItemParser extends AbstractHtmlItemParser {

    @Override
    protected List<String> parseSizes(Document document) {
    	List<String> sizes = new ArrayList<>();
    	Elements elements = document.getElementsByClass("sizeItem");
    	if (elements.isEmpty()) {
    		return sizes;
    	}
    	for (Element element : elements) {
    		String size = element.text();
    		if (!StringUtils.isEmpty(size) && !element.classNames().contains("notInStock")) {
    			sizes.add(size);
    		}
		}

        return sizes;
    }

    @Override
    protected String parseImgSrc(Document document) {
    	Elements elements = document.getElementsByClass("product-image-large");
        if (elements.size() > 0) {
            Element element = elements.get(0);
            Element child = element.child(0);
            Elements elementsByAttribute = child.getElementsByAttribute("src");
            String imgSrc = elementsByAttribute.get(0).attr("src");
            return formatImgSrc(imgSrc);
        }
        return "";
    }
    
	private String formatImgSrc(String s) {
		StringBuilder sb = new StringBuilder(HtmlParserConstants.XXL_FI);
		sb.append(s);

		return sb.toString();
	}
    
	@Override
	protected String parsePrice(Document document) {
    	Elements elements = document.getElementsByClass("bigPrice");
    	if (elements.isEmpty()) {
    		return null;
    	}
    	Element element = elements.get(0);
    	String price = element.text();
    	return price.replace(HtmlParserConstants.EURO_CHAR, "").trim().replace(",", ".");
    }
    
	@Override
	protected String parseId(Document document) {
        Elements elements = document.getElementsByClass("productDetailPageWrapper");
        if (elements.isEmpty()) {
        	return null;
        }
        String attr = elements.get(0).child(0).attr("data-id");
        int i = attr.lastIndexOf("_");
        if (i >= 0) {
        	attr = attr.substring(0, i);
        }
        return HtmlParserConstants.XXL_ID_PREFIX + attr;
    }
    
	@Override
    protected String parseBrand(Document document) {
    	 Elements elements = document.getElementsByClass("productDetailPageWrapper");
    	 if (elements.isEmpty()) {
    		 return null;
    	 }
         String brandAttr = elements.get(0).child(0).attr("data-brand");
         Brand brand = Brand.getBrandFrom(brandAttr);
         if (brand != null) {
             return brand.getFullName();
         }
         return null;
    }
}