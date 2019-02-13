package com.service.parking.theparker.Controller.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.service.parking.theparker.Controller.Activity.OfferLocation.ParkingPinActivity;
import com.service.parking.theparker.Controller.Activity.ProfileActivity;
import com.service.parking.theparker.Controller.Adapters.MySpotsAdapter;
import com.service.parking.theparker.Model.LocationPin;
import com.service.parking.theparker.R;
import com.service.parking.theparker.Services.NetworkServices;

import java.util.ArrayList;
import java.util.List;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class OfferPlaceFragment extends Fragment {

    @BindView(R.id.mySpotRecyclerView)
    RecyclerView mMySpotsRecyclerView;

    MySpotsAdapter mySpotsAdapter;
    List<LocationPin> locationPinList;

    @BindView(R.id.fragment_name)
    TextView mFragmentName;

    @BindView(R.id.custom_bar_image)
    CircleImageView mProfileView;

    @BindView(R.id.add_parking_btn)
    CircleButton mAddParkingBtn;

    public OfferPlaceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offer_place, container, false);
        ButterKnife.bind(this,view);

        mFragmentName.setText("Offer Place");

        mProfileView.setOnClickListener(v -> startActivity(new Intent(getContext(), ProfileActivity.class)));

        mAddParkingBtn.setOnClickListener(v -> startActivity(new Intent(getContext(), ParkingPinActivity.class)));

        locationPinList = new ArrayList<>();
        mySpotsAdapter = new MySpotsAdapter(locationPinList,getContext());

        RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(getContext());
        mMySpotsRecyclerView.setLayoutManager(mLayoutmanager);
        mMySpotsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMySpotsRecyclerView.setAdapter(mySpotsAdapter);

        NetworkServices.ParkingPin.getMyLocationPins(locationPinList,mySpotsAdapter);

        return view;
    }
}
