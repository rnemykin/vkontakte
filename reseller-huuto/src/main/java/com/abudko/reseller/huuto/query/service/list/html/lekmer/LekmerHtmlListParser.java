package com.abudko.reseller.huuto.query.service.list.html.lekmer;

import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.abudko.reseller.huuto.query.html.HtmlParserConstants;
import com.abudko.reseller.huuto.query.service.list.html.AbstractHtmlListParser;

@Component
public class LekmerHtmlListParser extends AbstractHtmlListParser {
    
    private static final List<String> RED_LABELS = Arrays.asList("Testivoittaja", "Kesäale", "Ale", "Ilmainen toimitus", "Outlet", " mm", "VARASTONTYHJENNYS");

    public static final String IMG_SRC_SMALL = "thumbnail168x169";
    public static final String IMG_SRC_BIG = "productmanMeasurement465x500";

    private static final String HTML_PRODUCT_LIST_CLASS = "product_list";
    private static final String HTML_PRODUCT_CLASS = "product round_corners_small three columns mobile-six end";
    private static final String HTML_PRODUCT_INFO_CLASS = "product_info";
    private static final String HTML_PRICE_SALE_CLASS = "price sale";
    private static final String HTML_PRICE_CLASS = "price";
    private static final String HTML_IMAGE_CLASS = "image_holder";
    private static final String DISCOUNT_CLASS = "price sale percentage";
    
    @Override
	protected Elements getProductList(Document document) {
    	Elements productList = document.getElementsByAttributeValueContaining("class", HTML_PRODUCT_LIST_CLASS);
    	if (productList != null && !productList.isEmpty()) {
    		Elements products = productList.get(0).getElementsByAttributeValueContaining("class", HTML_PRODUCT_CLASS);
    		return products;
    	}
		return null;
	}

    @Override
    protected String parseItemUrl(Element element) {
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

    @Override
    protected String parseDescription(Element element) {
        Element product_info = element.getElementsByClass(HTML_PRODUCT_INFO_CLASS).get(0);
        Element child = product_info.child(1);
        String description = child.ownText();

        if (StringUtils.isEmpty(description)) {
            description = product_info.child(0).ownText();
        }

        if (hasDescriptionRedLabels(description)) {
            description = product_info.child(2).ownText();
            
            if (hasDescriptionRedLabels(description)) {
                description = product_info.child(3).ownText();
            }
        }

        return description;
    }
    
    private boolean hasDescriptionRedLabels(String description) {
        for (String label : RED_LABELS) {
            if (description.contains(label)) {
                return true;
            }
        }
        
        return false;
    }

    @Override
    protected String parseBrand(String description) {
        return parseBrandDefault(description);
    }

    @Override
    protected String parseDiscount(Element element) {
        Element product_info = element.getElementsByClass(HTML_PRODUCT_INFO_CLASS).get(0);
        Elements elementsByClass = product_info.getElementsByAttributeValueContaining("class", DISCOUNT_CLASS);

        if (elementsByClass == null || elementsByClass.isEmpty()) {
            return null;
        }

        return elementsByClass.text();
    }

    @Override
    protected String parseImgSrc(Element element) {
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

    @Override
    protected String parseCurrentPrice(Element element) {
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
