package com.example.nand.abc;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by Nand on 14/03/15.
 */
public class Feedback extends DialogFragment implements View.OnClickListener {
    private Button cancel;
    private Button submit;
    private EditText additionalComments;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.feedback_activity, null);

        cancel = (Button) view.findViewById(R.id.cancelButton);
        cancel.setOnClickListener(this);
        submit = (Button) view.findViewById(R.id.submitButton);
        submit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(final View v) {

        if (v.getId() == R.id.cancelButton) {
            dismiss();

        } else {
            Toast.makeText(getActivity(), "Thank You For Your Feedback", Toast.LENGTH_SHORT).show();
            dismiss();

        }
    }

}