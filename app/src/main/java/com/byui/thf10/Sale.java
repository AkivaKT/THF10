package com.byui.thf10;

import java.util.Date;

public class Sale extends JsonConvertible {

    // Default constructor
    Sale() {
    }

    // data
    private String product;
    private String price;
    private String account;
    private Date date;
    private String quantity;

    //getters

    public String getPrice(){ return price; }
    public String getAccount(){ return account; }
    public String getProduct(){ return product; }
    public Date getDate() {
        return date;
    }
    public String getQuantity() {
        return quantity;
    }

    //setters

    public void setPrice(String price){ this.price = price; }
    public void setProduct(String product){ this.product = product; }
    public void setAccount(String account){ this.account = account; }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
