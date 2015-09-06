package com.uq.yapnak;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by vahizan on 31/08/2015.
 */
public class GenderDialog extends AlertDialog {
    private Activity activity;
    private Context context;
    private Button ok ;
    private View v;
    private String option;
    private RadioGroup group;
    private Button mainButton;

    public void setMainButton(Button button){
        mainButton = button;
    }
    public Button getMainButton(){
        return this.mainButton;
    }

    protected GenderDialog(Context context, Activity a) {
        super(context);
        this.activity = a;
        this.context = context;


        v = activity.getLayoutInflater().inflate(R.layout.gender,null);

        group = (RadioGroup) v.findViewById(R.id.radioGroup);

        this.setView(v);

            ok = (Button) v.findViewById(R.id.aboutOK);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int selected = group.getCheckedRadioButtonId();
                    RadioButton chosen = (RadioButton) v.findViewById(selected);
                    Log.d("chosen",chosen.getText().toString());
                    getMainButton().setText(chosen.getText().toString());
                    dismiss();
                }
            });

            TextView title = new TextView(this.getContext());
            title.setText("Gender");
            title.setGravity(Gravity.LEFT);
            title.setPadding(20, 40, 0, 40);
            title.setTextSize(25);
            title.setTextColor(Color.parseColor("#BF360C"));
            setCustomTitle(title);

       }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            Resources res = getContext().getResources();
            int id = res.getIdentifier("titleDivider", "id", "android");
            View view = findViewById(id);

            if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){

                view.setBackgroundColor(Color.parseColor("#BF360C"));
            }

        }


     private void setOption(String option){
         this.option = option;
     }

    public String getOption(){
        return this.option;
    }



}
