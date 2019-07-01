package com.byui.thf10;

import java.util.Date;

public class Sale extends JsonConvertible {

    // Default constructor
    Sale() {
    }

    // data
    public Product product;
    public Price price;
    public Account account;
    public Date date;
    public int amount;
    public boolean discount;
    public float shippingCost;
    public int created_by;

    //getters

    public Price getPrice_id(){ return price; }
    public Account getAccount_id(){ return account; }
    public Product getProduct_id(){ return product; }
    public Date getDate() {
        return date;
    }
    public int getAmount() {
        return amount;
    }
    public boolean getDiscount() {
        return discount;
    }
    public float getshippingCost() {
        return shippingCost;
    }
    public int getCreated_by() {
        return created_by;
    }

    //setters

    public void setPrice_id(Price price){ this.price = price; }
    public void setProduct_id(Product product){ this.product = product; }
    public void setAccount_id(Account account){ this.account = account; }
    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public void setDiscount(boolean discount) {
        this.discount = discount;
    }
    public void setShippingCost(float shippingCost) {
        this.shippingCost = shippingCost;
    }
}
