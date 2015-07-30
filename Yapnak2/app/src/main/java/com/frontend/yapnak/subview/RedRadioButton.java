package com.frontend.yapnak.subview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.widget.RadioButton;

/**
 * Created by vahizan on 30/07/2015.
 */
public class RedRadioButton extends RadioButton {

    private Color c;
    public RedRadioButton(Context context) {
        super(context);


        c = new Color();
       // this.getBackground().setColorFilter(c.parseColor("#E53935"), PorterDuff.Mode.SRC_IN);



    }
}
