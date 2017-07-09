package com.bignerdranch.android.photogallery;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * Created by matija on 21.3.17..
 */

public class ScrollLockLayout extends GridLayoutManager {
    private boolean mIsScrollVerticalEnabled=true;

    public ScrollLockLayout(Context context, int spanCount) {
        super(context, spanCount);
    }

    public void setScrollEnabledVertically(boolean scrollEnabledVertically) {
        mIsScrollVerticalEnabled=scrollEnabledVertically;
    }

    @Override
    public boolean canScrollVertically() {
        return mIsScrollVerticalEnabled && super.canScrollVertically();
    }

    public boolean isScrollVerticalEnabled() {
        return mIsScrollVerticalEnabled;
    }

}
