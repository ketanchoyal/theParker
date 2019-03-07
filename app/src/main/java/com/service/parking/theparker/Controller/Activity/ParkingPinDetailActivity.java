package com.service.parking.theparker.Controller.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.service.parking.theparker.Model.LocationPin;
import com.service.parking.theparker.R;

public class ParkingPinDetailActivity extends AppCompatActivity {

    LocationPin selectedPin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_pin_detail);

    }

}
