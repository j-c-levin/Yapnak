package com.native_tutorial;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uq.yapnak.MainActivity;
import com.uq.yapnak.R;

/**
 * Created by vahizan on 07/09/2015.
 */
public class TutorialFragFour extends Fragment {
    private MainActivity a;
    private View v;
    private RelativeLayout layout;
    private TextView text;
    private Fragment thisFrag = this;
    private Button map,get,recommend,feedback;
    private int count,num1,num2,num3,num4;

    public TutorialFragFour(MainActivity a) {
        this.a =a;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tutorial_fragment_3,container,false);

        layout = (RelativeLayout) v.findViewById(R.id.extendHeight);


        map = (Button) v.findViewById(R.id.takeMeThere);
        get = (Button) v.findViewById(R.id.getDealButton);
        recommend = (Button) v.findViewById(R.id.recommendMeal);
        feedback = (Button) v.findViewById(R.id.feedbackButton);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(num1!=1){
                    count++;
                    num1=1;
                    check();
                }

            }
        });

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num2!=1){
                    count++;
                    num2=1;
                    check();
                }
            }
        });


        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(num3!=1){
                    count++;
                    num3=1;
                    check();
                }
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num4!=1){
                    count++;
                    num4=1;
                    check();
                }
            }
        });





        return v;
    }

    public void check(){
        if (count>=4) {
            FragmentTransaction t = a.getFragmentManager().beginTransaction();
            t.remove(thisFrag).commit();
        }
    }
}
