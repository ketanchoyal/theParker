package com.service.parking.theparker.Controller.Adapters;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.service.parking.theparker.Model.LocationPin;
import com.service.parking.theparker.Model.Packages;
import com.service.parking.theparker.R;

import java.util.List;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MySpotsAdapter extends RecyclerView.Adapter<MySpotsAdapter.MySpotsViewHolder> {

    private List<LocationPin> locationPinList;
    private Context con;

    public MySpotsAdapter(List<LocationPin> locationPinList, Context con) {
        this.locationPinList = locationPinList;
        this.con = con;
    }

    @NonNull
    @Override
    public MySpotsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        con = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_spots_layout, parent, false);
        return new MySpotsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MySpotsViewHolder myViewHolder, int i) {
        GoogleMap thisMap = myViewHolder.googleMap;
        LocationPin pin = locationPinList.get(i);
        Log.d("RANDOM :", pin.getPinloc().toString());

        myViewHolder.mPrice.setText(pin.getPrice());
        myViewHolder.mNo_of_space.setText(pin.getNumberofspot());
        myViewHolder.mSpot_type.setText(pin.getType());

        if (thisMap != null) {
            Double lat = pin.getPinloc().get("lat");
            Double lon = pin.getPinloc().get("long");
            Log.d("RANDOM :", lat + " " + lon);
            LatLng latLng = new LatLng(lat, lon);

            myViewHolder.addMarker(latLng);

        }

    }

    @Override
    public void onViewRecycled(@NonNull MySpotsViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder.googleMap != null) {
            holder.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    @Override
    public int getItemCount() {
        return locationPinList.size();
    }

    class MySpotsViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
        @BindView(R.id.spot_type)
        TextView mSpot_type;

        @BindView(R.id.spot_price)
        TextView mPrice;

        @BindView(R.id.no_of_spots)
        TextView mNo_of_space;

        @BindView(R.id.spotMap)
        MapView mSpot_map;

        GoogleMap googleMap;

        MySpotsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            if (mSpot_map != null) {
                mSpot_map.onCreate(null);
                mSpot_map.onResume();
                mSpot_map.getMapAsync(this);
            }

        }

        public void addMarker(LatLng latLng) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            this.googleMap.clear();
            this.googleMap.addMarker(markerOptions);
            this.googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsInitializer.initialize(con);
            this.googleMap = googleMap;
            if (ActivityCompat.checkSelfPermission(con, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(con, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            this.googleMap.setMyLocationEnabled(true);
            this.googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }
}
