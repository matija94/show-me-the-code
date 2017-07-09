package criminalintent.android.bignerdranch.com.criminalintent;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import database.CrimeDbSchema.CrimeTable;

/**
 * Created by matija on 13.2.17..
 */

public class CrimeCursorWraper extends CursorWrapper {

    public CrimeCursorWraper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        Long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        int solved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(CrimeTable.Cols.SUSPECT));
        String phone = getString(getColumnIndex(CrimeTable.Cols.PHONE_NUM));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTittle(title);
        crime.setSolved(solved != 0);
        crime.setDate(new Date(date));
        crime.setSuspect(suspect);
        crime.setPhoneNumber(phone);
        return crime;
    }

}
