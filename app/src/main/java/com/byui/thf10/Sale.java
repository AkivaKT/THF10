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

    /**
     * @param price Describes the price of the products sold.
     *              This can fluctuate depending on time of year.
     */
    public void setPrice(Price price){ this.price = price; }

    /**
     * @param product Describes the product that is sold.
     *                A product can have multiple prices.
     */
    public void setProduct(Product product){ this.product = product; }

    /**
     * @param account Describes the account of the user who amde the sale.
     */
    public void setAccount(String account){ this.account = account; }

    /**
     * @param date This will determine the price of the product sold.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @param quantity Describes the number of ties sold in a specific sale.
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
