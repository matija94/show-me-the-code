package criminalintent.android.bignerdranch.com.criminalintent;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by matija on 19.2.17..
 */

public class CrimeImageFragment extends DialogFragment {

    private static final String EXTRA_CRIME_PHOTO_FILE = "com.android.bignerdranch.android.CrimeImage";
    private static final String EXTRA_CRIME_TITLE = "com.android.bignerdranch.android.CrimeTitle";
    private ImageView mCrimeImageView;


    public static CrimeImageFragment newInstance(File crimePhotoFile, String title) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_PHOTO_FILE, crimePhotoFile);
        args.putString(EXTRA_CRIME_TITLE, title);

        CrimeImageFragment fragment = new CrimeImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_crime_image, null);
        File photoFile = (File) getArguments().getSerializable(EXTRA_CRIME_PHOTO_FILE);
        if (!photoFile.exists()) {
            return new AlertDialog.Builder(getActivity())
                    .setView(v)
                    .setTitle(R.string.no_image)
                    .create();

        }
        else {
            mCrimeImageView = (ImageView) v.findViewById(R.id.image_view_dialog_crime_image);
            //mCrimeImageView.setImageURI(Uri.fromFile(photoFile));
            mCrimeImageView.setImageBitmap(PictureUtils.getScaledBitmap(photoFile.getAbsolutePath(), getActivity()));
            return new AlertDialog.Builder(getActivity())
                    .setView(v)
                    .setTitle(getArguments().getString(EXTRA_CRIME_TITLE))
                    .create();
        }

    }

}
