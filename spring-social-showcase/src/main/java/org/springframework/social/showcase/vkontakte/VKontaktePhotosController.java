/*
 * Copyright 2013 the original author or authors.
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
package org.springframework.social.showcase.vkontakte;

import javax.inject.Inject;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.vkontakte.api.PhotosOperations;
import org.springframework.social.vkontakte.api.UploadServer;
import org.springframework.social.vkontakte.api.UploadedPhoto;
import org.springframework.social.vkontakte.api.VKGenericResponse;
import org.springframework.social.vkontakte.api.VKontakte;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.JsonNode;

@Controller
public class VKontaktePhotosController {
    
    private static final String GROUP_ID = "11004536"; 
    private static final String ALBUM_ID = "166279845";
    private static final String COMMENT = "демисезонное полупальто Selected, размер 50, цена 890р, р-н Заречье (Комсомольская)";
	
	@Inject
	private ConnectionRepository connectionRepository;
	
	private static String photoId;

	@RequestMapping(value="/vkontakte/photos/add", method=RequestMethod.GET)
	public String add(Model model) {
		Connection<VKontakte> connection = connectionRepository.findPrimaryConnection(VKontakte.class);
		if (connection == null) {
			return "redirect:/connect/vkontakte";
		}
		
		PhotosOperations photosOperations = connection.getApi().photosOperations();
		UploadServer uploadServer = photosOperations.getUploadServer(GROUP_ID, ALBUM_ID);
		
		model.addAttribute("uploadserver", uploadServer);
		
		UploadedPhoto uploadedPhoto = photosOperations.uploadPhoto(uploadServer.getUpload_url(), "src/main/resources/1.jpg");
		
		VKGenericResponse response = photosOperations.savePhoto(uploadedPhoto, COMMENT);
		JsonNode jsonNode = response.getResponse();
		photoId = jsonNode.get(0).get("pid").asText();
		
		return "vkontakte/photos";
	}
	
	@RequestMapping(value="/vkontakte/photos/delete", method=RequestMethod.GET)
	public String delete(Model model) {
	    Connection<VKontakte> connection = connectionRepository.findPrimaryConnection(VKontakte.class);
	    if (connection == null) {
	        return "redirect:/connect/vkontakte";
	    }
	    
	    PhotosOperations photosOperations = connection.getApi().photosOperations();
	    String response = photosOperations.deletePhoto(photoId, GROUP_ID);
	    
	    System.err.println(response);
	    
	    return "vkontakte/photos";
	}

}
