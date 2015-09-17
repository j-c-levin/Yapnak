package com.uq.yapnak;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by vahizan on 14/06/2015.
 */
public class ErrorDialog extends DialogFragment{

    private View errorDialog;
    private String infoText;
    private TextView info;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder error = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        errorDialog = inflater.inflate(R.layout.error_dialog,null);
        info = (TextView) errorDialog.findViewById(R.id.infoAboutLogin);
        info.setText(getInfoText());


        error.setView(errorDialog);

        error.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                ErrorDialog.this.getDialog().dismiss();

            }
        });

       return error.create();


    }

    private String getInfoText() {
        return infoText;
    }

    public void setInfoText(String infoText) {
        this.infoText = infoText;
    }
}
