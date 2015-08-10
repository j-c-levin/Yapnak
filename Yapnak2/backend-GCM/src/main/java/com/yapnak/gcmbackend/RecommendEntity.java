package com.yapnak.gcmbackend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by Joshua on 11/08/2015.
 */
@Entity
public class RecommendEntity {

    @Id
    long success;

    public long getSuccess() {
        return success;
    }

    public void setSuccess(long success) {
        this.success = success;
    }
}