package com.abudko.reseller.huuto.query.service.list.json;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import com.abudko.reseller.huuto.query.service.item.json.JsonItemResponse;
import com.abudko.reseller.huuto.query.service.list.ListResponse;
import com.abudko.reseller.huuto.query.service.list.SizeParser;

public class JsonListResponse {
	
	private List<JsonItemResponse> items;
	
	public List<JsonItemResponse> getItems() {
		return items;
	}

	public void setItems(List<JsonItemResponse> items) {
		this.items = items;
	}
	
	public Collection<ListResponse> getListResponses() {
		Collection<ListResponse> responses = new LinkedHashSet<ListResponse>();
		
		List<JsonItemResponse>  jsonItems = getItems();
		for (JsonItemResponse jsonItemResponse : jsonItems) {
			ListResponse listResponse = new ListResponse();
			responses.add(listResponse);
			
			listResponse.setCurrentPrice(jsonItemResponse.getCurrentPrice());
			listResponse.setFullPrice(jsonItemResponse.getBuyNowPrice());
			listResponse.setDescription(jsonItemResponse.getTitle());
			listResponse.setBrand(jsonItemResponse.getItemResponse().getItemInfo().getBrand());
			listResponse.setItemResponse(jsonItemResponse.getItemResponse());
			listResponse.setItemUrl(jsonItemResponse.getItemResponse().getItemUrl());
			listResponse.setLast(jsonItemResponse.getClosingTime());
			listResponse.setSize(SizeParser.getSize(jsonItemResponse.getTitle()));
		}
		
		return responses;
	}
}
