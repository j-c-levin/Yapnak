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
import android.widget.TextView;
import android.widget.Toast;

import com.uq.yapnak.MainActivity;
import com.uq.yapnak.R;

/**
 * Created by vahizan on 07/09/2015.
 */
public class TutorialFragThree extends Fragment {
    private MainActivity a ;
    private View v;
    private RelativeLayout layout;
    private TextView text;
    private RelativeLayout cardOptions;

    public TutorialFragThree(MainActivity activity){
        this.a = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tutorial_fragment_2,container,false);
        a.extend(false);
        layout = (RelativeLayout) v.findViewById(R.id.extendHeight);
        text = (TextView) v.findViewById(R.id.infoText);
        text.setText("Click to Expand, in Order to View More Options");
        cardOptions = (RelativeLayout) v.findViewById(R.id.extendIconLayout);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        a.extendInfo(v,false);

                        if (cardOptions.getVisibility() == View.VISIBLE) {
                            FragmentTransaction t = a.getFragmentManager().beginTransaction();
                            t.replace(R.id.fragmentContainer, new TutorialFragFour(a)).commit();
                        }else{
                            Toast.makeText(a.getApplicationContext(),"Please Click on Card Again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



        return v;
    }
}
