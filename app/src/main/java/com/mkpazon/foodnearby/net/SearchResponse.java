package com.mkpazon.foodnearby.net;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mkpazon on 5/4/15.
 * -=Bitbitbitbit=-
 */
public class SearchResponse {

    private List<Result> results;

    public SearchResponse(){
        this.results = new ArrayList<>();
    }

    public List<Result> getResults() {
        return results;
    }
}
