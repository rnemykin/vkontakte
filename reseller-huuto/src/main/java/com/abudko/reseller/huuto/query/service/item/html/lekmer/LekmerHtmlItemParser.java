package com.abudko.reseller.huuto.query.service.item.html.lekmer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.html.HtmlParserConstants;
import com.abudko.reseller.huuto.query.service.item.html.AbstractHtmlItemParser;

@Component
public class LekmerHtmlItemParser extends AbstractHtmlItemParser {
    
    private static final String HTML_PRODUCT_ID = "product_band";
    
    private Logger log = LoggerFactory.getLogger(getClass());
    
    @Override
    protected List<String> parseSizes(Document document) {
        List<String> sizes = new ArrayList<>();
        Elements elements = document.getElementsByAttributeValueMatching("class", Pattern.compile("[^0]erpseparator\\w+"));
        for (Element element : elements) {
            String text = element.text();
            if (text.length() > 2) {
                if (!text.contains("/")) {
                    sizes.add(text.substring(0, 3).trim());
                }
                else {
                    try {
                        int length = text.trim().length();
                        int slashindex = text.indexOf("/");
                        int spaceindex = text.indexOf(" ");
                        String s1 = text.substring(0, slashindex).trim();
                        String s2 = text.substring(slashindex + 1, spaceindex < 0 ? length : spaceindex).trim();
                        sizes.add(s1);
                        sizes.add(s2);
                    }
                    catch (Exception e) {
                        log.error("Unable to parse size: " + text);
                    }
                }
            }
            else {
                sizes.add(text.substring(0, 2).trim());
            }
        }
        
        sizes.remove("Bab");
        
        return sizes;
    }

    @Override
    protected String parseImgSrc(Document document) {
        Elements elements = document.getElementsByAttributeValue("rel", "image_src");
        if (elements.size() > 0) {
            Element element = elements.get(0);
            String imgSrc = element.attributes().get("href");
            if (imgSrc.startsWith("http://lekmer.fihttp")) {
                imgSrc = imgSrc.replace("http://lekmer.fihttp", "http");
            }
            return formatImgSrc(imgSrc);
        }
        return "";
    }
    
    String formatImgSrc(String imgSrc) {
    	if (!imgSrc.contains("http://")) {
    		return "http://lekmer.fi" + imgSrc;
    	}
    	
    	return imgSrc;
    }
    
    @Override
    protected String parsePrice(Document document) {
        Elements elements = document.getElementsByAttributeValue("class", "campaignprice-value");
        if (elements.size() == 0) {
            elements = document.getElementsByAttributeValue("class", "price-value");
        }
        if (elements.size() > 0) {
            Element element = elements.get(0);
            String price = element.childNode(0).toString();
            return price.replace(HtmlParserConstants.EURO_CHAR, "").trim().replace(",", ".");
        }
        return "";
    }
    
    @Override
    protected String parseId(Document document) {
        Elements elements = document.getElementsByClass(HTML_PRODUCT_ID);
        if (elements.size() > 0) {
            Element element = elements.get(0);
            Element child = element.child(0);
            if (child.children().size() > 1) {
                Element childChild = child.child(1);
                return HtmlParserConstants.LEKMER_ID_PREFIX + getValidId(childChild.ownText());
            }
        }
        return "";
    }
    
    protected String getValidId(String id) {
        try {
            Long.parseLong(id);
        }
        catch (NumberFormatException e) {
            return id;
        }

        Matcher matcher = Pattern.compile("[1-9][0-9]{9,1000}$").matcher(id);
        if (matcher.find()) {
            return id.substring(0, 9);
        }
        
        return id;
    }
    
    @Override
    protected String parseBrand(Document document) {
        Elements elements = document.getElementsByClass(HTML_PRODUCT_ID);
        if (elements.size() > 0) {
            Element element = elements.get(0);
            Element child = element.child(0);
            if (child.children().size() > 0) {
                Element childChild = child.child(0);
                return childChild.ownText();
            }
        }
        
        return null;
    }
}