package com.frontend.yapnak.subview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by vahizan on 12/07/2015.
 */
public class MyEditText extends EditText{

    private Rect rect;
    private Paint paint;
    private Color color;


    public MyEditText(Context context, AttributeSet attrs) {
        super(context,attrs);

        rect = new Rect();
        paint = new Paint();
        color = new Color();

        this.getBackground().setColorFilter(color.parseColor("#FF5722"), PorterDuff.Mode.SRC_IN);




    }

    /*
    @Override
    protected void onDraw(Canvas canvas) {

        int height = getHeight();
        int lineHeight = getLineHeight();

        int  bottomEditText = getLineBounds(0,rect);


       // paint.setColor(color.parseColor("#B71C1C"));
        paint.setColor(color.parseColor("#FF5722"));

        canvas.drawLine(rect.left, bottomEditText,rect.right, bottomEditText,paint);

        super.onDraw(canvas);
    }
    */


}
