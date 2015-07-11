package com.uq.yapnak;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by vahizan on 11/07/2015.
 */
public class ProfileDialog extends AlertDialog.Builder {


    private Activity activity;
    private Context context;

    public ProfileDialog(Context context,Activity activity) {
        super(context);

        this.activity=activity;
        this.context=context;

        LayoutInflater inflater = activity.getLayoutInflater();

        View v = inflater.inflate(R.layout.profile_layout,null);
        this.setView(v);



    }
}
