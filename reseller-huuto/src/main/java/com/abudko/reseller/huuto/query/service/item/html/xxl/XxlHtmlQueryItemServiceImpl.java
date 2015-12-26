package com.abudko.reseller.huuto.query.service.item.html.xxl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.abudko.reseller.huuto.query.html.HtmlParserConstants;
import com.abudko.reseller.huuto.query.rules.AbstractPriceRules;
import com.abudko.reseller.huuto.query.rules.Add20PriceRules;
import com.abudko.reseller.huuto.query.service.item.AbstractQueryItemService;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.ItemStatus;

@Component
public class XxlHtmlQueryItemServiceImpl extends AbstractQueryItemService {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Resource
	protected Add20PriceRules priceRules;

	@Autowired
	private XxlHtmlItemParser htmlItemParser;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	protected ItemResponse callAndParse(String itemId) {
		String itemUrl = constructFullItemUrl(itemId);

		log.info(String.format("Quering item: %s", itemUrl));

		String decode = decodeUrl(itemUrl);

		String html = restTemplate.getForObject(decode, String.class);

		ItemResponse itemResponse = parseResponseFromHtml(html);
		itemResponse.setItemUrl(decode);

		if (itemResponse.getId() != null && !itemResponse.getSizes().isEmpty()) {
			itemResponse.setItemStatus(ItemStatus.OPENED);
		}

		return itemResponse;
	}

	private String constructFullItemUrl(String itemUrl) {
		StringBuilder sb = new StringBuilder(HtmlParserConstants.XXL_FI);
		sb.append(itemUrl);

		return sb.toString();
	}

	private ItemResponse parseResponseFromHtml(String html) {
		ItemResponse itemResponse = htmlItemParser.parse(html);
		return itemResponse;
	}

	protected String extractIdFromUrl(String urlSuffix) {
		return null;
	}

	protected AbstractPriceRules getPriceRules() {
		return priceRules;
	}
}
