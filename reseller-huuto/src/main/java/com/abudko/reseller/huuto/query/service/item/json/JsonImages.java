package com.abudko.reseller.huuto.query.service.item.json;

public class JsonImages {
	
	private JsonImageLinks links;

	public JsonImageLinks getLinks() {
		return links;
	}

	public void setLinks(JsonImageLinks links) {
		this.links = links;
	}
	
	@Override
	public String toString() {
		return "JsonImages [links=" + links + "]";
	}

}
