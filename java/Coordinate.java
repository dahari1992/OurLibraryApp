package com.example.matan.library;

public class Coordinate {
    //vars
    private double lag;
    private double lat;

    //constructors
    public Coordinate(double lag, double lat) {
        this.lag = lag;
        this.lat = lat;
    }

    //getters
    public double getLag() {
        return lag;
    }

    public double getLat() {
        return lat;
    }
}
