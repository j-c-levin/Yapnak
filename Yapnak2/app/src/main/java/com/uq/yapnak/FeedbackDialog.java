package com.uq.yapnak;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
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

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.yapnak.gcmbackend.sQLEntityApi.SQLEntityApi;

import java.io.IOException;

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
    private int feedbackNumber;
    private String ID;

    public FeedbackDialog(Context context,Activity activity,String id) {
        super(context);
        this.activity=activity;
        this.context=context;
        d = this;
        ID = id;

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
        //title.setBackgroundColor(Color.parseColor("#FFAB91"));
        title.setTextColor(Color.parseColor("#BF360C"));
        title.setGravity(Gravity.LEFT);

        setCustomTitle(title);

        feedbackNum();





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

                Toast.makeText(getContext(), " Feedback Wasn't Sent  ", Toast.LENGTH_SHORT).show();

                d.cancel();
            }
        });

    }

    private void submitClick(){

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = comments.getText().toString();
                new SubmitFeedback().execute(ID,text);
                Toast.makeText(getContext(), " Thank You For Your Feedback  " , Toast.LENGTH_SHORT).show();

                d.dismiss();
            }
        });
    }

    private void feedbackNum(){

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioHappy:
                        feedbackNumber=1;
                        break;
                    case R.id.radioUnhappy:
                        feedbackNumber=2;
                        break;
                    case R.id.radioStoreCode:
                        feedbackNumber=3;
                        break;
                }
            }
        });


    }

    public void setID(String ID) {
        this.ID = ID;
    }

    private class SubmitFeedback extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... params) {
            Feedback f = new Feedback();
            f.setID(params[0]);
            f.setComment(params[1]);
            f.setFeedbackNumber(feedbackNumber);

            try {
                SQLEntityApi.Builder apiB = new SQLEntityApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
                apiB.setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
                apiB.setApplicationName("Yapnak");
                SQLEntityApi api = apiB.build();
                api.feedback(f.getComment(),f.getFeedbackNumber(), f.getID()).execute();
            }catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }


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
