package com.uq.yapnak;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by vahizan on 04/09/2015.
 */
public class SettingsDialog extends AlertDialog {
    private View v;
    private Switch keepMe,rememberMe;
    private ContactDetails details;
    private Context c;
    private SharedPreferences remember;
    private SharedPreferences keep;
    private Button close;
    protected SettingsDialog(Context context, ContactDetails details,SharedPreferences remember,SharedPreferences keep) {
        super(context);

        v = getLayoutInflater().inflate(R.layout.settings,null);
        keepMe = (Switch) v.findViewById(R.id.keepMe);
        rememberMe = (Switch) v.findViewById(R.id.rememberMe);
        this.details = details;
        this.remember = remember;
        this.keep = keep;
        this.setView(v);
        this.c = context;

        close = (Button) v.findViewById(R.id.closeButton);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });

        TextView title = new TextView(this.getContext());
        title.setText("Settings");
        title.setGravity(Gravity.LEFT);
        title.setPadding(20, 40, 0, 40);
        title.setTextSize(25);
        title.setTextColor(Color.parseColor("#BF360C"));
        setCustomTitle(title);

        settings();
        keepMeClick();
        rememberMeClick();
    }
    private void keepMeClick(){
        keepMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(keep.getString("password","-1").equalsIgnoreCase("-1")) {
                        SharedPreferences.Editor editor = keep.edit();
                        editor.putString("password", details.getPassword());
                        editor.putString("email", details.getEmailAd());
                        editor.putString("userID", details.getUserID());
                        editor.putBoolean("on",isChecked);
                        editor.apply();
                    }
                }else{
                    keep.edit().clear();
                    keep.edit().putBoolean("on", isChecked).apply();
                }
            }
        });
    }
    private void rememberMeClick(){
        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(remember.getString("phone","-1").equalsIgnoreCase("-1")) {
                        SharedPreferences.Editor editor = remember.edit();
                        editor.putString("userID", details.getUserID());
                        editor.putString("password", details.getPassword());
                        editor.putString("email", details.getEmailAd());
                        editor.putString("phone", details.getPhoneNum());
                        editor.putBoolean("on", isChecked);
                        editor.apply();
                    }
                }else{
                    remember.edit().clear();
                    remember.edit().putBoolean("on",isChecked).apply();
                }
            }
        });
    }
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getContext().getResources();
        final int id = res.getIdentifier("titleDivier", "id", "android");
        final View v = findViewById(id);

        if(v!=null) {
            v.setBackgroundColor(Color.parseColor("#BF360C"));
        }
    }

    private void settings(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {

                if (keep != null) {
                    keepMe.setChecked(keep.getBoolean("on", false));
                }
                if (remember != null) {
                    rememberMe.setChecked(remember.getBoolean("on", false));
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
