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
import org.springframework.social.vkontakte.api.VKontakte;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class VKontaktePhotosController {
	
	@Inject
	private ConnectionRepository connectionRepository;

	@RequestMapping(value="/vkontakte/photos", method=RequestMethod.GET)
	public String add(Model model) {
		Connection<VKontakte> connection = connectionRepository.findPrimaryConnection(VKontakte.class);
		if (connection == null) {
			return "redirect:/connect/vkontakte";
		}
		
		PhotosOperations photosOperations = connection.getApi().photosOperations();
		UploadServer uploadServer = photosOperations.getUploadServer();
		
		model.addAttribute("uploadserver", uploadServer);
		
		UploadedPhoto uploadedPhoto = photosOperations.uploadPhoto(uploadServer.getUpload_url(), "src/main/resources/1.jpg");
		photosOperations.savePhoto(uploadedPhoto, "la la la");
		
		return "vkontakte/photos";
	}
	
	@RequestMapping(value="/vkontakte/photos/remove", method=RequestMethod.GET)
	public String remove(Model model) {
	    Connection<VKontakte> connection = connectionRepository.findPrimaryConnection(VKontakte.class);
	    if (connection == null) {
	        return "redirect:/connect/vkontakte";
	    }
	    
	    PhotosOperations photosOperations = connection.getApi().photosOperations();
	    UploadServer uploadServer = photosOperations.deletePhoto(photoId, ownerId)
	    
	    model.addAttribute("uploadserver", uploadServer);
	    
	    UploadedPhoto uploadedPhoto = photosOperations.uploadPhoto(uploadServer.getUpload_url(), "src/main/resources/1.jpg");
	    photosOperations.savePhoto(uploadedPhoto, "la la la");
	    
	    return "vkontakte/photos";
	}

}
