package com.bignerdranch.android.photogallery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.webkit.WebView;

/**
 * Created by matija on 9.5.17..
 */

public class PhotoPageActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context, Uri photoPageUri) {
        Intent i = new Intent(context, PhotoPageActivity.class);
        i.setData(photoPageUri);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        return PhotoPageFragment.newInstance(getIntent().getData());
    }

    @Override
    public void onBackPressed() {
        PhotoPageFragment photoPageFragment = (PhotoPageFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        WebView webView = photoPageFragment.getWebView();
        if (webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }

}
