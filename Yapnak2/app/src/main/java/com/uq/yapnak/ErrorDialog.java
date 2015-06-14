package com.uq.yapnak;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by vahizan on 14/06/2015.
 */
public class ErrorDialog extends DialogFragment{

    private View errorDialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder error = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        errorDialog = inflater.inflate(R.layout.error_dialog,null);
        error.setView(errorDialog);

        error.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                ErrorDialog.this.getDialog().dismiss();

            }
        });

       return error.create();


    }
}
