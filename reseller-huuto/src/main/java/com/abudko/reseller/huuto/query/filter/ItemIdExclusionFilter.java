package com.abudko.reseller.huuto.query.filter;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

@Component
public class ItemIdExclusionFilter extends AbstractNotEmptyFilter {

	private static final List<String> EXCLUSION_LIST = Arrays.asList("LE", "LEJ81899", "LE475521 056",
			"LE13092862 BRIGHT", "LE13092852 BRIGHT", "LE13122 DARK PURP", "LE13112 DARK PURP", "LE100078",
			"LE5-74200-203-21", "LE5-74200-1648-21", "LE5-74200-1709-21", "LE5-74200-1709-22", "LE5-74200-2205-25",
			"LE5-74200-2205-26");

	@Override
	protected String getValue(ListResponse queryListResponse) {
		ItemResponse itemResponse = queryListResponse.getItemResponse();
		if (itemResponse == null) {
			return " ";
		}
		if (shouldInclude(itemResponse)) {
			return itemResponse.getId();
		}

		return null;
	}

	private boolean shouldInclude(ItemResponse itemResponse) {
		return !EXCLUSION_LIST.contains(itemResponse.getId());
	}
}
