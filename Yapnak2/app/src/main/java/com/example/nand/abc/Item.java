package com.example.nand.abc;

/**
 * Created by Nand on 04/03/15.
 */
public class Item {
    private String title;
    private String subtle;
    private int logo;
    private String loyaltyPoints;
    private String distance;

    Item(String title, String subtle, String loyaltyPoints, int logo, String distance) {
        this.title = title;
        this.subtle = subtle;
        this.logo = logo;
        this.loyaltyPoints = loyaltyPoints;
        this.distance = distance;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtle() {
        return subtle;
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
