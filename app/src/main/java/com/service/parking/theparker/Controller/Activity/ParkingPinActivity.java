package com.service.parking.theparker.Controller.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.service.parking.theparker.R;
import com.service.parking.theparker.Theparker;
import com.service.parking.theparker.View.ActivityAnimator;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class ParkingPinActivity extends AppCompatActivity implements OnMapReadyCallback {

    LocationManager locManager;

    @BindView(R.id.action_bar_name)
    TextView mActionBarName;

    @BindView(R.id.back_btn)
    CircleImageView mBackBtn;

    //@BindView(R.id.map)
    SupportMapFragment supportMapFragment;

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_pin);
        ButterKnife.bind(this);

        Theparker.animate(this);

        mActionBarName.setText("Pin Parking Spot");

        mBackBtn.setOnClickListener(v -> {
            finish();
            Theparker.animate(this);
        });

        supportMapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));

        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        map.setMyLocationEnabled(true);
        map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);

        map.setOnMapClickListener(v -> {
            Toasty.success(this,"Click Captured").show();
        });
        map.setOnMapLongClickListener(v -> {
            Toasty.success(this,"Long Click").show();
            startActivity(new Intent(ParkingPinActivity.this, ParkingDetailsActivity.class));
        });

        Location locationCt;
        LocationManager locationManagerCt = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationCt = locationManagerCt
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);

        LatLng latLng = new LatLng(locationCt.getLatitude(),
                locationCt.getLongitude());

        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        map.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Theparker.animate(this);
    }
}
