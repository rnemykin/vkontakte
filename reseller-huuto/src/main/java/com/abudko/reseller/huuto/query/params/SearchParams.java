package com.abudko.reseller.huuto.query.params;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import com.abudko.reseller.huuto.query.enumeration.Category;
import com.abudko.reseller.huuto.validation.ValidationConstants;

public class SearchParams implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String biddernro;

    private String category;
    
    private String categoryenum;

    /**
     * condition: 0 - all, 1 - uusi, 2 - hyvä, 3 - etc
     */
    private String classification;

    private String closingtime;

    private String addtime;

    /**
     * keyword
     */
    private String words;

    private String area;

    @Pattern(regexp = ValidationConstants.NUMBER_MASK)
    private String price_max;

    @Pattern(regexp = ValidationConstants.NUMBER_MASK)
    private String price_min;

    @Pattern(regexp = ValidationConstants.NUMBER_MASK)
    private String sizeMin;

    @Pattern(regexp = ValidationConstants.NUMBER_MASK)
    private String sizeMax;

    private String seller;

    /**
     * S - kauppat, P - yksityiset
     */
    private String seller_type;

    /**
     * O - osta heti, H - huutokauppa 
     */
    private String sellstyle;

    /**
     *  open, closed
     */
    private String status;

    private String zipcode;

    private String brand;
    
    private Integer discount;

	public String getBiddernro() {
        return biddernro;
    }

    public void setBiddernro(String biddernro) {
        this.biddernro = biddernro;
    }

    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryenum() {
        return categoryenum;
    }

    public void setCategoryenum(String categoryenum) {
        this.categoryenum = categoryenum;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getClosingtime() {
        return closingtime;
    }

    public void setClosingtime(String closingtime) {
        this.closingtime = closingtime;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPrice_max() {
        return price_max;
    }

    public void setPrice_max(String price_max) {
        this.price_max = price_max;
    }

    public String getPrice_min() {
        return price_min;
    }

    public void setPrice_min(String price_min) {
        this.price_min = price_min;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getSeller_type() {
        return seller_type;
    }

    public void setSeller_type(String seller_type) {
        this.seller_type = seller_type;
    }

    public String getSellstyle() {
        return sellstyle;
    }

    public void setSellstyle(String sellstyle) {
        this.sellstyle = sellstyle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSizeMin() {
        return sizeMin;
    }

    public void setSizeMin(String sizeMin) {
        this.sizeMin = sizeMin;
    }

    public String getSizeMax() {
        return sizeMax;
    }

    public void setSizeMax(String sizeMax) {
        this.sizeMax = sizeMax;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	@Override
	public String toString() {
		return "SearchParams [biddernro=" + biddernro + ", category=" + category + ", categoryenum=" + categoryenum
				+ ", classification=" + classification + ", closingtime=" + closingtime + ", addtime=" + addtime
				+ ", words=" + words + ", area=" + area + ", price_max=" + price_max + ", price_min=" + price_min
				+ ", sizeMin=" + sizeMin + ", sizeMax=" + sizeMax + ", seller=" + seller + ", seller_type="
				+ seller_type + ", sellstyle=" + sellstyle + ", status=" + status + ", zipcode=" + zipcode + ", brand="
				+ brand + ", discount=" + discount + "]";
	}
}
