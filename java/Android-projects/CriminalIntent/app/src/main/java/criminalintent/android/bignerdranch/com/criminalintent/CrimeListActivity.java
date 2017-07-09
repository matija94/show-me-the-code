package criminalintent.android.bignerdranch.com.criminalintent;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by matija on 25.1.17..
 */

public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callback, CrimeFragment.Callback {

    @Override
    protected Fragment createFragment() {
        Fragment fragment = new CrimeListFragment();
        return fragment;
    }

    @Override
    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onCrimeSelected(Crime crime) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = CrimePagerActivity.newIntent(this, crime.getId());
             startActivity(intent);
        }
        else {
            CrimeFragment crimeFragment = CrimeFragment.newInstance(crime.getId());
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.detail_fragment_container, crimeFragment)
                    .commit();
        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
     CrimeListFragment listFragment = (CrimeListFragment) getSupportFragmentManager()
             .findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}
