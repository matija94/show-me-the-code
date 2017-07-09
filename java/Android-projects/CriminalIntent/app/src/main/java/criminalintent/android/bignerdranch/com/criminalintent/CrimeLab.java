package criminalintent.android.bignerdranch.com.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import database.CrimeBaseHelper;
import database.CrimeDbSchema;
import database.CrimeDbSchema.CrimeTable;

/**
 * Created by matija on 25.1.17..
 */

public class CrimeLab {

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private static CrimeLab sCrimeLab;

    public static CrimeLab get(Context context) {
        if (sCrimeLab==null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext)
                .getWritableDatabase();
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CrimeTable.Cols.UUID, crime.getId().toString());
        contentValues.put(CrimeTable.Cols.TITLE, crime.getTittle());
        contentValues.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        contentValues.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        contentValues.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());
        contentValues.put(CrimeTable.Cols.PHONE_NUM, crime.getPhoneNumber());
        return contentValues;
    }

    private CrimeCursorWraper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor =mDatabase.query(
                CrimeTable.NAME,
                null, // Columns-null select all columns
                whereClause,
                whereArgs,
                null, //groupBY
                null, //having
                null  // orderBy
        );
        return new CrimeCursorWraper(cursor);
    }


    public void addCrime(Crime crime) {
        ContentValues values = getContentValues(crime);
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    public boolean removeCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        mDatabase.delete(CrimeTable.NAME,CrimeTable.Cols.UUID + " = ?", new String[]{uuidString});
        return true;
    }

    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWraper cursor = queryCrimes(null, null);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return crimes;
    }

    public Crime getCrime(UUID id) {
        CrimeCursorWraper crimeCursorWraper = queryCrimes(CrimeTable.Cols.UUID + " = ?", new String[]{id.toString()});
        try{
            if (crimeCursorWraper.getCount() == 0) {
                return null;
            }
            crimeCursorWraper.moveToFirst();
            return crimeCursorWraper.getCrime();

        } finally {
            crimeCursorWraper.close();
        }
    }

    public File getPhotoFile(Crime crime) {
        File externaFileDir = mContext.
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (externaFileDir == null) {
            return null;
        }

        return new File(externaFileDir, crime.getPhotoFileName());
    }

}
