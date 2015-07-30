package com.frontend.yapnak.subview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.widget.EditText;

/**
 * Created by vahizan on 30/07/2015.
 */
public class RedEditText extends EditText{

    private Color c;


    public RedEditText(Context context) {
        super(context);


        c= new Color();

        this.getBackground().setColorFilter(c.parseColor("#E53935"), PorterDuff.Mode.SRC_IN);

    }
}
