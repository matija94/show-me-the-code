package com.bignerdranch.android.photogallery;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.List;

/**
 * Created by matija on 1.4.17..
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PollJobService extends JobService {

    private static final String QUERY_KEY="pollJobServiceQueryKey";

    private static final String TAG="POLL_JOB_SERVICE";

    public static final int JOB_ID = 11;

    private class PollTask extends AsyncTask<JobParameters, Void, Void> {

        @Override
        protected Void doInBackground(JobParameters... params) {
            FlickrFetchr fetcher = new FlickrFetchr();
            String query = (String) params[0].getExtras().get(QUERY_KEY);
            List<GalleryItem> items = null;

            if (query == null) {
                Log.i(TAG, "Fetching recent photos");
                items = fetcher.fetchRecentPhotos(1);
            }
            else {
                Log.i(TAG, "Fetching searched photos. Query: " + query);
                items = fetcher.searchPhotos(query, 1);
            }

            Log.i(TAG, "Fetching finished");
            jobFinished(params[0],false);
            return null;
        };

    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "Job started!");
        String query = QueryPreferences.getStoredQuery(this);
        params.getExtras().putString(QUERY_KEY, query);
        new PollTask().execute(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    public static void scheduleService (Context context) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        JobInfo jobInfo = new JobInfo.Builder(JOB_ID, new ComponentName(context, PollJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(1000*60)
                //needs permission RECEIVE_BOOT_COMPLETED
                //.setPersisted(true)
                .build();

        scheduler.schedule(jobInfo);
        Log.i(TAG, "Job has been scheudled to run every minute!");
    }

    public static boolean isScheduled(Context context) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        for (JobInfo jobInfo : scheduler.getAllPendingJobs()) {
            if (jobInfo.getId()==JOB_ID) {
                return true;
            }
        }
        return false;
    }

    public static void cancelJob(Context context) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.cancel(JOB_ID);
        Log.i(TAG, "Job has been cancelled!");
    }
}
