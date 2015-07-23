package com.uq.yapnak;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by vahizan on 11/07/2015.
 */
public class FeedbackDialog extends AlertDialog.Builder {

    private Context context;
    private Activity activity;
    private RadioButton option;
    private EditText comments;
    private RadioGroup group;

    public FeedbackDialog(Context context,Activity activity) {
        super(context);
        this.activity=activity;
        this.context=context;

        LayoutInflater inflater = this.activity.getLayoutInflater();

        View v = inflater.inflate(R.layout.feedback_activity,null);

        group=(RadioGroup) v.findViewById(R.id.feedbackRadioGroup);
        comments = (EditText) v.findViewById(R.id.commentField);
        setComments(comments);
        setGroup(group);



        setView(v);



    }


    public RadioButton getOption() {
        return option;
    }

    public void setOption(RadioButton option) {
        this.option = option;
    }

    public EditText getComments() {
        return comments;
    }

    public void setComments(EditText comments) {
        this.comments = comments;
    }

    public RadioGroup getGroup() {
        return group;
    }

    public void setGroup(RadioGroup group) {
        this.group = group;
    }
}
