package com.service.parking.theparker.Controller;

import android.os.Bundle;

import com.service.parking.theparker.R;

public class PlacesActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());



    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_places;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.places_activity;
    }

}
