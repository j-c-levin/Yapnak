package com.uq.yapnak;

/**
 * Created by Nand on 04/03/15.
 */
public class Item {
    private String title;
    private String foodStyle;
    private int logo;
    private String loyaltyPoints;
    private String distance;
    private double x;
    private double y;

    //TODO add loyalty points,distance and logo back in
    Item(String title, String foodStyle, double x, double y) {
        this.title = title;
        this.foodStyle = foodStyle;
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getTitle() {
        return title;
    }

    public String getFoodStyle() {
        return foodStyle;
    }

    public int getLogo() {
        return logo;
    }

    public String getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public String getDistance() {
        return distance;
    }
}
