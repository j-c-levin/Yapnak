package com.yapnak.gcmbackend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Date;

/**
 * Created by Joshua on 16/05/2015.
 */
@Entity
public class FeedbackEntity {
    @Id
    String userID;
    String clientID;
    String offerID;
    String feedbackText;
    int posOrNeg;
    Date Timestamp;
}
