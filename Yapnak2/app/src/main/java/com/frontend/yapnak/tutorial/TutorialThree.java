package com.frontend.yapnak.tutorial;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uq.yapnak.MoreInfo;
import com.uq.yapnak.R;

/**
 * Created by vahizan on 26/07/2015.
 */
public class TutorialThree extends Fragment {
    private View v;
    private RelativeLayout rel;
    private final int RESULT = 1;
    private TextView longText;
    private ImageView line,confirm;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      //return super.onCreateView(inflater, container, savedInstanceState);

        container = (ViewGroup) inflater.inflate(R.layout.tutorialthree,container,false);
        v= container;
        longText = (TextView) v.findViewById(R.id.longHoldText);
        line = (ImageView) v.findViewById(R.id.lineImg2);
        confirm = (ImageView) v.findViewById(R.id.confirm);
        clicked();

        return container;
    }


    public void clicked(){
        rel = (RelativeLayout) v.findViewById(R.id.extendHeight);


        rel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                Intent intent = new Intent(getActivity().getApplicationContext(),MoreInfo.class);
                intent.putExtra("logo", "Logo");
                intent.putExtra("location", "Distance in Time");
                intent.putExtra("rating","Rating");
                startActivityForResult(intent, 1);
                return true;
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT){

            Animator.AnimatorListener listener = new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    //super.onAnimationEnd(animation);

                    ObjectAnimator alphaText = ObjectAnimator.ofFloat(longText,"alpha",1.0f,0.0f);
                    ObjectAnimator alphaArrow = ObjectAnimator.ofFloat(line,"alpha",1.0f,0.0f);

                    ObjectAnimator transformImageY = ObjectAnimator.ofFloat(confirm,"y",-1000,confirm.getY());
                    ObjectAnimator alphaImage = ObjectAnimator.ofFloat(confirm,"alpha",0.0f,1.0f);

                    AnimatorSet s = new AnimatorSet();

                    s.playTogether(transformImageY,alphaImage,alphaText,alphaArrow);
                    s.setDuration(400);
                    s.start();

                }

                @Override
                public void onAnimationStart(Animator animation) {
                    //super.onAnimationStart(animation);


                }
            };

            confirm.animate().setListener(listener).start();
            //confirm.setVisibility(View.VISIBLE);





        }
    }
}
