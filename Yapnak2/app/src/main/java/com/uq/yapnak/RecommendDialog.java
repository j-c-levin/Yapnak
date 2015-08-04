package com.uq.yapnak;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by vahizan on 12/07/2015.
 */
public class RecommendDialog extends AlertDialog  {


    private Context context;
    private Activity activity;
    private int LAYOUT_ID;

    private Button contactListButton,pos,neg;
    private EditText enterId;



    public RecommendDialog(Context context, Activity activity) {
        super(context);
        this.context=context;
        this.activity = activity;

        LayoutInflater inflater = activity.getLayoutInflater();

        View v = inflater.inflate(R.layout.recommend_dialog,null);

        enterId = (EditText) v.findViewById(R.id.editTextID);
        contactListButton = (Button) v.findViewById(R.id.ChooseYapnakContact);
        pos = (Button) v.findViewById(R.id.ok);
        neg = (Button) v.findViewById(R.id.cancel);

        setContactListButton(contactListButton);
        setEditID(enterId);

        setLayoutId(R.id.recommendLayout);

        setView(v);


        //setTitle("👥 Recommend");


        TextView title = new TextView(this.getContext());
        title.setText("👥 Recommend");
        title.setGravity(Gravity.LEFT);
        title.setPadding(20,40,0,40);
        title.setTextSize(25);
        //title.setBackgroundColor(Color.parseColor("#FFAB91"));
        title.setTextColor(Color.parseColor("#BF360C"));
        setCustomTitle(title);

        setPositive();
        setNegative();



    }

    private void setPositive(){

        final AlertDialog d = this;

        pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d.dismiss();

            }
        });

    }

    private void setNegative(){

        final AlertDialog d = this;

        neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d.cancel();

            }
        });

    }

    public int getLayoutId() {
        return LAYOUT_ID;
    }

    public void setLayoutId(int layoutId) {
        LAYOUT_ID = layoutId;
    }

    public EditText getEditID(){
        return this.enterId;
    }
    private void setEditID(EditText text){
        this.enterId = text;
    }

    public Button getContactListButton(){
        return this.contactListButton;
    }
    private void setContactListButton (Button button){
        this.contactListButton = button;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Resources res = getContext().getResources();
        final int id = res.getIdentifier("titleDivider", "id", "android");
        final View divider = findViewById(id);

        Color c = new Color();
        if(divider!=null) {
            divider.setBackgroundColor(c.parseColor("#BF360C"));
        }
    }
}
