package com.byui.thf10;

import androidx.annotation.Nullable;


public class Price extends JsonConvertible{

    //member variables
    private String start_date;
    private String end_date;
    private float price;
    private boolean active;
    private String description;

    // getters

    public String getEnd_date(){ return end_date; }

    public String getStart_date(){return start_date;}

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
     * @param end_date
     */
    public void setEnd_date(@Nullable String end_date) { this.end_date = end_date;}

    /**
     * Start date , the price becomes active on the start date.
     * @param start_date
     */
    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

}
