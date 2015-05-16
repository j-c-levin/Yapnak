package com.yapnak.gcmbackend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by Joshua on 16/05/2015.
 */
@Entity
public class ClientEntity {

    @Id
    String id;
    String clientName;
    String clientLocation;
    float clientX;
    float clientY;
    String foodStyle;
    String offer;
    String imageLink;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientLocation() {
        return clientLocation;
    }

    public void setClientLocation(String clientLocation) {
        this.clientLocation = clientLocation;
    }

    public float getClientX() {
        return clientX;
    }

    public void setClientX(float clientX) {
        this.clientX = clientX;
    }

    public float getClientY() {
        return clientY;
    }

    public void setClientY(float clientY) {
        this.clientY = clientY;
    }

    public String getFoodStyle() {
        return foodStyle;
    }

    public void setFoodStyle(String foodStyle) {
        this.foodStyle = foodStyle;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

}
