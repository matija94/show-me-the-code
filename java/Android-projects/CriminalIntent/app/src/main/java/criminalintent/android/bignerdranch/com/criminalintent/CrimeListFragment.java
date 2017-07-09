package criminalintent.android.bignerdranch.com.criminalintent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by matija on 25.1.17..
 */
public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private TextView mEmptyCrimeListTextView;
    private Button mNewCrimeButton;
    private int mIntLastClickedListItem;
    private boolean mSubtitleVisible;
    //private boolean mItemDeleted;

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    private static final int REQUEST_CRIME_FRAGMENT_OPERATION = 0;
    private boolean mRecreated;

    private Callback mCallback;

    public interface Callback {
        void onCrimeSelected(Crime crime);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        /*if (requestCode == REQUEST_CRIME_FRAGMENT_OPERATION) {
            CrimeFragment.CrimeOperation operation = (CrimeFragment.CrimeOperation) data.getSerializableExtra(CrimeFragment.CRIME_ONFINSH);
            if (operation== CrimeFragment.CrimeOperation.DELETED) {
                mItemDeleted=true;
            }
            else {
                mItemDeleted=false;
            }
        }*/
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fragment manager is supposed to call onCreateOptionsMenu(), by calling this method below
        // we tell FM that onCreateOptionsMenu() should be called. Activity calls onCreateOptionsMenu() by default
        setHasOptionsMenu(true);

        if ( savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) v.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mEmptyCrimeListTextView = (TextView) v.findViewById(R.id.empty_crime_list_text_view_fragment_crime_list);

        mNewCrimeButton = (Button) v.findViewById(R.id.new_crime_button_fragment_crime_list);
        mNewCrimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity(),crime.getId());
                startActivity(intent);
            }
        });

       updateUI();
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (Callback) getActivity();
        mRecreated=true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mRecreated) {
            updateUI();
        }
        mRecreated=false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        }
        else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                /*Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
                startActivity(intent);*/
                updateUI();
                mCallback.onCrimeSelected(crime);
                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                // returns false
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getResources().getQuantityString(R.plurals.subtitle_format, crimeCount, crimeCount);
        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }


    public void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        // check if there is any crimes in crimeList
        if (crimes.isEmpty()) {
            // no existing crimes in list, views associated with empty crime list
            // should be visible
            mEmptyCrimeListTextView.setVisibility(View.VISIBLE);
            mNewCrimeButton.setVisibility(View.VISIBLE);
        }
        else {
            // there are already exsiting crimes, views associated with empty crime list
            // should be invisible now
            mEmptyCrimeListTextView.setVisibility(View.INVISIBLE);
            mNewCrimeButton.setVisibility(View.INVISIBLE);
        }

        // adapter managing
        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }
        /*else if (mItemDeleted){*/
        else if (crimes.size() < mAdapter.getCrimes().size()) {
            mAdapter.notifyItemRemoved(mIntLastClickedListItem);
            mAdapter.setCrimes(crimes);
            mAdapter.notifyItemRangeChanged(mIntLastClickedListItem, crimes.size());
            //mItemDeleted=false;
        }

        // crime added
        else if (crimes.size() > mAdapter.getCrimes().size()) {
            mAdapter.setCrimes(crimes);
            mAdapter.notifyItemInserted(crimes.size()-1);
            mAdapter.notifyItemRangeInserted(crimes.size()-1, crimes.size());
        }

            
        // crime item changed
        else if (mAdapter.getCrimes().size() == crimes.size()){
            mAdapter.setCrimes(crimes);
            mAdapter.notifyItemChanged(mIntLastClickedListItem);
        }

        updateSubtitle();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // fragment has been detached from activity so it cannot count on activity anymore
        mCallback = null;
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private CheckBox mSolvedCheckBox;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Crime mCrime;
        private int mPosition;

        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
        }

        public void bindCrime(Crime crime) {
            mCrime = crime;
            mSolvedCheckBox.setChecked(mCrime.isSolved());
            mTitleTextView.setText(mCrime.getTittle());
            mDateTextView.setText(formatDate(mCrime.getDate()));

        }

        private String formatDate(Date date) {
            String formatPattern = "EEEE, MMM d, yyyy. 'Time:' HH:mm:ss";
            Locale locale = Locale.ENGLISH;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                locale = new Locale.Builder().setLanguage("sr").setRegion("RS").setScript("Latn").build();
            }
            SimpleDateFormat format = new SimpleDateFormat(formatPattern, locale);
            return format.format(date);
        }

        @Override
        public void onClick(View v) {
            /*Intent i = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            mIntLastClickedListItem = this.getAdapterPosition();
            startActivityForResult(i, REQUEST_CRIME_FRAGMENT_OPERATION);*/
            mCallback.onCrimeSelected(mCrime);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            this.mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_crime,parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        public void setCrimes(List<Crime> crimes) {
            mCrimes = crimes;
        }

        public List<Crime> getCrimes() {
            return mCrimes;
        }
    }


}
