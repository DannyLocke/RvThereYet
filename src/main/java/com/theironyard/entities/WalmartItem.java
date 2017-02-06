package com.theironyard.entities;

import java.math.BigDecimal;

/**
 * Created by dlocke on 2/2/17.
 */
public class WalmartItem {



    int itemId;
    String name;
    BigDecimal msrp;
    BigDecimal salePrice;
    String upc;

    public WalmartItem(int itemId, String name, BigDecimal msrp, BigDecimal salePrice, String upc) {
        this.itemId = itemId;
        this.name = name;
        this.msrp = msrp;
        this.salePrice = salePrice;
        this.upc = upc;
    }

    public WalmartItem(){}

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMsrp() {
        return msrp;
    }

    public void setMsrp(BigDecimal msrp) {
        this.msrp = msrp;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }
}
