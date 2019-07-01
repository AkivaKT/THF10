package com.byui.thf10;


public class Product extends JsonConvertible {
    private int product_id;
    private String type;
    private String series;
    private String Pattern;
    private String color1;
    private String color2;
    private int quanitity;

    public int getProduct_id() {
        return product_id;
    }

    public int getQuanitity() {
        return quanitity;
    }

    public String getColor1() {
        return color1;
    }

    public String getColor2() {
        return color2;
    }

    public String getPattern() {
        return Pattern;
    }

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

    public void setPattern(String pattern) {
        Pattern = pattern;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setQuanitity(int quanitity) {
        this.quanitity = quanitity;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public void setType(String type) {
        this.type = type;
    }


}

