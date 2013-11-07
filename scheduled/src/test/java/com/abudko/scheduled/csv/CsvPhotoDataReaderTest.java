package com.abudko.scheduled.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class CsvPhotoDataReaderTest {

    private CsvPhotoDataReader reader;

    @Before
    public void setup() {
        reader = new CsvPhotoDataReader(new ClassPathResource("/csv/unit-test-data.csv"));
    }

    @Test
    public void testReadGroupId() {
        assertEquals("1", reader.read().get(0).getGroupId());
    }
    
    @Test
    public void testReadAlbumId() {
        assertEquals("166279845", reader.read().get(0).getAlbumId());
    }
    
    @Test
    public void testReadFile() {
        assertEquals("1.jpg", reader.read().get(0).getFileLocation());
    }
    
    @Test
    public void testReadDescription() {
        assertTrue(reader.read().get(0).getDescription().length() > 60);
    }
    
    @Test
    public void testReadSeveralRows() {
        assertTrue(reader.read().get(2).getDescription().length() > 60);
    }
    
    @Test
    public void testCommentAreNotRead() {
        List<PhotoData> photoDataList = reader.read();
        
        for (PhotoData photoData : photoDataList) {
            assertFalse(photoData.toString(), photoData.getGroupId().contains("Comment")); 
        }
    }
    
    @Test
    public void testReadDataAfterComments() {
        assertEquals(6, reader.read().size());
    }
}
