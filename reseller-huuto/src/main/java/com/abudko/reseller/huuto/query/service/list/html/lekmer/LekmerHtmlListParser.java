package com.abudko.reseller.huuto.query.service.list.html.lekmer;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.abudko.reseller.huuto.query.enumeration.Brand;
import com.abudko.reseller.huuto.query.html.HtmlParserConstants;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.reseller.huuto.query.service.list.html.HtmlListParser;

@Component
public class LekmerHtmlListParser implements HtmlListParser {

    public static final String IMG_SRC_SMALL = "thumbnail168x169";
    public static final String IMG_SRC_BIG = "productmanMeasurement465x500";
    
    private static final String HTML_PRODUCT_LIST_CLASS = "product_list";
    private static final String HTML_PRODUCT_CLASS = "product round_corners_small three columns mobile-six end";
    private static final String HTML_PRODUCT_INFO_CLASS = "product_info";
    private static final String HTML_PRICE_SALE_CLASS = "price sale";
    private static final String HTML_PRICE_CLASS = "price";
    private static final String HTML_IMAGE_CLASS = "image_holder";

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
                
                String brand = parseBrand(description);
                queryResponse.setBrand(brand);
                
                responses.add(queryResponse);
            }
        }

        return responses;
    }
    
    private String parseItemUrl(Element element) {
        Element product_info = element.getElementsByClass(HTML_PRODUCT_INFO_CLASS).get(0);
        Attributes attributes = product_info.child(1).attributes();
        String itemUrl = attributes.get("href");
        
        if (StringUtils.isEmpty(itemUrl)) {
            attributes = product_info.child(0).attributes();
            itemUrl = attributes.get("href");
        }
        
        if (StringUtils.isEmpty(itemUrl)) {
            attributes = product_info.child(2).attributes();
            itemUrl = attributes.get("href");
        }
        
        return itemUrl;
    }
    
    private String parseDescription(Element element) {
        Element product_info = element.getElementsByClass(HTML_PRODUCT_INFO_CLASS).get(0);
        Element child = product_info.child(1);
        String description = child.ownText();
        
        if (StringUtils.isEmpty(description)) {
            description = product_info.child(0).ownText();
        }
        
        if (description.contains("Testivoittaja") || description.contains("Kesäale")) {
            description = product_info.child(2).ownText();
        }
        
        return description;
    }
    
    private String parseBrand(String description) {
        Brand brand = Brand.getBrandFrom(description);
        if (brand != null) {
            return brand.getFullName();
        }
        return null;
    }
    
    private String parseImgSrc(Element element) {
        Element image = element.getElementsByClass(HTML_IMAGE_CLASS).get(0);
        Element child = image.child(0).child(0);
        String imgSrc = child.attr("src");
        
        return formatImgSrc(imgSrc);
    }
    
    private String formatImgSrc(String s) {
        StringBuilder sb = new StringBuilder(HtmlParserConstants.LEKMER_FI);
        sb.append(s.replace(IMG_SRC_SMALL, IMG_SRC_BIG));
        
        return sb.toString();
    }
    
    private String parseCurrentPrice(Element element) {
        Element product_info = element.getElementsByClass(HTML_PRODUCT_INFO_CLASS).get(0);
        Elements elementsByClass = product_info.getElementsByAttributeValueContaining("class", HTML_PRICE_SALE_CLASS);
        
        if (elementsByClass.isEmpty()) {
            elementsByClass = product_info.getElementsByAttributeValueContaining("class", HTML_PRICE_CLASS);
        }
        
        Element price = elementsByClass.get(0);
        String formatPrice = formatPrice(price.ownText());
        
        return formatPrice;
    }
    
    private String formatPrice(String price) {
        String euro = String.format("%s ", HtmlParserConstants.EURO_CHAR);
        String priceDouble = price.replace(euro, "");
        return priceDouble.replace(",", ".");
    }
}
