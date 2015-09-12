package com.uq.yapnak;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Nand on 14/03/15.
 */
public class Feedback  {
   private String comment;
    private String ID,clientID;
   private int feedbackNumber;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String ID) {
        this.clientID = ID;
    }

    public int getFeedbackNumber() {
        return feedbackNumber;
    }

    public void setFeedbackNumber(int feedbackNumber) {
        this.feedbackNumber = feedbackNumber;
    }
}
