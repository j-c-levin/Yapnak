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
//TODO add loyalty points,distance and logo back in
    Item(String title, String foodStyle) {
        this.title = title;
        this.foodStyle = foodStyle;
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
