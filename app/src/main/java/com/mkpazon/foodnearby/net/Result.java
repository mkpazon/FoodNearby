package com.mkpazon.foodnearby.net;

import java.util.List;

/**
 * Created by mkpazon on 5/4/15.
 * -=Bitbitbitbit=-
 */
public class Result {

    private List<Photo> photos;
    private Geometry geometry;
    private String name;
    private String vicinity;

    public String getName() {
        return name;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public String getVicinity() {
        return vicinity;
    }

    public List<Photo> getPhotos() {
        return photos;
    }
}
