package com.abudko.reseller.huuto.query.filter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

public class ItemIdExclusionFilterTest {

	private static final List<String> EXCLUSION_LIST = Arrays.asList("LE", "LEJ81899", "LE475521 056",
			"LE13092862 BRIGHT", "LE13092852 BRIGHT", "LE13122 DARK PURP", "LE13112 DARK PURP", "LE100078",
			"LE5-74200-203-21", "LE5-74200-1648-21", "LE5-74200-1709-21", "LE5-74200-1709-22", "LE5-74200-2205-25",
			"LE5-74200-2205-26", "LE5-74200-2205-29", "LE5-74200-1648-39", "LE5-74200-1709-31", "LE5-74200-2205-21",
			"LE5-74200-203-22", "LE5-74200-1648-22", "LE5-74200-1709-25", "LE5-74200-2205-28", "LE5-74200-1709-26",
			"LE5-74200-2205-30", "LE5-74200-1648-26", "LE5-74200-1648-24", "LE5-74200-2205-22", "LE5-74200-203-23",
			"LE5-74200-2205-24", "LE5-74200-1709-23", "LE5-74200-1648-25", "LE5-74200-2205-23", "LE245501161",
			"LE245910031", "LE277152475", "LE277152225");

	private ListResponse listResponse;

	SearchResultFilter filter = new ItemIdExclusionFilter();

	@Before
	public void setup() {
		listResponse = new ListResponse();
		listResponse.setItemResponse(new ItemResponse());
	}

	@Test
	public void test() {
		for (String id : EXCLUSION_LIST) {
			listResponse.getItemResponse().setId(id);

			Collection<ListResponse> filtered = filter.apply(Arrays.asList(listResponse), null);

			assertTrue("Not filtered " + id, filtered.isEmpty());
		}
	}

	@Test
	public void testSingle() {
		final String id = "LE5-74201-203-22";
		listResponse.getItemResponse().setId(id);

		Collection<ListResponse> filtered = filter.apply(Arrays.asList(listResponse), null);

		assertFalse(filtered.isEmpty());
	}
}
