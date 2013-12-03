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
    public void testLowerBorderTalvi() {
        assertEquals(AlbumMapper.TALVI_68_86, albumMapper.getAlbumId(Category.TALVIHAALARI.name(), 68));
    }
    
    @Test
    public void testInsideTalvi() {
        assertEquals(AlbumMapper.TALVI_68_86, albumMapper.getAlbumId(Category.TALVIHAALARI.name(), 75));
    }
    
    @Test
    public void testUpperBorderTalvi() {
        assertFalse(AlbumMapper.TALVI_68_86.equals(albumMapper.getAlbumId(Category.TALVIHAALARI.name(), 98)));
    }
    
    @Test
    public void testGetAlbumsTalvi() {
        assertEquals(3, albumMapper.getAlbumIds(Category.TALVIHAALARI.name()).size());
    }
    
    @Test
    public void testLowerBorderVäli1() {
        assertEquals(AlbumMapper.VALI_68_86, albumMapper.getAlbumId(Category.VALIKAUSIHAALARI.name(), 68));
    }
    
    @Test
    public void testInsideVäli1() {
        assertEquals(AlbumMapper.VALI_68_86, albumMapper.getAlbumId(Category.VALIKAUSIHAALARI.name(), 75));
    }
    
    @Test
    public void testLowerBorderVäli2() {
        assertEquals(AlbumMapper.VALI_68_86, albumMapper.getAlbumId(Category.VALIKAUSIHOUSUT.name(), 68));
    }
    
    @Test
    public void testInsideVäli2() {
        assertEquals(AlbumMapper.VALI_68_86, albumMapper.getAlbumId(Category.VALIKAUSIHOUSUT.name(), 75));
    }
    
    @Test
    public void testUpperBorderVäli() {
        assertFalse(AlbumMapper.TALVI_68_86.equals(albumMapper.getAlbumId(Category.VALIKAUSIHOUSUT.name(), 98)));
    }
    
    @Test
    public void testGetAlbumsVäli() {
        assertEquals(3, albumMapper.getAlbumIds(Category.VALIKAUSIHOUSUT.name()).size());
    }
    
    @Test
    public void testGetAlbumsLenkkarit() {
        assertEquals(2, albumMapper.getAlbumIds(Category.LENKKARIT.name()).size());
    }
    
    @Test
    public void testInsideLenkkarit() {
        assertEquals(AlbumMapper.VALILENKKARIT_20_27, albumMapper.getAlbumId(Category.LENKKARIT.name(), 26));
    }
}
