package com.service.parking.theparker.Controller.Fragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.service.parking.theparker.Controller.Activity.ProfileActivity;
import com.service.parking.theparker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class WalletFragment extends Fragment {

    @BindView(R.id.fragment_name)
    TextView mFragmentName;

    @BindView(R.id.custom_bar_image)
    CircleImageView mProfileView;

    public WalletFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_wallet, container, false);
        ButterKnife.bind(this,v);

        mFragmentName.setText("Wallet");

        mProfileView.setOnClickListener(v1 -> {
            Intent toProfileActivity = new Intent(getContext(), ProfileActivity.class);
            Pair pair = new Pair<View, String>(mProfileView,"circleImage");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pair);
            startActivity(toProfileActivity,options.toBundle());
        });
        return v;
    }
}
