package com.abudko.reseller.huuto.query.service.list.html.xxl;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.enumeration.Brand;
import com.abudko.reseller.huuto.query.html.HtmlParserConstants;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.reseller.huuto.query.service.list.html.HtmlListParser;

@Component
public class XxlHtmlListParser implements HtmlListParser {

    private static final String HTML_PRODUCT_LIST_CLASS = "prodList row quickViewGallery direct";
    private static final String HTML_PRODUCT_CLASS = "span3 product";

    @Override
    public Collection<ListResponse> parse(String htmlResponse) {
        Collection<ListResponse> responses = new LinkedHashSet<ListResponse>();

        Document document = Jsoup.parse(htmlResponse);

        Elements productList = document.getElementsByAttributeValueContaining("class", HTML_PRODUCT_LIST_CLASS);
        if (productList != null && !productList.isEmpty()) {
            Elements products = productList.get(0).getElementsByAttributeValueContaining("class", HTML_PRODUCT_CLASS);
            for (Element product : products) {
                ListResponse queryResponse = new ListResponse();

                String itemUrl = parseItemUrl(product);
                queryResponse.setItemUrl(itemUrl);
                
                String description = parseDescription(product);
                queryResponse.setDescription(description);
                
                String imgBaseSrc = parseImgSrc(product);
                queryResponse.setImgBaseSrc(imgBaseSrc);
                
                String currentPrice = parseCurrentPrice(product);
                queryResponse.setCurrentPrice(currentPrice);
                
                String brand = parseBrand(product);
                queryResponse.setBrand(brand);
                
                responses.add(queryResponse);
            }
        }

        return responses;
    }
    
    private String parseItemUrl(Element element) {
        Element info = element.getElementsByClass("image").get(0);
        Attributes attributes = info.child(0).attributes();
        String itemUrl = attributes.get("href");
        
        return itemUrl;
    }
    
    private String parseDescription(Element element) {
    	String description = element.attr("data-name");
        return description;
    }
    
    private String parseBrand(Element element) {
    	String brandAttr = element.attr("data-name");
        Brand brand = Brand.getBrandFrom(brandAttr);
        if (brand != null) {
            return brand.getFullName();
        }
        return null;
    }
    
    private String parseImgSrc(Element element) {
    	Element info = element.getElementsByClass("image").get(0);
        Elements elementsByAttribute = info.getElementsByAttribute("src");
        String imgSrc = elementsByAttribute.get(0).attr("src");
        
        return formatImgSrc(imgSrc);
    }
    
    private String formatImgSrc(String s) {
        StringBuilder sb = new StringBuilder(HtmlParserConstants.XXL_FI);
        sb.append(s);
        
        return sb.toString();
    }
    
    private String parseCurrentPrice(Element element) {
    	String price = element.attr("data-price");
        String formatPrice = formatPrice(price);
        
        return formatPrice;
    }
    
    private String formatPrice(String price) {
        String euro = String.format("%s ", HtmlParserConstants.EURO_CHAR);
        String priceDouble = price.replace(euro, "");
        return priceDouble.replace(",", ".");
    }
}
