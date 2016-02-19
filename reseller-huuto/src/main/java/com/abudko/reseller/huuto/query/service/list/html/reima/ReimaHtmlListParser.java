package com.abudko.reseller.huuto.query.service.list.html.reima;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.enumeration.Brand;
import com.abudko.reseller.huuto.query.html.HtmlParserConstants;
import com.abudko.reseller.huuto.query.service.list.html.AbstractHtmlListParser;

@Component
public class ReimaHtmlListParser extends AbstractHtmlListParser {

	private static final String HTML_PRODUCT_CLASS = "prod_grid";
	private static final String HTML_PRODUCT_INFO_CLASS = "details";
	private static final String HTML_PRICE_CLASS = "price";
	private static final String HTML_IMAGE_CLASS = "thumb";
	
	@Override
	protected Elements getProductList(Document document) {
		return document.getElementsByClass(HTML_PRODUCT_CLASS);
	}

	@Override
	protected String parseItemUrl(Element element) {
		Element product_info = element.getElementsByClass(HTML_PRODUCT_INFO_CLASS).get(0);
		Attributes attributes = product_info.child(0).attributes();
		String itemUrl = attributes.get("href");

		return itemUrl;
	}

	@Override
	protected String parseDescription(Element element) {
		Element product_info = element.getElementsByClass(HTML_PRODUCT_INFO_CLASS).get(0);
		String description = product_info.child(0).text();

		return description;
	}

	@Override
	protected String parseBrand(String description) {
		return description.toUpperCase().contains("REIMATEC") ? Brand.REIMATEC.name() : Brand.REIMA.name();
	}

	@Override
	protected String parseDiscount(Element element) {
		Elements prices = element.getElementsByClass(HTML_PRICE_CLASS);
		String normalPrice = prices.get(0).text();
		String discountPrice = null;
		if (prices.size() > 1) {
			discountPrice = prices.get(1).text();
		}

		if (discountPrice == null) {
			return null;
		}

		String formatPriceNormal = formatPrice(normalPrice);
		String formatPriceDiscount = formatPrice(discountPrice);

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
		Element product_info = element.getElementsByClass(HTML_IMAGE_CLASS).get(0);
		Elements elementsByAttribute = product_info.getElementsByAttribute("src");
		String imgSrc = elementsByAttribute.attr("src");

		return formatImgSrc(imgSrc);
	}

	private String formatImgSrc(String s) {
		StringBuilder sb = new StringBuilder(HtmlParserConstants.REIMA_FI);
		sb.append(s);

		return sb.toString();
	}

	@Override
	protected String parseCurrentPrice(Element element) {
		Elements prices = element.getElementsByClass(HTML_PRICE_CLASS);
		String price = prices.get(0).text();
		if (prices.size() > 1) {
			price = prices.get(1).text();
		}

		String formatPrice = formatPrice(price);
		return formatPrice.trim();
	}

	private String formatPrice(String price) {
		String euro = String.format("%s", HtmlParserConstants.EURO_CHAR);
		String priceDouble = price.replace(euro, "");
		return priceDouble.replace(",", ".");
	}
}
