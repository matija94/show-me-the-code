package com.bignerdranch.android.photogallery;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matija on 6.3.17..
 */

public class FlickrFetchr {

    private static final String TAG = "FLICKR FETCHER";
    private static final String API_KEY="233e9e9a591577b5ccbb16fb68964489";
    private static final String FETCH_RECENT_METHOD ="flickr.photos.getRecent";
    private static final String SEARCH_METHOD = "flickr.photos.search";
    private static final Uri ENDPOINT = Uri
            .parse("https://api.flickr.com/services/rest")
            .buildUpon()
            .appendQueryParameter("api_key", API_KEY)
            .appendQueryParameter("format", "json")
            .appendQueryParameter("nojsoncallback", "1") // this means get just raw json, without funcion wrapped
            .appendQueryParameter("extras", "url_s")
            .build();

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        // cast it to httpUrlConnection since we will be working with http urls
        // http url connection enables us to work with request methods, response codes etc..
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream inputStream = urlConnection.getInputStream();

            if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(urlConnection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            // continously read data into buffer until there is no more to read from input
            while ( (bytesRead=inputStream.read(buffer)) > 0){
                // write data read immediately since next time buffer is passed into read
                // it will lose all previously read data
                out.write(buffer, 0, bytesRead);
            }

            out.close();
            return out.toByteArray();
        }
        finally {
            urlConnection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<GalleryItem> fetchRecentPhotos(int page) {
        String url = buildUrl(FETCH_RECENT_METHOD, null, Integer.toString(page));
        Log.i(TAG, "Fetching page number: " + page);
        return downloadGalleryItems(url);
    }

    public List<GalleryItem> searchPhotos(String query, int page) {
        String url = buildUrl(SEARCH_METHOD, query, Integer.toString(page));
        Log.i(TAG, "Fetching photos by search key: " + query +". Page number: " + page);
        return downloadGalleryItems(url);
    }


    /**
     * Build the url to be fetched
     * @param method predefined method for flickr rest api
     * @param query will be used only if the method is equal to flickr.photos.search
     * @param page will be used only if the method is equal to flickr.photos.getRecent
     * @return url with appended parameters
     */
    private String buildUrl(String method, String query, String page) {
        Uri.Builder builder = ENDPOINT.buildUpon().appendQueryParameter("method", method);
        builder.appendQueryParameter("page", page);

        if (method.equals(SEARCH_METHOD)) {
            builder.appendQueryParameter("text", query);
        }

        return builder.toString();
    }

    private List<GalleryItem> downloadGalleryItems(String url) {

        List<GalleryItem> items = new ArrayList<>();
        try{
            String jsonString = getUrlString(url);
            JSONObject jsonBody = new JSONObject(jsonString);
            Log.i(TAG, "Recieved JSON" + jsonString);
            Log.i(TAG, "Parsing JSON. Loading Gallery items");
            parseItems(items, jsonBody);
            Log.i(TAG, "Gallery items loaded successfully from parsed JSON. Size of items loaded : " + items.size());
        }catch (IOException e) {
            Log.e(TAG, "Failed to fetch items", e);
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse JSON", e);
        }
        return items;
    }

    private void parseItems(List<GalleryItem> items, JSONObject jsonBody) throws JSONException {
        JSONObject photosJsonObject = jsonBody.getJSONObject("photos");
        JSONArray photoJsonArray = photosJsonObject.getJSONArray("photo");

        for (int i=0; i<photoJsonArray.length(); i++) {
            JSONObject photoJsonObject = photoJsonArray.getJSONObject(i);

            GalleryItem item = new GalleryItem();
            item.setId(photoJsonObject.getString("id"));
            item.setCaption(photoJsonObject.getString("title"));
            item.setOwner(photoJsonObject.getString("owner"));

            if (!photoJsonObject.has("url_s")) {
                continue;
            }
            item.setUrl(photoJsonObject.getString("url_s"));
            items.add(item);
        }
    }
}
