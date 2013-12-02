package com.abudko.scheduled.service.huuto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import com.abudko.reseller.huuto.query.enumeration.Category;

public class AlbumMapperTest {
    
    private AlbumMapper albumMapper = new AlbumMapper();

    @Before
    public void setup() {
        albumMapper.init();
    }
    
    @Test
    public void testLowerBorder() {
        assertEquals(AlbumMapper.TALVI_68_86, albumMapper.getAlbumId(Category.TALVIHAALARI.name(), 68));
    }
    
    @Test
    public void testInside() {
        assertEquals(AlbumMapper.TALVI_68_86, albumMapper.getAlbumId(Category.TALVIHAALARI.name(), 75));
    }
    
    @Test
    public void testUpperBorder() {
        assertFalse(AlbumMapper.TALVI_68_86.equals(albumMapper.getAlbumId(Category.TALVIHAALARI.name(), 98)));
    }
    
    @Test
    public void testGetAlbums() {
        assertEquals(3, albumMapper.getAlbumIds(Category.TALVIHAALARI.name()).size());
    }
}
