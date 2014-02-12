package com.abudko.scheduled.jobs.huuto;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.abudko.reseller.huuto.query.service.item.QueryItemService;
import com.abudko.scheduled.rules.ItemValidityRules;
import com.abudko.scheduled.service.PhotoManager;
import com.abudko.scheduled.service.huuto.AlbumMapper;
import com.abudko.scheduled.service.huuto.PublishManagerUtils;
import com.abudko.scheduled.vkontakte.Photo;

@RunWith(MockitoJUnitRunner.class)
public class CleanSchedulerTest {
    
    @Mock
    private AlbumMapper albumMapper;

    @Mock
    private PhotoManager photoManager;

    @Mock
    private PublishManagerUtils publishManagerUtils;

    @Mock
    private QueryItemService atomQueryItemService;
    
    @Mock
    private ItemValidityRules rule;
    
    @InjectMocks
    public CleanScheduler cleanScheduler = new CleanScheduler();
    
    @Before
    public void setup() {
        List<ItemValidityRules> rules = new ArrayList<ItemValidityRules>();
        rules.add(rule);
        
        ReflectionTestUtils.setField(cleanScheduler, "itemValidityRules", rules);
    }

    @Test
    public void testPhotoIdValid() throws InterruptedException {
        final String id = "id";
        final String albumId = "albumid"; 
        List<String> albumIds = Arrays.asList(albumId);
        when(photoManager.getAlbumIds(AlbumMapper.GROUP_ID)).thenReturn(albumIds);
        Photo photo = new Photo();
        final String description = "description";
        final String photoId = "photoId";
        photo.setDescription(description);
        photo.setPhotoId(photoId);
        List<Photo> photoIds = Arrays.asList(photo);
        when(photoManager.getPhotos(AlbumMapper.GROUP_ID, albumId)).thenReturn(photoIds);
        when(publishManagerUtils.getId(description)).thenReturn(id);
        when(rule.isValid(id)).thenReturn(true);
        
        cleanScheduler.schedule();
        
        verify(photoManager, never()).deletePhotoForce(photoId, AlbumMapper.GROUP_ID);
    }
    
    @Test
    public void testPhotoIdInValid() throws InterruptedException {
        final String id = "id";
        final String albumId = "albumid"; 
        List<String> albumIds = Arrays.asList(albumId);
        when(photoManager.getAlbumIds(AlbumMapper.GROUP_ID)).thenReturn(albumIds);
        Photo photo = new Photo();
        final String description = "description";
        final String photoId = "photoId";
        photo.setDescription(description);
        photo.setPhotoId(photoId);
        List<Photo> photoIds = Arrays.asList(photo);
        when(photoManager.getPhotos(AlbumMapper.GROUP_ID, albumId)).thenReturn(photoIds);
        when(publishManagerUtils.getId(description)).thenReturn(id);
        when(rule.isValid(id)).thenReturn(false);
        
        cleanScheduler.schedule();
        
        verify(photoManager).deletePhotoForce(photoId, AlbumMapper.GROUP_ID);
    }

}
