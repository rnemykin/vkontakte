package com.abudko.scheduled.service.huuto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.abudko.reseller.huuto.query.enumeration.Category;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

public class AlbumMapperTest {
    
    private AlbumMapper albumMapper = new AlbumMapper();
    
    private ListResponse testListResponse;

    @Before
    public void setup() {
        albumMapper.init();
        
        testListResponse = new ListResponse();
    }
    
    @Test
    public void testLowerBorderTalvi() {
        testListResponse.setSize("68");
        
        assertEquals(AlbumMapper.TALVI_68_86, albumMapper.getAlbumId(Category.TALVIHAALARI.name(), testListResponse));
    }
    
    @Test
    public void testInsideTalvi() {
        testListResponse.setSize("75");
        
        assertEquals(AlbumMapper.TALVI_68_86, albumMapper.getAlbumId(Category.TALVIHAALARI.name(), testListResponse));
    }
    
    @Test
    public void testUpperBorderTalvi() {
        testListResponse.setSize("98");
        
        assertFalse(AlbumMapper.TALVI_68_86.equals(albumMapper.getAlbumId(Category.TALVIHAALARI.name(), testListResponse)));
    }
    
    @Test
    public void testGetAlbumsTalvi() {
        assertEquals(3, albumMapper.getAlbumIds(Category.TALVIHAALARI.name()).size());
    }
    
    @Test
    public void testLowerBorderVäli1() {
        testListResponse.setSize("68");
        
        assertNull(albumMapper.getAlbumId(Category.VALIKAUSIHAALARI.name(), testListResponse));
    }
    
    @Test
    public void testInsideVäli1() {
        testListResponse.setSize("75");
        
        assertNull(albumMapper.getAlbumId(Category.VALIKAUSIHAALARI.name(), testListResponse));
    }
    
    @Test
    public void testLowerBorderVäli2() {
        testListResponse.setSize("68");
        
        assertNull(albumMapper.getAlbumId(Category.VALIKAUSIHOUSUT.name(), testListResponse));
    }
    
    @Test
    public void testInsideVäli2() {
        testListResponse.setSize("75");
        
        assertNull(albumMapper.getAlbumId(Category.VALIKAUSIHOUSUT.name(), testListResponse));
    }
    
    @Test
    public void testUpperBorderVäli() {
        testListResponse.setSize("98");
        
        assertFalse(AlbumMapper.TALVI_68_86.equals(albumMapper.getAlbumId(Category.VALIKAUSIHOUSUT.name(), testListResponse)));
    }
    
    @Test
    public void testGetAlbumsVäli() {
        assertEquals(0, albumMapper.getAlbumIds(Category.VALIKAUSIHOUSUT.name()).size());
    }
    
    @Test
    public void testGetAlbumsLenkkarit() {
        assertEquals(0, albumMapper.getAlbumIds(Category.LENKKARIT.name()).size());
    }
    
    @Test
    public void testInsideLenkkarit() {
        testListResponse.setSize("26");
        
        assertNull(albumMapper.getAlbumId(Category.LENKKARIT.name(), testListResponse));
    }
    
    @Test
    public void testGetAlbumIdManySizes() {
        testListResponse.setItemResponse(new ItemResponse());
        testListResponse.getItemResponse().setSizes(Arrays.asList("74", "104"));
        
        assertTrue(Arrays.asList(AlbumMapper.TALVI_68_86, AlbumMapper.TALVI_92_128, AlbumMapper.TALVI_134_164).contains(albumMapper.getAlbumId(Category.TALVIHOUSUT.name(), testListResponse)));
    }
}
