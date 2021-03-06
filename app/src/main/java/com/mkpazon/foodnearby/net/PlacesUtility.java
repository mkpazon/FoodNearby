package com.mkpazon.foodnearby.net;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.mkpazon.foodnearby.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by mkpazon on 4/4/15.
 * -=Bitbitbitbit=-
 */
public class PlacesUtility {
    private static final String TAG = "LocationManager";
    private static final String NEARBY_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";

    private PlacesUtility() {
    }

    public static void findPlacesWithinRadius(Context context, List<String> types, Location location, int radius, PlacesSearchListener placesSearchListener) {
        GetNearbyTask task = new GetNearbyTask(context, types, location, radius, placesSearchListener);
        task.execute();
    }

    private static class GetNearbyTask extends AsyncTask<Void, Void, SearchResponse> {
        private final List<String> types;
        private final Location location;
        private final int radius;
        private PlacesSearchListener placesSearchListener;
        private final String apiKey;

        public GetNearbyTask(Context context, List<String> types, Location location, int radius, PlacesSearchListener placesSearchListener) {
            this.types = types;
            this.location = location;
            this.radius = radius;
            this.placesSearchListener = placesSearchListener;
            this.apiKey = context.getResources().getString(R.string.google_browser_key);
        }

        @Override
        protected SearchResponse doInBackground(Void... params) {
            try {
                StringBuilder typesStrBuilder = new StringBuilder();
                typesStrBuilder.append(types.get(0));
                for (int i = 1; i < types.size(); i++) {
                    typesStrBuilder.append("|" + types.get(i));
                }

                URL url = new URL(NEARBY_SEARCH_URL +
                        "location=" + location.getLatitude() + "," + location.getLongitude() +
                        "&radius=" + radius +
                        "&types=" + typesStrBuilder.toString() +
                        "&key=" + apiKey);
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
