package com.byui.thf10;

import java.util.Date;

public class Sale extends JsonConvertible {

    // Default constructor
    Sale() {
    }

    // data
    public int product_id;
    public int price_id;
    public int account_id;
    public Date date;
    public int amount;
    public boolean discount;
    public float shippingCost;
    public int created_by;

    //getters

    public int getPrice_id(){ return price_id; }
    public int getAccount_id(){ return account_id; }
    public int getProduct_id(){ return product_id; }
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

    public void setPrice_id(int price_id){ this.price_id = price_id; }
    public void setProduct_id(int product_id){ this.product_id = product_id; }
    public void setAccount_id(int account_id){ this.account_id = account_id; }
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
