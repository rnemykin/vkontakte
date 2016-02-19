package com.abudko.reseller.huuto.query.service.list.html;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abudko.reseller.huuto.query.enumeration.Brand;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

public abstract class AbstractHtmlListParser implements HtmlListParser {

	private Logger log = LoggerFactory.getLogger(getClass());

	public Collection<ListResponse> parse(String html) {
		Collection<ListResponse> responses = new LinkedHashSet<>();
		Document document = Jsoup.parse(html);

		Elements productList = getProductList(document);
		if (productList != null && !productList.isEmpty()) {
			for (Element product : productList) {
				ListResponse queryResponse = new ListResponse();

				try {
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
				} catch (Exception e) {
					log.error("Error while parsing list response " + queryResponse, e);
					throw e;
				}

				responses.add(queryResponse);
			}
		}

		return responses;
	}
	
	protected String parseBrandDefault(String description) {
        Brand brand = Brand.getBrandFrom(description);
        if (brand != null) {
            return brand.getFullName();
        }
        return null;
    }

	protected abstract String parseItemUrl(Element element);

	protected abstract String parseDescription(Element element);

	protected abstract String parseImgSrc(Element element);

	protected abstract String parseCurrentPrice(Element element);

	protected abstract String parseDiscount(Element element);

	protected abstract String parseBrand(String description);

	protected abstract Elements getProductList(Document document);
}
