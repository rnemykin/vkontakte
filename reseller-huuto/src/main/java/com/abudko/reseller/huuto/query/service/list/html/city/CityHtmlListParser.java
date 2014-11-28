package com.abudko.reseller.huuto.query.service.list.html.city;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.reseller.huuto.query.service.list.html.HtmlListParser;

@Component
public class CityHtmlListParser implements HtmlListParser {

    @Override
    public Collection<ListResponse> parse(String htmlResponse) {
        Collection<ListResponse> responses = new LinkedHashSet<ListResponse>();

        Document document = Jsoup.parse(htmlResponse);

        Elements productList = document.getElementsByAttributeValueContaining("class", "search-results");
        if (productList != null && !productList.isEmpty()) {
            Elements products = productList.get(0).getElementsByAttributeValueContaining("class", "item-container");
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
        Elements elements = element.getElementsByAttribute("href");
        Element itemUrlElement = elements.get(0);
        return itemUrlElement.attributes().get("href");
    }
    
    private String parseDescription(Element element) {
        Elements descriptionElement = element.getElementsByClass("title");
        return descriptionElement.text();
    }
    
    private String parseBrand(Element element) {
        Elements brandElement = element.getElementsByClass("brand");
        return brandElement.text();
    }
    
    private String parseImgSrc(Element element) {
        Elements imageElement = element.getElementsByClass("image-normal");
        String imgUrl = imageElement.attr("src");
        return formatImgSrc(imgUrl);
    }
    
    private String formatImgSrc(String imgUrl) {
        StringBuilder sb = new StringBuilder("www.citymarket.fi");
        sb.append(imgUrl);
        
        return sb.toString();
    }
    
    private String parseCurrentPrice(Element element) {
        Elements priceElement = element.getElementsByClass("price");
        return formatPrice(priceElement.text());
    }
    
    private String formatPrice(String price) {
        return price.replace(",", ".");
    }
}
