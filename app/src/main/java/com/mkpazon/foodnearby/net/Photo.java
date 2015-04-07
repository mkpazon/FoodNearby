package com.mkpazon.foodnearby.net;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mkpazon on 7/4/15.
 * -=Bitbitbitbit=-
 */
public class Photo {

    @SerializedName("photo_reference")
    private String photoReference;
    private int height;
    private int width;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getPhotoReference() {
        return photoReference;
    }
}
