package com.yapnak.gcmbackend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by Joshua on 11/08/2015.
 */
@Entity
public class RecommendEntity {

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

    @Id
    String status;
    String message;

    public long getResult() {
        return result;
    }

    public void setResult(long result) {
        this.result = result;
    }

    long result;
}