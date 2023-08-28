package com.test.payment.paymenttest.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory")
public class Inventory {
    public Inventory() {}
    
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long itemId;

    
    @Column(name = "item_name")
    private String itemName;

    
    @Column(name = "quantity")
    private long quantity;

    
    @Column(name = "price")
    private long price;


    public long getItemId() {
        return itemId;
    }


    public void setItemId(long itemId) {
        this.itemId = itemId;
    }


    public String getItemName() {
        return itemName;
    }


    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


    public long getQuantity() {
        return quantity;
    }


    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }


    public long getPrice() {
        return price;
    }


    public void setPrice(long price) {
        this.price = price;
    }

    
}