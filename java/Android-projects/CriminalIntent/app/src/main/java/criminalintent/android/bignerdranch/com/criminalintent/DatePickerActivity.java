package criminalintent.android.bignerdranch.com.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.Date;
import java.util.UUID;

/**
 * Created by matija on 11.2.17..
 */

public class DatePickerActivity extends SingleFragmentActivity {

    private static final String EXTRA_CRIME_DATE = "com.bignerdranch.android.criminalintent.DatePickerActivity.extra_crime_date";

    public static Intent newIntent(Context packageContext, Date date) {
        Intent i = new Intent(packageContext, DatePickerActivity.class);
        i.putExtra(EXTRA_CRIME_DATE, date);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        Date date = (Date) getIntent().getSerializableExtra(EXTRA_CRIME_DATE);
        return DatePickerFragment.newInstance(date);
    }



}
