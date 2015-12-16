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

	private static final List<String> EXCLUSION_REGEXP_LIST = Arrays.asList("");

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
