package com.theironyard.entities;

import java.util.ArrayList;

/**
 * Created by dlocke on 2/6/17.
 */
public class WalmartList {

    String query;
    int numItems;
    ArrayList<WalmartItem>items;

    public WalmartList(String query, int numItems, ArrayList<WalmartItem> items) {
        this.query = query;
        this.numItems = numItems;
        this.items = items;
    }

    public WalmartList(){

    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getNumItems() {
        return numItems;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    public ArrayList<WalmartItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<WalmartItem> items) {
        this.items = items;
    }
}
