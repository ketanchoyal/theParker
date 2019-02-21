package com.service.parking.theparker.Controller.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.service.parking.theparker.Controller.Activity.ProfileActivity;
import com.service.parking.theparker.R;
import com.service.parking.theparker.Services.NetworkServices;
import com.service.parking.theparker.View.SearchableSpinner.SpinnerDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import static com.service.parking.theparker.Services.NetworkServices.ParkingPin.parkingAreas;

public class PlacesFragment extends Fragment {

    @BindView(R.id.fragment_name)
    TextView mFragmentName;

    @BindView(R.id.custom_bar_image)
    CircleImageView mProfileView;

    @BindView(R.id.searchBtn)
    ImageView mSearchBtn;

    @BindView(R.id.areaNameField)
    TextView mAreaNameField;

    SpinnerDialog spinnerDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_places, container, false);
        ButterKnife.bind(this,view);
        mFragmentName.setText("Places");

        spinnerDialog=new SpinnerDialog(getActivity(),parkingAreas,"Select or Search Area",R.style.DialogAnimations_SmileWindow,"Close");

        spinnerDialog.setCancellable(true); // for cancellable
        spinnerDialog.setShowKeyboard(false);// for open keyboard by default

        spinnerDialog.bindOnSpinerListener((item, position) -> {
            Toasty.info(getContext(), item + "  " + position).show();
                mAreaNameField.setText(item + " Position: " + position);
        });

        mSearchBtn.setOnClickListener(v -> spinnerDialog.showSpinerDialog());

        mProfileView.setOnClickListener(v1 -> startActivity(new Intent(getContext(), ProfileActivity.class)));

        return view;
    }

    public PlacesFragment() {
        // Required empty public constructor
    }

}
