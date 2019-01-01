package com.service.parking.theparker.Controller.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.service.parking.theparker.Controller.Adapters.PackageAdapter;
import com.service.parking.theparker.Model.PackageModel;
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
    List<PackageModel> packageModelList;

    public PackagesFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_packages, container, false);
        ButterKnife.bind(this,rootView);

        packageModelList= new ArrayList<>();
        packageAdapter = new PackageAdapter(packageModelList);

        RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(getContext());
        mPackageRecyclerView.setLayoutManager(mLayoutmanager);
        mPackageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPackageRecyclerView.setAdapter(packageAdapter);

        NetworkServices.Packages.getPackages(packageModelList,packageAdapter);

        return rootView;
    }

}
