package com.frontend.yapnak.rate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.uq.yapnak.R;

/**
 * Created by vahizan on 11/07/2015.
 */
public class RatingBuilder extends AlertDialog.Builder {

    private Activity activity;
    private Context context;

    public RatingBuilder(Context context,Activity activity) {
        super(context);

        this.context=context;
        this.activity=activity;

        LayoutInflater inflater = this.activity.getLayoutInflater();

        View v = inflater.inflate(R.layout.ratings_dialog,null);
        this.setView(v);

    }
}
