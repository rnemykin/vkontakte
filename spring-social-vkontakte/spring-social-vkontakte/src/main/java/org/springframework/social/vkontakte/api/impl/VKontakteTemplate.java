/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.vkontakte.api.impl;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.vkontakte.api.FeedOperations;
import org.springframework.social.vkontakte.api.FriendsOperations;
import org.springframework.social.vkontakte.api.PhotosOperations;
import org.springframework.social.vkontakte.api.UsersOperations;
import org.springframework.social.vkontakte.api.VKontakte;
import org.springframework.social.vkontakte.api.VKontakteErrorHandler;
import org.springframework.social.vkontakte.api.WallOperations;
import org.springframework.social.vkontakte.api.impl.json.VKontakteModule;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <p>This is the central class for interacting with VKontakte.</p>
 * <p>
 * There are some operations, such as searching, that do not require OAuth
 * authentication. In those cases, you may use a {@link VKontakteTemplate} that is
 * created through the default constructor and without any OAuth details.
 * Attempts to perform secured operations through such an instance, however,
 * will result in {@link org.springframework.social.NotAuthorizedException} being thrown.
 * </p>
 * @author vkolodrevskiy
 */
public class VKontakteTemplate extends AbstractOAuth2ApiBinding implements VKontakte {

    private UsersOperations usersOperations;
    private WallOperations wallOperations;
    private FriendsOperations friendsOperations;
    private FeedOperations feedOperations;
    private PhotosOperations photosOperations;

    private ObjectMapper objectMapper;

    private final String accessToken;
    private final String uid;

    // TODO: remove?
    public VKontakteTemplate() {
        initialize();
        this.accessToken = null;
        this.uid = null;
    }

    public VKontakteTemplate(String accessToken, String uid) {
        super(accessToken);
        this.accessToken = accessToken;
        this.uid = uid;
        initialize();
    }

    private void initialize() {
        registerJsonModule();
        getRestTemplate().setErrorHandler(new VKontakteErrorHandler());
        initSubApis();
    }

    private void registerJsonModule() {
        List<HttpMessageConverter<?>> converters = getRestTemplate().getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;

                List<MediaType> mTypes = new LinkedList<MediaType>(jsonConverter.getSupportedMediaTypes());
                mTypes.add(new MediaType("text", "javascript", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET));
                //mTypes.add(new MediaType("text", "html", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET));
                jsonConverter.setSupportedMediaTypes(mTypes);

                objectMapper = new ObjectMapper();
                objectMapper.registerModule(new VKontakteModule());
                jsonConverter.setObjectMapper(objectMapper);
            }
        }
    }

    private void initSubApis() {
        usersOperations = new UsersTemplate(getRestTemplate(), accessToken, uid, objectMapper, isAuthorized());
        friendsOperations = new FriendsTemplate(getRestTemplate(), accessToken, objectMapper, isAuthorized());
        wallOperations = new WallTemplate(getRestTemplate(), accessToken, objectMapper, isAuthorized());
        feedOperations = new FeedTemplate(getRestTemplate(), accessToken, objectMapper, isAuthorized());
        photosOperations = new PhotosTemplate(getRestTemplate(), accessToken, objectMapper, isAuthorized());
    }
    
    @Override
    protected FormHttpMessageConverter getFormMessageConverter() {
        FormHttpMessageConverter formMessageConverter = super.getFormMessageConverter();
        List<MediaType> mTypes = new LinkedList<MediaType>(formMessageConverter.getSupportedMediaTypes());
        mTypes.add(new MediaType("text", "html", Charset.forName("UTF-8")));
        formMessageConverter.setSupportedMediaTypes(mTypes);
        return formMessageConverter;
    }

    public UsersOperations usersOperations() {
        return usersOperations;
    }

    public WallOperations wallOperations() {
        return wallOperations;
    }

    public FriendsOperations friendsOperations() {
        return friendsOperations;
    }

    public FeedOperations feedOperations() {
        return feedOperations;
    }

    public PhotosOperations photosOperations() {
        return photosOperations;
    }
}
