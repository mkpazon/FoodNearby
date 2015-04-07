package com.mkpazon.foodnearby;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by mkpazon on 7/4/15.
 * -=Bitbitbitbit=-
 */
public class FoodNearbyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(this).build());
    }
}
