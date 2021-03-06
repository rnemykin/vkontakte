package com.abudko.scheduled.jobs.publish;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.abudko.reseller.huuto.query.service.item.QueryItemService;
import com.abudko.scheduled.rules.AbstractItemValidityRules;
import com.abudko.scheduled.rules.ItemValidityRules;
import com.abudko.scheduled.service.PhotoManager;
import com.abudko.scheduled.service.huuto.AlbumMapper;
import com.abudko.scheduled.service.huuto.PublishManagerUtils;
import com.abudko.scheduled.service.huuto.clean.ItemValidityRulesAlbumCleaner;
import com.abudko.scheduled.vkontakte.Photo;

@RunWith(MockitoJUnitRunner.class)
public class CleanSchedulerTest {
    
    private static final String DESCRIPTION = "description";
    private static final String ID = "id";
    private static final String ENCODED_URL = "ENCODED_URL";
    private static final String PHOTO_ID = "photoId";
    private static final String ALBUM_ID = "albumid";
    
    @Mock
    private AlbumMapper albumMapper;

    @Mock
    private PhotoManager photoManager;

    @Mock
    private PublishManagerUtils publishManagerUtils;

    @Mock
    private QueryItemService queryItemService;
    
    @Mock
    private AbstractItemValidityRules rule1;
    
    @Mock
    private AbstractItemValidityRules rule2;
    
    @InjectMocks
    private ItemValidityRulesAlbumCleaner itemValidityRulesAlbumCleaner = new ItemValidityRulesAlbumCleaner();
    
    @InjectMocks
    public CleanScheduler cleanScheduler = new CleanScheduler();
    
    private Photo photo;
    
    @Before
    public void setup() throws InterruptedException {
        List<ItemValidityRules> rules = new ArrayList<ItemValidityRules>();
        rules.add(rule1);
        rules.add(rule2);
        
        ReflectionTestUtils.setField(cleanScheduler, "itemValidityRulesAlbumCleaner", itemValidityRulesAlbumCleaner);
        ReflectionTestUtils.setField(itemValidityRulesAlbumCleaner, "itemValidityRules", rules);
        setupAlbums();
    }
    
    private void setupAlbums() throws InterruptedException {
        final String albumId = "albumid"; 
        List<String> albumIds = Arrays.asList(albumId);
        when(photoManager.getAlbumIds(AlbumMapper.GROUP_ID)).thenReturn(albumIds);
        photo = new Photo();
        photo.setDescription(DESCRIPTION);
        photo.setPhotoId(PHOTO_ID);
        photo.setCreated(Calendar.getInstance());
        List<Photo> photoIds = Arrays.asList(photo);
        when(photoManager.getPhotos(AlbumMapper.GROUP_ID, albumId)).thenReturn(photoIds);
        when(publishManagerUtils.getId(DESCRIPTION)).thenReturn(ID);
        when(publishManagerUtils.getDecodedURL(DESCRIPTION)).thenReturn(ENCODED_URL);
        when(rule1.getIdPrefix()).thenReturn("LE");
        when(rule2.getIdPrefix()).thenReturn("RE");
    }

    @Test
    public void testPhotoIdValid() throws InterruptedException {
        when(rule1.isValid(ID)).thenReturn(true);
        when(rule2.isValid(ID)).thenReturn(true);
        
        cleanScheduler.schedule();
        
        verify(photoManager, never()).deletePhoto(photo, AlbumMapper.GROUP_ID, ALBUM_ID);
    }
    
    @Test
    public void testPhotoIdInvalid() throws InterruptedException {
        when(rule1.isValid(ID)).thenReturn(false);
        when(rule2.isValid(ID)).thenReturn(false);
        
        cleanScheduler.schedule();
        
        verify(photoManager).deletePhoto(photo, AlbumMapper.GROUP_ID, ALBUM_ID);
    }
    
    @Test
    public void testPhotoIdInvalidForOneRuleButEncodedUrlIsValid() throws InterruptedException {
    	when(rule1.isValid(ID)).thenReturn(false);
    	when(rule1.isValid("LE" + ENCODED_URL)).thenReturn(true);
        when(rule2.isValid(ID)).thenReturn(true);
            	
    	cleanScheduler.schedule();
    	
    	verify(photoManager, never()).deletePhoto(photo, AlbumMapper.GROUP_ID, ALBUM_ID);
    }
    
    @Test
    public void testPhotoIdInvalidForOneRule() throws InterruptedException {
        when(rule1.isValid(ID)).thenReturn(false);
        when(rule2.isValid(ID)).thenReturn(true);
        
        cleanScheduler.schedule();
        
        verify(photoManager).deletePhoto(photo, AlbumMapper.GROUP_ID, ALBUM_ID);
    }
}
