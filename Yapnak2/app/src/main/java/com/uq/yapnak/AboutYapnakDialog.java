package com.uq.yapnak;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.AndroidAppUri;

/**
 * Created by vahizan on 04/08/2015.
 */
public class AboutYapnakDialog extends AlertDialog {

    private Activity activity;
    private Context context;
    private Button ok ;
    private View v;

    public AboutYapnakDialog(Context context,Activity activity) {

        super(context);
        this.activity = activity;
        this.context = context;

         v = activity.getLayoutInflater().inflate(R.layout.about_yapnak,null);


        //this.setView(v);

        ok = (Button) v.findViewById(R.id.aboutOK);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        TextView title = new TextView(this.getContext());
        title.setText("About Yapnak");
        title.setGravity(Gravity.LEFT);
        title.setPadding(20, 40, 0, 40);
        title.setTextSize(25);
        title.setTextColor(Color.parseColor("#BF360C"));
        setCustomTitle(title);

        setMessage("Yapnak finds you lunch for a £5er! 💰💰💰");


        final LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(20, 20, 20, 20);
        Button b = new Button(getContext());
        b.setLayoutParams(layoutParams);
        b.setText("OK");
        b.setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        linearLayout.addView(b);
        this.setView(linearLayout);

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
}
