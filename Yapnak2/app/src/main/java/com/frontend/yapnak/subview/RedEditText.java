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
 * Created by vahizan on 30/07/2015.
 */
public class RedEditText extends EditText{

    private boolean inFocus;
    private Color c;
    private Rect rect;
    private Paint paint;
    private int widthSize,heightSize;



    public RedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        rect = new Rect();
        c = new Color();
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1.5f);
        paint.setColor(c.parseColor("#FF5252"));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(widthSize, heightSize);


    }




    @Override
    protected void onDraw(Canvas canvas) {

        /*
        //Draw underline

        canvas.drawLine(5,heightSize-10,widthSize,(heightSize-10),paint);

        //Draw Vertical side bar - Left Side

        canvas.drawLine(5,heightSize-10,5,heightSize-20,paint);

        //Draw vertical side bar - RIGHT SIDE

        canvas.drawLine(widthSize, heightSize - 10, widthSize,heightSize-20,paint);
        */



        if(inFocus){

            paint.setStrokeWidth(3.0f);
            paint.setColor(c.parseColor("#B71C1C"));
        }else{
            paint.setStrokeWidth(1.5f);
            paint.setColor(c.parseColor("#FF5252"));

        }


        int bottomLine = getLineBounds(0,rect);

        /*
        int lineHeight = getLineHeight();
        int height = getHeight();

        int numberOfLines = height/lineHeight;

        /*
            canvas.drawLine(rect.left,bottomLine-10,rect.right,bottomLine-10,paint);


            //draw vertical line left
            canvas.drawLine(rect.left,bottomLine-20,rect.left,bottomLine-10,paint);


            //draw vertical line right
            canvas.drawLine(rect.right,bottomLine-20,rect.right,bottomLine-10,paint);
        */




        //Draw underline

        canvas.drawLine(-5,heightSize-10,widthSize,(heightSize-10),paint);

        //Draw Vertical side bar - Left Side

        canvas.drawLine(-5,heightSize-10,-5,heightSize-20,paint);

        //Draw vertical side bar - RIGHT SIDE

        canvas.drawLine(widthSize+5, heightSize - 10, widthSize+5,heightSize-20,paint);

        super.onDraw(canvas);




    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);

        inFocus = focused;

    }
}
