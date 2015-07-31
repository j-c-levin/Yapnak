package com.uq.yapnak;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import javax.xml.transform.Templates;

/**
 * Created by vahizan on 11/07/2015.
 */
public class FeedbackDialog extends AlertDialog {

    private Context context;
    private Activity activity;
    private RadioButton option;
    private EditText comments;
    private RadioGroup group;
    private Button submit,cancel;
    private AlertDialog d;

    public FeedbackDialog(Context context,Activity activity) {
        super(context);
        this.activity=activity;
        this.context=context;
        d = this;

        LayoutInflater inflater = this.activity.getLayoutInflater();

        View v = inflater.inflate(R.layout.feedback_activity,null);

        group=(RadioGroup) v.findViewById(R.id.feedbackRadioGroup);
        submit = (Button) v.findViewById(R.id.submitButton);
        cancel = (Button) v.findViewById(R.id.cancelButton);
        comments = (EditText) v.findViewById(R.id.commentField);
        setComments(comments);
        setGroup(group);
        setView(v);
        submitClick();
        cancelClick();

        final TextView title = new TextView(getContext());
        title.setText("Feedback");
        title.setTextSize(25);
        title.setPadding(20, 40, 0, 40);
        title.setBackgroundColor(Color.parseColor("#FFAB91"));
        title.setTextColor(Color.parseColor("#BF360C"));
        title.setGravity(Gravity.LEFT);

        setCustomTitle(title);





    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Resources res = getContext().getResources();

        final int id = res.getIdentifier("titleDivider", "id", "android");

        final View v = findViewById(id);

        if(v!=null) {
            v.setBackgroundColor(Color.parseColor("#BF360C"));
        }
    }

    private void cancelClick(){


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d.cancel();
            }
        });

    }

    private void submitClick(){

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText feedbackComment = getComments();

                String text = feedbackComment.getText().toString();
                //TODO:text must be stored in feedback table in the database

                Toast.makeText(getContext(), "Thank You For Your Feedback  " + text, Toast.LENGTH_SHORT).show();

                //load();
                //load(sql);

                d.dismiss();
            }
        });

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
