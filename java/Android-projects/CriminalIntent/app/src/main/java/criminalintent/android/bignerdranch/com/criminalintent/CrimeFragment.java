package criminalintent.android.bignerdranch.com.criminalintent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by matija on 19.1.17..
 */

public class CrimeFragment extends Fragment{

    public static final String CRIME_ONFINSH = "crime_operation";


    private static final String CRIME_ID = "crime_id";
    private static final String TIME = "DialogTime";
    private static final String IMAGE_ZOOM = "DialogImage";
    private static final String DATE = "DialogDate";


    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final int REQUEST_CONTACT = 2;
    private static final int READ_CONTACT_PERMISSION = 3;
    private static final int REQUEST_PHOTO = 4;

    private Crime mCrime;
    private File mPhotoFile;

    private Point mPhotoViewSize;

    private EditText mTitleField;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mSolvedCheckBox;
    private Button mSendReportButton;
    private Button mSuspectButton;
    private Button mCallSuspectButton;
    private ImageView mPhotoView;
    private ImageButton mPhotoButton;
    private Callback mCallback;

    public static enum CrimeOperation{
        MADE,
        DELETED;
    }

    public interface Callback {
        void onCrimeUpdated(Crime crime);
    }

    private void updateCrime() {
        CrimeLab.get(getActivity()).updateCrime(mCrime);
        mCallback.onCrimeUpdated(mCrime);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (Callback) getActivity();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCallback=null;
    }

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(CRIME_ID, crimeId);

        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(args);
        return crimeFragment;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
            updateCrime();
        }

        else if (requestCode == REQUEST_TIME) {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mCrime.setDate(date);
            updateTime();
            updateCrime();
        }

        else if (requestCode == REQUEST_CONTACT && data != null) {
            Uri contactUri = data.getData();
            addContactName(contactUri);
            addPhoneNumber(mCrime.getSuspect());
            mSuspectButton.setText(mCrime.getSuspect());
            updateCallSuspectButton();
            updateCrime();

        }

        else if (requestCode == REQUEST_PHOTO) {
            updatePhotoView();
            updateCrime();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        initPhotoViewDimensions();
    }

    private void addPhoneNumber(String suspect) {
        Cursor c = getActivity().getContentResolver()
                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER}, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = ?", new String[]{suspect}, null);
        String phoneNumber = null;
        try{
            if (c.moveToFirst()) {
                phoneNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                mCrime.setPhoneNumber(phoneNumber);
            }
        }finally {
            c.close();
        }
    }

    private void addContactName(Uri contactUri) {
        String queryFields[] = {ContactsContract.Contacts.DISPLAY_NAME};
        Cursor c = getActivity().getContentResolver()
                .query(contactUri,queryFields, null, null, null);
        try {
            if (c.moveToFirst()) {
                String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                mCrime.setSuspect(name);
            }
        } finally {
            c.close();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // uncomment line below if you want crime image to be loaded in proper way. Atm it's loaded in unefficient way.
        // although uncommenting line below will make the image reload and user may experiance very small delay upon loading crime from crime list
        // TODO: load image effiecntly if possible, having listener for layout pass has to happen after onCreateView(), that is either onStart() or onResume(). But image atm is being set
        // TODO: in onCreateView() so layout pass still hasnt happened yet and we do not have exact dimension of the image and have to use conservative estimate approach for setting bitmap img
        //updatePhotoView();
    }


    private void initPhotoViewDimensions() {
        // get viewTreeObserver for mPhotoView and implement on global layout listener
        // which will listen everytime layout is passed.
        //For drawing views android makes two passes first is to measure dimensions
        // and second pass is layout pass when each parent is resposnible for
        // positioning all of its childrends with the measured sizes
        // onGlobalLayoutListener is listening for layout pass when all the widgets should already
        // have their dimensions measured
        final ViewTreeObserver viewTreeObserver = mPhotoView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mPhotoView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                boolean isFirstPass = mPhotoViewSize==null;
                mPhotoViewSize = new Point();
                mPhotoViewSize.set(mPhotoView.getWidth(), mPhotoView.getHeight());

                if (isFirstPass) {
                    updatePhotoView();
                }

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
        mPhotoFile = CrimeLab.get(getActivity()).getPhotoFile(mCrime);
        // fragment manager will call onCreateOptionsMenu() if method below is called with true argument

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText) v.findViewById(R.id.crime_tittle);
        mTitleField.setText(mCrime.getTittle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // this space left blank on purpose
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTittle(s.toString());
                updateCrime();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // this space left blank on purpose
            }
        });
        mDateButton = (Button) v.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    FragmentManager fm = getFragmentManager();
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mCrime.getDate());
                datePickerFragment.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                datePickerFragment.show(fm, DATE);*/
                startActivityForResult(DatePickerActivity.newIntent(getActivity(), mCrime.getDate()), REQUEST_DATE);
            }
        });

        mTimeButton = (Button) v.findViewById(R.id.crime_time);
        updateTime();
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mCrime.getDate());
                timePickerFragment.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                timePickerFragment.show(fm, TIME);
            }
        });


        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
                updateCrime();
            }
        });

        mSendReportButton = (Button) v.findViewById(R.id.crime_report);
        mSendReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooserIntent = ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText(getCrimeReport())
                        .setSubject(getString(R.string.crime_report_subject))
                        .createChooserIntent();

                /*Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));
                i = Intent.createChooser(i , getString(R.string.send_report));
                */startActivity(chooserIntent);
            }
        });

        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        mSuspectButton = (Button) v.findViewById(R.id.crime_suspect);
        mSuspectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    getActivity().requestPermissions(new String[]{android.Manifest.permission.READ_CONTACTS}, READ_CONTACT_PERMISSION);
                }
                startActivityForResult(pickContact, REQUEST_CONTACT);
            }
        });
        if (mCrime.getSuspect() !=null) {
            mSuspectButton.setText(mCrime.getSuspect());
        }
        PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY) == null) {
            mSuspectButton.setEnabled(false);
        }

        mCallSuspectButton = (Button) v.findViewById(R.id.crime_call);
        updateCallSuspectButton();

        mPhotoView = (ImageView) v.findViewById(R.id.crime_photo);
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                CrimeImageFragment imageDialog = CrimeImageFragment.newInstance(mPhotoFile, mCrime.getTittle());
                imageDialog.show(fm,IMAGE_ZOOM);
            }
        });
       // updatePhotoView();

        mPhotoButton = (ImageButton) v.findViewById(R.id.crime_camera);
        final Intent captureImage= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePhoto = mPhotoFile != null && captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);

        if (canTakePhoto) {
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        return v;
    }

    private void updateCallSuspectButton() {
        if (mCrime.getSuspect() != null && mCrime.getPhoneNumber() != null) {
            mCallSuspectButton.setVisibility(View.VISIBLE);
            mCallSuspectButton.setText(getString(R.string.crime_call, mCrime.getSuspect()));

            mCallSuspectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                                      //passing R.string.telephone_uri_prefix doesn't work here, but "tel:" does
                    Uri uri = Uri.parse("tel:"+mCrime.getPhoneNumber());
                    Intent i = new Intent(Intent.ACTION_DIAL, uri);
                    startActivity(i);
                }
            });

        }
        else {
            mCallSuspectButton.setVisibility(View.INVISIBLE);
        }
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        }
        else {
            Bitmap bitmap = mPhotoViewSize == null ? PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity()) : PictureUtils.getScaledBitmap(mPhotoFile.getPath(), mPhotoViewSize.x, mPhotoViewSize.y);
            mPhotoView.setImageBitmap(bitmap);
        }
    }


    @Override
    public void onPause() {
        Log.d("CRIME_FRAGMENT", "ON PAUSE CALLED");
        super.onPause();

        CrimeLab.get(getActivity())
                .updateCrime(mCrime);
    }


    @Override
    public void onStop() {
        Log.d("CRIME_FRAGMENT", "ON STOP CALLED");
        super.onStop();
    }

    private void updateDate() {
        mDateButton.setText(formatDate(mCrime.getDate()));
    }

    private void updateTime() {
        mTimeButton.setText(formateTime(mCrime.getDate()));
    }

    private String formateTime(Date date) {
        String formatPattern = "HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(formatPattern);
        return sdf.format(date);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete_crime:
                CrimeLab.get(getActivity()).removeCrime(mCrime);
                finish(CrimeOperation.DELETED);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void finish(CrimeOperation operation) {
        Intent intent = new Intent();
        intent.putExtra(CRIME_ONFINSH, operation);
        getActivity().setResult(Activity.RESULT_OK,intent);
        getActivity().finish();
    }



    private String formatDate(Date date) {
        String formatPattern = "EEEE, MMM d, yyyy.";
        Locale locale = Locale.ENGLISH;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            locale = new Locale.Builder().setLanguage("sr").setRegion("RS").setScript("Latn").build();
        }
        SimpleDateFormat format = new SimpleDateFormat(formatPattern, locale);
        return format.format(date);
    }

    private String getCrimeReport() {
        String solvedString = null;
        solvedString = mCrime.isSolved() ? getString(R.string.crime_report_solved) : getString(R.string.crime_report_unsloved);
        String formattedDate = formatDate(mCrime.getDate());
        String suspect = mCrime.getSuspect();
        suspect = (suspect==null ? getString(R.string.crime_report_no_suspect) : getString(R.string.crime_report_suspect, suspect));
        String report = getString(R.string.crime_report, mCrime.getTittle(), formattedDate, solvedString, suspect);
        return report;
    }

}
