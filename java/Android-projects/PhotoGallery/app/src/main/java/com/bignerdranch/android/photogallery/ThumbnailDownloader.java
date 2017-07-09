package com.bignerdranch.android.photogallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by matija on 13.3.17..
 */

public class ThumbnailDownloader<T> {


    private static final String TAG = "THUMBNAIL DOWNLOADER";
    private static final int MESSAGE_DOWNLOAD = 0;
    private static final int MESSAGE_PRELOAD = 1;

    private ThumbnailDownloaderListener<T> mThumbnailDownloaderListener;
    private LruCache<String, Bitmap> cache = new LruCache<>(200);

    private List<String> mInPlaceQueuedUrls = new ArrayList<>();


    public interface ThumbnailDownloaderListener<T> {

        /**
         * Will be called after thumbnail is downloaded
         * @param target target which should hold the thumbnail downloaded
         * @param thumbnail downloaded thumbnail
         */
        void onThumbnailDownloaded(T target, Bitmap thumbnail);

        void onDownloadRequestMapEmpty();

        void onPreloadRequestMapEmpty();
    }

    public void setThumbnailDownloaderListener(ThumbnailDownloaderListener<T> thumbnailDownloaderListener) {
        mThumbnailDownloaderListener = thumbnailDownloaderListener;
    }


    public class InPlaceDownloader extends HandlerThread {

        private static final String TAG = "THUMBNAL_DLER.IN_PLACE";

        private Handler mRequestHandler;
        private Handler mResponseHandler;

        private ConcurrentHashMap<T, String> mRequestMap = new ConcurrentHashMap<>();

        public InPlaceDownloader(Handler responseHandler) {
            super(TAG);
            mRequestHandler = new Handler();
            mResponseHandler = responseHandler;
        }

        @Override
        protected void onLooperPrepared() {
            mRequestHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == MESSAGE_DOWNLOAD) {
                        T target = (T) msg.obj;
                        Log.i(TAG, "Got a download request for URL: " + mRequestMap.get(target));
                        handleRequest(target);
                    }
                }
            };
        }

        public boolean queueThumbnail(T target, String url) {
            if (mInPlaceQueuedUrls.contains(url)) {
                return false;
            }
            if (url == null) {
                mRequestMap.remove(target);
            } else {
                mRequestMap.put(target, url);
                mInPlaceQueuedUrls.add(url);
                mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD, target)
                        .sendToTarget();
            }
            return true;
        }

        private void handleRequest(final T target) {
            final String url = mRequestMap.get(target);
            final Bitmap bitmap = getImageAsBitmap(url);

            if (bitmap == null) {
                return;
            }

            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {

                    mRequestMap.remove(target);
                    mInPlaceQueuedUrls.remove(url);

                    mThumbnailDownloaderListener.onThumbnailDownloaded(target, bitmap);

                    if (mRequestMap.isEmpty()) {
                        mThumbnailDownloaderListener.onDownloadRequestMapEmpty();
                    }
                }
            });

        }

        public void clearQueue() {
            mRequestHandler.removeMessages(MESSAGE_DOWNLOAD);
        }

    }

    public class PreloadDownloader extends HandlerThread {

        private static final String TAG = "THUMBNAIL_DLER.PRELOAD";

        private Handler mRequestHandler;
        private Handler mResponseHandler;
        private ConcurrentHashMap<Integer, String> mRequestMap = new ConcurrentHashMap<>();

        public PreloadDownloader(Handler responseHandler) {
            super(TAG);
            mRequestHandler = new Handler();
            mResponseHandler = responseHandler;
        }

        @Override
        protected void onLooperPrepared() {
            mRequestHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == MESSAGE_PRELOAD) {
                        int holderPos = (int) msg.obj;
                        Log.i(TAG, "Got a preload request URL=" + mRequestMap.get(holderPos) + ". Holder pos=" + holderPos);
                        handleRequest(holderPos);
                    }
                }
            };
        }

        public void preloadImage(int holderPosition, String url) {
            if (url == null) {
                mRequestMap.remove(holderPosition);
                return;
            }

            if (mInPlaceQueuedUrls.contains(url)) {
                //Log.i(TAG, "Already queued for downloading URL=" + url);
                return;
            }

            if (cache.get(url) != null) {
                //Log.i(TAG, "Cache already contains url=" + url);
                return;
            }

            if (mRequestMap.containsKey(holderPosition)) {
                //Log.i(TAG, "Processing url=" + mPreloadRequestMap.get(holderPosition) + ", already!");
                return;
            }

            mRequestMap.put(holderPosition,url);
            mRequestHandler.obtainMessage(MESSAGE_PRELOAD, holderPosition)
                    .sendToTarget();
        }

        private void handleRequest(final int holderPos) {
            final String url = mRequestMap.get(holderPos);
            if (url==null) return;
            final Bitmap bitmap = getImageAsBitmap(url);

            if (bitmap == null) return;

            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {

                    mRequestMap.remove(holderPos);

                    if (mRequestMap.isEmpty()) {
                        mThumbnailDownloaderListener.onPreloadRequestMapEmpty();
                    }
                }
            });
        }

        public void clearQueue() {
            mRequestHandler.removeMessages(MESSAGE_PRELOAD);
        }
    }

    public void clearCache() {
        cache.evictAll();
    }

    public Bitmap getCachedImage(String url) {
        return cache.get(url);
    }


    private Bitmap getImageAsBitmap(String url) {
        if (url == null) {
            return null;
        }

        Bitmap bitmap=cache.get(url);
        if (bitmap==null) {
            byte[] bitmapBytes = new byte[0];
            try {
                bitmapBytes = new FlickrFetchr().getUrlBytes(url);
            } catch (IOException e) {
                Log.e(TAG, "Couldn't download image", e);
            }
            bitmap = BitmapFactory
                    .decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
            cache.put(url, bitmap);
            Log.i(TAG, "Bitmap created");
        }
        return bitmap;
    }
    
}
