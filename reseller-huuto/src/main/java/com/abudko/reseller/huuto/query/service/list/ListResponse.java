package com.abudko.reseller.huuto.query.service.list;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.abudko.reseller.huuto.query.enumeration.Classification;
import com.abudko.reseller.huuto.query.html.HtmlParserConstants;
import com.abudko.reseller.huuto.query.service.item.ItemResponse;

public class ListResponse implements Comparable<ListResponse> {

    private Logger log = LoggerFactory.getLogger(getClass());

    private String description;
    private String itemUrl;
    private String imgBaseSrc;
    private String bids;
    private String last;
    private String currentPrice;
    private String fullPrice;
    private String newPrice;
    private String condition;
    private String size;
    private String brand;
    private ItemResponse itemResponse;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
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

    public String getBids() {
        return bids;
    }

    public void setBids(String bids) {
        this.bids = bids;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(String fullPrice) {
        this.fullPrice = fullPrice;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isBrandNew() {
        return Classification.NEW.getConditionParam().equalsIgnoreCase(this.condition);
    }

    public ItemResponse getItemResponse() {
        return itemResponse;
    }

    public void setItemResponse(ItemResponse itemResponse) {
        this.itemResponse = itemResponse;
    }

    public boolean hasManySizes() {
        return getItemResponse() != null && StringUtils.isEmpty(getSize()) && !getItemResponse().getSizes().isEmpty();
    }

    public String dump() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");

        sb.append("description: ");
        sb.append(this.description);
        sb.append("\n");

        String prices = String.format("prices: %s (%s), [%s]", this.currentPrice, this.fullPrice, this.newPrice);
        sb.append(prices);
        sb.append("\n");

        sb.append("itemUrl: ");
        sb.append(HtmlParserConstants.HUUTO_NET);
        sb.append(HtmlParserConstants.ITEM_URL_CONTEXT);
        sb.append(this.itemUrl);
        sb.append("\n");

        sb.append("bids: ");
        sb.append(this.bids);
        sb.append("\n");

        sb.append("last: ");
        sb.append(this.last);
        sb.append("\n");

        sb.append("Condition: ");
        sb.append(Classification.getClassification(this.condition));
        sb.append("\n");

        if (this.itemResponse != null) {
            sb.append("Seller: ");
            sb.append(this.itemResponse.getSeller());
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((itemUrl == null) ? 0 : itemUrl.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ListResponse other = (ListResponse) obj;
        if (itemUrl == null) {
            if (other.itemUrl != null)
                return false;
        } else if (!itemUrl.equals(other.itemUrl))
            return false;
        return true;
    }

    @Override
    public int compareTo(ListResponse response) {
        double priceThis = 0;
        double priceThat = 0;
        try {
            priceThis = Double.parseDouble(this.newPrice);
            priceThat = Double.parseDouble(response.getNewPrice());
        } catch (Exception e) {
            String errMsg = String.format("Unable to parse priceThis %s and priceThat %s", this.newPrice,
                    response.getNewPrice());
            log.warn(errMsg);
        }
        return priceThis > priceThat ? 1 : priceThis < priceThat ? -1 : 0;
    }

    @Override
    public String toString() {
        return "ListResponse [description=" + description + ", currentPrice=" + currentPrice + ", itemUrl=" + itemUrl
                + ", imgBaseSrc=" + imgBaseSrc + ", bids=" + bids + ", last=" + last + ", fullPrice=" + fullPrice
                + ", newPrice=" + newPrice + ", condition=" + condition + ", size=" + size + ", brand=" + brand
                + ", itemResponse=" + itemResponse + "]";
    }
}
