package com.byui.thf10;

import java.util.Date;

public class Sale extends JsonConvertible {

    // Default constructor
    Sale() {
    }

    // data
    private Product product;
    private Price price;
    private String account;
    private String date;
    private String quantity;

    //getters

    public Price getPrice(){ return price; }
    public String getAccount(){ return account; }
    public Product getProduct(){ return product; }
    public String getDate() {
        return date;
    }
    public String getQuantity() {
        return quantity;
    }

    //setters

    public void setPrice(Price price){ this.price = price; }
    public void setProduct(Product product){ this.product = product; }
    public void setAccount(String account){ this.account = account; }
    public void setDate(String date) {
        this.date = date;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
