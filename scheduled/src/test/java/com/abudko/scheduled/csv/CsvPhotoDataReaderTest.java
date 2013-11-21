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
        assertEquals("1", reader.read(FILE_LOCATION).get(0).getGroupId());
    }
    
    @Test
    public void testReadAlbumId() {
        assertEquals("166279845", reader.read(FILE_LOCATION).get(0).getAlbumId());
    }
    
    @Test
    public void testReadFile() throws IOException {
        assertEquals("1.jpg", reader.read(FILE_LOCATION).get(0).getFileResource().getFile().getName());
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
    public void testCommentAreNotRead() {
        List<PhotoData> photoDataList = reader.read(FILE_LOCATION);
        
        for (PhotoData photoData : photoDataList) {
            assertFalse(photoData.toString(), photoData.getGroupId().contains("Comment")); 
        }
    }
    
    @Test
    public void testReadDataAfterComments() {
        assertEquals(6, reader.read(FILE_LOCATION).size());
    }
}
