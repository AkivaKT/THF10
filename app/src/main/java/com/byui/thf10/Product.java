package com.byui.thf10;


public class Product extends JsonConvertible {
    private String type;
    private String series;
    private String name;
    private String color1;
    private String color2;
    private String quantity;

    // getters
    String getQuantity() { return quantity; }

    String getColor1() {
        return color1;
    }

    String getColor2() {
        return color2;
    }

    public String getName() { return name; }

    String getSeries() {
        return series;
    }

    String getType() {
        return type;
    }

    // setters

    /**
     * Color 1 and 2 are describing the ties.
     */
    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    /**
     * @param name Ties have names that designate what kind of product they are.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The purpose of this is to track the number of inventory on each new product.
     * @param quantity
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * @param series Describes the group or collection that the ties come from.
     */
    public void setSeries(String series) {
        this.series = series;
    }

    /**
     * @param type Describes the type of Tie.
     */
    public void setType(String type) {
        this.type = type;
    }


}

