package com.abudko.scheduled.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CsvPhotoDataReaderTest {
    
    private static final String FILE_LOCATION = "/csv/unit-test-data.csv";

    private CsvPhotoDataReader reader;

    @Before
    public void setup() {
        reader = new CsvPhotoDataReader();
    }

    @Test
    public void testReadGroupId() {
        assertEquals("6", reader.read(FILE_LOCATION).get(0).getGroupId());
        assertEquals("6", reader.read(FILE_LOCATION).get(2).getGroupId());
    }
    
    @Test
    public void testReadAlbumId() {
    	assertEquals("100000001", reader.read(FILE_LOCATION).get(0).getAlbumId());
        assertEquals("100000003", reader.read(FILE_LOCATION).get(2).getAlbumId());
    }
    
    @Test
    public void testReadFile() throws IOException {
        assertEquals("2.jpg", reader.read(FILE_LOCATION).get(0).getFileResource().getFile().getName());
    }
    
    @Test
    public void testReadDescription() {
        assertTrue(reader.read(FILE_LOCATION).get(0).getDescription().length() > 60);
    }
    
    @Test
    public void testReadSeveralRows() {
        assertTrue(reader.read(FILE_LOCATION).get(2).getDescription().length() > 60);
    }
    
    @Test
    public void testCommentAreNotReadSymbolsInTheBeginning() {
        List<PhotoData> photoDataList = reader.read(FILE_LOCATION);
        
        for (PhotoData photoData : photoDataList) {
            assertFalse(photoData.toString(), photoData.getGroupId().contains("Comment")); 
        }
    }
    
    @Test
    public void testReadDataAfterComments() {
        assertEquals(9, reader.read(FILE_LOCATION).size());
    }
    
    @Test
    public void testGetDefaultGroupId() {
    	assertEquals("2727877", reader.getDefaultGroupId("(2727877|169942310)"));
    }
    
    @Test
    public void testGetDefaultAlbumId() {
    	assertEquals("169942310", reader.getDefaultAlbumId("(2727877|169942310)"));
    }
    
    @Test
    public void testValidateGroupIdNull() {
    	PhotoData photoData = new PhotoData();
    	photoData.setGroupId(null);
    	photoData.setAlbumId("22652");
    	
    	try {
    		List<PhotoData> list = new ArrayList<>();
    		list.add(photoData);
    		reader.validate(list);
    		fail("Should throw before");
    	}
    	catch (RuntimeException e) {
    		assertTrue(e.getMessage(), e.getMessage().contains("'groupId'"));
    		assertFalse(e.getMessage(), e.getMessage().contains("'albumId'"));
    	}
    }
    
    @Test
    public void testValidateAlbumIdNull() {
    	PhotoData photoData = new PhotoData();
    	photoData.setGroupId("2224242");
    	photoData.setAlbumId(null);
    	
    	try {
    		List<PhotoData> list = new ArrayList<>();
    		list.add(photoData);
    		reader.validate(list);
    		fail("Should throw before");
    	}
    	catch (RuntimeException e) {
    		assertFalse(e.getMessage(), e.getMessage().contains("'groupId'"));
    		assertTrue(e.getMessage(), e.getMessage().contains("'albumId'"));
    	}
    }
    
    @Test
    public void testValidateGroupIdEmpty() {
    	PhotoData photoData = new PhotoData();
    	photoData.setGroupId("");
    	photoData.setAlbumId("2323223");
    	
    	try {
    		List<PhotoData> list = new ArrayList<>();
    		list.add(photoData);
    		reader.validate(list);
    		fail("Should throw before");
    	}
    	catch (RuntimeException e) {
    		assertTrue(e.getMessage(), e.getMessage().contains("'groupId'"));
    		assertFalse(e.getMessage(), e.getMessage().contains("'albumId'"));
    	}
    }
    
    @Test
    public void testValidateAlbumIdEmpty() {
    	PhotoData photoData = new PhotoData();
    	photoData.setGroupId("22323");
    	photoData.setAlbumId("");
    	
    	try {
    		List<PhotoData> list = new ArrayList<>();
    		list.add(photoData);
    		reader.validate(list);
    		fail("Should throw before");
    	}
    	catch (RuntimeException e) {
    		assertFalse(e.getMessage(), e.getMessage().contains("'groupId'"));
    		assertTrue(e.getMessage(), e.getMessage().contains("'albumId'"));
    	}
    }
    
    @Test
    public void testValidateGroupIdNotdigit() {
    	PhotoData photoData = new PhotoData();
    	photoData.setGroupId("sssds");
    	photoData.setAlbumId("23223");
    	
    	try {
    		List<PhotoData> list = new ArrayList<>();
    		list.add(photoData);
    		reader.validate(list);
    		fail("Should throw before");
    	}
    	catch (RuntimeException e) {
    		assertTrue(e.getMessage(), e.getMessage().contains("'groupId'"));
    		assertFalse(e.getMessage(), e.getMessage().contains("'albumId'"));
    	}
    }
    
    @Test
    public void testValidateAlbumIdNotdigit() {
    	PhotoData photoData = new PhotoData();
    	photoData.setGroupId("2224242");
    	photoData.setAlbumId("dwdww");
    	
    	try {
    		List<PhotoData> list = new ArrayList<>();
    		list.add(photoData);
    		reader.validate(list);
    		fail("Should throw before");
    	}
    	catch (RuntimeException e) {
    		assertFalse(e.getMessage(), e.getMessage().contains("'groupId'"));
    		assertTrue(e.getMessage(), e.getMessage().contains("'albumId'"));
    	}
    }
}
