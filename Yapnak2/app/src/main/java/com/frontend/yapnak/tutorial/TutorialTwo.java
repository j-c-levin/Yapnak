package com.frontend.yapnak.tutorial;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uq.yapnak.R;

/**
 * Created by vahizan on 26/07/2015.
 */
public class TutorialTwo extends Fragment {

    private View v;
    private Button recommend;
    private Button takeMeThere;
    private Button rate;
    private LayoutInflater inflater;
    private boolean click1,click2,click3;
    private TextView longText;
    private ImageView line;
    private RelativeLayout layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        container = (ViewGroup) inflater.inflate(R.layout.tutorialtwo, container, false);
        v=container;
        this.inflater = inflater;
        layout = (RelativeLayout) v.findViewById(R.id.toggleTutorial);
        line = (ImageView) v.findViewById(R.id.swipeleft);
        longText= (TextView) v.findViewById(R.id.swipe);
        clicked();
        allClicked();
        return container;
    }

   private final int PICK_CONTACT = 1;

    public void clicked(){
        recommend = (Button)v.findViewById(R.id.recommendMeal);
        takeMeThere = (Button)v.findViewById(R.id.takeMeThere);
        rate = (Button)v.findViewById(R.id.feedbackButton);


        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                click1=true;
                AlertDialog.Builder userItems = new AlertDialog.Builder(v.getContext());
                final LinearLayout linearLayout = new LinearLayout(v.getContext());

                userItems.setTitle("👥 Recommend");
                //userItems.setMessage("Enter your friends Yapnak iD");
                userItems.setPositiveButton("OK", null);
                userItems.setNegativeButton("CANCEL", null);




                View dialog = inflater.inflate(R.layout.recommend_dialog,null);
                userItems.setView(dialog);


                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setPadding(40, 0, 40, 0);

                TextView message = new TextView(v.getContext());
                final EditText input = new EditText(v.getContext());

                TextView OR = new TextView(v.getContext());
                OR.setText("OR");

                final Button contactButton = new Button(v.getContext());


                contactButton.setText("Phonebook");

                contactButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {

                                //Self implemented contacts list - Go into ContactList if you want to DO SOMETHING Once a contact is selected.
                                //Add code in onItemClick Method in contactList, if you want a list item to do something
                                //Intent intent = new Intent(getApplicationContext(), ContactList.class);
                                //startActivity(intent);


                                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                                startActivityForResult(intent, PICK_CONTACT );

                        /*
                        Showing the google stock contacts picker
                        When contact chosen, DO SOMETHING in "onActivityResult" Method

                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent, PICK_CONTACT );
                        */

                            }
                        });

                    }
                });



                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                buttonParams.gravity = Gravity.CENTER;
                buttonParams.setMargins(0,20,0,20);

                input.setHint("EG: NS-6438");
                input.setSingleLine(false);
                input.setMaxLines(1);
                input.setTextColor(Color.BLACK);
                linearLayout.addView(message);
                linearLayout.addView(input);
                linearLayout.addView(OR,buttonParams);
                linearLayout.addView(contactButton,buttonParams);
                userItems.setView(linearLayout);




                userItems.show();

            }
        });


        takeMeThere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click2 =true;
                Toast.makeText(getActivity().getApplicationContext(),"This will provide you with directions to the restaurant that is providing the deal",Toast.LENGTH_LONG).show();
             }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click3 = true;
                Toast.makeText(getActivity().getApplicationContext(), "This will allow you to rate this deal", Toast.LENGTH_LONG).show();
            }
        });




    }

    private void allClicked(){
        if(click1 || click2 || click3 ){

            Animator.AnimatorListener listener = new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                    PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", layout.getHeight(), 0);
                    ObjectAnimator alpha = ObjectAnimator.ofFloat(layout, "alpha", 1.0f, 0.0f);
                    ObjectAnimator scale = ObjectAnimator.ofPropertyValuesHolder(layout, scaleY);
                    PropertyValuesHolder revScaleY = PropertyValuesHolder.ofFloat("scaleY",line.getHeight(),0);
                    ObjectAnimator scaleImg = ObjectAnimator.ofPropertyValuesHolder(line,revScaleY);

                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(scale, alpha, scaleImg);
                    set.setDuration(250);


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            layout.setVisibility(View.GONE);
                            longText.setVisibility(View.INVISIBLE);
                            line.setVisibility(View.INVISIBLE);
                        }

                    }, 200);

                  set.start();

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    PropertyValuesHolder scaleY = PropertyValuesHolder.ofInt("scaleY",0,line.getHeight());
                    ObjectAnimator alpha = ObjectAnimator.ofFloat(line, "alpha", 0.0f, 1.0f);
                    ObjectAnimator alphaT = ObjectAnimator.ofFloat(longText, "alpha", 0.0f, 1.0f);
                    ObjectAnimator scale = ObjectAnimator.ofPropertyValuesHolder(line, scaleY);

                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(scale, alpha,alphaT);
                    set.setInterpolator(new AccelerateDecelerateInterpolator());
                    set.setDuration(250);
                    set.start();

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            };

            layout.animate().setListener(listener).start();

        }
    }
}
