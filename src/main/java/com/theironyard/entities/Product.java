package com.theironyard.entities;

import javax.persistence.*;

/**
 * Created by dlocke on 1/31/17.
 */

@Entity
@Table(name="items")
public class Product {

    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String item;

    @ManyToOne
    User user;

    public Product(int id, String item){
        this.id = id;
        this.item = item;
    }

    public Product (String item){
        this.item = item;
    }

    public Product(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
