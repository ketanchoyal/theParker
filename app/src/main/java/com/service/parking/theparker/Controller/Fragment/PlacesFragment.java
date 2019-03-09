package com.service.parking.theparker.Controller.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.service.parking.theparker.Controller.Activity.SplashScreenActivity;
import com.service.parking.theparker.R;
import com.service.parking.theparker.Services.NetworkServices;
import com.service.parking.theparker.View.SearchableSpinner.SpinnerDialog;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_places, container, false);
        ButterKnife.bind(this,view);

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
            startActivity(new Intent(getContext(), SplashScreenActivity.class));
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
            // For showing a move to my location button
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            googleMap.setMyLocationEnabled(true);

            // For dropping a marker at a point on the Map
//            LatLng sydney = new LatLng(-34, 151);
//            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
//
//            // For zooming automatically to the location of the marker
//            CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        });
    }

    public PlacesFragment() {
        // Required empty public constructor
    }

}
