package com.frontend.yapnak.tutorial;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uq.yapnak.R;

/**
 * Created by vahizan on 26/07/2015.
 */
public class TutorialOne extends Fragment {


    private View v;
    private RelativeLayout extendHeight;
    private RelativeLayout extendIcon;
    private RelativeLayout extendText;
    private ImageView confirm;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        container = (ViewGroup) inflater.inflate(R.layout.tutorialone, container, false);
        v = container;
        confirm = (ImageView) v.findViewById(R.id.confirm);
        clickExpand();
        return container;
    }


    public void clickExpand(){


        extendHeight= (RelativeLayout) v.findViewById(R.id.extendHeight);
        extendIcon= (RelativeLayout) v.findViewById(R.id.extendIconLayout);
        extendText=(RelativeLayout) v.findViewById(R.id.extendTextLayout);

        extendHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(extendText.getVisibility()==View.GONE && extendIcon.getVisibility()==View.GONE){

                    extendInfo();
                    replaceText();

                }else{

                    collapse();

                }
            }
        });




    }

    private TextView text ;
    private ImageView arrow ;
    private void replaceText(){

         text = (TextView)v.findViewById(R.id.cardexpandText);
          arrow = (ImageView)v.findViewById(R.id.lineImg);

        Animator.AnimatorListener animator = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {




            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ObjectAnimator alpha = ObjectAnimator.ofFloat(text,"alpha",1.0f,0.0f);
                ObjectAnimator arrowAlpha = ObjectAnimator.ofFloat(arrow, "alpha", 1.0f, 0.0f);

                PropertyValuesHolder transformX = PropertyValuesHolder.ofFloat("scaleX",arrow.getWidth(),0.0f);
                PropertyValuesHolder transformY = PropertyValuesHolder.ofFloat("scaleY",arrow.getHeight(),0.0f);

                ObjectAnimator transform = ObjectAnimator.ofPropertyValuesHolder(arrow, transformX, transformY);

                ObjectAnimator transformImageY = ObjectAnimator.ofFloat(confirm,"y",-1000,confirm.getY());
                ObjectAnimator alphaImage = ObjectAnimator.ofFloat(confirm,"alpha",0.0f,1.0f);



                AnimatorSet set = new AnimatorSet();

                set.playTogether(alpha,arrowAlpha,transform,transformImageY,alphaImage);
                //set.playTogether(alpha, arrowAlpha, transform);

                set.setDuration(500);
                set.start();
            }


        };

        text.animate().setListener(animator).start();

        //confirm.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                text.setVisibility(View.GONE);
                arrow.setVisibility(View.GONE);
            }
        }, 552);




    }

    private ValueAnimator slideAnim(int start,int end){
        ValueAnimator animator = ValueAnimator.ofInt(start, end);


        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                ViewGroup.LayoutParams layout = extendHeight.getLayoutParams();
                layout.height = value;
                extendHeight.setLayoutParams(layout);
            }
        });

        return animator;


     }

    private int initHeight;
    private void extendInfo(){
        extendIcon.setVisibility(View.VISIBLE);
        extendText.setVisibility(View.VISIBLE);

        final int width= View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int height= View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        extendHeight.measure(width,height);

        initHeight = extendHeight.getHeight();

        ValueAnimator animator = slideAnim(extendHeight.getHeight(),(extendHeight.getMeasuredHeight()-5));
        animator.start();

    }

    private void collapse(){

        ValueAnimator animator = slideAnim(extendHeight.getHeight(),initHeight);

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                extendIcon.setVisibility(View.GONE);
                extendText.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.start();

    }




}
