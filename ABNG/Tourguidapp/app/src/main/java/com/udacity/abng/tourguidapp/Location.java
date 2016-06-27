package com.udacity.abng.tourguidapp;

/**
 * Created by yul on 27.06.16.
 */
public class Location {

    //Constant value that represents no image was provided for this location
    private static final int NO_IMAGE_PROVIDED = -1;
    //String resource ID for the default Location Name
    private int mLocationName;
    //String resource ID for the Location Description
    private int mLocationDescription;
    // Image resource ID for this Location
    private int mImageResourceId = NO_IMAGE_PROVIDED;


    //Create a new Location object.
    public Location(int locationName, int locationDescription) {
        mLocationName = locationName;
        mLocationDescription = locationDescription;
    }

    public Location(int locationName, int locationDescription, int imageResourceId) {
        mLocationName = locationName;
        mLocationDescription = locationDescription;
        mImageResourceId = imageResourceId;
    }

    public int getmLocationName() {
        return mLocationName;
    }

    public int getmLocationDescription() {
        return mLocationDescription;
    }

    public int getmImageResourceId() {
        return mImageResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

}
