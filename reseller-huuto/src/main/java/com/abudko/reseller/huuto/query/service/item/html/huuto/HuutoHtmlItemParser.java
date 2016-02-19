package com.abudko.reseller.huuto.query.service.item.html.huuto;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.html.HtmlParserConstants;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.html.HtmlItemParser;

@Component
public class HuutoHtmlItemParser implements HtmlItemParser {

    private static final String HTML_HV = "Hintavaraus";

    private static final String HTML_CONDITION = "Kunto";

    private static final String HTML_LOCATION = "Tuotteen sijainti";

    private static final String HTML_FULL_PRICE_ELEMENT_ID = "price";

    private static final String HTML_SELLER = "seller";

    private static final String IMG_SUFFIX = "-m.jpg";

    @Override
    public ItemResponse parse(String html) {
        ItemResponse response = new ItemResponse();

        Boolean hv = parseHv(html);
        response.setHv(hv);

        Document document = Jsoup.parse(html);

        String condition = parseCondition(document);
        response.setCondition(condition);

        String location = parseLocation(document);
        response.setLocation(location);

        String price = parsePrice(document);
        response.setPrice(price);

        String imgSrc = parseImgSrc(document);
        response.setImgBaseSrc(imgSrc);

        String seller = parseSeller(document);
        response.setSeller(seller);

        return response;
    }

    private Boolean parseHv(String html) {
        if (html.contains(HTML_HV)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private String parseCondition(Document document) {
        Elements conditionElement = document.getElementsContainingOwnText(HTML_CONDITION);
        Elements parents = conditionElement.parents();
        if (parents.size() > 0) {
            Element parent = parents.get(0);
            String condition = parent.parent().child(1).ownText();
            return condition;
        }
        return "";
    }

    private String parseLocation(Document document) {
        Elements locationElement = document.getElementsContainingOwnText(HTML_LOCATION);
        Elements parents = locationElement.parents();
        if (parents.size() > 0) {
            Element parent = parents.get(0);
            String location = parent.parent().child(1).ownText();
            if (location != null) {
                return location.trim();
            }
            return location;
        }
        return "";
    }

    private String parsePrice(Document document) {
        String fullPrice = "";
        Elements elementsByClass = document.getElementsByClass(HTML_FULL_PRICE_ELEMENT_ID);
        if (elementsByClass != null) {
            Element element = elementsByClass.get(0);
            if (elementsByClass.size() > 1) {
                Element element2 = elementsByClass.get(1);
                String text2 = element2.text();
                String fullPrice2 = text2.replace(HtmlParserConstants.EURO_CHAR, "").trim();
                return fullPrice2.replace(",", ".");
            }
            String text = element.text();
            fullPrice = text.replace(HtmlParserConstants.EURO_CHAR, "").trim();
        }
        return fullPrice.replace(",", ".");
    }

    private String parseImgSrc(Document document) {
        String imgSrc = "";
        Elements select = document.select("[src]");
        for (Element element : select) {
            if ("img".equals(element.tagName())) {
                String imgSrcAttribute = element.attr("src");
                if (imgSrcAttribute.contains(IMG_SUFFIX)) {
                    imgSrc = formatImgSrc(imgSrcAttribute);
                }
            }
        }
        return imgSrc;
    }

    private String formatImgSrc(String imgSrc) {
        String formattedImgSrc = imgSrc.replace(IMG_SUFFIX, "");
        return formattedImgSrc;
    }

    private String parseSeller(Document document) {
        Elements sellerElement = document.getElementsByClass(HTML_SELLER);
        String seller = sellerElement.get(0).child(0).text();
        return formatSeller(seller);
    }

    private String formatSeller(String seller) {
        int index = seller.indexOf(" ");
        return index < 0 ? seller : seller.substring(0, index);
    }

}