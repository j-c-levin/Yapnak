package com.frontend.yapnak.tutorial;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.uq.yapnak.MainActivity;
import com.uq.yapnak.R;

/**
 * Created by vahizan on 26/07/2015.
 */
public class TutorialFour extends Fragment {

    private View v;
    private Button button;
    private Intent getInfo;
    private String gName,dName;


    public TutorialFour(String googleName ,String defName){

        gName = (googleName==null)? "" : googleName;
        dName = (defName==null) ? "" :defName;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        container = (ViewGroup) inflater.inflate(R.layout.tutorialfour,container,false);
        v=container;
        getInfo = getActivity().getIntent();
        clickToActivity();
        return container;
    }

    public void clickToActivity(){

        button = (Button)v.findViewById(R.id.enterActivity);

        getInfo = getActivity().getIntent();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent i = new Intent(getActivity(), MainActivity.class);

                Toast.makeText(getActivity().getApplicationContext(),getInfo.getStringExtra("initials"),Toast.LENGTH_SHORT).show();
                i.putExtra("initials",dName);
                i.putExtra("accName",gName);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                v.getContext().startActivity(i);




            }
        });

    }






}
