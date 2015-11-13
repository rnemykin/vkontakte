package com.abudko.reseller.huuto.query.service.list.html.reima;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
public class ReimaHtmlListParser implements HtmlListParser {

	private static final String HTML_PRODUCT_CLASS = "prod_grid";
	private static final String HTML_PRODUCT_INFO_CLASS = "details";
	private static final String HTML_PRICE_CLASS = "price";
	private static final String HTML_IMAGE_CLASS = "thumb";

	@Override
	public Collection<ListResponse> parse(String htmlResponse) {
		Collection<ListResponse> responses = new LinkedHashSet<ListResponse>();

		Document document = Jsoup.parse(htmlResponse);

		Elements productList = document.getElementsByClass(HTML_PRODUCT_CLASS);
		if (productList != null && !productList.isEmpty()) {
			for (Element product : productList) {
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

				String discount = parseDiscount(product);
				queryResponse.setDiscount(discount);

				responses.add(queryResponse);
			}
		}

		return responses;
	}

	private String parseItemUrl(Element element) {
		Element product_info = element.getElementsByClass(HTML_PRODUCT_INFO_CLASS).get(0);
		Attributes attributes = product_info.child(0).attributes();
		String itemUrl = attributes.get("href");

		return itemUrl;
	}

	private String parseDescription(Element element) {
		Element product_info = element.getElementsByClass(HTML_PRODUCT_INFO_CLASS).get(0);
		String description = product_info.child(0).text();

		return description;
	}

	private String parseBrand(String description) {
		return description.toUpperCase().contains("REIMATEC") ? Brand.REIMATEC.name() : Brand.REIMA.name();
	}

	private String parseDiscount(Element element) {
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

		BigDecimal bd = new BigDecimal(discount);
		bd = bd.setScale(0, RoundingMode.HALF_UP);

		return bd.toString();
	}

	private String parseImgSrc(Element element) {
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

	private String parseCurrentPrice(Element element) {
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
