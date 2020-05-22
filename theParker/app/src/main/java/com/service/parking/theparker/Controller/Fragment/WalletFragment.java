package com.service.parking.theparker.Controller.Fragment;

import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.service.parking.theparker.R;
import com.service.parking.theparker.Services.NetworkServices;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletFragment extends Fragment {

    @BindView(R.id.wallet_available_balance)
    TextView wallet_available_balance;
    @BindView(R.id.wallet_available_earning)
    TextView wallet_available_earning;

    public WalletFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_wallet, container, false);
        ButterKnife.bind(this,v);

        Handler handler = new Handler();
        int delay = 2000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                wallet_available_balance.setText(NetworkServices.userProfile.Balance);
                wallet_available_earning.setText(NetworkServices.userProfile.Earnings);
                handler.postDelayed(this, delay);
            }
        }, delay);

        return v;
    }
}