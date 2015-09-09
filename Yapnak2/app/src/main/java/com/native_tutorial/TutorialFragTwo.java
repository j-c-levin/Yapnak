package com.native_tutorial;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.uq.yapnak.MainActivity;
import com.uq.yapnak.MoreInfo;
import com.uq.yapnak.R;

/**
 * Created by vahizan on 07/09/2015.
 */
public class TutorialFragTwo extends Fragment {
   private View v;
    private MainActivity activity;
    private RelativeLayout layout;


    public TutorialFragTwo(MainActivity activity) {
      this.activity=activity;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.tutorial_fragment_2,container,false);
        layout = (RelativeLayout) v.findViewById(R.id.extendHeight);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.extendInfo(v,false);
            }
        });

        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Intent intent = new Intent(activity.getApplicationContext(),MoreInfo.class);
                intent.putExtra("rating", 2.1);
                intent.putExtra("fragment",1);
                activity.startActivityForResult(intent,MainActivity.FRAGMENT_RESULT);
                return true;

            }
        });

        return v;
    }
}
