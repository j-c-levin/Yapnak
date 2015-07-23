package com.uq.yapnak;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by vahizan on 12/07/2015.
 */
public class RecommendDialog extends AlertDialog.Builder  {


    private Context context;
    private Activity activity;
    private int LAYOUT_ID;

    private Button contactListButton;
    private EditText enterId;

    public RecommendDialog(Context context, Activity activity) {
        super(context);
        this.context=context;
        this.activity = activity;

        LayoutInflater inflater = activity.getLayoutInflater();

        View v = inflater.inflate(R.layout.recommend_dialog,null);

        enterId = (EditText) v.findViewById(R.id.editTextID);
        contactListButton = (Button) v.findViewById(R.id.ChooseYapnakContact);

        setContactListButton(contactListButton);
        setEditID(enterId);

        setLayoutId(R.id.recommendLayout);

        setView(v);


        setTitle("👥 Recommend");


    }

    public int getLayoutId() {
        return LAYOUT_ID;
    }

    public void setLayoutId(int layoutId) {
        LAYOUT_ID = layoutId;
    }

    public EditText getEditID(){
        return this.enterId;
    }
    private void setEditID(EditText text){
        this.enterId = text;
    }

    public Button getContactListButton(){
        return this.contactListButton;
    }
    private void setContactListButton (Button button){
        this.contactListButton = button;
    }



}
