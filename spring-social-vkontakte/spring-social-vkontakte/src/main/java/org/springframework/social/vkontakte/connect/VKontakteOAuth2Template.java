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
package org.springframework.social.vkontakte.connect;

import java.util.Map;

import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * VKontakte-specific extension of OAuth2Template.
 * @author vkolodrevskiy
 */
public class VKontakteOAuth2Template extends OAuth2Template {
    private String uid = null;
    
    private final String clientId;

	public VKontakteOAuth2Template(String clientId, String clientSecret) {
		super(clientId, clientSecret, "http://oauth.vk.com/authorize", "https://oauth.vk.com/access_token");
        setUseParametersForClientAuthentication(true);
        this.clientId = clientId;
	}

    // override this method simply to get uid, didn't find better way,
    // using it while getting profile info for the current user
    // also when scope has "offline" option VKontakte returns expires_in=0, setting it to null in this case
    @Override
    protected AccessGrant createAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, Map<String, Object> response) {
        uid = Integer.toString((Integer) response.get("user_id"));
        return super.createAccessGrant(accessToken, scope, refreshToken, expiresIn == 0 ? null : expiresIn, response);
    }

    public String getUid() {
        return uid;
    }
    
    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri,
            MultiValueMap<String, String> additionalParameters) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("client_id", clientId);
        params.set("redirect_uri", "https://oauth.vk.com/blank.html");
        params.set("display", "page");
        params.set("scope", "notify,friends,photos,audio,video,notes,pages,wall,offline");
        params.set("v", "5.2");
        params.set("response_type", "token");
        String object = getRestTemplate().getForObject("https://oauth.vk.com/authorize?scope=notify,friends,photos,audio,video,notes,pages,wall,offline&redirect_uri=https://oauth.vk.com/authorize&display=page&v=5.2&response_type=token&client_id="+clientId, String.class);
        return null;
//        return createAccessGrant((String) result.get("access_token"), (String) result.get("scope"), (String) result.get("refresh_token"), getIntegerValue(result, "expires_in"), result);
    }
}
