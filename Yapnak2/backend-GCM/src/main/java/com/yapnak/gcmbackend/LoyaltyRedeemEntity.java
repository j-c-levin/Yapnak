package com.yapnak.gcmbackend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by Joshua on 15/09/2015.
 */
@Entity
public class LoyaltyRedeemEntity {
    @Id
    String status;
    String message;
    long loyaltyPoints;
    long loyaltyRedeemedLevel;

    public long getLoyaltyRedeemedLevel() {
        return loyaltyRedeemedLevel;
    }

    public void setLoyaltyRedeemedLevel(long loyaltyRedeemedLevel) {
        this.loyaltyRedeemedLevel = loyaltyRedeemedLevel;
    }

    public long getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(long loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

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
