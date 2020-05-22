package com.service.parking.theparker.Controller.Activity.OfferLocation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.service.parking.theparker.R;
import com.service.parking.theparker.Theparker;
import com.service.parking.theparker.Utils.LocationConstants;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class ParkingPinActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final float DEFAULT_ZOOM = 12;

    @BindView(R.id.action_bar_name)
    TextView mActionBarName;

    Boolean mLocationPermissionGranted = false;
    FusedLocationProviderClient mFusedLocationProviderClient;

    @BindView(R.id.back_btn)
    CircleImageView mBackBtn;

    @BindView(R.id.next_btn)
    CircleButton mNextBtn;

    SupportMapFragment supportMapFragment;

    GoogleMap map;
    private Location mLastKnownLocation;

    private int count = 0;
    private String area;
    private String address;
    private String pincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_pin);
        ButterKnife.bind(this);

        Theparker.animate(this);

        mActionBarName.setText("Pin Parking Spot");

        mBackBtn.setOnClickListener(v -> {
            onBackPressed();
            Theparker.animate(this);
        });

        mNextBtn.setOnClickListener(v -> {

            if (count > 0) {
                Intent areaAddress = new Intent(ParkingPinActivity.this, AreaAndAddressActivity.class);
                areaAddress.putExtra(LocationConstants.address,address);
                areaAddress.putExtra(LocationConstants.area,area);
                areaAddress.putExtra(LocationConstants.pincode,pincode);
                Pair pair = new Pair<View, String>(mNextBtn,"circleBtn");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pair);
                startActivity(areaAddress,options.toBundle());
            } else {
                Toasty.error(this, "Please select parking first!").show();
            }
        });

        supportMapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();
//        map.setOnMapClickListener(v -> {
//            Toasty.success(this, "Click Captured").show();
//        });

        map.setOnMapLongClickListener(v -> {

            count += 1;

            // Creating a marker
            MarkerOptions markerOptions = new MarkerOptions();

            // Setting the position for the marker
            markerOptions.position(v);

            // Setting the title for the marker.
            // This will be displayed on taping the marker
            markerOptions.title(v.latitude + " : " + v.longitude);

            // Clears the previously touched position
            map.clear();

            // Animating to the touched position
            map.animateCamera(CameraUpdateFactory.newLatLng(v));

            // Placing a marker on the touched position
            map.addMarker(markerOptions);

            Geocoder geocoder;
            List<Address> addresses = null;
            geocoder = new Geocoder(this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(v.latitude, v.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            } catch (IOException e) {
                e.printStackTrace();
            }

            address = addresses.get(0).getAddressLine(0);
            pincode = addresses.get(0).getPostalCode();

            area = addresses.get(0).getSubLocality();
            if (area == null) {
                area = addresses.get(0).getLocality();
            }

            Map<String,Double> pinloc = new HashMap<>();
            pinloc.put("lat",v.latitude);
            pinloc.put("long",v.longitude);

            Theparker.currentLocationpin.setPinloc(pinloc);

            Log.d("XYZ Location : ", v.latitude + " " + v.longitude);
            Log.d("XYZ Address : ", addresses.toString());

            Toasty.success(this, "Parking Selected").show();

        });

        map.setOnMyLocationButtonClickListener(() -> {
            getDeviceLocation();
            return false;
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Theparker.animate(this);
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = task.getResult();
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(mLastKnownLocation.getLatitude(),
                                        mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));

                    } else {
                        Log.d("XYZ", "Current location is null. Using defaults.");
                        Log.e("XYZ", "Exception: %s", task.getException());
                        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                        map.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

}
