package com.mkpazon.foodnearby.net;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mkpazon on 4/4/15.
 * -=Bitbitbitbit=-
 */
public class PlacesUtility {
    private static final String TAG = "LocationManager";
    private static final String NEARBY_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    private static final String GOOGLE_BROWSER_API_KEY = "AIzaSyAWkgzC4t3t0z4I5LCuszCBdfnqnfn32OQ";

    private PlacesUtility() {
    }

    public static void findPlacesWithinRadius(String type, Location location, int radius, PlacesSearchListener placesSearchListener) {
        GetNearbyTask task = new GetNearbyTask(type, location, radius, placesSearchListener);
        task.execute();
    }

    private static class GetNearbyTask extends AsyncTask<Void, Void, SearchResponse> {
        private final String types;
        private final Location location;
        private final int radius;
        private PlacesSearchListener placesSearchListener;

        public GetNearbyTask(String types, Location location, int radius, PlacesSearchListener placesSearchListener) {
            this.types = types;
            this.location = location;
            this.radius = radius;
            this.placesSearchListener = placesSearchListener;
        }

        @Override
        protected SearchResponse doInBackground(Void... params) {
            try {
                URL url = new URL(NEARBY_SEARCH_URL +
                        "location=" + location.getLatitude() + "," + location.getLongitude() +
                        "&radius=" + radius +
                        "&types=" + types +
                        "&key=" + GOOGLE_BROWSER_API_KEY); //TODO better way of adding this
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = null;
                try {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();

                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    Log.d(TAG, stringBuilder.toString());

                    Gson gson = new Gson();
                    return gson.fromJson(stringBuilder.toString(), SearchResponse.class);
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, "Failed to retrieve nearby places", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(SearchResponse searchResponse) {
            super.onPostExecute(searchResponse);

            placesSearchListener.onResult(searchResponse);
        }
    }
}
