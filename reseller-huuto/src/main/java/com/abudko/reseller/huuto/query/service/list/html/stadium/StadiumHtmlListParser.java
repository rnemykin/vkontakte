package com.abudko.reseller.huuto.query.service.list.html.stadium;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.html.HtmlParserConstants;
import com.abudko.reseller.huuto.query.service.list.html.AbstractHtmlListParser;

@Component
public class StadiumHtmlListParser extends AbstractHtmlListParser {
    
    public static final String IMG_SRC_SMALL = "Small1x1";
    public static final String IMG_SRC_BIG = "Detail";

    private static final String HTML_PRODUCT_LIST_CLASS = "trigger-product-card";
    
    @Override
	protected Elements getProductList(Document document) {
		return document.getElementsByAttributeValueContaining("class", HTML_PRODUCT_LIST_CLASS);
	}

    @Override
    protected String parseItemUrl(Element element) {
    	return element.attr("href");
    }

    @Override
    protected String parseDescription(Element element) {
    	Elements elementsByClass = element.getElementsByClass("product-brand");
    	return elementsByClass.get(0).attr("title");
    }
    
    @Override
    protected String parseBrand(String description) {
        return parseBrandDefault(description);
    }

    @Override
    protected String parseDiscount(Element element) {
		Elements reduced = element.getElementsByClass("reduced");
		if (reduced == null || reduced.isEmpty()) {
			return null;
		}
		Element normalPrice = element.getElementsByClass("regular").get(0);
		Element discountPrice = reduced.get(0);

		if (discountPrice == null) {
			return null;
		}

		String formatPriceNormal = formatPrice(normalPrice.text());
		String formatPriceDiscount = formatPrice(discountPrice.text());

		double discount = Double.parseDouble(formatPriceDiscount);
		double normal = Double.parseDouble(formatPriceNormal);

		discount /= normal;

		discount *= 100;
		
		discount = 100 - discount;

		BigDecimal bd = new BigDecimal(discount);
		bd = bd.setScale(0, RoundingMode.HALF_UP);

		return bd.toString();
	}

    @Override
    protected String parseImgSrc(Element element) {
        Element image = element.getElementsByClass("center-verticle").get(0);
        Element child = image.child(0);
        String imgSrc = child.attr("src");

        return formatImgSrc(imgSrc);
    }

    private String formatImgSrc(String s) {
        StringBuilder sb = new StringBuilder(HtmlParserConstants.STADIUM_FI);
        sb.append(s.replace(IMG_SRC_SMALL, IMG_SRC_BIG));

        return sb.toString();
    }

    @Override
    protected String parseCurrentPrice(Element element) {
    	Elements reduced = element.getElementsByClass("reduced");
    	Element price = null;
    	if (reduced != null && !reduced.isEmpty()) {
    		price = element.getElementsByClass("reduced").get(0);
    	}
    	else {
    		price = element.getElementsByClass("regular").get(0);
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
}
