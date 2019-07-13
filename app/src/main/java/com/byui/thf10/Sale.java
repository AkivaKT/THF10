package com.byui.thf10;

import java.util.Date;

public class Sale extends JsonConvertible {

    // Default constructor
    Sale() {
    }

    // data
    public String product;
    public String price;
    public Account account;
    public Date date;
    public int quantity;
    public boolean discount;
    public float shippingCost;
    public int created_by;

    //getters

    public String getPrice_id(){ return price; }
    public Account getAccount_id(){ return account; }
    public String getProduct_id(){ return product; }
    public Date getDate() {
        return date;
    }
    public int getAmount() {
        return quantity;
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

    public void setPrice(String price){ this.price = price; }
    public void setProduct(String product){ this.product = product; }
    public void setAccount_id(Account account){ this.account = account; }
    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setQuantity(String amount) {
        this.quantity = quantity;
    }
    public void setDiscount(boolean discount) {
        this.discount = discount;
    }
    public void setShippingCost(float shippingCost) {
        this.shippingCost = shippingCost;
    }
}
