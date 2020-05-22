package com.service.parking.theparker.Controller.Fragment;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.service.parking.theparker.Controller.Adapters.PackageAdapter;
import com.service.parking.theparker.Model.Packages;
import com.service.parking.theparker.Model.UserProfile;
import com.service.parking.theparker.R;
import com.service.parking.theparker.Services.NetworkServices;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PackagesFragment extends Fragment {

    @BindView(R.id.package_recyclerView)
    RecyclerView mPackageRecyclerView;

    PackageAdapter packageAdapter;
    List<Packages> packagesList;

    @BindView(R.id.total_spots)
    TextView mTotalSpots;
    @BindView(R.id.spotAvai)
    TextView mSpotAvailable;
    @BindView(R.id.spotInUse)
    TextView mSpotInUse;

    public PackagesFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_packages, container, false);
        ButterKnife.bind(this, rootView);

        packagesList = new ArrayList<>();
        packageAdapter = new PackageAdapter(packagesList,getActivity());

        RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(getContext());
        mPackageRecyclerView.setLayoutManager(mLayoutmanager);
        mPackageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPackageRecyclerView.setAdapter(packageAdapter);

        NetworkServices.PackagesData.getPackages(packagesList, packageAdapter);

        Handler handler = new Handler();
        int delay = 1000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                UserProfile userProfile = NetworkServices.userProfile;
                int totalSpots = Integer.parseInt(userProfile.Total_spots);
                int spotsUsed = Integer.parseInt(userProfile.Spots_used);

                mTotalSpots.setText(""+totalSpots);
                mSpotInUse.setText(""+spotsUsed);
                mSpotAvailable.setText(""+(totalSpots - spotsUsed));
                handler.postDelayed(this, delay);
            }
        }, delay);

        return rootView;
    }

}
