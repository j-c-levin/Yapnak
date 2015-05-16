package com.yapnak.gcmbackend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Date;

/**
 * Created by Joshua on 16/05/2015.
 */
@Entity
public class OffersEntity {
    @Id
    int offerID;
    String clientID;
    String offerText;
    Date offerStart;
    Date offerEnd;

    public int getOfferID() {
        return offerID;
    }

    public void setOfferID(int offerID) {
        this.offerID = offerID;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getOfferText() {
        return offerText;
    }

    public void setOfferText(String offerText) {
        this.offerText = offerText;
    }

    public Date getOfferStart() {
        return offerStart;
    }

    public void setOfferStart(Date offerStart) {
        this.offerStart = offerStart;
    }

    public Date getOfferEnd() {
        return offerEnd;
    }

    public void setOfferEnd(Date offerEnd) {
        this.offerEnd = offerEnd;
    }
}
