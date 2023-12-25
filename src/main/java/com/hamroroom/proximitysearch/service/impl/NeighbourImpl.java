package com.hamroroom.proximitysearch.service.impl;

import com.hamroroom.proximitysearch.service.Neighbour;

import java.util.UUID;

public class NeighbourImpl implements Neighbour {
    private final UUID mId;
    private final Double mLatitude;
    private final Double mLongitude;

    public NeighbourImpl(UUID id, Double latitude, Double longitude) {
        mId = id;
        mLatitude = latitude;
        mLongitude = longitude;
    }

    @Override
    public UUID getId() {
        return mId;
    }

    @Override
    public double getLatitude() {
        return mLatitude;
    }

    @Override
    public double getLongitude() {
        return mLongitude;
    }

    public String toString(){
        return "Id: " + mId + ", Lat: " + mLatitude + ", Long: " + mLongitude;
    }
}
