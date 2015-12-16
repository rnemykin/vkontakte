package com.abudko.scheduled.service.huuto.clean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abudko.scheduled.vkontakte.Photo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/test-app-config.xml" })
public class ItemValidityRulesAlbumCleanerIntegrationTest {
	
	@Autowired
	private ItemValidityRulesAlbumCleaner cleaner;

	@Test
	public void testCleanLekmerNoEncodedURL() {
		Photo photo = new Photo();
		photo.setDescription("bla bla [LE24590101110]");
		assertTrue(cleaner.isValid(photo));
	}
	
	@Test
	public void testCleanLekmerEncodedURL() {
		Photo photo = new Photo();
		photo.setDescription("bla bla [LE24591003080]|bGluZGJlcmctb3ZlcmFsbC12ZXJtb250LWdyZWVuLW5hdnk=|");
		assertTrue(cleaner.isValid(photo));
	}
	
	@Test
	public void testCleanLekmerInvalidEncodedURL() {
		Photo photo = new Photo();
		photo.setDescription("bla bla [LE24591003080]|bInvalidEncodedURLnk=|");
		assertFalse(cleaner.isValid(photo));
	}
	
	@Test
	public void testCleanHuutoEncodedURL() {
		Photo photo = new Photo();
		photo.setDescription("bla bla [24590101110]|bGluZGJlcmctb3ZlcmFsbC12ZXJtb250LWdyZWVuLW5hdnk=|");
		assertTrue(cleaner.isValid(photo));
	}

}
