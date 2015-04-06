package com.mkpazon.foodnearby;

import com.google.android.gms.maps.model.Marker;
import com.mkpazon.foodnearby.net.Result;

/**
 * Created by mkpazon on 6/4/15.
 * -=Bitbitbitbit=-
 */
public class PlaceMarker {

    private final Marker marker;
    private final Result place;

    public PlaceMarker(Marker marker, Result place){
        this.marker = marker;
        this.place = place;
    }

    public Marker getMarker() {
        return marker;
    }

    public Result getPlace() {
        return place;
    }
}
