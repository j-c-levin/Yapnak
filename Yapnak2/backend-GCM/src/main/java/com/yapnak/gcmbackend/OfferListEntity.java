package com.yapnak.gcmbackend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.List;

/**
 * Created by Joshua on 06/09/2015.
 */
@Entity
public class OfferListEntity {
    @Id
    String status;
    String message;

    public List<OfferEntity> getOfferList() {
        return offerList;
    }

    public void setOfferList(List<OfferEntity> offerList) {
        this.offerList = offerList;
    }

    List<OfferEntity> offerList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
