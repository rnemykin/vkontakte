package com.abudko.reseller.huuto.query.service.item;

public class ItemInfo {
    
    private String brand;
    
    private String size;

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public String getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "ItemInfo [brand=" + brand + ", size=" + size + "]";
    }
}
