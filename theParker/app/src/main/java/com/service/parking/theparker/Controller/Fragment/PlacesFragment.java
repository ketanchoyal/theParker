package com.service.parking.theparker.Controller.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.service.parking.theparker.Controller.Activity.ParkingPinDetailActivity;
import com.service.parking.theparker.Model.LocationPin;
import com.service.parking.theparker.R;
import com.service.parking.theparker.Services.NetworkServices;
import com.service.parking.theparker.Theparker;
import com.service.parking.theparker.View.SearchableSpinner.SpinnerDialog;

import java.util.Objects;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

import static com.service.parking.theparker.Services.NetworkServices.ParkingPin.parkingAreas;

public class PlacesFragment extends Fragment {

    @BindView(R.id.searchBtn)
    CircleButton mSearchBtn;

    @BindView(R.id.areaNameField)
    TextView mAreaNameField;

    SpinnerDialog spinnerDialog;
    MapView mMapView;
    private GoogleMap googleMap;

    private Location mLastKnownLocation;

    Boolean mLocationPermissionGranted = false;
    FusedLocationProviderClient mFusedLocationProviderClient;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final float DEFAULT_ZOOM = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_places, container, false);
        ButterKnife.bind(this,view);
        Theparker.animate(getActivity());

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        mMapView = view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        setupMapView();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            spinnerDialog=new SpinnerDialog(getActivity(),parkingAreas,"Select or Search Area",R.style.DialogAnimations_SmileWindow,"Close");
            spinnerDialog.setCancellable(true); // for cancellable
            spinnerDialog.setShowKeyboard(false);// for open keyboard by default
        }

        spinnerDialog.bindOnSpinerListener((item, position) -> {
            NetworkServices.ParkingPin.getParkingsOfArea(item,googleMap,true);
            Toasty.info(getContext(), item + "  " + position).show();
                mAreaNameField.setText(item);
        });

        mSearchBtn.setOnClickListener(v -> spinnerDialog.showSpinerDialog());

        mSearchBtn.setOnLongClickListener(v -> {
            //doTransaction Functioin demo
//            String uid  = FirebaseAuth.getInstance().getCurrentUser().getUid();
//            Transaction transaction = new Transaction("200",""+uid,"tVKSi9PujnUUXRfzLNB1f0MRkvt2","-L_b8-YiUu883DeRJUfH","Parking","timestamp");
//            NetworkServices.TransactionData.doTransaction(transaction);
            return false;
        });

        return view;
    }

    void setupMapView() {

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(mMap -> {
            googleMap = mMap;
            NetworkServices.ParkingPin.getParkingAreas(googleMap);

            getLocationPermission();
//            mLocationPermissionGranted = true;
            updateLocationUI();
            getDeviceLocation();


            googleMap.setOnMarkerClickListener(marker -> {

                LocationPin pin = (LocationPin) marker.getTag();
                Theparker.selectedLocationPin = pin;
                Log.d("RANDOM TAG",pin.getAddress()+" "+pin.getPrice());

                startActivity(new Intent(getContext(), ParkingPinDetailActivity.class));

                return false;
            });
        });
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
            if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            } else {
                ActivityCompat.requestPermissions(getActivity(),
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
        if (googleMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                googleMap.setMyLocationEnabled(false);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
//                mLastKnownLocation = null;
                getLocationPermission();
//                mLocationPermissionGranted = true;
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
                locationResult.addOnCompleteListener(Objects.requireNonNull(getActivity()), task -> {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        if (checkGPSStatus(getActivity())) {
                            mLastKnownLocation = task.getResult();
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {

                        }

                    } else {
                        Log.d("XYZ", "Current location is null. Using defaults.");
                        Log.e("XYZ", "Exception: %s", task.getException());
                        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    public static boolean checkGPSStatus(Context context){
        LocationManager manager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return statusOfGPS;
    };

    public PlacesFragment() {
        // Required empty public constructor
    }

}
