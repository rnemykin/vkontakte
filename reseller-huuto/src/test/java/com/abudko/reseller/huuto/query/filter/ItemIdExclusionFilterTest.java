package com.abudko.reseller.huuto.query.filter;

import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

public class ItemIdExclusionFilterTest {

	private ListResponse listResponse;

	SearchResultFilter filter = new ItemIdExclusionFilter();

	@Before
	public void setup() {
		listResponse = new ListResponse();
		listResponse.setItemResponse(new ItemResponse());
	}

	@Test
	public void testSingle() {
		final String id = "LE5-74201-203-22";
		listResponse.getItemResponse().setId(id);

		Collection<ListResponse> filtered = filter.apply(Arrays.asList(listResponse), null);

		assertFalse(filtered.isEmpty());
	}
	
	@Test
	public void testReima() {
	    final String id = "RE510195C-4621";
	    listResponse.getItemResponse().setId(id);
	    
	    Collection<ListResponse> filtered = filter.apply(Arrays.asList(listResponse), null);
	    
	    assertFalse(filtered.isEmpty());
	}
}
