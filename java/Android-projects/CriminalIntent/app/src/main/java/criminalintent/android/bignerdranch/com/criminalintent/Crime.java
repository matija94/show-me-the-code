package criminalintent.android.bignerdranch.com.criminalintent;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by matija on 19.1.17..
 */

public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private String mSuspect;
    private String mPhoneNumber;

    public Crime() {
        this(UUID.randomUUID());
    }

    public Crime(UUID uuid) {
        mId = uuid;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTittle() {
        return mTitle;
    }

    public void setTittle(String tittle) {
        this.mTitle = tittle;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getPhotoFileName() {
        return "IMG_" + getId().toString() + ".jpg";
    }


    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber!=null) {
            mPhoneNumber = phoneNumber.replaceAll(" ", "");
        }
    }
}
