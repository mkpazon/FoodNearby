package com.mkpazon.foodnearby;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mkpazon.foodnearby.net.Result;

/**
 * Created by mkpazon on 6/4/15.
 * -=Bitbitbitbit=-
 */
public class PlaceDetailsActivity extends ActionBarActivity {

    private static final String TAG = "PlaceDetailsActivity";
    public static final String EXTRA_PLACE_JSON = "place";

    private TextView tvName;
    private TextView tvVicinity;
    private TextView tvOpeningHours;
    private Result place;

    //TODO add photos?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        setTitle(getString(R.string.details));

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_PLACE_JSON)) {
            String placeJson = getIntent().getStringExtra(EXTRA_PLACE_JSON);
            Gson gson = new Gson();
            place = gson.fromJson(placeJson, Result.class);
            initializeViews();
        } else {
            //TODO show error info dialog and exit
            Log.e(TAG, "Place JSON missing");
        }
    }

    private void initializeViews() {
        tvName = (TextView) findViewById(R.id.textView_name);
        tvVicinity = (TextView) findViewById(R.id.textView_vicinity);
        tvOpeningHours = (TextView) findViewById(R.id.textView_opening_hours);

        tvName.setText(place.getName());
        tvVicinity.setText(place.getVicinity());
        tvOpeningHours.setText("???");

    }
}
