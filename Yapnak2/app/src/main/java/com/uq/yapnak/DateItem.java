package com.uq.yapnak;

/**
 * Created by vahizan on 04/08/2015.
 */
public class DateItem {

    private String value;
    private String backgroundColor;
    private int monthValue;

    public DateItem (){
        backgroundColor = "#FFF";
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getMonthValue() {
        return monthValue;
    }

    public void setMonthValue(int monthValue) {
        this.monthValue = monthValue;
    }
}
