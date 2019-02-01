package com.service.parking.theparker.Controller.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.service.parking.theparker.Controller.Activity.ParkingPinActivity;
import com.service.parking.theparker.Controller.Activity.ProfileActivity;
import com.service.parking.theparker.R;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class OfferPlaceFragment extends Fragment {

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

        return view;
    }
}
