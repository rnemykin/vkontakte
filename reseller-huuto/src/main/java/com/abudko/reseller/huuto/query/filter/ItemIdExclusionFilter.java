package com.abudko.reseller.huuto.query.filter;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

@Component
public class ItemIdExclusionFilter extends AbstractNotEmptyFilter {

	private static final List<String> EXCLUSION_REGEXP_LIST = Arrays.asList("LEJ81899", "LE475521 056",
			"LE13092862 BRIGHT", "LE13092852 BRIGHT", "LE13122 DARK PURP", "LE13112 DARK PURP", "LE100078",
			"LE5-74200-\\w+", "LE245501161", "LE245910031", "LE277152475", "LE277152225");

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
		String id = itemResponse.getId();
		
		if ("LE".equalsIgnoreCase(id)) {
			return false;
		}
		
		for (String exp : EXCLUSION_REGEXP_LIST) {
			Matcher matcher = Pattern.compile(exp).matcher(id);
			if (matcher.find()) {
				return false;
			}
		}
		
		return true;
	}
}
