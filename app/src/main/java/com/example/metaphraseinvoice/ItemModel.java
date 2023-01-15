package com.example.metaphraseinvoice;

import java.io.Serializable;

public class ItemModel implements Serializable {

    String description;
    String quantity;
    String price;

    public ItemModel(String description, String quantity, String price) {
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getDescription() {
        return description;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }
}
