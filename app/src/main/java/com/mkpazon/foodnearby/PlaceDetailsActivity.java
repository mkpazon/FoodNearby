package com.mkpazon.foodnearby;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mkpazon.foodnearby.net.Photo;
import com.mkpazon.foodnearby.net.Result;

import java.util.List;

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
    private ViewPager vpPhotos;
    private FragmentStatePagerAdapter adapter;

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

        vpPhotos = (ViewPager)findViewById(R.id.viewPager_photos);
        FragmentStatePagerAdapter adapter = new PhotoPagerAdapter(getSupportFragmentManager());
        vpPhotos.setAdapter(adapter);
    }


    private class PhotoPagerAdapter extends FragmentStatePagerAdapter {

        public PhotoPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            List<Photo> photos = place.getPhotos();
            String photoReference = photos.get(position).getPhotoReference();
            Fragment fragment = new PhotoFragment();
            Bundle data = new Bundle();
            data.putString(PhotoFragment.EXTRA_PHOTO_REFERENCE, photoReference);
            fragment.setArguments(data);
            return fragment;
        }

        @Override
        public int getCount() {
            if(place.getPhotos()!=null) {
                return place.getPhotos().size();
            } else {
                return 0;
            }
        }
    }
}
