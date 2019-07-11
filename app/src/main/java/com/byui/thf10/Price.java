package com.byui.thf10;

import androidx.annotation.Nullable;
import java.util.Date;

public class Price extends JsonConvertible{
    private Date start_date;
    private Date end_date;
    private float amount;
    private boolean active;
    private String description;

    // getters

    public Date getEnd_date(){ return end_date; }

    public Date getStart_date(){return start_date;}

    public boolean getActive(){return this.active;}

    public float getPrice(){return amount;}

    public String getDescription() { return description; }

    // setters


    public void setActive(boolean active) { this.active = active;}

    public void setDescription(String description) { this.description = description; }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setEnd_date(@Nullable Date end_date) { this.end_date = end_date;}

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

}
