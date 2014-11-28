package com.abudko.reseller.huuto.query.service.item.html.city;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.html.HtmlParserConstants;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.ItemStatus;
import com.abudko.reseller.huuto.query.service.item.html.HtmlItemParser;

@Component
public class CityHtmlItemParser implements HtmlItemParser {

    @Override
    public ItemResponse parse(String htmlResponse) {
        ItemResponse response = new ItemResponse();
        
        if (htmlResponse == null) {
            return response;
        }

        String html = truncateUnused(htmlResponse);

        Document document = Jsoup.parse(html);

        List<String> sizes = parseSizes(document);
        response.setSizes(sizes);

        String price = parsePrice(document);
        response.setPrice(price);

        String imgSrc = parseImgSrc(document);
        response.setImgBaseSrc(imgSrc);

        String id = parseId(document);
        response.setId(id);
        
        ItemStatus itemStatus = getItemStatus(sizes);
        response.setItemStatus(itemStatus);
        
        return response;
    }

    private String truncateUnused(String htmlResponse) {
        String str = String.format("<div class=\"%s\"", "drop");
        int indexOfResultList = htmlResponse.indexOf(str);
        if (indexOfResultList < 0) {
            return htmlResponse;
        }
        String truncatedHtml = htmlResponse.substring(indexOfResultList);
        return truncatedHtml;
    }

    private List<String> parseSizes(Document document) {
        List<String> sizes = new ArrayList<>();
        Element element = document.getElementById("facetSize");
        Elements sizesElements = element.getElementsByClass("overview");
        for (Element sizesElement : sizesElements) {
            Elements elements = sizesElement.getElementsByClass("filter");
            for (Element el : elements) {
                String text = el.text();
                text = text.substring(0, text.indexOf(" "));
                try {
                    sizes.add(Integer.toString(Integer.decode(text)));
                } catch (NumberFormatException e) {
                }
            }
        }
        return sizes;
    }

    private String parseImgSrc(Document document) {
        Elements elements = document.getElementsByClass("image-normal");
        String imgsrc = elements.attr("src").replace("small", "large");
        return "http://www.citymarket.fi" + imgsrc;
    }

    private String parseId(Document document) {
        String text = document.getElementsByTag("title").text();
        String id = text.substring(text.lastIndexOf(" ") + 1, text.length());
        return HtmlParserConstants.CITY_ID_PREFIX + id;
    }

    private String parsePrice(Document document) {
        Elements elements = document.getElementsByClass("price");
        String price = elements.get(1).text();
        return formatPrice(price);
    }

    private String formatPrice(String price) {
        return price.replace(",", ".");
    }
    
    private ItemStatus getItemStatus(List<String> sizes) {
        if (sizes == null || sizes.isEmpty()) {
            return ItemStatus.CLOSED;
        }
        else {
            return ItemStatus.OPENED;
        }
    }
}