package com.frontend.yapnak;

import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.uq.yapnak.R;

import java.util.List;

/**
 * Created by vahizan on 17/04/2015.
 */

public class ItemPrev {

    
    private int logo;
    private String points;
    private String location;
    private Double latitude;
    private Double longitude;
    private String mainText;
    private String subText;
    private final String loyaltyPointsTitle= "Loyalty Points";
    private int locationIcon;
    private String restaurantName;
    private String fetchImageURL;




    public ItemPrev(){
       locationIcon = R.drawable.locationicon;

    }


    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getDistance() {
        return location;
    }

    public void setDistance(String location) {
        this.location = location;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public int getLocationIcon(){
        return locationIcon;
    }

    public String getLoyaltyPointsTitle(){
        return loyaltyPointsTitle;
    }


    public String getRestaurantName(){
        return this.restaurantName;
    }

    public void setRestaurantName(String restaurantName){
        this.restaurantName=restaurantName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    public String getFetchImageURL() {
        return fetchImageURL;
    }

    public void setFetchImageURL(String fetchImageURL) {
        this.fetchImageURL = fetchImageURL;
    }
}
