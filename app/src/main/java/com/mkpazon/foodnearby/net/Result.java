package com.mkpazon.foodnearby.net;

/**
 * Created by mkpazon on 5/4/15.
 * -=Bitbitbitbit=-
 */
public class Result {
    //TODO cleanup!

    //    private String icon;
//    private String id;
//    private String placeId;
//    private List<String> photos;

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
}
