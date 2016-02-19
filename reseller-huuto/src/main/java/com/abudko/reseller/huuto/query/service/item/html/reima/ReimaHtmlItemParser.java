package com.abudko.reseller.huuto.query.service.item.html.reima;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.abudko.reseller.huuto.query.enumeration.Brand;
import com.abudko.reseller.huuto.query.html.HtmlParserConstants;
import com.abudko.reseller.huuto.query.service.item.html.AbstractHtmlItemParser;

@Component
public class ReimaHtmlItemParser extends AbstractHtmlItemParser {
    
    private static final String HTML_PRODUCT_ID = "prod";
    private static final String HTML_PRODUCT_IMAGE = "prod_image_main";
    private static final String HTML_PRODUCT_SIZE = "size-choice";
    
    @Override
    protected List<String> parseSizes(Document document) {
    	List<String> sizes = new ArrayList<>();
    	Elements elements = document.getElementsByClass(HTML_PRODUCT_SIZE);
    	if (elements.isEmpty()) {
    		return sizes;
    	}
    	Node sizeNode = elements.get(0).childNode(1);
    	List<Node> childNodes = sizeNode.childNodes();
    	for (Node node : childNodes) {
    		String size = node.attributes().get("value");
    		if (!StringUtils.isEmpty(size)) {
    			sizes.add(size);
    		}
		}

        return sizes;
    }

    @Override
    protected String parseImgSrc(Document document) {
    	Elements elements = document.getElementsByClass(HTML_PRODUCT_IMAGE);
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
		StringBuilder sb = new StringBuilder(HtmlParserConstants.REIMA_FI);
		sb.append(s);

		return sb.toString();
	}
    
	@Override
	protected String parsePrice(Document document) {
    	Elements elements = document.getElementsByClass(HTML_PRODUCT_ID);
    	if (elements.size() > 1) {
            Element element = elements.get(1);
            Element child = element.child(2);
            Elements allElements = child.getAllElements();
            for (Element el : allElements) {
            	if (el.attributes() != null && el.attributes().size() > 0) {
            		String style = el.attributes().get("style");
            		if (style.contains("red")) {
            			String price = el.text();
            			return price.replace(HtmlParserConstants.EURO_CHAR, "").trim().replace(",", ".");
            		}
            	}
			}
        }
    	return "";
    }
    
	@Override
	protected String parseId(Document document) {
        Elements elements = document.getElementsByClass(HTML_PRODUCT_ID);
        if (elements.size() > 1) {
            Element element = elements.get(1);
            Element child = element.child(1);
            return HtmlParserConstants.REIMA_ID_PREFIX + getValidId(child.text());
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
        if (elements.size() > 1) {
            Element element = elements.get(1);
            Element child = element.child(0);
            String text = child.text();
            return text.toUpperCase().contains("REIMATEC") ? Brand.REIMATEC.name() : Brand.REIMA.name();
        }
        
        return null;
    }
}