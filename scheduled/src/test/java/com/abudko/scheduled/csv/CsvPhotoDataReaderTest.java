package com.abudko.scheduled.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

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
}
