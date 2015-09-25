package com.yapnak.gcmbackend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by Joshua on 08/09/2015.
 */
@Entity
public class ClientOfferEntity {
    @Id
    long offerId;
    String offerText;
    int offerStart;
    int offerEnd;
    String status;
    String message;

    public int getOfferStart() {
        return offerStart;
    }

    public void setOfferStart(int offerStart) {
        this.offerStart = offerStart;
    }

    public int getOfferEnd() {
        return offerEnd;
    }

    public void setOfferEnd(int offerEnd) {
        this.offerEnd = offerEnd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getOfferId() {
        return offerId;
    }

    public void setOfferId(long offerId) {
        this.offerId = offerId;
    }

    public String getOfferText() {
        return offerText;
    }

    public void setOfferText(String offerText) {
        this.offerText = offerText;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
