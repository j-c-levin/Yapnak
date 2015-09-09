package com.frontend.yapnak;

import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.model.LatLng;
import com.uq.yapnak.R;

/**
 * Created by vahizan on 17/04/2015.
 */

public class ItemPrev {

    private SharedPreferences preferences;
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
    private int hotDeal;
    private ImageView tButton1;
    private ImageView tButton2;
    private ImageView tButton3;
    private ImageView tButton4;
    private ImageView tText1;
    private ImageView tText2;
    private ImageView tText3;
    private ImageView tText4;
    private boolean isTutorial;
    private LinearLayout buttonContainer;
    private LinearLayout textContainer;
    private RelativeLayout extendHeight;
    private RelativeLayout extendIcon;
    private RelativeLayout extendText;
    private ImageView tutorial1;



    public ItemPrev(){
       locationIcon = R.drawable.locationicon_red;

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

    public String getDistanceTime() {
        return location;
    }

    public void setDistanceTime(String location) {
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

    public int getHotDeal() {
        return hotDeal;
    }

    public void setHotDeal(int hotDeal) {
        this.hotDeal = hotDeal;
    }



    public boolean isTutorial() {
        return isTutorial;
    }

    public void setIsTutorial(boolean isTutorial) {
        this.isTutorial = isTutorial;
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }


}
