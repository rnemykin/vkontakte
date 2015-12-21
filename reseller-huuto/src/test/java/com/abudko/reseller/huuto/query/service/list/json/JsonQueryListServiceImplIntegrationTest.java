package com.abudko.reseller.huuto.query.service.list.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abudko.reseller.huuto.query.enumeration.Brand;
import com.abudko.reseller.huuto.query.params.SearchParams;
import com.abudko.reseller.huuto.query.service.item.ItemStatus;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.reseller.huuto.query.service.list.QueryListService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/test-app-config.xml" })
public class JsonQueryListServiceImplIntegrationTest {

	private static final String PARAMS = "/words/talvihaalari%20OR%20toppahaalari/classification/new/area/Helsinki%20OR%20Espoo%20OR%20Vantaa/addtime/past-day";

	@Autowired
	@Qualifier("jsonQueryListServiceImpl")
	private QueryListService jsonQueryListService;

	private static Collection<ListResponse> responses;

	private static ListResponse queryResponse;
	
	private static boolean initialized; 

	@Before
	public void setup() throws Exception {
		if (!initialized) {
			initialized = true;
			SearchParams searchParams = new SearchParams();
			searchParams.setBrand(Brand.NO_BRAND.name());
			responses = jsonQueryListService.search(PARAMS, searchParams);
			queryResponse = new ArrayList<ListResponse>(responses).get(3);
		}
	}

	@Test
	public void testNotEmpty() throws Exception {
		assertFalse(responses.isEmpty());
	}

	@Test
	public void testDescription() {
		assertTrue(queryResponse.getDescription().length() > 0);
	}

	@Test
	public void testItemUrl() {
		assertFalse("Url: " + queryResponse.getItemUrl(),
				queryResponse.getItemUrl().contains("http://www.huuto.net/kohteet"));
		assertTrue("Url: " + queryResponse.getItemUrl(), queryResponse.getItemUrl().contains("/"));
	}

	@Test
	public void testLast() {
		assertTrue("Last: " + queryResponse.getLast(), queryResponse.getLast().length() > 0);
	}

	@Test
	public void testCurrentPriceOstaHeti() {
		String currentPrice = queryResponse.getCurrentPrice();
		try {
			Double.parseDouble(currentPrice);
		} catch (Exception e) {
			fail("Unable to parse current price " + currentPrice);
		}
	}

	@Test
	public void testCurrentPriceHuuto() {
		queryResponse = new ArrayList<ListResponse>(responses).get(1);
		String currentPrice = queryResponse.getCurrentPrice();
		try {
			Double.parseDouble(currentPrice);
		} catch (Exception e) {
			fail("Unable to parse current price " + currentPrice);
		}
	}

	@Test
	public void testCurrentPriceHuutajaExists() {
		queryResponse = new ArrayList<ListResponse>(responses).get(5);
		String currentPrice = queryResponse.getCurrentPrice();
		try {
			Double.parseDouble(currentPrice);
		} catch (Exception e) {
			fail("Unable to parse current price " + currentPrice);
		}
	}

	@Test
	public void testFullPrice() {
		ListResponse fullPriceQueryResponse = new ArrayList<ListResponse>(responses).get(5);
		String fullPrice = fullPriceQueryResponse.getFullPrice();
		try {
			Double.parseDouble(fullPrice);
		} catch (Exception e) {
			fail("Unable to parse full price " + fullPrice);
		}
	}

	@Test
	public void testCompareFullPriceCurrentPrice() {
		ListResponse fullPriceQueryResponse = new ArrayList<ListResponse>(responses).get(4);
		String currentPrice = fullPriceQueryResponse.getCurrentPrice();
		String fullPrice = fullPriceQueryResponse.getFullPrice();

		double current = 0;
		try {
			current = Double.parseDouble(currentPrice);
		} catch (Exception e) {
			fail("Unable to parse current price " + fullPrice);
		}

		double full = 0;
		try {
			full = Double.parseDouble(fullPrice);
		} catch (Exception e) {
			fail("Unable to parse full price " + fullPrice);
		}

		assertTrue(full == current);
	}

	@Test
	public void testFullPriceNoComma() {
		ListResponse fullPriceQueryResponse = new ArrayList<ListResponse>(responses).get(5);
		String fullPrice = fullPriceQueryResponse.getFullPrice();

		assertFalse(fullPrice.contains(","));
	}

	@Test
	public void testImgSrc() {
		ListResponse imgSrcResponse = new ArrayList<ListResponse>(responses).get(5);
		assertTrue("Src: " + imgSrcResponse.getImgBaseSrc(),
				imgSrcResponse.getImgBaseSrc().contains("http://kuvat.huuto.net/"));
	}

	@Test
	public void testSize() {
		String size = queryResponse.getSize();
		try {
			Double.parseDouble(size);
		} catch (Exception e) {
			fail("Unable to parse size " + size);
		}
	}
	
	@Test
	public void testItemStatus() {
		assertEquals(ItemStatus.OPENED, queryResponse.getItemResponse().getItemStatus());
	}

	@Test
	public void testBrand() {
		assertNotNull("Brand: " + queryResponse.getBrand(), queryResponse.getBrand());
	}

}
