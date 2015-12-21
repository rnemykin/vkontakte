package com.abudko.reseller.huuto.query.service.item.json;

import java.util.List;

import com.abudko.reseller.huuto.query.enumeration.Brand;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;
import com.abudko.reseller.huuto.query.service.item.ItemStatus;

public class JsonItemResponse {
	
	private String condition;
	private String location;
	private String currentPrice;
	private String buyNowPrice;
	private String seller;
	private String status;
	private String title;
	private String closingTime;
	private JsonLinks links;
	private List<JsonImages> images;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<JsonImages> getImages() {
		return images;
	}

	public void setImages(List<JsonImages> images) {
		this.images = images;
	}

	public JsonLinks getLinks() {
		return links;
	}

	public void setLinks(JsonLinks links) {
		this.links = links;
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
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(String closingTime) {
		this.closingTime = closingTime;
	}
	
	public String getBuyNowPrice() {
		return buyNowPrice;
	}

	public void setBuyNowPrice(String buyNowPrice) {
		this.buyNowPrice = buyNowPrice;
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
		itemResponse.setItemUrl(links.getSelf());
		
		String brand = parseBrand(this.title);
		itemResponse.getItemInfo().setBrand(brand);
		
		if (this.getImages() != null && !this.getImages().isEmpty()) {
			JsonImages jsonImages = this.getImages().get(0);
			String original = jsonImages.getLinks().getOriginal();
			int index = original.indexOf("-orig.jpg");
			itemResponse.setImgBaseSrc(original.substring(0, index));
		}
		
		return itemResponse;
	}

	public String parseBrand(String description) {
		Brand brand = Brand.getBrandFrom(description);
		if (brand != null) {
			return brand.getFullName();
		}
		return null;
	}

	@Override
	public String toString() {
		return "JsonItemResponse [condition=" + condition + ", location=" + location + ", currentPrice=" + currentPrice
				+ ", buyNowPrice=" + buyNowPrice + ", seller=" + seller + ", status=" + status + ", title=" + title
				+ ", closingTime=" + closingTime + ", links=" + links + ", images=" + images + "]";
	}
}
