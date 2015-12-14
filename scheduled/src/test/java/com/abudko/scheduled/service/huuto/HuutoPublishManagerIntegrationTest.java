package com.abudko.scheduled.service.huuto;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abudko.reseller.huuto.query.enumeration.Category;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/test-app-config.xml" })
public class HuutoPublishManagerIntegrationTest {
	
    @Autowired
    @Qualifier("huutoPublishManager")
    private AbstractPublishManager publishManager;

	@Test
	public void testGetDescription() {
		ListResponse listResponse = new ListResponse();
		ItemResponse itemResponse = new ItemResponse();
		listResponse.setItemResponse(itemResponse);
		final String brand = "Reima";
		listResponse.setBrand(brand);
		final String newPrice = "2100";
		itemResponse.setNewPrice(newPrice);
		final String id = "22";
		itemResponse.setId(id);
		final String size = "27";
		listResponse.setSize(size);
		
		String description = publishManager.getDescription(Category.TALVIHAALARI, listResponse);
		
		assertTrue(description.contains(brand));
		assertTrue(description.contains(id));
		assertTrue(description.contains(size));
		assertTrue(description.contains(newPrice));
	}

}
