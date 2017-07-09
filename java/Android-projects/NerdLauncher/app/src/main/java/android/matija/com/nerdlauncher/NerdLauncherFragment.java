package android.matija.com.nerdlauncher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by matija on 2.3.17..
 */

public class NerdLauncherFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public static NerdLauncherFragment newInstance() {
        return new NerdLauncherFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.nerd_launcher_fragment,viewGroup,false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.nerd_launcher_fragment_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setUpAdapter();

        return v;
    }

    private class ActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // represents meta data for activity
        private ResolveInfo mResolveInfo;
        private TextView mNameTextView;
        private ImageView mAppIconTextview;

        public ActivityHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mNameTextView = (TextView) itemView.findViewById(R.id.nerd_launcher_fragment_recycler_view_text_view);
            mAppIconTextview = (ImageView) itemView.findViewById(R.id.nerd_launcher_fragment_recycler_view_icon_view);
        }

        public void bindActivity(ResolveInfo resolveInfo) {
            mResolveInfo=resolveInfo;
            PackageManager pm = getActivity().getPackageManager();
            String appName = mResolveInfo.loadLabel(pm).toString();
            mNameTextView.setText(appName);
            mAppIconTextview.setImageDrawable(mResolveInfo.loadIcon(pm));
        }

        @Override
        public void onClick(View v) {
            ActivityInfo activityInfo = mResolveInfo.activityInfo;

            // explicit intent
            Intent i = new Intent(Intent.ACTION_MAIN)
                    // creating explcit intent by passing packange name and activity class name
                    .setClassName(activityInfo.applicationInfo.packageName, activityInfo.name)
                    // by adding flags below we are telling OS to start each activity in it's own task
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }

    private class ActivityAdapter extends RecyclerView.Adapter<ActivityHolder> {
        private final List<ResolveInfo> mActivitiesMetadata;

        public ActivityAdapter(List<ResolveInfo> metadataActivities) {
            mActivitiesMetadata = metadataActivities;
        }

        @Override
        public ActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View v = inflater.inflate(R.layout.nerd_launcher_recycler_view_item, parent,false);
            return new ActivityHolder(v);
        }

        @Override
        public void onBindViewHolder(ActivityHolder holder, int position) {
            holder.bindActivity(mActivitiesMetadata.get(position));
        }

        @Override
        public int getItemCount() {
            return mActivitiesMetadata.size();
        }
    }

    // sets up the adapter for recycler view
    private void setUpAdapter() {
        // here we create an MAIN/LAUNCHER IMPLICIT INTENT
        // which is used to look-up all activities that can be performed with it
        // packetManager does this job for us
        Intent startupIntent = new Intent(Intent.ACTION_MAIN);
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(startupIntent,0);
        Log.i(getClass().getCanonicalName(), "Found " + activities.size() + " activities");

        // sort activities metadata by lables(application-names) alphabetically
        Collections.sort(activities, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo o1, ResolveInfo o2) {
                PackageManager pm = getActivity().getPackageManager();
                return String.CASE_INSENSITIVE_ORDER.compare(
                        o1.loadLabel(pm).toString(),
                        o2.loadLabel(pm).toString()
                );

            }
        });
        // register the adapter with activities metadata(result from query)
        mRecyclerView.setAdapter(new ActivityAdapter(activities));
    }
}
