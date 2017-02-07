package com.theironyard.entities;

import java.math.BigDecimal;

/**
 * Created by dlocke on 2/6/17.
 */
public class EbayItem {

    int itemId;
    String title;
    BigDecimal currentPrice;

    public EbayItem(int itemId, String title, BigDecimal currentPrice) {
        this.itemId = itemId;
        this.title = title;
        this.currentPrice = currentPrice;
    }

    public EbayItem(){

    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }
}
