package com.native_tutorial;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uq.yapnak.R;

/**
 * Created by vahizan on 02/09/2015.
 */
public class TutorialFragOne extends Fragment {
    private View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.tutorial_fragment_1,container,false);

        return v;
    }
}
