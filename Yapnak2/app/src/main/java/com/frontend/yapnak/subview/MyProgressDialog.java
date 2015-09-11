package com.frontend.yapnak.subview;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.uq.yapnak.R;

/**
 * Created by vahizan on 09/09/2015.
 */
public class MyProgressDialog extends ProgressDialog {

    private ImageView startImage;
    private AnimationDrawable drawable;
    private TextView message;

    public MyProgressDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog);

        message = (TextView) findViewById(R.id.progressMessage);
        startImage= (ImageView) findViewById(R.id.startImage);
        startImage.setBackgroundResource(R.drawable.progress_anim);

        drawable = (AnimationDrawable) startImage.getBackground();

    }

    @Override
    public void show() {
        super.show();
        drawable.start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        drawable.stop();
    }

   /*public void setMsg(String msg){
        this.message.setText(msg);
    }*/

}
