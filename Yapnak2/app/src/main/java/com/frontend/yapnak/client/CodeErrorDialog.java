package com.frontend.yapnak.client;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.uq.yapnak.R;

/**
 * Created by vahizan on 25/06/2015.
 */
public class CodeErrorDialog extends DialogFragment {

    private View clientError;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder clientErrorDialog = new AlertDialog.Builder(getActivity());

        LayoutInflater inflate = getActivity().getLayoutInflater();

        clientError = inflate.inflate(R.layout.enter_correct_user_code,null);

        clientErrorDialog.setView(clientError);

        clientErrorDialog.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               dialog.dismiss();

            }
        });

       return clientErrorDialog.create();


    }
}
