package com.abudko.scheduled.csv;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class CsvPhotoDataReaderTest {

    private CsvPhotoDataReader reader;

    @Before
    public void setup() {
        reader = new CsvPhotoDataReader(new ClassPathResource("data.csv"));
    }

    @Test
    public void testReadGroupId() {
        assertEquals("11004536", reader.read().get(0).getGroupId());
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
        assertTrue(reader.read().get(0).getDescription().length() > 30);
    }
    
    @Test
    public void testReadSeveralRows() {
        assertTrue(reader.read().get(2).getDescription().length() > 30);
    }
}
