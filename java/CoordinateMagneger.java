package com.example.matan.library;


/**
 * singleton class to avoid null coordinates
 **/
class CoordinateManager{
    //vars
    private static CoordinateManager instance = null;
    private Coordinate coordinate;

    //methods
    public static CoordinateManager newInstance(){
        if(instance == null){
            instance = new CoordinateManager();
        }
        return instance;
    }

    public void setCooridnate(double lat,double longtitude){
        if(coordinate == null){
            coordinate = new Coordinate(lat,longtitude);
        }
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
