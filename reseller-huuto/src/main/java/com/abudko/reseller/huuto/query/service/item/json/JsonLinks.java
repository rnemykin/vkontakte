package com.abudko.reseller.huuto.query.service.item.json;

public class JsonLinks {
	
	private String self;
	private String category;
	private String alternative;
	private String images;
	
	public String getSelf() {
		return self;
	}
	public void setSelf(String self) {
		this.self = self;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getAlternative() {
		return alternative;
	}
	public void setAlternative(String alternative) {
		this.alternative = alternative;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	@Override
	public String toString() {
		return "JsonLinks [self=" + self + ", category=" + category + ", alternative=" + alternative + ", images="
				+ images + "]";
	}
}
