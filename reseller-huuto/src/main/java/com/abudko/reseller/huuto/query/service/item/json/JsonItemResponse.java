package com.abudko.reseller.huuto.query.service.item.json;

import java.util.List;

import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.ItemStatus;

public class JsonItemResponse {
	
	private String condition;
	private String location;
	private String currentPrice;
	private String buyNowPrice;
	private String seller;
	private String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private List<JsonImages> images;
	
	public List<JsonImages> getImages() {
		return images;
	}

	public void setImages(List<JsonImages> images) {
		this.images = images;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return
	 */
	public ItemResponse getItemResponse() {
		ItemResponse itemResponse = new ItemResponse();
		
		itemResponse.setCondition(this.condition);
		itemResponse.setLocation(this.location);
		itemResponse.setCurrentPrice(this.currentPrice);
		itemResponse.setPrice(this.buyNowPrice);
		itemResponse.setSeller(this.seller);
		itemResponse.setItemStatus("open".equals(this.status) ? ItemStatus.OPENED : ItemStatus.CLOSED);
		
		String original = this.getImages().get(0).getLinks().getOriginal();
		int index = original.indexOf("-orig.jpg");
		itemResponse.setImgBaseSrc(original.substring(0, index));
		
		return itemResponse;
	}

	public String getBuyNowPrice() {
		return buyNowPrice;
	}

	public void setBuyNowPrice(String buyNowPrice) {
		this.buyNowPrice = buyNowPrice;
	}

}
