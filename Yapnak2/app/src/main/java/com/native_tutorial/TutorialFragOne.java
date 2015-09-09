package com.native_tutorial;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.uq.yapnak.MainActivity;
import com.uq.yapnak.R;

/**
 * Created by vahizan on 02/09/2015.
 */
public class TutorialFragOne extends Fragment {
    private View v;
    private int initialHeight;
    private MainActivity activity;
    private RelativeLayout layout;
    private RelativeLayout iconLayout;
    private RelativeLayout textLayout;

    public TutorialFragOne(MainActivity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.tutorial_fragment_1,container,false);

        layout = (RelativeLayout) v.findViewById(R.id.extendHeight);
        iconLayout = (RelativeLayout)v.findViewById(R.id.extendIconLayout);
        textLayout = (RelativeLayout) v.findViewById(R.id.extendTextLayout);


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        activity.extendInfo(v,false);
                        if(iconLayout.getVisibility() == View.VISIBLE && textLayout.getVisibility()==View.VISIBLE ) {
                            FragmentTransaction t = activity.getFragmentManager().beginTransaction();
                            t.replace(R.id.fragmentContainer, new TutorialFragTwo(activity)).commit();
                        }else{
                            Toast.makeText(activity.getApplicationContext(),"Sorry, please press again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });



        return v;
    }
}
