package com.theironyard.entities;

import java.util.ArrayList;

/**
 * Created by dlocke on 2/6/17.
 */
public class EbayList {

    String findItemsByKeywords;
    int count;
    ArrayList<EbayItem> item;

    public EbayList(String findItemsByKeywordsResponse, int count, ArrayList<EbayItem> item) {
        this.findItemsByKeywords = findItemsByKeywords;
        //this.count = count;
        this.item = item;
    }

    public EbayList(){

    }

    public String getFindItemsByKeywords() {
        return findItemsByKeywords;
    }

    public void setFindItemsByKeywords(String findItemsByKeywordsResponse) {
        this.findItemsByKeywords = findItemsByKeywordsResponse;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<EbayItem> getItem() {
        return item;
    }

    public void setItem(ArrayList<EbayItem> item) {
        this.item = item;
    }
}
