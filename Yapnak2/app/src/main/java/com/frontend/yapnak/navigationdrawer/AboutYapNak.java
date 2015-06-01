package com.frontend.yapnak.navigationdrawer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uq.yapnak.R;


/**
 * Created by vahizan on 26/05/2015.
 */
public class AboutYapNak extends Fragment {


    public AboutYapNak(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about_yapnak,container,false);
        return rootView;
    }
}
