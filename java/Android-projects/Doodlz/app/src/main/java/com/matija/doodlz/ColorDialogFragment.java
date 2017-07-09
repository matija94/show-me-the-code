package com.matija.doodlz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.SeekBar;

/**
 * Created by matija on 5.6.17..
 */

public class ColorDialogFragment extends DialogFragment {

    private SeekBar alphaSeekBar;
    private SeekBar redSeekBar;
    private SeekBar blueSeekBar;
    private SeekBar greenSeekBar;
    private View colorView;
    private int color;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        MainActivityFragment fragment = getDoodleFragment();

        if (fragment != null) {
            // tell main activity fragment that dialog is displayed atm
            fragment.setDialogOnScreen(true);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        MainActivityFragment doodleFragment = getDoodleFragment();
        if (doodleFragment != null) {
            doodleFragment.setDialogOnScreen(false);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        // create dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View colorDialogFragmentView = getActivity().getLayoutInflater().inflate(R.layout.fragment_color, null);

        builder.setView(colorDialogFragmentView); // add gui to dialog

        builder.setTitle(R.string.title_color_dialog); // set title

        // reference components
        alphaSeekBar = (SeekBar) colorDialogFragmentView.findViewById(R.id.alphaSeekBar);
        redSeekBar = (SeekBar) colorDialogFragmentView.findViewById(R.id.redSeekBar);
        greenSeekBar = (SeekBar) colorDialogFragmentView.findViewById(R.id.greenSeekBar);
        blueSeekBar = (SeekBar) colorDialogFragmentView.findViewById(R.id.blueSeekBar);
        colorView = colorDialogFragmentView.findViewById(R.id.colorView);


        // register listener
        alphaSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        greenSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        redSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        blueSeekBar.setOnSeekBarChangeListener(colorChangedListener);

        final DoodleView doodleView = getDoodleFragment().getDoodleView();
        color = doodleView.getDrawingColor();

        alphaSeekBar.setProgress(Color.alpha(color));
        redSeekBar.setProgress(Color.red(color));
        blueSeekBar.setProgress(Color.blue(color));
        greenSeekBar.setProgress(Color.green(color));

        // add set color button

        builder.setPositiveButton(R.string.button_set_color, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doodleView.setDrawingColor(color);
            }
        });

        return builder.create();
    }

    private MainActivityFragment getDoodleFragment() {
        return (MainActivityFragment) getFragmentManager().findFragmentById(R.id.doodleFragment);
    }

    private final SeekBar.OnSeekBarChangeListener colorChangedListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // user changed, not the program( we use this, becuase we initially set these seek bars to default color of white on start
            if (fromUser) {
                color = Color.argb(alphaSeekBar.getProgress(), redSeekBar.getProgress(), greenSeekBar.getProgress(), blueSeekBar.getProgress());
                // set colorView background color to new color so use can see color he selected
                colorView.setBackgroundColor(color);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

}
