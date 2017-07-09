package com.matija.doodlz;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Created by matija on 5.6.17..
 */

public class EraseImageDialogFragment extends DialogFragment {

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        MainActivityFragment doodleFragment = getDoodleFragment();
        if (doodleFragment != null) {
            doodleFragment.setDialogOnScreen(true);
        }
    }

    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.message_erase);

        builder.setPositiveButton(R.string.button_erase, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getDoodleFragment().getDoodleView().clear();
            }
        });

        builder.setNegativeButton(android.R.string.cancel, null);

        return builder.create();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        MainActivityFragment doodleFragment = getDoodleFragment();
        if (doodleFragment != null) {
            doodleFragment.setDialogOnScreen(false);
        }
    }

    private MainActivityFragment getDoodleFragment() {
        return (MainActivityFragment) getFragmentManager().findFragmentById(R.id.doodleFragment);
    }
}
