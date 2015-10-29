package com.abudko.reseller.huuto.query.service.item.json;

public class JsonImageLinks {
	
	private String self;
	private String thumbnail;
	private String medium;
	private String original;
	
	public String getSelf() {
		return self;
	}
	public void setSelf(String self) {
		this.self = self;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getMedium() {
		return medium;
	}
	public void setMedium(String medium) {
		this.medium = medium;
	}
	public String getOriginal() {
		return original;
	}
	public void setOriginal(String original) {
		this.original = original;
	}
	
	@Override
	public String toString() {
		return "JsonImageLinks [self=" + self + ", thumbnail=" + thumbnail + ", medium=" + medium + ", original="
				+ original + "]";
	}

}
