package com.service.parking.theparker.Controller.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.service.parking.theparker.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BookingFragment extends Fragment {

    public BookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_booking, container, false);
        ButterKnife.bind(this,v);

        return v;
    }


}
