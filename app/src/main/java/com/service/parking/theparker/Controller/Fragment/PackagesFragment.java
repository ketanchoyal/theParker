package com.service.parking.theparker.Controller.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.service.parking.theparker.Controller.Activity.ProfileActivity;
import com.service.parking.theparker.Controller.Adapters.PackageAdapter;
import com.service.parking.theparker.Model.Packages;
import com.service.parking.theparker.R;
import com.service.parking.theparker.Services.NetworkServices;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class PackagesFragment extends Fragment {

    @BindView(R.id.package_recyclerView)
    RecyclerView mPackageRecyclerView;

    PackageAdapter packageAdapter;
    List<Packages> packagesList;

    @BindView(R.id.fragment_name)
    TextView mFragmentName;

    @BindView(R.id.custom_bar_image)
    CircleImageView mProfileView;

    public PackagesFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_packages, container, false);
        ButterKnife.bind(this,rootView);

        mFragmentName.setText("Packages");

        packagesList = new ArrayList<>();
        packageAdapter = new PackageAdapter(packagesList);

        RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(getContext());
        mPackageRecyclerView.setLayoutManager(mLayoutmanager);
        mPackageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPackageRecyclerView.setAdapter(packageAdapter);

        NetworkServices.PackagesData.getPackages(packagesList,packageAdapter);

        mProfileView.setOnClickListener(v1 -> startActivity(new Intent(getContext(), ProfileActivity.class)));
        return rootView;
    }

}
