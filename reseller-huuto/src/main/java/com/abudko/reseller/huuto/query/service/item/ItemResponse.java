package com.abudko.reseller.huuto.query.service.item;

import java.util.ArrayList;
import java.util.List;

public class ItemResponse {

    private String id;

    private String location;

    private Boolean hv;

    private String condition;

    private String itemUrl;

    private String imgBaseSrc;

    private String price;

    private String currentPrice;

    private String newPrice;

    private String seller;
    
    private ItemInfo itemInfo = new ItemInfo();

    private ItemStatus itemStatus;
    
    private List<String> sizes = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean isHv() {
        return hv;
    }

    public void setHv(Boolean hv) {
        this.hv = hv;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public String getImgBaseSrc() {
        return imgBaseSrc;
    }

    public void setImgBaseSrc(String imgBaseSrc) {
        this.imgBaseSrc = imgBaseSrc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    
    public String getCurrentPrice() {
        return currentPrice;
    }
    
    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public ItemInfo getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(ItemInfo itemInfo) {
        this.itemInfo = itemInfo;
    }

    public ItemStatus getItemStatus() {
        return this.itemStatus;
    }
    
    public void setItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }
    
    public List<String> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    @Override
    public String toString() {
        return "ItemResponse [id=" + id + ", location=" + location + ", hv=" + hv + ", condition=" + condition
                + ", itemUrl=" + itemUrl + ", imgBaseSrc=" + imgBaseSrc + ", price=" + price + ", newPrice=" + newPrice
                + ", seller=" + seller + ", itemInfo=" + itemInfo + ", itemStatus=" + itemStatus + ", sizes=" + sizes
                + "]";
    }
}
