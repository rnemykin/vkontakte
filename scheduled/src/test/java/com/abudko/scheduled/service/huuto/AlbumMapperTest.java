package com.abudko.scheduled.service.huuto;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.abudko.reseller.huuto.query.enumeration.Category;

public class AlbumMapperTest {
    
    private AlbumMapper albumMapper = new AlbumMapper();

    @Test
    public void testLowerBorder() {
        assertNotNull(albumMapper.getAlbumId(Category.TALVIHAALARI.name(), 68));
    }
    
    @Test
    public void testInside() {
        assertNotNull(albumMapper.getAlbumId(Category.TALVIHAALARI.name(), 75));
    }
    
    @Test
    public void testUpperBorder() {
        assertNotNull(albumMapper.getAlbumId(Category.TALVIHAALARI.name(), 98));
    }
}
