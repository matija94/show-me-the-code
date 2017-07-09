package com.bignerdranch.android.photogallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matija on 6.3.17..
 */


public class PhotoGalleryFragment extends VisibleFragment {
    // api key
    // 233e9e9a591577b5ccbb16fb68964489

    // secret key
    // 0ef2a358d0a14372
    private RecyclerView mPhotoRecyclerView;
    private ProgressBar mProgressBar;
    private List<GalleryItem> mItems;
    private ThumbnailDownloader<PhotoHolder> mThumbnailDownloader;
    private ThumbnailDownloader<PhotoHolder>.InPlaceDownloader mThumbnailDownloaderInPlace;
    private ThumbnailDownloader<PhotoHolder>.PreloadDownloader mThumbnailDownloaderPreload;

    private int mPageToFetch=1;
    private boolean mNewSearch=true;
    private static final String TAG = "PHOTO GALLERY FRAGMENT";
    private TestCallback mTestCallback;

    public interface TestCallback {
        void endLoadingAnim();
    }


    public static PhotoGalleryFragment newInstance() {
        return new PhotoGalleryFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mTestCallback = (TestCallback) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // do not destroy activity when its poped from the stack
        // just detach it because every time onCreate method is called new fetch task would be started
        // while retaining instance we guard against OS calling onDestroy for this fragment
        // that means onCreate will be called just once. Every time device is rotated fragment will be detached and retached again


        setRetainInstance(true);

        // tells fragment to recieve menu callbacks
        setHasOptionsMenu(true);


        Handler responseHandler = new Handler();
        Handler preloadResponseHanlder = new Handler();
        mThumbnailDownloader = new ThumbnailDownloader<>();
        mThumbnailDownloaderInPlace = mThumbnailDownloader. new InPlaceDownloader(responseHandler);
        mThumbnailDownloaderPreload = mThumbnailDownloader.new PreloadDownloader(preloadResponseHanlder);

        mThumbnailDownloader.setThumbnailDownloaderListener(new ThumbnailDownloader.ThumbnailDownloaderListener<PhotoHolder>() {
            @Override
            public void onThumbnailDownloaded(PhotoHolder target, Bitmap thumbnail) {
                Drawable drawable = new BitmapDrawable(getResources(), thumbnail);
                target.bindItem(drawable);

                //TODO: dynamically compute the number of holders that can fit in device's display
                //target.getAdapterPosition()==12
                ScrollLockLayout manager = (ScrollLockLayout) mPhotoRecyclerView.getLayoutManager();
                int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                if (mNewSearch && target.getAdapterPosition() == lastVisibleItemPosition) {
                    Log.i(TAG, "Currently last visible item pos=" + lastVisibleItemPosition + ", got it's img binded");
                    // means new search was requested and first images to be displayed are loaded
                    Log.i(TAG, "New search was handled and first images to be displayed are loaded");
                    setNewSearch(false);
                   // endLoadingAnimation();

                }
            }

            @Override
            public void onDownloadRequestMapEmpty() {
                Log.i(TAG, "Download req map empty");
                endLoadingAnimation();
                // just testing callback impl in hosting activity of this fragment
                mTestCallback.endLoadingAnim();
            }

            @Override
            public void onPreloadRequestMapEmpty() {
                Log.i(TAG, "Preload req map empty");
            }

        });

        mThumbnailDownloaderInPlace.start();
        mThumbnailDownloaderInPlace.getLooper();

        mThumbnailDownloaderPreload.start();
        mThumbnailDownloaderPreload.getLooper();

        Log.i(TAG, "Background thread started: thumbnail downloader");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery,container,false);

        mPhotoRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_photo_gallery_recycler_view);
        // recycler view list will be displayed in grids, 3 columns per row
        mPhotoRecyclerView.setLayoutManager(new ScrollLockLayout(getActivity(),3));
        mPhotoRecyclerView.addOnScrollListener(new PhotoScrollListener());
        mPhotoRecyclerView.setVisibility(View.INVISIBLE);

        mProgressBar = (ProgressBar) v.findViewById(R.id.fragment_photo_gallery_progress_bar);
        // set it to gone(invisible and doesn't take up memory)
        mProgressBar.setVisibility(View.GONE);
        startLoadingAnimation();

        updateItems();

        //setupAdapter();
        return v;
    }

    /**
     * Inflates the menu with items, defined in corresponding menu xml file, in the app's toolbar
     * @param menu
     * @param menuInflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu,menuInflater);
        menuInflater.inflate(R.menu.fragment_photo_gallery, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);

        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "QueryTextSubmit: " + s);
                String previousQuery = QueryPreferences.getStoredQuery(getActivity());

                if (s==null || s.isEmpty() || previousQuery == null || !previousQuery.equalsIgnoreCase(s)) {
                    QueryPreferences.setStoredQuery(getActivity(), s);
                    mItems=null;

                    setNewSearch(true);
                    Log.i(TAG, "User is requesting new search");
                }
                else {
                    // current query is same as the previous query so do nothing
                    return true;
                }

                // collapse keyboard and search view
                searchView.onActionViewCollapsed();
                updateItems();
                // true is returned to signify the system that user input has been handled
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "QueryTextChange: " + s);
                return false;
            }
        });

        MenuItem toggleItem = menu.findItem(R.id.meni_item_toggle_polling);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (PollJobService.isScheduled(getActivity())) {
                toggleItem.setTitle(R.string.stop_polling);
            }
            else {
                toggleItem.setTitle(R.string.start_polling);
            }
        }*/
         //else{
            if (PollService.isServiceAlarmOn(getActivity())) {
                toggleItem.setTitle(R.string.stop_polling);
            }
            else {
                toggleItem.setTitle(R.string.start_polling);
            }
       // }

        // when the user expands the search window, set the query text
        // last query submited. Do not submit it before user tells
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = QueryPreferences.getStoredQuery(getActivity());
                searchView.setQuery(query, false);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_clear:
                QueryPreferences.setStoredQuery(getActivity(), null);
                Log.i(TAG, "Search cleared");
                //updateItems();
                return true;
            case R.id.meni_item_toggle_polling:
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (!PollJobService.isScheduled(getActivity())) {
                        PollJobService.scheduleService(getActivity());
                    }
                    else {
                        PollJobService.cancelJob(getActivity());
                    }
                }*/
                //else
                {
                    boolean shouldStartAlarm = !PollService.isServiceAlarmOn(getActivity());
                    PollService.setServiceAlarm(getActivity(), shouldStartAlarm);
                }
                getActivity().invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //TODO: reimpl
    private void updateItems() {
        String query = QueryPreferences.getStoredQuery(getActivity());
        new FetchItemTask(query).execute(mPageToFetch);
    }


    private class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mImageView;

        public PhotoHolder(View itemView) {
            super(itemView);
            mImageView= (ImageView) itemView;
            mImageView.setOnClickListener(this);
        }

        public void bindItem(Drawable drawable) {
            mImageView.setImageDrawable(drawable);
        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, Integer.toString(getAdapterPosition()));
            Intent i = PhotoPageActivity.newIntent(getActivity(), mItems.get(getAdapterPosition()).getPhotoPageUri());
            startActivity(i);
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
        private List<GalleryItem> mGalleryItemList;

        public PhotoAdapter(List<GalleryItem> items) {
            mGalleryItemList = items;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View v = inflater.inflate(R.layout.gallery_item, parent, false);
            return new PhotoHolder(v);
        }

        @Override
        public void onBindViewHolder(PhotoHolder holder, int position) {

            GalleryItem item = mGalleryItemList.get(position);
            Bitmap bitmap = mThumbnailDownloader.getCachedImage(item.getUrl());
            if (bitmap==null) {
                // no real image to display
                startLoadingAnimation();

                Drawable placeholder = getResources().getDrawable(R.drawable.bill_up_close);
                holder.bindItem(placeholder);
                if (mThumbnailDownloaderInPlace.queueThumbnail(holder, item.getUrl())) {
                    Log.i(TAG, "No image in cache. Queueing message to download. Holder position: " + holder.getAdapterPosition());
                }
            }
            else {
                Log.i(TAG, "Loaded image from cache. Holder position: " + holder.getAdapterPosition());
                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                holder.bindItem(drawable);

                // if last image to be loaded is in cache then loading anim might persist
                // if all previous loaded images were already in cache(because there was no call to onDownloadRequestMapEmpty()

                if (mItems != null && position == mItems.size()-1) {
                    Log.i(TAG, "Last image to be displayed is loaded from cache. Stop loading anim");
                    endLoadingAnimation();
                }
            }

        }

        @Override
        public int getItemCount() {
            return mGalleryItemList.size();
        }

        public GalleryItem getItem(int position) {
            return mGalleryItemList.get(position);
        }

    }

    private void setupAdapter() {
        // check if fragment is currently added to its activity(means if is attached)
        // which implicitly means that getActivity() != null
        if (isAdded()) {
            mPhotoRecyclerView.setAdapter(new PhotoAdapter(mItems));
        }
    }


    private class PhotoScrollListener extends RecyclerView.OnScrollListener {
        private static final int PRELOAD_ELEMENTS = 100;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy){
            // check to see if view could be scrolled down
            ScrollLockLayout manager = (ScrollLockLayout) recyclerView.getLayoutManager();
            PhotoAdapter photoAdapter = (PhotoAdapter) mPhotoRecyclerView.getAdapter();
            // if it is disabled then it's not page end, but items are just loading so scrolling is temporarly disabled
            if (mProgressBar.getVisibility()==View.GONE && manager.findLastVisibleItemPosition()==mPhotoRecyclerView.getAdapter().getItemCount()-1) {
                startLoadingAnimation();

                Log.i(TAG, "Reached end of page: " + mPageToFetch);
                String query = QueryPreferences.getStoredQuery(getActivity());
                new FetchItemTask(query).execute(++mPageToFetch);
            }
            if (dy>0) {
                // preload after the last visible item in the recycler view
                int last=manager.findLastVisibleItemPosition();
                int bound = Math.min(photoAdapter.getItemCount()-1, last+PRELOAD_ELEMENTS);
                for (int i=last;i<=bound;i++) {
                    mThumbnailDownloaderPreload.preloadImage(i, photoAdapter.getItem(i).getUrl());
                }
            }
            else if (dy<0){
                int first = manager.findFirstVisibleItemPosition();
                int bound = Math.max(first-PRELOAD_ELEMENTS,0);
                for (int i=first;i>=bound;i--) {
                    mThumbnailDownloaderPreload.preloadImage(i, photoAdapter.getItem(i).getUrl());
                }
            }

        }

        /*@Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState){
            if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_SETTLING) {
                ScrollLockLayout gridLayoutManager = (ScrollLockLayout) mPhotoRecyclerView.getLayoutManager();
                PhotoAdapter photoAdapter = (PhotoAdapter) mPhotoRecyclerView.getAdapter();

                //preload behind the first visible item in the recycler view
                int start=gridLayoutManager.findFirstVisibleItemPosition();
                int bound = Math.max(start-20,0);
                for (int i=start;i>=bound; i--) {
                    GalleryItem item = photoAdapter.getItem(i);
                    mThumbnailDownloader.preloadImage(i, photoAdapter.getItem(i).getUrl());
                }

                // preload after the last visible item in the recycler view
                int last=gridLayoutManager.findLastVisibleItemPosition();
                bound = Math.min(last+20,mItems.size()-1);
                for (int i=last;i<=bound;i++) {
                    GalleryItem item = photoAdapter.getItem(i);
                    mThumbnailDownloader.preloadImage(i, photoAdapter.getItem(i).getUrl());
                }
            }

        }*/
    }


    private class FetchItemTask extends AsyncTask<Integer, Void, List<GalleryItem>> {
        private String mQuery;

        public FetchItemTask(String query) {
            mQuery=query;
        }


        @Override
        protected void onPreExecute() {
            // activate progress bar and disable vertical scroling
            //Log.d(TAG, "onPreExecute(), loading animation started");
            //startLoadingAnimation();
        }


        // upon successfully terminating. This method will return it's result to
        // onPostExecute
        // takes integer parameter which servers as reference to page that should be downloaded
        @Override
        protected List<GalleryItem> doInBackground(Integer... params) {
            if (mQuery==null) {
                return new FlickrFetchr().fetchRecentPhotos(params[0]);
            }
            else {
                return new FlickrFetchr().searchPhotos(mQuery, params[0]);
            }
        }

        //this method will recieve the result of doInBackGround() after it is finished successfully
        // we are updating adapter in this method which is being run on the main thread(UI thread)
        // but it is safe to be that way because this method will always be run on the main (UI thread)
        @Override
        protected void onPostExecute(List<GalleryItem> items) {
            int lastPosInOldSet = 0;
            if (mItems==null) {
                mItems = new ArrayList<>(items);
                setupAdapter();
            }
            else {
                lastPosInOldSet = mPhotoRecyclerView.getAdapter().getItemCount()-1;
                mItems.addAll(items);
                mPhotoRecyclerView.getAdapter().notifyItemRangeInserted(mPhotoRecyclerView.getAdapter().getItemCount(), items.size());
            }

            // due to some wierd bug... loading animation doesn't get canceled..
            // probably if new fetched images are already loaded in cache then there is no call to
            // download image map empty which breaks the loading anim
            if (mPageToFetch>1) {
                ScrollLockLayout layoutManager = (ScrollLockLayout) mPhotoRecyclerView.getLayoutManager();
                // jump down just one row to make android toggle adapter for new images which will break loading anim in case it stucks
                layoutManager.scrollToPosition(lastPosInOldSet+3);
            }
            //Log.i(TAG, "onPostExecute(..), loading animation ended");
            //endLoadingAnimation();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mThumbnailDownloaderPreload.clearQueue();
        mThumbnailDownloaderInPlace.clearQueue();
        mThumbnailDownloader.clearCache();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThumbnailDownloaderInPlace.quit();
        mThumbnailDownloaderPreload.quit();
        Log.i(TAG, "Background thread destroyed");
    }

    private void startLoadingAnimation() {
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        Log.i(TAG, "Started loading animation");
        mProgressBar.setVisibility(View.VISIBLE);
        ((ScrollLockLayout) mPhotoRecyclerView.getLayoutManager()).setScrollEnabledVertically(false);
    }

    private void endLoadingAnimation() {
        if (mProgressBar.getVisibility() == View.GONE) return;
        Log.i(TAG, "Ended loading animation");
        mProgressBar.setVisibility(View.GONE);
        ((ScrollLockLayout) mPhotoRecyclerView.getLayoutManager()).setScrollEnabledVertically(true);
    }

    private void setNewSearch(boolean b) {
        if (b) {
            mPageToFetch=1;
            mPhotoRecyclerView.setVisibility(View.INVISIBLE);
        }
        else {
            mPhotoRecyclerView.setVisibility(View.VISIBLE);
        }
        mNewSearch=b;
    }


}
