package com.abudko.scheduled.service.huuto;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.Arrays;
import java.util.Locale;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import com.abudko.reseller.huuto.image.ImageManipulator;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.scheduled.csv.PhotoData;
import com.abudko.scheduled.service.PhotoManager;

@RunWith(MockitoJUnitRunner.class)
public class PublishManagerTest {

    @Mock
    private PhotoManager photoManager;

    @Mock
    private ApplicationContext context;

    @Mock
    private ImageManipulator imageManipulator;

    @InjectMocks
    private PublishManager publishManager = new PublishManager();

    @Before
    public void setup() {
        setField(publishManager, "imageTempFileLocation", "imageTempFileLocation");
    }

    @Test
    public void testDescriptionId() throws Exception {
        ListResponse listResponse = new ListResponse();
        ItemResponse itemResponse = new ItemResponse();
        final String id = "id";
        itemResponse.setId(id);
        listResponse.setItemResponse(itemResponse);
        when(
                context.getMessage(Mockito.anyString(),
                        Mockito.argThat(Matchers.<Object> hasItemInArray(is((Object) id))), Mockito.any(Locale.class)))
                .thenReturn(id);

        publishManager.publishResults(Arrays.asList(listResponse));

        verify(photoManager).publishPhoto(
                Mockito.argThat(Matchers.<PhotoData> hasProperty("description", containsString(id))));
    }

    @Test
    public void testDescriptionSize() throws Exception {
        ListResponse listResponse = new ListResponse();
        ItemResponse itemResponse = new ItemResponse();
        final String size = "90";
        listResponse.setSize(size);
        listResponse.setItemResponse(itemResponse);
        when(
                context.getMessage(Mockito.anyString(),
                        Mockito.argThat(Matchers.<Object> hasItemInArray(is((Object) size))), Mockito.any(Locale.class)))
                .thenReturn(size);

        publishManager.publishResults(Arrays.asList(listResponse));

        verify(photoManager).publishPhoto(
                Mockito.argThat(Matchers.<PhotoData> hasProperty("description", containsString(size))));
    }

    @Test
    public void testDescriptionBrand() throws Exception {
        ListResponse listResponse = new ListResponse();
        ItemResponse itemResponse = new ItemResponse();
        final String brand = "reima";
        listResponse.setBrand(brand);
        listResponse.setItemResponse(itemResponse);
        when(
                context.getMessage(Mockito.anyString(),
                        Mockito.argThat(Matchers.<Object> hasItemInArray(is((Object) brand))),
                        Mockito.any(Locale.class))).thenReturn(brand);

        publishManager.publishResults(Arrays.asList(listResponse));

        verify(photoManager).publishPhoto(
                Mockito.argThat(Matchers.<PhotoData> hasProperty("description", containsString(brand))));
    }
    
    @Test
    public void testDescriptionNewPrice() throws Exception {
        ListResponse listResponse = new ListResponse();
        ItemResponse itemResponse = new ItemResponse();
        final String newPrice = "100";
        itemResponse.setNewPrice(newPrice);
        listResponse.setItemResponse(itemResponse);
        when(
                context.getMessage(Mockito.anyString(),
                        Mockito.argThat(Matchers.<Object> hasItemInArray(is((Object) newPrice))),
                        Mockito.any(Locale.class))).thenReturn(newPrice);
        
        publishManager.publishResults(Arrays.asList(listResponse));
        
        verify(photoManager).publishPhoto(
                Mockito.argThat(Matchers.<PhotoData> hasProperty("description", containsString(newPrice))));
    }

}