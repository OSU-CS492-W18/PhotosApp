package com.example.android.photosapp;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.photosapp.utils.FlickrUtils;
import com.example.android.photosapp.utils.NetworkUtils;

import java.io.IOException;

/**
 * Created by hessro on 3/13/18.
 */

public class FlickrExploreLoader extends AsyncTaskLoader<String> {
    private final static String TAG = FlickrExploreLoader.class.getSimpleName();

    String mExploreResultsJSON;

    FlickrExploreLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if (mExploreResultsJSON != null) {
            deliverResult(mExploreResultsJSON);
        } else {
            forceLoad();
        }
    }

    @Override
    public String loadInBackground() {
        String flickrExploreURL = FlickrUtils.buildFlickrExploreURL();
        String exploreResults = null;
        try {
            exploreResults = NetworkUtils.doHTTPGet(flickrExploreURL);
        } catch (IOException e) {
            Log.d(TAG, "Error connecting to Flickr", e);
        }
        return exploreResults;
    }

    @Override
    public void deliverResult(String data) {
        mExploreResultsJSON = data;
        super.deliverResult(data);
    }
}
