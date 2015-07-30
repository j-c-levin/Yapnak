package com.frontend.yapnak.client;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.uq.yapnak.ErrorDialog;
import com.uq.yapnak.Login;
import com.uq.yapnak.R;

/**
 * Created by vahizan on 24/06/2015.
 */
public class ClientLogin extends Activity {


    private EditText clientEmail;
    private EditText clientPass;
    private int errorID = -1;
    private float originalUserY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.client_login_activity);
        arrow = (ImageView) findViewById(R.id.arrowDownClient);
        clientB = (Button) findViewById(R.id.clientButton);
        originalUserY = clientB.getY();

        buttonAnimate();

    }


   public void clientLogin(View v){

       clientEmail =(EditText) findViewById(R.id.clientEmailEdit);
       clientPass = (EditText) findViewById(R.id.clientPassEdit);


       //TODO: Compare Email and Pass against database and return boolean;

       //Right now I'm going to set default values to help client enter onto their main screen

        if(clientEmail.getText().toString().equalsIgnoreCase("") && clientPass.getText().toString().equalsIgnoreCase("")) {
            //Start Main Client Activity
           Intent mainClientPage = new Intent(this,MainClientActivity.class);

            mainClientPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mainClientPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mainClientPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainClientPage);

        }else{
            ErrorDialog dialog = new ErrorDialog();
            dialog.show(getFragmentManager(),"LoginError");
        }

   }

    private ImageView arrow;
    private Button clientB;


    private AnimatorSet arrowAppear(){
        ValueAnimator animator = ValueAnimator.ofFloat(-1000.0f,(originalUserY+110));

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {


                float y = (Float) animation.getAnimatedValue();


                clientB.setY(y);

            }
        });

        ObjectAnimator alpha = ObjectAnimator.ofFloat(clientB,"alpha",0.0f,1.0f);

        AnimatorSet s = new AnimatorSet();
        s.playTogether(animator, alpha);

        return s;

    }
    private AnimatorSet arrowGone(){
        ValueAnimator animator = ValueAnimator.ofFloat(originalUserY,-1000.0f);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {


                float y = (float) animation.getAnimatedValue();


                clientB.setY(y);

            }
        });

        ObjectAnimator alpha = ObjectAnimator.ofFloat(clientB,"alpha",1.0f,0.0f);

        AnimatorSet s = new AnimatorSet();
        s.playTogether(animator, alpha);

        return s;
    }

    private void buttonAnimate(){

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(clientB.getVisibility()!=View.VISIBLE){
                    arrowAppear().setDuration(400).start();
                    clientB.setVisibility(View.VISIBLE);

                    clientB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent userActivity = new Intent(getApplicationContext(), Login.class);
                            userActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            userActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            userActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(userActivity);
                        }
                    });

                }else{
                    arrowGone().setDuration(400).start();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            clientB.setVisibility(View.INVISIBLE);
                        }
                    }, 300);
                }

            }
        });


    }


    private void arrowTouch(){



         clientB.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent clientActivity = new Intent(getApplicationContext(), ClientLogin.class);
                 startActivity(clientActivity);
             }
         });

        final float originalY = clientB.getY();
        boolean vis =  clientB.getVisibility()!=View.VISIBLE;

        Toast.makeText(this,"Visibility : " + vis,Toast.LENGTH_SHORT).show();

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animator.AnimatorListener animator = new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //super.onAnimationEnd(animation);

                        if (clientB.getVisibility() != View.VISIBLE) {
                            ObjectAnimator moveY = ObjectAnimator.ofFloat(clientB, "y", -1000, clientB.getY());
                            ObjectAnimator alpha = ObjectAnimator.ofFloat(clientB, "alpha", 0.0f, 0.5f, 1.0f);
                            AnimatorSet s = new AnimatorSet();
                            s.playTogether(moveY, alpha);
                            //s.setInterpolator(new AccelerateDecelerateInterpolator());
                            s.start();
                            clientB.setVisibility(View.VISIBLE);

                        } else {
                            ObjectAnimator moveY = ObjectAnimator.ofFloat(clientB, "y", originalY, -1000);
                            ObjectAnimator alpha = ObjectAnimator.ofFloat(clientB, "alpha", 1.0f, 0.5f, 0.0f);
                            AnimatorSet s = new AnimatorSet();
                            s.playTogether(moveY, alpha);
                            s.start();



                        }
                    }
                };

                clientB.animate().setDuration(400).setListener(animator).start();

                if(clientB.getAlpha()!=1.0f){
                    clientB.setVisibility(View.INVISIBLE);
                }


            }
        });

    }


}
