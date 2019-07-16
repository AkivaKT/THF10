package com.byui.thf10;

import androidx.annotation.Nullable;


public class Price extends JsonConvertible{

    //member variables
    private String start_Date;
    private String end_Date;
    private float price;
    private boolean active;
    private String description;

    // getters

    public String getEnd_Date(){ return end_Date; }

    public String getStart_Date(){return start_Date;}

    public boolean getActive(){return this.active;}

    public float getPrice(){return price;}

    public String getDescription() { return description; }

    // setters

    /**
     * making price available
     * @param active if the price is available at this point or not
     */
    public void setActive(boolean active) { this.active = active;}

    /**
     * setting description of the price
     * @param description the simple description of the price (e.g. 10% off, regular)
     */
    public void setDescription(String description) { this.description = description; }

    public void setPrice(float amount) {
        this.price = amount;
    }

    /**
     * Nullable end_date, built for future reference. Price expires on the end date, and it will
     * no longer be active. (yet to implement)
     * @param end_Date
     */
    public void setEnd_Date(@Nullable String end_Date) { this.end_Date = end_Date;}

    /**
     * Start date , the price becomes active on the start date.
     * @param start_Date
     */
    public void setStart_Date(String start_Date) {
        this.start_Date = start_Date;
    }

}
