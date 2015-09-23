package com.frontend.yapnak.subview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.pkmmte.view.CircularImageView;

/**
 * Created by vahizan on 22/09/2015.
 */
public class MyImageView extends CircularImageView {
    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final Drawable drawable = this.getDrawable();
        if(drawable!=null) {
            //final int width = drawable.getIntrinsicWidth();
            //final int height = heightMeasureSpec;
            this.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
