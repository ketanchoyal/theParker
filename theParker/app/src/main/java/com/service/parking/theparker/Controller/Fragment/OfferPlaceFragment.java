package com.service.parking.theparker.Controller.Fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.service.parking.theparker.Controller.Activity.OfferLocation.ParkingPinActivity;
import com.service.parking.theparker.Controller.Adapters.MySpotsAdapter;
import com.service.parking.theparker.Model.LocationPin;
import com.service.parking.theparker.R;
import com.service.parking.theparker.Services.NetworkServices;

import java.util.ArrayList;
import java.util.List;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;


public class OfferPlaceFragment extends Fragment {

    @BindView(R.id.mySpotRecyclerView)
    RecyclerView mMySpotsRecyclerView;

    MySpotsAdapter mySpotsAdapter;
    List<LocationPin> locationPinList;

    @BindView(R.id.add_parking_btn)
    CircleButton mAddParkingBtn;

    @BindView(R.id.floating_add_parking_btn)
    FloatingActionButton mFloatingAddParkingBtn;

    public OfferPlaceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offer_place, container, false);
        ButterKnife.bind(this,view);

        mAddParkingBtn.setOnClickListener(v ->{
            Intent addPin = new Intent(getContext(), ParkingPinActivity.class);
            Pair pair = new Pair<View, String>(mAddParkingBtn,"circleBtn");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pair);
            startActivity(addPin,options.toBundle());
        });

        mFloatingAddParkingBtn.setOnClickListener(v -> {
            Intent addPin = new Intent(getContext(), ParkingPinActivity.class);
            Pair pair = new Pair<View, String>(mFloatingAddParkingBtn,"circleBtn");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pair);
            startActivity(addPin,options.toBundle());
        });

        locationPinList = new ArrayList<>();
        mySpotsAdapter = new MySpotsAdapter(locationPinList,getContext());

        RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(getContext());
        mMySpotsRecyclerView.setLayoutManager(mLayoutmanager);
        mMySpotsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMySpotsRecyclerView.setAdapter(mySpotsAdapter);

        NetworkServices.ParkingPin.getMyLocationPins(locationPinList,mySpotsAdapter);

        mMySpotsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    mFloatingAddParkingBtn.show();
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && mFloatingAddParkingBtn.isShown())
                    mFloatingAddParkingBtn.hide();
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        return view;
    }
}
