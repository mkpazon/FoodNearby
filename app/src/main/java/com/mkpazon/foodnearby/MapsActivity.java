package com.mkpazon.foodnearby;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.mkpazon.foodnearby.net.PlacesSearchListener;
import com.mkpazon.foodnearby.net.PlacesUtility;
import com.mkpazon.foodnearby.net.Result;
import com.mkpazon.foodnearby.net.SearchResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends ActionBarActivity implements GoogleMap.OnMyLocationChangeListener, View.OnClickListener, GoogleMap.OnInfoWindowClickListener {

    private static final String TAG = "MapsActivity";

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Button btnFindNearby;

    private Location currentLocation;
    private static final int DEFAULT_RADIUS = 500;
    private Map<Marker, Result> places = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setTitle(getString(R.string.map));
        initializeViews();
    }

    private void initializeViews() {
        setUpMapIfNeeded();
        btnFindNearby = (Button) findViewById(R.id.button_findNearby);
        btnFindNearby.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(this);
        mMap.setOnInfoWindowClickListener(this);
    }

    private void addMarker(Result place) {
        com.mkpazon.foodnearby.net.Location location = place.getGeometry().getLocation();
        double latitude = location.getLat();
        double longitude = location.getLng();
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title(place.getName())
                .snippet(place.getVicinity()));
        places.put(marker, place);

    }

    private void clearMarkers() {
        for (Marker marker : places.keySet()) {
            //Remove marker from map
            marker.remove();
        }
        places.clear();
    }

    @Override
    public void onMyLocationChange(Location location) {
        this.currentLocation = location;
    }

    @Override
    public void onClick(View v) {
        if (v == btnFindNearby) {
            if (currentLocation != null) {
                List<String> types = new ArrayList<>();
                types.add("food");
                types.add("cafe");
                types.add("restaurant");
                PlacesUtility.findPlacesWithinRadius(this, types, currentLocation, DEFAULT_RADIUS, new PlacesSearchListener() {
                    @Override
                    public void onResult(SearchResponse response) {
                        Log.d(TAG, "Clearing all markers");
                        clearMarkers();

                        Log.d(TAG, "Adding result markers");
                        List<Result> results = response.getResults();
                        for (Result result : results) {
                            com.mkpazon.foodnearby.net.Location location = result.getGeometry().getLocation();
                            double latitude = location.getLat();
                            double longitude = location.getLng();
                            Log.d(TAG, "> " + result.getName() + " " + latitude + "," + longitude);
                            addMarker(result);
                        }
                    }
                });
            } else {
                Log.d(TAG, "Current location not available");
                final Dialog dialog = InfoDialog.newInstance(this, getString(R.string.error),
                        getString(R.string.current_location_not_available), null);
                dialog.show();
            }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.d(TAG, "Info window of marker \"" + marker.getTitle() + "\" has been clicked.");
        Result place = places.get(marker);

        Gson gson = new Gson();
        String placeJson = gson.toJson(place);

        Intent intent = new Intent(this, PlaceDetailsActivity.class);
        intent.putExtra(PlaceDetailsActivity.EXTRA_PLACE_JSON, placeJson);
        startActivity(intent);
    }
}
