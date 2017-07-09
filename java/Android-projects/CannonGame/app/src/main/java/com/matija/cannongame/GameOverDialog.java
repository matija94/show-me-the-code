package com.matija.cannongame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by matija on 19.6.17..
 */

public class GameOverDialog extends DialogFragment {


    private CannonView cannonView;
    private int messageId;

    public void show(FragmentManager fm, String tag, CannonView
            cannonView, int messageId) {
        this.cannonView = cannonView;
        this.messageId = messageId;
        super.show(fm, tag);
    }

    public Dialog onCreateDialog(Bundle bundle) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(messageId));

        builder.setMessage(getResources().getString(R.string.results_format, cannonView.getShotsFired(), cannonView.getTotalTimeElapsed()));

        builder.setPositiveButton(R.string.reset_game, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cannonView.setDialogDisplayed(false); // indicates that dialog is no longer displayed
                cannonView.newGame();
            }
        });

        return builder.create();
    }
}
