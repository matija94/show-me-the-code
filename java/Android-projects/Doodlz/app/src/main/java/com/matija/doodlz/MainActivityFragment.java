package com.matija.doodlz;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private DoodleView doodleView; // handles touch events and draws
    private float acceleration; // float var to determine shake events which bring up erase dialog
    private float currentAcceleration;
    private float lastAcceleration;
    private boolean dialongOnScreen = false; // boolean to tell if there is dialog currently in the foreground, to prevent multiple dialogs active at the same time
                                                // for example if user selected color dialog from the app menu bar and then shaked the device erase dialog would pop up on top of color dialog.
                                                // we want to prevent this

    private static final int ACCELERATION_THRESHOLD = 100000; // used to determine whether user shook the device, or it was some small device movement(which are usual)

    // used to indetify the request for writing to external storage which the save image feature needs
    private static final int SAVE_IMAGE_PERMISSION_REQUEST_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        setHasOptionsMenu(true); // this fragment has menu items to display

        doodleView = (DoodleView) v.findViewById(R.id.doodleView);

        // initialize acceleration values
        acceleration = 0.00f;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        lastAcceleration = SensorManager.GRAVITY_EARTH;

        return v;
    }

    @Override
    public void onResume() {
        // once here we know that this fragment is currently on the foreground and user is interacting with it
        super.onResume();
        // so we will enable accelerometer listening
        enableAccelerometerListening(); // listen for shake event
    }

    @Override
    public void onPause() {
        // once here we know that this fragment is not in the foreground anymore!
        super.onPause();
        // so we will desiable accelerometer listening
        disableAccelerometerListening();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.doodle_fragment_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.color:
                ColorDialogFragment colorDialogFragment= new ColorDialogFragment();
                colorDialogFragment.show(getFragmentManager(), "color dialog");
                return true;
            case R.id.line_width:
                LineWidthDialogFragment lineWidthDialogFragment= new LineWidthDialogFragment();
                lineWidthDialogFragment.show(getFragmentManager(), "line width dialog");
                return true;
            case R.id.delete_drawing:
                confirmErase();
                return true;
            case R.id.save:
                saveImage();
                return true;
            case R.id.print:
                doodleView.printImage();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // requests the permission from to user to write to external storage if it is not approved yet by the user
    // saves the image drawn on the screen
    private void saveImage() {
        // check if app does not have permission needed to write to external storage(save the image)
        if (getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // show an explanation to the user why permission is needed

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                // set alert dialog msg
                builder.setMessage(R.string.permission_explanation);

                // add an OK button to the dialog
                builder.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                    SAVE_IMAGE_PERMISSION_REQUEST_CODE);
                            }
                        });

                // create and show the dialog
                builder.create().show();
            }
            else {
                // no explanation why permission is needed so just request it
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                SAVE_IMAGE_PERMISSION_REQUEST_CODE);
            }
        }
        else {
            // app already has required permission to save image
            doodleView.saveImage(); //save the image
        }
    }

    // called by the system when the user either grant or denies the permission
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case SAVE_IMAGE_PERMISSION_REQUEST_CODE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doodleView.saveImage();
                }
                else {
                    Toast.makeText(getActivity(), R.string.permission_write_external_store_denied, Toast.LENGTH_SHORT)
                            .show();
                }
                return;
        }
    }

    // will be called by the app's dialog fragment subclasses
    // e.g color dialog fragment needs a reference to doodleView to change it's color drawing
    public DoodleView getDoodleView() {
        return doodleView;
    }

    // will be called by the app's dialog fragment subclasses
    // to make the app know that one dialog is currently displayed and prevent displaying other dialogs while this one is active
    // e.g color dialog fragment is active and the user shakes the device to trigger on shake-erase event
    public void setDialogOnScreen(boolean visible) {
        dialongOnScreen = visible;
    }

    private void disableAccelerometerListening() {
        SensorManager sensorManager = (SensorManager) getActivity()
                .getSystemService(Context.SENSOR_SERVICE);

        sensorManager.unregisterListener(
                sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    // enables listening for accelerometer events
    private void enableAccelerometerListening() {
        SensorManager sensorManager = (SensorManager) getActivity()
                .getSystemService(Context.SENSOR_SERVICE);
        // register to listen for accelerometer events
        sensorManager.registerListener(sensorEventListener,
                // get the sensor object that listents to Accelerometer data
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                // rate at which android delivers sensor events, this is default value, it can be faster but it is also more cpu and battery intensive then
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    // anonymous listener to determine wheter the user actually shook the device or it was just usual small movement
    private final SensorEventListener sensorEventListener =
            new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    // no need to set dialongOnScreen to true in this case
                    // since this is the only dialog that can be activated while others are active
                    // because sensor events occur in different threads of execution

                    // ensure that other dialogs are not displayed
                    if (!dialongOnScreen) {
                        float x = event.values[0];
                        float y = event.values[1];
                        float z = event.values[2];

                        // save previous acceleration value
                        lastAcceleration = currentAcceleration;

                        // calc the current acceleration
                        currentAcceleration = x*x + y*y + z*z;

                        // calc the change in the acceleration
                        acceleration = currentAcceleration * (currentAcceleration - lastAcceleration);

                        // if acceleration is above a certain threshold
                        if (acceleration > ACCELERATION_THRESHOLD) {
                            confirmErase();
                        }
                    }
                }
                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {}
            };

    private void confirmErase() {
        EraseImageDialogFragment eraseImgDialogFragment = new EraseImageDialogFragment();
        eraseImgDialogFragment.show(getFragmentManager(), "erase dialog");
    }

}
