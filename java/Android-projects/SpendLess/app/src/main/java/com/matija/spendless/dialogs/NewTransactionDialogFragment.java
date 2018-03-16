package com.matija.spendless.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.matija.spendless.R;


/**
 * Created by matija on 15.3.18..
 */

public class NewTransactionDialogFragment extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_transaction_dialog, null);

        builder
            .setView(view)
            .setPositiveButton(R.string.categories, (dialogInterface, i) -> {
                dis
            });

    }
}
