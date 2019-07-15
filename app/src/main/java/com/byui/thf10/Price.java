package com.byui.thf10;

import androidx.annotation.Nullable;


public class Price extends JsonConvertible{
    private String start_date;
    private String end_date;
    private float amount;
    private boolean active;
    private String description;

    // getters

    public String getEnd_date(){ return end_date; }

    public String getStart_date(){return start_date;}

    public boolean getActive(){return this.active;}

    public float getPrice(){return amount;}

    public String getDescription() { return description; }


    // setters


    public void setActive(boolean active) { this.active = active;}

    public void setDescription(String description) { this.description = description; }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setEnd_date(@Nullable String end_date) { this.end_date = end_date;}

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

}
