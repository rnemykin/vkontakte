package com.abudko.scheduled.service.huuto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.abudko.reseller.huuto.query.enumeration.Category;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.ListResponse;

public class AlbumMapper2Test {
    
    private AlbumMapper2 albumMapper2 = new AlbumMapper2();
    
    private ListResponse testListResponse;

    @Before
    public void setup() {
        albumMapper2.init();
        
        testListResponse = new ListResponse();
    }
    
    @Test
    public void testLowerBorderTalvi() {
        testListResponse.setSize("68");
        
        assertEquals(AlbumMapper2.TALVI_68_86, albumMapper2.getAlbumId(Category.TALVIHAALARI.name(), testListResponse));
    }
    
    @Test
    public void testInsideTalvi() {
        testListResponse.setSize("75");
        
        assertEquals(AlbumMapper2.TALVI_68_86, albumMapper2.getAlbumId(Category.TALVIHAALARI.name(), testListResponse));
    }
    
    @Test
    public void testUpperBorderTalvi() {
        testListResponse.setSize("98");
        
        assertFalse(AlbumMapper2.TALVI_68_86.equals(albumMapper2.getAlbumId(Category.TALVIHAALARI.name(), testListResponse)));
    }
    
    @Test
    public void testGetAlbumsTalvi() {
        assertEquals(3, albumMapper2.getAlbumIds(Category.TALVIHAALARI.name()).size());
    }
    
    @Test
    public void testLowerBorderVäli1() {
        testListResponse.setSize("68");
        
        assertEquals(AlbumMapper2.VALI_68_86, albumMapper2.getAlbumId(Category.VALIKAUSIHAALARI.name(), testListResponse));
    }
    
    @Test
    public void testInsideVäli1() {
        testListResponse.setSize("75");
        
        assertEquals(AlbumMapper2.VALI_68_86, albumMapper2.getAlbumId(Category.VALIKAUSIHAALARI.name(), testListResponse));
    }
    
    @Test
    public void testLowerBorderVäli2() {
        testListResponse.setSize("68");
        
        assertEquals(AlbumMapper2.VALI_68_86, albumMapper2.getAlbumId(Category.VALIKAUSIHOUSUT.name(), testListResponse));
    }
    
    @Test
    public void testInsideVäli2() {
        testListResponse.setSize("75");
        
        assertEquals(AlbumMapper2.VALI_68_86, albumMapper2.getAlbumId(Category.VALIKAUSIHOUSUT.name(), testListResponse));
    }
    
    @Test
    public void testUpperBorderVäli() {
        testListResponse.setSize("98");
        
        assertFalse(AlbumMapper2.TALVI_68_86.equals(albumMapper2.getAlbumId(Category.VALIKAUSIHOUSUT.name(), testListResponse)));
    }
    
    @Test
    public void testGetAlbumsVäli() {
        assertEquals(3, albumMapper2.getAlbumIds(Category.VALIKAUSIHOUSUT.name()).size());
    }
    
    @Test
    public void testGetAlbumsLenkkarit() {
        assertEquals(2, albumMapper2.getAlbumIds(Category.LENKKARIT.name()).size());
    }
    
    @Test
    public void testInsideLenkkarit() {
        testListResponse.setSize("26");
        
        assertEquals(AlbumMapper2.VALILENKKARIT_20_27, albumMapper2.getAlbumId(Category.LENKKARIT.name(), testListResponse));
    }
    
    @Test
    public void testGetAlbumIdManySizes() {
        testListResponse.setItemResponse(new ItemResponse());
        testListResponse.getItemResponse().setSizes(Arrays.asList("74", "104"));
        
        assertTrue(Arrays.asList(AlbumMapper2.VALI_68_86, AlbumMapper2.VALI_92_128, AlbumMapper2.VALI_134_164).contains(albumMapper2.getAlbumId(Category.VALIKAUSIHOUSUT.name(), testListResponse)));
    }
}
