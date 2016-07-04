package com.udacity.abng.inventoryapp.model;

import android.graphics.Bitmap;

/**
 * Created by yul on 29.06.16.
 */
public class Item {

    private long id;
    private String title;
    private String description;
    private Bitmap imageBit;
    private double price;
    private int quantity;

    public Item(String title, String desc, Bitmap imageBit, double price, int quantity) {
        this.title = title;
        this.description = desc;
        this.imageBit = imageBit;
        this.price = price;
        this.quantity = quantity;
    }

    public Item() {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Bitmap getImageBit() {
        return imageBit;
    }

    public void setImageBit(Bitmap imageBit) {
        this.imageBit = imageBit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageBit=" + imageBit +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
