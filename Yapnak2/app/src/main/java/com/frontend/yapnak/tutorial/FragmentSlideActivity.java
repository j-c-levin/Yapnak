package com.frontend.yapnak.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.uq.yapnak.R;

/**
 * Created by vahizan on 26/07/2015.
 */
public class FragmentSlideActivity extends FragmentActivity {

    private Intent getI;
    private final int PAGES = 4;
    private ViewPager pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_viewpager);
        pager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter adapter = new SlideAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

    }





    private class SlideAdapter extends FragmentStatePagerAdapter {

        public SlideAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch(position){
                case 0:

                    return new TutorialOne();

                case 1:

                     return new TutorialTwo();

                case 2:

                    return new TutorialThree();

                case 3:

                    getI= getIntent();
                    String gName = getI.getStringExtra("accName");
                    String defaultName = getI.getStringExtra("initials");
                    String email = getI.getStringExtra("userID");
                    return new TutorialFour(gName,defaultName,email);

                default:

                    return null;

            }
        }

        @Override
        public int getCount() {
            return PAGES;
        }
    }
}
