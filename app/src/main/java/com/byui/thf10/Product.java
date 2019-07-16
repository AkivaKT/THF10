package com.byui.thf10;


public class Product extends JsonConvertible {
    private String type;
    private String series;
    private String name;
    private String color1;
    private String color2;
    private String quantity;


    public String getQuantity() { return quantity; }

    public String getColor1() {
        return color1;
    }

    public String getColor2() {
        return color2;
    }

    public String getName() { return name; }

    public String getSeries() {
        return series;
    }

    public String getType() {
        return type;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public void setType(String type) {
        this.type = type;
    }


}

