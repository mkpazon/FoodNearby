package com.mkpazon.foodnearby;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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

    public static void findPlacesWithinRadius(String type, Location location, int radius) {
        GetNearbyTask task = new GetNearbyTask(type, location, radius);
        task.execute();
    }

    private static class GetNearbyTask extends AsyncTask<Void, Void, Void> {

        private final String types;
        private final Location location;
        private final int radius;

        public GetNearbyTask(String types, Location location, int radius) {
            this.types = types;
            this.location = location;
            this.radius = radius;
        }

        @Override
        protected Void doInBackground(Void... params) {
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

                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }

            } catch (MalformedURLException e) {
                //TODO handle this
                e.printStackTrace();
            } catch (IOException e) {
                //TODO handle this
                e.printStackTrace();
            }

            return null;
        }


    }

}
